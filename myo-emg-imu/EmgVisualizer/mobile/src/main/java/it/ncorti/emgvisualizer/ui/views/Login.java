package it.ncorti.emgvisualizer.ui.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import it.ncorti.emgvisualizer.R;
import it.ncorti.emgvisualizer.ui.MainActivity;


public class Login extends AppCompatActivity{
    private EditText email;
    private EditText password;
    private Button Login;
    private TextView Error;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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
        if ((userEmail.equals("admin.team@gmail.com"))&&(uPassword.equals("admin"))){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        } else{
            Error = (TextView)findViewById(it.ncorti.emgvisualizer.R.id.textView5);
            Error.setText("Username and Password pair do not exist!!");
        }
    }

}