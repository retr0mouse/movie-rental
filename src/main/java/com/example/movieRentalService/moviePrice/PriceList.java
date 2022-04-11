package com.example.movieRentalService.moviePrice;

public class PriceList {
    private NewMoviePrice newMoviePrice;
    private RegularMoviePrice regularMoviePrice;
    private OldMoviePrice oldMoviePrice;

    public NewMoviePrice getNewMoviePrice() {
        return newMoviePrice;
    }

    public void setNewMoviePrice(NewMoviePrice newMoviePrice) {
        this.newMoviePrice = newMoviePrice;
    }

    public RegularMoviePrice getRegularMoviePrice() {
        return regularMoviePrice;
    }

    public void setRegularMoviePrice(RegularMoviePrice regularMoviePrice) {
        this.regularMoviePrice = regularMoviePrice;
    }

    public OldMoviePrice getOldMoviePrice() {
        return oldMoviePrice;
    }

    public void setOldMoviePrice(OldMoviePrice oldMoviePrice) {
        this.oldMoviePrice = oldMoviePrice;
    }

    public PriceList(NewMoviePrice newMoviePrice, RegularMoviePrice regularMoviePrice, OldMoviePrice oldMoviePrice) {
        this.newMoviePrice = newMoviePrice;
        this.regularMoviePrice = regularMoviePrice;
        this.oldMoviePrice = oldMoviePrice;
    }
}
