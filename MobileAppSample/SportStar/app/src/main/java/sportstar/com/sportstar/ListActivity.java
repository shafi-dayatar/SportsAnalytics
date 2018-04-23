package sportstar.com.sportstar;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.darken.myolib.BaseMyo;
import eu.darken.myolib.Myo;
import eu.darken.myolib.MyoCmds;
import eu.darken.myolib.MyoConnector;
import eu.darken.myolib.processor.emg.EmgData;
import eu.darken.myolib.processor.emg.EmgProcessor;
import eu.darken.myolib.tools.Logy;
import com.google.gson.Gson;

public class ListActivity extends AppCompatActivity  implements BaseMyo.ConnectionListener {
    public static final int MENU_SCAN = 0;
    public static final int LIST_DEVICE_MAX = 5;

    public static String TAG = "BluetoothList";

    /** Device Scanning Time (ms) */
    private static final long SCAN_PERIOD = 2000;

    /** Intent code for requesting Bluetooth enable */
    private static final int REQUEST_ENABLE_BT = 1;

    private Handler mHandler;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt    mBluetoothGatt;
    private ArrayList<String> deviceNames = new ArrayList<>();
    private String myoName = null;

    private List<Myo> myoList = new ArrayList();
    private ViewGroup mContainer;
    private boolean mScanning = false;
    private boolean isConnected = false;
    private MyoConnector mMyoConnector;
    private Myo connectedMyo;

    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView lv = (ListView) findViewById(R.id.listView1);
        mMyoConnector = new MyoConnector(this);
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
            adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, deviceNames);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                String item = (String) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item + " connect", Toast.LENGTH_SHORT).show();
                myoName = item;
                for(Myo myo : myoList) {
                    if(myoName.equals(myo.toString())) {
                        System.out.println("@@@@@@@@About to Connect @@@@@@");
                        myo.connect();
                        Toast.makeText(getApplicationContext(), "connected to clicked Device", Toast.LENGTH_SHORT).show();
                        connectedMyo = myo;
                        isConnected = true;
                        myo.setConnectionSpeed(BaseMyo.ConnectionSpeed.HIGH);
                        myo.writeSleepMode(MyoCmds.SleepMode.NEVER, null);
                        myo.writeMode(MyoCmds.EmgMode.RAW, MyoCmds.ImuMode.RAW, MyoCmds.ClassifierMode.DISABLED, null);
                        myo.writeUnlock(MyoCmds.UnlockType.HOLD, null);

                        EmgProcessor mEmgProcessor = new EmgProcessor();
                        myo.addProcessor(mEmgProcessor);
                        //System.out.println(myo.getMyoInfo().getReservedData());
                        System.out.println(myo.getEmgMode());
                        mEmgProcessor.addListener(new EmgProcessor.EmgDataListener() {
                            @Override
                            public void onNewEmgData(EmgData emgData) {
                                System.out.println("new EngDate");
                                Log.i("EMG-Data", Arrays.toString(emgData.getData()));
                            }
                        });
                        break;
                    }
                }

                    Intent intent;

                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    System.out.println("TAG"+connectedMyo.getFirmware() + connectedMyo.getManufacturerName());
                    String myoObject = (new Gson().toJson(connectedMyo));
                    System.out.println("#$$"+myoObject);
                    intent.putExtra("eu.darken.myolib.Myo", myoObject);

                    startActivity(intent);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_list, menu);
        menu.add(0, MENU_SCAN, 0, "Scan");
       // menu.add(0, MENU_Bye, 0, "Exit");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == MENU_SCAN) {
            scanDevice();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickScan(View v) {
        scanDevice();
    }

    public void scanDevice() {
            mMyoConnector.scan(2000, mScannerCallback);
    }

    private MyoConnector.ScannerCallback mScannerCallback = new MyoConnector.ScannerCallback() {
        @Override
        public void onScanFinished(final List<Myo> myos) {

            Logy.d(TAG, "MYOS:" + myos.size());
            System.out.println("****************"+myos.size());
            for (final Myo myo : myos) {
                if(myo != null) {
                    deviceNames.add(myo.toString());
                    System.out.println("myo-------->"+myo.getDeviceAddress());
                    myoList.add(myo);
                } else{
                }
            }
            if (mScanning) {
                mMyoConnector.scan(2000, mScannerCallback);
            }
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    adapter.notifyDataSetChanged();
                }
            });

        }

    };


    @Override
    public void onConnectionStateChanged(final BaseMyo myo, BaseMyo.ConnectionState state) {

        }
}
