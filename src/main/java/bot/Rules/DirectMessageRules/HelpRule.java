package bot.Rules.DirectMessageRules;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class HelpRule extends DirectMessageRule {
    public HelpRule(final Properties properties) {
        super(properties);
    }

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
