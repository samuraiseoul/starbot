package bot.Rules.DirectMessageRules;

import bot.Helpers.UrlHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class XkcdMessageRule extends DirectMessageRule {
    private static final String XKCD_URI = "/info.0.json";

    public XkcdMessageRule(final Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(final String msg, final String botId) {
        return super.canHandle(msg, botId) && msg.toLowerCase().contains("xkcd");
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
        final JsonElement jsonObj = UrlHelper.getJson("xkcd.com", uri, UrlHelper.HTTP);
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
