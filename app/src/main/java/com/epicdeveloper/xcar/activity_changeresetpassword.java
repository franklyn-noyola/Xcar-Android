package com.epicdeveloper.xcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicdeveloper.xcar.ui.LoginErrorsValidation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class activity_changeresetpassword extends AppCompatActivity {

    EditText newPass;
    EditText confirmPass;
    Button btnchangePass;
    TextView errorText;
    TextView newUserlbl;
    static String selectedLanguage;
    Context context;
    Resources resources;

    DatabaseReference changePassword;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeresetpassword);
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(activity_changeresetpassword.this, selectedLanguage);
        resources = context.getResources();
        newUserlbl = findViewById(R.id.newUserlbl);
        newPass = findViewById(R.id.newPassField);
        confirmPass = findViewById(R.id.newConfirmPass);
        btnchangePass =findViewById(R.id.changePassButton);
        errorText = findViewById(R.id.errChangeTextView);
        newPass.setEnabled(true);
        confirmPass.setEnabled(true);
        translatefields();

        newPass.setOnTouchListener((v, event) -> {
            //final int DRAWABLE_LEFT = 0;
            //final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            //final int DRAWABLE_BOTTOM = 3;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (newPass.getRight() - newPass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (newPass.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()){
                        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        newPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.showpass, 0);
                    }else{
                        newPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        newPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepass, 0);
                    }
                    return true;
                }
            }

            return false;
        });

        confirmPass.setOnTouchListener((v, event) -> {
            //final int DRAWABLE_LEFT = 0;
            //final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            //final int DRAWABLE_BOTTOM = 3;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (confirmPass.getRight() - confirmPass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (confirmPass.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()){
                        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        confirmPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.showpass, 0);
                    }else{
                        confirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        confirmPass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepass, 0);
                    }
                    return true;
                }
            }

            return false;
        });

        btnchangePass.setOnClickListener(v -> {
            if (btnchangePass.getText().equals(resources.getString(R.string.backLogin))){
                finish();
                return;
            }
            if (btnchangePass.getText().equals(resources.getString(R.string.changePssButton))) {
                if (TextUtils.isEmpty(newPass.getText().toString()) || TextUtils.isEmpty(confirmPass.getText().toString())) {
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.noEmptyPassFields), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!LoginErrorsValidation.isValid(newPass.getText().toString())) {
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.passStrongErr), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!TextUtils.equals(newPass.getText().toString(), confirmPass.getText().toString())) {
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.notPassMatch), Toast.LENGTH_SHORT).show();
                    return;
                }

                changePass(newPass.getText().toString());
                Toast.makeText(getApplicationContext(),resources.getString(R.string.passUpdated), Toast.LENGTH_SHORT).show();
                btnchangePass.setText(resources.getString(R.string.backLogin));
                newPass.setEnabled(false);
                confirmPass.setEnabled(false);
            }

            });

    }

    private void translatefields() {
        newUserlbl.setText(resources.getString(R.string.passChangeLbl));
        newPass.setHint(resources.getString(R.string.newPassHint));
        confirmPass.setHint(resources.getString(R.string.confirmNewPassHint));
        btnchangePass.setText(resources.getString(R.string.changePssButton));
        errorText.setText(resources.getString(R.string.passStrongChange));
    }

    private void changePass(final String passchange){
        changePassword = FirebaseDatabase.getInstance().getReference("Users");
        Query getData = changePassword.orderByChild("plate_user").equalTo(MainActivity.plate_user.toUpperCase());
        ValueEventListener valueEventListener = (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("user_password", passchange);
                    updates.put("resetPass","0");
                    ds.getRef().updateChildren(updates);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getData.addListenerForSingleValueEvent(valueEventListener);
    }


}