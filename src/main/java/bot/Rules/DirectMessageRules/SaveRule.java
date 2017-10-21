package bot.Rules.DirectMessageRules;

import bot.Helpers.UrlHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SaveRule extends AbstractDirectMessageRule {

    private final UrlHelper urlHelper;

    @Autowired
    public SaveRule(final UrlHelper urlHelper) {
        this.urlHelper = urlHelper;
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && msg.replace("<@" + botId + ">", "").toLowerCase().startsWith(" save ");
    }

    @Override
    public String handle(final String msg, final String botId) {
        try {
            final JsonElement element = this.urlHelper.getJson("www.ourmanna.com", "/verses/api/get/?format=text&order=random&format=json", UrlHelper.HTTP);
            final JsonObject response = element.getAsJsonObject();
            final JsonObject verse = response.get("verse").getAsJsonObject().get("details").getAsJsonObject();
            final String user = super.handle(msg, botId).replace("save ", "");
            return (!user.equals("me") ? user : "") + " ```" + verse.get("text").getAsString() + " - " + verse.get("reference").getAsString() + "```";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
