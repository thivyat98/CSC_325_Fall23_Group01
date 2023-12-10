package com.example.csc325.csc325.users;


import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

// Define a User class to represent user data
public abstract class User {


    private final String id;
    private String email;

    private String phone;


    public User() {
        this.id = UUID.randomUUID().toString();
    }

    public User(String email, String phone) {
        this.email = email;
        this.phone = phone;
        this.id = UUID.randomUUID().toString();
    }

    public User(String email, String id, String phone) {
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

    public abstract String getUserType();
    public abstract void save();
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
}