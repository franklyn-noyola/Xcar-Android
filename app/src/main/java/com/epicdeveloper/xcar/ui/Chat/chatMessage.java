package com.epicdeveloper.xcar.ui.Chat;

import android.text.format.DateFormat;

import java.util.Date;

public class chatMessage {
    private String messageText;
    private String chatFrom;
    private String messageTimeFrom;
    private String messageTimeTo;
    private String messageType;

    public chatMessage(String messageText, String chatFrom, String messageType) {
        this.messageText = messageText;
        this.chatFrom = chatFrom;
        this.messageType=messageType;

        long currentDate = new Date().getTime();
        messageTimeTo = (String) DateFormat.format("dd/MM/yyyy HH:mm",
                currentDate);

        messageTimeFrom = (String) DateFormat.format("dd/MM/yyyy HH:mm",
                currentDate);


    }

    public chatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTimeFrom() {
        return messageTimeFrom;
    }

    public void setMessageTimeFrom(String messageTimeFrom) {
        this.messageTimeFrom = messageTimeFrom;
    }

    public String getMessageTimeTo() {
        return messageTimeTo;
    }

    public void setMessageTimeTo(String messageTimeTo) {
        this.messageTimeTo = messageTimeTo;
    }

    public String getChatFrom() {
        return chatFrom;
    }

    public void setChatFrom(String chatFrom) {
        this.chatFrom = chatFrom;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }



}
