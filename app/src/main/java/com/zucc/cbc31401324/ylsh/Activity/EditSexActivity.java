package com.zucc.cbc31401324.ylsh.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zucc.cbc31401324.ylsh.Bin.GSONError;
import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/7.
 */

public class EditSexActivity extends Activity implements
        android.view.View.OnClickListener {
    private EditText sex;
    private GSONError gsonerror;
    private static final int LOGIN_RESULT = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_RESULT:
                    if(gsonerror == null){
                    parseJASONWithGASON((String) msg.obj);
                    }else {
                        Log.d("EditSexActivity", "handleMessage: ");
                    }
                    //TODO 更新UI
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.editsex);
        Button btn1=(Button)findViewById(R.id.set_back);
        sex = (EditText)findViewById(R.id.editsex);
        TextView tv1 = (TextView)findViewById(R.id.save);
        btn1.setOnClickListener(this);
        tv1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.set_back:
                finish();
                break;
            case R.id.save:
                //TODO 保存性别 如果男性用drawable.sex_men女性.sex_women
                LoginResult loginResult =new LoginResult();
                final String userSex = sex.getText().toString();
                final String userId = loginResult.getUserId();
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        String path = "";
                        //1.创建客户端对象
                        HttpClient hc = new DefaultHttpClient();
                        //2.创建post请求对象
                        HttpPost hp = new HttpPost(path);

                        //封装form表单提交的数据
                        BasicNameValuePair bnvp = new BasicNameValuePair("userId", userId);
                        BasicNameValuePair bnvp2 = new BasicNameValuePair("userSex", userSex);
                        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                        //把BasicNameValuePair放入集合中
                        parameters.add(bnvp);
                        parameters.add(bnvp2);

                        try {
                            //要提交的数据都已经在集合中了，把集合传给实体对象
                            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
                            //设置post请求对象的实体，其实就是把要提交的数据封装至post请求的输出流中
                            hp.setEntity(entity);
                            //3.使用客户端发送post请求
                            HttpResponse hr = hc.execute(hp);
                            if(hr.getStatusLine().getStatusCode() == 200){
                                InputStream is = hr.getEntity().getContent();
                                String text = Utils.getTextFromStream(is);

                                //发送消息，让主线程刷新ui显示text
                                Message message = new Message();
                                message.what = LOGIN_RESULT;
                                message.obj = text;
                                handler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
                break;
            default:break;

        }
    }

    private void parseJASONWithGASON(String text){
        Gson gson = new Gson();
        gsonerror = gson.fromJson(text,GSONError.class);
    }
}
