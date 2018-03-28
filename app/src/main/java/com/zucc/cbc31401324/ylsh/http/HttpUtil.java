package com.zucc.cbc31401324.ylsh.http;

import com.google.gson.Gson;
import com.zucc.cbc31401324.ylsh.Activity.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/28.
 */

public class HttpUtil {
    public static String serverIP = "10.66.48.5";
    public static String serverPath = "http://" + serverIP + ":8080/StudyWorkIndexSystem/";

    public static JSONObject postMethod(List<BasicNameValuePair> parameters , String path){
        JSONObject result = null;
        try {
            HttpClient hc = new DefaultHttpClient();
            HttpPost hp = new HttpPost(serverPath+path);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
            hp.setEntity(entity);
            HttpResponse hr = hc.execute(hp);
            int statusCode = hr.getStatusLine().getStatusCode();
            System.out.println(Calendar.getInstance() +": statusCode = " + statusCode);
            if (statusCode == 200) {
                InputStream is = hr.getEntity().getContent();
                String text = Utils.getTextFromStream(is);
                System.out.println("result: "+text);
                result = new JSONObject(text);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
