package com.zucc.cbc31401324.ylsh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zucc.cbc31401324.ylsh.Bin.FishSite;
import com.zucc.cbc31401324.ylsh.R;

import java.util.List;

/**
 * Created by chenbaichang on 2018/3/8.
 */

public class FishSiteAdapter extends ArrayAdapter<FishSite> {
    private int resourceId;//等会要把R.layout.fruit_item的值赋给resourceId
    //暂存 避免每次都重新加载布局
    class ViewHolder{
//        Button fishsite_pic;
        TextView fishsite_name;
        TextView fishsite_address;
        TextView distance;
    }

    public FishSiteAdapter(Context context, int textViewResourceId, List<FishSite> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;//把R.layout.fruit_item的值赋给resourceId
    }

    public View getView(int position, View convertView, ViewGroup parent){
        FishSite fishsite = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
//            viewHolder.fishsite_pic = (Button)view.findViewById(R.id.fishsite_pic);
            viewHolder.fishsite_name=(TextView)view.findViewById(R.id.fishsite_name);
            viewHolder.fishsite_address=(TextView)view.findViewById(R.id.fishsite_address);
            viewHolder.distance=(TextView)view.findViewById(R.id.distance);
            view.setTag(viewHolder);
        }
        else {//布局被加载过
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();//把之前暂存的ViewHolder赋给viewHolder
        }
        viewHolder.fishsite_name.setText(fishsite.getFishsite_name());
        viewHolder.fishsite_address.setText(fishsite.getFishsite_address());
        viewHolder.distance.setText(fishsite.getDistance());
        return view;
    }
}
