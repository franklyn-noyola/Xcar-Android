package com.epicdeveloper.xcar;

import static com.epicdeveloper.xcar.MainActivity.email_user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;


import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.epicdeveloper.xcar.LocaleHelper;
import com.epicdeveloper.xcar.MainActivity;
import com.epicdeveloper.xcar.R;
import com.epicdeveloper.xcar.ui.LoginErrorsValidation;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;

public class additionalPlate extends AppCompatActivity {

    Context context;
    Resources resources;
    String selectedLanguage;
    private DatabaseReference Users;

    Object userName;
    Object password;
    String existingPlate;

    EditText brandCarField, modelCarField, colorCarField, yearCarField, additionalPlate;

    Spinner carSelected;

    TextView addPlateLabel, infoLbl;

    AdView adview;

    private DatabaseReference addPlateDB;
    Button verifyButton, addPlateButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        infoLbl = findViewById(R.id.infoLblAdd);
        if (selectedLanguage.equals("RU")) {
            infoLbl.setTextSize(20);
        }
        infoLbl.setText(resources.getString(R.string.additionalInfor));
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

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existingPlate = additionalPlate.getText().toString();
                if (TextUtils.isEmpty(existingPlate)){
                    Toast.makeText(context, resources.getString(R.string.plateEmpty), Toast.LENGTH_SHORT).show();
                    return;
                }else{
                getPLateInformation(existingPlate);
            }
        };
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFielDisable();
            }
        });

    }
        public void getPLateInformation(String plate){
            if (plate.length() < 5 || plate.length()>10){
                Toast.makeText(getApplicationContext(),resources.getString(R.string.wrong_format_plate), Toast.LENGTH_SHORT).show();
                return;
            }else {
                String dot1 = new String (email_user);
                String dot2 = dot1.replace(".","_");
                Users = FirebaseDatabase.getInstance().getReference("Users/"+dot2);
                Users.orderByChild("plate_user").equalTo(plate.toUpperCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(),resources.getString(R.string.existePlate), Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            setFieldsEnable();
                            getDataInfo();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            }
            public void getDataInfo() {

                String dot1 = new String (email_user);
                String dot2 = dot1.replace(".","_");
                Users = FirebaseDatabase.getInstance().getReference("Users/"+dot2);
                Users.orderByChild("user_email").equalTo(email_user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                userName = snapshot.child("user_name").getValue();
                                password = snapshot.child("user_password").getValue();
                            }
                        }
                        addPlateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createAdditionalPlate((String) userName, (String) password);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        public void createAdditionalPlate (String username, String password){
            if (carSelected.getSelectedItemPosition() == 0 || TextUtils.isEmpty(brandCarField.getText().toString()) || TextUtils.isEmpty(modelCarField.getText().toString()) || TextUtils.isEmpty(colorCarField.getText().toString()) || TextUtils.isEmpty(yearCarField.getText().toString())) {
                Toast.makeText(getApplicationContext(),R.string.invalidAdditonalPlateFieñds, Toast.LENGTH_LONG).show();
                return;
           }
            //Toast.makeText(getApplicationContext(), carSelected.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            final UsersConnected user = new UsersConnected(additionalPlate.getText().toString().toUpperCase(), carSelected.getSelectedItem().toString(), brandCarField.getText().toString(),colorCarField.getText().toString(),modelCarField.getText().toString(),yearCarField.getText().toString(),  "A", username, email_user, password, "0");
            final singlePlates createdPlates = new singlePlates(additionalPlate.getText().toString().toUpperCase(), carSelected.getSelectedItem().toString(), brandCarField.getText().toString(),colorCarField.getText().toString(),modelCarField.getText().toString(),yearCarField.getText().toString());
            String dot1 = new String (email_user);
            String dot2 = dot1.replace(".","_");
            DatabaseReference singlePlates = FirebaseDatabase.getInstance().getReference("singlePlates/createdPlates");
            DatabaseReference userEmail = FirebaseDatabase.getInstance().getReference("Users/"+dot2);
            String id = userEmail.push().getKey();
            String id2 = singlePlates.push().getKey();
            userEmail.child(id).setValue(user);
            singlePlates.child(id2).setValue(createdPlates);
            Toast.makeText(getApplicationContext(),R.string.plateAdded, Toast.LENGTH_LONG).show();
            setFielDisable();
        }

        public void setFieldsEnable(){
            brandCarField.setEnabled(true);
            modelCarField.setEnabled(true);
            addPlateButton.setEnabled(true);
            colorCarField.setEnabled(true);
            yearCarField.setEnabled(true);
            cancelButton.setEnabled(true);
            cancelButton.setVisibility(View.VISIBLE);
            verifyButton.setEnabled(false);
            additionalPlate.setEnabled(false);
        }

    public void setFielDisable(){
        brandCarField.setEnabled(false);
        modelCarField.setEnabled(false);
        addPlateButton.setEnabled(false);
        colorCarField.setEnabled(false);
        yearCarField.setEnabled(false);
        cancelButton.setEnabled(false);
        cancelButton.setVisibility(View.INVISIBLE);
        verifyButton.setEnabled(true);
        additionalPlate.setEnabled(true);
        additionalPlate.setText("");
        brandCarField.setText("");
        modelCarField.setText("");
        addPlateButton.setText("");
        colorCarField.setText("");
        yearCarField.setText("");
        carSelected.setSelection(0);

    }


    }