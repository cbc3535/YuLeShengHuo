package com.zucc.cbc31401324.ylsh.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zucc.cbc31401324.ylsh.Adapter.FishTogetherAdapter;
import com.zucc.cbc31401324.ylsh.Bin.FishTogether;
import com.zucc.cbc31401324.ylsh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/22.
 */

public class Fragment_together_Fragment2 extends Fragment {
    private List<FishTogether> fishtogether_distance = new ArrayList<FishTogether>();
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publicfishtogether_listview, container, false);
        init();
        FishTogetherAdapter adapter = new FishTogetherAdapter(getActivity(),R.layout.myfishtogether,fishtogether_distance);
        ListView listView = (ListView)view.findViewById(R.id.list_view1);
        listView.setAdapter(adapter);
        return view;

    }
    private void init() {
        //TODO 约钓 距离最近
        // 我—>我的消息—>我发起的
        FishTogether site = new FishTogether(R.drawable.pic,
                "cya",
                "1分钟前",
                "100m",
                "有没有人一起去钓鱼啊？",
                "2017-11-4 星期六",
                "浙江大学城市学院内河");
        fishtogether_distance.add(site);
        FishTogether site1 = new FishTogether(R.drawable.pic,
                "cbc",
                "10分钟前",
                "1000m",
                "一起去钓鱼啊？",
                "2017-12-14 星期六",
                "浙江大学城市学院");
        fishtogether_distance.add(site1);
        FishTogether site2 = new FishTogether(R.drawable.pic,
                "cbc",
                "10分钟前",
                "1000m",
                "一起去钓鱼啊？",
                "2017-12-14 星期六",
                "浙江大学城市学院");
        fishtogether_distance.add(site2);
        FishTogether site3 = new FishTogether(R.drawable.pic,
                "cbc",
                "10分钟前",
                "1000m",
                "一起去钓鱼啊？",
                "2017-12-14 星期六",
                "浙江大学城市学院");
        fishtogether_distance.add(site3);

    }
}
