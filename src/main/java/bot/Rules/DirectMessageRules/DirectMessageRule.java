package bot.Rules.DirectMessageRules;

import bot.Rules.Rule;

public abstract class DirectMessageRule implements Rule {
    public boolean canHandle(String msg, String botId) {
        return msg.contains("<@" + botId + ">");
    }

    public String handle(String msg){
        return msg.replace("@starbot ", "");
    }
}
