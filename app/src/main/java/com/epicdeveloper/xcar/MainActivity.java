package com.epicdeveloper.xcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static String userSelected;
    public static String UserSel;

    SpannableStringBuilder newLinkBlueText;
    SpannableStringBuilder newforgottenPass;
    public static int screens;
    public static int fromNotifications;
    public static String userlanguage;
    public static int chatScreen;
    public static int getBackGround = 0;
    public EditText mUser, mPassword;
    public static String chatUser;
    public TextView welcomeTextView,forgottenPass,newUserLink;
    public Button btnLogin;
    public static String email_user;
    public static int init = 0;
    public static int profileView=0;
    public static String plate_user;
    private int progresStatus = 0;
    public static String passwordFind;
    public static String emailSelected;
    public static String typeCarUser;
    public static String brandCarUser;
    public static String modelCaruser;
    public static String colorCarUser;
    public static String yearCarUser;
    public static String resetPassUser;
    public static int getInit = 0;
    public static int getProfileView=0;
    ProgressBar progressBar;
    private final Handler handler = new Handler();
    public String mToken;
    public static MainActivity instance = new MainActivity();
    static Context context;
    Resources resources;


    public static MainActivity getInstance(Context ctx){
        context = ctx.getApplicationContext();
        return instance;
    }

    private DatabaseReference Users;

    public void getInterfaceId(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()){
                    Log.w("TAG", "Not able to get token", task.getException());
                    return;
                }
                mToken = task.getResult();
                System.out.println("El Tocken es: " + mToken);
                getSelectedLanguage(mToken);
            }
        });

    }

    @Override
    public void onPause(){
        profileView=getProfileView;
        init=getInit;
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme_SplashTheme);
        MobileAds.initialize(this, initializationStatus -> {}
                );
        profileView=0;
        init=0;

        if (!MainActivity.getInstance(this).isOnline(this)){
            context = LocaleHelper.setLocale(this, Locale.getDefault().getLanguage().toUpperCase());
            resources = context.getResources();

            Toast.makeText(this,resources.getString(R.string.noConexion), Toast.LENGTH_LONG).show();

        }else{
            MobileAds.initialize(this, initializationStatus ->
            {});
            if (getProfileView==2 && getInit==1){

                Intent intent = new Intent(getApplicationContext(), profile_activity.class);
                startActivity(intent);
            }else{
                //setTheme(R.style.AppTheme_SplashTheme);
                onStart();
            }
        }


    }


    @SuppressLint("ObsoleteSdkInt")
    public boolean isOnline(Context context){
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService((Context.CONNECTIVITY_SERVICE));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                final Network n = connectivityManager.getActiveNetwork();
                if (n != null) {
                    final NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(n);
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }


        return false;
    }


    @Override
    public void onRestart(){
        if (!MainActivity.getInstance(this).isOnline(this)){
            Toast.makeText(this,getString(R.string.noConexion), Toast.LENGTH_LONG).show();
        }
        if (init ==1 && profileView==0) {
            getInterfaceId();
        }
        if (init==1 && profileView==1){
            finish();
        }

        if (init==0 && profileView==1) {

                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()){
                            Log.w("TAG", "Not able to get token", task.getException());
                            return;
                        }
                        mToken = task.getResult();
                    DatabaseReference getLanguage = FirebaseDatabase.getInstance().getReference("Users/userLanguage");
                    getLanguage.orderByChild("Users").equalTo(mToken).addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String lang = "";
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    lang = ds.getValue().toString();
                                }
                                userlanguage = lang;
                            } else {
                                userlanguage= Locale.getDefault().getLanguage().toUpperCase();
                            }
                            setContentView(R.layout.activity_main);
                            progressBar = findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.GONE);
                            mUser = findViewById(R.id.name_user);
                            mPassword = findViewById(R.id.password_user);
                            welcomeTextView = findViewById(R.id.welcomeText);
                            forgottenPass = findViewById(R.id.forgottenPass);
                            newUserLink = findViewById(R.id.newUser);
                            email_user = mUser.getText().toString();
                            btnLogin =  findViewById(R.id.loginButton);
                            //setFieldsLanguage(userlanguage);
                            mPassword.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    final int DRAWABLE_RIGHT = 2;
                                    if(event.getAction() == MotionEvent.ACTION_UP) {
                                        if(event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                            if (mPassword.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()){
                                                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                mPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24, 0, R.drawable.showpass, 0);
                                            }else{
                                                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                                mPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24, 0, R.drawable.hidepass, 0);
                                            }
                                            return true;
                                        }
                                    }

                                    return false;
                                }
                            });
                            btnLogin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    signIn(mUser.getText().toString().toUpperCase(), mPassword.getText().toString(), mToken, userlanguage);
                                }

                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }
        super.onRestart();
    }



    @Override
    public void onDestroy(){
        profileView=0;
        init =1;
        getBackGround = 0;
        super.onDestroy();
    }



    private void signIn(final String userEmail, final String password, String mToken, String language) {
        email_user = mUser.getText().toString();
        context = LocaleHelper.setLocale(getApplication(), userlanguage);
        resources = context.getResources();
        if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(password) || password.length() < 8) {
            Toast.makeText(this,R.string.wrong_user, Toast.LENGTH_SHORT).show();
            return;
        } else {
            userSelected=email_user;

            String dot1 = new String (mUser.getText().toString());
            String dot2 = dot1.replace(".","_");
            Users = FirebaseDatabase.getInstance().getReference("Users/"+dot2);

            Users.orderByChild("typel").equalTo("M").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        if (!TextUtils.isEmpty(userEmail)) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                passwordFind = snapshot.child("user_password").getValue().toString();
                                UserSel = snapshot.child("user_name").getValue().toString();
                                plate_user = snapshot.child("plate_user").getValue().toString();
                                emailSelected = snapshot.child("user_email").getValue().toString();
                                typeCarUser = snapshot.child("cartype").getValue().toString();
                                brandCarUser = snapshot.child("carbrand").getValue().toString();
                                modelCaruser = snapshot.child("carmodel").getValue().toString();
                                colorCarUser = snapshot.child("carcolor").getValue().toString();
                                yearCarUser = snapshot.child("year").getValue().toString();
                                resetPassUser = snapshot.child("resetPass").getValue().toString();
                            }
                        }
                        Users = FirebaseDatabase.getInstance().getReference("Users/ActivatedUser");
                        Users.orderByChild("emailActivated").equalTo(email_user).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String userActive = "";
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        userActive = ds.child("activated").getValue().toString();
                                    }
                                    if (userActive.equals("OFF")) {
                                        if (userlanguage.toUpperCase().equals("ES")) {
                                            Toast.makeText(getApplicationContext(), "El usuario con el " + email_user + " no está activado. Vaya al email (Bandeja de Entrada o Spam) y activelo en el link Activar Usuario", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("EN")){
                                            Toast.makeText(getApplicationContext(), "Account with " + email_user + " is not activated. Go to your email(Inbox or Spam) and activate it in the link Activate Account", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("FR")){
                                            Toast.makeText(getApplicationContext(), "L'utilisateur avec" + email_user.toUpperCase() + " n'est pas activé. Accédez à votre messagerie (Boîte de réception ou Spam) et activez-la dans le lien Activer l'utilisateur", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("DE")){
                                            Toast.makeText(getApplicationContext(), "Benutzer " + email_user + " ist nicht aktiviert. Gehen Sie zu Ihrer E-Mail (Posteingang oder Spam) und aktivieren Sie sie über den Link Benutzer aktivieren", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("IT")){
                                            Toast.makeText(getApplicationContext(), "L'utente " + email_user + " non è attivato. Vai alla tua e-mail (Posta in arrivo o Spam) e attivala nel collegamento Attiva utente", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("PT")){
                                            Toast.makeText(getApplicationContext(), "O usuário " + email_user + " não está ativado. Acesse o e-mail (Caixa de entrada ou Spam) e ative-o no link Ativar usuário", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("RU")) {
                                            Toast.makeText(getApplicationContext(), "Пользователь "+ email_user  + " не активирован. Перейдите к письму (Входящие или Спам) и активируйте его по ссылке Активировать пользователя.", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("ZH")) {
                                            Toast.makeText(getApplicationContext(), "用户 "+ email_user +" 未激活。转到电子邮件（收件箱或垃圾邮件），然后在“激活用户”链接中将其激活。", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("JA")) {
                                            Toast.makeText(getApplicationContext(), "ユーザー "+ email_user+" はアクティブ化されていません。電子メール（受信トレイまたはスパム）に移動し、[ユーザーのアクティブ化]リンクでアクティブ化します。", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("NL")) {
                                            Toast.makeText(getApplicationContext(), "De gebruiker "+ email_user +" is niet geactiveerd. Ga naar de e-mail (Inbox of Spam) en activeer deze in de link Gebruiker activeren.", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("PL")) {
                                            Toast.makeText(getApplicationContext(), "Użytkownik " + email_user +" nie jest aktywowany. Przejdź do e-maila (Skrzynka odbiorcza lub Spam) i aktywuj go w linku Aktywuj użytkownika.", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("KO")) {
                                            Toast.makeText(getApplicationContext(), "사용자 "+ email_user +" 가 활성화되지 않았습니다. 이메일 (받은 편지함 또는 스팸)로 이동하여 사용자 활성화 링크에서 활성화합니다.", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("SV")) {
                                            Toast.makeText(getApplicationContext(), "Användaren "+ email_user +" är inte aktiverad. Gå till e-postmeddelandet (inkorg eller skräppost) och aktivera det i länken Aktivera användare.", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("AR")) {
                                            Toast.makeText(getApplicationContext(), "المستخدم " + email_user + " لم يتم تفعيله. انتقل إلى البريد الإلكتروني (صندوق الوارد أو البريد العشوائي) وقم بتنشيطه في رابط تنشيط المستخدم.", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("UR")) {
                                            Toast.makeText(getApplicationContext(), "صارف " + email_user + " چالو نہیں ہے۔ ای میل پر جائیں (ان باکس یا اسپام) اور اسے چالو کرنے والے صارف کے لنک میں چالو کریں۔", Toast.LENGTH_LONG).show();
                                        }
                                        if (userlanguage.toUpperCase().equals("HI")) {
                                            Toast.makeText(getApplicationContext(), "उपयोगकर्ता "+ email_user +" सक्रिय नहीं है। ईमेल (इनबॉक्स या स्पैम) पर जाएं और इसे सक्रिय उपयोगकर्ता लिंक में सक्रिय करें।", Toast.LENGTH_LONG).show();
                                        }

                                        return;
                                    } else {
                                        if (passwordFind.equals(password)) {
                                            if (resetPassUser.equals("1")) {
                                                Intent intent = new Intent(getApplicationContext(), activity_changeresetpassword.class);
                                                startActivity(intent);
                                                return;
                                            } else {
                                                profileView=0;
                                                init =1;
                                                activeSessionCreate(mToken);
                                                pushCreate(plate_user);
                                                progressBar.setVisibility(View.VISIBLE);
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        while (progresStatus <= 100) {
                                                            handler.post(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    progressBar.setProgress(progresStatus);
                                                                }
                                                            });
                                                            try {
                                                                Thread.sleep(200);
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }
                                                            progresStatus += 1;
                                                        }
                                                    }
                                                }).start();
                                                Intent intent = new Intent(getApplicationContext(), profile_activity.class);
                                                startActivity(intent);
                                                mUser.setText("");
                                                mPassword.setText("");
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), resources.getString(R.string.wrong_user), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }
                                else {
                                    if (userlanguage.toUpperCase().equals("ES")) {
                                        Toast.makeText(getApplicationContext(), "El usuario con el email" + email_user + " no está activado. Vaya al email (Bandeja de Entrada o Spam) y activelo en el link Activar Usuario", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("EN")){
                                        Toast.makeText(getApplicationContext(), "Account with email " + email_user + " is not activated. Go to your email(Inbox or Spam) and activate it in the link Activate Account", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("FR")){
                                        Toast.makeText(getApplicationContext(), "L'utilisateur avec" + email_user + " n'est pas activé. Accédez à votre messagerie (Boîte de réception ou Spam) et activez-la dans le lien Activer l'utilisateur", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("DE")){
                                        Toast.makeText(getApplicationContext(), "Benutzer " + email_user + " ist nicht aktiviert. Gehen Sie zu Ihrer E-Mail (Posteingang oder Spam) und aktivieren Sie sie über den Link Benutzer aktivieren", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("IT")){
                                        Toast.makeText(getApplicationContext(), "L'utente " + email_user + " non è attivato. Vai alla tua e-mail (Posta in arrivo o Spam) e attivala nel collegamento Attiva utente", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("PT")){
                                        Toast.makeText(getApplicationContext(), "O usuário" + email_user + "não está ativado. Acesse o e-mail (Caixa de entrada ou Spam) e ative-o no link Ativar usuário", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("RU")) {
                                        Toast.makeText(getApplicationContext(), "Пользователь "+ email_user + " не активирован. Перейдите к письму (Входящие или Спам) и активируйте его по ссылке Активировать пользователя.", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("ZH")) {
                                        Toast.makeText(getApplicationContext(), "用户 "+ email_user +" 未激活。转到电子邮件（收件箱或垃圾邮件），然后在“激活用户”链接中将其激活。", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("JA")) {
                                        Toast.makeText(getApplicationContext(), "ユーザー "+ email_user+" はアクティブ化されていません。電子メール（受信トレイまたはスパム）に移動し、[ユーザーのアクティブ化]リンクでアクティブ化します。", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("NL")) {
                                        Toast.makeText(getApplicationContext(), "De gebruiker "+ email_user +" is niet geactiveerd. Ga naar de e-mail (Inbox of Spam) en activeer deze in de link Gebruiker activeren.", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("PL")) {
                                        Toast.makeText(getApplicationContext(), "Użytkownik " + email_user +" nie jest aktywowany. Przejdź do e-maila (Skrzynka odbiorcza lub Spam) i aktywuj go w linku Aktywuj użytkownika.", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("KO")) {
                                        Toast.makeText(getApplicationContext(), "사용자 "+ email_user +" 가 활성화되지 않았습니다. 이메일 (받은 편지함 또는 스팸)로 이동하여 사용자 활성화 링크에서 활성화합니다.", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("SV")) {
                                        Toast.makeText(getApplicationContext(), "Användaren "+ email_user +" är inte aktiverad. Gå till e-postmeddelandet (inkorg eller skräppost) och aktivera det i länken Aktivera användare.", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("AR")) {
                                        Toast.makeText(getApplicationContext(), "المستخدم " + email_user + " لم يتم تفعيله. انتقل إلى البريد الإلكتروني (صندوق الوارد أو البريد العشوائي) وقم بتنشيطه في رابط تنشيط المستخدم.", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("UR")) {
                                        Toast.makeText(getApplicationContext(), "صارف " + email_user + " چالو نہیں ہے۔ ای میل پر جائیں (ان باکس یا اسپام) اور اسے چالو کرنے والے صارف کے لنک میں چالو کریں۔", Toast.LENGTH_LONG).show();
                                    }
                                    if (userlanguage.toUpperCase().equals("HI")) {
                                        Toast.makeText(getApplicationContext(), "उपयोगकर्ता "+ email_user +" सक्रिय नहीं है। ईमेल (इनबॉक्स या स्पैम) पर जाएं और इसे सक्रिय उपयोगकर्ता लिंक में सक्रिय करें।", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),resources.getString(R.string.wrong_user), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    @Override
    public void onStart(){
        getBackGround = 2;

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()){
                        Log.w("TAG", "Not able to get token", task.getException());
                        return;
                    }
                    mToken = task.getResult();
                DatabaseReference getLanguage = FirebaseDatabase.getInstance().getReference("Users/userLanguage");
                getLanguage.orderByChild("Users").equalTo(mToken).addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String lang = "";
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                lang = ds.getValue().toString();
                            }
                            userlanguage = lang;
                        } else {
                            userlanguage = Locale.getDefault().getLanguage().toUpperCase();
                        }
                        context = LocaleHelper.setLocale(MainActivity.this, userlanguage);
                        resources = context.getResources();
                        if (!MainActivity.getInstance(getApplication()).isOnline(getApplication())) {
                            Toast.makeText(getApplication(), resources.getString(R.string.noConexion), Toast.LENGTH_LONG).show();
                        }
                        if (getProfileView == 1 && getInit == 2) {
                            Intent intent = new Intent(getApplicationContext(), profile_activity.class);
                            startActivity(intent);
                        }
                        if (init == 0 && profileView == 0) {
                            getInterfaceId();
                        }
                        if (init == 0 && profileView == 1) {
                            setContentView(R.layout.activity_main);
                            progressBar = findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.GONE);
                            mUser = findViewById(R.id.name_user);
                            mPassword = findViewById(R.id.password_user);
                            welcomeTextView = findViewById(R.id.welcomeText);
                            forgottenPass = findViewById(R.id.forgottenPass);
                            newUserLink = findViewById(R.id.newUser);
                            email_user = mUser.getText().toString();
                            btnLogin = findViewById(R.id.loginButton);
                            setFieldsLanguage(userlanguage);
                            mPassword.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    final int DRAWABLE_LEFT = 0;
                                    final int DRAWABLE_TOP = 1;
                                    final int DRAWABLE_RIGHT = 2;
                                    final int DRAWABLE_BOTTOM = 3;

                                    if (event.getAction() == MotionEvent.ACTION_UP) {
                                        if (event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                            if (mPassword.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                                                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                mPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24, 0, R.drawable.showpass, 0);
                                            } else {
                                                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                                mPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24, 0, R.drawable.hidepass, 0);
                                            }
                                            return true;
                                        }
                                    }

                                    return false;
                                }
                            });
                            btnLogin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    signIn(mUser.getText().toString().toUpperCase(), mPassword.getText().toString(), mToken, userlanguage);
                                }
                            });
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });

        super.onStart();
    }

    private void activeSessionCreate(final String mToken) {
        final sessionActiveUser activeSessionUser = new sessionActiveUser(email_user, "ON");
        final DatabaseReference sessionActiveDb = FirebaseDatabase.getInstance().getReference("activeSession/"+mToken);
        sessionActiveDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    upDateSessionActive(mToken);
                }else{
                    String id = sessionActiveDb.push().getKey();
                    assert id != null;
                    sessionActiveDb.child(id).setValue(activeSessionUser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pushCreate(final String plateUser) {
        final pushNotification pushNotification = new pushNotification("ON",mToken);
        final DatabaseReference pushdb = FirebaseDatabase.getInstance().getReference("pushNotification/"+plateUser);
        pushdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    String id = pushdb.push().getKey();
                    assert id != null;
                    pushdb.child(id).setValue(pushNotification);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void resetPass(View view){
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()){
                        Log.w("TAG", "Not able to get token", task.getException());
                        return;
                    }
                    mToken = task.getResult();
                 DatabaseReference getLanguage = FirebaseDatabase.getInstance().getReference("Users/userLanguage");
                getLanguage.orderByChild("Users").equalTo(mToken).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String lang = "";
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                lang = ds.child("language").getValue().toString();
                            }
                            userlanguage = lang;
                        } else {
                            userlanguage = Locale.getDefault().getLanguage().toUpperCase();
                        }
                        Intent intent = new Intent(getApplication(), ResetPass.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    public void newUserFunc(View view) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()){
                        Log.w("TAG", "Not able to get token", task.getException());
                        return;
                    }
                    mToken = task.getResult();
                 DatabaseReference getLanguage = FirebaseDatabase.getInstance().getReference("Users/userLanguage");
                getLanguage.orderByChild("Users").equalTo(mToken).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String lang = "";
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                lang = ds.child("language").getValue().toString();
                            }
                            userlanguage = lang;
                        } else {
                            userlanguage = Locale.getDefault().getLanguage().toUpperCase();
                        }
                        Intent intent = new Intent(getApplication(), newuser.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed(){
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onStop(){
        profileView=getProfileView;
        init=getInit;
        super.onStop();

    }

    public void getSessionActive(String mToken, String language){
        DatabaseReference sessionActive = FirebaseDatabase.getInstance().getReference("activeSession/"+mToken);
        sessionActive.orderByChild("active").equalTo("ON").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        email_user = ds.child("activeUser").getValue().toString();
                    }
                    if (!TextUtils.isEmpty(email_user)) {
                        getDataUserSession(email_user);
                        userSelected = email_user;
                        Intent intent = new Intent(getApplicationContext(), profile_activity.class);
                        startActivity(intent);
                        init = 1;
                    }else{
                        setContentView(R.layout.activity_main);
                        progressBar = findViewById(R.id.progressBar);
                        progressBar.setVisibility(View.GONE);
                        mUser =  findViewById(R.id.name_user);
                        mPassword =  findViewById(R.id.password_user);
                        mUser =  findViewById(R.id.name_user);
                        mPassword =  findViewById(R.id.password_user);
                        welcomeTextView =  findViewById(R.id.welcomeText);
                        forgottenPass =  findViewById(R.id.forgottenPass);
                        newUserLink =  findViewById(R.id.newUser);
                        email_user = mUser.getText().toString();
                        btnLogin =  findViewById(R.id.loginButton);
                        mUser.setHint(resources.getString(R.string.plateHint));
                        mPassword.setHint(resources.getString(R.string.passHint));
                        welcomeTextView.setText(resources.getString(R.string.welcomeHomeText));
                        forgottenPass.setText(resources.getString(R.string.forgottenPass));
                        newLinkBlueText = new SpannableStringBuilder(resources.getString(R.string.newUser));
                        int colorBlue = getResources().getColor(R.color.purple_700);
                        ForegroundColorSpan blueLink = new ForegroundColorSpan(colorBlue);
                        newLinkBlueText.setSpan(blueLink,2,10, 0);
                        newUserLink.setText(newLinkBlueText);
                        mPassword.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                final int DRAWABLE_LEFT = 0;
                                final int DRAWABLE_TOP = 1;
                                final int DRAWABLE_RIGHT = 2;
                                final int DRAWABLE_BOTTOM = 3;

                                if(event.getAction() == MotionEvent.ACTION_UP) {
                                    if(event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                        if (mPassword.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()){
                                            mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                            mPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24, 0, R.drawable.showpass, 0);
                                        }else{
                                            mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                            mPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24, 0, R.drawable.hidepass, 0);
                                        }
                                        return true;
                                    }
                                }

                                return false;
                            }
                        });
                        btnLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                signIn(mUser.getText().toString().toUpperCase(), mPassword.getText().toString(), mToken,userlanguage);
                            }
                        });
                    }
                }else{
                    setContentView(R.layout.activity_main);
                    progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                    mUser =  findViewById(R.id.name_user);
                    mPassword = findViewById(R.id.password_user);
                    mUser = findViewById(R.id.name_user);
                    mPassword = findViewById(R.id.password_user);
                    welcomeTextView = findViewById(R.id.welcomeText);
                    forgottenPass = findViewById(R.id.forgottenPass);
                    newUserLink = findViewById(R.id.newUser);
                    email_user = mUser.getText().toString();
                    btnLogin = findViewById(R.id.loginButton);
                    mPassword.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;

                            if(event.getAction() == MotionEvent.ACTION_UP) {
                                if(event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                    if (mPassword.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()){
                                        mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                        mPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24, 0, R.drawable.showpass, 0);
                                    }else{
                                        mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                        mPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_24, 0, R.drawable.hidepass, 0);
                                    }
                                    return true;
                                }
                            }

                            return false;
                        }
                    });

                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            signIn(mUser.getText().toString().toUpperCase(), mPassword.getText().toString(), mToken, userlanguage);
                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getSelectedLanguage(String mToken) {
        DatabaseReference getLanguage = FirebaseDatabase.getInstance().getReference("Users/userLanguage");
        getLanguage.orderByChild("users").equalTo(mToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String lang = "";
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        lang = ds.child("language").getValue().toString();
                    }
                    userlanguage = lang;
                } else {
                    userlanguage = Locale.getDefault().getLanguage().toUpperCase();
                }
                context = LocaleHelper.setLocale(MainActivity.this,userlanguage);
                Locale locale = new Locale(userlanguage);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                getSessionActive(mToken, userlanguage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setFieldsLanguage(String language) {
        context = LocaleHelper.setLocale(MainActivity.this, userlanguage);
        resources = context.getResources();
        mUser.setHint(resources.getString(R.string.email_name));
        mPassword.setHint(resources.getString(R.string.passHint));
        welcomeTextView.setText(resources.getString(R.string.welcomeHomeText));
        int colorBlue = getResources().getColor(R.color.purple_700);
        newforgottenPass = new SpannableStringBuilder (resources.getString(R.string.forgottenPass));
        ForegroundColorSpan blueLink = new ForegroundColorSpan(colorBlue);
        newforgottenPass.setSpan(blueLink, 2, 10,0);
        newLinkBlueText = new SpannableStringBuilder(resources.getString(R.string.newUser));
        newLinkBlueText.setSpan(blueLink,2,10, 0);
    }

    public static void getDataUserSession(String plateUser) {
        String dot1 = new String (email_user);
        String dot2 = dot1.replace(".","_");
        DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users/"+dot2);
        Users.orderByChild("type").equalTo("M").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        plate_user = snapshot.child("plate_user").getValue().toString();
                        UserSel = snapshot.child("user_name").getValue().toString();
                        emailSelected= snapshot.child("user_email").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        profileView=getProfileView;
        init=getInit;
        getBackGround = 2;
     if (!MainActivity.getInstance(this).isOnline(this)){
            Toast.makeText(this,resources.getString(R.string.noConexion), Toast.LENGTH_LONG).show();
            //setTheme(R.style.AppTheme_SplashTheme);
        }else{
            MobileAds.initialize(this, initializationStatus ->  {});

           if (getProfileView==2 && getInit==1){
                Intent intent = new Intent(getApplicationContext(), profile_activity.class);
                startActivity(intent);
            }else{

                //setTheme(R.style.AppTheme_SplashTheme);
                onStart();
            }
        }
         /*   FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    mToken = instanceIdResult.getToken();
                    getSelectedLanguage(mToken);
                    DatabaseReference getLanguage = FirebaseDatabase.getInstance().getReference("Users/userLanguage");
                    getLanguage.orderByChild("Users").equalTo(mToken).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String lang = "";
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    lang = ds.child("language").getValue().toString();
                                }
                                userlanguage = lang;
                            } else {
                                userlanguage = Locale.getDefault().getLanguage().toUpperCase();
                            }
                            context = LocaleHelper.setLocale(MainActivity.this, userlanguage);
                            Locale locale = new Locale(userlanguage);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            getSessionActive(mToken, userlanguage);
                            if (!MainActivity.getInstance(getApplication()).isOnline()) {
                                Toast.makeText(getApplicationContext(), resources.getString(R.string.noConexion), Toast.LENGTH_LONG).show();
                                setTheme(R.style.AppTheme_SplashTheme);
                                getInit = 0;
                                getProfileView = 0;
                            } else {
                                if (getInit == 1 && getProfileView == 2) {
                                    Intent intent = new Intent(getApplicationContext(), profile_activity.class);
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            });*/
            super.onResume();
        }


    private void upDateSessionActive(String mToken) {
        DatabaseReference sessionActiveDb = FirebaseDatabase.getInstance().getReference("activeSession/"+mToken);
        sessionActiveDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, Object> updates = null;
                for (DataSnapshot ds:snapshot.getChildren()){
                    updates =  new HashMap<String, Object>();
                    updates.put("activeUser", email_user);
                    updates.put("active", "ON");
                }
                snapshot.getRef().updateChildren(updates);
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