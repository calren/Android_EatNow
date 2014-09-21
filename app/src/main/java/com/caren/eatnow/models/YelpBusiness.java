package com.caren.eatnow.models;

/**
 * Created by Caren on 9/20/14.
 */
public class YelpBusiness {

    private String name;
    private String address;
    private String image;
    private String numOfReviews;
    private String rating;

    public YelpBusiness(String name, String address, String image, String numOfReviews, String rating) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.numOfReviews = numOfReviews;
        this.rating = rating;
    }

    public YelpBusiness(String name) {
        this.name = name;
    }

    public YelpBusiness(String name, String rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getImage() {
        return this.image;
    }

    public String getNumOfReviews() {
        return numOfReviews;
    }

    public String getRating() {
        return rating;
    }
}


