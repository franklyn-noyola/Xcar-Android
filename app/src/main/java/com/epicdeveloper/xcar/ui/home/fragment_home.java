package com.epicdeveloper.xcar.ui.home;


import static com.epicdeveloper.xcar.MainActivity.email_user;
import static com.epicdeveloper.xcar.MainActivity.userSelected;
import static com.epicdeveloper.xcar.R.layout.searchuser_popup;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epicdeveloper.xcar.LocaleHelper;
import com.epicdeveloper.xcar.MainActivity;
import com.epicdeveloper.xcar.R;
import com.epicdeveloper.xcar.profile_activity;
import com.epicdeveloper.xcar.ui.Chat.fragment_chat;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class fragment_home extends Fragment {

    public View root;
    public TextView user;

    public static String plate_user;
    public static String UserSel;
    public static String emailSelected;
    AdView adview;
    Context context;
    Resources resources;
    String selectedLanguage;
    SearchView searchViewField;
    View view;
    TextView carType;
    TextView carBrand;
    TextView carModel;
    TextView carColor;
    TextView carYear;
    Button closeButton;
    RelativeLayout relative;
    TextView userInfo;
    LayoutInflater inflaterView;
    Button goChat;
    TextView plateUserSearch;
    TextView carTypeTextField;
    Button sendNotification;
    TextView carBrandTextField;
    TextView carModelTextField;
    TextView carYearTextField;
    TextView carColorTextField;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getActivity(), selectedLanguage);
        resources = context.getResources();
        root= inflater.inflate(R.layout.fragment_home_fragment, container, false);
        inflaterView = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adview = root.findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        searchViewField = root.findViewById(R.id.search_View);
        searchViewField.setQueryHint(resources.getString((R.string.plate_enter)));

        //searchViewField.setIconified(false);
        //searchViewField.clearFocus();

        MainActivity.screens=1;
        getUserData();


        searchViewField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewField.onActionViewExpanded();

             }
        });

        searchViewField.setOnCloseListener(() -> {
            searchViewField.onActionViewCollapsed();
            searchViewField.clearFocus();
            return false;
        });

        searchViewField.setOnQueryTextListener(new SearchView.OnQueryTextListener()  {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                if (TextUtils.isEmpty(searchViewField.getQuery())){
                    Toast.makeText(context, resources.getString(R.string.noExists), Toast.LENGTH_SHORT).show();
                }else{
                    getInfoData(searchViewField.getQuery().toString().toUpperCase());
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


        });

        return root;
    }

  public  final TextView userPlate(){
            return user=root.findViewById(R.id.placaUserN);
    }
    @Override
    public void onPause() {
        if (adview != null) {
            adview.pause();
        }
        super.onPause();
    }
    @Override
    public void onResume() {
        if (adview != null) {
            adview.pause();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (adview != null) {
            adview.destroy();
        }
        super.onDestroy();
    }

    public void getUserData() {
        String dot1 = new String (email_user);
        String dot2 = dot1.replace(".","_");
        DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users/"+dot2);
        Users.orderByChild("user_email").equalTo(email_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        plate_user = snapshot.child("plate_user").getValue().toString();
                        UserSel = snapshot.child("user_name").getValue().toString();
                        emailSelected = snapshot.child("user_email").getValue().toString();
                    }

                }
                userPlate().setText(plate_user);
                if (plate_user.length() <= 8) {
                    userPlate().setTextSize(50);
                }
                if (plate_user.length() == 9) {
                    userPlate().setTextSize(45);
                }
                if (plate_user.length() == 10) {
                    userPlate().setTextSize(40);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    protected void getInfoData(String userToSearch) {
        if (userToSearch.equals(plate_user)) {
            Toast.makeText(context, resources.getString(R.string.sameUserSearch), Toast.LENGTH_SHORT).show();
            return;
        }
        view = inflaterView.inflate(searchuser_popup, null);
        relative = new RelativeLayout(getActivity());
        userInfo = view.findViewById(R.id.userInfo);
        if (selectedLanguage.equals("RU")){
            userInfo.setTextSize(15);
        }else{
            userInfo.setTextSize(20);
        }
        userInfo.setText(resources.getString(R.string.userInfo));
        carType = view.findViewById(R.id.carTypeText);
        carBrand = view.findViewById(R.id.carBrandText);
        carModel = view.findViewById(R.id.carModelText);
        carColor = view.findViewById(R.id.carColorText);
        carYear =  view.findViewById(R.id.carYearText);
        closeButton = view.findViewById(R.id.closebutton);
        goChat = view.findViewById(R.id.buttonGoChat);
        carBrandTextField = view.findViewById(R.id.carBrandTextField);
        carTypeTextField = view.findViewById(R.id.carTypeTextField);
        carModelTextField = view.findViewById(R.id.carModelTextField);
        carYearTextField = view.findViewById(R.id.carYearTextField);
        carColorTextField = view.findViewById(R.id.carColorTextField);
        plateUserSearch = view.findViewById(R.id.placaUserSearch);
        sendNotification = view.findViewById(R.id.buttonSendNoti);
        goChat.setText(resources.getString(R.string.gotChat));
        sendNotification.setText(resources.getString(R.string.sendNoti));
        closeButton.setText(resources.getString(R.string.closeButton));
        plateUserSearch.setText(userToSearch);

        goChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.chatUser = searchViewField.getQuery().toString().toUpperCase();
                MainActivity.chatScreen = 1;
                Intent intent = new Intent (getActivity(), fragment_chat.class);
                startActivity(intent);
            }
        });

        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.chatUser = searchViewField.getQuery().toString().toUpperCase();
                MainActivity.chatScreen = 1;
                Intent intent = new Intent (getActivity(), com.epicdeveloper.xcar.ui.sendNotifications.sendNotification.class);
                startActivity(intent);
            }
        });

        DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        Users.orderByChild("plate_user").equalTo(userToSearch).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String typeProfileCarUser="";
                    String brandProfileCarUser="";
                    String modelProfileCaruser="";
                    String colorProfileCarUser="";
                    String yearProfileCarUser="";
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        typeProfileCarUser = snapshot.child("cartype").getValue().toString();
                        brandProfileCarUser = snapshot.child("carbrand").getValue().toString();
                        modelProfileCaruser = snapshot.child("carmodel").getValue().toString();
                        colorProfileCarUser = snapshot.child("carcolor").getValue().toString();
                        yearProfileCarUser = snapshot.child("year").getValue().toString();
                    }
                    final PopupWindow pw = new PopupWindow(view, 1200 , 1000, true);
                    if (TextUtils.isEmpty(typeProfileCarUser)) {
                        carType.setText(resources.getString(R.string.typeVehicle)+": ");
                        carTypeTextField.setText(resources.getString(R.string.noData));
                    }else{

                        carType.setText(resources.getString(R.string.typeVehicle)+": ");
                        carTypeTextField.setText(typeProfileCarUser);

                    }
                    if (TextUtils.isEmpty(brandProfileCarUser)){
                        carBrand.setText(resources.getString(R.string.brandHint)+":");
                        carBrandTextField.setText(resources.getString(R.string.noData));
                    }else{
                        carBrand.setText(resources.getString(R.string.brandHint)+":");
                        carBrandTextField.setText(brandProfileCarUser);
                    }
                    if (TextUtils.isEmpty(modelProfileCaruser)){
                        carModel.setText(resources.getString(R.string.modelHint)+":");
                        carModelTextField.setText(resources.getString(R.string.noData));
                    }else{
                        carModel.setText(resources.getString(R.string.modelHint)+":");
                        carModelTextField.setText(modelProfileCaruser);
                    }
                    if (TextUtils.isEmpty(colorProfileCarUser)){
                        carColor.setText(resources.getString(R.string.colorHint)+":");
                        carColorTextField.setText(resources.getString(R.string.noData));
                    }else{
                        carColor.setText(resources.getString(R.string.colorHint)+":");
                        carColorTextField.setText(colorProfileCarUser);
                    }
                    if (TextUtils.isEmpty(yearProfileCarUser)){
                        carYear.setText(resources.getString(R.string.yearHint)+":");
                        carYearTextField.setText(resources.getString(R.string.noData));
                    }else{
                        carYear.setText(resources.getString(R.string.yearHint)+":");
                        carYearTextField.setText(yearProfileCarUser);
                    }
                    pw.showAtLocation(relative, Gravity.NO_GRAVITY, 150, 300);
                    pw.setOutsideTouchable(false);
                    pw.setFocusable(true);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pw.setFocusable(false);
                            pw.dismiss();
                            searchViewField.clearFocus();
                            searchViewField.setQuery("",false);
                        }
                    });

                }else{
                    Toast.makeText(getActivity(), resources.getString(R.string.noExists), Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}