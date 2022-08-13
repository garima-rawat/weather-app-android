package com.example.myapplication1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.HIGradient;
import com.highsoft.highcharts.common.HIStop;
import com.highsoft.highcharts.core.*;

import com.highsoft.highcharts.common.hichartsclasses.*;
import com.highsoft.highcharts.core.HIChartView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;

    public WeeklyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeeklyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyFragment newInstance(String param1, String param2) {
        WeeklyFragment fragment = new WeeklyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_weekly);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_weekly, container, false);

        try {
            String dataFromActivity = getArguments().getString("dailyData");
            Log.d("TRYWEEKLY", "------------------"+dataFromActivity);
            JSONArray dailyData = new JSONArray(dataFromActivity);

            HIChartView chartView = view.findViewById(R.id.hc);

            HIOptions options = new HIOptions();

            HIChart chart = new HIChart();
            chart.setType("arearange");
            chart.setZoomType("x");

            options.setChart(chart);

            HITitle title = new HITitle();
            title.setText("Temperature variation by day");
            options.setTitle(title);


            HIXAxis xaxis = new HIXAxis();
            xaxis.setType("datetime");
            ArrayList xAxis = new ArrayList<HIXAxis>(){{add(xaxis);}};
            options.setXAxis(xAxis);

            HIYAxis yaxis = new HIYAxis();
            yaxis.setTitle(new HITitle());
            options.setYAxis(new ArrayList<HIYAxis>(){{add(yaxis);}});

            HITooltip tooltip = new HITooltip();
            tooltip.setShadow(true);
            tooltip.setValueSuffix("°F");
            options.setTooltip(tooltip);

            HILegend legend = new HILegend();
            legend.setEnabled(false);
            options.setLegend(legend);

            HIArearange series = new HIArearange();
            series.setName("Temperatures");

            HISeries hiseries = new HISeries();
            HIPlotOptions plotOptions = new HIPlotOptions();
            HIMarker hiMarker = new HIMarker();
            options.setPlotOptions(plotOptions);
            plotOptions.setSeries(hiseries);
            hiseries.setMarker(hiMarker);
            hiMarker.setFillColor(HIColor.initWithName("grey"));

            SimpleDateFormat axisDate = new SimpleDateFormat("yyyy-MM-dd");

            Object[][] seriesData = new Object[][]{

                    {axisDate.parse(dailyData.getJSONObject(0).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(0).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(0).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(1).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(1).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(1).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(2).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(2).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(2).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(3).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(3).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(3).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(4).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(4).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(4).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(5).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(5).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(5).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(6).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(6).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(6).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(7).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(7).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(7).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(8).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(8).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(8).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(9).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(9).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(9).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(10).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(10).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(10).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(11).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(11).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(11).getJSONObject("values").getInt("temperatureMin")} ,
                    {axisDate.parse(dailyData.getJSONObject(12).getString("startTime").substring(0,10)).getTime(), dailyData.getJSONObject(12).getJSONObject("values").getInt("temperatureMax"), dailyData.getJSONObject(12).getJSONObject("values").getInt("temperatureMin")}

            };


            HIStop grad1 = new HIStop(0,HIColor.initWithRGBA(201, 131, 60, 0.5));
            HIStop grad2 = new HIStop(1,HIColor.initWithRGBA(10, 164, 247, 0.4));
            LinkedList<HIStop> list = new LinkedList<>();
            list.add(grad1);
            list.add(grad2);
            HIGradient gradient = new HIGradient(0, (float) 0.5,0, (float) 1);
            series.setFillColor(HIColor.initWithLinearGradient(gradient,list));


            //series.setFillColor(HIColor.initWithRGBA(50, 192, 242, 0.4));
            ArrayList seriesList = new ArrayList<>(Arrays.asList(seriesData));
            series.setData(seriesList);

            ArrayList setSeriesList = new ArrayList<>(Arrays.asList(series));
            options.setSeries(setSeriesList);

            chartView.setOptions(options);

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }


        return view;

    }
}

