package bot.Rules.MessageRules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageRule extends AbstractMessageRule {
    private final JsonArray rules;

    public MessageRule(@Value("${RULES}") final String rules) {
        this.rules = new JsonParser().parse(rules).getAsJsonArray();
    }

    @Override
    public boolean canHandle(String msg, String botId) {
        if(!super.canHandle(msg, botId)){
            return false;
        }
        for(final JsonElement rule : this.rules){
            if(this.wordMatch(msg, rule.getAsJsonObject().get("word").getAsString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String handle(String msg, String botId) {
        for(final JsonElement rule : this.rules){
            if(this.wordMatch(msg, rule.getAsJsonObject().get("word").getAsString())) {
                return rule.getAsJsonObject().get("gif").getAsString();
            }
        }
        return "Gif not found";
    }
}
