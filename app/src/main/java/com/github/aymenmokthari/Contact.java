package com.github.aymenmokthari;

public class Contact {
    private  String firstName ;
    private  String lastName ;
    private  String email ;
    private  String img ;


    public Contact(String firstName, String lastName, String email, String img) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.img = img;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
