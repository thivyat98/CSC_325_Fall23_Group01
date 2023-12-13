package com.example.csc325.csc325.users;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * The base class representing a user in the system.
 */
public abstract class User {
    private final String id;
    private String email;
    private String phone;
    private String type;
    private String about = "";

    /**
     * Default constructor for the User class.
     * Initializes the user attributes with default values.
     */
    public User() {
        this.type = "";
        this.email = "";
        this.id = "";
        this.phone = "";
        this.about = "";
    }

    /**
     * Constructor for the User class with email, phone, and type parameters.
     *
     * @param email The user's email address.
     * @param phone The user's phone number.
     * @param type  The type of the user (employee or employer).
     */
    public User(String email, String phone, String type) {
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructor for the User class with email, id, phone, and type parameters.
     *
     * @param email The user's email address.
     * @param id    The unique identifier for the user.
     * @param phone The user's phone number.
     * @param type  The type of the user (employee or employer).
     */
    public User(String email, String id, String phone, String type) {
        this.type = type;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return The user's unique identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The new email address for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Abstract method to save the user's data.
     *
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public abstract void save() throws ExecutionException, InterruptedException;

    /**
     * Abstract method to register the user with a password.
     *
     * @param password The password to register the user.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public abstract void register(String password) throws ExecutionException, InterruptedException;

    /**
     * Gets the phone number of the user.
     *
     * @return The user's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phone The new phone number for the user.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the type of the user.
     *
     * @return The type of the user (employee or employer).
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the user.
     *
     * @param type The new type for the user (employee or employer).
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets additional information about the user.
     *
     * @return Additional information about the user.
     */
    public String getAbout() {
        return about;
    }

    /**
     * Sets additional information about the user.
     *
     * @param about Additional information about the user.
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * Provides a string representation of the user.
     *
     * @return A string representation of the user.
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + id +
                '}';
    }
}
