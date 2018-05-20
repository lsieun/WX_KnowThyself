package cn.lsieun.knowthyself.util.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import com.google.gson.GsonBuilder;

public class HttpUtilTest {

    @Test
    public void testOfflineAddDrucker() throws Exception {
        String url = "http://www.lsieun.cn:8888/kt/drucker/add";
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("userid","446759033722892288");
        paramMap.put("title", "啦啦");
        paramMap.put("author", "哈哈");
        paramMap.put("url", "http://chuantu.biz/t6/315/1526777329x-1376440114.jpg");
        paramMap.put("desc", "一个人的事情，要自己做。");
        paramMap.put("pubTime", "2018-05-20 16:00:00");

        postJson(url,paramMap);
    }

    @Test
    public void testOnlineAddDrucker() throws Exception {
        String url = "https://www.lsieun.cn/kt/drucker/add";
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("userid","446759033722892288");
        paramMap.put("title", "啦啦");
        paramMap.put("author", "哈哈");
        paramMap.put("url", "http://chuantu.biz/t6/315/1526777329x-1376440114.jpg");
        paramMap.put("desc", "一个人的事情，要自己做。");
        paramMap.put("pubTime", "2018-05-20 16:00:00");

        postJson(url,paramMap);
    }

    public static void postJson(String url,Map<String,String> paramMap) throws Exception {
        String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        //post.setHeader("Accept", "application/json");

        String jsonString = new GsonBuilder().create().toJson(paramMap).toString();

        StringEntity entity = new StringEntity(jsonString, Charset.forName("UTF-8"));
        post.setEntity(entity);


        HttpResponse response = client.execute(post);
        System.out.println("Response Code : "  + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
    }
}
