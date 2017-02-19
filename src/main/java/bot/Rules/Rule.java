package bot.Rules;

import java.util.Properties;

public abstract class Rule {
    protected Properties properties;
    public Rule(Properties properties) {
        this.properties = properties;
    }

    public abstract boolean canHandle(String msg, String botId);

    public abstract String handle(String msg, String botId);
}
