package com.epicdeveloper.xcar.ui.receivedNotifications;

public class receivedNotificationsData {
    private String userPlateData;
    private String subjectUserData;
    private String messageUserData;
    private String leido;
    private String messageTime;
    private String imageName;
    private String imageUri;

    public receivedNotificationsData(){

    }


    public receivedNotificationsData(String userPlateData, String subjectUserData, String messageUserData, String leido, String messageTime, String imageName, String imageUri){
        this.userPlateData = userPlateData;
        this.subjectUserData = subjectUserData;
        this.messageUserData = messageUserData;
        this.leido=leido;
        this.messageTime=messageTime;
        this.imageName = imageName;
        this.imageUri = imageUri;

    }

    public String getuserPlateData() {
        return userPlateData;
    }

    public String getsubjectUserData() {
        return subjectUserData;
    }

    public String getmessageUserData() {
        return messageUserData;
    }

    public String getLeido() {
        return leido;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageUri() {
        return imageUri;
    }

}
