package com.epicdeveloper.allconnected;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class howItWorks extends AppCompatActivity {
    TextView profile,chat,sendText,receiveText,about,setting,contact;
    TextView profileText,chatText,sendTextLbl,receiveTextLbl,aboutText,settingText,contactText;
    String selectedLanguage;
    Context context;
    Resources resources;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howitworks);
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getApplication(), selectedLanguage);
        resources = context.getResources();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
        getSupportActionBar().setTitle(resources.getString(R.string.action_howItWorks));
        profile = (TextView) findViewById(R.id.profile);
        chat = (TextView) findViewById(R.id.chat);
        sendText = (TextView) findViewById(R.id.sendText);
        receiveText = (TextView) findViewById(R.id.receiveText);
        about = (TextView) findViewById(R.id.about);
        setting = (TextView) findViewById(R.id.setting);
        contact = (TextView) findViewById(R.id.contact);
        profileText = (TextView) findViewById(R.id.profileText);
        chatText = (TextView) findViewById(R.id.chatText);
        sendTextLbl = (TextView) findViewById(R.id.sendTextLbl);
        receiveTextLbl = (TextView) findViewById(R.id.receiveTextLbl);
        aboutText = (TextView) findViewById(R.id.aboutText);
        settingText = (TextView) findViewById(R.id.settingText);
        contactText = (TextView) findViewById(R.id.contactText);

        profile.setText(resources.getString(R.string.profile_menu));
        chat.setText(resources.getString(R.string.chat_menu));
        sendText.setText(resources.getString(R.string.sendNoti));
        receiveText.setText(resources.getString(R.string.receiveNotification));
        about.setText(resources.getString(R.string.action_about));
        setting.setText(resources.getString(R.string.action_settings));
        contact.setText(resources.getString(R.string.action_contacts));

        profileText.setText(resources.getString(R.string.profileText));
        chatText.setText(resources.getString(R.string.chatText));
        sendTextLbl.setText(resources.getString(R.string.sendNotiText));
        receiveTextLbl.setText(resources.getString(R.string.receiveText));
        aboutText.setText(resources.getString(R.string.aboutText));
        settingText.setText(resources.getString(R.string.settingText));
        contactText.setText(resources.getString(R.string.contactText));

    }
}