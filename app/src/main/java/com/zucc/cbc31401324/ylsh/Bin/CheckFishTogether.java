package com.zucc.cbc31401324.ylsh.Bin;

/**
 * Created by chenbaichang on 2018/4/29.
 */

public class CheckFishTogether {
    //    private int fishtogether_pic;
//    private String myfishtogether_name;
//    private String myfishtogether_time;
//    private String myfishtogether_distance;
//    private String myfishtogether_info;
//    private String myfishtogether_calendar;
//    private String myfishtogether_address;
    private String fpName; // 钓点名称
    private String ftAddTime; // 约钓添加时间
    private Integer ftId; // 约钓ID
    private String ftTime; // 约钓开始时间
    private String ftDetail; // 约钓名称
    private FishTogetherCreateUser user; // 约钓发起者的信息

    public String getFpName() {
        return fpName;
    }

    public void setFpName(String fpName) {
        this.fpName = fpName;
    }

    public String getFtAddTime() {
        return ftAddTime;
    }

    public void setFtAddTime(String ftAddTime) {
        this.ftAddTime = ftAddTime;
    }

    public Integer getFtId() {
        return ftId;
    }

    public void setFtId(Integer ftId) {
        this.ftId = ftId;
    }

    public String getFtTime() {
        return ftTime;
    }

    public void setFtTime(String ftTime) {
        this.ftTime = ftTime;
    }

    public String getFtDetail() {
        return ftDetail;
    }

    public void setFtDetail(String ftDetail) {
        this.ftDetail = ftDetail;
    }

    public FishTogetherCreateUser getUser() {
        return user;
    }

    public void setUser(FishTogetherCreateUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CheckFishTogether{" +
                "fpName='" + fpName + '\'' +
                ", ftAddTime='" + ftAddTime + '\'' +
                ", ftId=" + ftId +
                ", ftTime='" + ftTime + '\'' +
                ", ftDetail='" + ftDetail + '\'' +
                ", user=" + user +
                '}';
    }
}
