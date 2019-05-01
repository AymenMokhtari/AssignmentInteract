package com.github.aymenmokthari.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Helper {
    public final static String ip_address  = "https://internal-api-staging-lb.interact.io/v2";
    public static final String TAG = "Response";

    private  Context context;

    public Helper(Context context) {
        this.context = context;
    }


    public void loginUser(final String email, final String password, final VolleyCallbackUserLogin volleyCallbackUserLogin) {


        RequestQueue queue = Volley.newRequestQueue(context);  // this = context
        // { "id_user": "1" , "foodname": "2019", "qte": "1", "calories": "2" }

        final String url = ip_address + "/login";
        Log.d("url" ,url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", email);
        params.put("password", password);
        Log.d("url" ,params.toString());

// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new com.android.volley.Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            JSONObject user = response.getJSONObject("user");


                                // user successfully logged in
                                // Create login session

                                // Now store the user in SQLite

                                String id = user.getString("id");
                                String first_name = user.getString("firstName");
                                String last_name = user.getString("lastName");
                                Toast.makeText(context, "Welcome " + first_name + " " + last_name, Toast.LENGTH_LONG).show();

                                Log.d(TAG, "Welcome " + first_name + " " + last_name);
                                volleyCallbackUserLogin.onSuccessResponse(id, email, password);
                                // Inserting row in users table


                        } catch (JSONException e) {
                            e.printStackTrace();
                            volleyCallbackUserLogin.onFail(e.getMessage());
                            Log.d(TAG, e.getMessage());

                        }


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String message = null;
                        if (error instanceof NetworkError) {
                            Log.d(TAG, "onErrorResponse: "+ error.networkResponse.statusCode );
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ServerError) {
                            Log.d(TAG, "onErrorResponse: "+ error.networkResponse.statusCode );

                            message = "The server could not be found. Please try again after some time!!";
                        } else if (error instanceof AuthFailureError) {
                            Log.d(TAG, "onErrorResponse: "+ error.networkResponse.statusCode );

                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof ParseError) {
                            Log.d(TAG, "onErrorResponse: "+ error.networkResponse.statusCode );

                            message = "Parsing error! Please try again after some time!!";
                        } else if (error instanceof NoConnectionError) {

                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (error instanceof TimeoutError) {
                            Log.d(TAG, "onErrorResponse: "+ error.networkResponse.statusCode );

                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        Log.d(TAG, "onErrorResponse: "+message);

                    int status  =   error.networkResponse.statusCode ;
                    switch (status) {
                        case 404 :
                            Log.d("not found"  , "not found");
                        case 400 :
                            Log.d("USER not found"  , "not found");
                    }
                        error.getStackTrace();
                        Log.d(TAG, "onErrorResponse: ");
                    }
                }

        );
        queue.add(getRequest);

    }
    public interface VolleyCallbackUserLogin {
        void onSuccessResponse(String id , String email,   String password);
        void onFail(String msg);
    }

}