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

    public XkcdMessageRule(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canHandle(String msg, String botId) {
        return super.canHandle(msg, botId) && msg.toLowerCase().contains("xkcd");
    }

    @Override
    public String handle(String msg, String botId) {
        try {
            msg = super.handle(msg, botId).replace("xkcd", "").trim();
            if(msg.equals("")){
                return this.getMostRecentXkcd();
            }
            if(msg.equals("random")){
                return this.getRandomXkcd();
            }
            return "Invalid xkcd format!";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JsonObject getXkcdJson(String uri) throws IOException {
        JsonElement jsonObj = UrlHelper.getJson("xkcd.com", uri, UrlHelper.HTTP);
        return jsonObj.getAsJsonObject();
    }

    private String getRandomXkcd() throws IOException{
        int max = this.getXkcdJson(XKCD_URI).get("num").getAsInt();
        Random random = new Random();
        int comic = random.nextInt(max) + 1;
        JsonObject xkcdObject = this.getXkcdJson("/" + comic + XKCD_URI).getAsJsonObject();
        return xkcdObject.get("alt") + " " + xkcdObject.get("img").getAsString();
    }

    private String getMostRecentXkcd() throws IOException {
        JsonObject xkcdObject = this.getXkcdJson(XKCD_URI).getAsJsonObject();
        return xkcdObject.get("alt") + " " + xkcdObject.get("img").getAsString();
    }
}
