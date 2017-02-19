package bot.Rules.DirectMessageRules;

import bot.Helpers.ImgurHelper;

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
            ImgurHelper imgur = new ImgurHelper(properties.getProperty("IMGUR_ID"));
            return imgur.search(super.handle(msg, botId).replace("image me", "").trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
