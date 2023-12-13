package com.example.csc325.csc325.Posts;

import java.util.UUID;

/**
 * Abstract class representing a generic post in the system.
 */
public abstract class Post {
    private String title;
    private String description;
    private String id;
    private long unixTime;

    /**
     * Default constructor for the Post class.
     * Initializes post attributes with default values.
     */
    public Post() {
        this.title = "";
        this.description = "";
        this.id = "pst-" + UUID.randomUUID().toString();
        this.unixTime = System.currentTimeMillis() / 1000;
    }

    /**
     * Constructor for creating a post with specified title and description.
     *
     * @param title       The title of the post.
     * @param description The description of the post.
     */
    public Post(String title, String description) {
        this.title = title;
        this.description = description;
        this.id = "pst-" + UUID.randomUUID().toString();
        this.unixTime = System.currentTimeMillis() / 1000;
    }

    /**
     * Gets the title of the post.
     *
     * @return The title of the post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the post.
     *
     * @param title The new title for the post.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the post.
     *
     * @return The description of the post.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the post.
     *
     * @param description The new description for the post.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the unique identifier of the post.
     *
     * @return The post ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the post.
     *
     * @param id The new post ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the Unix timestamp representing the time of the post creation.
     *
     * @return The Unix timestamp.
     */
    public long getUnixTime() {
        return unixTime;
    }

    /**
     * Sets the Unix timestamp representing the time of the post creation.
     *
     * @param unixTime The new Unix timestamp.
     */
    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

    /**
     * Checks whether two posts are equal based on their IDs.
     *
     * @param o The object to compare.
     * @return True if the posts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post that = (Post) o;
        return id.equals(that.getId());
    }

    /**
     * Generates a hash code based on the post's ID.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Abstract method for saving the post data.
     */
    public abstract void save();
}
