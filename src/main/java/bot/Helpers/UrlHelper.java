package bot.Helpers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UrlHelper {
    public final static String HTTP = "http";
    public final static String HTTPS = "https";

    public static JsonElement getJson(final String host, final String uri, final int port, final String scheme, final List<Header> headers) throws IOException{
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost target = new HttpHost(host, port, scheme);
        HttpGet getRequest = new HttpGet(uri);
        for(Header header : headers){
            getRequest.addHeader(header);
        }
        HttpEntity entity = httpClient.execute(target, getRequest).getEntity();
        return new JsonParser().parse(EntityUtils.toString(entity));
    }

    public static JsonElement getJson(final String host, final String uri) throws IOException{
        return getJson(host, uri, 80, HTTPS, Collections.emptyList());
    }

    public static JsonElement getJson(final String host, final String uri, final int port, final String scheme) throws IOException{
        return getJson(host, uri, port, scheme, Collections.emptyList());
    }

    public static JsonElement getJson(final String host, final String uri, final int port, final List<Header> headers) throws IOException{
        return getJson(host, uri, port, HTTPS, headers);
    }

    public static JsonElement getJson(final String host, final String uri, final String scheme, final List<Header> headers) throws IOException{
        return getJson(host, uri, 80, scheme, headers);
    }

    public static JsonElement getJson(final String host, final String uri, final int port) throws IOException{
        return getJson(host, uri, port, HTTPS, Collections.emptyList());
    }

    public static JsonElement getJson(final String host, final String uri, final String scheme) throws IOException{
        return getJson(host, uri, 80, scheme, Collections.emptyList());
    }

    public static JsonElement getJson(final String host, final String uri, final List<Header> headers) throws IOException{
        return getJson(host, uri, 80, "https", headers);
    }
}
