package telegrambot.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Multipart {

    public final static String getBoundary() {
        return "-------------------------" + Long.toHexString(System.currentTimeMillis());
    }

    private static class BodyWriter implements AutoCloseable {

        private final OutputStream out;
        private final PrintWriter writer;
        private final String boundary;

        // use a GZIPOutputStream as OutputStream
        private BodyWriter(OutputStream out, String boundary) throws IOException {
            this.writer   = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
            this.boundary = boundary;
            this.out      = out;
        }

        public void write(String k, String v) {

            writer.append("--" + boundary).append("\r\n")
                    .append("Content-Disposition: form-data; name=\"" + k + "\"\r\n")
                    .append("Content-Length: " + v.length() + "\r\n")
                    .append("\r\n")
                    .append(v)
                    .append("\r\n")
                    .flush();

        }

        public void write(String k, File v) throws IOException {

            try (InputStream in = new FileInputStream(v)) {
                writer.append("--" + boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"" + k + "\"; filename=\"" + v.getName() + "\"\r\n")
                        .append("Content-Type: " + Files.probeContentType(v.toPath()) + "\r\n")
                        .append("Content-Length: " + v.length() + "\r\n")
                        .append("\r\n")
                        .flush();

                Utils.copy(in, out);
                writer.append("\r\n").flush();
            }
        }

        @Override
        public void close() {
            writer.append("--" + boundary + "--").append("\r\n").flush();
            writer.close();
        }

    }

    public final static String multipartFormData(String url, String method_name, Map<String,String> fields, Map<String,File> files)
            throws InvalidUrlQueryException, NetworkError {


        try {

            final String boundary = Multipart.getBoundary();
            final URL u = new URL(url += ((url.endsWith("/") ? "" : "/") + method_name));
            HttpURLConnection hcon = null;

            {
                URLConnection conn = u.openConnection();
                assert(conn instanceof HttpURLConnection);
                hcon = (HttpURLConnection)conn;
            }


            hcon.setRequestMethod("POST");
            hcon.setDoOutput(true);
            hcon.setRequestProperty("Content-Type",     "multipart/form-data; boundary=" + boundary);
            hcon.setRequestProperty("Connection",       "Keep-Alive");
            hcon.setRequestProperty("Content-Encoding", "gzip");
            hcon.setRequestProperty("Accept-Encoding",  "gzip");

            // GZIP everything
            try (Multipart.BodyWriter writer = new Multipart.BodyWriter(new GZIPOutputStream(hcon.getOutputStream()), boundary)) {

                Lock lock = new ReentrantLock();

                fields.entrySet().parallelStream().forEach(
                        (e) -> {
                            lock.lock();
                            try     { writer.write(e.getKey(), e.getValue()); }
                            finally { lock.unlock();                          }
                        }
                );

                files.entrySet().parallelStream().forEach(
                        (e) -> {
                            lock.lock();
                            try { writer.write(e.getKey(), e.getValue());            }
                            catch (IOException ee) { throw new RuntimeException(ee); }
                            finally { lock.unlock();                                 }
                        }
                );

            }


            // Telegram doesn't use gzip
            int responseCode = hcon.getResponseCode();
            InputStream is = responseCode == HttpURLConnection.HTTP_OK ? hcon.getInputStream() : hcon.getErrorStream();

            // if is gzipped decompress it
            try (InputStream in = "gzip".equals((hcon.getContentEncoding() + "").toLowerCase()) ? new GZIPInputStream(is) : is) {

                if (in == null) throw new RuntimeException("unknown error");

                switch (responseCode) {
                    case HttpURLConnection.HTTP_OK:
                        return Utils.mapToString(in);
                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        throw new InvalidUrlQueryException(Utils.mapToString(in));
                    default:
                        throw new RuntimeException("Error code: (" + responseCode + ")\n" + Utils.mapToString(in));
                }

            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new NetworkError(e);
        }
    }

}
