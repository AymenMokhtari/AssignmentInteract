package com.github.aymenmokthari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText username ;
    EditText pass;

    private Context context;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (Button)findViewById(R.id.loginBtn);
        username = (EditText)findViewById(R.id.loginTxt);
        pass = (EditText)findViewById(R.id.passwordTxt);



    }




}