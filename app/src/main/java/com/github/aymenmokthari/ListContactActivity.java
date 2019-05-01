package com.github.aymenmokthari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.github.aymenmokthari.model.Contact;

import java.util.ArrayList;
import java.util.Collection;

public class ListContactActivity extends AppCompatActivity {
    ListView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);
        contactList  = findViewById(R.id.list_view);
        ArrayList<Contact> contacts = new ArrayList<>();

        contacts.add(new Contact("sdf" , "sdfq" , "fdqsf" ));
        contacts.add(new Contact("sdf" , "sdfq" , "fdqsf" ));
        contacts.add(new Contact("sdf" , "sdfq" , "fdqsf" ));
        contacts.add(new Contact("sdf" , "sdfq" , "fdqsf" ));

        contactList.setAdapter(new ContactAdapter(this , R.layout.contact_item , contacts));
    }
}
