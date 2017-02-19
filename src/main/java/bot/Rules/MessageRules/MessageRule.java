package bot.Rules.MessageRules;

import bot.Rules.Rule;

public abstract class MessageRule implements Rule{
    public boolean canHandle(String msg, String botId) {
        return !msg.contains("<@" + botId + ">");
    }
}
