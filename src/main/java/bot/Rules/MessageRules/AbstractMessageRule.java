package bot.Rules.MessageRules;

import bot.Rules.Rule;

abstract class AbstractMessageRule extends Rule{
    public boolean canHandle(final String msg, final String botId) {
        return !msg.contains("<@" + botId + ">");
    }
}
