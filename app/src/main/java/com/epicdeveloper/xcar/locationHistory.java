package com.epicdeveloper.xcar;

import static com.epicdeveloper.xcar.MainActivity.email_user;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.epicdeveloper.xcar.ui.Chat.fragment_chat;
import com.epicdeveloper.xcar.ui.Chat.listUsertoChat;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


public class locationHistory extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TableView tableView;
    View historyLocation;

    String selectedLanguage;

    Context context;

    ListView locationView;

    Resources resources;

    private FirebaseListAdapter<locationHistoryList> locationAdapter;

    public locationHistory() {

    }


    public static locationHistory newInstance(String param1, String param2) {
        locationHistory fragment = new locationHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedLanguage = MainActivity.userlanguage;
        context = LocaleHelper.setLocale(getActivity(),selectedLanguage);
        resources = context.getResources();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        historyLocation = inflater.inflate(R.layout.fragment_location_history, container, false);

        getLocationHistory();
        return historyLocation;
    }
    @Override
    public void onStart(){
        locationAdapter.startListening();
        super.onStart();
    }

    public void getLocationHistory() {
        locationView = historyLocation.findViewById(R.id.locationhistoricalList);
        String dot1 = new String (email_user);
        String dot2 = dot1.replace(".","_");
        DatabaseReference location = FirebaseDatabase.getInstance().getReference("Location/"+dot2);
        Query query = location.orderByChild("type").equalTo("H").limitToLast(5);

        FirebaseListOptions<locationHistoryList> options = new FirebaseListOptions.Builder<locationHistoryList>()
                .setQuery(query, locationHistoryList.class)
                .setLayout(R.layout.locationdatahistory)
                .build();

        locationAdapter = new FirebaseListAdapter<locationHistoryList>(options) {

            @Override
            protected void populateView(@NonNull View view, @NonNull locationHistoryList model, int position) {

                TextView longitude = view.findViewById(R.id.longituLocationdlbl);
                TextView latitude = view.findViewById(R.id.latitudLocationlbl);
                TextView place = view.findViewById(R.id.placeslblLocation);
                TextView date = view.findViewById(R.id.datelbl);
                longitude.setText(resources.getString(R.string.longitudLbl)+" " + model.getLongitude());
                latitude.setText(resources.getString(R.string.latitudLbl)+" "+model.getLatitude());;
                place.setText(resources.getString(R.string.placeLabel)+" "+model.getPlace());
                date.setText(resources.getString(R.string.dateLbl)+" "+model.getDate());
            }
        };
            locationView.setAdapter(locationAdapter);
            //locationAdapter.startListening();



        /*locationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView getUser = (TextView) view.findViewById(R.id.message_user);
                userToChat = getUser.getText().toString();
                MainActivity.chatScreen = 2;
                Intent intent = new Intent(getActivity(), fragment_chat.class);
                startActivity(intent);
            }
        });*/

    }

}