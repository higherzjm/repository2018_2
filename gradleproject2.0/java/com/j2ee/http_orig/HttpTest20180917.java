package com.j2ee.http_orig;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

/**
 * Created by zjm on 2018/9/17.
 */
@RequestMapping("/httptest")
public class HttpTest20180917 {

    @RequestMapping("/get")
    @ResponseBody
    public String getdatas(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //创建默认的httpClient实例
        HttpEntity entity=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StatusLine StatusLine=null;
        String entityStr=null;
        try {
            //用get方法发送http请求
            HttpGet httpGet = new HttpGet("http_orig://vipmerchant.paytos.com/CubeICardMerchantConsole/NewUI/dataProvider/v001_01.jsp?token=tuofu@123456");
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(7000).setConnectTimeout(8000).build();
            httpGet.setConfig(requestConfig);

            System.out.println("执行get请求:...."+httpGet.getURI());
            CloseableHttpResponse httpResponse = null;
            //发送get请求
            httpResponse = httpClient.execute(httpGet);
            try{
                //response实体
                entity = httpResponse.getEntity();
                if (null != entity){
                    StatusLine=httpResponse.getStatusLine();
                    System.out.println("响应状态码:"+ StatusLine);
                    entityStr= EntityUtils.toString(entity);
                    System.out.println("响应内容:" + entityStr);
                }
            }
            finally{
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try{
                httpClient.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return   JSONObject.fromObject(entityStr).toString();
    }


    @RequestMapping("post")
    @ResponseBody
    public String updateDelWithDrawalSu2m(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String retresult = null;
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();;
            String url="http_orig://new.17track.net/restapi/handlertrack.ashx";
            HttpPost httpPost = new HttpPost();

            httpPost .setHeader("Accept",      "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpPost .setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
            httpPost .setHeader("Accept-Encoding", "gzip, deflate");
            httpPost .setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpPost .setHeader("Connection", "keep-alive");
            httpPost .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");

            httpPost.setURI(new URI(url));
            RequestConfig reuqestConfig = RequestConfig.custom()
                    .setSocketTimeout(6000)
                    .setConnectTimeout(10000).build();

            httpPost.setConfig(reuqestConfig);

            JSONObject sendata = new JSONObject();
            sendata.accumulate("guid","");
            JSONObject data = new JSONObject();
            data.accumulate("num","LT189637536CN");
            JSONObject[] data2=new JSONObject [1];
            data2[0]=data;
            sendata.accumulate("data",data2);
            System.out.println("sendata.toString():"+sendata.toString());
            StringEntity entity = new StringEntity(sendata.toString(),"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);
            HttpEntity entity2=httpResponse.getEntity();
            System.out.println("statues:"+httpResponse.getStatusLine());
            retresult=EntityUtils.toString(entity2);

        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject jsonObject=JSONObject.fromObject(retresult);
        JSONArray jsonArray_dat= JSONArray.fromObject(jsonObject.get("dat"));
        JSONObject dat=JSONObject.fromObject(jsonArray_dat.get(0));
        JSONObject track=JSONObject.fromObject(dat.get("track"));
        JSONObject z0=JSONObject.fromObject((track.get("z0")));
        JSONArray jsonArray_z1=JSONArray.fromObject(track.get("z1"));
        JSONArray jsonArray_z2=JSONArray.fromObject(track.get("z2"));
        System.out.println("retresult:"+retresult);
        return  "z1:"+jsonArray_z1.toString()+"-------------z2:"+jsonArray_z2.toString()+"-----------------z0:"+z0;

    }



}
