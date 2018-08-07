package telegrambot.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

public final class Utils {

    public final static String benchmark(Runnable r) {
        final double start = System.currentTimeMillis();
        r.run();
        final double end = System.currentTimeMillis() - start;
        return String.format("%s Elapsed time: %.5f\n", r.getClass().getName(), (end * 1E-3));
    }

    // java 8 has no in.transferTo(out), the caller must close() these stream,
    // copy perform a out::flush before return

    public final static void copy(InputStream in, OutputStream out) {
        assert(in != null);
        assert(out != null);

        try {
            byte[] buf = new byte[4096];
            for (int len; (len = in.read(buf)) != -1; out.write(buf, 0, len));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // automatically close his stream
    public final static String mapToString(InputStream in) {

        assert(in != null);

        try (Scanner s = new Scanner(in)) {

            StringBuilder cat = new StringBuilder();

            while (s.hasNextLine())
                cat.append(s.nextLine());

            return cat + "";
        }

    }

    // input stream is automatically closed after fconsumer call
    public final static <T> T getHtmlBody(String url, Function<InputStream, T> fconsumer) throws InvalidUrlQueryException, NetworkError {

        try {

            final URL u = new URL(url);
            HttpURLConnection hcon = null;

            {
                URLConnection con = u.openConnection();
                assert(con instanceof HttpURLConnection);
                hcon = (HttpURLConnection)con;
            }

            hcon.setRequestProperty("Accept-Encoding", "gzip");

            int responseCode = hcon.getResponseCode();
            InputStream is   = responseCode == HttpURLConnection.HTTP_OK ? hcon.getInputStream() : hcon.getErrorStream();

            // if is gzipped decompress it
            try (InputStream in = "gzip".equals( (hcon.getContentEncoding()+"").toLowerCase() ) ? new GZIPInputStream(is) : is) {

                if (in == null) throw new RuntimeException("unknown error");

                switch (responseCode) {
                    case HttpURLConnection.HTTP_OK:
                        return fconsumer.apply(in);
                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        throw new InvalidUrlQueryException(Utils.mapToString(in));
                    default:
                        throw new RuntimeException("Error code: (" + responseCode + ")\n" + Utils.mapToString(in));
                }

            }

        } catch (IOException e) {
            throw new NetworkError(e);
        }

    }

    public static final String getHtmlBody(String url) throws InvalidUrlQueryException, NetworkError {
        return getHtmlBody(url, Utils::mapToString);
    }

    public static final void getHtmlBody(String url, File file, boolean overwrite) throws IOException, InvalidUrlQueryException, NetworkError {

        if (file.exists() && !overwrite)
            throw new FileAlreadyExistsException(file.getAbsolutePath());

        if (!file.exists())
            Files.createFile(file.toPath());

        try (OutputStream out = new FileOutputStream(file)) {
            // Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Utils.getHtmlBody(url, (in) -> { Utils.copy(in, out); return true; });
        }

    }

    // you must close OutputStream
    public static void getHtmlBody(String url, OutputStream out) throws InvalidUrlQueryException, NetworkError {
        Utils.getHtmlBody(url, (in) -> { Utils.copy(in,out); return true; });
    }


}