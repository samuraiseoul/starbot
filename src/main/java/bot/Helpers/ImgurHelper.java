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
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ImgurHelper {
    private String clientId;

    public ImgurHelper(String clientId) {
        this.clientId = clientId;
    }

    public String search(String query, int number) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost target = new HttpHost("api.imgur.com", 443, "https");
        HttpGet getRequest = new HttpGet("/3/gallery/search/?q=" + URLEncoder.encode(query, "UTF-8"));
        getRequest.addHeader(new BasicHeader("Authorization", "Client-ID " + clientId));
        HttpEntity entity = httpClient.execute(target, getRequest).getEntity();
        JsonObject jsonObj = new JsonParser().parse(EntityUtils.toString(entity)).getAsJsonObject();
        JsonArray data = jsonObj.get("data").getAsJsonArray();
        if(data.size() == 0){return null;}
        Random random = new Random();
        Set<String> images = new HashSet<>();
        while(images.size() < number){
            images.add("http://i.imgur.com/" + data.get(random.nextInt(data.size()) + 1).getAsJsonObject().get("cover").getAsString() + ".jpg");
        }
        return String.join(" ", images);
    }

    public String search(String query) throws IOException {
        return this.search(query, 1);
    }

}
