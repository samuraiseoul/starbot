package bot.Rules.DirectMessageRules;

public class XkcdMessageRule extends DirectMessageRule {
    @Override
    public boolean canHandle(String msg, String botId) {
        return super.canHandle(msg, botId) && msg.toLowerCase().contains("xkcd");
    }

    @Override
    public String handle(String msg) {
        return "<insert xkcd comic here>";
    }
}
