package com.github.aymenmokthari.model;

import java.io.Serializable;

public class ListContact implements Serializable {
    private  int id ;
    private  String name ;
    private  int contactsCount ;

    public ListContact(int id, String name, int contactsCount) {
        this.id = id;
        this.name = name;
        this.contactsCount = contactsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactsCount() {
        return contactsCount;
    }

    public void setContactsCount(int contactsCount) {
        this.contactsCount = contactsCount;
    }
}
