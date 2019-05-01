package com.github.aymenmokthari;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.github.aymenmokthari.helper.Helper;
import com.github.aymenmokthari.model.Contact;
import com.github.aymenmokthari.tools.Session;

import java.util.ArrayList;
import java.util.Collection;

public class ListContactActivity extends AppCompatActivity {
    ListView contactList;
    String authToken;
    SharedPreferences prefs;
    Session session ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);
        contactList  = findViewById(R.id.list_view);


        ArrayList<Contact> contacts = new ArrayList<>();
        session = new Session( this );
        prefs = this.getSharedPreferences("myapp", this.MODE_PRIVATE);


        authToken =    prefs.getString( "authToken" ,"");

        Helper helper ;
        helper = new Helper( this );
        Bundle bundle = getIntent().getExtras();
        int listid =     bundle.getInt("listId");
        helper.GetListContacts(authToken, listid, new Helper.VolleyCallbackGetListContacts() {
            @Override
            public void onSuccessResponse(ArrayList<Contact> result) {
                contactList.setAdapter(new ContactAdapter(getBaseContext() , R.layout.contact_item , result));

            }

            @Override
            public void onFail(String msg) {

            }
        });


    }
}
