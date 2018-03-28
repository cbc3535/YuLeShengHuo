package com.zucc.cbc31401324.ylsh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenbaichang on 2018/3/20.
 */

public class Infos implements Serializable {
    private static final long serialVersionUID = 3998628669601540079L;
    private double latitude;
    private double longitude;
    private String imgurl;
    private String name;
    private String addr;
    private String distance;
    private int zan;
    private String mytitle;
    private String charge_type;
    private String site_location;
    private String site_mode;
    private String site_type;
    private String site_info;
    private String returnid;
    private double mLatitude;
    private double mLongitude;

    public static List<Infos> infos = new ArrayList<Infos>();

    public static List<Infos> returnInfos = new ArrayList<Infos>();




    public static List<Infos> getInfos() {
        return infos;
    }
    public static void setInfos(List<Infos> infos) {
        Infos.infos = infos;
    }

    //我的位置坐标
    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }
    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }
    public double getmLatitude() {
        return mLatitude;
    }
    public double getmLongitude() {
        return mLongitude;
    }
    public String getMytitle() {
        return mytitle;
    }
    public void setMytitle(String mytitle) {
        this.mytitle = mytitle;
    }
    public String getSite_location() {
        return site_location;
    }
    public void setSite_location(String site_location) {this.site_location = site_location;}
    public String getSite_mode() {
        return site_mode;
    }
    public void setSite_mode(String site_mode) {this.site_mode = site_mode;}
    public String getSite_type() {
        return site_type;
    }
    public void setSite_type(String site_type) {this.site_type = site_type;}
    public String getSite_info() {
        return site_info;
    }
    public void setSite_info(String site_info) {this.site_info = site_info;}
    public String getAddr() {return addr;}
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getCharge_type(){return charge_type;}
    public void setCharge_type(String charge_type){this.charge_type = charge_type;}
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getImgurl() {
        return imgurl;
    }
    public void setImgurl(String imgurl) {this.imgurl = imgurl;}
    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public int getZan() {
        return zan;
    }
    public void setZan(int zan) {
        this.zan = zan;
    }


    //云存储返回数据
    public String getReturnid() {
        return returnid;
    }
    public void setReturnid(String returnid) {
        this.returnid = returnid;
    }

    public static List<Infos> getReturnInfos() {
        return returnInfos;
    }
    public static void setReturnInfos(List<Infos> returnInfos) {
        Infos.returnInfos = returnInfos;
    }

}
