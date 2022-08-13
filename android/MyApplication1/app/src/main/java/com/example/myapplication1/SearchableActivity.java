package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class SearchableActivity extends AppCompatActivity {

    public RequestQueue queue;

    // XML Variables
    TextView mtextView;
    ImageView imageView;
    CardView cardView;
    ProgressBar progressBar_cyclic;

    //Data variables
    String addr;
    JSONObject currentData;
    JSONArray dailyData;
    HashMap<Integer, String> weatherCode = new HashMap<Integer, String>();

    //Button variables
    FloatingActionButton saveButton;
    Boolean isFav = false;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);


        showProgressBar();
        putWeatherCode();

        Intent intent = getIntent();
        addr = intent.getStringExtra("locationName");

        queue = Volley.newRequestQueue(this);



        getGeolocation(addr);

        getSupportActionBar().setTitle(addr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardView cardView = findViewById(R.id.card1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchableActivity.this,DetailsActivity.class);
                intent.putExtra("currentData", currentData.toString());
                intent.putExtra("dailyData", dailyData.toString());
                intent.putExtra("locationName", addr);
                startActivity(intent);
            }
        });



        saveButton = (FloatingActionButton) findViewById(R.id.fab);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


                isFav = !isFav;
                imageView = findViewById(R.id.fab);
                if (isFav==true){
                    imageView.setImageResource(R.drawable.map_marker_minus);
                    Toast.makeText(getApplicationContext(),addr +" was added to favorites",Toast.LENGTH_SHORT).show();
                    //save data
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    HashSet<String> StoredData = new HashSet<String>();
                    JSONObject store_val = new JSONObject();

                    try{
                        store_val.put("city",addr);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Set<String> retrieveVal = sharedPreferences.getStringSet("key3",null);
                    if (retrieveVal != null){
                        for(String s: retrieveVal){
                            StoredData.add(s);
                        }
                    }

                    StoredData.add(store_val.toString());
                    editor.putStringSet("key3",StoredData);
                    editor.putString(addr, currentData.toString());
                    editor.commit();

                    Log.d("Adding in SEARCH *****", "onClick: "+ editor);
                    Log.d("Adding in SEARCH again *****", "onClick: "+ StoredData);


//                    String cities = sharedPreferences.getString("FavCities", null);
//                    if (cities==null){
//                        editor.putString("FavCities", addr );
//                        editor.putString(addr, currentData.toString());
//                    }
//                    else{
//                        editor.putString("FavCities", cities+"-"+addr );
//                        editor.putString(addr, currentData.toString());
//                    }
//                    editor.apply();
                }
                else{
                    imageView.setImageResource(R.drawable.map_marker_plus);
                    Toast.makeText(getApplicationContext(),addr +" was removed from favorites",Toast.LENGTH_SHORT).show();
                    //remove data
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("FavCities", null );
//                    editor.apply();
                }


            }
        });
    }



    private void getGeolocation(String addr){

        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+addr+"&key=AIzaSyBDPQ5rNobPBGZp-C-f31NK9nRLmqgQfew";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Geolocation Output:",response.toString());
                        try {
                            //String[] location = response.getString("loc").split(",");
                            double lat = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");//   Double.parseDouble(location[0]);
                            double lng = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");



                            callTomorrowio(lat,lng,addr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    private void callTomorrowio(double lat,double lng, String locationName) {

        String weatherurl = "https://weatherapp-102021.wn.r.appspot.com/card/"+lat+"/"+lng;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, weatherurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // Extracted Main objects
                            dailyData = response.getJSONObject("daily").getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals");
                            currentData = response.getJSONObject("current").getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals").getJSONObject(0).getJSONObject("values");


                            //Dynamically Allocating current data to card1 and 2
                            String temperature = Integer.toString(currentData.getInt("temperature"));
                            Integer code = currentData.getInt("weatherCode");

                            String pressure = Double.toString(currentData.getDouble("pressureSeaLevel"));
                            String visibility = Double.toString(currentData.getDouble("visibility"));
                            String windSpeed = Integer.toString(currentData.getInt("windSpeed"));
                            String humidity = Integer.toString(currentData.getInt("humidity"));


                            mtextView = (TextView) findViewById(R.id.card1Temperature);
                            mtextView.setText(temperature + "°F");
                            mtextView = (TextView) findViewById(R.id.card1Weather);
                            mtextView.setText(weatherCode.get(code));
                            mtextView = (TextView) findViewById(R.id.City);
                            mtextView.setText(locationName);
                            imageView = findViewById(R.id.card1WeatherIcon);
                            setImage(code, imageView );

                            mtextView = (TextView) findViewById(R.id.card2Pressure);
                            mtextView.setText(pressure + "inHg");
                            mtextView = (TextView) findViewById(R.id.card2Windspeed);
                            mtextView.setText(windSpeed + "mph");
                            mtextView = (TextView) findViewById(R.id.card2Visibility);
                            mtextView.setText(visibility + "mi");
                            mtextView = (TextView) findViewById(R.id.card2Humidity);
                            mtextView.setText(humidity + "%");


                            //Dynamically Allocating Daily data to card3
                            String[] dailyTempMin = new String[10];
                            String[] dailyTempMax = new String[10];
                            String[] dailyDate = new String[10];
                            Integer[] dailyIcon = new Integer[10];

                            //String tempMin = Integer.toString(dailyData.getJSONObject(0).getJSONObject("values").getInt("temperatureMin"));
                            //String tempMax = Integer.toString(dailyData.getJSONObject(0).getJSONObject("values").getInt("temperatureMax"));

                            for (int i = 0; i < 8; i++) {
                                dailyDate[i] = dailyData.getJSONObject(i).getString("startTime");
                                dailyTempMin[i] = Integer.toString(dailyData.getJSONObject(i).getJSONObject("values").getInt("temperatureMin"));
                                dailyTempMax[i] = Integer.toString(dailyData.getJSONObject(i).getJSONObject("values").getInt("temperatureMax"));
                                dailyIcon[i] = dailyData.getJSONObject(i).getJSONObject("values").getInt("weatherCode");
                            }

                            for (int i = 0; i < 8; i++) {

                                //Log.d("Arrays:", "hi---"+i+dailyTempMax[i]+" "+dailyTempMin[i]+dailyDate[i]);
                                int id;

                                id = getResources().getIdentifier("table" + (i + 1) + 1, "id", getPackageName());
                                mtextView = findViewById(id);
                                mtextView.setText(dailyDate[i].substring(0,10));

                                id = getResources().getIdentifier("table" + (i + 1) + 2, "id", getPackageName());
                                //Log.d("CHECKME","----------------------------"+id);

                                imageView = findViewById(id);
                                setImage(dailyIcon[i], imageView );


                                id = getResources().getIdentifier("table" + (i + 1) + 3, "id", getPackageName());
                                mtextView = findViewById(id);
                                mtextView.setText(String.valueOf(dailyTempMin[i]));
                                id = getResources().getIdentifier("table" + (i + 1) + 4, "id", getPackageName());
                                mtextView = findViewById(id);
                                mtextView.setText(String.valueOf(dailyTempMax[i]));

                                removeProgressBar();
                            }



                        } catch (JSONException e) {
                            //callTomorrowio(lat,lng);
                            Log.d("Error:", "I am in catch block of tomorrow io call ......");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }


    public void showProgressBar(){
        progressBar_cyclic = (ProgressBar)findViewById(R.id.progressBar_cyclic);
        progressBar_cyclic.setVisibility(View.VISIBLE);
        mtextView = findViewById(R.id.fetching);
        mtextView.setVisibility(View.VISIBLE);

//        cardView = findViewById(R.id.cardProgress);
//        cardView.setVisibility(View.VISIBLE);

        cardView = findViewById(R.id.card1);
        cardView.setVisibility(View.GONE);
        cardView = findViewById(R.id.card2);
        cardView.setVisibility(View.GONE);
        cardView = findViewById(R.id.card3);
        cardView.setVisibility(View.GONE);
        mtextView = findViewById(R.id.randomtext);
        mtextView.setVisibility(View.GONE);
    }

    public void removeProgressBar(){
        progressBar_cyclic = (ProgressBar)findViewById(R.id.progressBar_cyclic);
        progressBar_cyclic.setVisibility(View.GONE);
        mtextView = findViewById(R.id.fetching);
        mtextView.setVisibility(View.GONE);


        cardView = findViewById(R.id.card1);
        cardView.setVisibility(View.VISIBLE);
        cardView = findViewById(R.id.card2);
        cardView.setVisibility(View.VISIBLE);
        cardView = findViewById(R.id.card3);
        cardView.setVisibility(View.VISIBLE);
        mtextView = findViewById(R.id.randomtext);
        mtextView.setVisibility(View.VISIBLE);

    }

    public void putWeatherCode(){

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