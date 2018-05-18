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
import it.ncorti.emgvisualizer.utils.Constants;
import it.ncorti.emgvisualizer.utils.ObservableHashMap;
import it.ncorti.emgvisualizer.utils.Utils;

public class LiveDetect extends AppCompatActivity {
    PredictionResult predictionResult = PredictionResult.getInstance();
    ObservableHashMap<String,Integer> resultMap =  predictionResult.getAllResults();
    RequestQueue requestQueue;
    private Button FT;
    private Button FS;
    private Button BT;
    private Button BS;
    private Button button;
    private Handler handler;
    private Button endButton;
    private TextView Error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_live_detect);
        endButton = (Button) findViewById(R.id.button);
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


        final Dialog dialog = new Dialog(this);

        endButton = (Button)findViewById(R.id.button);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.end_game_save_dialog);
                dialog.setTitle("Save data");
                TextView text = (TextView) dialog.findViewById(R.id.textDialogYesNoMessage);
                text.setText("Save this match data?");

                Button yes = (Button) dialog.findViewById(R.id.btnDialogYes);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        //Enter code to save data
                        saveGame();
                    }
                });
                Button no = (Button) dialog.findViewById(R.id.btnDialogNo);

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        //Enter code to delete or dismiss data

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
        System.out.println("emailid" + emailId);
        Game game = new Game();
        Player player = new Player();
        player.setEmailid(emailId);
        game.setPlayer(player);
        game.setPlayedOn(Utils.getCurrentDate());
        game.setStartTime(startTime);
        game.setEndTime(Utils.getCurrentTime());
        // TODO change as per labels
        game.setBackhandSlice(resultMap.get("Backhand"));
        game.setBackhandTopspin(resultMap.get("Backhand"));
        game.setForehandSlice(resultMap.get("Forehand"));
        game.setForehandTopspin(resultMap.get("Forehand"));
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
                        bundle.putInt(Stroke.BACKSLICE.toString(),resultMap.get("Backhand"));
                        bundle.putInt(Stroke.BACKTOP.toString(),resultMap.get("Backhand"));
                        bundle.putInt(Stroke.FORESLICE.toString(),resultMap.get("Forehand"));
                        bundle.putInt(Stroke.FORETOP.toString(),resultMap.get("Forehand"));
                        // TODO change after model change
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Error = (TextView) findViewById(it.ncorti.emgvisualizer.R.id.textView5);
                Error.setText("Error in Saving the progress!!");
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
