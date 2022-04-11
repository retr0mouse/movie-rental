package com.example.movieRentalService.moviePrice;

public class RegularMoviePrice {
    private String title = "Regular Movie";
    private int duration;
    private float price = 3.49f;

    public RegularMoviePrice() {
    }

    public RegularMoviePrice(int duration) {
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

    public RegularMoviePrice(String title, int duration, float price) {
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

    public RegularMoviePrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RegularMoviePrice{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }
}
