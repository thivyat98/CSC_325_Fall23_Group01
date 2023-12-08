package com.example.csc325.csc325.users;


import java.util.Objects;
import java.util.UUID;

// Define a User class to represent user data
public abstract class User {


    private final String id;
    private String email;

    private String password;


    public User() {
        this.password = "";
        this.id = UUID.randomUUID().toString();
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getUserType();
    public abstract void save();
    public abstract void register();



    @Override
    public String toString() {
        return "User{" +
                "id='" + id +
                '}';
    }
}