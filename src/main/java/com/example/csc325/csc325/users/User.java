package com.example.csc325.csc325.users;


import java.util.UUID;
import java.util.concurrent.ExecutionException;

// Define a User class to represent user data
public abstract class User {


    private final String id;
    private String email;

    private String phone;

    private String type;
    private String about = "";


    public User() {
        this.type = "";
        this.email = "";
        this.id = "";
        this.phone = "";
        this.about ="";
    }

    public User(String email, String phone, String type) {
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.id = UUID.randomUUID().toString();
    }

    public User(String email, String id, String phone, String type) {
        this.type = type;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }


    public String getId() {
        return id;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract void save() throws ExecutionException, InterruptedException;

    public abstract void register(String password) throws ExecutionException, InterruptedException;


    @Override
    public String toString() {
        return "User{" +
                "id='" + id +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}