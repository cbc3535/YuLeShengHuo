package com.zucc.cbc31401324.ylsh.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zucc.cbc31401324.ylsh.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddYuLunActivity extends AppCompatActivity implements View.OnClickListener{
    private CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addyulun);
        circleImageView = findViewById(R.id.iv_icon);
        circleImageView.setOnClickListener(this);
        TextView tv = findViewById(R.id.putyulun);
        TextView tv1 = findViewById(R.id.yulun_time);
        EditText editText = findViewById(R.id.yulun_text);
        tv.setOnClickListener(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        tv1.setText(simpleDateFormat.format(date));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                Intent intent = new Intent(AddYuLunActivity.this, AddYuLunPicActivity.class);
                AddYuLunActivity.this.startActivity(intent);
                break;
            case R.id.putyulun:
                //TODO HTTP 舆论信息
                break;
            case R.id.back:
                finish();
                break;
            default:break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0&&resultCode ==0){
            circleImageView.setImageURI(Uri.fromFile((File)data.getExtras().get("0")));
        }
    }
}
