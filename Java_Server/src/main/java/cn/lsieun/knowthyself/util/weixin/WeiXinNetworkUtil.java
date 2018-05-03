package cn.lsieun.knowthyself.util.weixin;

import cn.lsieun.knowthyself.dto.WeiXinDTO;
import cn.lsieun.knowthyself.util.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WeiXinNetworkUtil {
    private static final String appid = ""; //小程序唯一标识
    private static final String secret = ""; //小程序的 app secret
    private static final String js_code = "";	//登录时获取的 code
    private static final String grant_type = "authorization_code"; //填写为 authorization_code
    private static final String USER_AGENT = "Mozilla/5.0";

    public static String getWeiXinUserInfo(String appid,String appSecret, String js_code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appSecret + "&js_code=" + js_code + "&grant_type=authorization_code";
        String content = null;
        try {
            content = getContent(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static WeiXinDTO str2DTO(String content){
        if(StringUtils.isBlank(content)) return null;
        WeiXinDTO dto = JSONUtil.getObjectFromJson(content, WeiXinDTO.class);
        return dto;
    }

    public static String getContent(String url) throws IOException {
        //1、创建HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        System.out.println("创建HttpClient...");

        //2、创建HttpRequest请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", USER_AGENT);
        System.out.println("创建请求...");

        //3、接收返回结果HttpResponse
        System.out.println("发送请求...");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println("返回结果成功...");

        //4、对结果进行处理
        System.out.println("处理结果...");
        //(4-1)处理StatusLine
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        ProtocolVersion protocolVersion = statusLine.getProtocolVersion();
        String reasonPhrase = statusLine.getReasonPhrase();
        System.out.println("GET Response Status: " + statusCode);
        System.out.println("GET Protocol Version: " + protocolVersion.toString());
        System.out.println("GET Protocol Version: " + reasonPhrase);

        //(4-2)处理Header
        HeaderIterator headerIterator = response.headerIterator();
        while(headerIterator.hasNext()){
            Header header = headerIterator.nextHeader();
            String headerName = header.getName();
            String headerValue = header.getValue();
            System.out.println(headerName + ": " + headerValue);
        }

        //(4-3)处理HttpEntity
        HttpEntity entity = response.getEntity();
        StringBuffer sb = new StringBuffer();
        if(entity != null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                //sb.append(inputLine + "\r\n");
                sb.append(inputLine);
            }
            reader.close();
            System.out.println(sb.toString());
        }
        else{
            System.out.println("获取不到内容");
        }

        //5、关闭资源连接
        System.out.println("关闭HttpClient连接...");
        response.close();
        httpClient.close();
        System.out.println("执行结束...");
        return sb.toString();
    }
}
