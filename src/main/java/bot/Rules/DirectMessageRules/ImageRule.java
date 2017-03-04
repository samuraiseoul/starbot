package bot.Rules.DirectMessageRules;

import bot.Helpers.GoogleImageHelper;

import java.io.IOException;
import java.util.Properties;

public class ImageRule extends DirectMessageRule {
    public ImageRule(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(String msg, String botId) {
        return super.canHandle(msg, botId) && msg.contains("image me");
    }

    @Override
    public String handle(String msg, String botId) {
        try {
            GoogleImageHelper googleImageHelper = new GoogleImageHelper(properties.getProperty("GOOGLE_SEARCH"), properties.getProperty("GOOGLE_KEY"));
            return googleImageHelper.search(super.handle(msg, botId).replace("image me", "").trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
