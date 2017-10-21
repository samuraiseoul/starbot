package bot.Rules.DirectMessageRules;

import bot.Helpers.UrlHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class XkcdMessageRule extends AbstractDirectMessageRule {
    private static final String XKCD_URI = "/info.0.json";

    private final UrlHelper urlHelper;

    @Autowired
    public XkcdMessageRule(final UrlHelper urlHelper) {
        this.urlHelper = urlHelper;
    }

    @Override
    public boolean canHandle(final String msg, final String botId, final boolean isDirect) {
        return super.canHandle(msg, botId, isDirect) && msg.toLowerCase().contains("xkcd");
    }

    @Override
    public String handle(final String msg, final String botId) {
        try {
            final String message = super.handle(msg, botId).replace("xkcd", "").trim();
            if(message.equals("")){
                return this.getMostRecentXkcd();
            }
            if(message.equals("random")){
                return this.getRandomXkcd();
            }
            return "Invalid xkcd format!";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JsonObject getXkcdJson(final String uri) throws IOException {
        final JsonElement jsonObj = this.urlHelper.getJson("xkcd.com", uri, UrlHelper.HTTP);
        return jsonObj.getAsJsonObject();
    }

    private String getRandomXkcd() throws IOException{
        final int max = this.getXkcdJson(XKCD_URI).get("num").getAsInt();
        final Random random = new Random();
        final int comic = random.nextInt(max) + 1;
        final JsonObject xkcdObject = this.getXkcdJson("/" + comic + XKCD_URI).getAsJsonObject();
        return xkcdObject.get("alt") + " " + xkcdObject.get("img").getAsString();
    }

    private String getMostRecentXkcd() throws IOException {
        final JsonObject xkcdObject = this.getXkcdJson(XKCD_URI).getAsJsonObject();
        return xkcdObject.get("alt") + " " + xkcdObject.get("img").getAsString();
    }
}
