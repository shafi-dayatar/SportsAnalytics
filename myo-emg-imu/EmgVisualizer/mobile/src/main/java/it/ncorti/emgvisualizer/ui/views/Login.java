package it.ncorti.emgvisualizer.ui.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import it.ncorti.emgvisualizer.DTO.Player;
import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.ui.MainActivity;

import it.ncorti.emgvisualizer.utils.Constants;
import it.ncorti.emgvisualizer.ui.Signup;


public class Login extends AppCompatActivity{
    private EditText email;
    private EditText password;
    private Button Login;
    private TextView Error;
    RequestQueue requestQueue;

    private Button Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        requestQueue = Volley.newRequestQueue(this);
        email = (EditText)findViewById(it.ncorti.emgvisualizer.R.id.editText);
        password = (EditText)findViewById(it.ncorti.emgvisualizer.R.id.editText2);
        Login = (Button)findViewById(it.ncorti.emgvisualizer.R.id.button3);
        Signup = (Button)findViewById(it.ncorti.emgvisualizer.R.id.button4);

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validate(email.getText().toString(),password.getText().toString());
            }
        });

        Signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent_signup = new Intent(Login.this, Signup.class);
                startActivity(intent_signup);
            }
        });

    }
    private void validate(String userEmail, String uPassword){
        authenticateRestHandle(userEmail, uPassword);
    }

    private void authenticateRestHandle(final String userEmail, final String uPassword) {
        String url = Constants.REST_URL_BASE+Constants.LOGIN_URL;
        Map<String, String> params = new HashMap();
        params.put("emailid", userEmail);
        params.put("password", uPassword);

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("emailid", userEmail);
                    editor.apply();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Error = (TextView) findViewById(it.ncorti.emgvisualizer.R.id.textView5);
                Error.setText("Username and Password pair do not exist!!");
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


        //TODO uncmment above code and comment below

        /*SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("emailid", "madhuribharathula@gmail.com");
        editor.apply();
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();*/
    }

}
