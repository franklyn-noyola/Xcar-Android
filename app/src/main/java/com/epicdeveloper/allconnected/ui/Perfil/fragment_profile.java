package com.epicdeveloper.allconnected.ui.Perfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.epicdeveloper.allconnected.LocaleHelper;
import com.epicdeveloper.allconnected.MainActivity;
import com.epicdeveloper.allconnected.R;
import com.epicdeveloper.allconnected.profile_activity;
import com.epicdeveloper.allconnected.ui.LoginErrorsValidation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epicdeveloper.allconnected.R.layout.changepass;

public class fragment_profile extends Fragment {
    TextView userSelected, plateSelected,  emailSelected, infoLbl;
    String selectedLanguage;
    EditText brandCarField, modelCarField, colorCarField, yearCarField, newPassField, confirmPassField;
    public List<String> cartypeSelected;
    Spinner carTypeField;
    TextView carTypeFieldLbl;
    Button btnModiInfoButton, btnchangePassButton, btnCancelPassButton, btnUpdatePass, btnCancelModInfo;
    public  Object typeProfileCarUser;
    public  Object brandProfileCarUser;
    public  Object modelProfileCaruser;
    public  Object colorProfileCarUser;
    public  Object yearProfileCarUser;
    public View Profile;
    Context context;
    Resources resources;
    View view;
    RelativeLayout relative;
    private LayoutInflater inflaterView;

    AdView adview;
    private DatabaseReference Users;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getActivity(), selectedLanguage);
        resources = context.getResources();
        Profile = inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        inflaterView = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adview = Profile.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        cartypeSelected = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Vehiculos)));
        infoLbl = Profile.findViewById(R.id.infoLbl);
        if (selectedLanguage.equals("RU")) {
            infoLbl.setTextSize(20);
        }
        infoLbl.setText(resources.getString(R.string.additionalInfor));
        carTypeFieldLbl = Profile.findViewById(R.id.vehicleTypeLbl);
        userSelected = Profile.findViewById(R.id.userDataLbl);
        plateSelected = Profile.findViewById(R.id.plateDataLbl);
        emailSelected = Profile.findViewById(R.id.emailDataLbl);
        carTypeField = Profile.findViewById(R.id.vehicleType);
        carTypeField.setEnabled(false);
        carTypeField.setVisibility(View.INVISIBLE);
        carTypeFieldLbl.setHint(resources.getString(R.string.typeVehicle));
        brandCarField = Profile.findViewById(R.id.carBrand);
        brandCarField.setHint(resources.getString(R.string.brandHint));
        modelCarField = Profile.findViewById(R.id.carModel);
        modelCarField.setHint(resources.getString(R.string.modelHint));
        btnCancelModInfo = Profile.findViewById(R.id.cancelInfo);
        btnCancelModInfo.setHint(resources.getString(R.string.cancel));
        colorCarField = Profile.findViewById(R.id.carColor);
        colorCarField.setHint(resources.getString(R.string.colorHint));
        yearCarField = Profile.findViewById(R.id.carYear);
        yearCarField.setHint(resources.getString(R.string.yearHint));
        btnModiInfoButton = Profile.findViewById(R.id.ModInfo);
        btnModiInfoButton.setHint(resources.getString(R.string.modifyInfo));
        btnchangePassButton = Profile.findViewById(R.id.modPass);
        btnchangePassButton.setHint(resources.getString(R.string.changePassHint));
        userSelected.setText(MainActivity.UserSel);
        plateSelected.setText(MainActivity.userSelected);
        emailSelected.setText(MainActivity.emailSelected);
        getInfoData();

        btnModiInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataInfo();
            }
        });

        btnchangePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        btnCancelModInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelModInfo.setVisibility(View.GONE);
                btnModiInfoButton.setHint(resources.getString(R.string.modifyInfo));
                carTypeField.getSelectedView().setEnabled(false);
                carTypeField.setVisibility(View.INVISIBLE);
                carTypeField.setEnabled(false);
                carTypeFieldLbl.setVisibility(View.VISIBLE);
                brandCarField.setEnabled(false);
                modelCarField.setEnabled(false);
                colorCarField.setEnabled(false);
                yearCarField.setEnabled(false);
                btnchangePassButton.setEnabled(true);
            }
        });
        return Profile;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void changePassword() {
            view = inflaterView.inflate(changepass,null);
            relative = new RelativeLayout(getActivity());
            TextView changePassLbl = view.findViewById(R.id.changePassLbl);
            changePassLbl.setText(resources.getString(R.string.changePassHint));
            newPassField=(EditText) view.findViewById(R.id.newPass);
            confirmPassField=(EditText) view.findViewById(R.id.confirmPass);
            newPassField.setHint(resources.getString(R.string.newPassHint));
            confirmPassField.setHint(resources.getString(R.string.confirmPassHint));
            btnUpdatePass=(Button) view.findViewById(R.id.updatePass);
            btnUpdatePass.setHint(resources.getString(R.string.updatePassHint));
            btnCancelPassButton=(Button) view.findViewById(R.id.cancelPass);
            btnCancelPassButton.setHint(resources.getString(R.string.cancel));

            newPassField.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (newPassField.getRight() - newPassField.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (newPassField.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                                newPassField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                newPassField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.showpass, 0);
                            } else {
                                newPassField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                newPassField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepass, 0);
                            }
                            return true;
                        }
                    }

                    return false;
                }
            });

            confirmPassField.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (confirmPassField.getRight() - confirmPassField.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (confirmPassField.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                                confirmPassField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                confirmPassField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.showpass, 0);
                            } else {
                                confirmPassField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                confirmPassField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepass, 0);
                            }
                            return true;
                        }
                    }

                    return false;
                }
            });

                PopupWindow pw = new PopupWindow(view, 800, 550, true);
                pw.showAtLocation(relative, Gravity.CENTER, 0, 0);
                pw.setOutsideTouchable(false);
                pw.setFocusable(true);
                pw.update();
                btnCancelPassButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });

                btnUpdatePass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(newPassField.getText().toString()) || TextUtils.isEmpty(confirmPassField.getText().toString())) {
                            Toast.makeText(context, resources.getString(R.string.noPassEmpty), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!LoginErrorsValidation.isValid(newPassField.getText().toString())) {
                            Toast.makeText(context, resources.getString(R.string.password_strong), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (!newPassField.getText().toString().equals(confirmPassField.getText().toString())) {
                            Toast.makeText(context, resources.getString(R.string.noPassMatch), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Users = FirebaseDatabase.getInstance().getReference("Users");
                        Query getData = Users.orderByChild("plate_user").equalTo(MainActivity.plateUser.toUpperCase());
                        final String changPass = newPassField.getText().toString();
                        ValueEventListener valueEventListener = (new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Map<String, Object> updates = new HashMap<String, Object>();
                                    updates.put("user_password", changPass);
                                    ds.getRef().updateChildren(updates);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        getData.addListenerForSingleValueEvent(valueEventListener);
                        confirmPassField.setText("");
                        newPassField.setText("");
                        newPassField.setEnabled(false);
                        confirmPassField.setEnabled(false);
                        btnchangePassButton.setHint(resources.getString(R.string.changePassHint));
                        Toast.makeText(context, resources.getString(R.string.passUpdated), Toast.LENGTH_SHORT).show();
                       pw.dismiss();
                    }
                });

    }

    private void updateDataInfo() {
        if (btnModiInfoButton.getHint().equals(resources.getString(R.string.modifyInfo))){
            btnModiInfoButton.setHint(resources.getString(R.string.updateInfo));

            btnCancelModInfo.setVisibility(View.VISIBLE);
            carTypeField.getSelectedView().setEnabled(true);
            carTypeField.setEnabled(true);
            carTypeFieldLbl.setVisibility(View.INVISIBLE);
            carTypeField.setVisibility(View.VISIBLE);
            brandCarField.setEnabled(true);
            modelCarField.setEnabled(true);
            colorCarField.setEnabled(true);
            yearCarField.setEnabled(true);
            btnchangePassButton.setEnabled(false);
            return;
       }

       if (btnModiInfoButton.getHint().equals(resources.getString(R.string.updateInfo))){
            btnModiInfoButton.setHint(resources.getString(R.string.modifyInfo));

           Users = FirebaseDatabase.getInstance().getReference("Users");
           Users.orderByChild("plate_user").equalTo(MainActivity.plateUser.toUpperCase()).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for (DataSnapshot ds: snapshot.getChildren()){
                       Map<String, Object> updates = new HashMap<String, Object>();
                       updates.put("cartype", carTypeField.getSelectedItem().toString());
                       updates.put("carbrand", brandCarField.getText().toString());
                       updates.put("carmodel", modelCarField.getText().toString());
                       updates.put("carcolor", colorCarField.getText().toString());
                       updates.put("year", yearCarField.getText().toString());
                       ds.getRef().updateChildren(updates);
                   }
               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
                ((Spinner)carTypeField).getSelectedView().setEnabled(false);
                if (carTypeField.getSelectedItemPosition() == 0) {
                    carTypeFieldLbl.setText("");
                }else {
                    carTypeFieldLbl.setText(carTypeField.getSelectedItem().toString());
                }
                carTypeFieldLbl.setVisibility(View.VISIBLE);
                carTypeField.setVisibility(View.INVISIBLE);
                carTypeField.setEnabled(false);
                carTypeField.setClickable(false);
                carTypeField.setFocusable(false);
                brandCarField.setEnabled(false);
                modelCarField.setEnabled(false);
                colorCarField.setEnabled(false);
                yearCarField.setEnabled(false);
                btnCancelModInfo.setVisibility(View.INVISIBLE);
                Toast.makeText(context, resources.getString(R.string.infoUpdated), Toast.LENGTH_SHORT).show();
                btnchangePassButton.setEnabled(true);
        }
    }

    private void getInfoData() {
        Users = FirebaseDatabase.getInstance().getReference("Users");
        Users.orderByChild("plate_user").equalTo(MainActivity.plateUser.toUpperCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        typeProfileCarUser = snapshot.child("cartype").getValue();
                        brandProfileCarUser = snapshot.child("carbrand").getValue();
                        modelProfileCaruser = snapshot.child("carmodel").getValue();
                        colorProfileCarUser = snapshot.child("carcolor").getValue();
                        yearProfileCarUser = snapshot.child("year").getValue();
                    }
                    carTypeFieldLbl.setText(typeProfileCarUser.toString());
                    brandCarField.setText(brandProfileCarUser.toString());
                    modelCarField.setText(modelProfileCaruser.toString());
                    colorCarField.setText(colorProfileCarUser.toString());
                    yearCarField.setText(yearProfileCarUser.toString());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}