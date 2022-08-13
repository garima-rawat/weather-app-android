package com.example.myapplication1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.view.menu.MenuWrapperICS;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.AdapterView;
import android.app.SearchManager;
import androidx.appcompat.widget.SearchView;



public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    public RequestQueue queue;


    TextView mtextView;
    ImageView imageView;
    CardView cardView;
    ProgressBar progressBar_cyclic;

    String locationName;
    JSONObject currentData;
    JSONArray dailyData;

    HashMap<Integer, String> weatherCode = new HashMap<Integer, String>();



    ArrayList<String> suggestions;
    JSONArray predictions;
    ArrayAdapter<String> newsAdapter;
    SearchView.SearchAutoComplete searchAuto;
    String addr;


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        favouriteImplementation();
        Log.d("MAIN ACTIVITY", "*************** BACK ***********");

        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        showProgressBar();
        putWeatherCode();

//        getIPInfo();
//        getSupportActionBar().setTitle("WeatherApp");

        //Adding click functionality to Card 1 to go to details
        cardView = findViewById(R.id.card1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("currentData", currentData.toString());
                intent.putExtra("dailyData", dailyData.toString());
                intent.putExtra("locationName", locationName);
                startActivity(intent);
            }


        });



    }



    // importing menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("Search...");
        searchAuto = searchView.findViewById(androidx.appcompat.R.id.search_src_text);


        searchAuto.setBackgroundColor(getColor(R.color.black));
        searchAuto.setTextColor(getColor(R.color.white));
        searchAuto.setDropDownBackgroundResource(R.color.white);

        searchAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                addr=(String)adapterView.getItemAtPosition(itemIndex);
                searchAuto.setText("" + addr);
                Log.d("in item click", "$$$$$$$$$$$$$$$$$$$$$onItemClick: ");


            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("onQueryTextSubmit: ",query);
                suggestions = new ArrayList<String>();

                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                intent.putExtra("locationName", addr);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("onQueryTextChange: ",newText);

                    autocomplete(newText);

                //Log.d("val","################"+newText);
                return false;
            }
        });

//        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return false;
//            }
//        };
//        menu.findItem(R.id.actionSearch).setOnActionExpandListener(onActionExpandListener);
//        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
//        searchView.setQueryHint("Search ...");
        return true;

    }

    public void autocomplete(String prefix){

        //Log.d("a","######################entered autocomplete"+newText);


        String autoUrl = "https://weatherapp-102021.wn.r.appspot.com/autoComplete/"+prefix;

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, autoUrl, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    suggestions = new ArrayList<String>();

                    try {
                        predictions = response.getJSONArray("predictions");

                        for(int i=0; i<predictions.length(); i++){
                            suggestions.add(predictions.getJSONObject(i).getString("description"));
                        }

                        newsAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, suggestions);
                        //Log.d("hh",newsAdapter.toString());
                        searchAuto.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Log.d("catchException","((((((((()))))))))");
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("errorResponse","((((((((()))))))))");
                error.printStackTrace();
            }
        });
        queue.add(request);
    }



    //implementing menu
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.actionSearch:
//                // Autocomplete Search
//                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
//                intent.putExtra("locationName", "Seattle, Washington");
//                startActivity(intent);
//
//                return true;
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

    // ipinfo fetches location data
    private void getIPInfo(){
        String url = "https://ipinfo.io/json?token=addtokenhere";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("IPInfo Output:",response.toString());
                        try {
                            String[] location = response.getString("loc").split(",");
                            double lat = Double.parseDouble(location[0]);
                            double lng = Double.parseDouble(location[1]);
                            String city = response.getString("city");
                            String state = response.getString("region");
                            String country = response.getString("country");
                            locationName = city+", "+state;

                            callTomorrowio(lat,lng,locationName);
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

        Log.d("TomorrowIo :","tomorrrow Enter");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, weatherurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("TomorrowIo try:","tomorrrow Enter");
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

                                id = getResources().getIdentifier("table" + (i + 1) + 1, "id", getPackageName());
                                mtextView = findViewById(id);
                                //Log.d("Arrays:", "daily---"+i+" "+dailyDate[i]);
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
                            Log.d("Catch:", "I am in catch block of tomorrow io call ......");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error:", "I am in error block of tomorrow io call ......");
                error.printStackTrace();
            }
        });
        queue.add(request);
    }



    public void favouriteImplementation(){


        Intent intent = new Intent(MainActivity.this, Favourites.class);
        startActivity(intent);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String allCities = sharedPreferences.getString("FavCities", null);


        if (allCities==null){
            //do something
            Log.d("fav cities", "%%%%%%% IS NULL");
        }
        else{
            //do something
            String[] cities = allCities.split("-");
            for(int i=0; i<cities.length; i++){
                Log.d("fav cities", i + " " + cities[i]+" DATA " + sharedPreferences.getString(cities[i], null));

            }
        }
    }
    public void showProgressBar(){
        progressBar_cyclic = (ProgressBar)findViewById(R.id.progressBar_cyclic);
        progressBar_cyclic.setVisibility(View.VISIBLE);
        mtextView = findViewById(R.id.fetching);
        mtextView.setVisibility(View.VISIBLE);

        cardView = findViewById(R.id.card1);
        cardView.setVisibility(View.GONE);
        cardView = findViewById(R.id.card2);
        cardView.setVisibility(View.GONE);
        cardView = findViewById(R.id.card3);
        cardView.setVisibility(View.GONE);

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

















//        mtextView = (TextView) findViewById(R.id.Windspeed);
//
//        mtextView.setText("Dynamically setting this");

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        setSupportActionBar(binding.toolbar);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }








































//
//import androidx.appcompat.app.AppCompatActivity;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.View;
//        import android.widget.ProgressBar;
//        import android.widget.TextView;
//
//        import com.android.volley.Request;
//        import com.android.volley.RequestQueue;
//        import com.android.volley.Response;
//        import com.android.volley.VolleyError;
//        import com.android.volley.toolbox.JsonObjectRequest;
//        import com.android.volley.toolbox.Volley;
//
//        import org.json.JSONArray;
//        import org.json.JSONException;
//        import org.json.JSONObject;
//
//public class MainActivity extends AppCompatActivity {
//    private ProgressBar progressBarStart;
//    private TextView initText;
//    protected RequestQueue queue;
//    public String city;
//    public String country;
//    public String state;
//    public double lat;
//    public double lng;
//    JSONArray weatherData;
//    JSONObject currentData;
//    JSONArray daysData;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        progressBarStart = findViewById(R.id.progressBarStart);
//        initText = findViewById(R.id.initText);
//        progressBarStart.setVisibility(View.VISIBLE);
//        initText.setVisibility(View.VISIBLE);
//        Log.d("Test","Test");
//        queue = Volley.newRequestQueue(this);
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        getIPInfo();
//        /*
//        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
//        if(currentData!=null)
//            intent.putExtra("currentData",currentData.toString());
//        else
//            intent.putExtra("currentData","Some error");
//        if(daysData!=null)
//            intent.putExtra("daysData",daysData.toString());
//        else
//            intent.putExtra("daysData","Some error");
//        startActivity(intent);*/
//    }
//
//    private void getIPInfo(){
//        String url = "https://ipinfo.io/json?token=878e96704f64a8";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("IPInfo Output:",response.toString());
//                        try {
//                            String[] location = response.getString("loc").split(",");
//                            double lat = Double.parseDouble(location[0]);
//                            double lng = Double.parseDouble(location[1]);
//                            city = response.getString("city");
//                            state = response.getString("region");
//                            country = response.getString("country");
//
//                            getWeatherInfo(lat,lng);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        queue.add(request);
//    }
//    private void getWeatherInfo(double lat,double lng){
//        String weatherurl="https://weatherapp-nodejs-backend.ue.r.appspot.com/weather?";
//        //String weatherurl = "http://localhost:3000/weather";
//        //String weatherurl = "http://10.26.164.6:3000/weather?";
//        String location = "latitude="+String.valueOf(lat)+"&longitude="+String.valueOf(lng);
//        weatherurl = weatherurl+location;
//        JSONObject params = new JSONObject();
//        try{
//            params.put("latitude",lat);
//            params.put("longitude",lng);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, weatherurl, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONObject data = response.getJSONObject("data");
//                            weatherData = data.getJSONArray("timelines");
//                            currentData = weatherData.getJSONObject(0).getJSONArray("intervals").getJSONObject(0);
//                            daysData = weatherData.getJSONObject(1).getJSONArray("intervals");
//
//                            Log.d("CurrentData",currentData.toString());
//                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
//                            if(currentData!=null)
//                                intent.putExtra("currentData",currentData.toString());
//                            else
//                                intent.putExtra("currentData","Some error");
//                            if(daysData!=null)
//                                intent.putExtra("daysData",daysData.toString());
//                            else
//                                intent.putExtra("daysData","Some error");
//                            startActivity(intent);
//
//                        } catch (JSONException e) {
//                            getWeatherInfo(lat,lng);
//                            Log.d("Check:","In catch block......");
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        queue.add(request);
//        progressBarStart.setVisibility(View.GONE);
//        initText.setVisibility(View.GONE);
//    }
//}