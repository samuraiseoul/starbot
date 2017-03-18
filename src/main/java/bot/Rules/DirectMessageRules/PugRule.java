package bot.Rules.DirectMessageRules;

import bot.Helpers.GoogleImageHelper;

import java.io.IOException;
import java.util.Properties;

public class PugRule extends DirectMessageRule {
    public PugRule(final Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && msg.contains("pug bomb");
    }

    @Override
    public String handle(final String msg, final String botId) {
        try {
            int number;
            try {
                number = Integer.parseInt(super.handle(msg, botId).replace("pug bomb", "").trim());
            } catch(NumberFormatException e){
                number = 1;
            }
            final GoogleImageHelper googleImageHelper = new GoogleImageHelper(properties.getProperty("GOOGLE_SEARCH"), properties.getProperty("GOOGLE_KEY"));
            return googleImageHelper.search("pug", number);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
