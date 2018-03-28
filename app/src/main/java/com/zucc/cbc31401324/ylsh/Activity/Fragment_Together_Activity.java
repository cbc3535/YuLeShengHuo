package com.zucc.cbc31401324.ylsh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zucc.cbc31401324.ylsh.Adapter.MyFragmentPagerAdapter;
import com.zucc.cbc31401324.ylsh.R;

/**
 * Created by chenbaichang on 2018/3/22.
 */

public class Fragment_Together_Activity extends Fragment implements View.OnClickListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_together, container, false);
        Button btn = (Button)view.findViewById(R.id.back);
        btn.setOnClickListener(this);
        Button btn1 = (Button)view.findViewById(R.id.date);
        btn1.setOnClickListener(this);
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager)view.findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                Fragment_Together_Activity.this.startActivity(intent);
                break;
            case R.id.date:
                Intent intent1 = new Intent(getActivity(), DateActivity.class);
                Fragment_Together_Activity.this.startActivity(intent1);
                break;
            default:break;
        }
    }
}
