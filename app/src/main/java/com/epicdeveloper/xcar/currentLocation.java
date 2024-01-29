package com.epicdeveloper.xcar;

import static com.epicdeveloper.xcar.MainActivity.email_user;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link currentLocation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class currentLocation extends Fragment {

    private Button gotomap;
    View locationCurrent;

    public static float longitudfield;
    public static float latitudfield;
    static String place;

    static TextView longitudLbl;
    static TextView latitudLbl;
    static TextView placeLbl;
    public  Object longintude;
    public Object latitude;
    public Object placeF;
    public static String type;
    DatabaseReference Location;
    Intent intent;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button viewLocation;

    String selectedLanguage;

    Context context;

    Resources resources;

    private String mParam1;
    private String mParam2;

    public currentLocation() {

    }


    public static currentLocation newInstance(String param1, String param2) {
        currentLocation fragment = new currentLocation();
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
        locationCurrent = inflater.inflate(R.layout.fragment_current_location, container, false);
        gotomap = locationCurrent.findViewById(R.id.gotomap);
        longitudLbl = locationCurrent.findViewById(R.id.longitudLbl);
        latitudLbl = locationCurrent.findViewById(R.id.latitudLbbl);
        placeLbl = locationCurrent.findViewById(R.id.placeLbl);
        viewLocation = locationCurrent.findViewById(R.id.viewLocation);
        gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "M";
                intent = new Intent(getActivity(), locationActivity.class);
                startActivity(intent);
            }
        });

        getLocationData();

        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lati = latitudLbl.getText().toString();
                String longi = longitudLbl.getText().toString();
                int latiSubstr = lati.indexOf(":");
                int longiSubstr = longi.indexOf(":");
                int latiCoordinate = latiSubstr+2;
                int longiCoordinate = longiSubstr+2;
                if (lati.contains(resources.getString(R.string.noData).toString()) || longi.equals(resources.getString(R.string.noData).toString())){
                    Toast.makeText(context,resources.getString(R.string.noDataLocation),Toast.LENGTH_SHORT).show();
                }else{
                    String getLongi = longi.substring(longiCoordinate);
                    String getlati = lati.substring(latiCoordinate);
                    longitudfield = new Float(getLongi);
                    latitudfield = new Float(getlati);
                    type = "C";
                    intent = new Intent(getActivity(), locationActivity.class);
                    startActivity(intent);
                    System.out.println("Longitud: "+longitudfield+" Latitud: "+latitudfield);
                }

            }
        });

        // Inflate the layout for this fragment
        return  locationCurrent;
    }



    public void getLocationData(){
        String dot1 = new String (email_user);
        String dot2 = dot1.replace(".","_");
        Location = FirebaseDatabase.getInstance().getReference("Location/"+dot2);

        Location.orderByChild("Type").equalTo("C").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        longintude = dataSnapshot.child("Longitude").getValue();
                        latitude = snapshot.child("Latitude").getValue();
                        placeF = snapshot.child("Place").getValue();

                    }
                    longitudLbl.setText(resources.getString(R.string.longitudLbl)+" "+longintude.toString());
                    latitudLbl.setText(resources.getString(R.string.latitudLbl)+" "+latitude.toString());
                    placeLbl.setText(resources.getString(R.string.placeLabel)+" "+placeF.toString());
                }else{
                    longitudLbl.setText(resources.getString(R.string.longitudLbl)+" "+ resources.getString(R.string.noData));
                    latitudLbl.setText(resources.getString(R.string.latitudLbl)+" "+ resources.getString(R.string.noData) );
                    placeLbl.setText(resources.getString(R.string.placeLabel)+" "+resources.getString(R.string.noData));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}