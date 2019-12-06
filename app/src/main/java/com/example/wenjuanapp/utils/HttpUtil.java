package com.example.wenjuanapp.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class HttpUtil {
    //实例话对象
    private static AsyncHttpClient client =new AsyncHttpClient();
    static {
        client.setTimeout(11000);   //设置链接超时，如果不设置，默认为10s
    }

    //用一个完整url获取一个string对象
    public static void get(String urlString,AsyncHttpResponseHandler res) {
        client.get(urlString, res);
    }
    //url里面带参数
    public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res)
    {
        client.get(urlString, params,res);
    }
    //不带参数，获取json对象或者数组()
    public static void get(String urlString,JsonHttpResponseHandler res)
    {
        client.get(urlString,res);
    }
    //带参数，获取json对象或者数组()
    public static void get(String urlString,RequestParams params,JsonHttpResponseHandler res)
    {
       // System.out.println("================================"+urlString);
        client.get(urlString, params,res);
    }
    //下载数据使用，会返回byte数据
    public static void get( String uString, BinaryHttpResponseHandler bHandler)
    {
        client.get(uString, bHandler);
    }
    public static AsyncHttpClient getClient()
    {
        return client;
    }
}