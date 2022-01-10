package com.jt;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpClientTest {
    /**
     * 要求：java代码内部，获取百度的页面
     * 实现步骤：
     *      1.确定目标地址：https://www.baidu.com/
     *      2.创建httpclient客户端对象
     *      3.创建请求类型
     *      4.发起http请求，并且获取响应的结果，之后判断状态码是否为200，
     * 如果等于200则请求正确。
     *      5.如果请求正确则动态获取响应值信息，之后进行数据的再次加工。
     */
    @Test
    public void httpClientTest() throws IOException {
        //确定目标地址
        String url="https://www.baidu.com/";
        //创建httpclient客户端对象
        HttpClient httpClient= HttpClients.createDefault();
        //创建请求类型
        HttpGet httpGet=new HttpGet(url);
        //发起http请求
        HttpResponse httpResponse=httpClient.execute(httpGet);
        //判断状态码是否为200
        if(httpResponse.getStatusLine().getStatusCode()==200){
            //表示用户请求正确。获取返回值数据
            HttpEntity httpEntity=httpResponse.getEntity();
            //将对象转换为字符串类型，并拼接UTF-8编码以防乱码
            String result= EntityUtils.toString(httpEntity,"UTF-8");
            System.out.println(result);
        }
    }

    //自己写一个
    @Test
    public void httpClientTest2() throws IOException {
        //确定目标地址——这个一天只能用一次
        String url="https://m.10010.com/mall-mobile/CheckMessage/captcha?phoneVal=18312674405&type=";
        for (int i = 1;i <= 25;i++){
            //创建httpclient客户端对象
            CloseableHttpClient httpClient= HttpClients.createDefault();
            /*循环测试*/
            //创建请求类型
            HttpGet httpGet=new HttpGet(url+i);
            //发起http请求
            HttpResponse httpResponse=httpClient.execute(httpGet);
            //判断状态码是否为200
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            System.out.println("响应内容为:" + EntityUtils.toString(httpResponse.getEntity()));
            httpClient.close();
        }
    }

    @Test
    public void httpClientTest3() throws IOException {
        //确定目标地址——这个一天只能用一次
        String url="https://user.daojia.com/mobile/getcode?mobile=18312674405";
        //创建httpclient客户端对象
        CloseableHttpClient httpClient= HttpClients.createDefault();
        //创建请求类型
        HttpGet httpGet=new HttpGet(url);
        //发起http请求
        HttpResponse httpResponse=httpClient.execute(httpGet);
        //判断状态码是否为200
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        HttpEntity entity = httpResponse.getEntity();
        System.out.println("响应内容为:" + EntityUtils.toString(entity));

        httpClient.close();
    }

    @Test
    public void httpClientTest4() throws IOException {//直接卡死
//        JSONObject json = new JSONObject();
//        json.put("mobile", "13660021070");
        //确定目标地址——这个一天只能用一次
        String url="https://www.hunterplus.net/api/code/send/mobile/13660021070";
        //创建httpclient客户端对象
        CloseableHttpClient httpClient= HttpClients.createDefault();
        /*循环测试*/
        //创建请求类型
        HttpPost httpPost=new HttpPost(url);
        // 创建参数队列
//        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
//        formparams.add(new BasicNameValuePair("mobile", "13660021070"));
//        UrlEncodedFormEntity useEntity;
//        useEntity = new UrlEncodedFormEntity(formparams,"UTF-8");
//        httpPost.setEntity(useEntity);
        System.out.println("executing request " + httpPost.getURI());
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println("--------------------------------------");
            System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
            System.out.println("--------------------------------------");
        }
        response.close();
        httpClient.close();

    }

    @Test
    public void httpClientTest5() throws IOException {//报500，不知道为什么
        JSONObject json = new JSONObject();
        json.put("mobile", "13660021070");
        System.out.println(json);
        Map<String, String> data = new HashMap<>();
        data.put("mobile", "13660021070");

        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : data.entrySet()){
            sb.append(entry.getKey()).append("=")
                    .append(entry.getValue()).append("\n");
        }

        String url = "https://www.hunterplus.net/api/code/send";
        //创建httpclient客户端对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求类型
        HttpPost httpPost=new HttpPost(url);


//        StringEntity entity = new StringEntity(json.toString(), "UTF-8");
        System.out.println(sb.toString());
        StringEntity entity = new StringEntity(sb.toString(),"UTF-8");
        httpPost.setEntity(entity);
        //得到response
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        System.out.println("--------------------------------------");
        System.out.println("Response content: " + EntityUtils.toString(responseEntity, "UTF-8"));
        System.out.println("--------------------------------------");

    }



}

