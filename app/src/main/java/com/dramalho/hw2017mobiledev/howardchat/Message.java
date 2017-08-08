package com.dramalho.hw2017mobiledev.howardchat;

/**
 * Created by dramalho on 8/7/17.
 */

import com.google.firebase.database.DataSnapshot;

public class Message {

    private String mUserName;
    private String mUserId;
    private String mContent;

    public Message(String userName, String userId, String message){
        mUserName = userName;
        mUserId = userId;
        mContent = message;

    }

    public Message(DataSnapshot messageSnapshot) {
        mUserName = messageSnapshot.child("fromUserName").getValue(String.class);
        mUserId = messageSnapshot.child("fromUserId").getValue(String.class);
        mContent = messageSnapshot.child("content").getValue(String.class);
    }

    public String getmUserName(){
        return mUserName;
    }

    public String getmUserId(){
        return mUserId;
    }

    public String getmContent(){
        return mContent;
    }

}