package com.dramalho.hw2017mobiledev.howardchat;

/**
 * Created by dramalho on 8/7/17.
 */

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageSource {

    public interface MessageListener{
        void onMessageRecieved(List<Message> messageList);
    }

    private static  MessageSource sMessageSource;
    private Context mContext;

    public static MessageSource get(Context context){
        if (sMessageSource == null){
            sMessageSource = new MessageSource(context);
        }
        return sMessageSource;
    }

    //Downwards is code from Lab8
    private MessageSource(Context context){
        mContext = context;
    }

    public void getMessages (final MessageListener messageListener) {
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("messages");
        Query last50messageQuery = messageRef.limitToLast(50);
        last50messageQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> messageSnapshots = dataSnapshot.getChildren();
                ArrayList<Message> messageList = new ArrayList<Message>();
                for (DataSnapshot messages : messageSnapshots) {
                    Message message = new Message(messages);
                }
                messageListener.onMessageRecieved(messageList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getMessagesForUserId(String userId, final MessageListener messageListener){
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("messages");
        Query userQuery = messageRef.orderByChild("userId").equalTo(userId).limitToFirst(50);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> messageSnapshots = dataSnapshot.getChildren();
                ArrayList<Message> messageList = new ArrayList<Message>();
                for (DataSnapshot messages : messageSnapshots) {
                    Message message = new Message(messages);
                }
                messageListener.onMessageRecieved(messageList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void sendMessage(Message message){
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("messages");
        DatabaseReference newMessage = messageRef.push();
        HashMap<String, Object> messageValueMap = new HashMap<String, Object>();
        messageValueMap.put("fromUserName", message.getmUserName());
        messageValueMap.put("fromUserId", message.getmUserId());
        messageValueMap.put("content", message.getmContent());
        newMessage.setValue(messageValueMap);

    }
}