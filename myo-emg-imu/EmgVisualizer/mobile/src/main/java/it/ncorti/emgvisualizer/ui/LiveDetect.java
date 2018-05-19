package it.ncorti.emgvisualizer.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import it.ncorti.emgvisualizer.DTO.Game;
import it.ncorti.emgvisualizer.DTO.Player;
import it.ncorti.emgvisualizer.DataAnalysis.PredictionResult;
import it.ncorti.emgvisualizer.DataAnalysis.Stroke;
import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.model.Sensor;
import it.ncorti.emgvisualizer.utils.Constants;
import it.ncorti.emgvisualizer.utils.ObservableHashMap;
import it.ncorti.emgvisualizer.utils.Utils;

public class LiveDetect extends AppCompatActivity {
    PredictionResult predictionResult = PredictionResult.getInstance();
    private Sensor controlledSensor;

    ObservableHashMap<String,Integer> resultMap =  predictionResult.getAllResults();
    RequestQueue requestQueue;
    private Button FT;
    private Button FS;
    private Button BT;
    private Button BS;
    private Button button;
    private Handler handler;
    private Button endButton;
    private Button Serve;
    private TextView motionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        this.controlledSensor = MySensorManager.getInstance().getMyo();
        setContentView(R.layout.activity_live_detect);
        endButton = (Button) findViewById(R.id.button);
        FT = (Button) findViewById(R.id.FT);
        FS = (Button) findViewById(R.id.FS);
        BT = (Button) findViewById(R.id.BT);
        BS = (Button) findViewById(R.id.BS);
        Serve = (Button) findViewById(R.id.Serve);
        motionData = (TextView) findViewById(R.id.detecting);
        handler = new Handler();
        resultMap.setOnEventListener(new ObservableHashMap.OnEventListener() {
            @Override
            public void onPut(ObservableHashMap map, Object key, Object value) {
                if (key.equals(Stroke.ForehandTop.toString())) {
                    button = FT;
                    motionData.setText(Stroke.ForehandTop.toString());
                } else if (key.equals(Stroke.ForehandSlice.toString())) {
                    button = FS;
                    motionData.setText(Stroke.ForehandSlice.toString());

                } else if (key.equals(Stroke.BackhandSlice.toString())) {
                    button = BS;
                    motionData.setText(Stroke.BackhandSlice.toString());

                } else if (key.equals(Stroke.BackhandTop.toString())) {
                    button = BT;
                    motionData.setText(Stroke.BackhandTop.toString());

                } else if (key.equals(Stroke.Serve.toString())){
                    button = Serve;
                    motionData.setText(Stroke.Serve.toString());
                } else {
                    motionData.setText(Stroke.NoSwing.toString());
                }
                if (button != null) {
                    System.out.println("Inisde Button ");
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


        final Dialog dialog = new Dialog(this);

        endButton = (Button)findViewById(R.id.button);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlledSensor.stopMeasurement();
                dialog.setContentView(R.layout.end_game_save_dialog);
                dialog.setTitle("Save data");
                TextView text = (TextView) dialog.findViewById(R.id.textDialogYesNoMessage);
                text.setText("Save this match data?");

                Button yes = (Button) dialog.findViewById(R.id.btnDialogYes);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        saveGame();
                    }
                });
                Button no = (Button) dialog.findViewById(R.id.btnDialogNo);

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(LiveDetect.this, MainActivity.class);
                        startActivity(intent);

                    }
                });
                dialog.show();
            }
        });

    }
    private void saveGame() {
        Bundle bundle = getIntent().getExtras();
        String startTime= bundle.getString("startTime");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String emailId = settings.getString("emailid",null);
        Game game = new Game();
        Player player = new Player();
        player.setEmailid(emailId);
        game.setPlayer(player);
        game.setPlayedOn(Utils.getCurrentDate());
        game.setStartTime(startTime);
        game.setEndTime(Utils.getCurrentTime());
        game.setBackhandSlice(resultMap.get(Stroke.BackhandSlice.toString()));
        game.setBackhandTopspin(resultMap.get(Stroke.BackhandTop.toString()));
        game.setForehandSlice(resultMap.get(Stroke.ForehandSlice.toString()));
        game.setForehandTopspin(resultMap.get(Stroke.ForehandTop.toString()));
        game.setServe(resultMap.get(Stroke.Serve.toString()));
        String url = Constants.REST_URL_BASE+Constants.START_GAME;
        Gson gson = new Gson();
        String json = gson.toJson(game);
        System.out.println(json);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Intent intent = new Intent(LiveDetect.this, Stats.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(Stroke.BackhandSlice.toString(),resultMap.get(Stroke.BackhandSlice.toString()));
                        bundle.putInt(Stroke.BackhandTop.toString(),resultMap.get(Stroke.BackhandTop.toString()));
                        bundle.putInt(Stroke.ForehandSlice.toString(),resultMap.get(Stroke.ForehandSlice.toString()));
                        bundle.putInt(Stroke.ForehandTop.toString(),resultMap.get(Stroke.ForehandTop.toString()));
                        bundle.putInt(Stroke.Serve.toString(),resultMap.get(Stroke.Serve.toString()));
                        intent.putExtras(bundle);
                        intent.putExtra("FROM_ACTIVITY", "Live");
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error saving progress!!!",
                        Toast.LENGTH_LONG).show();
                /*error.printStackTrace();
                Error = (TextView) findViewById(it.ncorti.emgvisualizer.R.id.textView5);
                Error.setText("Error in Saving the progress!!");*/
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };


        requestQueue.add(jsonRequest);
    }

}
