package com.epicdeveloper.xcar.ui.Chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.epicdeveloper.xcar.LocaleHelper;
import com.epicdeveloper.xcar.MainActivity;
import com.epicdeveloper.xcar.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class chatMainScreen extends Fragment {


    SearchView searchView;
    ListView listView;
    String selectedLanguage;
    private LayoutInflater inflaterView;
    private DatabaseReference Users;
    public static  String userToChat;
    Context context;
    Resources resources;
    AdView adview;
    View root;


    TextView messageUser;

    private FirebaseListAdapter<listUsertoChat> adapterFirst;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getActivity(),selectedLanguage);
        resources = context.getResources();
        root= inflater.inflate(R.layout.chat_main_screen, container, false);
        MobileAds.initialize(getActivity(), initializationStatus ->
                resources.getString(R.string.addMob));
        adview = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adview.loadAd(adRequest);
        searchView = root.findViewById(R.id.searchView);
        searchView.setQueryHint(resources.getString((R.string.plate_enter)));
         inflaterView = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                                          @Override
                                          public boolean onClose() {
                                              searchView.setQuery("", false);
                                              searchView.onActionViewCollapsed();
                                              searchView.clearFocus();                                              return false;
                                          }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String query) {
                    getUserData(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

        getChatUserList();
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView getUser = (TextView) view.findViewById(R.id.message_user);
                userToChat = getUser.getText().toString();
                return false;
            }
        });
            return root;

        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu4, menu);
        menu.findItem(R.id.delete).setTitle(resources.getString(R.string.delete));
    }

        @Override
        public void onStart(){
            adapterFirst.startListening();
            super.onStart();
        }

        private void getUserData(final String userData){
            Users = FirebaseDatabase.getInstance().getReference("Users");
            Users.orderByChild("plate_user").equalTo(userData.toUpperCase()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Object getExistUser = null;
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()){
                          getExistUser = ds.child("plate_user").getValue();
                        }
                        if (getExistUser.toString().equals(MainActivity.plateUser)){
                            if (selectedLanguage.equals("ES")){
                                Toast.makeText(getActivity(), "El usuario "+userData.toUpperCase()+" no se puede enviar un automensaje.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("EN")){
                                Toast.makeText(getActivity(), "User "+userData.toUpperCase()+" cannot send message to himself/herself.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("FR")){
                                Toast.makeText(getActivity(), "L'utilisateur "+userData.toUpperCase()+" ne peut pas s'envoyer de message.", Toast.LENGTH_SHORT).show();

                            }
                            if (selectedLanguage.equals("DE")){
                                Toast.makeText(getActivity(), "Der Benutzer "+userData.toUpperCase()+" kann keine Nachricht an sich selbst senden.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("IT")){
                                Toast.makeText(getActivity(), "L'utente  "+userData.toUpperCase()+"  non può inviare messaggi a se stesso.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("PT")){
                                Toast.makeText(getActivity(), "O usuário "+ userData.toUpperCase() +" não pode enviar mensagem para si mesmo.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("RU")){
                                Toast.makeText(getActivity(), "Пользователь "+ userData.toUpperCase() +" не может отправлять автоматическое сообщение.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("ZH")){
                                Toast.makeText(getActivity(), "用户" + userData.toUpperCase()+" 无法发送自动消息。", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("JA")){
                                Toast.makeText(getActivity(), "ユーザー "+ userData.toUpperCase()+" は自動メッセージを送信できません。", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("NL")){
                                Toast.makeText(getActivity(), "De gebruiker "+ userData.toUpperCase() +" kan geen automatisch bericht verzenden.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("PL")){
                                Toast.makeText(getActivity(), "Użytkownik "+ userData.toUpperCase() +" nie może wysłać automatycznej wiadomości.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("KO")){
                                Toast.makeText(getActivity(), "사용자 "+ userData.toUpperCase() +" 는 자동 메시지를 보낼 수 없습니다.", Toast.LENGTH_SHORT).show();
                            }

                            if (selectedLanguage.equals("SV")){
                                Toast.makeText(getActivity(), "Användaren "+ userData.toUpperCase() +" kan inte skicka ett automatiskt meddelande.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("AR")){
                                Toast.makeText(getActivity(), "المستخدم "+userData.toUpperCase()+" لا يمكن إرسال رسالة تلقائية.", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("HI")){
                                Toast.makeText(getActivity(), "उपयोगकर्ता "+ userData.toUpperCase() +" एक ऑटो संदेश नहीं भेज सकता है।", Toast.LENGTH_SHORT).show();
                            }
                            if (selectedLanguage.equals("UR")){
                                Toast.makeText(getActivity(), "صارف "+userData.toUpperCase()+" خودکار پیغام نہیں بھیجا جاسکتا۔", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }else{
                            getUserBlocked(userData.toUpperCase(), new FirebaseSuccessListener() {
                                @Override
                                public void onCallBack(boolean isDataFetched) {
                                    if (isDataFetched) {
                                        userToChat = userData.toUpperCase() + " - "+resources.getString(R.string.locked);
                                    } else {
                                        userToChat = userData.toUpperCase();
                                    }
                                    MainActivity.chatScreen = 2;
                                    Intent intent = new Intent (getActivity(), fragment_chat.class);
                                    startActivity(intent);
                                }
                                });
                    }
                    }else{
                        Toast.makeText(getActivity(), resources.getString(R.string.noExists), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    public void getUserBlocked(final String userBlock,final FirebaseSuccessListener dataFetched){
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

    public interface FirebaseSuccessListener{
        void onCallBack(boolean isDataFetched);
    }

      public void getChatUserList() {
            listView = root.findViewById(R.id.messageList);
            Query query = FirebaseDatabase.getInstance().getReference("Chat/listChatUsers/" + MainActivity.plateUser);
            FirebaseListOptions<listUsertoChat> options = new FirebaseListOptions.Builder<listUsertoChat>()
                    .setQuery(query, listUsertoChat.class)
                    .setLayout(R.layout.messageslist)
                    .build();

            adapterFirst = new FirebaseListAdapter<listUsertoChat>(options) {
                @Override
                protected void populateView(View v, listUsertoChat model, int position) {

                    // Get references to the views of message.xml
                    TextView messageText = (TextView) v.findViewById(R.id.message_text);
                    messageUser = (TextView) v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView) v.findViewById(R.id.message_time);
                    Drawable image = getActivity().getResources().getDrawable(R.drawable.imageicon);
                    int h = image.getIntrinsicHeight();
                    int w = image.getIntrinsicWidth();
                    image.setBounds( 0, 0, w, h );
                    if (model.getBlockedUs().equals("Si")){
                        messageText.setTextColor(Color.GRAY);
                        messageUser.setText(model.getUserToChat()+" - "+resources.getString(R.string.locked));
                    }else{
                        messageText.setTextColor(Color.BLACK);
                        messageUser.setText(model.getUserToChat());
                    }
                    if (model.getMessageType().equals("image")){
                        messageText.setCompoundDrawables(image,null,null,null);
                        messageText.setText("  Foto");
                    }else{
                        messageText.setCompoundDrawables(null,null,null,null);
                        messageText.setText(model.getMessageText());
                    }
                    messageTime.setText(model.getMessageTime());
                }
            };
            listView.setAdapter(adapterFirst);
            adapterFirst.startListening();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView getUser = (TextView) view.findViewById(R.id.message_user);
                    userToChat = getUser.getText().toString();
                    MainActivity.chatScreen = 2;
                    Intent intent = new Intent(getActivity(), fragment_chat.class);
                    startActivity(intent);
                }
            });

        }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(resources.getString(R.string.delete))) {
            deleteAllChat();
        }
        return true;
    }

    private void deleteChat(){
        DatabaseReference Chat = FirebaseDatabase.getInstance().getReference("Chat/"+MainActivity.plateUser);
        Chat.child(userToChat).removeValue();
    }

    private void deleteListChat(){
        DatabaseReference Chat = FirebaseDatabase.getInstance().getReference("Chat/listChatUsers/"+MainActivity.plateUser);
        Chat.orderByChild("userToChat").equalTo(userToChat).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void deleteAllChat(){
        AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
        alert.setMessage(resources.getString(R.string.deleteChatMsg));
        alert.setButton(AlertDialog.BUTTON_POSITIVE,resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteChat();
                deleteListChat();
                Toast.makeText(getActivity(), resources.getString(R.string.deletedChat), Toast.LENGTH_SHORT).show();
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

}
