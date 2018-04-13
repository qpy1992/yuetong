package com.example.win7.ytdemo.adapter;


import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/4/12 19:43
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class MessageListenerAdapter implements EMMessageListener {
    @Override
    public void onMessageReceived(List<EMMessage> list) {
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> messages) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> messages) {

    }

//    @Override
//    public void onMessageReadAckReceived(List<EMMessage> list) {
//    }
//
//    @Override
//    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
//    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
    }
}
