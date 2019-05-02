package com.github.aymenmokthari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
    SharedPreferences prefs;
    int idSP;


    protected void onCreate(Bundle savedInstanceState) {

        session = new Session(this  );
        prefs = this.getSharedPreferences("myapp", this.MODE_PRIVATE);


        super.onCreate(savedInstanceState);
        if(session.loggedin()) {
            Intent intent = new Intent( MainActivity.this, ListActivity.class );
            Log.d("new intent" , "net intend");
            startActivity( intent );
        }
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
        helper = new Helper( this );

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = username.getText().toString();
                final String password = pass.getText().toString();

                if (!isValidEmail(email)) {
                    Toast.makeText(getBaseContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();


                } else {
                    helper.loginUser(email, password, new Helper.VolleyCallbackUserLogin() {
                        @Override
                        public void onSuccessResponse(String id, String email, String authToken) {


                            session.setLoggedin(true, email, id, authToken);

                            Intent intent = new Intent(MainActivity.this, ListActivity.class);
                            Log.d("new intent", "net intend");
                            startActivity(intent);
                        }

                        @Override
                        public void onFail(String msg) {
                            Log.d("ddd", msg);
                        }
                    });

                    Log.d("fsdf", email + "---" + password);


                    //authenticate user

                }
            }
        });
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }



}