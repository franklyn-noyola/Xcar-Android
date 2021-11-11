package com.epicdeveloper.allconnected;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.epicdeveloper.allconnected.ui.Chat.chatMainScreen;
import com.epicdeveloper.allconnected.ui.Perfil.fragment_profile;
import com.epicdeveloper.allconnected.ui.Salir.fragment_salir;
import com.epicdeveloper.allconnected.ui.home.fragment_home;
import com.epicdeveloper.allconnected.ui.receivedNotifications.receivedNotifications;
import com.epicdeveloper.allconnected.ui.sendNotifications.sendNotification;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class profile_activity extends AppCompatActivity {

    private int noReadCount;
    private int countNoRead;
    public static String selectedLanguage;
    private boolean noReadNoti=false;
    Context context;
    Resources resources;
    Menu menu;
    Menu barNavigation;
    public static String mToken;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.fromNotifications = 0;
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(profile_activity.this, selectedLanguage);
        resources = context.getResources();
        Locale locale = new Locale(selectedLanguage);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        MainActivity.getInit = 1;
        MainActivity.getProfileView = 2;
        setContentView(R.layout.activity_profile);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        barNavigation = bottomNavigationView.getMenu();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(resources.getString(R.string.home_menu));
        getNotifications();


         bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()) {
                    case R.id.Home_Menu:
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Fragment fm = new fragment_home();
                        transaction.add(R.id.home_fragment, fm);
                        transaction.commit();
                        item.setTitle(resources.getString(R.string.home_menu));
                        getSupportActionBar().setTitle(resources.getString(R.string.home_menu));
                        return true;

                    case R.id.Chat_Menu:
                        manager = getSupportFragmentManager();
                        transaction = manager.beginTransaction();
                        fm = new chatMainScreen();
                        transaction.replace(R.id.home_fragment, fm);
                        transaction.commit();
                        item.setTitle(resources.getString(R.string.chat_menu));
                        getSupportActionBar().setTitle(resources.getString(R.string.chat_menu));
                        return true;

                    case R.id.Profile_Menu:
                       manager = getSupportFragmentManager();
                        transaction = manager.beginTransaction();
                        fm = new fragment_profile();
                        transaction.replace(R.id.home_fragment, fm);
                        transaction.commit();
                        item.setTitle(resources.getString(R.string.profile_menu));
                        getSupportActionBar().setTitle(resources.getString(R.string.profile_menu));
                        return true;

                    case R.id.Notifications_Menu:
                        item.setTitle(resources.getString(R.string.receivedNotifications));
                        getSupportActionBar().setTitle(resources.getString(R.string.receivedNotifications));
                        manager = getSupportFragmentManager();
                        transaction = manager.beginTransaction();
                        fm = new receivedNotifications();
                        transaction.replace(R.id.home_fragment, fm);
                        transaction.commit();
                        item.setTitle(resources.getString(R.string.receivedNotifications));
                        return true;

                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu2, menu);
        this.menu = menu;
        menu.findItem(R.id.action_Contacts).setTitle(resources.getString(R.string.action_contacts));
        menu.findItem(R.id.action_logout).setTitle(resources.getString(R.string.logout_menu));
        menu.findItem(R.id.action_Settings).setTitle(resources.getString(R.string.action_settings));
        menu.findItem(R.id.action_HowitWorks).setTitle(resources.getString(R.string.action_howItWorks));
        menu.findItem(R.id.action_About).setTitle(resources.getString(R.string.action_about));
        menu.findItem(R.id.action_Share).setTitle(resources.getString(R.string.shareWith));
        return true;
    }

     @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.action_Contacts:
                Intent intent = new Intent(this, contacto.class);
                startActivity(intent);
                return true;
            case R.id.action_About:
                intent = new Intent(this, acercade.class);
                startActivity(intent);
                return true;
            case R.id.action_Settings:
                intent = new Intent(this, settings.class);
                startActivity(intent);
                return true;
            case R.id.action_HowitWorks:
                intent = new Intent(this, howItWorks.class);
                startActivity(intent);
                return true;
            case R.id.action_Share:
                String shareLink = "https://play.google.com/store/apps/details?id=com.epicdeveloper.allconnected";
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, shareLink);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, resources.getString(R.string.shareFriend)));
                return true;
            case R.id.action_logout:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertDialog alert = new AlertDialog.Builder(this).create();

           alert.setMessage(resources.getString(R.string.logout));
        alert.setButton(AlertDialog.BUTTON_POSITIVE,resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getInterfaceID();
                finish();
                MainActivity.getInit=0;
                MainActivity.getProfileView=1;
                MainActivity.init = 0;
                MainActivity.profileView=1;
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

    public void getNotifications(){
        final DatabaseReference getNoLeidos = FirebaseDatabase.getInstance().getReference("sendMessages").child(MainActivity.plateUser.toUpperCase());
        getNoLeidos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if (dataSnapshot.child("leido").getValue().toString().equals("No")) {
                            countNoRead = countNoRead + 1;
                        }
                    }
                    noReadCount=countNoRead;
                    countNoRead = 0;
                    noReadNoti=true;
                }else{
                    noReadNoti=false;
                }
                if (noReadCount >=1) {
                    noReadNoti=true;
                }else {
                    noReadNoti=false;
                }
                if (noReadNoti) {
                    barNavigation.findItem(R.id.Notifications_Menu).setIcon(R.drawable.ic_email_unread_24);
                } else  {
                    barNavigation.findItem(R.id.Notifications_Menu).setIcon(R.drawable.ic_email_read);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if (MainActivity.init==1){
            MainActivity.profileView=1;
            MainActivity.getInit=1;
            MainActivity.getProfileView=2;
        }else{
            MainActivity.init=0;
        }


    }

    public void getInterfaceID() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()){
                    Log.w("TAG", "Not able to get token", task.getException());
                    return;
                }
                mToken = task.getResult();
                upDateSessionActive(mToken, "OFF", MainActivity.plateUser);
            }
        });
    }

    private void upDateSessionActive(String mToken, final String active, final String activeUser) {
        DatabaseReference sessionActiveDb = FirebaseDatabase.getInstance().getReference("activeSession/"+mToken);
        sessionActiveDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    Map<String, Object> updates = new HashMap<String, Object>();
                    for (DataSnapshot ds:snapshot.getChildren()){
                        updates.put("active", "OFF");
                    }

                    snapshot.getRef().updateChildren(updates);
                }


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


}