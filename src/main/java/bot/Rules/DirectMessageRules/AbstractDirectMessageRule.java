package bot.Rules.DirectMessageRules;

import bot.Rules.Rule;

public abstract class AbstractDirectMessageRule extends Rule {
    public boolean canHandle(final String msg, final String botId, final boolean isDirect) {
        return msg.contains("<@" + botId + ">") || isDirect;
    }

    public String handle(final String msg, final String botId){
        return msg.replace("<@" + botId + "> ", "");
    }
}
