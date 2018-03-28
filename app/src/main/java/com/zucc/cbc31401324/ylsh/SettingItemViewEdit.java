package com.zucc.cbc31401324.ylsh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by chenbaichang on 2018/3/20.
 */

public class SettingItemViewEdit extends LinearLayout {

    TextView myLeftTextView;
    EditText myRightEditText;

    public SettingItemViewEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();
    }

    private void initview() {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view=inflater.inflate(R.layout.setting_view_edit, this);
        findView(view);
    }

    private void findView(View view) {
        myLeftTextView = (TextView) view.findViewById(R.id.setting_edit_LeftText);
        myRightEditText = (EditText) view.findViewById(R.id.setting_edit_RightEdit);
    }
    /**
     * 返回左边的控件
     * @return
     */
    public TextView getMyLeftTextView() {
        return myLeftTextView;
    }
    /**
     * 返回右边的控件
     * @return
     */
    public TextView getMyRightTextView() {
        return myRightEditText;
    }


    /**
     * 设置左边的文字
     * @param txt
     */
    public void setLeftText(String txt){
        myLeftTextView.setText(txt);
    }
    /**
     * 设置右边的文字
     * @param txt
     */
    public void setRightText(String txt){
        myRightEditText.setText(txt);
    }
}
