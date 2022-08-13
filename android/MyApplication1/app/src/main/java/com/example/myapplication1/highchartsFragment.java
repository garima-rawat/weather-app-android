package com.example.myapplication1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.highsoft.highcharts.common.hichartsclasses.*;
import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.core.HIChartView;
import com.highsoft.highcharts.core.HIFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link highchartsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class highchartsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public highchartsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment highchartsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static highchartsFragment newInstance(String param1, String param2) {
        highchartsFragment fragment = new highchartsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_highcharts, container, false);

        String dataFromActivity = getArguments().getString("currentData");
        Log.d("TRYTODAY", "------------------"+dataFromActivity);
        try {
            JSONObject currentData = new JSONObject(dataFromActivity);

            Integer cloudCover = currentData.getInt("cloudCover");
            Integer precipitationProbability = currentData.getInt("precipitationProbability");
            Integer humidity = currentData.getInt("humidity");

            HIChartView chartView = view.findViewById(R.id.hc);

            HIOptions options = new HIOptions();

            HIChart chart = new HIChart();
            chart.setType("solidgauge");
            chart.setEvents(new HIEvents());
            chart.getEvents().setRender(new HIFunction(renderIconsString));
            options.setChart(chart);

            HITitle title = new HITitle();
            title.setText("Stats Summary");
            title.setStyle(new HICSSObject());
            title.getStyle().setFontSize("24px");
            options.setTitle(title);

            HITooltip tooltip = new HITooltip();
            tooltip.setBorderWidth(0);
            tooltip.setBackgroundColor(HIColor.initWithName("none"));
            tooltip.setShadow(false);
            tooltip.setStyle(new HICSSObject());
            tooltip.getStyle().setFontSize("16px");
            tooltip.setPointFormat("{series.name}<br><span style=\"font-size:2em; color: {point.color}; font-weight: bold\">{point.y}%</span>");
            tooltip.setPositioner(
                    new HIFunction(
                            "function (labelWidth) {" +
                                    "   return {" +
                                    "       x: (this.chart.chartWidth - labelWidth) /2," +
                                    "       y: (this.chart.plotHeight / 2) + 15" +
                                    "   };" +
                                    "}"
                    ));
            options.setTooltip(tooltip);

            HIPane pane = new HIPane();
            pane.setStartAngle(0);
            pane.setEndAngle(360);

            HIBackground paneBackground1 = new HIBackground();
            paneBackground1.setOuterRadius("110%");
            paneBackground1.setInnerRadius("91%");
            paneBackground1.setBackgroundColor(HIColor.initWithRGBA(130, 238, 106, 0.35));
            paneBackground1.setBorderWidth(0);

            HIBackground paneBackground2 = new HIBackground();
            paneBackground2.setOuterRadius("90%");
            paneBackground2.setInnerRadius("71%");
            paneBackground2.setBackgroundColor(HIColor.initWithRGBA(106, 165, 231, 0.35));
            paneBackground2.setBorderWidth(0);

            HIBackground paneBackground3 = new HIBackground();
            paneBackground3.setOuterRadius("70%");
            paneBackground3.setInnerRadius("50%");
            paneBackground3.setBackgroundColor(HIColor.initWithRGBA(221, 56, 20, 0.35));
            paneBackground3.setBorderWidth(0);

            pane.setBackground(new ArrayList<>(Arrays.asList(paneBackground1, paneBackground2, paneBackground3)));
            options.setPane(pane);

            HIYAxis yaxis = new HIYAxis();
            yaxis.setMin(0);
            yaxis.setMax(100);
            yaxis.setLineWidth(0);
            yaxis.setTickPositions(new ArrayList<>()); // to remove ticks from the chart
            options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));

            HIPlotOptions plotOptions = new HIPlotOptions();
            plotOptions.setSolidgauge(new HISolidgauge());
            HIDataLabels dataLabels = new HIDataLabels();
            dataLabels.setEnabled(false);
            ArrayList<HIDataLabels> dataLabelList = new ArrayList<>();
            plotOptions.getSolidgauge().setDataLabels(dataLabelList);
            plotOptions.getSolidgauge().getDataLabels();
            plotOptions.getSolidgauge().setLinecap("round");
            plotOptions.getSolidgauge().setStickyTracking(false);
            plotOptions.getSolidgauge().setRounded(true);
            options.setPlotOptions(plotOptions);

            HISolidgauge solidgauge1 = new HISolidgauge();
            solidgauge1.setName("Cloud Cover");
            HIData data1 = new HIData();
            data1.setColor(HIColor.initWithRGB(179, 219, 83));
            data1.setRadius("110%");
            data1.setInnerRadius("91%");
            data1.setY(cloudCover);
            solidgauge1.setData(new ArrayList<>(Collections.singletonList(data1)));

            HISolidgauge solidgauge2 = new HISolidgauge();
            solidgauge2.setName("Precipitation");
            HIData data2 = new HIData();
            data2.setColor(HIColor.initWithRGB(106, 165, 231));
            data2.setRadius("90%");
            data2.setInnerRadius("71%");
            data2.setY(precipitationProbability);
            solidgauge2.setData(new ArrayList<>(Collections.singletonList(data2)));

            HISolidgauge solidgauge3 = new HISolidgauge();
            solidgauge3.setName("Humidity");
            HIData data3 = new HIData();
            data3.setColor(HIColor.initWithRGB(217, 135, 41));
            data3.setRadius("70%");
            data3.setInnerRadius("50%");
            data3.setY(humidity);
            solidgauge3.setData(new ArrayList<>(Collections.singletonList(data3)));

            options.setSeries(new ArrayList<>(Arrays.asList(solidgauge1, solidgauge2, solidgauge3)));

            chartView.setOptions(options);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return view;
    }

    private String renderIconsString = "function renderIcons() {" +
            "                            if(!this.series[0].icon) {" +
            "                               this.series[0].icon = this.renderer.path(['M', -8, 0, 'L', 8, 0, 'M', 0, -8, 'L', 8, 0, 0, 8]).attr({'stroke': '#303030','stroke-linecap': 'round','stroke-linejoin': 'round','stroke-width': 2,'zIndex': 10}).add(this.series[2].group);}this.series[0].icon.translate(this.chartWidth / 2 - 10,this.plotHeight / 2 - this.series[0].points[0].shapeArgs.innerR -(this.series[0].points[0].shapeArgs.r - this.series[0].points[0].shapeArgs.innerR) / 2); if(!this.series[1].icon) {this.series[1].icon = this.renderer.path(['M', -8, 0, 'L', 8, 0, 'M', 0, -8, 'L', 8, 0, 0, 8,'M', 8, -8, 'L', 16, 0, 8, 8]).attr({'stroke': '#ffffff','stroke-linecap': 'round','stroke-linejoin': 'round','stroke-width': 2,'zIndex': 10}).add(this.series[2].group);}this.series[1].icon.translate(this.chartWidth / 2 - 10,this.plotHeight / 2 - this.series[1].points[0].shapeArgs.innerR -(this.series[1].points[0].shapeArgs.r - this.series[1].points[0].shapeArgs.innerR) / 2); if(!this.series[2].icon) {this.series[2].icon = this.renderer.path(['M', 0, 8, 'L', 0, -8, 'M', -8, 0, 'L', 0, -8, 8, 0]).attr({'stroke': '#303030','stroke-linecap': 'round','stroke-linejoin': 'round','stroke-width': 2,'zIndex': 10}).add(this.series[2].group);}this.series[2].icon.translate(this.chartWidth / 2 - 10,this.plotHeight / 2 - this.series[2].points[0].shapeArgs.innerR -(this.series[2].points[0].shapeArgs.r - this.series[2].points[0].shapeArgs.innerR) / 2);}";


}