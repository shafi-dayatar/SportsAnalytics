package it.ncorti.emgvisualizer.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import it.ncorti.emgvisualizer.DTO.Player;
import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.ui.views.Login;
import it.ncorti.emgvisualizer.utils.Constants;
import it.ncorti.emgvisualizer.utils.Handedness;

public class Signup extends AppCompatActivity {

    RequestQueue requestQueue;
    private TextView Error;
    private Button bSignup;
    private EditText tName;
    private EditText tEmail;
    private EditText tpassword;
    private EditText tage;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private RadioGroup radiohandGroup;
    private RadioButton radiohandButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tName = (EditText)findViewById(it.ncorti.emgvisualizer.R.id.editText);
        tEmail = (EditText)findViewById(it.ncorti.emgvisualizer.R.id.editText2);
        tpassword = (EditText)findViewById(it.ncorti.emgvisualizer.R.id.editText3);
        tage = (EditText)findViewById(it.ncorti.emgvisualizer.R.id.editText4);
        radioSexGroup = (RadioGroup) findViewById(R.id.gender_radiogroup);
        radiohandGroup = (RadioGroup) findViewById(R.id.handedness_radiogroup);
        bSignup = (Button)findViewById(R.id.button3);
        bSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int selectedIdSex = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedIdSex);
                String sex = radioSexButton.getText().toString();
                int selectedId = radiohandGroup.getCheckedRadioButtonId();
                radiohandButton = (RadioButton) findViewById(selectedId);
                String handedness = radiohandButton.getText().toString().contains("Right")? "Right": "Left";
                Player player = new Player();
                player.setEmailid(tEmail.getText().toString());
                player.setName(tName.getText().toString());
                player.setAge(Integer.parseInt(tage.getText().toString()));
                player.setPassword(tpassword.getText().toString());
                player.setGender(sex);
                player.setHandedness(handedness);
                createPlayer(player);
            }
        });
    }

    private void createPlayer(Player player) {
        String url = Constants.REST_URL_BASE+Constants.CREATE_PLAYER;
        Gson gson = new Gson();
        String json = gson.toJson(player);

        //JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: handle success
                        System.out.println(response);
                        Intent intent = new Intent(Signup.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Error = (TextView) findViewById(it.ncorti.emgvisualizer.R.id.textView5);
                Error.setText("Error in creating the Player!!");
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
