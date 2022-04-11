package com.example.movieRentalService.moviePrice;

public class OldMoviePrice {
    private String title = "New Movie";
    private int duration;
    private float price = 1.99f;

    public OldMoviePrice() {
    }

    public OldMoviePrice(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public OldMoviePrice(String title, int duration, float price) {
        this.title = title;
        this.duration = duration;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public OldMoviePrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OldMoviePrice{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }
}
