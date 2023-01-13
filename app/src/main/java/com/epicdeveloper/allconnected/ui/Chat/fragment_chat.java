package com.epicdeveloper.allconnected.ui.Chat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.widget.AdapterView.*;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.epicdeveloper.allconnected.LocaleHelper;
import com.epicdeveloper.allconnected.MainActivity;
import com.epicdeveloper.allconnected.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static com.epicdeveloper.allconnected.R.*;
import static com.epicdeveloper.allconnected.R.drawable.*;
import static com.epicdeveloper.allconnected.R.layout.*;

public class fragment_chat extends AppCompatActivity {
    static String messageSent;
    private static final int CAMERA_REQUEST = 1;
    TextView userInfo;
    public static String pathFile;
    View view;
    private LayoutInflater inflater;
    public static String userBl;
    public static String userFragmentToChat;
    private DatabaseReference Chat;
    private DatabaseReference Chat2;
    private Uri fileUri;
    private  String userBlockedTitle;
    private String checker="";
    private String blocked1="";
    RelativeLayout relative;
    public Uri pictureUri;
    public static String selectedLanguage;
    public static String FileImage;
    public static String imageView="";
    private String myUri="", myUri2;
    private ListView messageList;
    EditText sendMessage;
    Context context;
    Resources resources;
    TextView carType;
    TextView carBrand;
    TextView carModel;
    TextView carColor;
    TextView carYear;
    TextView closeButton;

    private FirebaseListAdapter<chatMessage> adapter;

    private void getValidUsertoChat(){
        if (chatMainScreen.userToChat.contains("-")) {
            int indexP = chatMainScreen.userToChat.indexOf("-");
            userFragmentToChat = chatMainScreen.userToChat.substring(0,indexP - 1);
        }else{
            userFragmentToChat = chatMainScreen.userToChat;
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "ObsoleteSdkInt"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiCompat.init(new BundledEmojiCompatConfig(getApplicationContext()));
        selectedLanguage= MainActivity.userlanguage;
        context = LocaleHelper.setLocale(fragment_chat.this, selectedLanguage);
        resources = context.getResources();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setContentView(fragment_chat_fragment);
        if (Build.VERSION.SDK_INT>=29){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (MainActivity.chatScreen == 1 || MainActivity.chatScreen == 3) {
            userFragmentToChat = MainActivity.chatUser;
            DatabaseReference blockedUser = FirebaseDatabase.getInstance().getReference("BlockUsers").child(MainActivity.plateUser).child(MainActivity.chatUser);
            Query query = blockedUser.orderByChild("Blocked").equalTo("Si");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Objects.requireNonNull(getSupportActionBar()).setTitle(MainActivity.chatUser+" - "+resources.getString(string.locked));
                    }else{
                        Objects.requireNonNull(getSupportActionBar()).setTitle(MainActivity.chatUser);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            getValidUsertoChat();
            Objects.requireNonNull(getSupportActionBar()).setTitle(chatMainScreen.userToChat);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));

        sendMessage = findViewById(id.input);
        displayChatMessages();
        sendMessage.setVerticalScrollBarEnabled(true);
        sendMessage.setMovementMethod(new ScrollingMovementMethod());
        sendMessage.computeScroll();
        sendMessage.isVerticalScrollBarEnabled();

        sendMessage.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (sendMessage.hasFocus()) {

                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    else{
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        }

                }
                return false;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab);
        fab.setOnClickListener(new OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       userBlockedTitle = getSupportActionBar().getTitle().toString();
                                       if (userBlockedTitle.contains(resources.getString(string.locked))) {
                                           Toast.makeText(getApplicationContext(), resources.getString(string.NoMessageSendLockUser), Toast.LENGTH_SHORT).show();
                                           sendMessage.setText("");
                                       } else {
                                           getUserBlocked(MainActivity.plateUser, new FirebaseSuccessListener() {
                                               @Override
                                               public void onCallBack(boolean isDataFetched) {
                                                   Chat = FirebaseDatabase.getInstance().getReference("Chat");
                                                   Chat2 = FirebaseDatabase.getInstance().getReference("Chat/" + userFragmentToChat + "/" + MainActivity.plateUser);
                                                   messageSent = sendMessage.getText().toString();
                                                   String id = Chat2.push().getKey();
                                                   String id2 = Chat.push().getKey();
                                                   if (isDataFetched) {
                                                       assert id2 != null;
                                                       Chat.child(MainActivity.plateUser).child(userFragmentToChat).child(id2).setValue(new chatMessage(messageSent, MainActivity.plateUser, "text"));
                                                       setListChatUsers(messageSent, "text");
                                                   } else {
                                                       if (!TextUtils.isEmpty(messageSent)) {
                                                           assert id2 != null;
                                                           Chat.child(MainActivity.plateUser).child(userFragmentToChat).child(id2).setValue(new chatMessage(messageSent, MainActivity.plateUser, "text"));
                                                           assert id != null;
                                                           Chat2.child(id).setValue(new chatMessage(messageSent, MainActivity.plateUser, "text"));
                                                           sendMessagePush(messageSent, null);
                                                           setListChatUsers(messageSent, "text");
                                                           setListChatUsers2(messageSent, "text");
                                                       } else {
                                                           return;
                                                       }
                                                   }
                                                   sendMessage.setText("");
                                                   onStart();
                                               }
                                           });
                                       }
                                   }
                               });

        ImageButton buttonSend = findViewById(id.buttonImage);
        buttonSend.setOnClickListener(new OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              checker = "image";
                                              userBlockedTitle = getSupportActionBar().getTitle().toString();
                                              if (userBlockedTitle.contains(resources.getString(string.locked))) {
                                                  Toast.makeText(getApplicationContext(), resources.getString(string.NoMessageSendLockUser), Toast.LENGTH_SHORT).show();
                                              } else {
                                                  getUserBlocked(MainActivity.plateUser, new FirebaseSuccessListener() {
                                                      @Override
                                                      public void onCallBack(boolean isDataFetched) {
                                                          Intent intent = new Intent();
                                                          if (isDataFetched) {
                                                              blocked1 = "blocked";
                                                              intent = new Intent();
                                                              intent.setAction(Intent.ACTION_GET_CONTENT);
                                                              intent.setType("image/*");
                                                              startActivityForResult(Intent.createChooser(intent, resources.getString(string.selectImage)), 438);
                                                              setListChatUsers(messageSent, "text");
                                                          } else {
                                                              intent.setAction(Intent.ACTION_GET_CONTENT);
                                                              intent.setType("image/*");
                                                              startActivityForResult(Intent.createChooser(intent, resources.getString(string.selectImage)), 438);
                                                          }
                                                          onStart();
                                                      }
                                                  });
                                              }
                                          }
                                      });
        ImageButton buttonCamera = findViewById(id.buttonCamera);
        buttonCamera.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                checker = "image";
                                                userBlockedTitle = getSupportActionBar().getTitle().toString();
                                                System.out.println("locked: " + userBlockedTitle);
                                                if (userBlockedTitle.contains(resources.getString(string.locked))) {
                                                    Toast.makeText(getApplicationContext(), resources.getString(string.NoMessageSendLockUser), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    getUserBlocked(MainActivity.plateUser, new FirebaseSuccessListener() {
                                                        @Override
                                                        public void onCallBack(boolean isDataFetched) {
                                                            if (isDataFetched) {
                                                                blocked1 = "blocked";
                                                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                                File file = getFile();
                                                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                                                setListChatUsers(messageSent, "text");
                                                            } else {
                                                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                                                                    File pictureFile = null;
                                                                    pictureFile = getFile();
                                                                    if (pictureFile != null) {
                                                                        pathFile = pictureFile.getAbsolutePath();
                                                                        pictureUri = FileProvider.getUriForFile(fragment_chat.this, "com.epicdeveloper.allconnected.provider", pictureFile);
                                                                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                                                                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                                                    }

                                                                }

                                                            }
                                                            onStart();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                displayChatMessages();
        DatabaseReference imageSel = FirebaseDatabase.getInstance().getReference("Chat/" + MainActivity.plateUser + "/" + userFragmentToChat);
        imageSel.orderByChild("messageTimeFrom").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    ArrayList <String> messageImageView = new ArrayList<String>();
                    ArrayList <String> messageTypeView = new ArrayList<String>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        messageImageView.add(ds.child("messageText").getValue().toString());
                        messageTypeView.add(ds.child("messageType").getValue().toString());
                    }
                    messageList.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                            if (messageTypeView.get(position).equals("image")){
                                imageView = messageImageView.get(position);
                                Intent intent = new Intent(getApplicationContext(), fullViewImage.class);
                                startActivity(intent);
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private File getFile(){

        String date = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
        FileImage = "plateImage_"+date;
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(FileImage, ".jpg", storageDir);

        }catch (Exception e){
            Log.d("TAG", "Exception: "+e.toString());
        }
        return image;
    }

    private void setUserActive(final String setActive) {
        DatabaseReference userActive = FirebaseDatabase.getInstance().getReference("Chat/activeUser/"+MainActivity.userSelected);
        userActive.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Map<String, Object> updates = new HashMap<String, Object>();
                    String key="";
                    for (DataSnapshot ds:snapshot.getChildren()){
                        key = ds.getKey();
                    }
                    updates.put("active", setActive);
                    assert key != null;
                    userActive.child(key).getRef().updateChildren(updates);
                }else{
                    Map<String, Object> updates = new HashMap<String, Object>();
                    updates.put("active", setActive);
                    userActive.push().setValue(updates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==438 && resultCode==RESULT_OK && data!=null && data.getData()!=null) || requestCode==CAMERA_REQUEST){

            if (requestCode==CAMERA_REQUEST){
                fileUri = pictureUri;
            }else{
                fileUri = data.getData();
            }

            if (!checker.equals("image")){
                Toast.makeText(getApplicationContext(),resources.getString(R.string.noImage), Toast.LENGTH_SHORT).show();
            }else if(checker.equals("image")){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Imagenes");
                Chat = FirebaseDatabase.getInstance().getReference("Chat/" + MainActivity.plateUser + "/" + userFragmentToChat);
                Chat2=FirebaseDatabase.getInstance().getReference("Chat/"+userFragmentToChat+"/"+MainActivity.plateUser);
                String id = Chat.push().getKey();
                String id2 = Chat2.push().getKey();
                StorageReference filePath = storageReference.child(MainActivity.plateUser+"-"+id+"."+"jpg");
                StorageReference filePath2 = storageReference.child(userFragmentToChat+"-"+id+"."+"jpg");

                StorageTask uploadTask = filePath.putFile(fileUri);
                StorageTask uploadTask2 = filePath2.putFile(fileUri);

                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {

                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri downloadUrl = task.getResult();
                            myUri = downloadUrl.toString();
                            Chat = FirebaseDatabase.getInstance().getReference("Chat");
                            Chat.child(MainActivity.plateUser).child(userFragmentToChat).child(id2).setValue(new chatMessage(myUri, MainActivity.plateUser,"image"));
                            if (!blocked1.equals("blocked")){
                                sendMessagePush("$Foto$",myUri);
                            }
                            setListChatUsers(myUri,"image");

                        }

                    }
                });
                if (!blocked1.equals(("blocked"))) {
                    uploadTask2.continueWithTask(new Continuation() {
                        @Override
                        public Object then(@NonNull Task task) throws Exception {

                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return filePath2.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUrl = task.getResult();
                                myUri2 = downloadUrl.toString();
                                assert id != null;
                                Chat2.child(id).setValue(new chatMessage(myUri2, MainActivity.plateUser, "image"));
                                setListChatUsers2(myUri2, "image");
                            }
                        }
                    });
                }
            }else{
                Toast.makeText(this,resources.getString(R.string.noSelectedImage), Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void sendMessagePush(String messageOUt, String uri) {
        DatabaseReference userActive = FirebaseDatabase.getInstance().getReference("Chat/activeUser/"+userFragmentToChat);
        userActive.orderByChild("active").equalTo("ON").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    System.out.println("Listo para enviar");
                    DatabaseReference pushNoti = FirebaseDatabase.getInstance().getReference("pushNotification/"+userFragmentToChat);
                    pushNoti.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                String token=null;
                                for (DataSnapshot ds:snapshot.getChildren()){
                                    token = ds.child("tockenUser").getValue().toString();
                                }
                                sendPushNotification(token, messageOUt, uri);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);
        menu.findItem(id.contactInfo).setTitle(resources.getString(R.string.userInfo));
        getblockedUser(userFragmentToChat, new FirebaseSuccessListener() {
            @Override
            public void onCallBack(boolean isDataFetched) {
                if (isDataFetched) {
                    menu.findItem(id.blockUser).setIcon(locked);
                    menu.findItem(id.blockUser).setTitle("Locked");

                }else{
                    menu.findItem(id.blockUser).setIcon(unlock);
                    menu.findItem(id.blockUser).setTitle("Unlocked");
                }
            }
        });
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(final MenuItem item){
            switch (item.getItemId()){
                case android.R.id.home:
                    this.finish();
                      return true;
            case id.deleteChat:
                deleteAllChat();
                return true;
            case id.blockUser:
                invalidateOptionsMenu();
                userBl = item.getTitle().toString();
                AlertDialog alert = new AlertDialog.Builder(this).create();
                if (userBl.equals("Unlocked")) {
                    alert.setMessage(resources.getString(R.string.lockedUserMsg)+userFragmentToChat + "?");
                }else {
                    alert.setMessage(resources.getString(R.string.unlockUserMsg)+userFragmentToChat+"?");
                }
                alert.setButton(AlertDialog.BUTTON_POSITIVE,resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (userBl.equals("Locked")) {
                            unblockUser();
                            getblockedUser(userFragmentToChat, new FirebaseSuccessListener() {
                                @Override
                                public void onCallBack(boolean isDataFetched) {
                                    if (isDataFetched) {
                                        setBlockListChatUsers("Si", "No");
                                        item.setIcon(unlock);
                                        item.setTitle("Unlocked");
                                        Objects.requireNonNull(getSupportActionBar()).setTitle(userFragmentToChat);
                                        invalidateOptionsMenu();
                                    }
                                }
                            });
                            Toast.makeText(getApplicationContext(), resources.getString(R.string.unblockedUser), Toast.LENGTH_SHORT).show();
                        }
                        if (userBl.equals("Unlocked")) {
                            blockUser();
                            setBlockListChatUsers("No", "Si");
                            Toast.makeText(getApplicationContext(), resources.getString(R.string.blockedUser), Toast.LENGTH_SHORT).show();
                            getSupportActionBar().setTitle(userFragmentToChat + " - " + resources.getString(string.locked));;
                        }
                    }
                });
                alert.setButton(AlertDialog.BUTTON_NEGATIVE,resources.getString(R.string.No), (DialogInterface.OnClickListener) null);
                alert.show();
                TextView alertMessage = (TextView) alert.findViewById(android.R.id.message);
                TextView alertTitle = (TextView) alert.findViewById(android.R.id.title);
                if (alertTitle!=null) {
                    alertTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                Button alertYes = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                Button alertNos = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
                LinearLayout.LayoutParams positive = (LinearLayout.LayoutParams) alertYes.getLayoutParams();
                positive.weight = 20;
                alertMessage.setGravity(Gravity.CENTER);
                alertNos.setTextSize(15);
                alertYes.setTextSize(15);
                alertYes.setLayoutParams(positive);
                alertNos.setLayoutParams(positive);
                return true;
            case id.contactInfo:
                getInfoData();
                return true;
        }
       return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        super.onStart();
        setUserActive("ON");
        adapter.startListening();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        setUserActive("ON");
        displayChatMessages();
        adapter.startListening();
    }

    public void getblockedUser(final String userBlock,final  FirebaseSuccessListener dataFetched){
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

    public void getUserBlocked(final String userBlock,final  FirebaseSuccessListener dataFetched){
        DatabaseReference blockedUser = FirebaseDatabase.getInstance().getReference("BlockUsers").child(userFragmentToChat).child(userBlock);
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

    private void displayChatMessages() {
        messageList= (ListView) findViewById(id.list_of_messages);
        Query query = FirebaseDatabase.getInstance().getReference("Chat/" + MainActivity.plateUser + "/" + userFragmentToChat);
        FirebaseListOptions<chatMessage> options = new FirebaseListOptions.Builder<chatMessage>()
                .setQuery(query, chatMessage.class)
                .setLayout(messages)
                .build();


        adapter = new FirebaseListAdapter<chatMessage>(options) {
            @Override
            protected void populateView(View v, chatMessage model, int position) {
                TextView messageText = (TextView) v.findViewById(id.messageText);
                TextView messageTime = (TextView) v.findViewById(id.messageTime);
                ImageView messageImage = (ImageView) v.findViewById(id.viewImage);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) messageText.getLayoutParams();
                LinearLayout.LayoutParams vi = (LinearLayout.LayoutParams) messageImage.getLayoutParams();
                if (model.getMessageType().equals("text")) {
                    messageImage.setVisibility(View.GONE);
                    messageText.setVisibility(View.VISIBLE);
                    if (model.getChatFrom().equals(userFragmentToChat)) {
                        lp.gravity = Gravity.LEFT | Gravity.END;
                        lp.setMargins(10, 10, 10, 10);
                        messageText.setText(model.getMessageText());
                        messageText.setLayoutParams(lp);
                        messageText.setBackgroundResource(incoming_bubble);
                        messageText.setTextColor(Color.BLACK);
                        messageTime.setLayoutParams(lp);
                        messageText.setPadding(40, 15, 40, 25);
                        messageTime.setText(model.getMessageTimeTo());

                    }
                    if (model.getChatFrom().equals(MainActivity.plateUser)) {
                        lp.gravity = Gravity.RIGHT | Gravity.END;
                        lp.setMargins(10, 10, 10, 10);
                        messageText.setText(model.getMessageText());
                        messageText.setLayoutParams(lp);
                        messageText.setBackgroundResource(outgoing_bubble);
                        messageText.setTextColor(Color.BLACK);
                        messageTime.setLayoutParams(lp);
                        messageText.setPadding(25, 15, 40, 25);
                        messageTime.setText(model.getMessageTimeTo());

                    }
                }
                if (model.getMessageType().equals("image")){
                    messageText.setVisibility(View.GONE);
                    messageImage.setVisibility(View.VISIBLE);
                    if (model.getChatFrom().equals(userFragmentToChat)) {
                        lp.gravity = Gravity.LEFT | Gravity.END;
                        lp.setMargins(10, 10, 10, 10);
                        vi.width=500;
                        vi.height=400;
                        vi.gravity = Gravity.LEFT | Gravity.END;
                        vi.setMargins(10, 10, 10, 10);
                        Picasso.get().load(model.getMessageText()).into(messageImage);
                        messageImage.setLayoutParams(vi);
                        messageTime.setLayoutParams(lp);
                        messageTime.setText(model.getMessageTimeTo());
                    }
                    if (model.getChatFrom().equals(MainActivity.plateUser)) {
                        lp.gravity = Gravity.RIGHT | Gravity.END;
                        lp.setMargins(10, 10, 10, 10);
                        vi.width=500;
                        vi.height=400;
                        vi.gravity = Gravity.RIGHT | Gravity.END;
                        vi.setMargins(10, 10, 10, 10);
                        Picasso.get().load(model.getMessageText()).into(messageImage);
                        messageImage.setLayoutParams(vi);
                        messageTime.setLayoutParams(lp);
                        messageTime.setText(model.getMessageTimeTo());
                    }
                }

            }

        };
        messageList.setAdapter(adapter);
    }


    public void setListChatUsers(final String messageSentout, final String typeMsg){
        final DatabaseReference Chat3=FirebaseDatabase.getInstance().getReference("Chat/listChatUsers"+"/"+MainActivity.plateUser);
        Chat3.orderByChild("userToChat").equalTo(userFragmentToChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long currentDate = new Date().getTime();
                String messageTime = (String) DateFormat.format("dd/MM/yyyy HH:mm",
                        currentDate);
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> updates = new HashMap<String, Object>();
                        updates.put("messageText", messageSentout);
                        updates.put("messageTime", messageTime);
                        updates.put("blockedUs", "No");
                        updates.put("messageType", typeMsg);
                        ds.getRef().updateChildren(updates);
                    }

                }else{
                    Chat3.push().setValue(new listUsertoChat(messageSentout, userFragmentToChat, "No",typeMsg));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }

    public void setBlockListChatUsers(final String first, final String second){
        final DatabaseReference Chat3=FirebaseDatabase.getInstance().getReference("Chat/listChatUsers"+"/"+MainActivity.plateUser);
        Chat3.orderByChild("userToChat").equalTo(userFragmentToChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String getBlocked=null;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        getBlocked = ds.child("blockedUs").getValue().toString();
                    }
                    if (getBlocked.equals(first)){
                        Map<String, Object> updates;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            updates = new HashMap<String, Object>();
                            updates.put("blockedUs", second);
                            ds.getRef().updateChildren(updates);
                            return;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void setListChatUsers2(final String messageSentout, final String typeMsg){
        final DatabaseReference Chat4=FirebaseDatabase.getInstance().getReference("Chat/listChatUsers"+"/"+userFragmentToChat);
        Chat4.orderByChild("userToChat").equalTo(MainActivity.plateUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long currentDate = new Date().getTime();
                String  messageTime = (String) DateFormat.format("dd/MM/yyyy HH:mm",
                        currentDate);
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> updates = new HashMap<String, Object>();
                        updates.put("messageText", messageSentout);
                        updates.put("messageTime", messageTime);
                        updates.put("messageType", typeMsg);
                        ds.getRef().updateChildren(updates);
                    }

                }else{
                    Chat4.push().setValue(new listUsertoChat(messageSentout, MainActivity.plateUser,"",typeMsg));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteAllChat(){
    AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setMessage(resources.getString(R.string.deleteChatMsg));
        alert.setButton(AlertDialog.BUTTON_POSITIVE,resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                confirmDeleteChat();
                confirmDeleteListofUsers();

            }
        });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE,resources.getString(R.string.No), (DialogInterface.OnClickListener) null);
        alert.show();
        TextView alertMessage = (TextView) alert.findViewById(android.R.id.message);
        TextView alertTitle = (TextView) alert.findViewById(android.R.id.title);
        if (alertTitle!=null) {
            alertTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        Button alertYes = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button alertNos = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams positive = (LinearLayout.LayoutParams) alertYes.getLayoutParams();
        positive.weight = 20;
        alertMessage.setGravity(Gravity.CENTER);
        alertNos.setTextSize(15);
        alertYes.setTextSize(15);
        alertYes.setLayoutParams(positive);
        alertNos.setLayoutParams(positive);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        setUserActive("OFF");
        if (MainActivity.fromNotifications == 1) {
            MainActivity.fromNotifications = 0;
            finishAffinity();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        setUserActive("OFF");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (MainActivity.fromNotifications == 1) {
            MainActivity.getBackGround = 0;
        }
        setUserActive("OFF");

    }

    @Override
    public void onStop(){
        super.onStop();
        setUserActive("OFF");

    }

    @Override
    public void onResume(){
        super.onResume();
        setUserActive("ON");

    }

    private void confirmDeleteChat(){
        DatabaseReference Chat = FirebaseDatabase.getInstance().getReference("Chat/"+MainActivity.plateUser);
        Chat.child(userFragmentToChat).removeValue();
    }
    private void confirmDeleteListofUsers() {
        DatabaseReference Chat = FirebaseDatabase.getInstance().getReference("Chat/listChatUsers/"+MainActivity.plateUser);
        Chat.orderByChild("userToChat").equalTo(userFragmentToChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = null;
                for (DataSnapshot ds: snapshot.getChildren()){
                    key = ds.getKey();
                }
                snapshot.child(key).getRef().removeValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void blockUser(){
        final DatabaseReference blockUser = FirebaseDatabase.getInstance().getReference("BlockUsers/"+MainActivity.plateUser);
        Query query = blockUser.child(userFragmentToChat);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    snapshot.getRef().push().child("Blocked").setValue("Si");
                }else{
                    Map<String, Object> updates;
                    for (DataSnapshot ds:snapshot.getChildren()){
                        updates = new HashMap<String, Object>();
                        updates.put("Blocked","Si");
                        ds.getRef().updateChildren(updates);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public void unblockUser(){
        DatabaseReference blockUser = FirebaseDatabase.getInstance().getReference("BlockUsers/"+MainActivity.plateUser);
        blockUser.child(userFragmentToChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updates;
                for (DataSnapshot ds:snapshot.getChildren()){
                    updates = new HashMap<String, Object>();
                    updates.put("Blocked", "No");
                    ds.getRef().updateChildren(updates);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendPushNotification(String tokenUser, String messageOut, String uri){
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try{
            json.put("to", tokenUser);
            JSONObject notification = new JSONObject();
            notification.put ("titulo", "Chat: "+MainActivity.plateUser);
            if (messageOut.equals("$Foto$")){
                notification.put("detalle","Foto");
                notification.put("image",uri);
            }else{
                notification.put("detalle", messageOut);
            }
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

    protected void getInfoData() {
        view = inflater.inflate(userinfo_popup, null);
        relative = new RelativeLayout(this);
        userInfo = view.findViewById(R.id.userInfo);
        if (selectedLanguage.equals("RU")){
            userInfo.setTextSize(15);
        }else{
            userInfo.setTextSize(20);
        }
        userInfo.setText(resources.getString(string.userInfo));
        carType = view.findViewById(id.carTypeText);
        carBrand =  view.findViewById(id.carBrandText);
        carModel = view.findViewById(id.carModelText);
        carColor =  view.findViewById(id.carColorText);
        carYear =  view. findViewById(id.carYearText);
        closeButton = view.findViewById(id.closebutton);
        closeButton.setText(resources.getString(string.closeButton));
        final PopupWindow pw = new PopupWindow(view, 800 , 550, true);
        DatabaseReference Users = FirebaseDatabase.getInstance().getReference("Users");
        Users.orderByChild("plate_user").equalTo(userFragmentToChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String typeProfileCarUser="";
                    String brandProfileCarUser="";
                    String modelProfileCaruser="";
                    String colorProfileCarUser="";
                    String yearProfileCarUser="";
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        typeProfileCarUser = snapshot.child("cartype").getValue().toString();
                        brandProfileCarUser = snapshot.child("carbrand").getValue().toString();
                        modelProfileCaruser = snapshot.child("carmodel").getValue().toString();
                        colorProfileCarUser = snapshot.child("carcolor").getValue().toString();
                        yearProfileCarUser = snapshot.child("year").getValue().toString();
                    }

                    if (TextUtils.isEmpty(typeProfileCarUser)) {
                        carType.setText(resources.getString(R.string.typeVehicle)+": "+resources.getString(R.string.noData));
                    }else{

                        carType.setText(resources.getString(R.string.typeVehicle)+": "+typeProfileCarUser);
                    }
                    if (TextUtils.isEmpty(brandProfileCarUser)){
                        carBrand.setText(resources.getString(R.string.brandHint)+": "+ resources.getString(R.string.noData));
                    }else{
                        carBrand.setText(resources.getString(R.string.brandHint)+": "+brandProfileCarUser);
                    }
                    if (TextUtils.isEmpty(modelProfileCaruser)){
                        carModel.setText(resources.getString(R.string.modelHint)+": "+ resources.getString(R.string.noData));
                    }else{
                        carModel.setText(resources.getString(R.string.modelHint)+": "+modelProfileCaruser);
                    }
                    if (TextUtils.isEmpty(colorProfileCarUser)){
                        carColor.setText(resources.getString(R.string.colorHint)+": "+resources.getString(R.string.noData));
                    }else{
                        carColor.setText(resources.getString(R.string.colorHint)+": "+colorProfileCarUser);
                    }
                    if (TextUtils.isEmpty(yearProfileCarUser)){
                        carYear.setText(resources.getString(R.string.yearHint)+": "+resources.getString(R.string.noData));
                    }else{
                        carYear.setText(resources.getString(R.string.yearHint)+": "+yearProfileCarUser);
                    }

                }else{
                        carType.setText(resources.getString(R.string.typeVehicle)+": "+resources.getString(R.string.noData));
                        carBrand.setText(resources.getString(R.string.brandHint)+": "+ resources.getString(R.string.noData));
                        carModel.setText(resources.getString(R.string.modelHint)+": "+ resources.getString(R.string.noData));
                        carColor.setText(resources.getString(R.string.colorHint)+": "+resources.getString(R.string.noData));
                        carYear.setText(resources.getString(R.string.yearHint)+": "+resources.getString(R.string.noData));
                }
                pw.showAtLocation(relative, Gravity.NO_GRAVITY, 150, 300);
                closeButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}