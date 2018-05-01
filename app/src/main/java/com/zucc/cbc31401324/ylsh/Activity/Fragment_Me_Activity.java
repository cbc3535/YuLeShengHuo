package com.zucc.cbc31401324.ylsh.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.CachePathUtil;
import com.zucc.cbc31401324.ylsh.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenbaichang on 2018/2/8.
 */

public class Fragment_Me_Activity extends Fragment implements View.OnClickListener {
    private TextView user_name;
    private Button user_sex;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_me, container, false);
        user_name = (TextView) view.findViewById(R.id.user_name);
        user_sex = (Button) view.findViewById(R.id.user_sex);
        TextView tv1 = (TextView) view.findViewById(R.id.personal);
        TextView tv2 = (TextView) view.findViewById(R.id.myfishsite);
        TextView tv5 = (TextView) view.findViewById(R.id.mynotice);
        TextView tv6 = (TextView) view.findViewById(R.id.setting);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
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
        return view;
    }

    @SuppressLint("ShowToast")
    private void login() {
        //TODO set头像有问题
        if (LoginResult.user.getUserHeadSrc() != null && !LoginResult.user.getUserHeadSrc().equals("default.jpg")) {
            Log.i("CachePath", CachePathUtil.getDiskCachePath(this.getContext()));
            circleImageView.setImageURI(Uri.fromFile(new File(CachePathUtil.getDiskCachePath(this.getContext()) + LoginResult.user.getUserHeadSrc())));
        }
        if (LoginResult.user.getUserName() != null) {
            user_name.setText(LoginResult.user.getUserName());
        }
        if (LoginResult.user.getUserSex() != null) {
            if (LoginResult.user.getUserSex().equals("男"))
                user_sex.setBackgroundResource(R.drawable.sex_men);
            else
                user_sex.setBackgroundResource(R.drawable.sex_women);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal:
                Intent intent = new Intent(getActivity(), PersonalprofileActivity.class);
                Fragment_Me_Activity.this.startActivityForResult(intent,1);
                break;
            case R.id.myfishsite:
                Intent intent2 = new Intent(getActivity(), MyFishSiteActivity.class);
                Fragment_Me_Activity.this.startActivity(intent2);
                break;
            case R.id.mynotice:
                Intent intent3 = new Intent(getActivity(), MyMessageActivity.class);
                Fragment_Me_Activity.this.startActivity(intent3);
                break;
            case R.id.setting:
                Intent intent1 = new Intent(getActivity(), ExitActivity.class);
                Fragment_Me_Activity.this.startActivity(intent1);
                break;
            default:
                break;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 5:
               login();
               break;
               default:
        }
    }
}
