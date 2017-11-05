package bot.Rules;

import com.ullink.slack.simpleslackapi.SlackSession;

public abstract class Rule {
    public abstract boolean canHandle(final String msg, final String botId, final boolean isDirect);

    public abstract String handle(final String msg, final String botId, SlackSession session);

    protected boolean wordMatch(final String msg, final String word){
        return msg.toLowerCase().matches(".*\\s?" + word + "[\\s?!.].*|.*\\s?" + word + "$");
    }
}
