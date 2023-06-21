package com.example.memorizes.db.vo;


public class UserVO {
    private String userID;
    private String userPassword;
    private String userName;
    private String userPhone;

    public UserVO() {
    }

    public UserVO(String id, String pw, String name, String phone) {
        this.userID = id;
        this.userPassword = pw;
        this.userName = name;
        this.userPhone = phone;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
