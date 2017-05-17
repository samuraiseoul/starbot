package bot.Rules;

public abstract class Rule {
    public abstract boolean canHandle(final String msg, final String botId);

    public abstract String handle(final String msg, final String botId);
}
