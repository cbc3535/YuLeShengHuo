package com.zucc.cbc31401324.ylsh.Bin;

/**
 * Created by chenbaichang on 2018/3/8.
 */

public class FishSite {
//    private int fishsite_pic;
    private String fishsite_name;
    private String fishsite_address;
    private String distance;

    public FishSite(String fishsite_name,String fishsite_address,String distance){
//        this.fishsite_pic=fishsite_pic;
        this.fishsite_name=fishsite_name;
        this.fishsite_address=fishsite_address;
        this.distance=distance;
    }

//    public int getFishsite_pic(){
//        return fishsite_pic;
//    }
    public String getFishsite_name(){
        return fishsite_name;
    }
    public String getFishsite_address(){
        return fishsite_address;
    }
    public String getDistance(){
        return distance;
    }
}
