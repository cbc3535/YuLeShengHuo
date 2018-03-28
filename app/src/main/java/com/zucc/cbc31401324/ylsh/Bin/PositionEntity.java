package com.zucc.cbc31401324.ylsh.Bin;

import java.text.DecimalFormat;

/**
 * Created by chenbaichang on 2018/3/20.
 */

public class PositionEntity {
    DecimalFormat decfmt = new DecimalFormat("##0.000000");

    public static double latitue;

    public static double longitude;

    public static String address;

    public static String mytitle;

    public static String charge_type;

    public static String site_location;

    public static String site_mode;

    public static String site_type;

    public static String site_info;

    public PositionEntity() {
    }

    public PositionEntity(double latitude, double longtitude, String address, String mytitle, String charge_type, String site_location,
                          String site_mode, String site_type, String site_info) {
        this.latitue = latitude;
        this.longitude = longtitude;
        this.mytitle = mytitle;
        this.address = address;
        this.charge_type = charge_type;
        this.site_location = site_location;
        this.site_mode = site_mode;
        this.site_type = site_type;
        this.site_info = site_info;
    }
}
