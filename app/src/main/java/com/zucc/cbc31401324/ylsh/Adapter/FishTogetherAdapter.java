package com.zucc.cbc31401324.ylsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zucc.cbc31401324.ylsh.Bin.FishTogether;
import com.zucc.cbc31401324.ylsh.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenbaichang on 2018/3/9.
 */

public class FishTogetherAdapter extends ArrayAdapter<FishTogether> {
    private int resourceId;//等会要把R.layout.fruit_item的值赋给resourceId
    class ViewHolder//用来暂存“水果名称”的TextView控件与“水果图片”的ImageView控件，避免每次都重新加载布局，优化程序的流畅度
    {
        CircleImageView fishtogether_pic;
        TextView myfishtogether_name;
        TextView myfishtogether_time;
        TextView myfishtogether_distance;
        TextView myfishtogether_info;
        TextView myfishtogether_calendar;
        TextView myfishtogether_address;
    }

    public FishTogetherAdapter(Context context, int textViewResourceId, List<FishTogether> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;//把R.layout.fruit_item的值赋给resourceId
    }

    public View getView(int position, View convertView, ViewGroup parent){
        FishTogether fishtogether = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.fishtogether_pic = (CircleImageView)view.findViewById(R.id.iv_icon);
            viewHolder.myfishtogether_name = (TextView)view.findViewById(R.id.myfishtogether_name);
            viewHolder.myfishtogether_time = (TextView)view.findViewById(R.id.myfishtogether_time);
            viewHolder.myfishtogether_distance = (TextView)view.findViewById(R.id.myfishtogether_distance);
            viewHolder.myfishtogether_info = (TextView)view.findViewById(R.id.myfishtogether_info);
            viewHolder.myfishtogether_calendar = (TextView)view.findViewById(R.id.myfishtogether_calendar);
            viewHolder.myfishtogether_address = (TextView)view.findViewById(R.id.myfishtogether_address);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.fishtogether_pic.setImageResource(fishtogether.getFishtogether_pic());
        viewHolder.myfishtogether_name.setText(fishtogether.getMyfishtogether_name());
        viewHolder.myfishtogether_time.setText(fishtogether.getMyfishtogether_time());
        viewHolder.myfishtogether_distance.setText(fishtogether.getMyfishtogether_distance());
        viewHolder.myfishtogether_info.setText(fishtogether.getMyfishtogether_info());
        viewHolder.myfishtogether_calendar.setText(fishtogether.getMyfishtogether_calendar());
        viewHolder.myfishtogether_address.setText(fishtogether.getMyfishtogether_address());
        return view;
    }
}
