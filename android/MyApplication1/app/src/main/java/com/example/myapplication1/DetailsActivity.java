package com.example.myapplication1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication1.ui.main.SectionsPagerAdapter;
import com.example.myapplication1.databinding.ActivityDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    TextView textView;
    String locationName;

    Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), bundle);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.calendar_today);
        tabs.getTabAt(1).setIcon(R.drawable.trending_up);
        tabs.getTabAt(2).setIcon(R.drawable.thermometer);

        Intent intent = getIntent();
        String currentDataS = intent.getStringExtra("currentData");
        String dailyDataS = intent.getStringExtra("dailyData");
        locationName = intent.getStringExtra("locationName");
        Log.d("currentDataInDetails", currentDataS);
        Log.d("dailyDataInDetails", dailyDataS);
        Log.d("locationInDetails", locationName);

        getSupportActionBar().setTitle(locationName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle.putString("currentData", currentDataS);
        bundle.putString("dailyData", dailyDataS);


//        textView = (TextView) findViewById(R.id.cityNameDetails);
//        Log.d("DetailsError", String.valueOf(textView));
//        textView.setText("hi");
        try {
            JSONObject currentData = new JSONObject(currentDataS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray dailyData = new JSONArray(dailyDataS);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    // importing menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);

        return true;

    }

    //implementing menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionTwitter:
                // User chose the "Settings" item, show the app settings UI...
                String a = "Check out "+ locationName +"'s weather! It is 58°F";
                String url = "https://twitter.com/intent/tweet?text="+a+"&hashtags=CSCI571WeatherForecast";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    // Chrome is probably not installed
                    // Try with the default browser
                    i.setPackage(null);
                    startActivity(i);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void setTodayTab(JSONObject currentData){
        textView = (TextView) findViewById(R.id.Windspeed);
        Log.d("DetailsError", String.valueOf(textView));

        //String windSpeed = Integer.toBinaryString(currentData.getInt("windSpeed"));
        textView.setText("ersw");

    }

    public void setWeeklyTab(JSONArray dailyData){

    }

    public void setHighchartsTab(JSONObject currentData){

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