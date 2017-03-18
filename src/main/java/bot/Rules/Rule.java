package bot.Rules;

import java.util.Properties;

public abstract class Rule {
    protected final Properties properties;
    public Rule(final Properties properties) {
        this.properties = properties;
    }

    public abstract boolean canHandle(final String msg, final String botId);

    public abstract String handle(final String msg, final String botId);
}
