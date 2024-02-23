package com.epicdeveloper.xcar.ui.sendNotifications;

import android.text.format.DateFormat;

import java.util.Date;

public class sendNotificationData {

    private String subjectUserData;
    private String messageUserData;
    private String userPlateData;
    private String imageName;
    private String imageUri;
    public String messageTime;
    private String leido;



    public sendNotificationData(){

    }

    public sendNotificationData(String userPlataData, String subjectUserData,  String messageUserData, String leido,String imageName,String imageUri){
        this.userPlateData=userPlataData;
        this.subjectUserData = subjectUserData;
        this.messageUserData = messageUserData;
        this.leido=leido;
        this.imageName = imageName;
        this.imageUri = imageUri;

        long currentDate = new Date().getTime();
        messageTime = (String) DateFormat.format("dd/MM/yyyy HH:mm",
                currentDate);


    }

    public String getUserPlateData() {
        return userPlateData;
    }

    public String getSubjectUserData() {
        return subjectUserData;
    }

    public String getMessageUserData() {
        return messageUserData;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getLeido() {
        return leido;
    }


    public String getImageName() {
        return imageName;
    }

    public String getImageUri() {
        return imageUri;
    }
}
