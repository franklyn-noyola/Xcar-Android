package com.epicdeveloper.xcar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link currentLocation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class currentLocation extends Fragment {

    private Button gotomap;
    View locationCurrent;
    Intent intent;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


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

        gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), locationActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return  locationCurrent;
    }
}