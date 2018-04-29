package com.zucc.cbc31401324.ylsh.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.cbc31401324.ylsh.Adapter.FishSiteAdapter;
import com.zucc.cbc31401324.ylsh.Bin.FishSite;
import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/7.
 */

public class MyFishSiteActivity extends Activity implements
        android.view.View.OnClickListener {

    private List<FishSite> fishsite = new ArrayList<FishSite>();
    private static final int LOGIN_RESULT = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_RESULT:
                    //TODO 更新UI
                    parseJASONWithGASON((String) msg.obj);
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myfishsite_listview);
        Button btn1=(Button)findViewById(R.id.button3);
        btn1.setOnClickListener(this);
        init();
        FishSiteAdapter adapter = new FishSiteAdapter(MyFishSiteActivity.this,R.layout.myfishsite,fishsite);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        //TODO 我的钓点
    }

    private void init() {
        LoginResult loginResult = new LoginResult();
        final String userId = loginResult.getUserId();
        //TODO 我的钓点 2.2.8
        new Thread(){
            @Override
            public void run() {
                String path = "";
                //1.创建客户端对象
                org.apache.http.client.HttpClient hc = new DefaultHttpClient();
                //2.创建post请求对象
                HttpPost hp = new HttpPost(path);
                //封装form表单提交的数据
                BasicNameValuePair bnvp = new BasicNameValuePair("name", userId);
                List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                parameters.add(bnvp);
                try {
                    //要提交的数据都已经在集合中了，把集合传给实体对象
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
                    //设置post请求对象的实体，其实就是把要提交的数据封装至post请求的输出流中
                    hp.setEntity(entity);
                    //3.使用客户端发送post请求
                    HttpResponse hr = hc.execute(hp);
                    if (hr.getStatusLine().getStatusCode() == 200) {
                        InputStream is = hr.getEntity().getContent();
                        String text = Utils.getTextFromStream(is);
                        Message message = new Message();
                        message.what = LOGIN_RESULT;
                        message.obj = text;
                        handler.sendMessage(message);
                        //发送消息，让主线程刷新ui显示text
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
//        FishSite site = new FishSite("浙江大学城市学院","浙江大学城市学院内河","100m");
//        FishSite site1 = new FishSite("浙江大学城市学院","浙江大学城市学院内河","100m");
//        fishsite.add(site);
//        fishsite.add(site1);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button3:
                finish();
                break;
            default:break;

        }
    }

    private void parseJASONWithGASON(String text){
        Gson gson = new Gson();
        List<FishSite> fishsite = gson.fromJson(text,new TypeToken<List<FishSite>>(){}.getType());
        this.fishsite.addAll(fishsite);
    }
}
