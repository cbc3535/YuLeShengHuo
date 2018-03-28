package com.zucc.cbc31401324.ylsh.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zucc.cbc31401324.ylsh.Bin.Passing;
import com.zucc.cbc31401324.ylsh.MarkerClickActivity_Fragment1;
import com.zucc.cbc31401324.ylsh.MarkerClickActivity_Fragment2;
import com.zucc.cbc31401324.ylsh.R;

/**
 * Created by chenbaichang on 2018/3/22.
 */

public class MarkerClickActivity extends Activity implements
        android.view.View.OnClickListener {
    private TextView name;
    private TextView charge;
    private TextView sitetype;
    private TextView model;
    private TextView siteinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.marker_click);
        Button btn = (Button)findViewById(R.id.back);
        btn.setOnClickListener(this);
        TextView tv = (TextView)findViewById(R.id.enter_yulun);
        tv.setOnClickListener(this);
        TextView tv1 = (TextView)findViewById(R.id.enter_yuediao);
        tv1.setOnClickListener(this);
        TextView name = (TextView) findViewById(R.id.name);
        TextView location = (TextView) findViewById(R.id.location);
        TextView charge = (TextView)findViewById(R.id.charge);
        TextView sitetype = (TextView)findViewById(R.id.sitetype);
        TextView model = (TextView)findViewById(R.id.model);
        TextView siteinfo = (TextView)findViewById(R.id.siteinfo);

        name.setText(Passing.name);
        location.setText(Passing.location);
        charge.setText(Passing.charge);
        sitetype.setText(Passing.sitetype);
        model.setText(Passing.model);
        siteinfo.setText(Passing.siteinfo);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.enter_yulun:
                Intent intent = new Intent(MarkerClickActivity.this, MarkerClickActivity_Fragment1.class);
                MarkerClickActivity.this.startActivity(intent);
                break;
            case R.id.enter_yuediao:
                Intent intent1 = new Intent(MarkerClickActivity.this, MarkerClickActivity_Fragment2.class);
                MarkerClickActivity.this.startActivity(intent1);
                break;
                default:break;
        }
    }
}
