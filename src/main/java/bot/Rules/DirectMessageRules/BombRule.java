package bot.Rules.DirectMessageRules;

import bot.Helpers.GoogleImageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BombRule extends DirectMessageRule {

    private final String bombs;

    private final GoogleImageHelper googleImageHelper;

    @Autowired
    public BombRule(@Value("${BOMBS}") final String bombs, GoogleImageHelper googleImageHelper) {
        this.bombs = bombs;
        this.googleImageHelper = googleImageHelper;
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        for(final String bomb: this.bombs.split(",")){
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
            for(final String bomb: this.bombs.split(",")){
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
            return this.googleImageHelper.search(bombType, number);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
