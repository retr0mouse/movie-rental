package com.example.movieRentalService.moviePrice;

public class NewMoviePrice {
    private String title = "New Movie";
    private int duration;
    private float price = 5.0f;

    public NewMoviePrice() {
    }

    public NewMoviePrice(String title, int duration, float price) {
        this.title = title;
        this.duration = duration;
        this.price = price;
    }

    public NewMoviePrice(int duration) {
        this.duration = duration;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "NewMoviePrice{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }
}
