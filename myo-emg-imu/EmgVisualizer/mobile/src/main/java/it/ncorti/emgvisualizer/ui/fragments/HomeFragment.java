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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.model.Sensor;
import it.ncorti.emgvisualizer.ui.LiveDetect;
import it.ncorti.emgvisualizer.ui.MySensorManager;
import it.ncorti.emgvisualizer.ui.Stats;


/**
 * Fragment for showing home information.
 */
public class HomeFragment extends Fragment {

    /**
     * Public constructor to create a new HomeFragment
     */
    public HomeFragment() {
    }
    private Sensor controlledSensor;
    private LinearLayout live_stats;
    private LinearLayout chart;
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
        live_stats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(controlledSensor == null || !controlledSensor.isConnected()) {
                    Toast.makeText(getActivity(), "Use Device Settings to connect the Myo", Toast.LENGTH_SHORT).show();
                }
                else {
                    controlledSensor.startMeasurement("BOTH");
                    Intent intent_live = new Intent(getActivity(), LiveDetect.class);
                    startActivity(intent_live);
                }
            }
        });

        chart = (LinearLayout)view.findViewById(R.id.Stats);
        chart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent_chart = new Intent(getActivity(), Stats.class);
                startActivity(intent_chart);
            }
        });

        return view;
    }
}