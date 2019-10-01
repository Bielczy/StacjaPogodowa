package com.example.stacjapogodowa;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {

    CheckBox cbTemperature;
    CheckBox cbHumidity;
    Button btnRefresh;
    DateRange range;
    LineChart lineChart;

    private Object log;

    boolean temperatureChecked;
    boolean humidityChecked;

    public static TemperatureFragment NewInstance(DateRange range){
        TemperatureFragment fr = new TemperatureFragment();
        fr.range = range;
        return fr;
    }

    public TemperatureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.temperature_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);
        cbHumidity = (CheckBox) view.findViewById(R.id.cbHumidity);
        cbTemperature = (CheckBox) view.findViewById(R.id.cbTemperature);
        lineChart = (LineChart) view.findViewById(R.id.chart);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        String startFormated = DateFormatter.toString(range.start);
        String stopFormated = DateFormatter.toString(range.end);

        Disposable d = DB.getDatabase(getContext()).temperatureLogs().getByDate(startFormated, stopFormated)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TemperatureLog>>() {


                    @Override
                    public void accept(final List<TemperatureLog> dane) throws Exception {

                       btnRefresh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawCharts(dane);
                            }
                        });
                        cbTemperature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                temperatureChecked = isChecked;
                                drawCharts(dane);
                            }
                        });

                        cbHumidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                humidityChecked = isChecked;
                                drawCharts(dane);
                            }
                        });

                    }

                    private void drawCharts(final List<TemperatureLog> dane) {

                        boolean temperatureChecked = cbTemperature.isChecked();
                        boolean humidityChecked = cbHumidity.isChecked();

                        lineChart.setVisibility(View.VISIBLE);

                        List<Entry> temperatureEntries = new ArrayList<>();
                        for (TemperatureLog data : dane) {
                            temperatureEntries.add(new Entry(data.uid, data.getTemperature()));
                        }

                        List<Entry> humidityEntries = new ArrayList<>();
                        for (TemperatureLog data : dane) {
                            humidityEntries.add(new Entry(data.uid, data.getHumidity()));
                        }

                        LineDataSet temperatureDataSet = new LineDataSet(temperatureEntries, "℃");
                        temperatureDataSet.setColors(new int []{R.color.colorTemperature}, getContext());
                        temperatureDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        temperatureDataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        temperatureDataSet.setDrawCircles(false);

                        LineDataSet humidityDataSet = new LineDataSet(humidityEntries, "% hum.");
                        humidityDataSet.setColors(new int []{R.color.colorHumidity}, getContext());
                        humidityDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                        humidityDataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        humidityDataSet.setDrawCircles(false);


                        Legend legend = lineChart.getLegend();
                        legend.setEnabled(true);
                        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                        legend.setTextColor(Color.BLACK);

                        if (!temperatureChecked && !humidityChecked ){
                            lineChart.setVisibility(View.INVISIBLE);
                        }

// only Temperature

                        if (temperatureChecked && !humidityChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature");
                            lineChart.invalidate();
                        }
//only Humidity

                        if (!temperatureChecked && humidityChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(humidityDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Huminidity");
                            lineChart.invalidate();
                        }

// Temperature + Humidity

                        if (temperatureChecked && humidityChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);
                            dataSets.add(humidityDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature & Huminidity");
                            lineChart.invalidate();
                        }

                    }
                });
    }

    public static class DateRange{

        public Date start = new Date();
        public Date end = new Date();

        void setStartDate(int year, int month, int date) {
            start.setYear(year);
            start.setMonth(month);
            start.setDate(date);
        }

        void setStopDate(int year, int month, int date) {
            end.setYear(year);
            end.setMonth(month);
            end.setDate(date);
        }

        void setStartTime(int hrs, int min){
            start.setHours(hrs);
            start.setMinutes(min);
        }

        void setStopTime(int hrs, int min){
            end.setHours(hrs);
            end.setMinutes(min);
        }
    }
}