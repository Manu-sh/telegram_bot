package it.manu.sh.telegram_bot.stickerbot;

public class Pair<A,B> {

    public A first;
    public B second;

    public Pair(A first, B second) {
        this.first  = first;
        this.second = second;
    }

    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (((Pair)o).first  == null || first == null)  return false;
        if (((Pair)o).second == null || second == null) return false;
        return first.equals(((Pair)o).first) && second.equals(((Pair)o).second);
    }

    @Override public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @Override public String toString() {
        return getClass().getName() + " <" + first + ", " + second + "> ";
    }

}
