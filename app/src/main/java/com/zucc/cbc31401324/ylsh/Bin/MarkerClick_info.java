package com.zucc.cbc31401324.ylsh.Bin;

/**
 * Created by chenbaichang on 2018/3/23.
 */

public class MarkerClick_info {
    private int headpic;
    private String userlevel;
    private String username;
    private String time;
    private String userinfo;
    private int userimage;

    public MarkerClick_info(int headpic,String userlevel,
                        String username,String time,
                        String userinfo,int userimage){
        this.headpic = headpic;
        this.userlevel = userlevel;
        this.username = username;
        this.time = time;
        this.userinfo = userinfo;
        this.userimage = userimage;
    }

    public int getHeadpic(){
        return headpic;
    }
    public String getUserlevel(){return userlevel;}
    public String getUsername(){return username;}
    public String getTime(){return time;}
    public String getUserinfo(){return userinfo;}
    public int getUserimage(){return userimage;}
}
