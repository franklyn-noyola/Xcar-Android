package com.epicdeveloper.xcar;

import static com.epicdeveloper.xcar.MainActivity.email_user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class currentLocation extends Fragment {

    private Button gotomap;
    View locationCurrent;

    public static float longitudfield;
    public static float latitudfield;
    static String place;

    public static double longitudData;
    public static double latitudData;
    static String placeData;

    static TextView longitudLbl;
    static TextView latitudLbl;
    static TextView placeLbl;
    public  Object longintude;
    public Object latitude;
    public Object placeF;
    public static String type;
    private DatabaseReference Location;
    Intent intent;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button viewLocation;

    String selectedLanguage;

    Context context;

    Resources resources;

    static TextView newCurrentLbl;

    static TextView longiFieldLbl;
    static TextView latiFieldLbl;
    static TextView placeFieldLbl;

    static Button newLocation;

    static Button saveButton;
    static Button cancelButton;
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
        longiFieldLbl = locationCurrent.findViewById(R.id.longitudLbl2);
        latiFieldLbl = locationCurrent.findViewById(R.id.latitudLbbl2);
        placeFieldLbl = locationCurrent.findViewById(R.id.placeLbl2);
        newCurrentLbl = locationCurrent.findViewById(R.id.newCurrentLbl);
        newLocation = locationCurrent.findViewById(R.id.newLocation);
        saveButton = locationCurrent.findViewById(R.id.saveLocation);
        cancelButton = locationCurrent.findViewById(R.id.cancelLocation);

        newLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "N";
                newLocation.setEnabled(false);
                gotomap.setVisibility(View.VISIBLE);
            }
        });
        gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "N";
                intent = new Intent(getActivity(), locationActivity.class);
                startActivity(intent);
            }
        });
        setFieldsInvisible();
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

        saveButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                                              alert.setMessage(resources.getString(R.string.locationAlert));
                                              alert.setButton(AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.yes), new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialogInterface, int i) {

                                                      updateCurrentLocationData();
                                                      Toast.makeText(getActivity(), resources.getString(R.string.locationAdded), Toast.LENGTH_SHORT).show();
                                                      setFieldsInvisible();
                                                      newLocation.setEnabled(true);
                                                  }
                                              });
                                              alert.setButton(AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.No), new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      setFieldsInvisible();
                                                      newLocation.setEnabled(true);
                                                  }
                                              });
                                              alert.show();
                                          }
                                      });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFieldsInvisible();
                newLocation.setEnabled(true);
            }
        });

        // Inflate the layout for this fragment
        return  locationCurrent;
    }

    public void setFieldsInvisible(){
        gotomap.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        longiFieldLbl.setVisibility(View.INVISIBLE);
        latiFieldLbl.setVisibility(View.INVISIBLE);
        placeFieldLbl.setVisibility(View.INVISIBLE);
        newCurrentLbl.setVisibility(View.INVISIBLE);;
    }
    public static void setFieldsVisible(){
        longiFieldLbl.setVisibility(View.VISIBLE);
        latiFieldLbl.setVisibility(View.VISIBLE);
        placeFieldLbl.setVisibility(View.VISIBLE);
        newCurrentLbl.setVisibility(View.VISIBLE);;
    }

    public void getLocationData() {
        String dot1 = new String(email_user);
        String dot2 = dot1.replace(".", "_");
        Location = FirebaseDatabase.getInstance().getReference("Location/" + dot2);

        Location.orderByChild("type").equalTo("C").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        longintude = dataSnapshot.child("longitude").getValue();
                        latitude = dataSnapshot.child("latitude").getValue();
                        placeF = dataSnapshot.child("place").getValue();

                    }
                    longitudLbl.setText(resources.getString(R.string.longitudLbl) + " " + longintude.toString());
                    latitudLbl.setText(resources.getString(R.string.latitudLbl) + " " + latitude.toString());
                    placeLbl.setText(resources.getString(R.string.placeLabel) + " " + placeF.toString());
                } else {
                    longitudLbl.setText(resources.getString(R.string.longitudLbl) + " " + resources.getString(R.string.noData));
                    latitudLbl.setText(resources.getString(R.string.latitudLbl) + " " + resources.getString(R.string.noData));
                    placeLbl.setText(resources.getString(R.string.placeLabel) + " " + resources.getString(R.string.noData));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }

        public void updateCurrentLocationData() {
            String lati = latitudLbl.getText().toString();
            String longi = longitudLbl.getText().toString();
            if (lati.contains(resources.getString(R.string.noData).toString()) || longi.equals(resources.getString(R.string.noData).toString())) {
                saveLocationData();
            } else {
                String dot1 = new String(email_user);
                String dot2 = dot1.replace(".", "_");
                Location = FirebaseDatabase.getInstance().getReference("Location/" + dot2);
                Location.orderByChild("type").equalTo("C").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Map<String, Object> updates = new HashMap<String, Object>();
                            updates.put("type", "H");
                            ds.getRef().updateChildren(updates);
                        }
                        saveLocationData();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }
        public void saveLocationData(){
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
            String currentDateTime = dateFormat.format(currentDate);
            String dot1 = new String(email_user);
            String dot2 = dot1.replace(".", "_");
            String longiData = String.valueOf(longitudData);
            String latiData = String.valueOf(latitudData);
            final LocationData locationData = new LocationData(currentDateTime,"C",longiData,latiData,placeData);
            DatabaseReference Location = FirebaseDatabase.getInstance().getReference("Location/"+dot2);
            String id = Location.push().getKey();
            Location.child(id).setValue(locationData);
            getLocationData();

        }

    }
