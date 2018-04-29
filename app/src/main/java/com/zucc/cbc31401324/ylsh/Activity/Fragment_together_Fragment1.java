package com.zucc.cbc31401324.ylsh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.http.HttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.cbc31401324.ylsh.Adapter.FishTogetherAdapter;
import com.zucc.cbc31401324.ylsh.Bin.CheckFishTogether;
import com.zucc.cbc31401324.ylsh.Bin.FishTogether;
import com.zucc.cbc31401324.ylsh.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/22.
 */

public class Fragment_together_Fragment1 extends Fragment {
    private List<FishTogether> publicfishtogether = new ArrayList<FishTogether>();
    private List<CheckFishTogether> checkFishTogethers = new ArrayList<CheckFishTogether>();
    private static final int LOGIN_RESULT = 1;
    private CheckFishTogether cft = new CheckFishTogether();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_RESULT:
                    parseJASONWithGASON((String) msg.obj);
                    if(publicfishtogether.isEmpty()){
                        StorageFT();
                    }else {
                        Log.d("FTF", "handleMessage: ");
                    }
                     //TODO 更新UI
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publicfishtogether_listview, container, false);
        init();
        FishTogetherAdapter adapter = new FishTogetherAdapter(getActivity(),R.layout.myfishtogether,publicfishtogether);
        ListView listView = (ListView)view.findViewById(R.id.list_view1);
        listView.setAdapter(adapter);
        return view;

    }
    private void init() {
        //TODO 约钓 最新发布
        // 我—>我的消息—>我发起的
        //发送消息，让主线程刷新ui显示text
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

//        FishTogether site = new FishTogether(R.drawable.pic, getSrc();
//                "cya", //ft.getUserName()
//                "1分钟前",// ft.getAddTime();
//                "100m", //
//                "有没有人一起去钓鱼啊？", //ft.getTitle
//                "2017-11-4 星期六", // ft.getBeginTime()
//                "浙江大学城市学院内河"); // ft.getAddress
//        publicfishtogether.add(site);
//        FishTogether site1 = new FishTogether(R.drawable.pic,
//                "cbc",
//                "10分钟前",
//                "1000m",
//                "一起去钓鱼啊？",
//                "2017-12-14 星期六",
//                "浙江大学城市学院");
//        publicfishtogether.add(site1);
//        FishTogether site2 = new FishTogether(R.drawable.pic,
//                "cbc",
//                "10分钟前",
//                "1000m",
//                "一起去钓鱼啊？",
//                "2017-12-14 星期六",
//                "浙江大学城市学院");
//        publicfishtogether.add(site2);
//        FishTogether site3 = new FishTogether(R.drawable.pic,
//                "cbc",
//                "10分钟前",
//                "1000m",
//                "一起去钓鱼啊？",
//                "2017-12-14 星期六",
//                "浙江大学城市学院");
//        publicfishtogether.add(site3);

    }

    private void parseJASONWithGASON(String text){
        Gson gson = new Gson();
        List<CheckFishTogether> checkFishTogethers = gson.fromJson(text,new TypeToken<List<CheckFishTogether>>(){}.getType());
        this.checkFishTogethers.addAll(checkFishTogethers);
    }
    private void StorageFT(){
        for(int i=0;i<checkFishTogethers.size();i++){
            FishTogether site = new FishTogether(cft.getFishtogether_pic(),
            cft.getMyfishtogether_name(),
            cft.getMyfishtogether_time(),
            cft.getMyfishtogether_distance(),
            cft.getMyfishtogether_info(),
            cft.getMyfishtogether_calendar(),
            cft.getMyfishtogether_address());
            publicfishtogether.add(site);
        }
    }
}
