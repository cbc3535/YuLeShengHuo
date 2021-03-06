package com.zucc.cbc31401324.ylsh.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
 * Created by chenbaichang on 2018/3/7.
 */

public class EditContactInfoActivity extends Activity implements
        android.view.View.OnClickListener {
    private EditText et_phonenumber,et_Emailaddr,et_OtherName;
    private GSONError gsonerror;
    private static final int LOGIN_RESULT = 1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_RESULT:
                    parseJASONWithGASON((String) msg.obj);
                    if(gsonerror == null || "".equals(gsonerror.getError())){
                        LoginResult.user.setUserMail(et_Emailaddr.getText().toString());
                        LoginResult.user.setUserComDetail(et_OtherName.getText().toString());
                        Toast.makeText(EditContactInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Log.d("EditNameActivity", "handleMessage: "+gsonerror.getError());
                    }
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.editcontact);
        et_phonenumber = (EditText)findViewById(R.id.editPhoneNumber);
        et_Emailaddr = (EditText)findViewById(R.id.editEmailaddr);
        et_OtherName = (EditText)findViewById(R.id.editOtherName);
        Button btn1=(Button)findViewById(R.id.set_back);
        TextView tv1 = (TextView)findViewById(R.id.save);
        btn1.setOnClickListener(this);
        tv1.setOnClickListener(this);
        et_Emailaddr.setText(LoginResult.user.getUserMail());
        et_OtherName.setText(LoginResult.user.getUserName());
        et_phonenumber.setText("暂不可修改");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.set_back:
                setResult(1);
                finish();
                break;
            case R.id.save:
                //TODO 保存联系方式
//                final String phonenumber = et_phonenumber.getText().toString();
                final String userMail = et_Emailaddr.getText().toString();
                final String userComDetail = et_OtherName.getText().toString();
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        String path = HttpUtil.serverPath + "/user/edit";
                        //1.创建客户端对象
                        HttpClient hc = new DefaultHttpClient();
                        //2.创建post请求对象
                        HttpPost hp = new HttpPost(path);

                        //封装form表单提交的数据
                        BasicNameValuePair bnvp = new BasicNameValuePair("userMail", userMail);
                        BasicNameValuePair bnvp2 = new BasicNameValuePair("userComDetail", userComDetail);
                        BasicNameValuePair bnvp3 = new BasicNameValuePair("userId", LoginResult.user.getUserId());
                        BasicNameValuePair bnvp4 = new BasicNameValuePair("userName", LoginResult.user.getUserName());
                        BasicNameValuePair bnvp5 = new BasicNameValuePair("userSex", LoginResult.user.getUserSex());
                        BasicNameValuePair bnvp6 = new BasicNameValuePair("userHeadSrc", LoginResult.user.getUserHeadSrc());
                        BasicNameValuePair bnvp7 = new BasicNameValuePair("userDetail", LoginResult.user.getUserDetail());
//                        BasicNameValuePair bnvp2 = new BasicNameValuePair("pass", pass);
                        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                        //把BasicNameValuePair放入集合中
                        parameters.add(bnvp);
                        parameters.add(bnvp2);
                        parameters.add(bnvp3);
                        parameters.add(bnvp4);
                        parameters.add(bnvp5);
                        parameters.add(bnvp6);
                        parameters.add(bnvp7);

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
                                Message msg = handler.obtainMessage();
                                msg.obj = text;
                                msg.what = LOGIN_RESULT;
                                handler.sendMessage(msg);
                            }
                        } catch (Exception e) {
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
