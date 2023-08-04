package com.epicdeveloper.xcar;

import static android.widget.RadioGroup.*;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class settings  extends AppCompatActivity {
    RadioButton spanish, english,french,german,italian,portuguese,russian,chinese,japanese,dutch, poland, korean, swedish, arabic, hindi, urdu;
    Button changeLang, cancel;
    TextView selectLang,warning,languagechangeheader;
    String selectedLang;
    Context context;
    Resources resources;
    RadioGroup radioLan1;
    RadioGroup radioLan2;
    public static String updateSelectLanguage="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        selectedLang = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getApplication(), selectedLang);
        resources = context.getResources();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
        getSupportActionBar().setTitle(resources.getString(R.string.action_settings));
        languagechangeheader = (TextView) findViewById(R.id.languagechangeheader);
        radioLan1 = findViewById(R.id.radioLan1);
        radioLan2 = findViewById(R.id.radioLan2);

        selectLang = (TextView) findViewById(R.id.selectLang);
        warning = (TextView) findViewById(R.id.warning);
        changeLang = (Button) findViewById(R.id.changeLan);
        cancel = (Button) findViewById(R.id.cancel);
        spanish = (RadioButton) findViewById(R.id.spanish);
        english = (RadioButton) findViewById(R.id.english);
        french = (RadioButton) findViewById(R.id.french);
        german = (RadioButton) findViewById(R.id.german);
        italian = (RadioButton) findViewById(R.id.italian);
        portuguese = (RadioButton) findViewById(R.id.portuguese);
        russian = (RadioButton) findViewById(R.id.russian);
        chinese = (RadioButton) findViewById(R.id.chinese);
        japanese = (RadioButton) findViewById(R.id.japanese);
        dutch = (RadioButton) findViewById(R.id.dutch);
        poland = (RadioButton) findViewById(R.id.poland);
        korean = (RadioButton) findViewById(R.id.korean);
        swedish = (RadioButton) findViewById(R.id.swedish);
        arabic = (RadioButton) findViewById(R.id.arabic);
        hindi = (RadioButton) findViewById(R.id.hindi);
        urdu = (RadioButton) findViewById(R.id.urdu);

        translatedLanguage();
        radioLan1.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    getChanged2();
                }
            }
        });

        radioLan2.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    getChanged1();
                }
            }
        });
        getSelectedLang();
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeLang.getText().toString().equals(resources.getString(R.string.changeLanguage))) {
                    changeLang.setText(resources.getString(R.string.updateLanguage));
                    selectLang.setText(resources.getString(R.string.selectLanguage));
                    spanish.setEnabled(true);
                    english.setEnabled(true);
                    french.setEnabled(true);
                    german.setEnabled(true);
                    italian.setEnabled(true);
                    portuguese.setEnabled(true);
                    russian.setEnabled(true);
                    chinese.setEnabled(true);
                    japanese.setEnabled(true);
                    dutch.setEnabled(true);
                    poland.setEnabled(true);
                    korean.setEnabled(true);
                    swedish.setEnabled(true);
                    arabic.setEnabled(true);
                    hindi.setEnabled(true);
                    urdu.setEnabled(true);
                    cancel.setVisibility(View.VISIBLE);
                    warning.setVisibility(View.VISIBLE);
                } else {


                radioLan2.setOnCheckedChangeListener(new OnCheckedChangeListener()
                    {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radioLan1.clearCheck();
                    }
                });
                    if (changeLang.getText().toString().equals(resources.getString(R.string.updateLanguage))) {
                        changeLang.setText(resources.getString(R.string.changeLanguage));
                        if (spanish.isChecked()) {

                            updateSelectLanguage = "ES";
                        }
                        if (english.isChecked()) {
                            updateSelectLanguage = "EN";
                        }
                        if (french.isChecked()) {
                            updateSelectLanguage = "FR";
                        }
                        if (german.isChecked()) {
                            updateSelectLanguage = "DE";
                        }
                        if (italian.isChecked()) {
                            updateSelectLanguage = "IT";
                        }
                        if (portuguese.isChecked()) {
                            updateSelectLanguage = "PT";
                        }
                        if (russian.isChecked()) {
                            updateSelectLanguage = "RU";
                        }
                        if (chinese.isChecked()) {
                            updateSelectLanguage = "ZH";
                        }
                        if (japanese.isChecked()) {
                            updateSelectLanguage = "JA";
                        }
                        if (dutch.isChecked()) {
                            updateSelectLanguage = "NL";
                        }
                        if (poland.isChecked()) {
                            updateSelectLanguage = "PL";
                        }
                        if (korean.isChecked()) {
                            updateSelectLanguage = "KO";
                        }
                        if (swedish.isChecked()) {
                            updateSelectLanguage = "SV";
                        }
                        if (arabic.isChecked()) {
                            updateSelectLanguage = "AR";
                        }
                        if (hindi.isChecked()) {
                            updateSelectLanguage = "HI";
                        }
                        if (urdu.isChecked()) {
                            updateSelectLanguage = "UR";
                        }
                        updateLanguageUser();
                        selectLang.setText("");
                        spanish.setEnabled(false);
                        english.setEnabled(false);
                        french.setEnabled(false);
                        german.setEnabled(false);
                        italian.setEnabled(false);
                        portuguese.setEnabled(false);
                        russian.setEnabled(false);
                        chinese.setEnabled(false);
                        japanese.setEnabled(false);
                        dutch.setEnabled(false);
                        poland.setEnabled(false);
                        korean.setEnabled(false);
                        swedish.setEnabled(false);
                        arabic.setEnabled(false);
                        hindi.setEnabled(false);
                        urdu.setEnabled(false);
                        cancel.setVisibility(View.GONE);
                        warning.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),resources.getString(R.string.languageUpdated),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedLang();
                changeLang.setText(resources.getString(R.string.changeLanguage));
                selectLang.setText("");
                spanish.setEnabled(false);
                english.setEnabled(false);
                french.setEnabled(false);
                german.setEnabled(false);
                italian.setEnabled(false);
                portuguese.setEnabled(false);
                russian.setEnabled(false);
                chinese.setEnabled(false);
                japanese.setEnabled(false);
                dutch.setEnabled(false);
                poland.setEnabled(false);
                korean.setEnabled(false);
                swedish.setEnabled(false);
                arabic.setEnabled(false);
                hindi.setEnabled(false);
                urdu.setEnabled(false);
                cancel.setVisibility(View.GONE);
                warning.setVisibility(View.GONE);
            }
        });

    }

    public void getSelectedLang() {
        switch (selectedLang){
            case "ES" :     spanish.setChecked(true);
                break;
            case "EN" :     english.setChecked(true);
                break;
            case "FR" :     french.setChecked(true);
                break;
            case "DE" :     german.setChecked(true);
                break;
            case "IT" :     italian.setChecked(true);
                break;
            case "PT" :     portuguese.setChecked(true);
                break;
            case "RU" :     russian.setChecked(true);
                break;
            case "ZH" :     chinese.setChecked(true);
                break;
            case "JA" :     japanese.setChecked(true);
                break;
            case "NL" :     dutch.setChecked(true);
                break;
            case "PL" :     poland.setChecked(true);
                break;
            case "KO" :     korean.setChecked(true);
                break;
            case "SV" :     swedish.setChecked(true);
                break;
            case "AR" :     arabic.setChecked(true);
                break;
            case "HI" :     hindi.setChecked(true);
                break;
            case "UR" :     urdu.setChecked(true);
                break;
        }
    }

    public void getChanged1()  {
            radioLan1.setOnCheckedChangeListener(null);
            radioLan1.clearCheck();
            radioLan1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    getChanged2();
                }
            });
        }

    public void getChanged2()  {
        radioLan2.setOnCheckedChangeListener(null);
        radioLan2.clearCheck();
        radioLan2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                getChanged1();
            }
        });
    }

    private void translatedLanguage() {
        spanish.setText(resources.getString(R.string.spanishLan));
        english.setText(resources.getString(R.string.englishLan));
        french.setText(resources.getString(R.string.frenchLan));
        german.setText(resources.getString(R.string.germanLan));
        italian.setText(resources.getString(R.string.italianLan));
        languagechangeheader.setText(resources.getString(R.string.changeLanguage));
        portuguese.setText(resources.getString(R.string.portugueseLan));
        russian.setText(resources.getString(R.string.russianLan));
        chinese.setText(resources.getString(R.string.chineseLan));
        japanese.setText(resources.getString(R.string.japanLan));
        dutch.setText(resources.getString(R.string.dutchLan));
        poland.setText(resources.getString(R.string.polishLan));
        korean.setText(resources.getString(R.string.koreanLan));
        swedish.setText(resources.getString(R.string.swedishLan));
        arabic.setText(resources.getString(R.string.arabicLan));
        hindi.setText(resources.getString(R.string.hindiLan));
        urdu.setText(resources.getString(R.string.urduLan));
        selectLang.setHint(resources.getString(R.string.selectLanguage));
        cancel.setText(resources.getString(R.string.cancel));
        changeLang.setText(resources.getString(R.string.changeLanguage));
        warning.setHint(resources.getString(R.string.warningLanguage));
    }

    private void updateLanguageUser() {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()){
                        Log.w("TAG", "Not able to get token", task.getException());
                        return;
                    }
                    String mToken = task.getResult();
                 final languageUserData createUserLanguage = new languageUserData(mToken,updateSelectLanguage);
                DatabaseReference updateLanguageUser =  FirebaseDatabase.getInstance().getReference("Users/userLanguage");
                updateLanguageUser.orderByChild("users").equalTo(mToken).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            HashMap updates = new HashMap();
                            String key = null;
                            for (DataSnapshot ds:snapshot.getChildren()){
                                key = ds.getKey();
                            }
                            updates.put("language",updateSelectLanguage);
                            updateLanguageUser.child(key).getRef().updateChildren(updates);
                        }else{
                            updateLanguageUser.push().setValue(createUserLanguage);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }
}
