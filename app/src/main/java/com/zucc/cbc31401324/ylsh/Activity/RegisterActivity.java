package com.zucc.cbc31401324.ylsh.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zucc.cbc31401324.ylsh.Bin.GSONError;
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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by chenbaichang on 2018/3/5.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText edit_phone;
    private EditText edit_cord;
    private TextView now;
    private Button btn_getCord;
    private Button btn_register;
    private String phone_number;
    private String cord_number;
    private EditText editpassword;
    private EditText confirmpassword;
    EventHandler eventHandler;
    private int time=60;
    private boolean flag=true;
    private GSONError gsonerror;
    private static final int LOGIN_RESULT = 1;
    private Handler handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_RESULT:
                    parseJSONWithGSON((String)msg.obj);
                    if(gsonerror.getError() == null){
                        //跳转登录页面
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    }else {
                        Log.d("RegisterActivity", "handleMessage: "+gsonerror.getError());
                    }
                    //TODO 更新UI
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getId();


        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg=new Message();
                msg.arg1=event;
                msg.arg2=result;
                msg.obj=data;
                handler.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    /**
     * 使用Handler来分发Message对象到主线程中，处理事件
     */
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event=msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if(result == SMSSDK.RESULT_COMPLETE) {
                    boolean smart = (Boolean)data;
                    if(smart) {
                        Toast.makeText(getApplicationContext(),"该手机号已经注册过，请重新输入",
                                Toast.LENGTH_LONG).show();
                        edit_phone.requestFocus();
                        return;
                    }
                }
            }
            if(result==SMSSDK.RESULT_COMPLETE)
            {

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码输入正确",
                            Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                if(flag)
                {
                    btn_getCord.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"验证码获取失败请重新获取", Toast.LENGTH_LONG).show();
                    edit_phone.requestFocus();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"验证码输入错误", Toast.LENGTH_LONG).show();
                }
            }

        }

    };

    /**
     * 获取id
     */
    private void getId()
    {
        edit_phone=findViewById(R.id.edit_phone);
        edit_cord=findViewById(R.id.edit_code);
        btn_getCord=findViewById(R.id.btn_getcord);
        btn_register=findViewById(R.id.btn_register);
        editpassword=findViewById(R.id.editpassword);
        confirmpassword=findViewById(R.id.confirmpassword);
        btn_getCord.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    /**
     * 按钮点击事件
     */
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_getcord:
                if(judPhone())//去掉左右空格获取字符串
                {
                    SMSSDK.getVerificationCode("86",phone_number);
                    edit_cord.requestFocus();
                }
                break;
            case R.id.btn_register:
                //TODO 向服务器提交注册信息
                if(judCord())
                    SMSSDK.submitVerificationCode("86",phone_number,cord_number);
                flag=false;
                final String phone = edit_phone.getText().toString();
                final String pass = editpassword.getText().toString();
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        String path = "";
                        //1.创建客户端对象
                        HttpClient hc = new DefaultHttpClient();
                        //2.创建post请求对象
                        HttpPost hp = new HttpPost(path);

                        //封装form表单提交的数据
                        BasicNameValuePair bnvp = new BasicNameValuePair("name", phone);
                        BasicNameValuePair bnvp2 = new BasicNameValuePair("pass", pass);
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
                                handler1.sendMessage(message);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };
                t.start();
                break;
            default:
                break;
        }
    }

    private boolean judPhone()
    {
        if(TextUtils.isEmpty(edit_phone.getText().toString().trim()))
        {
            Toast.makeText(RegisterActivity.this,"请输入您的电话号码",Toast.LENGTH_LONG).show();
            edit_phone.requestFocus();
            return false;
        }
        else if(edit_phone.getText().toString().trim().length()!=11)
        {
            Toast.makeText(RegisterActivity.this,"您的电话号码位数不正确",Toast.LENGTH_LONG).show();
            edit_phone.requestFocus();
            return false;
        }
        else
        {
            phone_number=edit_phone.getText().toString().trim();
            String num="[1][358]\\d{9}";
            if(phone_number.matches(num))
                return true;
            else
            {
                Toast.makeText(RegisterActivity.this,"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    private boolean judCord()
    {
        judPhone();
        if(TextUtils.isEmpty(edit_cord.getText().toString().trim()))
        {
            Toast.makeText(RegisterActivity.this,"请输入您的验证码",Toast.LENGTH_LONG).show();
            edit_cord.requestFocus();
            return false;
        }
        else if(edit_cord.getText().toString().trim().length()!=4)
        {
            Toast.makeText(RegisterActivity.this,"您的验证码位数不正确",Toast.LENGTH_LONG).show();
            edit_cord.requestFocus();

            return false;
        }
        else
        {
            cord_number=edit_cord.getText().toString().trim();
            return true;
        }

    }

    private void parseJSONWithGSON(String text){
        Gson gson = new Gson();
        gsonerror = gson.fromJson(text,GSONError.class);
    }

}
