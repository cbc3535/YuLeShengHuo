package com.zucc.cbc31401324.ylsh.Bin;

/**
 * Created by chenbaichang on 2018/3/27.
 */

public class LoginResult {
    public static String error;
    public static String userId;
    public static String userName;
    public static String userSex;
    public static String userHeadSrc;
    public static String userDetail;
    public static String userMail;
    public static String userComDetail;
    public static String isNotTalk;
    public static String isNotFish;

    public void setError(String error){this.error=error;}
    public String getError() {
        return error;
    }

    public void setUserId(String userId){this.userId=userId;}
    public String getUserId() {
        return userId;
    }

    public void setUserName(String userName){this.userName=userName;}
    public String getUserName() {return userName;}

    public void setUserSex(String userSex){this.userSex = userSex;}
    public String getUserSex(){return userSex;}

    public void setUserHeadSrc(String userHeadSrc){this.userHeadSrc = userHeadSrc;}
    public String getUserHeadSrc(){return userHeadSrc;}

    public void setUserDetail(String userDetail){this.userDetail = userDetail;}
    public String getUserDetail(){return userDetail;}

    public void setUserMail(String userMail){this.userMail = userMail;}
    public String getUserMail(){return userMail;}

    public String getUserComDetail() {return userComDetail;}
    public void setUserComDetail(String userComDetail) {this.userComDetail = userComDetail;}

    public String getIsNotTalk() {return isNotTalk;}
    public void setIsNotTalk(String isNotTalk) {this.isNotTalk = isNotTalk;}

    public String getIsNotFish() {return isNotFish;}
    public void setIsNotFish(String isNotFish) {this.isNotFish = isNotFish;}
}

