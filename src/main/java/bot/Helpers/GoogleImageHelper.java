package bot.Helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class GoogleImageHelper {
    private static final int START_MIN = 1;
    private static final int START_MAX = 100;
    private static final int MAX_YEARS_PAST = 10;

    private String searchKey;
    private String googleKey;

    public GoogleImageHelper(String searchKey, String googleKey) {
        this.searchKey = searchKey;
        this.googleKey = googleKey;
    }

    public String search(String query, int number) throws IOException {
        JsonObject jsonObj = UrlHelper.getJson("www.googleapis.com", uriBuilder(query, number), 443, UrlHelper.HTTPS).getAsJsonObject();
        JsonArray items = jsonObj.get("items").getAsJsonArray();
        if(items.size() == 0){return null;}
        Set<String> images = new HashSet<>();
        for(JsonElement item : items){
            images.add(item.getAsJsonObject().get("link").getAsString());
        }
        return String.join(" ", images);
    }

    public String search(String query) throws IOException {
        return this.search(query, 1);
    }

    private String uriBuilder(final String query, final int number) throws UnsupportedEncodingException {
        return "/customsearch/v1?q=" + URLEncoder.encode(query, "UTF-8") +
                "&cx=" + searchKey +
                "&searchType=image" +
                "&key=" + googleKey +
                "&num=" + number +
                "&start=" + getStart(number) +
                "&fields=items/link" +
                "&sort=" + getDateRange();
    }

    private int getStart(final int number){
        final int max = START_MAX - number;
        final Random random = new Random();
        return random.nextInt(max) + START_MIN;
    }

    private String getDateRange(){
        final Random random = new Random();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        final int minYear = calendar.get(Calendar.YEAR) - MAX_YEARS_PAST;
        final int fromYear = minYear + random.nextInt(MAX_YEARS_PAST);
        final int toYear = fromYear + random.nextInt(calendar.get(Calendar.YEAR) - fromYear);
        return "date:r:" + fromYear + (random.nextInt(12) + 1) + "01:" + toYear + (random.nextInt(12) + 1) + "01";
    }
}
