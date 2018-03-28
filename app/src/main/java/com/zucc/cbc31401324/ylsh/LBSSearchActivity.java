package com.zucc.cbc31401324.ylsh;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by chenbaichang on 2018/3/20.
 */

public class LBSSearchActivity {
    private static String mTAG = "LBSSearchActivity";
    // 百度云检索API URI
    private static final String SEARCH_URI_LOCAL = "http://api.map.baidu.com/geosearch/v3/local?";
    // 云检索公钥
    private static String ak = "GVXtC4g8x3p5jHtpn42Y0Nztag0KexEE";
    //每页显示数量
    private static String page_size = "50";
    private static String geotable_id = "185770";

    private static int retry = 1;
    private static boolean IsBusy = false;

    /**
     * 云检索访问
     *
     * @param filterParams
     *            访问参数.
     * @param handler
     *            数据回调Handler
     * @return
     */
    public static boolean request(final HashMap<String, String> filterParams,
                                  final Handler handler) {

        if (IsBusy || filterParams == null)
            return false;
        IsBusy = true;

        new Thread() {
            public void run() {
                int count = retry;
                while (count > 0) {
                    try {
                        String requestURL = "";
                        requestURL = SEARCH_URI_LOCAL;
                        requestURL = requestURL + "&" + "ak=" + ak
                                + "&geotable_id=" + geotable_id
                                + "&page_size=" + page_size;

                        Iterator iter = filterParams.entrySet().iterator();

                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            String key = entry.getKey().toString();
                            String value = entry.getValue().toString();

                            requestURL = requestURL + "&" + key + "=" + value;
                        }
                        Log.d(mTAG, "request url:" + requestURL);

                        URL requestUrl = new URL(requestURL);
                        HttpURLConnection connection = (HttpURLConnection) requestUrl
                                .openConnection();
                        // 建立实际的连接
                        connection.connect();

                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                        connection.getInputStream(), "utf-8"));

                        StringBuilder entityStringBuilder=new StringBuilder();
                        String result = "";
                        while ((result = reader.readLine()) != null) {
                            entityStringBuilder.append(result+"/n");
                            Log.d(mTAG, result);
                        }
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        msg.obj = entityStringBuilder.toString();
                        msg.sendToTarget();

                        reader.close();
                        connection.disconnect();

                    } catch (Exception e) {
                        Log.e(mTAG, "GET请求错误！");
                        e.printStackTrace();
                    }
                    count--;
                }
                IsBusy = false;
            }
        }.start();
        return true;
    }
}
