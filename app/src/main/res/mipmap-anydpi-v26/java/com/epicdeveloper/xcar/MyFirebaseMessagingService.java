package com.epicdeveloper.xcar;


import static com.epicdeveloper.xcar.MainActivity.getBackGround;
import static com.epicdeveloper.xcar.MainActivity.plateUser;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.epicdeveloper.xcar.ui.Chat.chatMainScreen;
import com.epicdeveloper.xcar.ui.Chat.fragment_chat;
import com.epicdeveloper.xcar.ui.receivedNotifications.receivedNotifications;
import com.epicdeveloper.xcar.ui.receivedNotifications.viewNotification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static Class classToClick;
    private static String notiUser;
    public static String bodyNoti, imageName, imageUri;
    public static String timeNoti;
    public static Bitmap image;
    public Intent intent;
    public static String language;
    public int idNotify;

    String mToken;


    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        Log.d(TAG, "From: " + from);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "El Titulo es: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "El mensaje es: " + remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            String titulo = remoteMessage.getData().get("titulo");
            String detalle = remoteMessage.getData().get("detalle");
            String imageUri = remoteMessage.getData().get("image");

            image = getBitmapfromUrl(imageUri);

            getSessionActive(titulo, detalle, image);
        }

    }

    private Bitmap getBitmapfromUrl(String imageUri) {
        try {
            URL url = new URL(imageUri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

    }

    private PendingIntent clickOnNoti(Intent intent) {
        intent.putExtra("color", "rojo");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent;
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }else {
            pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }*/
        return  PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    private void getNotificationData(String subject, String plateUser, String notiUser, final FirebaseCallBack firebaseCallBack) {
        DatabaseReference notification = FirebaseDatabase.getInstance().getReference("receivedMessage/"+notiUser+"/"+plateUser);
        notification.orderByChild("subjectUserData").equalTo(subject).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds:snapshot.getChildren()) {
                        bodyNoti = ds.child("messageUserData").getValue().toString();
                        timeNoti = (ds.child("messageTime").getValue().toString());
                        imageName = ds.child("imageName").getValue().toString();
                        imageUri = ds.child("imageUri").getValue().toString();
                    }
                    firebaseCallBack.onCallback(bodyNoti,timeNoti,imageName,imageUri);
                } else {
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    public void getSessionActive(final String titulo, final String detalle, Bitmap image){
        final String id = "Mensaje";
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()){
                        Log.w("TAG", "Not able to get token", task.getException());
                        return;
                    }
                    mToken = task.getResult();
                 DatabaseReference sessionActive = FirebaseDatabase.getInstance().getReference("activeSession/" + mToken);
                sessionActive.orderByChild("active").equalTo("ON").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                            plateUser = ds.child("activeUser").getValue().toString();
                        }
                            if (!TextUtils.isEmpty(plateUser)) {
                                if (titulo.contains("Chat")) {
                                    String chat = fragment_chat.class.getCanonicalName();
                                    idNotify=0;
                                    MainActivity.fromNotifications = 1;
                                    MainActivity.chatScreen = 1;
                                    try {
                                        chatMainScreen.userToChat = titulo.substring(6);
                                        classToClick = Class.forName(chat);
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (titulo.contains("Notificación")) {
                                    String chat = viewNotification.class.getCanonicalName();
                                    MainActivity.fromNotifications = 2;
                                    idNotify=1;
                                    try {
                                        notiUser = titulo.substring(14);
                                        getNotificationData(detalle, plateUser, notiUser, new FirebaseCallBack() {
                                            @Override
                                            public void onCallback(String subject, String time, String image, String uri) {
                                                receivedNotifications.readTargetUser = titulo.substring(14);
                                                receivedNotifications.readSubject=detalle;
                                                receivedNotifications.readMessage = bodyNoti;
                                                receivedNotifications.readMessageTime = timeNoti;
                                                receivedNotifications.itemgetTime = timeNoti;
                                                receivedNotifications.imageName = imageName;
                                                receivedNotifications.imageViewName = imageUri;
                                            }
                                        });

                                        classToClick = Class.forName(chat);
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                MainActivity.getDataUserSession(plateUser);
                                MainActivity.userSelected = plateUser.toUpperCase();
                                MainActivity.init = 1;
                               } else {
                                String chat = MainActivity.class.getCanonicalName();
                                try {
                                    notiUser = titulo.substring(14);
                                    classToClick = Class.forName(chat);
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            String chat = MainActivity.class.getCanonicalName();
                            try {
                                notiUser = titulo.substring(14);
                                classToClick = Class.forName(chat);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                        MainActivity.chatUser = chatMainScreen.userToChat;
                        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
                        ActivityManager.getMyMemoryState(myProcess);
                        Boolean isInBackGround = myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;


                        if (isInBackGround && getBackGround == 0) {
                            DatabaseReference getLanguage = FirebaseDatabase.getInstance().getReference("Users/userLanguage");
                            getLanguage.orderByChild("users").equalTo(mToken).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String lang = "";
                                    String lang2 = "";
                                    if (snapshot.exists()) {

                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            lang = ds.child("language").getValue().toString();
                                        }
                                        lang2 = lang;
                                    } else {
                                        lang2 = Locale.getDefault().getLanguage().toUpperCase();
                                    }
                                    MainActivity.userlanguage = lang2;
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            intent = new Intent(getApplicationContext(), classToClick);
                        }
                        if (!isInBackGround)  {
                            MainActivity.fromNotifications = 0;
                            intent = new Intent(getApplicationContext(), classToClick);
                        }

                        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), id);
                        NotificationChannel nc = new NotificationChannel(id, "Nuevo", NotificationManager.IMPORTANCE_HIGH);
                        nc.setShowBadge(true);
                        AudioAttributes att = new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build();
                        nc.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),att);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                        r.play();
                        assert nm != null;
                        nm.createNotificationChannel(nc);

                        builder.setAutoCancel(true)
                                .setWhen(System.currentTimeMillis())
                                .setContentTitle(titulo)

                                .setSmallIcon(R.drawable.logoconected)
                                         .setContentInfo("Nuevo")
                                         .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                         .setVibrate(new long[]{0,300,200,300})
                                         .setContentIntent(clickOnNoti(intent));


                        if (detalle.equals("Foto")) {
                                builder.setStyle(new NotificationCompat.BigPictureStyle()
                                                .bigPicture(image));
                            }else{
                                        builder.setContentText(detalle);

                        }
                        assert nm != null;
                        nm.notify(idNotify, builder.build());
                         }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    private interface FirebaseCallBack{
        void onCallback(String subject, String time, String image, String uri);
    }

}