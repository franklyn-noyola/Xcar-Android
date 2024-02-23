package com.epicdeveloper.xcar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.epicdeveloper.xcar.ui.LoginErrorsValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;


public class newuser extends AppCompatActivity {
    EditText name_user, plate_user, email_user, pass_user, confirm_pass;
    TextView linkButton,newUserLabel,textView2;
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
    String userLanguage;

    Resources resources;
    String termsLink;


    private DatabaseReference Users;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newuser);
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
        acceptButton.setEnabled(true);
        email_user.setEnabled(true);
        plate_user.setEnabled(true);
        pass_user.setEnabled(true);
        confirm_pass.setEnabled(true);
        name_user.setEnabled(true);
        userLanguage= Locale.getDefault().getLanguage().toUpperCase();
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
                context = LocaleHelper.setLocale(getApplication(), userLanguage);
                resources = context.getResources();

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
                            if (userLanguage.equals("ES")){
                                Toast.makeText(getApplicationContext(),"El usuario "+plate_user.getText().toString().toUpperCase()+" ha sido registrado, por favor revise su correo electrónico (Bandeja de entrada o SPAM) para activarlo.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("EN")){
                                Toast.makeText(getApplicationContext(),"Account "+plate_user.getText().toString().toUpperCase()+" has been registered, pleasae check your email (Inbox or SPAM) to activate it.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("FR")){
                                Toast.makeText(getApplicationContext(),"L'utilisateur "+ plate_user.getText().toString().toUpperCase() + "a été enregistré, vérifiez votre Email (Boîte de réception ou SPAM) pour l'activer.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("DE")){
                                Toast.makeText(getApplicationContext(),"Der Benutzer "+plate_user.getText().toString().toUpperCase() +" wurde registriert. Überprüfen Sie Ihre E-Mails (Posteingang oder SPAM), um sie zu aktivieren.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("IT")){
                                Toast.makeText(getApplicationContext(),"L'utente "+ plate_user.getText().toString().toUpperCase() +" è stato registrato, controlla la tua Email (Posta in arrivo o SPAM) per attivarlo.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("PT")){
                                Toast.makeText(getApplicationContext(),"O usuário "+ plate_user.getText ().toString().toUpperCase() +" foi registrado, por favor, revisar seu correio eletrônico (Bandeja de entrada o SPAM) para ativar.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("RU")){
                                Toast.makeText(getApplicationContext(),"Пользователь "+ plate_user.getText ().toString ().toUpperCase() +" был зарегистрирован, пожалуйста, проверьте свою электронную почту (Входящие или СПАМ), чтобы активировать его.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("ZH")){
                                Toast.makeText(getApplicationContext(),"用户 " + plate_user.getText().toString().toUpperCase()+" 已注册，请检查您的电子邮件（收件箱或垃圾邮件）以将其激活.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("JA")){
                                Toast.makeText(getApplicationContext(),"ユーザー "+ plate_user.getText().toString().toUpperCase()+" が登録されました。メール（受信トレイまたはスパム）をチェックしてアクティブにしてください。", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("NL")){
                                Toast.makeText(getApplicationContext(),"Gebruiker "+ plate_user.getText ().toString().toUpperCase() +" is geregistreerd, controleer uw e-mail (Inbox of SPAM) om deze te activeren.", Toast.LENGTH_LONG).show();
                            }

                            if (userLanguage.equals("PL")){
                                Toast.makeText(getApplicationContext(),"Użytkownik "+ plate_user.getText().toString().toUpperCase() +" został zarejestrowany, sprawdź swój adres e-mail (skrzynkę odbiorczą lub SPAM), aby go aktywować.", Toast.LENGTH_LONG).show();
                            }

                            if (userLanguage.equals("KO")){
                                Toast.makeText(getApplicationContext(),"사용자 "+ plate_user.getText().toString().toUpperCase() +"가 등록되었습니다. 활성화하려면 이메일 (받은 편지함 또는 스팸)을 확인하십시오.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("SV")){
                                Toast.makeText(getApplicationContext(),"Användaren "+ plate_user.getText().toString().toUpperCase() +" har registrerats, kontrollera din e-post (Inkorgen eller SPAM) för att aktivera den.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("AR")){
                                 Toast.makeText(getApplicationContext(),plate_user.getText().toString().toUpperCase() +" ، يرجى التحقق من بريدك الإلكتروني (صندوق الوارد أو الرسائل الاقتحامية) لتنشيطه.",Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("HI")){
                                Toast.makeText(getApplicationContext(),"उपयोगकर्ता "+ plate_user.getText().toString().toUpperCase() +" पंजीकृत किया गया है, कृपया इसे सक्रिय करने के लिए अपना ईमेल (इनबॉक्स या स्पैम) देखें।", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("UR")){
                                Toast.makeText(getApplicationContext(), plate_user.getText().toString().toUpperCase() +" رجسٹرڈ ہوچکا ہے ، براہ کرم اسے فعال کرنے کے لئے اپنا ای میل (ان باکس یا اسپیم) چیک کریں۔", Toast.LENGTH_LONG).show();
                            }

                            btnRegister.setHint(resources.getString(R.string.backLogin));
                            email_user.setEnabled(false);
                            plate_user.setEnabled(false);
                            pass_user.setEnabled(false);
                            confirm_pass.setEnabled(false);
                            name_user.setEnabled(false);
                            acceptButton.setEnabled(false);
                            if (userLanguage.equals("ES")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),"Bienvenido/a a XCar", sendWelcomeMessageES());
                            }
                            if (userLanguage.equals("EN")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(), resources.getString(R.string.welcomeHomeText), sendWelcomeMessageEN());

                            }
                            if (userLanguage.equals("FR")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageFR());
                            }
                            if (userLanguage.equals("DE")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageDE());
                            }
                            if (userLanguage.equals("IT")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageIT());
                            }
                            if (userLanguage.equals("PT")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessagePT());
                            }
                            if (userLanguage.equals("RU")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageRU());
                            }
                            if (userLanguage.equals("ZH")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageZH());
                            }
                            if (userLanguage.equals("JA")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageJA());
                            }
                            if (userLanguage.equals("NL")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageNL());
                            }

                            if (userLanguage.equals("PL")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessagePL());
                            }
                            if (userLanguage.equals("KO")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageKO());
                            }
                            if (userLanguage.equals("SV")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageSV());
                            }
                            if (userLanguage.equals("AR")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageAR());
                            }
                            if (userLanguage.equals("HI")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageHI());
                            }
                            if (userLanguage.equals("UR")){
                                sendEmail.sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.welcomeHomeText), sendWelcomeMessageUR());
                            }
                            createLanguageUser();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }else{
                            if (userLanguage.equals("ES")) {
                                Toast.makeText(getApplicationContext(), "La mátricula " + plate_user.getText().toString().toUpperCase() + " ya está registrada", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("EN")){
                                Toast.makeText(getApplicationContext(), "Plate number " + plate_user.getText().toString().toUpperCase() + " is already registered", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("FR")){
                                Toast.makeText(getApplicationContext(), "Numéro de plaque " + plate_user.getText().toString().toUpperCase() + " est déjà enregistré", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("DE")){
                                Toast.makeText(getApplicationContext(), "Kennzeichen "+ plate_user.getText ().toString().toUpperCase() + " ist bereits registriert", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("IT")){
                                Toast.makeText(getApplicationContext(), "Numero di targa " + plate_user.getText ().toString ().toUpperCase() + " è già registrato", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("PT")){
                                Toast.makeText(getApplicationContext(), "Número da placa " + plate_user.getText ().toString ().toUpperCase() + " já foi cadastrado", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("RU")){
                                Toast.makeText(getApplicationContext(), "Табличный номер "+ plate_user.getText ().toString ().toUpperCase() +" уже зарегистрирован.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("ZH")){
                                Toast.makeText(getApplicationContext(), "车牌号 " + plate_user.getText().toString().toUpperCase()+" 已经注册", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("JA")){
                                Toast.makeText(getApplicationContext(), "プレート番号 "+ plate_user.getText().toString().toUpperCase()+" はすでに登録されています", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("NL")){
                                Toast.makeText(getApplicationContext(), "Kenteken "+ plate_user.getText ().toString().toUpperCase() +" is al geregistreerd", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("PL")){
                                Toast.makeText(getApplicationContext(), "Tabliczka "+ plate_user.getText().toString().toUpperCase() +" jest już zarejestrowana", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("KO")){
                                Toast.makeText(getApplicationContext(), "플레이트 "+ plate_user.getText().toString().toUpperCase() +"가 이미 등록되었습니다.", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("SV")){
                                Toast.makeText(getApplicationContext(), "Plattan "+ plate_user.getText().toString().toUpperCase() +" är redan registrerad", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("AR")){
                                Toast.makeText(getApplicationContext()," اللوحة "+ plate_user.getText().toString().toUpperCase() +"\" مسجلة بالفعل,," ,  Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("HI")){
                                Toast.makeText(getApplicationContext(), "प्लेट "+ plate_user.getText().toString().toUpperCase() +" पंजीकृत नहीं है", Toast.LENGTH_LONG).show();
                            }
                            if (userLanguage.equals("UR")){
                                Toast.makeText(getApplicationContext(), "پلیٹ "+ plate_user.getText ().toString ().toUpperCase() +" پہلے ہی رجسٹرڈ ہے", Toast.LENGTH_LONG).show();
                            }

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
                    final languageUserData createUserLanguage = new languageUserData(mToken,userLanguage);
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
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Bienvenido/a a XCar!!!</font></b><br><br>";
        headerMessage="Estimado Sr./Sra "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Gracias por registrarse en el mundo de XCar. Para activar el usuario, haga click en este link: <a href=epicdevelopers.app?ES"+plate_user.getText().toString().toUpperCase()+">Activar usuario</a> <br><br>";
        farewellMessage="Un saludo cordial,<br>El equipo de XCar.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageEN(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Welcome to XCar!!!</font></b><br><br>";
        headerMessage="Dear Mr/Mrs. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Thanks for regisgtering to XCar world. To activate your account, click on this link: <a href=epicdevelopers.app?EN"+plate_user.getText().toString().toUpperCase()+">Activate account</a> <br><br>";
        farewellMessage="Truly yours,<br>XCar Team.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageFR(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Bienvenue à XCar!!!</font></b><br><br>";
        headerMessage="Cher Monsieur / Madame. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Merci de vous enregistrer dans le monde XCar. Pour activer votre utilisateur, cliquez sur ce lien: <a href=epicdevelopers.app?FR"+plate_user.getText().toString().toUpperCase()+">Activer l'utilisateur</a> <br><br>";
        farewellMessage="Cordialement,<br>L'équipe XCar.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageIT(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Benvenuto in XCar!!!</font></b><br><br>";
        headerMessage="Caro signor/signora "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Grazie per esserti registrato a XCar world. Per attivare il tuo utente, fare clic su questo collegamento: <a href=epicdevelopers.app?IT"+plate_user.getText().toString().toUpperCase()+">Attiva utente</a> <br><br>";
        farewellMessage="Cordiali saluti,<br>Il team XCar.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageDE(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Willkommen bei XCar!!!</font></b><br><br>";
        headerMessage="Sehr geehrter Herr/Frau "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Vielen Dank, dass Sie sich bei XCar world registriert haben. Kopieren Sie diesen Aktivierungscode, klicken Sie auf diesen Link: <a href=epicdevelopers.app?DE"+plate_user.getText().toString().toUpperCase()+">Benutzer aktivieren</a> <br><br>";
        farewellMessage="Mit freundlichen Grüßen,<br>Das XCar-Team.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessagePT(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Bem-vindo a XCar!!!</font></b><br><br>";
        headerMessage="Prezado Sr./Sra. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Obrigado por se registrar no mundo de XCar. Para ativar o usuário, clique neste link: <a href=epicdevelopers.app?PT"+plate_user.getText().toString().toUpperCase()+">Ativar usuário</a> <br><br>";
        farewellMessage="Sinceramente,<br>A equipe XCar.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageRU(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Добро пожаловать в XCar!!!</font></b><br><br>";
        headerMessage="Уважаемый господин/госпожа "+ name_user.getText (). toString ()+"<br><br>";
        bodyMessage="Спасибо за регистрацию в мире XCar. Чтобы активировать свою учетную запись, нажмите на эту ссылку: <a href=epicdevelopers.app?RU"+plate_user.getText().toString().toUpperCase()+">Активировать пользователя</a> <br><br>";
        farewellMessage="Искренне,<br>Команда XCar.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageZH(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">欢迎来到XCar!!!</font></b><br><br>";
        headerMessage="亲爱的先生/夫人： " + name_user.getText().toString()+"<br><br>";
        bodyMessage="感谢您注册到所有连接的世界。要激活您的帐户，点击此链接:<a href=epicdevelopers.app?ZH"+plate_user.getText().toString().toUpperCase()+">激活帐户</a> <br><br>";
        farewellMessage="亲切的问候,<br>该团队的 XCar。";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageJA(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">XCarへようこそ!!!</font></b><br><br>";
        headerMessage="親愛なるミスター/ミセス"+ name_user.getText().toString()+"<br><br>";
        bodyMessage="XCarworldに登録していただきありがとうございます。アカウントをアクティブ化するには、このリンクをクリックしてください：<a href=epicdevelopers.app/?JA"+plate_user.getText().toString().toUpperCase()+">ユーザーのアクティブ化</a> <br><br>";
        farewellMessage="心から,<br>XCarチーム。";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageNL(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Welkom bij XCar!!!</font></b><br><br>";
        headerMessage="Beste meneer/mevrouw. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Bedankt voor uw aanmelding bij XCar world. Om uw account te activeren, klikt u op deze link: <a href=epicdevelopers.app?NL"+plate_user.getText().toString().toUpperCase()+"> Activeer gebruiker</a> <br><br>";
        farewellMessage="Oprecht,<br>Het XCar -team.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }
    public String sendWelcomeMessagePL(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Witamy w XCar!!!</font></b><br><br>";
        headerMessage="Szanowny Panie/Pani.. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Bedankt voor uw aanmelding bij XCar world. Om uw account te activren, kliknij ten link: <a href=epicdevelopers.app?PL"+plate_user.getText().toString().toUpperCase()+">Activeer gebruiker</a> <br><br>";
        farewellMessage="Z poważaniem,<br>Zespół XCar.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageKO(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">XCar에 오신 것을 환영합니다!!!</font></b><br><br>";
        headerMessage="친애하는 Mr./Ms. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="XCar의 세계에 등록 해 주셔서 감사합니다. 계정을 활성화하려면이 링크를 클릭하십시오.: <a href=epicdevelopers.app?KO"+plate_user.getText().toString().toUpperCase()+"> 사용자 활성화</a> <br><br>";
        farewellMessage="진정으로,<br>XCar 팀.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageSV(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">Välkommen till XCar!!!</font></b><br><br>";
        headerMessage="Kära herr/fru. "+name_user.getText().toString()+"<br><br>";
        bodyMessage="Tack för att du registrerade dig i XCar-världen. För att aktivera användaren, klicka på den här länken: <a href=epicdevelopers.app?SV"+plate_user.getText().toString().toUpperCase()+"> Aktivera användare</a> <br><br>";
        farewellMessage="vänliga hälsningar,<br>XCar-teamet.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }

    public String sendWelcomeMessageHI(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">XCar में आपका स्वागत है!!!</font></b><br><br>";
        headerMessage="प्रिय श्री / एम। एस। "+name_user.getText().toString()+"<br><br>";
        bodyMessage="XCar दुनिया के लिए regisgtering के लिए धन्यवाद। अपने खाते को सक्रिय करने के लिए, इस लिंक पर क्लिक करें: <a href=epicdevelopers.app?HI"+plate_user.getText().toString().toUpperCase()+"> उपयोगकर्ता को सक्रिय करें</a> <br><br>";
        farewellMessage="ईमानदारी से,<br>XCar टीम.";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }
    public String sendWelcomeMessageAR(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">" +
                "مرحبًا بك في XCar"+"!!!</font></b><br><br>";
        headerMessage="" +
                "عزيزي السيد / السيدة."
                +name_user.getText().toString()+"<br><br>";
        bodyMessage= "نشكرك على التسجيل في XCar world. لتنشيط حسابك ، انقر فوق هذا الارتباط"+
                "<a href=epicdevelopers.app?AR"+plate_user.toString().toUpperCase()+">"+  "تنشيط المستخدم" +"</a> <br><br>" ;
        farewellMessage="بإخلاص"+"<br"+"فريق XCar";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }
    public String sendWelcomeMessageUR(){
        welcomeMessage="<b><font size=\"20\" color=\"blue\">"+"allCnnected میں خوش آمدید"+"</font></b><br><br>";
        headerMessage=" محترم جناب / محترمہ "+name_user.getText().toString()+"<br><br>";
        bodyMessage = "XCar دنیا میں اندراج کرنے کا شکریہ۔ اپنے اکاؤنٹ کو چالو کرنے کے لئے ، اس لنک پر کلک کریں"+"<a href=epicdevelopers.app?UR"+plate_user.toString().toUpperCase()+">"+"صارف کو چالو کریں"+"</a> <br><br>";
        farewellMessage="مخلص,"+"<br>"+"آل سے منسلک ٹیم۔";
        return allMessage=welcomeMessage+headerMessage+bodyMessage+farewellMessage;
    }


    public void translanguage(){
        context = LocaleHelper.setLocale(newuser.this, userLanguage);
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

        if (userLanguage.equals("ES")){
            acceptButton.setTextSize(9);
            linkButton.setTextSize(10);
        //    linkButton.setY(1670);
         //   linkButton.setX(450);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/spanish";
        }
        if (userLanguage.equals("EN")){
            acceptButton.setTextSize(9);
            linkButton.setTextSize(12);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/english";
        }
        if (userLanguage.equals("FR")){
            acceptButton.setTextSize(9);
            linkButton.setTextSize(9);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/frances";
        }
        if (userLanguage.equals("DE")){
            acceptButton.setTextSize(7);
            linkButton.setTextSize(7);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/aleman";
        }
        if (userLanguage.equals("IT")){
            acceptButton.setTextSize(12);
            linkButton.setTextSize(10);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/italiano";
        }
        if (userLanguage.equals("PT")){
            acceptButton.setTextSize(12);
            linkButton.setTextSize(9);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/portugues";
        }
        if (userLanguage.equals("RU")){
            acceptButton.setTextSize(13);
            linkButton.setTextSize(9);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/ruso";
        }
        if (userLanguage.equals("ZH")){
            acceptButton.setTextSize(13);
            linkButton.setTextSize(14);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/chino";
        }
        if (userLanguage.equals("JA")){
            acceptButton.setTextSize(14);
            linkButton.setTextSize(12);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/japones";
        }
        if (userLanguage.equals("NL")){
            acceptButton.setTextSize(19);
            linkButton.setTextSize(11);
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/holandes";
        }

        if (userLanguage.equals("KO")) {
            termsLink= "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/coreano";
        }
        if (userLanguage.equals("PL")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/polaco";
        }
        if (userLanguage.equals("SV")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/sueco";
        }
        if (userLanguage.equals("AR")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/arabe";
        }
        if (userLanguage.equals("UR")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/urdu";
        }
        if (userLanguage.equals("HI")) {
            termsLink = "https://sites.google.com/view/epicdevelopersapp/allconneted/terminos-y-condicones/hindi";
        }
    }


}
