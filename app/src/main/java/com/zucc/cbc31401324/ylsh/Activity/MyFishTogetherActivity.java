package com.zucc.cbc31401324.ylsh.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.cbc31401324.ylsh.Adapter.FishTogetherAdapter;
import com.zucc.cbc31401324.ylsh.Bin.CheckFishTogether;
import com.zucc.cbc31401324.ylsh.Bin.FishTogether;
import com.zucc.cbc31401324.ylsh.Bin.FishTogetherCreateUser;
import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.R;
import com.zucc.cbc31401324.ylsh.http.HttpUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/8.
 */

public class MyFishTogetherActivity extends Activity implements
        android.view.View.OnClickListener {
    private FishTogetherAdapter adapter;
    private String error;
    private List<FishTogether> publicfishtogether = new ArrayList<FishTogether>();
    private List<CheckFishTogether> checkFishTogethers = new ArrayList<CheckFishTogether>();
    private static final int LOGIN_RESULT = 1;
    private CheckFishTogether cft = new CheckFishTogether();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_RESULT:
                    parseJASONWithGASON((String) msg.obj);
                    if (error.isEmpty()) {
                        StorageFT();
                    } else {
                        Log.d("FTF", "handleMessage: ");
                    }
                    adapter.notifyDataSetChanged();
                    //TODO 更新UI
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myfishtogether_listview);
        Button btn1 = (Button) findViewById(R.id.button3);
        btn1.setOnClickListener(this);
        adapter = new FishTogetherAdapter(MyFishTogetherActivity.this, R.layout.myfishtogether, publicfishtogether);
        ListView listView = (ListView) findViewById(R.id.list_view1);
        listView.setAdapter(adapter);
        init();
        final SwipeRefreshLayout swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.mysrl);
        //setColorSchemeResources()可以改变加载图标的颜色。
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                init();
            }
        });
    }

    private void init() {
        // 我—>我的消息—>我发起的
        new Thread() {
            @Override
            public void run() {
                String path = HttpUtil.serverPath + "/ft/queryByMine";
                //1.创建客户端对象
                org.apache.http.client.HttpClient hc = new DefaultHttpClient();
                //2.创建post请求对象
                HttpPost hp = new HttpPost(path);
                //封装form表单提交的数据
                BasicNameValuePair bnvp = new BasicNameValuePair("userId", LoginResult.user.getUserId());
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
//        FishTogether site = new FishTogether(R.drawable.pic,
//                "cya",
//                "1分钟前",
//                "100m",
//                "有没有人一起去钓鱼啊？",
//                "2017-11-4 星期六",
//                "浙江大学城市学院内河");
//        fishtogether.add(site);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                finish();
                break;
            default:
                break;

        }
    }

    private void parseJASONWithGASON(String text) {
        try {
            JSONObject result = new JSONObject(text);
            error = result.getString("error");
            JSONArray resultList = result.getJSONArray("fishTogethers");
            checkFishTogethers.clear();
            for (int i = 0; i < resultList.length(); i++) {
                JSONObject jsonObject = (JSONObject) resultList.get(i);
                CheckFishTogether checkFishTogether = new CheckFishTogether();
                checkFishTogether.setFpName(jsonObject.getString("fpName"));
                checkFishTogether.setFtAddTime(jsonObject.getString("ftAddTime"));
                checkFishTogether.setFtDetail(jsonObject.getString("ftDetail"));
                checkFishTogether.setFtId(jsonObject.getInt("ftId"));
                checkFishTogether.setFtTime(jsonObject.getString("ftTime"));
                JSONObject userObject = jsonObject.getJSONObject("user");
                FishTogetherCreateUser fishTogetherCreateUser = new FishTogetherCreateUser();
                fishTogetherCreateUser.setUserHeadSrc(userObject.getString("userHeadSrc"));
                fishTogetherCreateUser.setUserName(userObject.getString("userName"));
                checkFishTogether.setUser(fishTogetherCreateUser);
                checkFishTogethers.add(checkFishTogether);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void StorageFT() {
        publicfishtogether.clear();
        for (int i = 0; i < checkFishTogethers.size(); i++) {
            cft = checkFishTogethers.get(i);
            FishTogether site = new FishTogether(R.drawable.pic,//TODO 图片
                    cft.getUser().getUserName(),
                    cft.getFtAddTime(), // TODO 计算时间差
                    "100m",
                    cft.getFtDetail(),
                    cft.getFtTime(),
                    cft.getFpName());
            publicfishtogether.add(site);
        }
    }
}
