package com.zucc.cbc31401324.ylsh.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zucc.cbc31401324.ylsh.Bin.GSONError;
import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.R;
import com.zucc.cbc31401324.ylsh.SiteMoreChoiceActivity;
import com.zucc.cbc31401324.ylsh.http.HttpUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/22.
 */

public class DateActivity extends Activity implements
        android.view.View.OnClickListener {
    private DatePickerDialog mDataPicker;
    public TextView okcalendar;
    private TextView oksite;
    private String gsonerror;
    private static final int LOGIN_RESULT = 1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_RESULT:
                    if (gsonerror == null) {
                        parseJASONWithGASON((String) msg.obj);
                        Toast.makeText(DateActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("DateActivity", "handleMessage: ");
                    }
                    if(gsonerror.isEmpty()){
                        // TODO
                    }
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.date);
        oksite = findViewById(R.id.ok_site);
        okcalendar = (TextView) findViewById(R.id.ok_calendar);
        Button btn = (Button) findViewById(R.id.back);
        btn.setOnClickListener(this);
        TextView tv = (TextView) findViewById(R.id.putyulun);
        tv.setOnClickListener(this);
        Button btn1 = (Button) findViewById(R.id.calendar_more);
        btn1.setOnClickListener(this);
        Button btn2 = (Button) findViewById(R.id.site_more);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calendar_more:
                openDatePicker();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.site_more:
                Intent intent2 = new Intent(DateActivity.this, SiteMoreChoiceActivity.class);
                DateActivity.this.startActivityForResult(intent2, 0);
                break;
            case R.id.putyulun:
                //TODO 向后台传约钓信息 同时 回到上一级页面
                //TODO fpId 需要修改为具体位置 String
                LoginResult loginResult = new LoginResult();
                TextView time = (TextView) findViewById(R.id.ok_calendar);
                TextView addr = (TextView) findViewById(R.id.ok_site);
                EditText info = (EditText) findViewById(R.id.info);
                final String ftTime = time.getText().toString();
                final String ftDetail = info.getText().toString();
                final String fpName = addr.getText().toString();
//                final String userHeadSrc = loginResult.getUserHeadSrc();
                final String userId = LoginResult.user.getUserId();
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        //TODO ip Address
                        String path = HttpUtil.serverPath + "/ft/add";
                        //1.创建客户端对象
                        HttpClient hc = new DefaultHttpClient();
                        //2.创建post请求对象
                        HttpPost hp = new HttpPost(path);
                        //封装form表单提交的数据
                        BasicNameValuePair bnvp3 = new BasicNameValuePair("userId", userId);
                        BasicNameValuePair bnvp = new BasicNameValuePair("ftTime", ftTime);
                        BasicNameValuePair bnvp2 = new BasicNameValuePair("fpName", fpName);
                        BasicNameValuePair bnvp4 = new BasicNameValuePair("ftDetail", ftDetail);
                        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                        //把BasicNameValuePair放入集合中
                        parameters.add(bnvp);
                        parameters.add(bnvp2);
                        parameters.add(bnvp3);
                        parameters.add(bnvp4);
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
            default:
                break;

        }
    }

    private void parseJASONWithGASON(String text) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(text);
            gsonerror = jsonObject.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openDatePicker() {
        getDatePickerDialog();
        mDataPicker.show();
    }

    private void getDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mDataPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日  EE");
                okcalendar.setText(df.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 0) {
            oksite.setText(data.getExtras().getString("0"));
        }
    }

}
