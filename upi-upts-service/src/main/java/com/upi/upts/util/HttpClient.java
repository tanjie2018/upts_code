package com.upi.upts.util;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upi.upts.*;
import com.upi.upts.common.CoreException;
import com.upi.upts.common.UiisReplyCode;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClient {

	private static Logger logger = LoggerFactory.getLogger(HttpClient.class);
	private static String CONTENT_TYPE = "application/json";
    private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    private static int conTimeout = 2000;
    private static int socTimeout = 30000;
    
    // get无参请求
    public static String doGet(String uri) throws Exception {
        //创建一个httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个GET对象
        HttpGet get = new HttpGet(uri);
        HttpHost proxy = new HttpHost("172.19.66.204", 9080, "http");
        
        // 带定制超时时间的httpclient创建
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(socTimeout)
                .setConnectTimeout(conTimeout)
                .setConnectionRequestTimeout(conTimeout)
                .setProxy(proxy)
                .build();

        get.setHeader("User-Agent", USER_AGENT);
        get.setHeader("Connection", "close");
        get.setHeader("Content-Type", CONTENT_TYPE);
        
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
        
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
//        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");
//        System.out.println(string);
        //关闭httpclient
        response.close();
        httpClient.close();
        return string;
    }

    // get带参请求
    public void doGetWithParam(String uri) throws Exception {
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个uri对象
        URIBuilder uriBuilder = new URIBuilder(uri);
        uriBuilder.addParameter("query", "花千骨");
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");
        System.out.println(string);
        //关闭httpclient
        response.close();
        httpClient.close();
    }

    // post无参请求
    public void doPost(String uri) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建一个post对象
        HttpPost post = new HttpPost(uri);
        //执行post请求
        CloseableHttpResponse response = null;

        response = httpClient.execute(post);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println(Thread.currentThread().getName() + " => " + string);
        response.close();

        httpClient.close();
    }

    // post带参请求①
    public void doPostWithParam(String uri) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建一个post对象
        HttpPost post = new HttpPost(uri);
        //创建一个Entity。模拟一个表单
        List<NameValuePair> kvList = new ArrayList<>();
        kvList.add(new BasicNameValuePair("username", "张三"));
        kvList.add(new BasicNameValuePair("password", "123"));

        //包装成一个Entity对象
        StringEntity entity = new UrlEncodedFormEntity(kvList, "utf-8");
        //设置请求的内容
        post.setEntity(entity);

        //执行post请求
        CloseableHttpResponse response = httpClient.execute(post);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println(string);
        response.close();
        httpClient.close();
    }

    // post带参请求②
    public String doPostWithJson(String url, String jString) {
        boolean isSucc;
        String CONTENT_TYPE = "application/json";
        String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

        HttpPost post = null;
        int timeOut = 1000000;
        try {
        	
        	//设置代理IP、端口、协议（请分别替换）
            HttpHost proxy = new HttpHost("你的代理的IP", 8080, "http");
     
            // 带定制超时时间的httpclient创建
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(timeOut)
                    .setConnectTimeout(timeOut)
                    .setConnectionRequestTimeout(timeOut)
                    .setProxy(proxy)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();

//            CloseableHttpClient httpClient = HttpClients.createDefault();

            post = new HttpPost(url);
            // 构造消息头
            post.setHeader("User-Agent", USER_AGENT);
            post.setHeader("Content-Type", CONTENT_TYPE);

            // 构建消息实体
            StringEntity entity = new StringEntity(jString, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
//            entity.setContentType(CONTENT_TYPE);
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);

            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String string = EntityUtils.toString(response.getEntity());
                System.out.println(Thread.currentThread().getName() + " => " + string);

                int retCode = 0;
                String sessendId = "";
                // 返回码中包含retCode及会话Id
                for (Header header : response.getAllHeaders()) {
                    if (header.getName().equals("retcode")) {
                        retCode = Integer.parseInt(header.getValue());
                    }
                    if (header.getName().equals("SessionId")) {
                        sessendId = header.getValue();
                    }
                }
            }
            isSucc = true;
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            isSucc=false;
        } finally {
            if (post != null) {
                post.releaseConnection();
//                    Thread.sleep(500);
            }
        }
        return "";
    }
    
    // post带参请求,请求微信服务
    public static String doPost2WX(String url, String jString, HttpHost proxyHost) throws CoreException {

        HttpPost post = null;
        
        try {
            // 带定制超时时间的httpclient创建
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(socTimeout)
                    .setConnectTimeout(conTimeout)
                    .setConnectionRequestTimeout(socTimeout)
                    .setProxy(proxyHost)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();

            post = new HttpPost(url);
            // 构造消息头
            post.setHeader("User-Agent", USER_AGENT);
//            post.setHeader("Connection", "Keep-Alive");
            post.setHeader("Connection", "close");
            post.setHeader("Content-Type", CONTENT_TYPE);

            // 构建消息实体
            StringEntity entity = new StringEntity(jString, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            post.setEntity(entity);
            
            HttpResponse response = httpClient.execute(post);

            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String string = EntityUtils.toString(response.getEntity());
                logger.error("WX响应状态异常："+Thread.currentThread().getName() + " => " + string);

                int retCode = 0;
                String sessendId = "";
                // 返回码中包含retCode及会话Id
                for (Header header : response.getAllHeaders()) {
                    if (header.getName().equals("retcode")) {
                        retCode = Integer.parseInt(header.getValue());
                    }
                    if (header.getName().equals("SessionId")) {
                        sessendId = header.getValue();
                    }
                }
                logger.error("statusCode:"+statusCode+",retCode:"+retCode+",sessendId:"+sessendId);
                throw new CoreException(UiisReplyCode.SYSTEMERROR,"WPHK："+UiisReplyCode.CODE_MAP.get(UiisReplyCode.HTTPSTATEERROR));
            }
            return EntityUtils.toString(response.getEntity());
        } catch (CoreException e) {
        	logger.error("HttpStateError");
			throw e;
		}catch (Exception e) {
            logger.error("HTTP Get Error:{}",e);
            String message = e.getMessage();
            if(message.contains("SocketTimeoutException")) {//非SocketTimeoutException异常就认为是ConnectTimeoutException
            	logger.error("WPHK响应超时");
            	logger.error("WPHKSocketTimeoutException");
            	throw new CoreException(UiisReplyCode.SYSTEMERROR,"WPHK"+UiisReplyCode.CODE_MAP.get(UiisReplyCode.WPHKATSOCEX));
            }else {
            	logger.error("WPHK线路异常");
            	logger.error("WPHKConnectionTimeoutException");
            	throw new CoreException(UiisReplyCode.SYSTEMERROR,"WPHK"+UiisReplyCode.CODE_MAP.get(UiisReplyCode.WPHKCONEX));
            }
        }  finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
        
    }
    
 // post带参请求,请求微信服务
    public static String doPost2AT(String url, Map headerMap, String jString, HttpHost proxyHost) throws CoreException {

        HttpPost post = null;
        
        try {
            // 带定制超时时间的httpclient创建
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(socTimeout)
                    .setConnectTimeout(conTimeout)
                    .setConnectionRequestTimeout(socTimeout)
                    .setProxy(proxyHost)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();

            post = new HttpPost(url);
            // 构造消息头
            post.setHeader("User-Agent", USER_AGENT);
            post.setHeader("Content-Type", CONTENT_TYPE);
//            post.setHeader("Connection", "Keep-Alive");
            post.setHeader("Connection", "close");
            
            // 构建消息实体
            StringEntity entity = new StringEntity(jString, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            post.setEntity(entity);
            
            HttpResponse response = httpClient.execute(post);

            return EntityUtils.toString(response.getEntity());
		}catch (Exception e) {
            logger.error("HTTP Get Error:{}",e);
            String message = e.getMessage();
            if(message.contains("SocketTimeoutException")) {//非SocketTimeoutException异常就认为是ConnectTimeoutException
            	logger.error("AT响应超时");
            	logger.error("ATSocketTimeoutException");
            	throw new CoreException(UiisReplyCode.ATSOCEX,"AT"+UiisReplyCode.CODE_MAP.get(UiisReplyCode.ATSOCEX));
            }else {
            	logger.error("AT线路异常");
            	logger.error("ATConnectionTimeoutException");
            	throw new CoreException(UiisReplyCode.ATCONEX,"AT"+UiisReplyCode.CODE_MAP.get(UiisReplyCode.ATCONEX));
            }
        }  finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
        
    }

}
