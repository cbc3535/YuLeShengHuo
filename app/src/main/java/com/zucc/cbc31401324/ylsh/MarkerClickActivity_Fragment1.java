package com.zucc.cbc31401324.ylsh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.zucc.cbc31401324.ylsh.Adapter.MarkerClickListViewAdapter;
import com.zucc.cbc31401324.ylsh.Bin.MarkerClick_info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/23.
 */

public class MarkerClickActivity_Fragment1 extends Activity implements  android.view.View.OnClickListener {
    private List<MarkerClick_info> markerclick = new ArrayList<MarkerClick_info>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.markerclick_listview);
        Button btn = (Button)findViewById(R.id.back);
        btn.setOnClickListener(this);
        init();
        MarkerClickListViewAdapter adapter = new MarkerClickListViewAdapter(MarkerClickActivity_Fragment1.this,R.layout.markerclick_base,markerclick);
        ListView listView = (ListView)findViewById(R.id.list_view1);
        listView.setAdapter(adapter);
    }
    private void init() {
        //TODO 渔论 某一钓点内的渔论
        // 我—>我的消息—>我发起的
        MarkerClick_info site = new MarkerClick_info(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                R.drawable.fish);
        markerclick.add(site);
        MarkerClick_info site1 = new MarkerClick_info(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                R.drawable.fish);
        markerclick.add(site1);
        MarkerClick_info site2 = new MarkerClick_info(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                R.drawable.fish);
        markerclick.add(site2);
        MarkerClick_info site3 = new MarkerClick_info(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                R.drawable.fish);
        markerclick.add(site3);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:break;
        }
    }
}
