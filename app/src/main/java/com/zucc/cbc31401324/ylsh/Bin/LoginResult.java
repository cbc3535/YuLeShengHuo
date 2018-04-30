package com.zucc.cbc31401324.ylsh.Bin;

/**
 * Created by chenbaichang on 2018/3/27.
 */

public class LoginResult {
    public static LoginResult user = new LoginResult();
    private String error;
    private String userId;
    private String userName;
    private String userSex;
    private String userHeadSrc;
    private String userDetail;
    private String userMail;
    private String userComDetail;
    private String isNotTalk;
    private String isNotFish;

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

    @Override
    public String toString() {
        return "LoginResult{" +
                "error='" + error + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userHeadSrc='" + userHeadSrc + '\'' +
                ", userDetail='" + userDetail + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userComDetail='" + userComDetail + '\'' +
                ", isNotTalk='" + isNotTalk + '\'' +
                ", isNotFish='" + isNotFish + '\'' +
                '}';
    }
}

