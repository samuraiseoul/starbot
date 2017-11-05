package bot.Rules.DirectMessageRules;

import bot.Rules.Rule;
import com.ullink.slack.simpleslackapi.SlackSession;

public abstract class AbstractDirectMessageRule extends Rule {
    public boolean canHandle(final String msg, final String botId, final boolean isDirect) {
        return msg.contains("<@" + botId + ">") || isDirect;
    }

    public String handle(final String msg, final String botId, SlackSession session){
        return msg.replace("<@" + botId + "> ", "");
    }
}
