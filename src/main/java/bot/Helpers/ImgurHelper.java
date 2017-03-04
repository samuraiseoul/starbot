package bot.Helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class ImgurHelper {
    private String clientId;

    public ImgurHelper(String clientId) {
        this.clientId = clientId;
    }

    public String search(String query, int number) throws IOException {
        JsonObject jsonObj = UrlHelper.getJson(
                "api.imgur.com",
                "/3/gallery/search/?q=" + URLEncoder.encode(query, "UTF-8"),
                443,
                "https",
                Collections.singletonList(new BasicHeader("Authorization", "Client-ID " + clientId))
        ).getAsJsonObject();
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
