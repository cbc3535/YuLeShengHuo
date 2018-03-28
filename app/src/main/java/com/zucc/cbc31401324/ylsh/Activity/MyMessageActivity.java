package com.zucc.cbc31401324.ylsh.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zucc.cbc31401324.ylsh.R;

/**
 * Created by chenbaichang on 2018/3/8.
 */

public class MyMessageActivity extends Activity implements
        android.view.View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mymessage);
        Button btn1=(Button)findViewById(R.id.button4);
        btn1.setOnClickListener(this);
        Button btn2=(Button)findViewById(R.id.meme_more);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button4:
                finish();
                break;
            case R.id.meme_more:
                Intent intent1 = new Intent(MyMessageActivity.this, MyFishTogetherActivity.class);
                MyMessageActivity.this.startActivity(intent1);
                break;
            default:break;

        }
    }
}
