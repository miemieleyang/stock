import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import stockSpider.Http;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) throws IOException {
        Http http = new Http();
        http.getJsonContent("https://xueqiu.com/service/v5/stock/screener/quote/list?page=1&size=30&order=desc&orderby=percent&order_by=percent&market=CN&type=sh_sz&_=1588639128007");
    }


}
