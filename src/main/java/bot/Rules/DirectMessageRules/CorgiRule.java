package bot.Rules.DirectMessageRules;

import bot.Helpers.GoogleImageHelper;

import java.io.IOException;
import java.util.Properties;

public class CorgiRule extends DirectMessageRule {
    public CorgiRule(final Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && msg.contains("corgi bomb");
    }

    @Override
    public String handle(final String msg, final String botId) {
        try {
            int number;
            try {
                number = Integer.parseInt(super.handle(msg, botId).replace("corgi bomb", "").trim());
            } catch(NumberFormatException e){
                number = 1;
            }
            GoogleImageHelper googleImageHelper = new GoogleImageHelper(properties.getProperty("GOOGLE_SEARCH"), properties.getProperty("GOOGLE_KEY"));
            return googleImageHelper.search("corgi", number);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}