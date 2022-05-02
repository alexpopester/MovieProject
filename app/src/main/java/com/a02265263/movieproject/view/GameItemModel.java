package com.a02265263.movieproject.view;

public class GameItemModel implements Comparable<GameItemModel> {

    @Override
    public int compareTo(GameItemModel o) {
        if (this.popularity > o.popularity) {
            return -1;
        }
        else if (this.popularity < o.popularity) {
            return 1;
        }
        else {
            return 0;
        }
    }

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
    private double popularity;

    public GameItemModel(String id, String title, String image, String role, Type type, double popularity) {
        this.id = id;
        this.title = title;
        if (this.title.equals("Happiness Isn't Everything")){
            System.out.println("Happiness Isn't Everything");
        }
        if (!image.equals("https://image.tmdb.org/t/p/w500/null")) {
            this.image = "https://image.tmdb.org/t/p/w500" + image;
        }
        else {
            this.image = "null";
        }
        this.role = role;
        this.type = type;
        this.popularity = popularity;
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

    public Type getType() { return type; }

    public double getPopularity() { return popularity; }
}
