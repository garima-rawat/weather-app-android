package com.example.myapplication1;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication1.databinding.ActivityFavouritesBinding;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Favourites extends AppCompatActivity {


    public RequestQueue queue;


    ProgressBar progressBar_cyclic;
    TextView mtextView;
    CardView cardView;
    ArrayList<String> suggestions;
    JSONArray predictions;
    ArrayAdapter<String> newsAdapter;
    SearchView.SearchAutoComplete searchAuto;

    String addr;
    JSONObject currentData;
    JSONArray dailyData;

    String locationName;

    SharedPreferences sharedpreferences;
    Switch FavoriteAdapter;
    private ProgressBar spinner;
    private TextView fetch;



    Integer counter,Num_frags;

    ArrayList<JSONObject> allData;

    Set<String> retrieveVal;
    Set<String> StoredData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Log.d("FAV ACTIVITY", "*************** BACK ***********");
        queue = Volley.newRequestQueue(this);

        Log.d("IN FAVOURITES", "I am re routed to favourite ......");

        progressBar_cyclic = (ProgressBar)findViewById(R.id.progressBar_cyclic);
        progressBar_cyclic.setVisibility(View.VISIBLE);
        mtextView = findViewById(R.id.fetching);
        mtextView.setVisibility(View.VISIBLE);


        getIPInfo();
        getSupportActionBar().setTitle("WeatherApp");


        sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        retrieveVal = sharedpreferences.getStringSet("key3",null);

        counter = 0;
        Num_frags = 1;

        if (retrieveVal != null){
            Num_frags = retrieveVal.size()+1;
            Log.d("frag",String.valueOf(Num_frags));
        }
        StoredData = new HashSet<String>();
        allData = new ArrayList<JSONObject>();

    }


    // ipinfo fetches location data
    private void getIPInfo(){
        Log.d("IPInfo ", "I am in IPinfo ......");
        String url = "https://ipinfo.io/json?token=179e7754a836f1";
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
                            //String country = response.getString("country");
                            locationName = city+", "+state;
                            Log.d("IPInfo Try", "I am in IPinfo try ......"+lat+lng+locationName);
                            callTomorrowio(lat,lng,locationName);
                        } catch (JSONException e) {
                            Log.d("Error:", "I am in catch block of IP info call ......");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error:", "I am in Error block of IP info call ......");
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    private void callTomorrowio(double lat,double lng, String locationName) {

        String weatherurl = "https://weatherapp-102021.wn.r.appspot.com/card/"+lat+"/"+lng;
        //weatherurl = "https://weatherapp-102021.wn.r.appspot.com/card/34.003/-118.2863";

        Log.d("TomorrowIo ", "I am in TomorrowIo ......"+weatherurl);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, weatherurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TomorrowIo onResponse", "I am in TomorrowIo ......");
                        try {
                            Log.d("TomorrowIo Try", "I am in TomorrowIo ......");
                            // Extracted Main objects
                            dailyData = response.getJSONObject("daily").getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals");
                            currentData = response.getJSONObject("current").getJSONObject("data").getJSONArray("timelines").getJSONObject(0).getJSONArray("intervals").getJSONObject(0).getJSONObject("values");
                            Log.d("TomorrowIo Data", "I am in TomorrowIo ......"+currentData);

                            JSONObject fav = new JSONObject();
                            try{
                                fav.put("addr",locationName);
                                fav.put("currentData",currentData);
                                fav.put("dailyData", dailyData);
                                allData.add(fav);
                                setupAdapter();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            addFav();

                        } catch (JSONException e) {

                            Log.d("TomorrowIo Catch:", "I am in catch block of tomorrow io call ......");
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TomorrowIo Error", "I am in TomorrowIo Error.....");
                error.printStackTrace();
            }
        });
        queue.add(request);
    }


    public void addFav(){

        Log.d("IN add fav to list", "addFav: ");
        Log.d("IN add fav to list, current list", ""+allData);
        Log.d("IN add fav to list, retreived data", ""+retrieveVal);

        if (retrieveVal != null){
            for(String cityName: retrieveVal){
                StoredData.add(cityName);
                int n = cityName.length();
                String names = cityName.substring(9,n-2);
                Log.d("Stored data in fav", "addFav: "+names);
                JSONObject fav = new JSONObject();
                try{

                    String alpha = sharedpreferences.getString(names, null);
                    Log.d("TAG", cityName + " addFav: "+alpha);
                    JSONObject beta = new JSONObject(alpha);

                    fav.put("addr", names);
                    fav.put("currentData",alpha);
                    fav.put("dailyData", dailyData);
                    allData.add(fav);
                    setupAdapter();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }



    }



    public void setupAdapter() {

        counter+=1;
        Log.d("counter",String.valueOf(counter));
        if (counter == (Num_frags )) {

            Log.d("IN add fav to list, leaving current list", ""+allData);

            progressBar_cyclic = (ProgressBar)findViewById(R.id.progressBar_cyclic);
            progressBar_cyclic.setVisibility(View.GONE);
            mtextView = findViewById(R.id.fetching);
            mtextView.setVisibility(View.GONE);

            FavoriteAdapter = new Switch(this.getSupportFragmentManager(), this, allData, currentData, dailyData);


            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(FavoriteAdapter);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
        }
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

                Intent intent = new Intent(Favourites.this, SearchableActivity.class);
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



    public void showProgressBar(){

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
}