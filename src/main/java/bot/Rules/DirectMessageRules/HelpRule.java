package bot.Rules.DirectMessageRules;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Component
public class HelpRule extends AbstractDirectMessageRule {

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && msg.replace("<@" + botId + ">", "").toLowerCase().equals(" help");
    }

    @Override
    public String handle(final String msg, final String botId) {
        try {
            return "```" + new Scanner(new File("README.md")).useDelimiter("\\Z").next() + "```";
        } catch (IOException e) {
            return "Unable to display help. Please contact my creator.";
        }
    }
}
