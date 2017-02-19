package bot.Rules.DirectMessageRules;

import bot.Rules.Rule;

import java.util.Properties;

public abstract class DirectMessageRule extends Rule {
    DirectMessageRule(Properties properties) {
        super(properties);
    }

    public boolean canHandle(String msg, String botId) {
        return msg.contains("<@" + botId + ">");
    }

    public String handle(String msg, String botId){
        return msg.replace("<@" + botId + "> ", "");
    }
}
