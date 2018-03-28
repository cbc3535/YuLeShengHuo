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
import com.zucc.cbc31401324.ylsh.R;

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
        //TODO 我的钓点 2.2.8
        new Thread(){
            @Override
            public void run() {
                try {
                    String path = "";
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if(code == 200){

                        InputStream is = conn.getInputStream();
                        String text = Utils.getTextFromStream(is);
                        //TODO 数据请求成功 拿到信息 is如何处理
                        Message message = new Message();
                        message.what = LOGIN_RESULT;
                        message.obj = text;
                        handler.sendMessage(message);
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
