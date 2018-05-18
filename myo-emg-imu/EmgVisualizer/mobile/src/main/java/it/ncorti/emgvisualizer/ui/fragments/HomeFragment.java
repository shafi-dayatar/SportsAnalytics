/* This file is part of EmgVisualizer.

    EmgVisualizer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    EmgVisualizer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with EmgVisualizer.  If not, see <http://www.gnu.org/licenses/>.
*/
package it.ncorti.emgvisualizer.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import it.ncorti.emgvisualizer.DataAnalysis.Stroke;
import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.model.Sensor;
import it.ncorti.emgvisualizer.ui.LiveDetect;
import it.ncorti.emgvisualizer.ui.MySensorManager;
import it.ncorti.emgvisualizer.ui.Stats;
import it.ncorti.emgvisualizer.utils.Utils;


/**
 * Fragment for showing home information.
 */
public class HomeFragment extends Fragment {

    int[] stats = {20,23,24,40};
    String strokes[] = {"Forehand-Slice", "Forehand-Topspin", "Backhand-Slice", "Backhand-Topspin"};
    float barWidth = 0.3f;
    float barSpace;
    float groupSpace = 0.4f;

    /**
     * Public constructor to create a new HomeFragment
     */
    public HomeFragment() {
    }
    private Sensor controlledSensor;
    private LinearLayout live_stats;
    private LinearLayout chartPage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controlledSensor = MySensorManager.getInstance().getMyo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        live_stats = (LinearLayout)view.findViewById(R.id.LiveResults);
        BarChart chart = (BarChart) view.findViewById(R.id.chart);
        live_stats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(controlledSensor == null || !controlledSensor.isConnected()) {
                    Toast.makeText(getActivity(), "Use Device Settings to connect the Myo", Toast.LENGTH_SHORT).show();
                }
                else {
                    controlledSensor.startMeasurement("BOTH");
                    Intent intent_live = new Intent(getActivity(), LiveDetect.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("startTime", Utils.getCurrentTime());
                    intent_live.putExtras(bundle);
                    startActivity(intent_live);
                }
            }
        });

        chartPage = (LinearLayout)view.findViewById(R.id.Stats);
        chartPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent_chart = new Intent(getActivity(), Stats.class);
                startActivity(intent_chart);
            }
        });

        setupBarChart(chart);

        return view;
    }

    private void setupBarChart(BarChart chart) {

        int groupCount = 7;

        ArrayList xVals = new ArrayList();

        xVals.add("Day 1");
        xVals.add("Day 2");
        xVals.add("Day 3");
        xVals.add("Day 4");
        xVals.add("Day 5");
        xVals.add("Day 6");
        xVals.add("Day 7");

        ArrayList fh_Topspin = new ArrayList();
        ArrayList fh_Slice = new ArrayList();
        ArrayList bh_Topspin = new ArrayList();
        ArrayList bh_Slice = new ArrayList();

        fh_Topspin.add(new BarEntry(1, (float) 1));
        fh_Topspin.add(new BarEntry(2, (float) 2));
        fh_Topspin.add(new BarEntry(3, (float) 3));
        fh_Topspin.add(new BarEntry(4, (float) 4));
        fh_Topspin.add(new BarEntry(5, (float) 5));
        fh_Topspin.add(new BarEntry(6, (float) 6));
        fh_Topspin.add(new BarEntry(7, (float) 7));

        fh_Slice.add(new BarEntry(1, (float) 1));
        fh_Slice.add(new BarEntry(2, (float) 4));
        fh_Slice.add(new BarEntry(3, (float) 6));
        fh_Slice.add(new BarEntry(4, (float) 8));
        fh_Slice.add(new BarEntry(5, (float) 1));
        fh_Slice.add(new BarEntry(6, (float) 3));
        fh_Slice.add(new BarEntry(7, (float) 2));

        bh_Topspin.add(new BarEntry(1, (float) 1));
        bh_Topspin.add(new BarEntry(2, (float) 7));
        bh_Topspin.add(new BarEntry(3, (float) 4));
        bh_Topspin.add(new BarEntry(4, (float) 5));
        bh_Topspin.add(new BarEntry(5, (float) 3));
        bh_Topspin.add(new BarEntry(6, (float) 8));
        bh_Topspin.add(new BarEntry(7, (float) 9));

        bh_Slice.add(new BarEntry(1, (float) 5));
        bh_Slice.add(new BarEntry(1, (float) 7));
        bh_Slice.add(new BarEntry(1, (float) 2));
        bh_Slice.add(new BarEntry(1, (float) 0));
        bh_Slice.add(new BarEntry(1, (float) 8));
        bh_Slice.add(new BarEntry(1, (float) 1));
        bh_Slice.add(new BarEntry(1, (float) 9));

        BarDataSet set1, set2, set3, set4;

        set1 = new BarDataSet(fh_Topspin, "FH Topspin");
        set1.setColor(Color.CYAN);
        set2 = new BarDataSet(fh_Slice, "FH Slice");
        set2.setColor(Color.RED);
        set3 = new BarDataSet(bh_Topspin, "BH Topspin");
        set3.setColor(Color.LTGRAY);
        set4 = new BarDataSet(bh_Slice, "BH Topspin");
        set4.setColor(Color.GREEN);

        BarData data = new BarData(set1, set2, set3, set4);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(0 + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(0, groupSpace, barSpace);
        chart.getData().setHighlightEnabled(false);
        chart.invalidate();
        chart.animateY(1000);


        Legend l = chart.getLegend();
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(8f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(12f);
    }
}