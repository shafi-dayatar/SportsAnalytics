package it.ncorti.emgvisualizer.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.ncorti.emgvisualizer.DataAnalysis.PredictionResult;
import it.ncorti.emgvisualizer.DataAnalysis.Stroke;
import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.utils.ObservableHashMap;

public class LiveDetect extends AppCompatActivity {
    PredictionResult predictionResult = PredictionResult.getInstance();
    ObservableHashMap resultMap =  predictionResult.getAllResults();
    private Button FT;
    private Button FS;
    private Button BT;
    private Button BS;
    private Button button;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detect);
        FT = (Button) findViewById(R.id.FT);
        FS = (Button) findViewById(R.id.FS);
        BT = (Button) findViewById(R.id.BT);
        BS = (Button) findViewById(R.id.BS);
        handler = new Handler();
        resultMap.setOnEventListener(new ObservableHashMap.OnEventListener() {
            @Override
            public void onPut(ObservableHashMap map, Object key, Object value) {
                System.out.println("-------------Key is -------------"+ key);
                if (key.equals(Stroke.FORETOP)) {
                    button = FT;
                } else if (key.equals(Stroke.FORESLICE)) {
                    button = FS;
                } else if (key.equals(Stroke.BACKSLICE)) {
                    button = BS;
                } else if (key.equals(Stroke.BACKTOP)) {
                    button = BT;
                } // TODO comment below lies after new model
                else if(key.equals("Backhand")){
                    button = BS;
                } else if(key.equals("Forehand")){
                    button = FS;
                }
                if (button != null) {
                    button.setBackgroundResource(R.drawable.rounded_button2);
                    button.setText(key + "  " + value);

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            button.setBackgroundResource(R.drawable.rounded_button);
                            button = null;
                        }
                    }, 500);
                }
            }
        },handler);


    }
}
