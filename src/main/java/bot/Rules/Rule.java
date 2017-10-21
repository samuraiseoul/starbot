package bot.Rules;

public abstract class Rule {
    public abstract boolean canHandle(final String msg, final String botId, final boolean isDirect);

    public abstract String handle(final String msg, final String botId);

    protected boolean wordMatch(final String msg, final String word){
        return msg.toLowerCase().matches(".*\\s?" + word + "[\\s?!.].*|.*\\s?" + word + "$");
    }
}
