package bot.Rules;

import java.util.Properties;

public abstract class Rule {
    private Properties properties;
    public Rule(Properties properties) {
        properties = properties;
    }

    public abstract boolean canHandle(String msg, String botId);

    public abstract String handle(String msg, String botId);
}
