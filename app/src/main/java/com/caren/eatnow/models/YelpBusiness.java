package com.caren.eatnow.models;

import java.io.Serializable;

/**
 * Created by Caren on 9/20/14.
 */
public class YelpBusiness implements Serializable {

    private String name;
    private String image;
    private String address;
    private String rating;
    private String numOfReviews;
    private String description;
    private String yelpLink;

    public YelpBusiness(String name, String image, String address, String numOfReviews, String rating, String description, String yelpLink) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.numOfReviews = numOfReviews;
        this.rating = rating;
        this.description = description;
        this.yelpLink = yelpLink;
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

    public String getDescription() { return description; }

    public String getYelpLink() { return yelpLink; }
}


