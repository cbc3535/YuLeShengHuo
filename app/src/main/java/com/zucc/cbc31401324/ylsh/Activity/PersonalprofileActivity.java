package com.zucc.cbc31401324.ylsh.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.CachePathUtil;
import com.zucc.cbc31401324.ylsh.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenbaichang on 2018/3/6.
 */

public class PersonalprofileActivity extends Activity implements
        android.view.View.OnClickListener {
    private TextView tv_name, tv_sex;
    private CircleImageView circleImageView;
    private static final int PERSONALPROFILE_RESULT = 1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PERSONALPROFILE_RESULT:
                    login();
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personalprofile);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        Button btn = (Button) findViewById(R.id.pic_more);
        Button btn1 = (Button) findViewById(R.id.name_more);
        Button btn2 = (Button) findViewById(R.id.sex_more);
        Button btn3 = (Button) findViewById(R.id.profile_more);
        Button btn4 = (Button) findViewById(R.id.contactinfo_more);
        Button btn5 = (Button) findViewById(R.id.set_back);
        circleImageView = findViewById(R.id.iv_icon);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
//              result = myhttp(username , userid);
                LoginResult loginresult = new LoginResult();
                // TODO 2.4.3暂时没找到
                Message message = new Message();
                message.what = PERSONALPROFILE_RESULT;
                message.obj = loginresult;
                handler.sendMessage(message);
            }
        }).run();
        //TODO 获取头像、昵称、性别
    }

    @SuppressLint("ShowToast")
    private void login() {
        Log.i("cws", LoginResult.user.toString());
        //TODO set头像有问题
        if (LoginResult.user.getUserHeadSrc() != null && !LoginResult.user.getUserHeadSrc().equals("default.jpg")) {
            Log.i("CachePath", CachePathUtil.getDiskCachePath(getApplicationContext()));
            circleImageView.setImageURI(Uri.fromFile(new File(CachePathUtil.getDiskCachePath(getApplicationContext()) + LoginResult.user.getUserHeadSrc())));
        }
        if (LoginResult.user.getUserName() != null) {
            tv_name.setText(LoginResult.user.getUserName());
        }
        if (LoginResult.user.getUserSex() != null) {
            tv_sex.setText(LoginResult.user.getUserSex());
        }
//        else {
//            Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_LONG).show();
//        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pic_more:
                Intent intent = new Intent(PersonalprofileActivity.this, ChangeHeadPicActivity.class);
                PersonalprofileActivity.this.startActivityForResult(intent, 0);
                break;
            case R.id.name_more:
                Intent intent1 = new Intent(PersonalprofileActivity.this, EditNameActivity.class);
                PersonalprofileActivity.this.startActivityForResult(intent1,1);
                break;
            case R.id.sex_more:
                Intent intent2 = new Intent(PersonalprofileActivity.this, EditSexActivity.class);
                PersonalprofileActivity.this.startActivityForResult(intent2,2);
                break;
            case R.id.profile_more:
                Intent intent3 = new Intent(PersonalprofileActivity.this, EditProfileActivity.class);
                PersonalprofileActivity.this.startActivityForResult(intent3,3);
                break;
            case R.id.contactinfo_more:
                Intent intent4 = new Intent(PersonalprofileActivity.this, EditContactInfoActivity.class);
                PersonalprofileActivity.this.startActivityForResult(intent4,4);
                break;
            case R.id.set_back:
                setResult(5);
                finish();
                break;
            default:
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                if(resultCode == 0){
                    circleImageView.setImageURI(Uri.fromFile((File) data.getExtras().get("0")));
                }
                break;
            default:
                if (resultCode == 1) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LoginResult loginresult = new LoginResult();
                            Message message = new Message();
                            message.what = PERSONALPROFILE_RESULT;
                            message.obj = loginresult;
                            handler.sendMessage(message);
                        }
                    }).run();
                }
                break;

        }
    }

}
