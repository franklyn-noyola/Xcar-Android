package com.epicdeveloper.xcar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import static com.epicdeveloper.xcar.sendEmail.sendEmailMessage;
import java.util.Objects;

public class contacto extends AppCompatActivity {
    Spinner selectSubject;
    EditText message;
    Context context;
    Resources resources;
    String selectedLanguage;
    TextView sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto);
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getApplication(), selectedLanguage);
        resources = context.getResources();
        selectSubject = findViewById(R.id.subject);
        message = findViewById(R.id.contactMessage);

        sendMessage= findViewById(R.id.buttonSend);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
        getSupportActionBar().setTitle(resources.getString(R.string.action_contacts));
        selectSubject.setAdapter(new ArrayAdapter<>(contacto.this, android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.contactos_asuntos)));
        translatedLanguage();
        sendMessage.setOnClickListener(v -> {

            if (TextUtils.equals(resources.getString(R.string.issueSelect),selectSubject.getSelectedItem().toString())){
                Toast.makeText(getApplicationContext(), resources.getString(R.string.issueEmpty), Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(message.getText().toString())){
                Toast.makeText(getApplicationContext(), resources.getString(R.string.emptyMessage), Toast.LENGTH_SHORT).show();
            }else{
                sendEmailMessage("xcar_support@epicdevelopers.app",MainActivity.plateUser+"-"+selectSubject.getSelectedItem().toString(), message.getText().toString());
                Toast.makeText(getApplicationContext(), resources.getString(R.string.sentMessage), Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), profile_activity.class);
                startActivity(intent);
            }
        });

    }

    private void translatedLanguage() {
        message.setHint(resources.getString(R.string.targetMessageContacto));
        sendMessage.setText(resources.getString(R.string.sendText));
    }


}
