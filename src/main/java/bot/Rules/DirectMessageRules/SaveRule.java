package bot.Rules.DirectMessageRules;

import bot.Helpers.UrlHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Properties;

public class SaveRule extends DirectMessageRule{

    public SaveRule(final Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && msg.replace("<@" + botId + ">", "").toLowerCase().equals(" save me");
    }

    @Override
    public String handle(final String msg, final String botId) {
        try {
            final JsonElement element = UrlHelper.getJson("www.ourmanna.com", "/verses/api/get/?format=text&order=random&format=json", UrlHelper.HTTP);
            final JsonObject response = element.getAsJsonObject();
            final JsonObject verse = response.get("verse").getAsJsonObject().get("details").getAsJsonObject();
            return "```" + verse.get("text").getAsString() + " - " + verse.get("reference").getAsString() + "```";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
