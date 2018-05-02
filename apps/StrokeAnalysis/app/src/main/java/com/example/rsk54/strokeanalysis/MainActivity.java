package com.example.rsk54.strokeanalysis;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rsk54.strokeanalysis.models.Classifier;
import com.example.rsk54.strokeanalysis.models.TensorflowClassifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;
public class MainActivity extends AppCompatActivity {

    private static final int FEATURES = 500;
    private Classifier mClassifier;
    private TensorFlowInferenceInterface inferenceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        prepArray();
        loadModel();
    }

    private List<TennisData> tennisData = new ArrayList<>();
    private void prepArray() {



        String line = null;
        try{
            InputStream is = getAssets().open("data.csv");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            //step over headers
            reader.readLine();

            while( (line = reader.readLine()) != null) {
                // split by ','
                String[] tokens = line.split(",");

                // read data

                TennisData data = new TennisData();
                data.setTimstamp(Double.parseDouble(tokens[1]));
                data.setOrientaion_x(Double.parseDouble(tokens[2]));
                data.setOrientaion_y(Double.parseDouble(tokens[3]));
                data.setOrientaion_z(Double.parseDouble(tokens[4]));
                data.setAcc_x(Double.parseDouble(tokens[5]));
                data.setAcc_y(Double.parseDouble(tokens[6]));
                data.setAcc_z(Double.parseDouble(tokens[7]));
                data.setGyro_x(Double.parseDouble(tokens[8]));
                data.setGyro_y(Double.parseDouble(tokens[9]));
                data.setGyro_z(Double.parseDouble(tokens[10]));
                tennisData.add(data);

                Log.d("MyActivity", "Just Created" + data);
            }
        } catch (IOException e){
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }



    private void loadModel() {
        //The Runnable interface is another way in which you can implement multi-threading other than extending the
        // //Thread class due to the fact that Java allows you to extend only one class. Runnable is just an interface,
        // //which provides the method run.
        // //Threads are implementations and use Runnable to call the method run().
        System.out.println("IN load model");
        //inferenceInterface = new TensorFlowInferenceInterface(getAssets(), "tensorflow_lite_stroke_prediction.pb");
         new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("starting load model thread");
                try {
                    //add 2 classifiers to our classifier arraylist
                    //the tensorflow classifier and the keras classifier
//                    mClassifiers.add(
//                            TensorflowClassifier.create(getAssets(), "TensorFlow",
//                                    "opt_mnist_convnet.pb", "labels.txt", PIXEL_WIDTH,
//                                    "input", "output", true));
                    mClassifier = TensorflowClassifier.create(getAssets(), "Keras",
                            "tensorflow_lite_stroke_prediction.pb", "labels.txt", 10*50,
                            "input", "dense_6/Softmax", false);
                    // conv2d_11_input, dense_6/Softmax"
                } catch (final Exception e) {
                    //if they aren't found, throw an error!
                    System.out.println("excepetion while loading thread : " + e.getMessage());
                    Log.wtf("Error",e);
                    throw new RuntimeException("Error initializing classifiers!", e);
                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
