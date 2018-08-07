package stickerbot;

import telegrambot.network.MsgHandler;
import telegrambot.tgcore.BotCfg;
import telegrambot.tgcore.Update;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main {

    private final static List<String> whitelist = Arrays.asList(
            "username"
    );

    public static void main(String[] args) throws Exception {

        System.out.println(BotCfg.bot_token);

        /* TODO handle each request in a separate thread */
        MsgHandler handler = new MsgHandler(BotCfg.bot_url);
        Map<Rid, StickerCollectionAbs> jobs = new HashMap<>();

        // TODO discard invalid requests
        handler.forEach((a)->{});

        for (handler.sync(); true; handler.sync()) {

            final Update u = handler.pop();
            final Rid rid  = Rid.create(u);

            if (u == null || rid == null)
                continue;

            System.out.println(rid);

            CmdUtils.Action action  = new CmdUtils.Action(u);
            StickerCollectionAbs sc = jobs.get(rid);

            /*
            if (whitelist.stream().noneMatch(u.message.from.username::equals)) {
                System.out.println(u);
                continue;
            }
            */

            if (sc == null && (action.getType() == CmdUtils.ActionType.INVALID || action.getType() == CmdUtils.ActionType.DONE)) {
                u.message.reply("Invalid action, type /help to get an help");
                continue;
            }

            switch (action.getType()) {

                case START:
                case HELP:
                    u.message.reply("Markdown", BotUtils.bot_commands);
                    break;

                case ABORT:
                    if (sc != null) {
                        jobs.put(rid, (sc = null));
                        u.message.reply("Operation aborted");
                    }
                    break;

                case NEW_STICKER_SET:
                case ADD_TO_STICKER_SET:
                    if (sc == null) {
                        try {

                            jobs.put(rid, (sc = new StickerCollection(rid, action.getArgs().get(0), action.getType() == CmdUtils.ActionType.NEW_STICKER_SET)));
                            u.message.reply( "Ok send me some stickers, when you have finished type /done or /abort.");

                        } catch (Exception e) {
                            e.printStackTrace();
                            u.message.reply(action.getType() == CmdUtils.ActionType.NEW_STICKER_SET ? "Sticker pack already exists" : "Sticker pack not found");
                            jobs.put(rid, (sc = null));
                        }
                    }
                    break;

                case DONE:
                    u.message.reply("Processing...");

                    try {
                        final StickerCollectionAbs ss = sc;
                        // System.out.println(Utils.benchmark(()->{ try { ss.publish(); } catch (Exception e) { throw new RuntimeException(e); }}));
                        sc.publish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        u.message.chat.sendMessage("FIXME");
                    }

                    jobs.put(rid, (sc = null));
                    break;

                case INVALID:
                    sc.fetchSticker(u);
            }

        }

    }

}
