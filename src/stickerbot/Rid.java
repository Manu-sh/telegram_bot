package stickerbot;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import telegrambot.tgcore.Chat;
import telegrambot.tgcore.Update;
import telegrambot.tgcore.User;


public class Rid extends Pair<User,Chat> {

    public static Rid create(Update u) {
        try { return new Rid(u);}
        catch (NullPointerException e){ return null; }
    }

    private Rid(Update u) {
        this(u.message.from, u.message.chat);
    }
    private Rid(User user, Chat chat) { super(user, chat); }

    public String uid() {
        return first.id+"";
    }
    public String cid() { return second.id+""; }


    public boolean equals(Update u) {
        Rid r = Rid.create(u);
        return !(r == null || !r.equals(this));
    }

    @Override public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override public int hashCode() {
        return super.hashCode();
    }

    @Override public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}