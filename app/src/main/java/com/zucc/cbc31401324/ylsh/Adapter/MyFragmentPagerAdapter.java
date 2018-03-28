package com.zucc.cbc31401324.ylsh.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zucc.cbc31401324.ylsh.Activity.Fragment_together_Fragment1;
import com.zucc.cbc31401324.ylsh.Activity.Fragment_together_Fragment2;
import com.zucc.cbc31401324.ylsh.Activity.Fragment_together_Fragment3;

/**
 * Created by chenbaichang on 2018/3/22.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"最新发布", "距离最近", "我关注的"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new Fragment_together_Fragment2();
        } else if (position == 2) {
            return new Fragment_together_Fragment3();
        }
        return new Fragment_together_Fragment1();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
