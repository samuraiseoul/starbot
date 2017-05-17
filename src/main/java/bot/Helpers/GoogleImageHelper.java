package bot.Helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Component
public class GoogleImageHelper {
    private static final int START_MIN = 1;
    private static final int START_MAX = 100;
    private static final int MAX_YEARS_PAST = 10;


    private final String searchKey;

    private final String googleKey;

    private final UrlHelper urlHelper;

    @Autowired
    public GoogleImageHelper(@Value("${GOOGLE_SEARCH}") final String searchKey, @Value("${GOOGLE_KEY}") final String googleKey, final UrlHelper urlHelper) {
        this.searchKey = searchKey;
        this.googleKey = googleKey;
        this.urlHelper = urlHelper;
    }

    public String search(final String query, final int number) throws IOException {
        final JsonObject jsonObj = this.urlHelper.getJson("www.googleapis.com", uriBuilder(query, number), 443, UrlHelper.HTTPS).getAsJsonObject();
        final JsonArray items = jsonObj.get("items").getAsJsonArray();
        if(items.size() == 0){return null;}
        final Set<String> images = new HashSet<>();
        for(final JsonElement item : items){
            images.add(item.getAsJsonObject().get("link").getAsString());
        }
        return String.join(" ", images);
    }

    public String search(final String query) throws IOException {
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
        final int fromMonth = (random.nextInt(12) + 1);
        final int toMonth = (random.nextInt(12) + 1);
        return "date:r:" + (fromYear - 1) + (fromMonth  < 10 ? "0" + fromMonth : fromMonth ) + "01:" + toYear + (toMonth < 10 ? "0" + toMonth: toMonth) + "01";
    }
}
