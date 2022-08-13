package com.example.myapplication1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link TodayFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TodayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String receivedData;
    HashMap<Integer, String> weatherCode = new HashMap<Integer, String>();

    public TodayFragment() {

        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TodayFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static TodayFragment newInstance(String param1, String param2) {
//        TodayFragment fragment = new TodayFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_today, container, false);


        weatherCode.put(1000, "Clear");
        weatherCode.put(1001, "Cloudy");
        weatherCode.put(1100, "Mostly Clear");
        weatherCode.put(1101, "Partly Cloudy");
        weatherCode.put(1102, "Mostly Cloudy");
        weatherCode.put(2000, "Fog");
        weatherCode.put(2100, "Light Fog");
        weatherCode.put(3000, "Light WInd");
        weatherCode.put(3001, "Wind");
        weatherCode.put(3002, "Strong Wind");
        weatherCode.put(4000, "Drizzle");
        weatherCode.put(4001, "Rain");
        weatherCode.put(4200, "Light Rain");
        weatherCode.put(4201, "Heavy Rain");
        weatherCode.put(5000, "Snow");
        weatherCode.put(5001, "Flurries");
        weatherCode.put(5100, "Light Snow");
        weatherCode.put(5101, "Heavy Snow");
        weatherCode.put(6000, "Freezing Drizzle");
        weatherCode.put(6001, "Freezing Rain");
        weatherCode.put(6200, "Light Freezing Rain");
        weatherCode.put(6201, "Heavy Freezing Rainw");
        weatherCode.put(7000, "Ice Pellets");
        weatherCode.put(7101, "Heavy Ice Pellets");
        weatherCode.put(7102, "Light Ice Pellets");


        try {

            String dataFromActivity = getArguments().getString("currentData");
            Log.d("TRYTODAY", "------------------"+dataFromActivity);
            JSONObject currentData = new JSONObject(dataFromActivity);

            TextView mtextView;
            mtextView = (TextView) view.findViewById(R.id.Windspeed);
            mtextView.setText(Double.toString(currentData.getDouble("windSpeed"))+"mph");
            mtextView = (TextView) view.findViewById(R.id.Pressure);
            mtextView.setText(Double.toString(currentData.getDouble("pressureSeaLevel"))+"inHg");
            mtextView = (TextView) view.findViewById(R.id.Precipitation);
            mtextView.setText(Integer.toString(currentData.getInt("precipitationProbability"))+".00%");
            mtextView = (TextView) view.findViewById(R.id.Temperature);
            mtextView.setText(Integer.toString(currentData.getInt("temperature"))+"°F");
            mtextView = (TextView) view.findViewById(R.id.Humidity);
            mtextView.setText(Integer.toString(currentData.getInt("humidity"))+"%");
            mtextView = (TextView) view.findViewById(R.id.Visibility);
            mtextView.setText(Double.toString(currentData.getDouble("visibility"))+"mi");
            mtextView = (TextView) view.findViewById(R.id.CloudCover);
            mtextView.setText(Integer.toString(currentData.getInt("cloudCover"))+"%");
            mtextView = (TextView) view.findViewById(R.id.Ozone);
            mtextView.setText(Double.toString(currentData.getDouble("uvIndex"))+"0");
            mtextView = (TextView) view.findViewById(R.id.todayWeather);
            mtextView.setText(weatherCode.get(currentData.getInt("weatherCode")));



            ImageView imageView;
            imageView = (ImageView)  view.findViewById(R.id.todayIcon);
            Integer code = currentData.getInt("weatherCode");
            setImage(code, imageView );

        } catch (JSONException e) {
            Log.d("TodayFragmentDataReceive", "Could not receive");
            e.printStackTrace();
        }

        return view;

    }

    public void setImage(int iconId, ImageView imageView ){

        //Log.d("SETIMAGE","---------------hello-------------"+id+imageView);
        if (iconId == 1000) {
            imageView.setImageResource(R.drawable.ic_clear_day);
        } else if (iconId == 1001) {
            imageView.setImageResource(R.drawable.ic_cloudy);
        } else if (iconId == 1001) {
            imageView.setImageResource(R.drawable.ic_mostly_cloudy);
        } else if (iconId == 1100) {
            imageView.setImageResource(R.drawable.ic_mostly_clear_day);
        } else if (iconId == 1101) {
            imageView.setImageResource(R.drawable.ic_partly_cloudy_day);
        } else if (iconId == 1102) {
            imageView.setImageResource(R.drawable.ic_mostly_cloudy);
        } else if (iconId == 2000) {
            imageView.setImageResource(R.drawable.ic_fog);
        } else if (iconId == 2100) {
            imageView.setImageResource(R.drawable.ic_fog_light);
        } else if (iconId == 3000 || iconId == 3001 || iconId == 3002) {
            imageView.setImageResource(R.drawable.weather_windy);
        } else if (iconId == 4000 || iconId == 4001 || iconId == 4200 || iconId == 4201) {
            imageView.setImageResource(R.drawable.ic_rain);
        } else if (iconId == 5000 || iconId == 5100 || iconId == 5101) {
            imageView.setImageResource(R.drawable.ic_snow_light);
        } else if (iconId == 5001) {
            imageView.setImageResource(R.drawable.ic_flurries);
        } else if (iconId == 6000 || iconId == 6001 || iconId == 6200 || iconId == 6201) {
            imageView.setImageResource(R.drawable.ic_freezing_rain);
        } else if (iconId == 7000 || iconId == 7101 || iconId == 7102) {
            imageView.setImageResource(R.drawable.ic_ice_pellets_light);
        } else {
            imageView.setImageResource(R.drawable.weather_sunny);
        }
    }

}