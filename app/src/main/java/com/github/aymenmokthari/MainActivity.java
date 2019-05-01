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
import android.widget.Toast;
//aymen123M%
import com.github.aymenmokthari.helper.Helper;
import com.github.aymenmokthari.tools.Session;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText username ;
    EditText pass;
    Helper helper;
    Session session ;
    private Context context;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        login = (Button)findViewById(R.id.loginBtn);
        username = (EditText)findViewById(R.id.loginTxt);
        pass = (EditText)findViewById(R.id.passwordTxt);
        // adduser
        session = new Session(this  );
        helper = new Helper( this );

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                final String password = pass.getText().toString();


                helper.loginUser( email, password, new Helper.VolleyCallbackUserLogin() {
                    @Override
                    public void onSuccessResponse(String id, String email, String password) {


                        session.setLoggedin(true,email , password , id);

                        Intent intent = new Intent( MainActivity.this, ListContactActivity.class );
                        Log.d("new intent" , "net intend");
                        startActivity( intent );
                    }

                    @Override
                    public void onFail(String msg) {
                            Log.d("ddd",msg);
                    }
                } );

                Log.d( "fsdf", email+"---"+password );



                //authenticate user

            }
        });
    }




}