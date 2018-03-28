package com.zucc.cbc31401324.ylsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zucc.cbc31401324.ylsh.Bin.MarkerClick_info;
import com.zucc.cbc31401324.ylsh.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenbaichang on 2018/3/23.
 */

public class MarkerClickListViewAdapter extends ArrayAdapter<MarkerClick_info> {
    private int resourceId;//等会要把R.layout.fruit_item的值赋给resourceId
    class ViewHolder//用来暂存“水果名称”的TextView控件与“水果图片”的ImageView控件，避免每次都重新加载布局，优化程序的流畅度
    {
        CircleImageView headpic;
        TextView userlevel;
        TextView username;
        TextView time;
        TextView userinfo;
        ImageView userimage;
    }

    public MarkerClickListViewAdapter(Context context, int textViewResourceId, List<MarkerClick_info> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;//把R.layout.fruit_item的值赋给resourceId
    }

    public View getView(int position, View convertView, ViewGroup parent){
        MarkerClick_info markerclickinfo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new MarkerClickListViewAdapter.ViewHolder();
            viewHolder.headpic = (CircleImageView)view.findViewById(R.id.iv_icon);
            viewHolder.userlevel = (TextView)view.findViewById(R.id.userlevel);
            viewHolder.username = (TextView)view.findViewById(R.id.username);
            viewHolder.time = (TextView)view.findViewById(R.id.time);
            viewHolder.userinfo = (TextView)view.findViewById(R.id.userinfo);
            viewHolder.userimage = (ImageView) view.findViewById(R.id.userimage);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (MarkerClickListViewAdapter.ViewHolder)view.getTag();
        }
        viewHolder.headpic.setImageResource(markerclickinfo.getHeadpic());
        viewHolder.userlevel.setText(markerclickinfo.getUserlevel());
        viewHolder.username.setText(markerclickinfo.getUsername());
        viewHolder.time.setText(markerclickinfo.getTime());
        viewHolder.userinfo.setText(markerclickinfo.getUserinfo());
        viewHolder.userimage.setImageResource(markerclickinfo.getUserimage());
        return view;
    }
}
