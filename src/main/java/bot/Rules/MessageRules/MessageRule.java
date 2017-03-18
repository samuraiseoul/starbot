package bot.Rules.MessageRules;

import bot.Rules.Rule;

import java.util.Properties;

abstract class MessageRule extends Rule{
    MessageRule(final Properties properties) {
        super(properties);
    }

    public boolean canHandle(final String msg, final String botId) {
        return !msg.contains("<@" + botId + ">");
    }
}
