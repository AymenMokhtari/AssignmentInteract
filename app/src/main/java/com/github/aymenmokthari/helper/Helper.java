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
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.aymenmokthari.model.Contact;
import com.github.aymenmokthari.model.ListContact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
                                String authToken = response.getJSONObject("token").getString("authToken");
                                Toast.makeText(context, "Welcome " + first_name + " " + last_name, Toast.LENGTH_LONG).show();

                                Log.d(TAG, "Welcome " + first_name + " " + last_name);
                                volleyCallbackUserLogin.onSuccessResponse(id, email, authToken);
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



    public void getActiveLists ( final String authToken , final VolleyCallbackGetActiveLists callback){


        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = ip_address+"/lists";

        Log.i("link",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            ArrayList<ListContact> listContacts = new ArrayList<>();

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject j = array.getJSONObject(i);
                                listContacts.add( new ListContact(  j.getInt( "id" ), j.getString( "name" ) , j.getInt( "contactsCount" )) );
                                // Log.d( TAG, "onResponse:  "+j.get( "foodname" ) );

                            }
                            callback.onSuccessResponse( listContacts );



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                        callback.onFail( error.getMessage() );
                    }
                }) {


                    @Override
                    public Map<String,String> getHeaders(){
                        HashMap<String, String> headers = new HashMap<String, String>();

                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("authToken",authToken);
                        Log.d(TAG, "getHeaders: "+headers);
                        return headers;
                    }
                };

// add it to the RequestQueue
        queue.add(stringRequest);
    }


    public void GetListContacts ( final String authToken ,int listId, final VolleyCallbackGetListContacts callback){


        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = ip_address+"/lists/"+String.valueOf(listId)+"/contacts";

        Log.i("link",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            ArrayList<Contact> contacts = new ArrayList<>();
                            JSONObject object = new JSONObject(response);

                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++){
                                JSONObject j = array.getJSONObject(i);


                                contacts.add( new Contact(  j.getString( "firstName" ), j.getString( "lastName" ) , j.getJSONArray( "emails" ).getJSONObject(0).getString("email")) );
                                // Log.d( TAG, "onResponse:  "+j.get( "foodname" ) );

                            }
                            callback.onSuccessResponse( contacts );



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                        callback.onFail( error.getMessage() );
                    }
                }) {


            @Override
            public Map<String,String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("authToken",authToken);
                Log.d(TAG, "getHeaders: "+headers);
                return headers;
            }
        };

// add it to the RequestQueue
        queue.add(stringRequest);
    }



    //Volley  callbacks
    public interface VolleyCallbackUserLogin {
        void onSuccessResponse(String id , String email,   String authToken);
        void onFail(String msg);
    }
    public interface VolleyCallbackGetActiveLists {
        void onSuccessResponse(ArrayList<ListContact> result);
        void onFail(String msg);
    }

    public interface VolleyCallbackGetListContacts {
        void onSuccessResponse(ArrayList<Contact> result);
        void onFail(String msg);
    }




}