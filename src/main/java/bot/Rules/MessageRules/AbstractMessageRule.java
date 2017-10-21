package bot.Rules.MessageRules;

import bot.Rules.Rule;

abstract class AbstractMessageRule extends Rule{
    public boolean canHandle(final String msg, final String botId, final boolean isDirect) {
        return !msg.contains("<@" + botId + ">");
    }
}
