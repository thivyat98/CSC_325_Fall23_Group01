package com.example.csc325.csc325.users;


import java.util.Objects;

// Define a User class to represent user data
public abstract class User {


    private String id;
    private String name;
    private String email;

    // Constructor, getters, and setters for User class
    // ...


    public User() {
        this.name = "";
        this.id = "";
    }

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public abstract String getUserType();
    public abstract void save();

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}