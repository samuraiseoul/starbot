package bot.Rules.DirectMessageRules;

import java.util.Properties;

public class HelpRule extends DirectMessageRule {
    public HelpRule(final Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && msg.replace("<@" + botId + ">", "").toLowerCase().equals(" help");
    }

    @Override
    public String handle(final String msg, final String botId) {
        return "Visit https://github.com/samuraiseoul/starbot to see a list of things I can do!";
    }
}
