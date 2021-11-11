package com.epicdeveloper.allconnected.ui.Salir;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.epicdeveloper.allconnected.LocaleHelper;
import com.epicdeveloper.allconnected.MainActivity;
import com.epicdeveloper.allconnected.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import com.epicdeveloper.allconnected.ResetPass;
import com.epicdeveloper.allconnected.profile_activity;
import com.epicdeveloper.allconnected.ui.Chat.fragment_chat;
import com.epicdeveloper.allconnected.ui.Perfil.fragment_profile;
import com.epicdeveloper.allconnected.ui.home.fragment_home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.*;
import com.google.firebase.iid.*;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class fragment_salir extends Fragment {
    public static View root;
    public static TextView textView;
    public static View view;
    public static String mToken;
    String selectedLanguage;
    Context context;
    Resources resources;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selectedLanguage=MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getActivity(), selectedLanguage);
        resources = context.getResources();

        if (MainActivity.screens == 1) {
            Fragment fm = new fragment_home();
            FragmentManager fr = getFragmentManager();
            FragmentTransaction fT = fr.beginTransaction();
            fT.add(R.id.home_fragment, fm);
            fT.commit();
            root=inflater.inflate(R.layout.fragment_home_fragment, null, false);
        }
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();

                alert.setMessage(resources.getString(R.string.logout));
                alert.setButton(AlertDialog.BUTTON_POSITIVE,resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getInterfaceID();
                        getActivity().finish();
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
        return root;
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