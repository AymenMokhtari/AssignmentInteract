package com.github.aymenmokthari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.aymenmokthari.helper.Helper;
import com.github.aymenmokthari.model.Contact;
import com.github.aymenmokthari.model.ListContact;
import com.github.aymenmokthari.tools.Session;

import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {
    ListView listList;
    int idSP;
    String authToken;
    SharedPreferences prefs;
    Session session ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listList  = findViewById(R.id.listLV);
        session = new Session( this );
        prefs = this.getSharedPreferences("myapp", this.MODE_PRIVATE);


        authToken =    prefs.getString( "authToken" ,"");
        Log.d("authToken", "onCreate: "+authToken);






        Helper helper ;






        helper = new Helper( this );

        helper.GetListContacts(authToken, 3549, new Helper.VolleyCallbackGetListContacts() {
            @Override
            public void onSuccessResponse(ArrayList<Contact> result) {

                Log.d("listcontact", "onSuccessResponse: "+result.get(0));
            }

            @Override
            public void onFail(String msg) {
                Log.d("listcontacterror", "onSuccessResponse: "+msg);

            }
        });

        helper.getActiveLists( authToken ,  new Helper.VolleyCallbackGetActiveLists(){
            @Override
            public void onSuccessResponse(ArrayList<ListContact> result) {
                listList.setAdapter( new ListAdapter( getBaseContext() , R.layout.list_item, result ) );
            }

            @Override
            public void onFail(String msg) {
            }
        } );




        listList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ListContact listContact =(ListContact) parent.getItemAtPosition(position);

                Log.d("listcontatformlist", "onItemClick: "+listContact.getId());

                Intent intent = new Intent(ListActivity.this, ListContactActivity.class);
                intent.putExtra("listId",listContact.getId() );
                startActivity(intent);
            }
        });

    }
}
