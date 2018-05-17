package it.ncorti.emgvisualizer.ui.views;

import android.content.Intent;
import android.os.Bundle;
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


public class Login extends AppCompatActivity{
    private EditText email;
    private EditText password;
    private Button Login;
    private TextView Error;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        requestQueue = Volley.newRequestQueue(this);
        email = (EditText)findViewById(it.ncorti.emgvisualizer.R.id.editText);
        password = (EditText)findViewById(it.ncorti.emgvisualizer.R.id.editText2);
        Login = (Button)findViewById(it.ncorti.emgvisualizer.R.id.button3);

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validate(email.getText().toString(),password.getText().toString());
            }
        });


    }
    private void validate(String userEmail, String uPassword){
//        if ((userEmail.equals("admin.team@gmail.com"))&&(uPassword.equals("admin"))){
//            Intent intent = new Intent(Login.this, MainActivity.class);
//            startActivity(intent);
//        } else{
//            Error = (TextView)findViewById(it.ncorti.emgvisualizer.R.id.textView5);
//            Error.setText("Username and Password pair do not exist!!");
//        }
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
                    //TODO: handle success
                    System.out.println(response);
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
    }

}
