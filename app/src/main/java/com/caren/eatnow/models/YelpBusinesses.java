package com.caren.eatnow.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caren on 9/21/14.
 */
public class YelpBusinesses {

    private static List<YelpBusiness> results = new ArrayList<YelpBusiness>();

    public List<YelpBusiness> getResults() {
        return results;
    }

    public void setResults(List<YelpBusiness> results) {
        this.results = results;
    }

}
