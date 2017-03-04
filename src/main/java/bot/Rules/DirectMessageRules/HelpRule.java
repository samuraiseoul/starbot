package bot.Rules.DirectMessageRules;

import java.util.Properties;

public class HelpRule extends DirectMessageRule {
    public HelpRule(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(String msg, String botId) {
        return super.canHandle(msg, botId) && msg.replace("<@" + botId + ">", "").toLowerCase().equals(" help");
    }

    @Override
    public String handle(String msg, String botId) {
        return "Visit https://github.com/samuraiseoul/starbot to see a list of things I can do!";
    }
}
