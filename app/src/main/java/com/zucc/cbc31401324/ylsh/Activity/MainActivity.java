package com.zucc.cbc31401324.ylsh.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.zucc.cbc31401324.ylsh.Fragment_Map_Activity;
import com.zucc.cbc31401324.ylsh.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    private ViewPager mViewPager;// 用来放置界面切换
    private PagerAdapter mPagerAdapter;// 初始化View适配器
    private List<Fragment> mViews = new ArrayList<Fragment>();// 用来存放Tab01-04
    // 四个Tab，每个Tab包含一个按钮
    private LinearLayout mTabMap;
    private LinearLayout mTabFishTogether;
    private LinearLayout mTabMe;
    // 四个按钮
    private ImageButton mMapImg;
    private ImageButton mFishTogetherImg;
    private ImageButton mMeImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        initView();
        initViewPage();
        initEvent();
    }

    private void initEvent() {
        mTabMap.setOnClickListener(this);
        mTabFishTogether.setOnClickListener(this);
        mTabMe.setOnClickListener(this);
        // mTabSetting.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             *ViewPage左右滑动时
             */
            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetImg();
                        mMapImg.setImageResource(R.drawable.ditu_1);
                        break;
                    case 1:
                        resetImg();
                        mFishTogetherImg.setImageResource(R.drawable.yue_1);
                        break;
                    case 2:
                        resetImg();
                        mMeImg.setImageResource(R.drawable.me_1);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     * 初始化设置
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
        // 初始化四个LinearLayout
        mTabMap = (LinearLayout) findViewById(R.id.id_tab_map);
        mTabFishTogether = (LinearLayout) findViewById(R.id.id_tab_fishtogether);
        mTabMe = (LinearLayout) findViewById(R.id.id_tab_me);
        // 初始化四个按钮
        mMapImg = (ImageButton) findViewById(R.id.id_tab_map_img);
        mFishTogetherImg = (ImageButton) findViewById(R.id.id_tab_fishtogether_img);
        mMeImg = (ImageButton) findViewById(R.id.id_tab_me_img);
        resetImg();
    }

    /**
     * 初始化ViewPage
     */
    private void initViewPage() {

        // 初妈化四个布局
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        Fragment tab01 = new Fragment_Map_Activity();
        Fragment tab02 = new Fragment_Together_Activity();
        Fragment tab03 = new Fragment_Me_Activity();
        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);

        // 适配器初始化并设置
        mPagerAdapter = new myPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    /**
     * 判断哪个要显示，及设置按钮图片
     */
    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.id_tab_map:
                mViewPager.setCurrentItem(0);
                resetImg();
                mMapImg.setImageResource(R.drawable.ditu_1);
                break;
            case R.id.id_tab_fishtogether:
                mViewPager.setCurrentItem(1);
                resetImg();
                mFishTogetherImg.setImageResource(R.drawable.yue_1);
                break;
            case R.id.id_tab_me:
                mViewPager.setCurrentItem(2);
                resetImg();
                mMeImg.setImageResource(R.drawable.me_1);
                break;
            default:
                break;
        }
    }

    /**
     * 把所有图片变暗
     */
    private void resetImg() {
        mMapImg.setImageResource(R.drawable.map);
        mFishTogetherImg.setImageResource(R.drawable.yue);
        mMeImg.setImageResource(R.drawable.me);
    }

    private class myPagerAdapter extends FragmentPagerAdapter {

        public myPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mViews.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}