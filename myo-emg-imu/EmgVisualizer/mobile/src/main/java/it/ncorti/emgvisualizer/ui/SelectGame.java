package it.ncorti.emgvisualizer.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ncorti.emgvisualizer.DTO.Game;
import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.utils.Constants;

public class SelectGame extends AppCompatActivity {

    private CalendarView CP;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);
        requestQueue = Volley.newRequestQueue(this);
        CP = (CalendarView)findViewById(R.id.calendar1);
        CP.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day_in_month) {
                StringBuffer sb = new StringBuffer();
                sb.append(year);
                sb.append("-");
                sb.append(month+1);
                sb.append("-");
                sb.append(day_in_month);
                getGameData(sb.toString());

            }
        });
    }
    private void getGameData(String requestDate) {
        String url = Constants.REST_URL_BASE+Constants.GAME_DATE;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String emailId = settings.getString("emailid",null);

        url  = url+"?requestDate="+requestDate+"&emailId="+emailId;
        System.out.println("url" + url);

        final List<Game> gameList = new ArrayList();
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
                                System.out.println("$$$$$$$$$$$" + game.getStartTime()+" "+game.getGameid());
                            }
                        } catch(Exception e){

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

        requestQueue.add(stringRequest);
    }
}
