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

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.model.EventBusProvider;
import it.ncorti.emgvisualizer.model.ImuDataPoint;
import it.ncorti.emgvisualizer.model.RawDataPoint;
import it.ncorti.emgvisualizer.model.Sensor;
import it.ncorti.emgvisualizer.model.SensorConnectEvent;
import it.ncorti.emgvisualizer.model.SensorMeasuringEvent;
import it.ncorti.emgvisualizer.myo.CSVUtil;
import it.ncorti.emgvisualizer.ui.MySensorManager;

import com.opencsv.CSVWriter;


/**
 * Fragment for controlling sensors, allowing to connect and start raw data receiving
 * @author Nicola
 */
public class ControlFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /** TAG for debugging purpose */
    private static final String TAG = "ControlFragment";

    /** Reference to controlled sensor */
    private Sensor controlledSensor;

    /** Reference to Textview for Sensor name */
    private TextView txtSensorName;
    /** Reference to Textview for Sensor status */
    private TextView txtSensorStatus;
    /** Reference to Button to trigger connection */
    private Button btnConnection;
    /** Reference to Switch to trigger measuring */
    private Switch onStream;
    //private Switch imuStream;
    /** Reference to Seekbar for streaming speed */
    private SeekBar skbRatio;

    /**
     * Public constructor to create a new ControlFragment
     */
    public ControlFragment() {
        this.controlledSensor = MySensorManager.getInstance().getMyo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_control, container, false);

        btnConnection = (Button) view.findViewById(R.id.control_btn_connect);
        txtSensorName = (TextView) view.findViewById(R.id.control_sensor_name);
        txtSensorStatus = (TextView) view.findViewById(R.id.control_sensor_status);
        onStream = (Switch) view.findViewById(R.id.control_swc_stream);
        onStream.setOnCheckedChangeListener(this);
//        imuStream = (Switch) view.findViewById(R.id.control_imu_stream);
//        imuStream.setOnCheckedChangeListener(this);

        txtSensorName.setText(controlledSensor.getName());
        updateSensorStatusView();

        btnConnection.setOnClickListener(this);
        setButtonConnect(controlledSensor.isConnected());
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.control_btn_connect) {
            if (!controlledSensor.isConnected()) {
                controlledSensor.startConnection();
                Toast.makeText(getActivity(), this.getString(R.string.connection_started), Toast.LENGTH_SHORT).show();
            } else {
                controlledSensor.stopConnection();
                Toast.makeText(getActivity(), this.getString(R.string.connection_stopped), Toast.LENGTH_SHORT).show();
            }
            setButtonConnect(controlledSensor.isConnected());
        }
        updateSensorStatusView();
    }

    /**
     * Private method for updating button connect state upon sensor status
     * @param connect True if sensor is connected, false otherwise
     */
    private void setButtonConnect(boolean connect) {

        Resources res = this.getResources();

        if (connect) {
            // Display disconnect layout
            btnConnection.setText(this.getString(R.string.disconnect));
            btnConnection.setCompoundDrawablesWithIntrinsicBounds(res.getDrawable(R.drawable.ic_bluetooth_disabled_white_36dp), null, null, null);
        } else {
            btnConnection.setText(this.getString(R.string.connect));
            btnConnection.setCompoundDrawablesWithIntrinsicBounds(res.getDrawable(R.drawable.ic_bluetooth_searching_white_36dp), null, null, null);
        }
    }

    /**
     * Method for updating sensor status textview
     */
    private void updateSensorStatusView() {
        txtSensorStatus.setText(Html.fromHtml(controlledSensor.getStatusString()));
        if (controlledSensor.isConnected()) {
            onStream.setEnabled(true);
            //imuStream.setEnabled(true);
        }
        else {
            onStream.setEnabled(false);
            //imuStream.setEnabled(false);
        }
        if (controlledSensor.isMeasuring() && controlledSensor.isIMUMeasuring()) {
            onStream.setChecked(true);
        } else {
            onStream.setChecked(false);
        }
//        if(controlledSensor.isIMUMeasuring()){
//            imuStream.setChecked(true);
//        } else {
//            imuStream.setChecked(false);
//
//        }
    }

    /**
     * Callback for Sensor connect event
     * @param event Event just received
     */
    @Subscribe
    public void onSensorConnectEvent(SensorConnectEvent event) {
        if (event.getSensor().getName().contentEquals(controlledSensor.getName())) {
            setButtonConnect(event.getSensor().isConnected());
            updateSensorStatusView();
            Log.d(TAG, "Event connected received " + event.getState());
        }
    }

    /**
     * Callback for Sensor start measuring event
     * @param event Event just received
     */
    @Subscribe
    public void onSensorMeasuringEvent(SensorMeasuringEvent event) {
        if (event.getSensor().getName().contentEquals(controlledSensor.getName())) {
            updateSensorStatusView();
            Log.d(TAG, "Event measuring received " + event.getState());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBusProvider.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBusProvider.unregister(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        System.out.println("inside");
        if (compoundButton.getId() == R.id.control_swc_stream) {
            if (b) {
                //start Measuring both IMU and EMG Data
                controlledSensor.startMeasurement("BOTH");
            }
            else {
                controlledSensor.stopMeasurement();

                System.out.println("@@@@@@@@@ Start collecting IMU Data @@@@@@@@");
                try{
                    String filePath = getActivity().getFilesDir().getPath().toString() + "/imu.csv";
                    System.out.println(filePath);

                    File file = new File(filePath);
                    // if file doesnt exists, then create it
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
                    CSVWriter csvWriter = new CSVWriter(filewriter);


                    csvWriter.writeNext(new String[]{"timestamp", "orientation_x", "orientation_y",
                            "orientation_z", "orientation_w", "AccX",
                            "AccY", "AccZ", "GyroX", "GyroY", "GyroZ"});

                    LinkedList<ImuDataPoint> dataList = controlledSensor.getIMUDataPoints();
                for(ImuDataPoint point : dataList) {
                    List<String> list  = new ArrayList<>();
                    list.add(String.valueOf(point.getTimestamp()));
                    for(double val : point.getOrientationData()) {
                        list.add(String.valueOf(val));
                    }
                    for(double val : point.getOrientationData()) {
                        list.add(String.valueOf(val));
                    }
                    for(double val : point.getAccelerometerData()) {
                        list.add(String.valueOf(val));
                    }
                    String[] arr = new String[list.size()];
                    csvWriter.writeNext(list.toArray(arr));
                }
                csvWriter.close();
                }catch(Exception e) {
                    e.printStackTrace();
                }
                try{
                    String filePath = getActivity().getFilesDir().getPath().toString() + "/emg.csv";

                    File file = new File(filePath);
                    // if file doesnt exists, then create it
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
                    CSVWriter writer = new CSVWriter(filewriter);

                    writer.writeNext(new String[]{"Timestamp", "emg1", "emg2","emg3","emg4","emg5","emg6","emg7","emg8"});

                    LinkedList<RawDataPoint> dataList = controlledSensor.getDataPoints();
                    for(RawDataPoint point : dataList) {
                        List<String> list  = new ArrayList<>();
                        list.add(String.valueOf(point.getTimestamp()));
                        for(float val : point.getValues()) {
                            list.add(String.valueOf(val));
                        }
                        String[] arr = new String[list.size()];
                        writer.writeNext(list.toArray(arr));
                    }
                    writer.close();
                }catch(Exception e ) {
                    e.printStackTrace();
                }
                try{
                    readCsvData();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void readCsvData() throws Exception{
        String filePathImu = getActivity().getFilesDir().getPath().toString() + "/imu.csv";

        CSVReader reader = new CSVReader(new FileReader(filePathImu));
        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            System.out.println(nextLine[0] +"<-->"+ nextLine[1] +" <-->"+ nextLine[2]+"<--> "+ nextLine[3]+"<--> "+nextLine[4]+"<--> "+ nextLine[5]+" <-->"+ nextLine[6]+"<--> "+ nextLine[7]+"<--> "+ nextLine[8]+"<--> "+ nextLine[9]+"<--> "+ nextLine[10]);
        }
        System.out.println("############ IMU FINISHED EMG PRINTINt #################");
        String filePathEmg = getActivity().getFilesDir().getPath().toString() + "/emg.csv";

        CSVReader readerEmf = new CSVReader(new FileReader(filePathEmg));
        String [] nextEmg;
        while ((nextEmg = readerEmf.readNext()) != null) {
            System.out.println(nextEmg[0] +"<-->"+ nextEmg[1] +" <-->"+ nextEmg[2]+"<--> "+ nextEmg[3]+"<--> "+nextEmg[4]+"<--> "+ nextEmg[5]+" <-->"+ nextEmg[6]+"<--> "+ nextEmg[7]+"<--> "+ nextEmg[8]);
        }
    }
}