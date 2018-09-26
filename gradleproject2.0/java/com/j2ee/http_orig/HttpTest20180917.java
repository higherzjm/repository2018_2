package com.j2ee.http_orig;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zjm on 2018/9/17.
 * http原始
 */
@Controller
@RequestMapping("/httptest")
public class HttpTest20180917 {

    //get客户端
    @RequestMapping("/get")
    @ResponseBody
    public String httpGet_client(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //创建默认的httpClient实例
        HttpEntity entity=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StatusLine StatusLine=null;
        String entityStr=null;
        try {
            //用get方法发送http请求
            HttpGet httpGet = new HttpGet("http://localhost:8080/repository2018_2/httptest/httpGet_server.do?token=1314520");
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(20000).setConnectTimeout(80000).build();
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
        System.out.println("entityStr:"+entityStr);//打印出来 "{\"dataTime\":\"2018-09-20 15:09;20\",\"msg\":\"success\"}"
        entityStr=entityStr.substring(1,entityStr.length()-1);//去掉前后的双引号
        System.out.println("entityStr2:"+entityStr);//打印出来 {\"dataTime\":\"2018-09-20 15:09;20\",\"msg\":\"success\"}
        entityStr=entityStr.replace("\\", "");//去掉\符合
        System.out.println("entityStr3:"+entityStr);//打印出来 :{"dataTime":"2018-09-20 15:09;20","msg":"success"}
        try {
            JSONObject jsonObject=JSONObject.fromObject(entityStr);
            System.out.println("dataTime:"+jsonObject.get("dataTime"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return   entityStr;
    }


    //post客户端
    @RequestMapping(value = "post")
    @ResponseBody
    public String httpPost_client(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer jsonString = new StringBuffer();
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();
            //httpclient=createSSLClientDefault();
            String url="http://localhost:8080/repository2018_2/httptest/httpPost_server.do";
            HttpPost httpPost = new HttpPost();
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            httpPost.setHeader("Api-key", "201809201609abctf");
            httpPost.setURI(new URI(url));
            RequestConfig reuqestConfig = RequestConfig.custom()
                    .setSocketTimeout(30000)
                    .setConnectTimeout(80000).build();
            httpPost.setConfig(reuqestConfig);
            JSONObject sendata = new JSONObject();
            sendata.accumulate("name","张三");
            sendata.accumulate("age",18);
            StringEntity entity = new StringEntity(sendata.toString(),"UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(httpResponse.getEntity().getContent(),"UTF-8"));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                jsonString.append(line);
            }
            System.out.println("服务端返回数据:"+jsonString);//返回结果"{\"name\":\"张三\",\"age\":18}"

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonString.toString();
    }

    //get服务端
    @RequestMapping("httpGet_server")
    @ResponseBody
    public String httpGet_server(HttpServletRequest request,HttpServletResponse response){
        String token=request.getParameter("token");
        JSONObject retMsg=new JSONObject();
        retMsg.accumulate("dataTime",new SimpleDateFormat("YYYY-MM-dd HH:mm;ss").format(new Date()));
        if ("1314520".equals(token)){
            retMsg.accumulate("msg","success");
        }else {
            retMsg.accumulate("msg","failure");
        }
        return retMsg.toString();
    }
    //post服务端
    @RequestMapping(value = "httpPost_server",method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String httpPost_server(HttpServletRequest request,HttpServletResponse response){
         //获取请求投参数begin----------------
         String contentType=request.getHeader("Content-Type");
         String  apikey=request.getHeader("Api-key");
         System.out.println("contentType:"+contentType+";apikey:"+apikey);
        //获取请求投参数end---------------
         String receivedDatas="";
        try {
            receivedDatas=parseStream(request);//解析request数据包
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject=JSONObject.fromObject(receivedDatas);
        String name=jsonObject.getString("name");
        String age=jsonObject.getString("age");
        System.out.println(name+":"+age+" server received json  data:"+jsonObject.toString());
        return jsonObject.toString();
    }

    //设置信任所有链接
    public static CloseableHttpClient createSSLClientDefault() {

        System.setProperty ("jsse.enableSNIExtension", "false");
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, new TrustStrategy() {
                        // 信任所有
                        public boolean isTrusted(X509Certificate[] chain,
                                                 String authType) throws CertificateException {
                            return true;
                        }
                    }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);//信任所有连接
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    //解析request请求参数
    public String parseStream(HttpServletRequest httpServletRequest)
            throws UnsupportedEncodingException {
        httpServletRequest.setCharacterEncoding("UTF-8");
        BufferedReader buff = null;
        StringBuffer str = new StringBuffer();
        try {
            String s = null;
            buff = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
            while ((s = buff.readLine()) != null)
                str.append(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str.toString();
    }
}
