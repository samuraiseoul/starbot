package bot.Rules.DirectMessageRules;

import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Component
public class HelpRule extends AbstractDirectMessageRule {

    @Override
    public boolean canHandle(final String msg, final String botId, final boolean isDirect) {
        return super.canHandle(msg, botId, isDirect) && msg.replace("<@" + botId + ">", "").toLowerCase().equals((isDirect ? "" : " ") + "help");
    }

    @Override
    public String handle(final String msg, final String botId, SlackSession session) {
        try {
            return "```" + new Scanner(new File("README.md")).useDelimiter("\\Z").next() + "```";
        } catch (IOException e) {
            return "Unable to display help. Please contact my creator.";
        }
    }
}
