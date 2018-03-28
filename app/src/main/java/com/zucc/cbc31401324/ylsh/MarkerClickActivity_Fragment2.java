package com.zucc.cbc31401324.ylsh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.zucc.cbc31401324.ylsh.Adapter.FishTogetherAdapter;
import com.zucc.cbc31401324.ylsh.Bin.FishTogether;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/23.
 */

public class MarkerClickActivity_Fragment2 extends Activity implements  android.view.View.OnClickListener {
    private List<FishTogether> markerclick2 = new ArrayList<FishTogether>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.markerclick_yuediao);
        Button btn = (Button)findViewById(R.id.back);
        btn.setOnClickListener(this);
        init();
        FishTogetherAdapter adapter = new FishTogetherAdapter(MarkerClickActivity_Fragment2.this,R.layout.myfishtogether,markerclick2);
        ListView listView = (ListView)findViewById(R.id.list_view1);
        listView.setAdapter(adapter);
    }
    private void init() {
        //TODO 约钓 某一钓点内的约钓
        // 我—>我的消息—>我发起的
        FishTogether site = new FishTogether(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                "2017-11-4 星期六",
                "浙江大学城市学院内河");
        markerclick2.add(site);
        FishTogether site1 = new FishTogether(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                "2017-11-4 星期六",
                "浙江大学城市学院内河");
        markerclick2.add(site1);
        FishTogether site2 = new FishTogether(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                "2017-11-4 星期六",
                "浙江大学城市学院内河");
        markerclick2.add(site2);
        FishTogether site3 = new FishTogether(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                "2017-11-4 星期六",
                "浙江大学城市学院内河");
        markerclick2.add(site3);

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
