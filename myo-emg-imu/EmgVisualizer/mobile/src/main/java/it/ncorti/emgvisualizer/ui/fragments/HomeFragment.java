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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ncorti.emgvisualizer.DTO.Game;
import it.ncorti.emgvisualizer.DataAnalysis.Stroke;
import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.model.Sensor;
import it.ncorti.emgvisualizer.ui.LiveDetect;
import it.ncorti.emgvisualizer.ui.MySensorManager;
import it.ncorti.emgvisualizer.ui.SelectGame;
import it.ncorti.emgvisualizer.ui.Stats;
import it.ncorti.emgvisualizer.utils.Constants;
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
    final Map<String, Integer> fh_topspinmap = new HashMap<>();
    final Map<String, Integer> bh_topspinmap = new HashMap<>();
    final Map<String, Integer> fh_slicemap = new HashMap<>();
    final Map<String, Integer> bh_slicemap = new HashMap<>();
    RequestQueue requestQueue;

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
                Intent intent_chart = new Intent(getActivity(), SelectGame.class);
                startActivity(intent_chart);
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        getData(chart);

        return view;
    }


    private void getData(BarChart chart) {

        String url = Constants.REST_URL_BASE + Constants.WEEKLY_DATA;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String emailId = settings.getString("emailid",null);

        url  = url+"?emailId="+emailId;
        System.out.println("url" + url);

        final List<Game> gameList = new ArrayList();
        final BarChart chartdata = chart;
        final Gson gson = new Gson();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JsonParser parser = new JsonParser();
                            JsonObject jsonObject = (JsonObject) parser.parse(response);
                            JsonArray array = jsonObject.getAsJsonArray("responseObject");
                            for (int i=0;i<array.size();i++){
                                JsonElement gameObject = array.get(i);
                                Game game = gson.fromJson(gameObject,Game.class);
                                gameList.add(game);
                                System.out.println("$$$$$$$$$$$" + game.getForehandTopspin() + "Played on " + game.getPlayedOn());
                                if(!fh_topspinmap.containsKey(game.getPlayedOn())){
                                    fh_topspinmap.put(game.getPlayedOn(), game.getForehandTopspin());
                                }
                                else {
                                    fh_topspinmap.put(game.getPlayedOn(), fh_topspinmap.get(game.getPlayedOn()) + game.getForehandTopspin());
                                }
                                if(!bh_topspinmap.containsKey(game.getPlayedOn())){
                                    bh_topspinmap.put(game.getPlayedOn(), game.getBackhandTopspin());
                                }
                                else {
                                    bh_topspinmap.put(game.getPlayedOn(), game.getBackhandTopspin() + game.getBackhandTopspin());
                                }
                                if(!fh_slicemap.containsKey(game.getPlayedOn())){
                                    fh_slicemap.put(game.getPlayedOn(), game.getForehandTopspin());
                                }
                                else {
                                    fh_slicemap.put(game.getPlayedOn(), game.getForehandTopspin() + game.getForehandTopspin());
                                }if(!bh_slicemap.containsKey(game.getPlayedOn())){
                                    bh_slicemap.put(game.getPlayedOn(), game.getForehandTopspin());
                                }
                                else {
                                    bh_slicemap.put(game.getPlayedOn(), game.getForehandTopspin() + game.getForehandTopspin());
                                }
                            }
                            SetupBarChart(chartdata);
                        } catch(Exception e){
                            System.out.println("Error" + e);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }
        };
        System.out.println("Before string request");
        requestQueue.add(stringRequest);
        System.out.println("after string request");
    }

    public void SetupBarChart(BarChart chart){
        int groupCount = fh_topspinmap.size();

        ArrayList xVals = new ArrayList();

        for(Map.Entry<String, Integer> entry: fh_topspinmap.entrySet()){
            xVals.add(entry.getKey());
        }

        int i =0;

        ArrayList fh_Topspin = new ArrayList();
        ArrayList fh_Slice = new ArrayList();
        ArrayList bh_Topspin = new ArrayList();
        ArrayList bh_Slice = new ArrayList();

        for(Map.Entry<String, Integer> entry: fh_topspinmap.entrySet()){
            fh_Topspin.add(new BarEntry(i, entry.getValue()));
            i++;
        }

        for(Map.Entry<String, Integer> entry: fh_slicemap.entrySet()){
            fh_Slice.add(new BarEntry(i, entry.getValue()));
            i++;
        }

        for(Map.Entry<String, Integer> entry: bh_topspinmap.entrySet()){
            bh_Topspin.add(new BarEntry(i, entry.getValue()));
            i++;
        }

        for(Map.Entry<String, Integer> entry: bh_slicemap.entrySet()){
            bh_Slice.add(new BarEntry(i, entry.getValue()));
            i++;
        }

        BarDataSet set1, set2, set3, set4;

        set1 = new BarDataSet(fh_Topspin, "FH Topspin");
        set1.setColor(Color.CYAN);
        set1.setValueTextColor(Color.WHITE);
        set2 = new BarDataSet(fh_Slice, "FH Slice");
        set2.setColor(Color.RED);
        set2.setValueTextColor(Color.WHITE);
        set3 = new BarDataSet(bh_Topspin, "BH Topspin");
        set3.setColor(Color.YELLOW);
        set3.setValueTextColor(Color.WHITE);
        set4 = new BarDataSet(bh_Slice, "BH Topspin");
        set4.setColor(Color.GREEN);
        set4.setValueTextColor(Color.WHITE);

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
        chart.setBackgroundColor(Color.TRANSPARENT); //set whatever color you prefer
        chart.setDrawGridBackground(false);// this is a must

//        chart.setDescription("This Week Progress");
//        chart.setDescriptionColor(Color.WHITE);

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