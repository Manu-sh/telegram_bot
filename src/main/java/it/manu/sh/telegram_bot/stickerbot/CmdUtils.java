package it.manu.sh.telegram_bot.stickerbot;

import it.manu.sh.telegram_bot.tgcore.Update;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CmdUtils {

    public enum ActionType {
        NEW_STICKER_SET,
        ADD_TO_STICKER_SET,
        ABORT,
        DONE,
        HELP,
        START,
        INVALID
    }

    public static class Action {

        private final static Pattern cmd_reg = Pattern.compile("^/[\\w]+\\p{Space}?\\w+");
        private ActionType actionType = ActionType.INVALID;
        private ArrayList<String> args = new ArrayList<>(1);

        public Action(Update u) {

            try {

                if (u.message.text.length() > 1024 || !cmd_reg.matcher(u.message.text).matches())
                    return;

                String[] words =  u.message.text.split(" ");
                // System.out.println(Arrays.toString(words));

                switch ((words[0] = words[0].toLowerCase())) {
                    case "/done":
                        actionType = ActionType.DONE;
                        break;
                    case "/abort":
                        actionType = ActionType.ABORT;
                        break;
                    case "/help":
                        actionType = ActionType.HELP;
                        break;
                    case "/start":
                        actionType = ActionType.START;
                        break;
                    case "/new":
                        if (words.length != 2) return;
                        actionType = ActionType.NEW_STICKER_SET;
                        args.add(words[1]);
                        break;
                    case "/add":
                        if (words.length != 2) return;
                        actionType = ActionType.ADD_TO_STICKER_SET;
                        args.add(words[1]);
                        break;
                }

            } catch (NullPointerException e) {}
        }

        public ActionType getType() {
            return actionType;
        }
        public ArrayList<String> getArgs() {
            return args;
        }

    }

}
