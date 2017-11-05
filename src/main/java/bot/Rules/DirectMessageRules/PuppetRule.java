package bot.Rules.DirectMessageRules;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class PuppetRule extends AbstractDirectMessageRule {
    @Override
    public boolean canHandle(String msg, String botId, boolean isDirect) {
        return isDirect && msg.toLowerCase().startsWith("puppet ");
    }

    @Override
    public String handle(String msg, String botId, SlackSession session) {
        final List<String> tokens = new LinkedList<>(Arrays.asList(msg.split(" ")));
        tokens.remove(0);
        final String channel = tokens.remove(0);
        SlackChannel slackChannel = session.findChannelByName(channel);
        if(slackChannel == null) { return "Channel " + channel + " doesn't exist!"; }
        final StringBuilder messageBuilder = new StringBuilder();
        for (String token: tokens) {
            messageBuilder.append(token).append(" ");
        }
        session.sendMessage(slackChannel, messageBuilder.toString());
        return "Message puppeted to: " + channel;
    }
}
