package bot.Rules.DirectMessageRules;

import bot.Helpers.ImgurHelper;

import java.io.IOException;
import java.util.Properties;

public class PugRule extends DirectMessageRule {
    public PugRule(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(String msg, String botId) {
        return super.canHandle(msg, botId) && msg.contains("pug bomb");
    }

    @Override
    public String handle(String msg, String botId) {
        try {
            int number = 0;
            try {
                number = Integer.parseInt(super.handle(msg, botId).replace("pug bomb", "").trim());
            } catch(NumberFormatException e){
                number = 1;
            }
            ImgurHelper imgur = new ImgurHelper(properties.getProperty("IMGUR_ID"));
            return imgur.search("pug", number);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
