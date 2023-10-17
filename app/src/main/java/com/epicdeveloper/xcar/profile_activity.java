package com.epicdeveloper.xcar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.epicdeveloper.xcar.ui.Chat.chatMainScreen;
import com.epicdeveloper.xcar.ui.Perfil.fragment_profile;
import com.epicdeveloper.xcar.ui.home.fragment_home;
import com.epicdeveloper.xcar.ui.receivedNotifications.receivedNotifications;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import java.util.Objects;


public class profile_activity extends AppCompatActivity {

    private int noReadCount;
    private int countNoRead;
    public static String selectedLanguage;
    private boolean noReadNoti=false;
    final int homeMenu = R.id.Home_Menu;
    final int chatMenu = R.id.Chat_Menu;
    final int profileMenu = R.id.Profile_Menu;
    final int notificationsMenu = R.id.Notifications_Menu;
    final int contactsAction = R.id.action_Contacts;

    final int location = R.id.location;
    final int aboutAction = R.id.action_About;

    final int addPlate = R.id.add_newPlate;
    final int addPlateAction = R.id.add_newPlate;
    final int settingsAction = R.id.action_Settings;
    final int howitWorksAction = R.id.action_HowitWorks;
    final int shareAction = R.id.action_Share;
    final int logoutAction = R.id.action_logout;
    Context context;
    Resources resources;
    Menu menu;
    Menu barNavigation;
    public static String mToken;
    BottomNavigationView bottomNavigationView;

        EditText brandCarField, modelCarField, colorCarField, yearCarField, additionalPlate;

    Spinner carSelected;

    TextView addPlateLabel, infoLbl;

    AdView adview;

    private DatabaseReference addPlateDB;
    Button verifyButton, addPlateButton, cancelButton;


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
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        MainActivity.getInit = 1;
        MainActivity.getProfileView = 2;
        setContentView(R.layout.activity_profile);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        barNavigation = bottomNavigationView.getMenu();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(resources.getString(R.string.home_menu));
        getNotifications();



         bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case homeMenu:
                     FragmentManager manager = getSupportFragmentManager();
                     FragmentTransaction transaction = manager.beginTransaction();
                     Fragment fm = new fragment_home();
                     transaction.add(R.id.home_fragment, fm);
                     transaction.commit();
                     item.setTitle(resources.getString(R.string.home_menu));
                     Objects.requireNonNull(getSupportActionBar()).setTitle(resources.getString(R.string.home_menu));
                     return true;

                case chatMenu:
                     manager = getSupportFragmentManager();
                     transaction = manager.beginTransaction();
                     fm = new chatMainScreen();
                     transaction.replace(R.id.home_fragment, fm);
                     transaction.commit();
                     item.setTitle(resources.getString(R.string.chat_menu));
                     Objects.requireNonNull(getSupportActionBar()).setTitle(resources.getString(R.string.chat_menu));
                     return true;

                 case profileMenu:
                    manager = getSupportFragmentManager();
                     transaction = manager.beginTransaction();
                     fm = new fragment_profile();
                     transaction.replace(R.id.home_fragment, fm);
                     transaction.commit();
                     item.setTitle(resources.getString(R.string.profile_menu));
                     Objects.requireNonNull(getSupportActionBar()).setTitle(resources.getString(R.string.profile_menu));
                     return true;

                 case notificationsMenu:
                     item.setTitle(resources.getString(R.string.receivedNotifications));
                     Objects.requireNonNull(getSupportActionBar()).setTitle(resources.getString(R.string.receivedNotifications));
                     manager = getSupportFragmentManager();
                     transaction = manager.beginTransaction();
                     fm = new receivedNotifications();
                     transaction.replace(R.id.home_fragment, fm);
                     transaction.commit();
                     item.setTitle(resources.getString(R.string.receivedNotifications));
                     return true;

             }
             return true;
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
        menu.findItem(R.id.location);
        menu.findItem(R.id.add_newPlate);

        return true;
    }

     @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case contactsAction:
              Intent intent = new Intent(this, contacto.class);
                startActivity(intent);
                return true;
            case R.id.add_newPlate:
                addPlate();
                return true;
            case aboutAction:
                intent = new Intent(this, acercade.class);
                startActivity(intent);
                return true;
            case settingsAction:
                intent = new Intent(this, settings.class);
                startActivity(intent);
                return true;
            case howitWorksAction:
                intent = new Intent(this, howItWorks.class);
                startActivity(intent);
                return true;
            case shareAction:
                String shareLink = "https://play.google.com/store/apps/details?id=com.epicdeveloper.allconnected";
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, shareLink);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, resources.getString(R.string.shareFriend)));
                return true;
            case logoutAction:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertDialog alert = new AlertDialog.Builder(this).create();

           alert.setMessage(resources.getString(R.string.logout));
        alert.setButton(AlertDialog.BUTTON_POSITIVE,resources.getString(R.string.yes), (dialogInterface, i) -> {
            getInterfaceID();
            finish();
            MainActivity.getInit=0;
            MainActivity.getProfileView=1;
            MainActivity.init = 0;
            MainActivity.profileView=1;
        });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE,resources.getString(R.string.No), (DialogInterface.OnClickListener) null);
        alert.show();
        TextView alertMessage = alert.findViewById(android.R.id.message);
        TextView alertTitle = alert.findViewById(android.R.id.title);
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
                        if (Objects.requireNonNull(dataSnapshot.child("leido").getValue()).toString().equals("No")) {
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
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                Log.w("TAG", "Not able to get token", task.getException());
                return;
            }
            mToken = task.getResult();
            upDateSessionActive(mToken);
        });
    }

    private void upDateSessionActive(String mToken) {
        DatabaseReference sessionActiveDb = FirebaseDatabase.getInstance().getReference("activeSession/"+mToken);
        sessionActiveDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()){
                    Map<String, Object> updates = new HashMap<>();
                    for (DataSnapshot ignored :snapshot.getChildren()){
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

    public void addPlate() {
        setContentView(R.layout.additional_plate);
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getApplication(), selectedLanguage);
        resources = context.getResources();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
        getSupportActionBar().setTitle(resources.getString(R.string.addPlateButton));
        adview = findViewById(R.id.adViewAdd);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        carSelected = findViewById(R.id.vehiclePlate);
        carSelected.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.Vehiculos)));
        infoLbl = findViewById(R.id.infoLblAdd);
        if (selectedLanguage.equals("RU")) {
            infoLbl.setTextSize(20);
        }
        infoLbl.setText(resources.getString(R.string.additionalInfor));
        addPlateLabel = findViewById(R.id.additionalPlate);
        addPlateLabel.setText(resources.getString(R.string.addPlate));
        additionalPlate = findViewById(R.id.addPlateField);
        brandCarField = findViewById(R.id.carBrandAdd);
        brandCarField.setHint(resources.getString(R.string.brandHint));
        modelCarField = findViewById(R.id.carModelAdd);
        modelCarField.setHint(resources.getString(R.string.modelHint));
        verifyButton = findViewById(R.id.verify);
        addPlateButton = findViewById(R.id.addPlateButton);
        addPlateButton.setHint(resources.getString(R.string.addPlateButton));
        colorCarField = findViewById(R.id.carColorAdd);
        colorCarField.setHint(resources.getString(R.string.colorHint));
        yearCarField = findViewById(R.id.carYearAdd);
        yearCarField.setHint(resources.getString(R.string.yearHint));
        cancelButton = findViewById(R.id.cancelButtonAdd);
        cancelButton.setHint(resources.getString(R.string.cancel));
    }


}