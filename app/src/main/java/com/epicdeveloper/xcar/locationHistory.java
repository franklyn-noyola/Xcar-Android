package com.epicdeveloper.xcar;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.codecrafters.tableview.TableView;


public class locationHistory extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TableView tableView;
    View historyLocation;

    String selectedLanguage;

    Context context;

    Resources resources;

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
        tableView = historyLocation.findViewById(R.id.historyLocationTable);
        String [] header = {resources.getString(R.string.longitudLbl),resources.getString(R.string.latitudLbl),resources.getString(R.string.placeLabel),resources.getString(R.string.dateLbl)};
        return historyLocation;
    }
}