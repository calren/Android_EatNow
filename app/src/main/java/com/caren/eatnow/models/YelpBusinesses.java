package com.caren.eatnow.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Caren on 9/21/14.
 */
public class YelpBusinesses {

    private static List<YelpBusiness> results = new ArrayList<YelpBusiness>();

    public List<YelpBusiness> getResults() {
        return results;
    }

    public void setResults(List<YelpBusiness> results) {
        long seed = System.nanoTime();
        Collections.shuffle(results, new Random(seed));
        this.results = results;
    }

}
