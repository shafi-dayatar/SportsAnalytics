package it.ncorti.emgvisualizer.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.ncorti.emgvisualizer.R;

public class LiveDetect extends AppCompatActivity {
    private Button FT;
    private Button FS;
    private Button BT;
    private Button BS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detect);
        FT = (Button)findViewById(R.id.FT);
        FT.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View view) {
                FT.setBackgroundResource(R.drawable.rounded_button2);

                FT.setText("Forehand Topspin " + (++counter));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        FT.setBackgroundResource(R.drawable.rounded_button);
                    }
                }, 500);
            }
        });
        FS = (Button)findViewById(R.id.FS);
        FS.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View view) {
                FS.setBackgroundResource(R.drawable.rounded_button2);

                FS.setText("Forehand Topspin " + (++counter));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        FT.setBackgroundResource(R.drawable.rounded_button);
                    }
                }, 500);
            }
        });
        BT = (Button)findViewById(R.id.BT);
        BT.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View view) {
                BT.setBackgroundResource(R.drawable.rounded_button2);

                BT.setText("Forehand Topspin " + (++counter));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        FT.setBackgroundResource(R.drawable.rounded_button);
                    }
                }, 500);
            }
        });
        BS = (Button)findViewById(R.id.BS);
        BS.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View view) {
                BS.setBackgroundResource(R.drawable.rounded_button2);

                BS.setText("Forehand Topspin " + (++counter));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        FT.setBackgroundResource(R.drawable.rounded_button);
                    }
                }, 500);
            }
        });
    }
}
