package bot.Rules.MessageRules;

import bot.Rules.Rule;

import java.util.Properties;

public abstract class MessageRule extends Rule{
    MessageRule(Properties properties) {
        super(properties);
    }

    public boolean canHandle(String msg, String botId) {
        return !msg.contains("<@" + botId + ">");
    }
}
