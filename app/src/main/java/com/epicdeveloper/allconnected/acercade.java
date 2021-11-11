package com.epicdeveloper.allconnected;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.epicdeveloper.allconnected.ui.Chat.chatMainScreen;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class acercade extends AppCompatActivity {
    public static String getVersion;
    AdView adview;
    String selectedLanguage;
    Context context;
    Resources resources;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acercade);
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getApplication(), selectedLanguage);
        resources = context.getResources();
        getSupportActionBar().setTitle(resources.getString(R.string.action_about));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
        adview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        setupLinkButton();
        TextView version = (TextView) findViewById(R.id.version);
        try {
            getVersion = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version.setText(resources.getString(R.string.version)+": "+getVersion);
    }

    private void setupLinkButton() {
        TextView linkButton = (TextView) findViewById(R.id.epicPage);
        linkButton.setTextColor(Color.BLUE);
        linkButton.setMovementMethod(LinkMovementMethod.getInstance());
        linkButton.setLinkTextColor(Color.BLUE);
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.epicdevelopers.app"));
                startActivity(intent);
            }
        });

    }
}
