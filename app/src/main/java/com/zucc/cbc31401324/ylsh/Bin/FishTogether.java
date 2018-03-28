package com.zucc.cbc31401324.ylsh.Bin;

/**
 * Created by chenbaichang on 2018/3/9.
 */

public class FishTogether {
    private int fishtogether_pic;
    private String myfishtogether_name;
    private String myfishtogether_time;
    private String myfishtogether_distance;
    private String myfishtogether_info;
    private String myfishtogether_calendar;
    private String myfishtogether_address;

    public FishTogether(int fishtogether_pic,String myfishtogether_name,
                        String myfishtogether_time,String myfishtogether_distance,
                        String myfishtogether_info,String myfishtogether_calendar,
                        String myfishtogether_address){
        this.fishtogether_pic = fishtogether_pic;
        this.myfishtogether_name = myfishtogether_name;
        this.myfishtogether_time = myfishtogether_time;
        this.myfishtogether_distance = myfishtogether_distance;
        this.myfishtogether_address = myfishtogether_address;
        this.myfishtogether_info = myfishtogether_info;
        this.myfishtogether_calendar = myfishtogether_calendar;
    }

    public int getFishtogether_pic(){
        return fishtogether_pic;
    }
    public String getMyfishtogether_name(){return myfishtogether_name;}
    public String getMyfishtogether_time(){return myfishtogether_time;}
    public String getMyfishtogether_distance(){return myfishtogether_distance;}
    public String getMyfishtogether_info(){return myfishtogether_info;}
    public String getMyfishtogether_calendar(){return myfishtogether_calendar;}
    public String getMyfishtogether_address(){return myfishtogether_address;}
}
