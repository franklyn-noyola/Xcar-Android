package com.epicdeveloper.xcar.ui.sendNotifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.epicdeveloper.xcar.LocaleHelper;
import com.epicdeveloper.xcar.MainActivity;
import com.epicdeveloper.xcar.R;
import com.epicdeveloper.xcar.ui.receivedNotifications.receivedNotifications;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class sendNotification extends AppCompatActivity {
    EditText targetUser;
    Spinner selectSubject;
    EditText message;
    String subjectSel;
    TextView imageSel, sendButton, selectedSubject;
    public static String imageUriName;
    public static String id2;
    public static String userTarget;
    public static String userToSend;
    public static Uri imageUri;
    public static String imageName;
    public static String  messageSend;
    String selectedLanguage;
    Context context;
    Resources resources;
    DatabaseReference userValidation;
    DatabaseReference sendMessageUser;
    DatabaseReference receiveMessageUser;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sendnotifications_fragment);
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getApplication(), selectedLanguage);
        resources = context.getResources();
        sendButton = (TextView) findViewById(R.id.sendButton);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
        getSupportActionBar().setTitle(resources.getString(R.string.sendNotifications));
        imageName="";
        imageSel = (TextView) findViewById(R.id.imageSelected);
        targetUser = findViewById(R.id.targetUser);
        selectedSubject = findViewById(R.id.selectedSubject);
        selectSubject = findViewById(R.id.subject);
        message = findViewById(R.id.notificationMessage);
        imageUriName = null;
        imageSel.setText(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (MainActivity.chatScreen == 1) {
            selectedSubject.setVisibility(View.INVISIBLE);
            selectSubject.setVisibility(View.VISIBLE);
            targetUser.setText(MainActivity.chatUser);
            targetUser.setEnabled(false);
        }else if (MainActivity.chatScreen == 3) {
            targetUser.setText(MainActivity.chatUser);
            targetUser.setEnabled(false);
            selectedSubject.setText(receivedNotifications.readSubject);
            selectedSubject.setEnabled(false);
            selectedSubject.setVisibility(View.VISIBLE);
            selectSubject.setVisibility(View.INVISIBLE);
        } else {
            selectedSubject.setVisibility(View.INVISIBLE);
            selectSubject.setVisibility(View.VISIBLE);
            targetUser.setText("");
            targetUser.setEnabled(true);
        }
        selectSubject.setAdapter(new ArrayAdapter<String>(sendNotification.this, android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.asuntos)));
        translateLanguage();
        imageUri = null;
        imageSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageName = "IMAGE_"+new  SimpleDateFormat("yyyy-MM-dd HHmm").format(new Date());
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, getString(R.string.selectImage)), 438);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==438 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            imageUri = data.getData();
            imageSel.setText(imageName);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendNotification(View view){
        userToSend = targetUser.getText().toString().toUpperCase();
        if (TextUtils.isEmpty(userToSend)){
            Toast.makeText(this, resources.getString(R.string.emptyTargetUser), Toast.LENGTH_SHORT).show();
            return;
        }
        if (MainActivity.chatScreen == 3) {
            if (TextUtils.equals(resources.getString(R.string.issueSelect), selectedSubject.getText())) {
                Toast.makeText(this, resources.getString(R.string.issueEmpty), Toast.LENGTH_SHORT).show();
                return;
            }
        }
            if (MainActivity.chatScreen != 3) {
                if (TextUtils.equals(resources.getString(R.string.issueSelect), selectSubject.getSelectedItem().toString())) {
                    Toast.makeText(this, resources.getString(R.string.issueEmpty), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        if (TextUtils.equals(MainActivity.plateUser, userToSend)){
            Toast.makeText(this, resources.getString(R.string.autoSend), Toast.LENGTH_SHORT).show();
            return;
        }
        userValidation = FirebaseDatabase.getInstance().getReference("Users");
        userValidation.orderByChild("plate_user").equalTo(userToSend).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    getblockedUser(userToSend, new FirebaseSuccessListener() {
                        @Override
                        public void onCallBack(boolean isDataFetched) {
                            if (isDataFetched){
                                Toast.makeText(getApplicationContext(), resources.getString(R.string.noNotificationSent)+" "+userToSend+". "+resources.getString(R.string.blockedUser), Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                getUserBlocked(MainActivity.plateUser, new FirebaseSuccessListener() {
                                    @Override
                                    public void onCallBack(boolean isDataFetched) {
                                        if (isDataFetched){
                                            Toast.makeText(getApplicationContext(), resources.getString(R.string.messageSent)+" "+targetUser.getText().toString()+", "+getString(R.string.sucessfully), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else{
                                            sendMesage();
                                            sendMessagePush();
                                            Toast.makeText(getApplicationContext(), resources.getString(R.string.recentMessage)+" "+targetUser.getText().toString()+", "+getString(R.string.recently), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });

                }else{
                    if (selectedLanguage.equals("ES")){
                        Toast.makeText(getApplicationContext(), "El destinatario "+targetUser.getText().toString()+" no existe", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("EN")){
                        Toast.makeText(getApplicationContext(), "Target user "+targetUser.getText().toString()+" does not exist", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("FR")){
                        Toast.makeText(getApplicationContext(), "Utilisateur cible "+targetUser.getText().toString()+" n'existe pas", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("DE")){
                        Toast.makeText(getApplicationContext(), "Zielbenutzer "+targetUser.getText().toString()+" ist nicht vorhanden", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("IT")){
                        Toast.makeText(getApplicationContext(), "Utente di destinazione "+targetUser.getText().toString()+" non esiste", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("PT")){
                        Toast.makeText(getApplicationContext(), "O destinatário "+ targetUser.getText().toString() +" não existe", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("RU")){
                        Toast.makeText(getApplicationContext(), "Получатель "+ targetUser.getText().toString () +" не существует", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("ZH")){
                        Toast.makeText(getApplicationContext(), "收件人" + targetUser.getText().toString()+ " 不存在", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("JA")){
                        Toast.makeText(getApplicationContext(), "受信者 "+ targetUser.getText().toString()+"は存在しません", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("NL")){
                        Toast.makeText(getApplicationContext(), "De ontvanger "+ targetUser.getText().toString() +" bestaat niet", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("PL")){
                        Toast.makeText(getApplicationContext(), "Odbiorca "+ targetUser.getText().toString () +" nie istnieje", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("KO")){
                        Toast.makeText(getApplicationContext(), "수신자 "+ targetUser.getText().toString() + " 이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("SV")){
                        Toast.makeText(getApplicationContext(), "Mottagaren "+ targetUser.getText().toString() +" finns inte", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("AR")){
                        Toast.makeText(getApplicationContext(), "المستقبل "+targetUser.getText().toString()+" غير موجود", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("HI")){
                        Toast.makeText(getApplicationContext(), "प्राप्तकर्ता "+ targetUser.getText().toString() +" मौजूद नहीं है", Toast.LENGTH_SHORT).show();
                    }
                    if (selectedLanguage.equals("UR")){
                        Toast.makeText(getApplicationContext(), "وصول کرنے والا "+targetUser.getText().toString()+" موجود نہیں ہے", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private void sendMesage(){
        userTarget = targetUser.getText().toString();
        if (MainActivity.chatScreen == 3) {
            subjectSel = "RE: " + selectedSubject.getText().toString();
        } else {
            subjectSel = selectSubject.getSelectedItem().toString();
        }
        messageSend=message.getText().toString();
        receiveMessageUser = FirebaseDatabase.getInstance().getReference("receivedMessage").child(MainActivity.plateUser).child(userTarget.toUpperCase());
        sendMessageUser = FirebaseDatabase.getInstance().getReference("sendMessages/"+userTarget.toUpperCase());
        String id = receiveMessageUser.push().getKey();
        id2 = sendMessageUser.push().getKey();
        if (!TextUtils.isEmpty(imageSel.getText().toString())) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Imagenes");
            StorageReference filePath = storageReference.child(MainActivity.plateUser + "-" + imageName + "." + "jpg");
            StorageTask uploadTask = filePath.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        imageUriName = downloadUrl.toString();
                    }
                    if (imageUriName != null) {
                        sendMessageUser.child(id2).setValue(new sendNotificationData(MainActivity.plateUser,subjectSel,messageSend,"No",imageName,imageUriName));
                        receiveMessageUser.child(id).setValue(new sendNotificationData(MainActivity.plateUser,subjectSel,messageSend,"No",imageName,imageUriName));
                    }
                }
            });
        }else{
            imageName = "";
            imageUriName = "";
            sendMessageUser.child(id2).setValue(new sendNotificationData(MainActivity.plateUser,subjectSel,messageSend,"No",imageName,imageUriName));
            receiveMessageUser.child(id).setValue(new sendNotificationData(MainActivity.plateUser,subjectSel,messageSend,"No",imageName,imageUriName));
        }
        targetUser.setText("");
        selectSubject.setSelection(0);
        message.setText("");
        imageSel.setText(null);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        MainActivity.chatScreen = 0;
    }

    public void getblockedUser(final String userBlock,final FirebaseSuccessListener dataFetched){
        DatabaseReference blockedUser = FirebaseDatabase.getInstance().getReference("BlockUsers").child(MainActivity.plateUser).child(userBlock);
        Query query = blockedUser.orderByChild("Blocked").equalTo("Si");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dataFetched.onCallBack(true);
                }else{
                    dataFetched.onCallBack(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getUserBlocked(final String userBlock,final FirebaseSuccessListener dataFetched){
        DatabaseReference blockedUser = FirebaseDatabase.getInstance().getReference("BlockUsers").child(userToSend).child(userBlock);
        Query query = blockedUser.orderByChild("Blocked").equalTo("Si");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dataFetched.onCallBack(true);
                }else{
                    dataFetched.onCallBack(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }


    public interface FirebaseSuccessListener{
        void onCallBack(boolean isDataFetched);
    }

    private void sendPushNotification(String tokenUser){
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try{
            json.put("to", tokenUser);
            JSONObject notification = new JSONObject();
            notification.put ("titulo", "Notificación: "+MainActivity.plateUser);
            notification.put("detalle", subjectSel);
            json.put("data",notification);
            String URL = "https://fcm.googleapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> header = new HashMap<>();
                    header.put ("content-type", "application/json");
                    header.put("authorization", "key=AAAA_uhTPWk:APA91bHXUuG1mB3KRvxx4DX-BZr5LTVhTbkAU9JY_4kv0QLrvzD-oQPxVsKOf9y9ExcPsKMyS24K7Z_VtYmcULC98RH9dVn9NSMYJqd6I4b6zXBxqdTfnjzlEkekqd4vr8ot1XU0I6sY");
                    return header;
                }
            };
            myRequest.add(request);

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void sendMessagePush() {
        DatabaseReference pushNoti = FirebaseDatabase.getInstance().getReference("pushNotification/"+userToSend);
        pushNoti.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String token=null;
                if (snapshot.exists()){
                    for (DataSnapshot ds:snapshot.getChildren()){
                        token = ds.child("tockenUser").getValue().toString();
                    }
                    Log.d("TAG", token);
                    sendPushNotification(token);
                }else{
                    Log.d("TAG", "No existe " +token);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void translateLanguage(){
        imageSel.setHint(resources.getString(R.string.imageSelectLbl));
        message.setHint(resources.getString(R.string.targetMessage));
        sendButton.setText(resources.getString(R.string.sendText));
        targetUser.setHint(resources.getString(R.string.targetUser));
    }

}
