package com.caren.eatnow.models;

import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


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

    public static void search(String query, String location) {
        final OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
        request.addQuerystringParameter("term", query);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("sort", "2");

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

                    currentList.add(new YelpBusiness(objectB.get("name").toString(),
                            objectB.get("image_url").toString(),
                            objectB.get("location").toString(), //TODO
                            objectB.get("review_count").toString(),
                            objectB.get("rating_img_url_large").toString(),
                            "fake sample description for now", //TODO
                            objectB.get("url").toString()));
                }

                new YelpBusinesses().setResults(currentList);

                return "";

            }

            @Override
            protected void onPostExecute(String url) {
//                mWebView.loadUrl(url);
            }
        }).execute();
//        String rawData = response.getBody();
    }

}