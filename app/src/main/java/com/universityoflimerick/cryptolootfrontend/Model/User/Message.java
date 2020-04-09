package com.universityoflimerick.cryptolootfrontend.Model.User;

public class Message {
    private String msg;
    private User user;

    public Message(String msg, User user){
        this.msg = msg;
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public String getUserName(){
        return user.getUsername();
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
