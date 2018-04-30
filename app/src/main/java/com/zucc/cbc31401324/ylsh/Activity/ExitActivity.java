package com.zucc.cbc31401324.ylsh.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.zucc.cbc31401324.ylsh.Bin.LoginResult;
import com.zucc.cbc31401324.ylsh.R;

/**
 * Created by chenbaichang on 2018/3/6.
 */

public class ExitActivity extends Activity implements
        android.view.View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        Button btn1=(Button)findViewById(R.id.set_back1);
        Button btn2=(Button)findViewById(R.id.exit);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        //TODO 注销操作
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.set_back1:
                finish();
                break;
            case R.id.exit:
                LoginResult.user = new LoginResult();
                Intent intent = new Intent(ExitActivity.this, LoginActivity.class);
                ExitActivity.this.startActivity(intent);
                break;
            default:break;

        }

    }
}
