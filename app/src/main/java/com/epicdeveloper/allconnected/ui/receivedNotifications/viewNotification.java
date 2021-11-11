package com.epicdeveloper.allconnected.ui.receivedNotifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.epicdeveloper.allconnected.LocaleHelper;
import com.epicdeveloper.allconnected.MainActivity;
import com.epicdeveloper.allconnected.R;
import com.epicdeveloper.allconnected.profile_activity;
import com.epicdeveloper.allconnected.ui.Chat.fragment_chat;
import com.epicdeveloper.allconnected.ui.Chat.fullViewImage;
import com.epicdeveloper.allconnected.ui.sendNotifications.sendNotification;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Quota;

public class viewNotification extends AppCompatActivity {

    TextView notiUser;
    TextView notiTime;
    TextView notiSubject;
    TextView notiBody;
    TextView imageName;
    Button btngoChat;
    Button btnReply;
    Button btnDeleteNotification;
    String selectedLanguage;
    Context context;
    Resources resources;

    DatabaseReference viewNotification;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewnotification);
        MainActivity.chatScreen = 3;
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(viewNotification.this, selectedLanguage);
        resources = context.getResources();
        notiUser = findViewById(R.id.userTarget);
        notiTime =  findViewById(R.id.timieNoti);
        notiSubject = findViewById(R.id.notiSubject);
        notiBody = findViewById(R.id.bodyNoti);
        imageName = findViewById(R.id.imageView);
        btnReply = findViewById(R.id.replyButton);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
        getSupportActionBar().setTitle(receivedNotifications.readTargetUser+"-"+receivedNotifications.readSubject);
        String userText = resources.getString(R.string.from)+" " + "<br><b>" + receivedNotifications.readTargetUser + "<\b>";
        String subjectText = resources.getString(R.string.subject)+" " + "<br>" + receivedNotifications.readSubject;
        String bodytText = resources.getString(R.string.message)+" " + "<br>" + receivedNotifications.readMessage;
        Spanned userHtml = HtmlCompat.fromHtml(userText, HtmlCompat.FROM_HTML_MODE_COMPACT);
        Spanned subjectHtml = HtmlCompat.fromHtml(subjectText, HtmlCompat.FROM_HTML_MODE_COMPACT);
        Spanned bodyHtml = HtmlCompat.fromHtml(bodytText, HtmlCompat.FROM_HTML_MODE_COMPACT);
        if (TextUtils.equals(receivedNotifications.imageName,"")){
            imageName.setVisibility(View.GONE);
        }else{
            imageName.setVisibility(View.VISIBLE);
            imageName.setText(receivedNotifications.imageName);
        }
        notiUser.setText(userHtml);
        notiTime.setText(receivedNotifications.readMessageTime);
        notiSubject.setText(subjectHtml);
        notiBody.setText(bodyHtml);
        btngoChat = findViewById(R.id.goChatButton);
        btnDeleteNotification = findViewById(R.id.deleteButton);
        btnDeleteNotification.setText(resources.getString(R.string.delete));
        btngoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnNotificationList();
                MainActivity.chatUser = receivedNotifications.readTargetUser;
                Intent intent = new Intent(getApplicationContext(), fragment_chat.class);
                startActivity(intent);
            }
        });

        btngoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnNotificationList();
                MainActivity.chatUser = receivedNotifications.readTargetUser;
                Intent intent = new Intent(getApplicationContext(), fragment_chat.class);
                startActivity(intent);
            }
        });

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnNotificationList();
                MainActivity.chatUser = receivedNotifications.readTargetUser;
                Intent intent = new Intent(getApplicationContext(), sendNotification.class);
                startActivity(intent);
            }
        });
        btnDeleteNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotification();
            }
        });

            imageName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), fullViewImageNoti.class);
                    startActivity(intent);

                }
            });

    }

    public void deleteNotification(){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setMessage(resources.getString(R.string.deleteChatMsg));
        alert.setButton(AlertDialog.BUTTON_POSITIVE,resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmDeleteNotificacion();
                Toast.makeText(getApplicationContext(), resources.getString(R.string.notificationDeleted), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE,resources.getString(R.string.No), (DialogInterface.OnClickListener) null);
        alert.show();
        TextView alertMessage = (TextView) alert.findViewById(android.R.id.message);
        TextView alertTitle = (TextView) alert.findViewById(android.R.id.title);
        if (alertTitle!=null) {
            alertTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        Button alertYes = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button alertNos = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams positive = (LinearLayout.LayoutParams) alertYes.getLayoutParams();
        positive.weight = 20;
        alertMessage.setGravity(Gravity.CENTER);
        alertNos.setTextSize(15);
        alertYes.setTextSize(15);
        alertYes.setLayoutParams(positive);
        alertNos.setLayoutParams(positive);

    }


    public void returnNotificationList() {
        viewNotification = FirebaseDatabase.getInstance().getReference("sendMessages/" + MainActivity.plateUser);
        Query getData = viewNotification.orderByChild("messageTime").equalTo(receivedNotifications.readMessageTime);
        getData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, Object> updates =  new HashMap<String, Object>();
                updates.put("leido", "Si");
                snapshot.getRef().updateChildren(updates);
                }

                @Override
                public void onChildChanged (@NonNull DataSnapshot snapshot, @Nullable String
                previousChildName){

                }

                @Override
                public void onChildRemoved (@NonNull DataSnapshot snapshot){

                }

                @Override
                public void onChildMoved (@NonNull DataSnapshot snapshot, @Nullable String
                previousChildName){

                }

                @Override
                public void onCancelled (@NonNull DatabaseError error){

                }
            });
    }
    @Override
    public void onBackPressed(){
        returnNotificationList();
        MainActivity.chatScreen = 0;
        if (MainActivity.fromNotifications == 2) {
            MainActivity.fromNotifications = 0;
            finishAffinity();
        }
        super.onBackPressed();
    }

    private void confirmDeleteNotificacion(){
        viewNotification = FirebaseDatabase.getInstance().getReference("sendMessages");
        Query query = viewNotification.child(MainActivity.plateUser).orderByChild("messageTime").equalTo(receivedNotifications.itemgetTime);
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        snapshot.getRef().setValue(null);

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


}
