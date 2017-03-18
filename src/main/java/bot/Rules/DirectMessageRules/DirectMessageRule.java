package bot.Rules.DirectMessageRules;

import bot.Rules.Rule;

import java.util.Properties;

public abstract class DirectMessageRule extends Rule {
    DirectMessageRule(final Properties properties) {
        super(properties);
    }

    public boolean canHandle(final String msg, final String botId) {
        return msg.contains("<@" + botId + ">");
    }

    public String handle(final String msg, final String botId){
        return msg.replace("<@" + botId + "> ", "");
    }
}
