package sportstar.com.sportstar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import eu.darken.myolib.processor.emg.EmgData;
import eu.darken.myolib.processor.emg.EmgProcessor;
import eu.darken.myolib.processor.imu.ImuData;
import eu.darken.myolib.processor.imu.ImuProcessor;
import eu.darken.myolib.Myo;
import eu.darken.myolib.MyoCmds;
import eu.darken.myolib.msgs.MyoMsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.Queue;
import java.util.LinkedList;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity implements
        ImuProcessor.ImuDataListener{
    public static final int MENU_LIST = 0;
    public static final int MENU_BYE = 1;

    private long mLastImuUpdate = 0;
    private long mLastEmgUpdate = 0;


    private EmgProcessor mEmgProcessor;
    private ImuProcessor mImuProcessor;

    public Myo mMyo;


    /** Device Scanning Time (ms) */
    private static final long SCAN_PERIOD = 2000;

    /** Intent code for requesting Bluetooth enable */
    private static final int REQUEST_ENABLE_BT = 1;

    private static final String TAG = "";

    private Handler mHandler;

    private TextView         emgDataText;
    private TextView         gestureText;


    private String deviceName;

    private Button graphButton1;
    private Button graphButton2;
    private Button graphButton3;
    private Button graphButton4;
    private Button graphButton5;
    private Button graphButton6;
    private Button graphButton7;
    private Button graphButton8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ready






        emgDataText = (TextView)findViewById(R.id.emgDataTextView);
        gestureText = (TextView)findViewById(R.id.gestureTextView);
        mHandler = new Handler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, MENU_LIST, 0, "Find Myo");
        menu.add(0, MENU_BYE, 0, "exit");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case MENU_LIST:
//                Log.d("Menu","Select Menu A");
                Intent intent = new Intent(this,ListActivity.class);
                startActivity(intent);
                return true;

            case MENU_BYE:
//                Log.d("Menu","Select Menu B");
                Toast.makeText(getApplicationContext(), "Close GATT", Toast.LENGTH_SHORT).show();
                return true;

        }
        return false;
    }


    public void onClickVibration(View v){


    }

    public void onClickUnlock(View v) {

    }

    public void onClickEMG(View v) {
        String s = getIntent().getStringExtra("eu.darken.myolib.Myo");
        System.out.println("###############"+s);
        mMyo = new Gson().fromJson(s, eu.darken.myolib.Myo.class);

        System.out.println("on click emg"+mMyo.toString());
        mEmgProcessor = new EmgProcessor();
        mMyo.addProcessor(mEmgProcessor);
        System.out.println(mMyo.getMyoInfo().getReservedData());
        System.out.println(mMyo.getEmgMode());
        mEmgProcessor.addListener(new EmgProcessor.EmgDataListener() {
            @Override
            public void onNewEmgData(EmgData emgData) {
                System.out.println("new EngDate");
                Log.i("EMG-Data", Arrays.toString(emgData.getData()));
            }
        });
    }

    public void onClickNoEMG(View v) {
        mImuProcessor = new ImuProcessor();
        mImuProcessor.addListener(this);
        mMyo.addProcessor(mImuProcessor);
    }

    public void onClickSave(View v) {

    }

    public void onClickDetect(View v) {

    }


    public void startSaveModel() {

    }

    public void startDetectModel() {

    }


    public void setGestureText(final String message) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNewImuData(final ImuData imuData) {
        if (System.currentTimeMillis() - mLastImuUpdate > 500) {
            if (mHandler != null)
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Img*******"+ imuData.toString());

                        //mOrientationData.setText("Orient: " + ImuData.format(imuData.getOrientationData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                       // mAcclData.setText("Accel: " + ImuData.format(imuData.getAccelerometerData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                        gestureText.setText("Gyro: " + ImuData.format(imuData.getGyroData()) + "\n" + mImuProcessor.getPacketCounter() + " IMU/s");
                    }
                });
            mLastImuUpdate = System.currentTimeMillis();
        }
    }
//    @Override
//    public void onNewEmgData(final EmgData emgData) {
//                       Log.i("EMG-Data", Arrays.toString(emgData.getData()));
//                System.out.println("EMG-Data&&&&&&&&&&"+emgData.toString());
//            }

}

