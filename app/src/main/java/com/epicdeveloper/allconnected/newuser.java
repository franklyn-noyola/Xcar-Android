package com.epicdeveloper.allconnected;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.epicdeveloper.allconnected.ui.LoginErrorsValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class newuser extends AppCompatActivity {
    EditText name_user, plate_user, email_user, pass_user, confirm_pass;
    Spinner languageSelection;
    TextView linkButton,newUserLabel,textView2;
    public static String selectedLang;
    String cartype="",carBrand="", carModel="", carColor="", yearCar="", resetPass="0";
    Button btnRegister;
    String welcomeMessage;
    String headerMessage;
    String bodyMessage;
    public String codeActivated;
    String farewellMessage;
    String allMessage;
    public String userPlate;
    CheckBox acceptButton;
    Context context;
    Resources resources;
    String termsLink;


    private DatabaseReference Users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newuser);
        selectedLang = MainActivity.userlanguage;
        name_user = (EditText) findViewById(R.id.newUserNameField);
        plate_user =  (EditText) findViewById(R.id.newUserPlateField);
        email_user = (EditText)  findViewById(R.id.newUserEmailField);
        pass_user = (EditText)   findViewById(R.id.newUserPassField);
        confirm_pass = (EditText) findViewById(R.id.newUserPassConField);
        btnRegister = (Button) findViewById(R.id.registerButton);
        acceptButton = (CheckBox) findViewById(R.id.checkBox);
        linkButton = (TextView) findViewById(R.id.terminos);
        newUserLabel = (TextView) findViewById(R.id.newUserlbl);
        textView2 = (TextView) findViewById(R.id.textView2);
        languageSelection = (Spinner) findViewById(R.id.languageSelection);
        acceptButton.setEnabled(true);
        email_user.setEnabled(true);
        plate_user.setEnabled(true);
        pass_user.setEnabled(true);
        confirm_pass.setEnabled(true);
        name_user.setEnabled(true);


        languageSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (languageSelection.getSelectedItem().toString().equals("Español") || languageSelection.getSelectedItem().toString().equals("Spanish") || languageSelection.getSelectedItem().toString().equals("Espagnol") || languageSelection.getSelectedItem().toString().equals("Spanisch") || languageSelection.getSelectedItem().toString().equals("Spagnolo") || languageSelection.getSelectedItem().toString().equals("Espanhol") || languageSelection.getSelectedItem().toString().equals("испанский") || languageSelection.getSelectedItem().toString().equals("西班牙语") || languageSelection.getSelectedItem().toString().equals("スペイン語") || languageSelection.getSelectedItem().toString().equals("Spaans") || languageSelection.getSelectedItem().toString().equals("Hiszpański") || languageSelection.getSelectedItem().toString().equals("Spanska") || languageSelection.getSelectedItem().toString().equals("스페인의") || languageSelection.getSelectedItem().toString().equals("الأسبانية") || languageSelection.getSelectedItem().toString().equals("स्पेनिश") || languageSelection.getSelectedItem().toString().equals("ہسپانوی")){
                    selectedLang = "ES";
                }
                if (languageSelection.getSelectedItem().toString().equals("Inglés") || languageSelection.getSelectedItem().toString().equals("English") || languageSelection.getSelectedItem().toString().equals("Anglais") || languageSelection.getSelectedItem().toString().equals("Englisch") || languageSelection.getSelectedItem().toString().equals("Inglese") || languageSelection.getSelectedItem().toString().equals("Inglês") || languageSelection.getSelectedItem().toString().equals("английский") || languageSelection.getSelectedItem().toString().equals("英语") || languageSelection.getSelectedItem().toString().equals("英語") || languageSelection.getSelectedItem().toString().equals("Engels") || languageSelection.getSelectedItem().toString().equals("Język angielski") || languageSelection.getSelectedItem().toString().equals("Engelsk") || languageSelection.getSelectedItem().toString().equals("영어") || languageSelection.getSelectedItem().toString().equals("الإنجليزية") || languageSelection.getSelectedItem().toString().equals("अंग्रेज़ी") || languageSelection.getSelectedItem().toString().equals("انگریزی")){
                    selectedLang = "EN";
                }
                if (languageSelection.getSelectedItem().toString().equals("Francés") || languageSelection.getSelectedItem().toString().equals("French") || languageSelection.getSelectedItem().toString().equals("Français") || languageSelection.getSelectedItem().toString().equals("Französisch") || languageSelection.getSelectedItem().toString().equals("Francese") || languageSelection.getSelectedItem().toString().equals("Francês") || languageSelection.getSelectedItem().toString().equals("Французский") || languageSelection.getSelectedItem().toString().equals("法语") || languageSelection.getSelectedItem().toString().equals("フランス語") || languageSelection.getSelectedItem().toString().equals("Frans") || languageSelection.getSelectedItem().toString().equals("Francuski") || languageSelection.getSelectedItem().toString().equals("Franska") || languageSelection.getSelectedItem().toString().equals("프랑스 국민") || languageSelection.getSelectedItem().toString().equals("الفرنسية") || languageSelection.getSelectedItem().toString().equals("फ्रेंच") || languageSelection.getSelectedItem().toString().equals("فرانسیسی")){
                    selectedLang = "FR";
                }
                if (languageSelection.getSelectedItem().toString().equals("Deutsche") || languageSelection.getSelectedItem().toString().equals("Alemán") || languageSelection.getSelectedItem().toString().equals("German") || languageSelection.getSelectedItem().toString().equals("Allemand") || languageSelection.getSelectedItem().toString().equals("Tedesco")|| languageSelection.getSelectedItem().toString().equals("Alemão") || languageSelection.getSelectedItem().toString().equals("德语") || languageSelection.getSelectedItem().toString().equals("ドイツ人") || languageSelection.getSelectedItem().toString().equals("Duitse") || languageSelection.getSelectedItem().toString().equals("Niemiecki") || languageSelection.getSelectedItem().toString().equals("Tysk") || languageSelection.getSelectedItem().toString().equals("독일 사람") || languageSelection.getSelectedItem().toString().equals("ألمانية") || languageSelection.getSelectedItem().toString().equals("जर्मन") || languageSelection.getSelectedItem().toString().equals("جرمن")){
                    selectedLang = "DE";
                    textView2.setTextSize(10);
                }
                if (languageSelection.getSelectedItem().toString().equals("Italiano") || languageSelection.getSelectedItem().toString().equals("Italian") || languageSelection.getSelectedItem().toString().equals("Italien") || languageSelection.getSelectedItem().toString().equals("Italienisch") || languageSelection.getSelectedItem().toString().equals("Итальянский") || languageSelection.getSelectedItem().toString().equals("Итальянский") || languageSelection.getSelectedItem().toString().equals("义大利文") || languageSelection.getSelectedItem().toString().equals("イタリア語") || languageSelection.getSelectedItem().toString().equals("Italiaans") || languageSelection.getSelectedItem().toString().equals("Italiaans") || languageSelection.getSelectedItem().toString().equals("Włoski") || languageSelection.getSelectedItem().toString().equals("Italienska") || languageSelection.getSelectedItem().toString().equals("이탈리아 사람") || languageSelection.getSelectedItem().toString().equals("إيطالي") || languageSelection.getSelectedItem().toString().equals("इतालवी") || languageSelection.getSelectedItem().toString().equals("اطالوی")){
                    selectedLang = "IT";
                }
                if (languageSelection.getSelectedItem().toString().equals("Português") || languageSelection.getSelectedItem().toString().equals("Portugués") || languageSelection.getSelectedItem().toString().equals("Portuguese") || languageSelection.getSelectedItem().toString().equals("Portugais") || languageSelection.getSelectedItem().toString().equals("Portugiesisch") || languageSelection.getSelectedItem().toString().equals("Portoghese") || languageSelection.getSelectedItem().toString().equals("португальский") || languageSelection.getSelectedItem().toString().equals("鲁索") || languageSelection.getSelectedItem().toString().equals("ポルトガル語") || languageSelection.getSelectedItem().toString().equals("Portugees") || languageSelection.getSelectedItem().toString().equals("Portugalski") || languageSelection.getSelectedItem().toString().equals("Portugisiska") || languageSelection.getSelectedItem().toString().equals("포르투갈 인") || languageSelection.getSelectedItem().toString().equals("البرتغالية") || languageSelection.getSelectedItem().toString().equals("पुर्तगाली") || languageSelection.getSelectedItem().toString().equals("پرتگالی")){
                    selectedLang = "PT";
                }
                if (languageSelection.getSelectedItem().toString().equals("Ruso") || languageSelection.getSelectedItem().toString().equals("Russian") || languageSelection.getSelectedItem().toString().equals("Russe") || languageSelection.getSelectedItem().toString().equals("Russo") || languageSelection.getSelectedItem().toString().equals("Russisch") || languageSelection.getSelectedItem().toString().equals("русский") || languageSelection.getSelectedItem().toString().equals("俄语") || languageSelection.getSelectedItem().toString().equals("ロシア") || languageSelection.getSelectedItem().toString().equals("Russisch") || languageSelection.getSelectedItem().toString().equals("Rosyjski") || languageSelection.getSelectedItem().toString().equals("Ryska") || languageSelection.getSelectedItem().toString().equals("러시아인") || languageSelection.getSelectedItem().toString().equals("الروسية") || languageSelection.getSelectedItem().toString().equals("रूसी") || languageSelection.getSelectedItem().toString().equals("روسی")){
                    selectedLang = "RU";
                    textView2.setTextSize(10);
                }
                if (languageSelection.getSelectedItem().toString().equals("Chino") || languageSelection.getSelectedItem().toString().equals("Chinese") || languageSelection.getSelectedItem().toString().equals("Chinois") || languageSelection.getSelectedItem().toString().equals("Chinesisch") || languageSelection.getSelectedItem().toString().equals("Cinese") || languageSelection.getSelectedItem().toString().equals("Chinês") || languageSelection.getSelectedItem().toString().equals("Китайский") || languageSelection.getSelectedItem().toString().equals("中国人") || languageSelection.getSelectedItem().toString().equals("中国語") || languageSelection.getSelectedItem().toString().equals("Chinese") || languageSelection.getSelectedItem().toString().equals("Chiński") || languageSelection.getSelectedItem().toString().equals("Kinesiska") || languageSelection.getSelectedItem().toString().equals("중국말") || languageSelection.getSelectedItem().toString().equals("صينى") || languageSelection.getSelectedItem().toString().equals("चीनी") || languageSelection.getSelectedItem().toString().equals("چینی")){
                    selectedLang = "ZH";
                }
                if (languageSelection.getSelectedItem().toString().equals("Japonés") || languageSelection.getSelectedItem().toString().equals("Japanese") || languageSelection.getSelectedItem().toString().equals("Japonais") || languageSelection.getSelectedItem().toString().equals("Japanisch") || languageSelection.getSelectedItem().toString().equals("Giapponese") || languageSelection.getSelectedItem().toString().equals("Japonês") || languageSelection.getSelectedItem().toString().equals("Японский") || languageSelection.getSelectedItem().toString().equals("日本人") || languageSelection.getSelectedItem().toString().equals("日本語") || languageSelection.getSelectedItem().toString().equals("Japans") || languageSelection.getSelectedItem().toString().equals("Język japoński") || languageSelection.getSelectedItem().toString().equals("Japanska") || languageSelection.getSelectedItem().toString().equals("일본어") || languageSelection.getSelectedItem().toString().equals("اليابانية") || languageSelection.getSelectedItem().toString().equals("जापानी") || languageSelection.getSelectedItem().toString().equals("جاپانی")){
                    selectedLang = "JA";
                }
                if (languageSelection.getSelectedItem().toString().equals("Holandés") || languageSelection.getSelectedItem().toString().equals("Dutch") || languageSelection.getSelectedItem().toString().equals("Néerlandais") || languageSelection.getSelectedItem().toString().equals("Niederländisch") || languageSelection.getSelectedItem().toString().equals("Olandese") || languageSelection.getSelectedItem().toString().equals("Holandês") || languageSelection.getSelectedItem().toString().equals("нидерландский язык") || languageSelection.getSelectedItem().toString().equals("荷兰语") || languageSelection.getSelectedItem().toString().equals("オランダの") || languageSelection.getSelectedItem().toString().equals("Nederlands") || languageSelection.getSelectedItem().toString().equals("Holenderski") || languageSelection.getSelectedItem().toString().equals("Nederländska") || languageSelection.getSelectedItem().toString().equals("네덜란드 사람") || languageSelection.getSelectedItem().toString().equals("Holenderski") || languageSelection.getSelectedItem().toString().equals("اللغة الهولندية") || languageSelection.getSelectedItem().toString().equals("डच") || languageSelection.getSelectedItem().toString().equals("ڈچ")){
                    selectedLang = "NL";
                }
                if (languageSelection.getSelectedItem().toString().equals("Polaco") || languageSelection.getSelectedItem().toString().equals("Polish") || languageSelection.getSelectedItem().toString().equals("Polonais") || languageSelection.getSelectedItem().toString().equals("Polieren") || languageSelection.getSelectedItem().toString().equals("Polacco") || languageSelection.getSelectedItem().toString().equals("Polonês") || languageSelection.getSelectedItem().toString().equals("Польский") || languageSelection.getSelectedItem().toString().equals("抛光") || languageSelection.getSelectedItem().toString().equals("研磨") || languageSelection.getSelectedItem().toString().equals("Pools") || languageSelection.getSelectedItem().toString().equals("Polskie") || languageSelection.getSelectedItem().toString().equals("광택") || languageSelection.getSelectedItem().toString().equals("Putsa") || languageSelection.getSelectedItem().toString().equals("تلميع") || languageSelection.getSelectedItem().toString().equals("पोलिश") || languageSelection.getSelectedItem().toString().equals("پولش")){
                    selectedLang = "PL";
                }
                if (languageSelection.getSelectedItem().toString().equals("Coreano") || languageSelection.getSelectedItem().toString().equals("Korean") || languageSelection.getSelectedItem().toString().equals("Coréen") || languageSelection.getSelectedItem().toString().equals("Koreanisch") || languageSelection.getSelectedItem().toString().equals("Coreano") || languageSelection.getSelectedItem().toString().equals("Coreano") || languageSelection.getSelectedItem().toString().equals("Корейский") || languageSelection.getSelectedItem().toString().equals("韩国人") || languageSelection.getSelectedItem().toString().equals("韓国語") || languageSelection.getSelectedItem().toString().equals("Koreaans") || languageSelection.getSelectedItem().toString().equals("Koreański") || languageSelection.getSelectedItem().toString().equals("한국어") || languageSelection.getSelectedItem().toString().equals("Koreanska") || languageSelection.getSelectedItem().toString().equals("الكورية") || languageSelection.getSelectedItem().toString().equals("कोरियाई") || languageSelection.getSelectedItem().toString().equals("کورین")){
                    selectedLang = "KO";
                }
                if (languageSelection.getSelectedItem().toString().equals("Sueco") || languageSelection.getSelectedItem().toString().equals("Swedish") || languageSelection.getSelectedItem().toString().equals("Suédois") || languageSelection.getSelectedItem().toString().equals("Schwedisch") || languageSelection.getSelectedItem().toString().equals("Svedese") || languageSelection.getSelectedItem().toString().equals("Sueco") || languageSelection.getSelectedItem().toString().equals("Шведский") || languageSelection.getSelectedItem().toString().equals("瑞典") || languageSelection.getSelectedItem().toString().equals("スウェーデンの") || languageSelection.getSelectedItem().toString().equals("Zweeds") || languageSelection.getSelectedItem().toString().equals("Szwedzki") || languageSelection.getSelectedItem().toString().equals("스웨덴어")|| languageSelection.getSelectedItem().toString().equals("Svenska")|| languageSelection.getSelectedItem().toString().equals("السويدية")|| languageSelection.getSelectedItem().toString().equals("स्वीडिश")|| languageSelection.getSelectedItem().toString().equals("سویڈش")){
                    selectedLang = "SV";
                }
                if (languageSelection.getSelectedItem().toString().equals("Arabe") || languageSelection.getSelectedItem().toString().equals("Arabic") || languageSelection.getSelectedItem().toString().equals("Arabe") || languageSelection.getSelectedItem().toString().equals("Arabisch") || languageSelection.getSelectedItem().toString().equals("Arabo") || languageSelection.getSelectedItem().toString().equals("Arabe") || languageSelection.getSelectedItem().toString().equals("арабский") || languageSelection.getSelectedItem().toString().equals("阿拉伯") || languageSelection.getSelectedItem().toString().equals("アラビア語") || languageSelection.getSelectedItem().toString().equals("Arabisch") || languageSelection.getSelectedItem().toString().equals("Arabski") || languageSelection.getSelectedItem().toString().equals("아라비아 말") || languageSelection.getSelectedItem().toString().equals("Arabiska") || languageSelection.getSelectedItem().toString().equals("عربى") || languageSelection.getSelectedItem().toString().equals("अरबी") || languageSelection.getSelectedItem().toString().equals("عربی")){
                    selectedLang = "AR";
                }
                if (languageSelection.getSelectedItem().toString().equals("Hindi") || languageSelection.getSelectedItem().toString().equals("Hindi") || languageSelection.getSelectedItem().toString().equals("Hindi") || languageSelection.getSelectedItem().toString().equals("Hindi") || languageSelection.getSelectedItem().toString().equals("Hindi") || languageSelection.getSelectedItem().toString().equals("Urdu") || languageSelection.getSelectedItem().toString().equals("хинди") || languageSelection.getSelectedItem().toString().equals("印地语") || languageSelection.getSelectedItem().toString().equals("ヒンディー語") || languageSelection.getSelectedItem().toString().equals("Hinduski") || languageSelection.getSelectedItem().toString().equals("Hindi") || languageSelection.getSelectedItem().toString().equals("힌디 어") || languageSelection.getSelectedItem().toString().equals("Hindi") || languageSelection.getSelectedItem().toString().equals("هندي") || languageSelection.getSelectedItem().toString().equals("हिन्दी") || languageSelection.getSelectedItem().toString().equals("ہندی")){
                    selectedLang = "HI";
                }
                if (languageSelection.getSelectedItem().toString().equals("Urdu") || languageSelection.getSelectedItem().toString().equals("Urdu") || languageSelection.getSelectedItem().toString().equals("Ourdou") || languageSelection.getSelectedItem().toString().equals("Urdu") || languageSelection.getSelectedItem().toString().equals("Ourdou") || languageSelection.getSelectedItem().toString().equals("Hindi") || languageSelection.getSelectedItem().toString().equals("Урду") || languageSelection.getSelectedItem().toString().equals("乌尔都语") || languageSelection.getSelectedItem().toString().equals("ウルドゥー語") || languageSelection.getSelectedItem().toString().equals("Urdu") || languageSelection.getSelectedItem().toString().equals("Urdu") || languageSelection.getSelectedItem().toString().equals("우르두어") || languageSelection.getSelectedItem().toString().equals("Urdu") || languageSelection.getSelectedItem().toString().equals("الأردية") || languageSelection.getSelectedItem().toString().equals("उर्दू") || languageSelection.getSelectedItem().toString().equals("اردو")){
                    selectedLang = "UR";
                }
                translanguage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        translanguage();
        setupLinkButton();

        pass_user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (pass_user.getRight() - pass_user.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (pass_user.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()){
                            pass_user.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            pass_user.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.showpass, 0);
                        }else{
                            pass_user.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            pass_user.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepass, 0);
                        }
                        return true;
                    }
                }

                return false;
            }
        });

        confirm_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (confirm_pass.getRight() - confirm_pass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (confirm_pass.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()){
                            confirm_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confirm_pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.showpass, 0);
                        }else{
                            confirm_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            confirm_pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hidepass, 0);
                        }
                        return true;
                    }
                }

                return false;
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = LocaleHelper.setLocale(getApplication(), selectedLang);
                resources = context.getResources();

                if (languageSelection.getSelectedItem().toString().equals(resources.getString(R.string.selectLanguage))){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.noLanguageSelected), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name_user.getText().toString()) || TextUtils.isEmpty(plate_user.getText().toString()) || TextUtils.isEmpty(email_user.getText().toString()) || TextUtils.isEmpty(pass_user.getText().toString()) || TextUtils.isEmpty(confirm_pass.getText().toString())){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.allfieldFilled), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (plate_user.getText().toString().length()<5 || plate_user.getText().toString().length()>10){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.wrong_format_plate), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!LoginErrorsValidation.isValid(pass_user.getText().toString())){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.wrong_password), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!pass_user.getText().toString().equals(confirm_pass.getText().toString())){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.noPassMatch), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!LoginErrorsValidation.emailValidation(email_user.getText().toString())){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.wrongEmailFormat), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!acceptButton.isChecked()){
                    Toast.makeText(getApplicationContext(),resources.getString(R.string.noTermsAccepted), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (btnRegister.getText().equals(resources.getString(R.string.backLogin))){
                    finish();
                    return;
                }
                final UsersConnected user = new UsersConnected(name_user.getText().toString(), plate_user.getText().toString().toUpperCase(),  email_user.getText().toString(),  pass_user.getText().toString(), cartype, carBrand,carColor,carModel,yearCar, resetPass);

                final ActivatedUser activated = new ActivatedUser("OFF", plate_user.getText().toString().toUpperCase());
                DatabaseReference userActivated = FirebaseDatabase.getInstance().getReference("Users/ActivatedUser");
                Users = FirebaseDatabase.getInstance().getReference("Users");
                Users.orderByChild("plate_user").equalTo(plate_user.getText().toString().toUpperCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            userPlate = plate_user.getText().toString().toUpperCase();
                            String id2 = userActivated.push().getKey();
                            String id = Users.push().getKey();
                            Users.child(id).setValue(user);
                            userActivated.child(id2).setValue(activated);
                            if (selectedLang.equals("ES")){
                                Toast.makeText(getApplicationContext(),"El usuario "+plate_user.getText().toString().toUpperCase()+" ha sido registrado, por favor revise su correo electrónico (Bandeja de entrada o SPAM) para activarlo.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("EN")){
                                Toast.makeText(getApplicationContext(),"Account "+plate_user.getText().toString().toUpperCase()+" has been registered, pleasae check your email (Inbox or SPAM) to activate it.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("FR")){
                                Toast.makeText(getApplicationContext(),"L'utilisateur "+ plate_user.getText().toString().toUpperCase() + "a été enregistré, vérifiez votre Email (Boîte de réception ou SPAM) pour l'activer.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("DE")){
                                Toast.makeText(getApplicationContext(),"Der Benutzer "+plate_user.getText().toString().toUpperCase() +" wurde registriert. Überprüfen Sie Ihre E-Mails (Posteingang oder SPAM), um sie zu aktivieren.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("IT")){
                                Toast.makeText(getApplicationContext(),"L'utente "+ plate_user.getText().toString().toUpperCase() +" è stato registrato, controlla la tua Email (Posta in arrivo o SPAM) per attivarlo.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("PT")){
                                Toast.makeText(getApplicationContext(),"O usuário "+ plate_user.getText ().toString().toUpperCase() +" foi registrado, por favor, revisar seu correio eletrônico (Bandeja de entrada o SPAM) para ativar.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("RU")){
                                Toast.makeText(getApplicationContext(),"Пользователь "+ plate_user.getText ().toString ().toUpperCase() +" был зарегистрирован, пожалуйста, проверьте свою электронную почту (Входящие или СПАМ), чтобы активировать его.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("ZH")){
                                Toast.makeText(getApplicationContext(),"用户 " + plate_user.getText().toString().toUpperCase()+" 已注册，请检查您的电子邮件（收件箱或垃圾邮件）以将其激活.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("JA")){
                                Toast.makeText(getApplicationContext(),"ユーザー "+ plate_user.getText().toString().toUpperCase()+" が登録されました。メール（受信トレイまたはスパム）をチェックしてアクティブにしてください。", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("NL")){
                                Toast.makeText(getApplicationContext(),"Gebruiker "+ plate_user.getText ().toString().toUpperCase() +" is geregistreerd, controleer uw e-mail (Inbox of SPAM) om deze te activeren.", Toast.LENGTH_LONG).show();
                            }

                            if (selectedLang.equals("PL")){
                                Toast.makeText(getApplicationContext(),"Użytkownik "+ plate_user.getText().toString().toUpperCase() +" został zarejestrowany, sprawdź swój adres e-mail (skrzynkę odbiorczą lub SPAM), aby go aktywować.", Toast.LENGTH_LONG).show();
                            }

                            if (selectedLang.equals("KO")){
                                Toast.makeText(getApplicationContext(),"사용자 "+ plate_user.getText().toString().toUpperCase() +"가 등록되었습니다. 활성화하려면 이메일 (받은 편지함 또는 스팸)을 확인하십시오.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("SV")){
                                Toast.makeText(getApplicationContext(),"Användaren "+ plate_user.getText().toString().toUpperCase() +" har registrerats, kontrollera din e-post (Inkorgen eller SPAM) för att aktivera den.", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("AR")){
                                 Toast.makeText(getApplicationContext(),plate_user.getText().toString().toUpperCase() +" ، يرجى التحقق من بريدك الإلكتروني (صندوق الوارد أو الرسائل الاقتحامية) لتنشيطه.",Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("HI")){
                                Toast.makeText(getApplicationContext(),"उपयोगकर्ता "+ plate_user.getText().toString().toUpperCase() +" पंजीकृत किया गया है, कृपया इसे सक्रिय करने के लिए अपना ईमेल (इनबॉक्स या स्पैम) देखें।", Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("UR")){
                                Toast.makeText(getApplicationContext(), plate_user.getText().toString().toUpperCase() +" رجسٹرڈ ہوچکا ہے ، براہ کرم اسے فعال کرنے کے لئے اپنا ای میل (ان باکس یا اسپیم) چیک کریں۔", Toast.LENGTH_LONG).show();
                            }

                            btnRegister.setHint(resources.getString(R.string.backLogin));
                            email_user.setEnabled(false);
                            plate_user.setEnabled(false);
                            pass_user.setEnabled(false);
                            confirm_pass.setEnabled(false);
                            languageSelection.setEnabled(false);
                            name_user.setEnabled(false);
                            acceptButton.setEnabled(false);
                            if (selectedLang.equals("ES")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),"Bienvenido/a a allConnected", sendWelcomeMessageES());
                            }
                            if (selectedLang.equals("EN")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(), resources.getString(R.string.welcomeHomeText), sendWelcomeMessageEN());

                            }
                            if (selectedLang.equals("FR")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageFR());
                            }
                            if (selectedLang.equals("DE")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageDE());
                            }
                            if (selectedLang.equals("IT")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageIT());
                            }
                            if (selectedLang.equals("PT")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessagePT());
                            }
                            if (selectedLang.equals("RU")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageRU());
                            }
                            if (selectedLang.equals("ZH")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageZH());
                            }
                            if (selectedLang.equals("JA")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageJA());
                            }
                            if (selectedLang.equals("NL")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageNL());
                            }

                            if (selectedLang.equals("PL")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessagePL());
                            }
                            if (selectedLang.equals("KO")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageKO());
                            }
                            if (selectedLang.equals("SV")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageSV());
                            }
                            if (selectedLang.equals("AR")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageAR());
                            }
                            if (selectedLang.equals("HI")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageHI());
                            }
                            if (selectedLang.equals("UR")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageUR());
                            }
                            createLanguageUser();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }else{
                            if (selectedLang.equals("ES")) {
                                Toast.makeText(getApplicationContext(), "La mátricula " + plate_user.getText().toString().toUpperCase() + " ya está registrada", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("EN")){
                                Toast.makeText(getApplicationContext(), "Plate number " + plate_user.getText().toString().toUpperCase() + " is already registered", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("FR")){
                                Toast.makeText(getApplicationContext(), "Numéro de plaque " + plate_user.getText().toString().toUpperCase() + " est déjà enregistré", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("DE")){
                                Toast.makeText(getApplicationContext(), "Kennzeichen "+ plate_user.getText ().toString().toUpperCase() + " ist bereits registriert", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("IT")){
                                Toast.makeText(getApplicationContext(), "Numero di targa " + plate_user.getText ().toString ().toUpperCase() + " è già registrato", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("PT")){
                                Toast.makeText(getApplicationContext(), "Número da placa " + plate_user.getText ().toString ().toUpperCase() + " já foi cadastrado", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("RU")){
                                Toast.makeText(getApplicationContext(), "Табличный номер "+ plate_user.getText ().toString ().toUpperCase() +" уже зарегистрирован.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("ZH")){
                                Toast.makeText(getApplicationContext(), "车牌号 " + plate_user.getText().toString().toUpperCase()+" 已经注册", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("JA")){
                                Toast.makeText(getApplicationContext(), "プレート番号 "+ plate_user.getText().toString().toUpperCase()+" はすでに登録されています", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("NL")){
                                Toast.makeText(getApplicationContext(), "Kenteken "+ plate_user.getText ().toString().toUpperCase() +" is al geregistreerd", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("PL")){
                                Toast.makeText(getApplicationContext(), "Tabliczka "+ plate_user.getText().toString().toUpperCase() +" jest już zarejestrowana", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("KO")){
                                Toast.makeText(getApplicationContext(), "플레이트 "+ plate_user.getText().toString().toUpperCase() +"가 이미 등록되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("SV")){
                                Toast.makeText(getApplicationContext(), "Plattan "+ plate_user.getText().toString().toUpperCase() +" är redan registrerad", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("AR")){
                                Toast.makeText(getApplicationContext()," اللوحة "+ plate_user.getText().toString().toUpperCase() +"\" مسجلة بالفعل,," ,  Toast.LENGTH_LONG).show();
                            }
                            if (selectedLang.equals("HI")){
                                Toast.makeText(getApplicationContext(), "प्लेट "+ plate_user.getText().toString().toUpperCase() +" पंजीकृत नहीं है", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLang.equals("UR")){
                                Toast.makeText(getApplicationContext(), "پلیٹ "+ plate_user.getText ().toString ().toUpperCase() +" پہلے ہی رجسٹرڈ ہے", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void createLanguageUser() {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()){
                        Log.w("TAG", "Not able to get token", task.getException());
                        return;
                    }
                    String mToken = task.getResult();
                    final languageUserData createUserLanguage = new languageUserData(mToken,selectedLang);
                    DatabaseReference createLanguageUser =  FirebaseDatabase.getInstance().getReference("Users/userLanguage");
                    createLanguageUser.push().setValue(createUserLanguage);
            }
        });
    }

    private void setupLinkButton() {
        linkButton.setTextColor(Color.BLUE);
        linkButton.setMovementMethod(LinkMovementMethod.getInstance());
        linkButton.setLinkTextColor(Color.BLUE);

        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(termsLink));
                startActivity(intent);
            }
        });
    }

    public String sendWelcomeMessageES(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Bienvenido/a a allConnected!!!</font></b><br><br>";
        headerMessage="Estimado Sr./Sra "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Gracias por registrarse en el mundo de allConnected. Para activar el usuario, haga click en este link: <a href=http://epicdevelopers.es?ES"+plate_user.toString().toUpperCase()+">Activar usuario</a> <br><br>";
        farewellMessage="Un saludo cordial,<br>El equipo de allConnected.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageEN(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Welcome to allConnected!!!</font></b><br><br>";
        headerMessage="Dear Mr/Mrs. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Thanks for regisgtering to allConnected world. To activate your account, click on this link: <a href=http://epicdevelopers.es?EN"+plate_user.getText().toString().toUpperCase()+">Activate account</a> <br><br>";
        farewellMessage="Truly yours,<br>allConnected Team.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageFR(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Bienvenue à allConnected!!!</font></b><br><br>";
        headerMessage="Cher Monsieur / Madame. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Merci de vous enregistrer dans le monde allConnected. Pour activer votre utilisateur, cliquez sur ce lien: <a href=http://epicdevelopers.es?FR"+plate_user.getText().toString().toUpperCase()+">Activer l'utilisateur</a> <br><br>";
        farewellMessage="Cordialement,<br>L'équipe allConnected.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageIT(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Benvenuto in allConnected!!!</font></b><br><br>";
        headerMessage="Caro signor/signora "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Grazie per esserti registrato a allConnected world. Per attivare il tuo utente, fare clic su questo collegamento: <a href=http://epicdevelopers.es?IT"+plate_user.getText().toString().toUpperCase()+">Attiva utente</a> <br><br>";
        farewellMessage="Cordiali saluti,<br>Il team allConnected.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageDE(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Willkommen bei allConnected!!!</font></b><br><br>";
        headerMessage="Sehr geehrter Herr/Frau "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Vielen Dank, dass Sie sich bei allConnected world registriert haben. Kopieren Sie diesen Aktivierungscode, klicken Sie auf diesen Link: <a href=http://epicdevelopers.es?DE"+plate_user.getText().toString().toUpperCase()+">Benutzer aktivieren</a> <br><br>";
        farewellMessage="Mit freundlichen Grüßen,<br>Das allConnected-Team.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessagePT(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Bem-vindo a allConnected!!!</font></b><br><br>";
        headerMessage="Prezado Sr./Sra. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Obrigado por se registrar no mundo de allConnected. Para ativar o usuário, clique neste link: <a href=http://epicdevelopers.es?PT"+plate_user.getText().toString().toUpperCase()+">Ativar usuário</a> <br><br>";
        farewellMessage="Sinceramente,<br>A equipe allConnected.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageRU(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Добро пожаловать в allConnected!!!</font></b><br><br>";
        headerMessage="Уважаемый господин/госпожа "+ name_user.getText (). toString ()+"<br><br>";
        bodyMessage="Спасибо за регистрацию в мире allConnected. Чтобы активировать свою учетную запись, нажмите на эту ссылку: <a href=http://epicdevelopers.es?RU"+plate_user.getText().toString().toUpperCase()+">Активировать пользователя</a> <br><br>";
        farewellMessage="Искренне,<br>Команда allConnected.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageZH(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">欢迎来到allConnected!!!</font></b><br><br>";
        headerMessage="亲爱的先生/夫人： " + name_user.getText().toString()+"<br><br>";
        bodyMessage="感谢您注册到所有连接的世界。要激活您的帐户，点击此链接:<a href=http:/epicdevelopers.es?ZH"+plate_user.getText().toString().toUpperCase()+">激活帐户</a> <br><br>";
        farewellMessage="亲切的问候,<br>该团队的 allConnected。";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageJA(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">allConnectedへようこそ!!!</font></b><br><br>";
        headerMessage="親愛なるミスター/ミセス"+ name_user.getText().toString()+"<br><br>";
        bodyMessage="allConnectedworldに登録していただきありがとうございます。アカウントをアクティブ化するには、このリンクをクリックしてください：<a href=http:/epicdevelopers.es?JA"+plate_user.getText().toString().toUpperCase()+">ユーザーのアクティブ化</a> <br><br>";
        farewellMessage="心から,<br>allConnectedチーム。";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageNL(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Welkom bij allConnected!!!</font></b><br><br>";
        headerMessage="Beste meneer/mevrouw. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Bedankt voor uw aanmelding bij allConnected world. Om uw account te activeren, klikt u op deze link: <a href=http://epicdevelopers.es?NL"+plate_user.getText().toString().toUpperCase()+"> Activeer gebruiker</a> <br><br>";
        farewellMessage="Oprecht,<br>Het allConnected -team.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }
    public String sendWelcomeMessagePL(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Witamy w allConnected!!!</font></b><br><br>";
        headerMessage="Szanowny Panie/Pani.. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Bedankt voor uw aanmelding bij allConnected world. Om uw account te activren, kliknij ten link: <a href=http://epicdevelopers.es?PL"+plate_user.toString().toUpperCase()+">Activeer gebruiker</a> <br><br>";
        farewellMessage="Z poważaniem,<br>Zespół allConnected.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageKO(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">allConnected에 오신 것을 환영합니다!!!</font></b><br><br>";
        headerMessage="친애하는 Mr./Ms. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="allConnected의 세계에 등록 해 주셔서 감사합니다. 계정을 활성화하려면이 링크를 클릭하십시오.: <a href= http://epicdevelopers.es?KO"+plate_user.toString().toUpperCase()+"> 사용자 활성화</a> <br><br>";
        farewellMessage="진정으로,<br>allConnected 팀.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageSV(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Välkommen till allConnected!!!</font></b><br><br>";
        headerMessage="Kära herr/fru. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Tack för att du registrerade dig i allConnected-världen. För att aktivera användaren, klicka på den här länken: <a href=http://epicdevelopers.es?SV"+plate_user.toString().toUpperCase()+"> Aktivera användare</a> <br><br>";
        farewellMessage="vänliga hälsningar,<br>AllConnected-teamet.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageHI(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">allConnected में आपका स्वागत है!!!</font></b><br><br>";
        headerMessage="प्रिय श्री / एम। एस। "+name_user.getText().toString()+"<br><br>";
        bodyMessage="allConnected दुनिया के लिए regisgtering के लिए धन्यवाद। अपने खाते को सक्रिय करने के लिए, इस लिंक पर क्लिक करें: <a href=http://epicdevelopers.es?HI"+plate_user.toString().toUpperCase()+"> उपयोगकर्ता को सक्रिय करें</a> <br><br>";
        farewellMessage="ईमानदारी से,<br>allConnected टीम.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }
    public String sendWelcomeMessageAR(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">" +
                "مرحبًا بك في allConnected"+"!!!</font></b><br><br>";
        headerMessage="" +
                "عزيزي السيد / السيدة."
                +name_user.getText().toString()+"<br><br>";
        bodyMessage= "نشكرك على التسجيل في allConnected world. لتنشيط حسابك ، انقر فوق هذا الارتباط"+
                "<a href=http://epicdevelopers.es?AR"+plate_user.toString().toUpperCase()+">"+  "تنشيط المستخدم" +"</a> <br><br>" ;
        farewellMessage="بإخلاص"+"<br"+"فريق allConnected";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }
    public String sendWelcomeMessageUR(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">"+"allCnnected میں خوش آمدید"+"</font></b><br><br>";
        headerMessage=" محترم جناب / محترمہ "+name_user.getText().toString()+"<br><br>";
        bodyMessage = "allConnected دنیا میں اندراج کرنے کا شکریہ۔ اپنے اکاؤنٹ کو چالو کرنے کے لئے ، اس لنک پر کلک کریں"+"<a href=http://epicdevelopers.es?UR"+plate_user.toString().toUpperCase()+">"+"صارف کو چالو کریں"+"</a> <br><br>";
        farewellMessage="مخلص,"+"<br>"+"آل سے منسلک ٹیم۔";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }


    public void translanguage(){
        context = LocaleHelper.setLocale(newuser.this, selectedLang);
        resources = context.getResources();
        name_user.setHint(resources.getString(R.string.name));
        plate_user.setHint(resources.getString(R.string.plate_name));
        email_user.setHint(resources.getString(R.string.email_name));
        pass_user.setHint(resources.getString(R.string.password_name));
        confirm_pass.setHint(resources.getString(R.string.confirm_password));
        btnRegister.setHint(resources.getString(R.string.register));
        newUserLabel.setText(resources.getString(R.string.new_user));
        linkButton.setText(resources.getString(R.string.terms_privacy));
        acceptButton.setText(resources.getString(R.string.accept_terms));
        textView2.setText(resources.getString(R.string.password_strong));

        if (selectedLang.equals("ES")){
            acceptButton.setTextSize(9);
            linkButton.setTextSize(10);
        //    linkButton.setY(1670);
         //   linkButton.setX(450);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/spanish";
        }
        if (selectedLang.equals("EN")){
            acceptButton.setTextSize(9);
            linkButton.setTextSize(12);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/english";
        }
        if (selectedLang.equals("FR")){
            acceptButton.setTextSize(9);
            linkButton.setTextSize(9);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/frances";
        }
        if (selectedLang.equals("DE")){
            acceptButton.setTextSize(7);
            linkButton.setTextSize(7);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/aleman";
        }
        if (selectedLang.equals("IT")){
            acceptButton.setTextSize(12);
            linkButton.setTextSize(10);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/italiano";
        }
        if (selectedLang.equals("PT")){
            acceptButton.setTextSize(12);
            linkButton.setTextSize(9);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/portugues";
        }
        if (selectedLang.equals("RU")){
            acceptButton.setTextSize(13);
            linkButton.setTextSize(9);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/ruso";
        }
        if (selectedLang.equals("ZH")){
            acceptButton.setTextSize(13);
            linkButton.setTextSize(14);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/chino";
        }
        if (selectedLang.equals("JA")){
            acceptButton.setTextSize(14);
            linkButton.setTextSize(12);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/japones";
        }
        if (selectedLang.equals("NL")){
            acceptButton.setTextSize(19);
            linkButton.setTextSize(11);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/holandes";
        }

        if (selectedLang.equals("KO")) {
            termsLink= "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/coreano";
        }
        if (selectedLang.equals("PL")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/polaco";
        }
        if (selectedLang.equals("SV")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/sueco";
        }
        if (selectedLang.equals("AR")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/arabe";
        }
        if (selectedLang.equals("UR")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/urdu";
        }
        if (selectedLang.equals("HI")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/hindi";
        }

        languageSelection.setAdapter(new ArrayAdapter<String>(newuser.this, R.layout.spinner_items,resources.getStringArray(R.array.languageSelected)));
    }


}
