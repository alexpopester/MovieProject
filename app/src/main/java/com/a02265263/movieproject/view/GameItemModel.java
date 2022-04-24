package com.a02265263.movieproject.view;

public class GameItemModel {

    public enum Type {
        MOVIE,
        TV,
        PERSON
    }

    private String id;
    private String title;
    private String image;
    private String role;
    private Type type;

    public GameItemModel(String id, String title, String image, String role, Type type) {
        this.id = id;
        this.title = title;
        this.image = "https://image.tmdb.org/t/p/w500" + image;
        this.role = role;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getRole() {
        return role;
    }
}
