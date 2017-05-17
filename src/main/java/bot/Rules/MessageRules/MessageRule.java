package bot.Rules.MessageRules;

import bot.Rules.Rule;

abstract class MessageRule extends Rule{
    public boolean canHandle(final String msg, final String botId) {
        return !msg.contains("<@" + botId + ">");
    }
}
