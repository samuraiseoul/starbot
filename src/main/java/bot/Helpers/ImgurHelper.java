package bot.Helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Random;

public class ImgurHelper {
    private String clientId;

    public ImgurHelper(String clientId) {
        this.clientId = clientId;
    }

    public String search(String query) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost target = new HttpHost("api.imgur.com", 443, "https");
        HttpGet getRequest = new HttpGet("/3/gallery/search/?q=" + query);
        getRequest.addHeader(new BasicHeader("Authorization", "Client-ID " + clientId));
        HttpEntity entity = httpClient.execute(target, getRequest).getEntity();
        JsonObject jsonObj = new JsonParser().parse(EntityUtils.toString(entity)).getAsJsonObject();
        JsonArray data = jsonObj.get("data").getAsJsonArray();
        if(data.size() == 0){return null;}
        Random random = new Random();
        int image = random.nextInt(data.size()) + 1;
        return "http://i.imgur.com/" + data.get(image).getAsJsonObject().get("cover").getAsString() + ".jpg";
    }

}
