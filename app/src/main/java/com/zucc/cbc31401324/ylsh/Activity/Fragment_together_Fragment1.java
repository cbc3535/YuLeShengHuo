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
import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
        LoginResult loginResult = new LoginResult();
        final String userId = loginResult.getUserId();
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

//        FishTogether site = new FishTogether(R.drawable.pic, getSrc();
//                "cya", //ft.getUserName()
//                "1分钟前",// ft.getAddTime();
//                "100m", //
//                "有没有人一起去钓鱼啊？", //ft.getTitle
//                "2017-11-4 星期六", // ft.getBeginTime()
//                "浙江大学城市学院内河"); // ft.getAddress
//        publicfishtogether.add(site);
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
