package com.epicdeveloper.xcar.ui.receivedNotifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.epicdeveloper.xcar.LocaleHelper;
import com.epicdeveloper.xcar.MainActivity;
import com.epicdeveloper.xcar.R;
import com.epicdeveloper.xcar.ui.sendNotifications.sendNotification;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


import static android.widget.AdapterView.*;
import static android.widget.Toast.LENGTH_SHORT;

public class receivedNotifications extends Fragment {
    public static String readTargetUser;
    public static String readSubject;
    public static String readMessage;
    public static String readMessageTime;
    public static String imageName;
    public ArrayList<String> imageViewUri;
    public static String imageViewName;
    public static String itemgetTime;
    public ArrayList<String> getTime;
    TextView imageView;
    String selectedLanguage;
    Context context;
    String selectedPlate;
    Resources resources;
    View root;
    public ListView notificationList;


    private FirebaseListAdapter<receivedNotificationsData> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root= inflater.inflate(R.layout.fragment_receivednotifications_fragment, container, false);
        selectedLanguage = MainActivity.userlanguage;
        if (TextUtils.isEmpty(MainActivity.getSelectedPlate)){
            selectedPlate = MainActivity.plate_user;
        }else{
            selectedPlate = MainActivity.getSelectedPlate;
        }

        setHasOptionsMenu((true));
        context = LocaleHelper.setLocale(getActivity(), selectedLanguage);
        resources = context.getResources();
        displayNotifications();
            registerForContextMenu(notificationList);
         notificationList.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemgetTime = getTime.get(position);
                return false;
            }
        });

        notificationList.setOnItemClickListener(new OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        TextView imageNameField =(TextView) view.findViewById(R.id.imageView);
                                                        if (imageNameField.getVisibility()==View.VISIBLE){
                                                            imageName =((TextView) view.findViewById(R.id.imageView)).getText().toString();
                                                            imageViewName = imageViewUri.get(position);
                                                        }else{
                                                            imageName = "";
                                                        }
                                                        Log.d("TAG",imageName);
                                                        readTargetUser = ((TextView) view.findViewById(R.id.from_text)).getText().toString();
                                                        readSubject= ((TextView) view.findViewById(R.id.subject_text)).getText().toString();
                                                        readMessage = ((TextView) view.findViewById(R.id.body_text)).getText().toString();
                                                        itemgetTime = getTime.get(position);
                                                        readMessageTime = ((TextView) view.findViewById(R.id.notification_time)).getText().toString();
                                                        Intent intent = new Intent(getActivity(), viewNotification.class);
                                                        startActivity(intent);
                                                    }
                                                });


        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.sendNotificationButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), sendNotification.class);
                startActivity(intent);
            }
        });
            return root;
    }

    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle().equals(resources.getString(R.string.delete))) {
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setMessage(resources.getString(R.string.delNotificationMessage));
            alert.setButton(AlertDialog.BUTTON_POSITIVE,resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deleteNotificacion();
                    Toast.makeText(getActivity(), resources.getString(R.string.deletedNotification), LENGTH_SHORT).show();
                }
            });
            alert.setButton(AlertDialog.BUTTON_NEGATIVE,resources.getString(R.string.No), (DialogInterface.OnClickListener) null);
            alert.show();
            TextView alertMessage = (TextView) alert.findViewById(android.R.id.message);
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
        return true;
    }

    private void deleteNotificacion(){
        DatabaseReference delNotification = FirebaseDatabase.getInstance().getReference("sendMessages");
        Query query = delNotification.child(selectedPlate).orderByChild("messageTime").equalTo(itemgetTime);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                snapshot.getRef().setValue(null);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu4, menu);
        menu.findItem(R.id.delete).setTitle(resources.getString(R.string.delete));
    }

    public void displayNotifications(){
        getTime = new ArrayList<>();
        imageViewUri = new ArrayList<>();
        notificationList = (ListView) root.findViewById(R.id.list_of_notifications);
        Query query = FirebaseDatabase.getInstance().getReference("sendMessages/"+ selectedPlate);
        FirebaseListOptions<receivedNotificationsData> options = new FirebaseListOptions.Builder<receivedNotificationsData>()
                .setQuery(query, receivedNotificationsData.class)
                .setLayout(R.layout.notifications)
                .build();

        adapter = new FirebaseListAdapter<receivedNotificationsData>(options) {
            @Override
            protected void populateView(View v, receivedNotificationsData model, int position) {
                ImageView readData = (ImageView) v.findViewById(R.id.imageViwID);
                TextView userData = (TextView) v.findViewById(R.id.from_text);
                TextView subjectData = (TextView) v.findViewById(R.id.subject_text);
                TextView bodyData = (TextView) v.findViewById(R.id.body_text);
                TextView notiTime = (TextView) v.findViewById(R.id.notification_time);
                imageView = (TextView) v.findViewById(R.id.imageView);
                readTargetUser = model.getuserPlateData();
                readSubject = model.getsubjectUserData();
                readMessage = model.getmessageUserData();
                imageName = model.getImageName();
                getTime.add(model.getMessageTime());
                readMessageTime = model.getMessageTime();
                userData.setText(model.getuserPlateData());
                subjectData.setText(model.getsubjectUserData());
               bodyData.setText(model.getmessageUserData());
                if (model.getLeido().equals("No")){
                    readData.setImageResource(R.drawable.closemail);
                }else{
                    readData.setImageResource(R.drawable.openmail);
                }
                notiTime.setText(model.getMessageTime());
                if (imageName.equals("")){
                    imageView.setVisibility(View.GONE);
                }else{
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setText(model.getImageName());
                    imageViewUri.add(model.getImageUri());
                }

            }
        };
        notificationList.setAdapter(adapter);
    }


}
