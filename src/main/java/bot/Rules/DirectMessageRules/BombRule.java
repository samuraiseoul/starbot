package bot.Rules.DirectMessageRules;

import bot.Helpers.GoogleImageHelper;

import java.io.IOException;
import java.util.Properties;

public class BombRule extends DirectMessageRule {
    public BombRule(final Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        for(final String bomb: properties.getProperty("BOMBS").split(",")){
            if(super.canHandle(msg, botId) && msg.contains(bomb + " bomb")){
                return true;
            }
        }
        return false;
    }

    @Override
    public String handle(final String msg, final String botId) {
        try {
            String bombType = null;
            for(final String bomb: properties.getProperty("BOMBS").split(",")){
                if(super.canHandle(msg, botId) && msg.contains(bomb + " bomb")){
                    bombType = bomb;
                    break;
                }
            }
            if(bombType == null || bombType.isEmpty()){
                throw new Error("Invalid Bomb Type");
            }
            int number;
            try {
                number = Integer.parseInt(super.handle(msg, botId).replace(bombType + " bomb", "").trim());
            } catch(NumberFormatException e){
                number = 1;
            }
            final GoogleImageHelper googleImageHelper = new GoogleImageHelper(properties.getProperty("GOOGLE_SEARCH"), properties.getProperty("GOOGLE_KEY"));
            return googleImageHelper.search(bombType, number);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
