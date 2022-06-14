package stockSpider;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.*;
import comstock.dao.StockDao;
import comstock.domain.Stock;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Http {
    public static void main(String[] args) throws IOException {
        //Json解析
        //int pageNum = 234;

        //页码
        for (int i =1; i <= 1; i ++) {
            String content = getJsonContent("https://xueqiu.com/service/v5/stock/screener/quote/list?page="+i+"&size=30&order=desc&orderby=percent&order_by=percent&market=CN&type=sh_sz&_=1588639128007");

            JSONObject jsonObject = JSONObject.parseObject(content);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray dataList1 = data.getJSONArray("list");
            System.out.println(dataList1.size());
            List<Stock> list = new ArrayList<>();
            //遍历所有股票数据
            for (int k=0;k< dataList1.size(); k++){
                //遍历单行股票数据
                for (int j =0; j< 20; j++){
                    Stock stock = new Stock();
                    stock.setStockCode(dataList1.getJSONObject(j).getString("symbol"));
                    stock.setStockName(dataList1.getJSONObject(j).getString("name"));
                    stock.setStockPrice(dataList1.getJSONObject(j).getDouble("current"));
                    stock.setStockMarketValue(dataList1.getJSONObject(j).getDouble("float_market_capital"));
                    list.add(stock);
                }
            }

            //mybatis配置
            ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");
            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
            SqlSession session = sqlSessionFactory.openSession(true);
            StockDao stockDao = session.getMapper(StockDao.class);

            stockDao.deleteAll();
            if (list.size()>0){
                for (int a = 0; a < list.size(); a++){
                    stockDao.addStock(list.get(a));
                }
            }else {
                break;
            }
        }
    }


    //连接访问
    public static String connect(String url){
        //生成HttpClient=打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        //创建get请求=输入网址
        HttpGet request = new HttpGet(url);
        //设置Head=伪装成浏览器
        request.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36");
        HttpHost proxy = new HttpHost("183.247.199.114", 30001);
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        request.setConfig(config);

        try{
            //执行请求=回车
            response = httpClient.execute(request);
            //if response code=200 continue
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity,"utf-8");
                System.out.println(html);
                return html;
            }else {
                System.out.println("can not open web");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return null;
    }


    public static String getJsonContent(String url) throws IOException, ParseException {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //方便的方法来构建一个URL并将其加载到当前的WebWindow中
        Page page = webClient.getPage(url);
        //返回最初用于创建此页的web响应
        WebResponse response = page.getWebResponse();
        //使用服务器响应中指定的字符集/编码，以字符串形式返回响应内容。
        String json = response.getContentAsString();
        System.out.println(json);
        return json;
    }

    public static String htmlUnitUrl(String url, WebClient webClient) {
        try {
            WebRequest request = new WebRequest(new URL(url), HttpMethod.GET);
            Map<String, String> additionalHeaders = new HashMap<String, String>();
            additionalHeaders.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36");
            additionalHeaders.put("Accept-Language", "zh-CN,zh;q=0.8");
            additionalHeaders.put("Accept", "*/*");
            request.setAdditionalHeaders(additionalHeaders);
            // 获取某网站页面
            Page page = webClient.getPage(request);
            return page.getWebResponse().getContentAsString();
        } catch (Exception e) {

        }
        return null;
    }
}

