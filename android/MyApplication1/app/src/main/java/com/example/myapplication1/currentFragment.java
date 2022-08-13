package com.example.myapplication1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link currentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class currentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    View view;
    TextView mtextView;
    CardView cardView;
    ImageView imageView;

    String locationName;
    JSONObject currentData;
    JSONArray dailyData;


    HashMap<Integer, String> weatherCode = new HashMap<Integer, String>();

    public currentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment currentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static currentFragment newInstance(String param1, String param2, String param3) {
        currentFragment fragment = new currentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);

            locationName = mParam1;
            try {
                currentData = new JSONObject(mParam2);
                dailyData = new JSONArray(mParam3);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_current, container, false);

        cardView = view.findViewById(R.id.card1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("currentData", currentData.toString());
                intent.putExtra("dailyData", dailyData.toString());
                intent.putExtra("locationName", locationName);
                startActivity(intent);
            }


        });
        try {

            // Extracted Main objects

            //Dynamically Allocating current data to card1 and 2
            String temperature = Integer.toString(currentData.getInt("temperature"));
            Integer code = currentData.getInt("weatherCode");

            String pressure = Double.toString(currentData.getDouble("pressureSeaLevel"));
            String visibility = Double.toString(currentData.getDouble("visibility"));
            String windSpeed = Integer.toString(currentData.getInt("windSpeed"));
            String humidity = Integer.toString(currentData.getInt("humidity"));


            mtextView = (TextView) view.findViewById(R.id.card1Temperature);
            mtextView.setText(temperature + "°F");
            mtextView = (TextView) view.findViewById(R.id.card1Weather);
            mtextView.setText(weatherCode.get(code));
            mtextView = (TextView) view.findViewById(R.id.City);
            mtextView.setText(locationName);
            imageView = view.findViewById(R.id.card1WeatherIcon);
            setImage(code, imageView);

            mtextView = (TextView) view.findViewById(R.id.card2Pressure);
            mtextView.setText(pressure + "inHg");
            mtextView = (TextView) view.findViewById(R.id.card2Windspeed);
            mtextView.setText(windSpeed + "mph");
            mtextView = (TextView) view.findViewById(R.id.card2Visibility);
            mtextView.setText(visibility + "mi");
            mtextView = (TextView) view.findViewById(R.id.card2Humidity);
            mtextView.setText(humidity + "%");


            //Dynamically Allocating Daily data to card3
            String[] dailyTempMin = new String[12];
            String[] dailyTempMax = new String[12];
            String[] dailyDate = new String[12];
            Integer[] dailyIcon = new Integer[12];

            //String tempMin = Integer.toString(dailyData.getJSONObject(0).getJSONObject("values").getInt("temperatureMin"));
            //String tempMax = Integer.toString(dailyData.getJSONObject(0).getJSONObject("values").getInt("temperatureMax"));

            for (int i = 0; i < 12; i++) {
                dailyDate[i] = dailyData.getJSONObject(i).getString("startTime");
                dailyTempMin[i] = Integer.toString(dailyData.getJSONObject(i).getJSONObject("values").getInt("temperatureMin"));
                dailyTempMax[i] = Integer.toString(dailyData.getJSONObject(i).getJSONObject("values").getInt("temperatureMax"));
                dailyIcon[i] = dailyData.getJSONObject(i).getJSONObject("values").getInt("weatherCode");

                //Log.d("Arrays:", "Adding---"+i+dailyTempMax[i]+" "+" "+dailyDate[i]);
            }

            for (int i = 0; i < 10; i++) {

                //Log.d("Arrays:", "hi---"+i+dailyTempMax[i]+" "+dailyTempMin[i]+dailyDate[i]);
                int id;

                id = getResources().getIdentifier("table" + (i + 1) + 1, "id", "com.example.myapplication1");
                mtextView = view.findViewById(id);
                //Log.d("Arrays:", "daily---"+i+" "+dailyDate[i]);
                mtextView.setText(dailyDate[i].substring(0, 10));

                id = getResources().getIdentifier("table" + (i + 1) + 2, "id", "com.example.myapplication1");
                //Log.d("CHECKME","----------------------------"+id);

                imageView = view.findViewById(id);
                setImage(dailyIcon[i], imageView);


                id = getResources().getIdentifier("table" + (i + 1) + 3, "id", "com.example.myapplication1");
                mtextView = view.findViewById(id);
                mtextView.setText(String.valueOf(dailyTempMin[i]));
                id = getResources().getIdentifier("table" + (i + 1) + 4, "id", "com.example.myapplication1");
                mtextView = view.findViewById(id);
                mtextView.setText(String.valueOf(dailyTempMax[i]));

            }


        } catch (JSONException e) {
            //callTomorrowio(lat,lng);
            Log.d("Error:", "I am in catch block of tomorrow io call ......");
            e.printStackTrace();
        }

        return view;
    }


    public void putWeatherCode() {

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
        weatherCode.put(6201, "Heavy Freezing Rain");
        weatherCode.put(7000, "Ice Pellets");
        weatherCode.put(7101, "Heavy Ice Pellets");
        weatherCode.put(7102, "Light Ice Pellets");

    }

    public void setImage(int iconId, ImageView imageView) {

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



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}