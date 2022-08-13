package com.example.myapplication1;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class Switch extends FragmentPagerAdapter {
    protected Context mContext;
    public ArrayList<JSONObject> stored_location;
    private long baseId = 0;

    String addr;
    JSONObject currentData;
    JSONArray dailyData;
    JSONObject cityData;
    public ArrayList<JSONObject> allData;

    public Switch(FragmentManager fm, Context context, ArrayList<JSONObject> input, JSONObject beta, JSONArray alpha) {
        super(fm);
        mContext = context;
        allData = input;
        dailyData = alpha;
        currentData = beta;


    }


    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        cityData = new JSONObject();
        try {

            addr = allData.get(i).getString("addr");

            Log.d("In switch city", addr);

            //currentData = allData.get(i).getJSONObject("currentData");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (i == 0){
            fragment = currentFragment.newInstance(addr, currentData.toString(), dailyData.toString());
        }
        else{
            fragment = favCitiesFragment.newInstance(addr, currentData.toString(), dailyData.toString(), String.valueOf(i));
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return allData.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        // give an ID different from position when position has been changed
        return baseId + position;
    }
    public void notifyChangeInPosition(int n) {
        // shift the ID returned by getItemId outside the range of all previous fragments
        Log.d("#####deleting fragment",String.valueOf(n));
        stored_location.remove(n);
//        baseId += getCount() + n;
        notifyDataSetChanged();
    }




}