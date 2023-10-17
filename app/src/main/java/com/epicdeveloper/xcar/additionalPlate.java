package com.epicdeveloper.xcar;

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
        carSelected.setAdapter(new ArrayAdapter<>(additionalPlate.this, android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.Vehiculos)));
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

    }




 }
