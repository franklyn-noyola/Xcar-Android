package com.epicdeveloper.xcar.ui.Chat;

import android.text.format.DateFormat;

import java.util.Date;

public class listUsertoChat {
    private String messageText;
    private String userToChat;
    private String blockedUs;
    private String messageType;
    private String messageTime;

    public listUsertoChat(String messageText, String userToChat, String blockedUs, String messageType) {
        this.messageText = messageText;
        this.userToChat = userToChat;
        this.blockedUs = blockedUs;
        this.messageType=messageType;

        long currentDate = new Date().getTime();
        messageTime = (String) DateFormat.format("dd/MM/yyyy HH:mm",
                currentDate);

    }

    public listUsertoChat(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getUserToChat() {
        return userToChat;
    }

    public void setUserToChat(String chatFrom) {
        this.userToChat = chatFrom;
    }

    public String getBlockedUs() {
        return blockedUs;
    }

    public void setBlockedUs(String blockedUs) {
        this.blockedUs = blockedUs;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
