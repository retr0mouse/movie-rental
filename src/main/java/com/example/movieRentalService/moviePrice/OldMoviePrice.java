package com.example.movieRentalService.moviePrice;

/**
 * A class to represent a price for old movie rental
 */
public class OldMoviePrice {
    private String title = "Old Movie";
    private float price = 1.99f;

    public OldMoviePrice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OldMoviePrice(String title, float price) {
        this.title = title;
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
                ", price=" + price +
                '}';
    }
}
