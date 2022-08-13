package com.example.myapplication1.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication1.R;
import com.example.myapplication1.TodayFragment;
import com.example.myapplication1.WeeklyFragment;
import com.example.myapplication1.highchartsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;
    public Bundle bundle;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Bundle bundle1) {
        super(fm);
        mContext = context;
        bundle = bundle1;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        Fragment fragment = new TodayFragment();
        fragment.setArguments(bundle);


        switch(position){
            case 0:
                fragment = new TodayFragment();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new WeeklyFragment();
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment = new highchartsFragment();
                fragment.setArguments(bundle);
                break;
        }


        return fragment;


//        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}