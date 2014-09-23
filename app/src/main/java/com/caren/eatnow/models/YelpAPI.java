package com.caren.eatnow.models;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.caren.eatnow.activities.BrowseActivity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;
import java.util.List;

public class YelpAPI {

    private static final String API_HOST = "api.yelp.com";
    private static final String DEFAULT_TERM = "dinner";
    private static final String DEFAULT_LOCATION = "San Francisco, CA";
    private static final int SEARCH_LIMIT = 3;
    private static final String SEARCH_PATH = "/v2/search";
    private static final String BUSINESS_PATH = "/v2/business";

    private static final String CONSUMER_KEY = "FyCeKucvvdYGs1DTawIxWQ";
    private static final String CONSUMER_SECRET = "WR5JSUsb7PASqXfC1105nfvekUo";
    private static final String TOKEN = "FjyLBARfE2t9ZOQXsePSzbgooxW7wjSP";
    private static final String TOKEN_SECRET = "LFR4usUA9wjf8BKiTS6Yx6FnXrU";

    public static final List<YelpBusiness> currentList = new ArrayList<YelpBusiness>();

    private static Activity activity;

    public YelpAPI(Activity activity) {
        this.activity = activity;
    }

    public static void search(String query, String location) {
        final OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
        request.addQuerystringParameter("term", query);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("sort", "2");
        request.addQuerystringParameter("limit", "20");

        callAPI(request);
    }

    public static void callAPI(final OAuthRequest request) {

        OAuthService service = new ServiceBuilder()
                .provider(YelpV2API.class)
                .apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET)
                .build();
        Token accessToken = new Token(TOKEN, TOKEN_SECRET);

        service.signRequest(accessToken, request);
        (new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Response response = request.send();

                String strResponse = response.getBody();

                System.out.println("STRING RESPONSE: " + strResponse);

                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = (JSONObject) parser.parse(strResponse);
                } catch (ParseException pe) {
                    System.out.println("Error: could not parse JSON response:");
                    System.out.println(strResponse);
                    System.exit(1);
                }

                JSONArray businesses = (JSONArray) jsonResponse.get("businesses");

                currentList.clear();

                for (int i = 0; i < businesses.size(); i++) {
                    JSONObject objectB = (JSONObject) businesses.get(i);

                    String categoriesS = "";
                    JSONArray categories= (JSONArray) objectB.get("categories");
                    for (int j = 0; j < categories.size(); j++) {
                        if (j != 0) {
                            categoriesS += ", ";
                        }
                        categoriesS += ((JSONArray) categories.get(j)).get(0).toString().replace(" \" ", "");
                    }

                    String address = "";
                    try {
                        address = ((JSONArray)((JSONObject)objectB.get("location")).get("address")).get(0).toString();
                    } catch (Exception e ) {
                        address = "No address found";
                    }

                    try {
                        address += "\n" + ((JSONObject)objectB.get("location")).get("city").toString();
                    } catch (Exception e) {

                    }

                    currentList.add(new YelpBusiness(objectB.get("name").toString(),
                            objectB.get("image_url").toString(),
                            address,
                            objectB.get("review_count").toString() + " reviews",
                            objectB.get("rating_img_url_large").toString(),
                            categoriesS, //TODO
                            objectB.get("url").toString()));
                }

                new YelpBusinesses().setResults(currentList);

                return "";

            }

            @Override
            protected void onPostExecute(String url) {
                Intent  i =  new Intent(activity, BrowseActivity.class);
                i.putExtra("numOfResult", 0);
                activity.startActivity(i);
            }
        }).execute();
    }

}