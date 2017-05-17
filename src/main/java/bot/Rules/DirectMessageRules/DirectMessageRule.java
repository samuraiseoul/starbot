package bot.Rules.DirectMessageRules;

import bot.Rules.Rule;

public abstract class DirectMessageRule extends Rule {
    public boolean canHandle(final String msg, final String botId) {
        return msg.contains("<@" + botId + ">");
    }

    public String handle(final String msg, final String botId){
        return msg.replace("<@" + botId + "> ", "");
    }
}
