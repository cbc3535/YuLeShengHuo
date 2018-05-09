package com.zucc.cbc31401324.ylsh.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.R;
import com.zucc.cbc31401324.ylsh.http.HttpUtil;

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
 * Created by chenbaichang on 2018/2/6.
 */

public class LoginActivity extends Activity implements
        android.view.View.OnClickListener {
    private Context applicationContext = null ;
    private static final int LOGIN_RESULT = 1;
    private EditText et_password,et_userName;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_RESULT:
                    parseJASONWithGASON((String) msg.obj);
                    if (LoginResult.user.getError().isEmpty()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext = getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button btn1=(Button)findViewById(R.id.btn_login2);
        TextView tv1 = (TextView)findViewById(R.id.register);
        btn1.setOnClickListener(this);
        tv1.setOnClickListener(this);
        et_userName = findViewById(R.id.et_userName);
        et_password = findViewById(R.id.et_password);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login2:
                final String phone = et_userName.getText().toString();
                final String pass = et_password.getText().toString();
                //TODO 判断用户手机号和密码是否匹配
                // TODO 2.4.3 登录
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //封装
//                        JSONObject job = new JSONObject();
//                        /**
//                         * ...
//                         */
//                        BasicNameValuePair c = new BasicNameValuePair("userPhone", phone);
//                        BasicNameValuePair c1 = new BasicNameValuePair("userPwd", pass);
//                        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
//                        parameters.add(c);
//                        parameters.add(c1);
//                        //http请求
//                        JSONObject jsonObjectResult = HttpUtil.postMethod(parameters, "year_loadall.action");
//                        LoginResult loginresult = new LoginResult();
////                        if (error == null||error.equl(""))
////                            //正常流程
////                            System.out.println(jsonObject.toString());
////                        else
////                            //非法流程
////                            System.out.println(error);
//                    }
//                }).start();
//                if(phone.isEmpty()){
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    LoginActivity.this.startActivity(intent);
//                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = HttpUtil.serverPath + "/user/login";
                        //1.创建客户端对象
                        HttpClient hc = new DefaultHttpClient();
                        //2.创建post请求对象
                        HttpPost hp = new HttpPost(path);
                        //封装form表单提交的数据
                        BasicNameValuePair bnvp = new BasicNameValuePair("userPhone", phone);
                        BasicNameValuePair bnvp2 = new BasicNameValuePair("userPwd", pass);
                        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                      //1.GSON.toJSONResult
                        //2.HTTP -> GSON
                            //3.->JSON,JSONUtil->RESULT
                                //4.show();

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
                }).start();
                break;
            case R.id.register:
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent2);
                break;
                default:break;

        }
    }
    private void parseJASONWithGASON(String text){
        Gson gson = new Gson();
        LoginResult.user = gson.fromJson(text,new TypeToken<LoginResult>(){}.getType());
//        List<LoginResult> gsonloginresult = gson.fromJson(text,new TypeToken<List<LoginResult>>(){}.getType());
    }
}
