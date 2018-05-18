package it.ncorti.emgvisualizer.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import it.ncorti.emgvisualizer.DataAnalysis.Stroke;
import it.ncorti.emgvisualizer.R;


public class Stats extends AppCompatActivity {
    int[] stats = {20,30,40,50,60};
    String strokes[] = {"Forehand-Slice", "Forehand-Topspin", "Backhand-Slice", "Backhand-Topspin","serve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Bundle bundle = getIntent().getExtras();
        stats[0] = bundle.getInt(Stroke.ForehandSlice.toString(),0);
        stats[1] = bundle.getInt(Stroke.ForehandTop.toString(),0);
        stats[2] = bundle.getInt(Stroke.BackhandSlice.toString(),0);
        stats[3] = bundle.getInt(Stroke.BackhandTop.toString(),0);
        stats[4] = bundle.getInt(Stroke.Serve.toString(),0);
        setupPieChart();
    }

    private void setupPieChart() {

        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i=0; i< stats.length; i++){
            pieEntries.add(new PieEntry(stats[i], strokes[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Stroke Distribution");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(16f);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        Legend legend = chart.getLegend();
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();

        legend.setTextSize(16f);
        legend.setTextColor(Color.WHITE);
        legend.setWordWrapEnabled(true);
        legend.setXEntrySpace(25f);
        legend.setYEntrySpace(25f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
    }
}
