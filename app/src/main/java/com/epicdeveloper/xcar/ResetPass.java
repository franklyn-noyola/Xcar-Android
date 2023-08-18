package com.epicdeveloper.xcar;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.epicdeveloper.xcar.sendEmail.*;
import static java.security.SecureRandom.getInstance;

public class ResetPass extends AppCompatActivity  {

    String[] alphalow = {"a","b","c","d","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x",
        "y","z","0","1", "2", "3", "4", "5", "6", "7", "8", "9","_","!",".","?","-", "A","B","C","D","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X",
            "Y","Z"};
    private static String passwordGenerated;
    EditText plate_user;
    TextView resetLabel;
    public static String selectedLanguage;
    Context context;
    Resources resources;
    EditText email_user;
    Button btnReset;
    public Object emailFound;
    public static Object userPlate;
    static String passwordGen;
    static String messageHeader;
    static String messabeBody1;
    static String messageFarawell;
    static String allMessages;

    DatabaseReference resetPass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        selectedLanguage = MainActivity.userlanguage;
        plate_user = (EditText) findViewById(R.id.plate_reset_user);
        resetLabel = (TextView) findViewById(R.id.resetLabel);
        email_user = (EditText) findViewById(R.id.email_reset_user);
        btnReset = (Button) findViewById(R.id.resetButton);
        translateField(selectedLanguage);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserData();
            }
        });

    }

    private void translateField(String selectedLanguage) {
        context = LocaleHelper.setLocale(ResetPass.this, selectedLanguage);
        resources = context.getResources();
        resetLabel.setText(resources.getString(R.string.passResetLbl));
        plate_user.setHint(resources.getString(R.string.plate_name));
        email_user.setHint(resources.getString(R.string.email_name));
        btnReset.setText(resources.getString(R.string.reset));

    }

    private void getUserData() {
        context = LocaleHelper.setLocale(ResetPass.this, selectedLanguage);
        resources = context.getResources();
        if (btnReset.getText().equals(resources.getString(R.string.backLogin))){
            finish();
            return;
        }
        if (TextUtils.isEmpty(plate_user.getText().toString()) || TextUtils.isEmpty(email_user.getText().toString()) ){
            Toast.makeText(this,R.string.noEmptyFields, Toast.LENGTH_SHORT).show();
            return;
        }

        final String emailUser = email_user.getText().toString();
        resetPass = FirebaseDatabase.getInstance().getReference("Users");
        resetPass.orderByChild("plate_user").equalTo(plate_user.getText().toString().toUpperCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        emailFound = snapshot.child("user_email").getValue();
                        userPlate = snapshot.child("user_name").getValue();
                    }

                if (emailFound.toString().equals(email_user.getText().toString().toLowerCase())) {
                    try {
                        Toast.makeText(getApplicationContext(),resources.getString(R.string.passResetMsg), Toast.LENGTH_SHORT).show();
                        btnReset.setText(resources.getString(R.string.backLogin));
                        changePass(getPasswordGenerated());
                        email_user.setEnabled(false);
                        plate_user.setEnabled(false);
                        if (selectedLanguage.equals("ES")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentES());
                        }
                        if (selectedLanguage.equals("EN")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentEN());
                        }
                        if (selectedLanguage.equals("FR")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentFR());
                        }
                        if (selectedLanguage.equals("DE")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentDE());
                        }
                        if (selectedLanguage.equals("IT")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentIT());
                        }
                        if (selectedLanguage.equals("PT")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentPT());
                        }
                        if (selectedLanguage.equals("RU")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentRU());
                        }
                        if (selectedLanguage.equals("ZH")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentZH());
                        }
                        if (selectedLanguage.equals("JA")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentJA());
                        }
                        if (selectedLanguage.equals("NL")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentNL());
                        }
                        if (selectedLanguage.equals("PL")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentPL());
                        }
                        if (selectedLanguage.equals("KO")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentKO());
                        }
                        if (selectedLanguage.equals("SV")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentSV());
                        }
                        if (selectedLanguage.equals("AR")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentAR());
                        }
                        if (selectedLanguage.equals("HI")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentHI());
                        }
                        if (selectedLanguage.equals("UR")){
                            sendEmailMessage(email_user.getText().toString(),resources.getString(R.string.generatedPass), messagetoBeSentUR());
                        }


                    } catch (NoSuchAlgorithmException e) {//| AddressException e) {
                        e.printStackTrace();
                    }


                } else {
                    if (selectedLanguage.equals("ES")){
                        Toast.makeText(getApplicationContext(),"El email " + email_user.getText().toString() + " no corresponde con el usuario " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("EN")){
                        Toast.makeText(getApplicationContext(),"Email " + email_user.getText().toString() + " does not belong to Account " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("FR")){
                        Toast.makeText(getApplicationContext(),"Email " + email_user.getText().toString() + " n'appartient pas à l'utilisateur " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("DE")){
                    Toast.makeText(getApplicationContext(),"Email " + email_user.getText().toString() + " gehört nicht dem Benutzer " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("IT")){
                            Toast.makeText(getApplicationContext(),"Email " + email_user.getText().toString() + " non appartiene all'utente " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("PT")){
                        Toast.makeText(getApplicationContext(),"O email" + email_user.getText().toString() + "não corresponde ao usuário" + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("RU")){
                        Toast.makeText(getApplicationContext(),"Электронная почта "+ email_user.getText ().toString() +" не соответствует пользователю "+ plate_user.getText ().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("ZH")){
                        Toast.makeText(getApplicationContext(),"电子邮件 " + email_user.getText().toString()+" 与用户" + plate_user.getText().toString().toUpperCase()+" 不对应", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("JA")){
                        Toast.makeText(getApplicationContext(),"email "+ email_user.getText().toString()+" はユーザーに対応していません "+ plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("NL")){
                        Toast.makeText(getApplicationContext(),"De e-mail" + email_user.getText ().toString() + "komt niet overeen met de gebruiker" + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("PL")){
                        Toast.makeText(getApplicationContext(),"Adres e-mail "+ email_user.getText().toString() +" nie odpowiada użytkownikowi " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("KO")){
                        Toast.makeText(getApplicationContext(),"이메일 "+ email_user.getText().toString()+"이 사용자와 일치하지 않습니다. " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("SV")){
                        Toast.makeText(getApplicationContext(),"E-postmeddelandet" + email_user.getText().toString () + "motsvarar inte användaren " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("AR")){
                        Toast.makeText(getApplicationContext(),"البريد الإلكتروني" + email_user.getText().toString () + "موتسفارار انفاندارين " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("HI")){
                        Toast.makeText(getApplicationContext(),"ईमेल "+ email_user.getText().toString() +" उपयोगकर्ता के अनुरूप नहीं है " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("UR")){
                        Toast.makeText(getApplicationContext(),"ای میل " + email_user.getText().toString() + " صارف سے مطابقت نہیں رکھتا ہے " + plate_user.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    }


                    return;
                }
            }else{
                    if (selectedLanguage.equals("ES")){
                        Toast.makeText(getApplicationContext(),"El usuario " + plate_user.getText().toString().toUpperCase() + " no existe o no ha sido registrado.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("EN")){
                        Toast.makeText(getApplicationContext(),"Account " + plate_user.getText().toString().toUpperCase() + " does not exist or hasn't registered yet.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("FR")){
                        Toast.makeText(getApplicationContext(),"Utilisateur " + plate_user.getText().toString().toUpperCase() + " n'existe pas ou ne s'est pas encore inscrit.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("DE")){
                        Toast.makeText(getApplicationContext(),"Der Nutzer " + plate_user.getText().toString().toUpperCase() + " existiert nicht oder hat sich noch nicht registriert.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("IT")){
                        Toast.makeText(getApplicationContext(),"L'utente " + plate_user.getText().toString().toUpperCase() + " non esiste o non si è ancora registrato.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("PT")){
                        Toast.makeText(getApplicationContext(),"O usuário "+ plate_user.getText ().toString().toUpperCase() +" não existe ou não foi registrado.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("RU")){
                        Toast.makeText(getApplicationContext(),"Пользователь "+ plate_user.getText().toString().toUpperCase() +" не существует или не был зарегистрирован.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("ZH")){
                        Toast.makeText(getApplicationContext(),"用户 " + plate_user.getText().toString().toUpperCase()+"不存在或尚未注册。", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("JA")){
                        Toast.makeText(getApplicationContext(),"ユーザー " + plate_user.getText().toString().toUpperCase()+" が存在しないか、登録されていません。", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("NL")){
                        Toast.makeText(getApplicationContext(),"De gebruiker "+ plate_user.getText ().toString().toUpperCase () +" bestaat niet of is niet geregistreerd.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("PL")){
                        Toast.makeText(getApplicationContext(),"Użytkownik "+ plate_user.getText ().toString().toUpperCase() +" nie istnieje lub nie został zarejestrowany.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("KO")){
                        Toast.makeText(getApplicationContext(),"사용자 "+ plate_user.getText().toString().toUpperCase() +" 가 존재하지 않거나 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("SV")){
                        Toast.makeText(getApplicationContext(),"Användaren "+ plate_user.getText ().toString().toUpperCase() +" finns inte eller har inte registrerats.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("HI")) {
                        Toast.makeText(getApplicationContext(), "उपयोगकर्ता "+ plate_user.getText().toString().toUpperCase() +" मौजूद नहीं है या पंजीकृत नहीं किया गया है।", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("AR")){
                        Toast.makeText(getApplicationContext(),"المستخدم " + plate_user.getText().toString().toUpperCase() + " غير موجود أو لم يتم تسجيله.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("UR")) {
                        Toast.makeText(getApplicationContext(), "صارف "+ plate_user.getText().toString().toUpperCase() +" موجود نہیں ہے یا رجسٹرڈ نہیں کیا گیا ہے۔", Toast.LENGTH_SHORT).show();
                    }


                        return;
            }
        }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    private String getPasswordGenerated() throws NoSuchAlgorithmException {

        int length = (int) Math.floor(Math.random()*9+7);
        Random random = SecureRandom.getInstance("SHA1PRNG");
        StringBuilder sb = new StringBuilder(length);
        boolean loop = true;
        while (loop){
            int index = random.nextInt(alphalow.length);
            sb.append(alphalow[index]);
            if (sb.length()>7 && sb.length()<16){
                loop=false;
            }
        }
        passwordGen=sb.toString();
        return passwordGenerated = sb.toString();

    }

    private void changePass(final String passchange){
        resetPass = FirebaseDatabase.getInstance().getReference("Users");
        Query getData = resetPass.orderByChild("plate_user").equalTo(plate_user.getText().toString().toUpperCase());
        ValueEventListener valueEventListener = (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Map<String, Object> updates = new HashMap<String, Object>();
                    updates.put("user_password", passchange);
                    updates.put("resetPass","1");
                    ds.getRef().updateChildren(updates);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getData.addListenerForSingleValueEvent(valueEventListener);
    }

    public static String  messagetoBeSentES(){
        messageHeader = "Estimado Sr./Sra. "+userPlate.toString()+"<br><br>";
        messabeBody1="Su contraseña <strong>"+passwordGen+"</strong> ha sido generada automáticamente. Ir a la app XCar y una vez en el inicio introducir la matrícula y la contraseña generada, " +
                "y hacer clic al botón LOGIN. Luego aparecerá una pantalla para que introduzca su nueva contraseña, ya que la contraseña generada es temporal.<br> Una vez en la pantalla de cambio de contraseña, " +
                "cambie a una contraseña que elijas y una vez cambiada vaya al inicio de la aplicación y loguese con su nueva contraseña.<br><br> Para cualquier duda o consulta, no dude en contactarnos vía soporte<br><br>";
        messageFarawell="Un saludo cordial,<br>El equipo de XCar.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentEN(){
        messageHeader = "Dear Mr./Mrs. "+userPlate.toString()+"<br><br>";
        messabeBody1="Your password <strong>"+passwordGen+"</strong> has been generated automatically. Please, go to XCar app and type the plate number (your account) and the generated password, " +
                "and click on LOGIN button. Afterwards, another screen will be displayed so you can enter your new password, this is why the generated password is for temporal use.<br> Once, in the Change Password screen, " +
                "change your password for another password you select and once it has been changed you can enter in XCar app.<br><br> For any doubt or comment, don't hesitate to contact with Support Team.<br><br>";
        messageFarawell="Truly yours,<br>XCar Team.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentFR(){
        messageHeader = "Cher Monsieur/Madame "+userPlate.toString()+"<br><br>";
        messabeBody1="Votre mot de passe <strong>"+passwordGen+"</strong> a été généré automatiquement. S'il vous plaît, allez sur l'application XCar et tapez le numéro de plaque (votre compte) et le mot de passe généré " +
                "et cliquez sur le bouton LOGIN. Ensuite, un autre écran s'affichera pour que vous puissiez saisir votre nouveau mot de passe, c'est pourquoi le mot de passe généré est à usage temporaire.<br> Une fois, dans l'écran Modifier le mot de passe, " +
                "changez votre mot de passe pour un autre mot de passe que vous sélectionnez et une fois qu'il a été changé, vous pouvez entrer dans l'application XCar.<br><br> Pour tout doute ou commentaire, n'hésitez pas à contacter l'équipe de support.<br><br>";

        messageFarawell="Cordialement,<br>L'équipe XCar.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentDE(){
        messageHeader = "Sehr geehrter Herr/Frau "+userPlate.toString()+"<br><br>";
        messabeBody1="Ihr Passwort <strong>"+passwordGen+"</strong> wurde automatisch generiert. Bitte gehen Sie zur XCar App und geben Sie die Kennzeichen (Ihr Konto) und das generierte Passwort ein " +
                "und klicken Sie auf die Schaltfläche LOGIN. Anschließend wird ein weiterer Bildschirm angezeigt, in dem Sie Ihr neues Passwort eingeben können. Aus diesem Grund ist das generierte Passwort für die vorübergehende Verwendung vorgesehen.<br> Einmal im Bildschirm Passwort ändern, " +
                "Ändern Sie Ihr Passwort für ein anderes Passwort, das Sie auswählen. Sobald es geändert wurde, können Sie es in die XCar-App eingeben.<br><br> Bei Zweifeln oder Kommentaren wenden Sie sich bitte an das Support-Team.<br><br>";
        messageFarawell="Mit freundlichen Grüßen,<br>Das XCar-Team.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentIT(){
        messageHeader = "Caro signor/signora "+userPlate.toString()+"<br><br>";
        messabeBody1="La tua password <strong>"+passwordGen+"</strong> è stato generato automaticamente. Per favore, vai all'app XCar e digita il numero di targa (il tuo account) e la password generata, " +
                "e fare clic sul pulsante LOGIN. Successivamente, verrà visualizzata un'altra schermata in cui puoi inserire la tua nuova password, questo è il motivo per cui la password generata è per uso temporaneo.<br> Una volta, nella schermata Cambia password, " +
                "cambia la tua password per un'altra password che hai selezionato e una volta che è stata cambiata puoi entrare nell'app XCar.<br><br> Per qualsiasi dubbio o commento, non esitare a contattare il team di supporto.<br><br>";
        messageFarawell="Cordiali saluti,<br>Il team XCar.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }
    public static String  messagetoBeSentPT(){
        messageHeader = "Prezado Sr./Sra. "+userPlate.toString()+"<br><br>";
        messabeBody1="Sua senha <strong>"+passwordGen+"</strong> foi gerado automaticamente. Vá para o aplicativo XCar e, uma vez no início, insira a placa do carro e a senha gerada, " +
                "e clique no botão LOGIN. Em seguida, aparecerá uma tela para você inserir sua nova senha, pois a senha gerada é temporária. <br> Uma vez na tela de alteração de senha, " +
                "mude para uma senha de sua escolha e uma vez alterada vá para o início do aplicativo e faça o login com sua nova senha. <br> <br> Em caso de dúvidas ou consultas, não hesite em nos contatar através do suporte.<br><br>";
        messageFarawell="Atenciosamente, <br> Equipe XCar.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentNL(){
        messageHeader = "Beste meneer/mevrouw "+userPlate.toString()+"<br><br>";
        messabeBody1="Uw wachtwoord <strong>"+passwordGen+"</strong> is automatisch gegenereerd. Ga alsjeblieft naar de app XCar en typ het kenteken (je account) en het gegenereerde wachtwoord, " +
                "en klik op LOGIN. Daarna wordt een ander scherm weergegeven zodat u uw nieuwe wachtwoord kunt invoeren, daarom is het gegenereerde wachtwoord voor tijdelijk gebruik. <br> Eenmaal, in het scherm Wachtwoord wijzigen, " +
                "verander uw wachtwoord voor een ander wachtwoord dat u selecteert en zodra het is gewijzigd, kunt u dit invoeren in de XCar-app. <br> <br> Aarzel niet om contact op te nemen met het ondersteuningsteam bij twijfel of opmerkingen.<br><br>";
        messageFarawell="oprecht,<br>Het XCar-team.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentRU(){
        messageHeader = "Уважаемый господин /госпожа. "+userPlate.toString()+"<br><br>";
        messabeBody1="Ваш пароль <strong> "+passwordGen+" </strong> был создан автоматически. Пожалуйста, зайдите в приложение XCar и введите номер телефона (ваш аккаунт) и сгенерированный пароль., " +
                "и нажмите кнопку ВХОД. После этого появится другой экран, на котором вы сможете ввести свой новый пароль, поэтому сгенерированный пароль предназначен для временного использования. <br> Один раз на экране «Изменить пароль», " +
                "измените свой пароль на другой выбранный вами пароль, и как только он будет изменен, вы сможете ввести его в приложении XCar. <br> <br> Если у вас возникнут сомнения или комментарии, не стесняйтесь обращаться в службу поддержки.<br><br>";
        messageFarawell="Искренне,<br>Команда XCar.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentZH(){
        messageHeader = "尊敬的先生/夫人： "+userPlate.toString()+"<br><br>";
        messabeBody1="您的密码<strong>" +passwordGen+" </strong>已自动生成。请转到XCar应用并输入车牌号（您的帐户）和生成的密码, " +
                "然后单击“登录”按钮。之后，将显示另一个屏幕，以便您输入新密码，这就是为什么生成的密码仅供临时使用的原因。一次，在“更改密码”屏幕中。, " +
                "将您的密码更改为您选择的另一个密码，更改后即可输入XCar应用程序。<br> <br>如有任何疑问或意见，请随时与支持团队联系。.<br><br>";
        messageFarawell="Truly yours,<br>XCar Team.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentJA(){
        messageHeader = "親愛なるサー/マダム "+userPlate.toString()+"<br><br>";
        messabeBody1="パスワード<strong>" +passwordGen+ "</strong>は自動的に生成されました。 XCarアプリに移動し、プレート番号（アカウント）と生成されたパスワードを入力してください, " +
                "ログインボタンをクリックします。その後、別の画面が表示され、新しいパスワードを入力できます。これが、生成されたパスワードが一時的に使用される理由です。一度、パスワードの変更画面で, " +
                "選択した別のパスワードにパスワードを変更すると、XCarアプリに入力できます。<br> <br>疑問やコメントがある場合は、遠慮なくサポートチームに連絡してください。.<br><br>";
        messageFarawell="心から,<br>XCarチーム。";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentPL(){
        messageHeader = "Szanowny Panie /Pani. "+userPlate.toString()+"<br><br>";
        messabeBody1="Twoje hasło <strong> "+ passwordGen +" </strong> zostało wygenerowane automatycznie. Przejdź do aplikacji XCar i od razu na początku wprowadź numer rejestracyjny oraz wygenerowane hasło, " +
                "i kliknij przycisk LOGIN. Następnie pojawi się ekran, na którym należy wprowadzić nowe hasło, ponieważ wygenerowane hasło jest tymczasowe.<br> Na ekranie zmiany hasła " +
                "zmień na wybrane przez siebie hasło, a po zmianie przejdź na początek aplikacji i zaloguj się przy użyciu nowego hasła. <br> <br> W przypadku jakichkolwiek pytań lub pytań prosimy o kontakt poprzez support<br><br>";
        messageFarawell="Z poważaniem,<br>Zespół XCar.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentKO(){
        messageHeader = "친애하는 Mr./Mrs. "+userPlate.toString()+"<br><br>";
        messabeBody1="비밀번호 <strong> "+ passwordGen +"</strong>이 자동으로 생성되었습니다. XCar 앱으로 이동하여 처음에 번호판과 생성 된 비밀번호를 입력합니다 " +
                "로그인 버튼을 클릭합니다. 생성 된 비밀번호는 임시이므로 새 비밀번호를 입력하는 화면이 나타납니다. <br> 비밀번호 변경 화면에서 " +
                "원하는 암호로 변경하고 변경 한 후에는 응용 프로그램 시작으로 이동하여 새 암호로 로그인하십시오. <br> <br> 질문이나 질문이 있으시면 언제든지 지원을 통해 저희에게 연락하십시오.<br><br>";
        messageFarawell="진정으로<br>XCar 팀";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentSV(){
        messageHeader = "Kära herr/fru "+userPlate.toString()+"<br><br>";
        messabeBody1="Ditt lösenord <strong> "+ passwordGen +" </strong> har genererats automatiskt. Gå till XCar-appen och ange registreringsskylten och det genererade lösenordet en gång i början, " +
                "och klicka på LOGGA IN-knappen. Då visas en skärm där du kan ange ditt nya lösenord, eftersom det genererade lösenordet är tillfälligt. <br> En gång på skärmen för lösenordsbyte, " +
                "byt till ett lösenord som du väljer och när det har ändrats går du till applikationens start och loggar in med ditt nya lösenord. <br> <br> För frågor eller frågor, tveka inte att kontakta oss via support.<br><br>";
        messageFarawell="Vänliga hälsningar,<br>XCar-teamet.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentHI(){
        messageHeader = "प्रिय श्री/श्रीमती। "+userPlate.toString()+"<br><br>";
        messabeBody1="आपका पासवर्ड <strong> "+ passwordGen +" </strong> अपने आप उत्पन्न हो गया है। XCar ऐप पर जाएं और शुरुआत में एक बार लाइसेंस प्लेट और जनरेट किए गए पासवर्ड को दर्ज करें " +
                "और LOGIN बटन पर क्लिक करें। तब आपके लिए अपना नया पासवर्ड दर्ज करने के लिए एक स्क्रीन दिखाई देगी, क्योंकि उत्पन्न पासवर्ड अस्थायी है। पासवर्ड परिवर्तन स्क्रीन में एक बार " +
                "अपनी पसंद के पासवर्ड को बदलने और एक बार बदले जाने के बाद एप्लिकेशन की शुरुआत में जाएं और अपने नए पासवर्ड के साथ लॉग इन करें। <br> <br> किसी भी प्रश्न या प्रश्न के लिए, समर्थन के माध्यम से हमसे संपर्क करने में संकोच न करें।<br><br>";
        messageFarawell="ईमानदारी से<br>XCar टीम";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentAR(){
        messageHeader = "عزيزي السيد / السيدة. "+userPlate.toString()+"<br><br>";
        messabeBody1="كلمة السر خاصتك "+"<strong>"+passwordGen+"</strong>"+" تم إنشاؤه تلقائيًا. انتقل إلى تطبيق XCar ومرة \u200B\u200Bواحدة في البداية أدخل لوحة الترخيص وكلمة المرور التي تم إنشاؤها " +
                "وانقر على زر تسجيل الدخول. ثم ستظهر لك شاشة لإدخال كلمة المرور الجديدة ، لأن كلمة المرور التي تم إنشاؤها مؤقتة. "+"<br>"+" بمجرد ظهور شاشة تغيير كلمة المرور ، " +
                "قم بالتغيير إلى كلمة مرور من اختيارك وبمجرد تغييرها ، انتقل إلى بدء التطبيق وقم بتسجيل الدخول باستخدام كلمة المرور الجديدة."+"<br><br>"+"لأية أسئلة أو استفسارات ، لا تتردد في الاتصال بنا عبر الدعم<br><br>";
        messageFarawell="بإخلاص"+"<br>"+"فريق XCar";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }

    public static String  messagetoBeSentUR(){
        messageHeader = "محترم جناب / محترمہ "+userPlate.toString()+"<br><br>";
        messabeBody1="آپ کا پاس ورڈ "+"<strong>"+passwordGen+"</strong>"+" یہ خود بخود تیار ہوچکا ہے۔ آل سے منسلک ایپ پر جائیں اور شروع میں ایک بار لائسنس پلیٹ اور تیار کردہ پاس ورڈ درج کریں ، " +
                "اور لاگ ان بٹن پر کلک کریں۔ تب آپ کے پاس اپنا نیا پاس ورڈ داخل کرنے کے لئے ایک اسکرین ظاہر ہوگی ، کیوں کہ پیدا کردہ پاس ورڈ عارضی ہے۔"+"<br>"+" ایک بار پاس ورڈ کی تبدیلی کی سکرین پر ، " +
                "اپنی پسند کے پاس ورڈ میں تبدیلی کریں اور ایک بار تبدیل ہونے کے بعد درخواست شروع کریں اور اپنے نئے پاس ورڈ کے ساتھ لاگ ان کریں۔"+"<br><br>"+"کسی بھی سوال یا سوالات کے ل support ، مدد کے ذریعہ ہم سے رابطہ کرنے میں سنکوچ نہ کریں<br><br>";
        messageFarawell="مخلص"+"<br>"+"XCar ٹیم.";

        return allMessages = messageHeader+messabeBody1+messageFarawell;
    }



}