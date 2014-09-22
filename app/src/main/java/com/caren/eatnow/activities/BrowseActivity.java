package com.caren.eatnow.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.caren.eatnow.R;
import com.caren.eatnow.models.YelpBusiness;
import com.caren.eatnow.helpers.ImageHelpers;
import com.caren.eatnow.models.YelpBusinesses;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends Activity {

    private TextView tvName;
    private ImageView ivPicture;
    private TextView tvDistance;
    private ImageView ivRating;
    private TextView tvRatingCount;
    private TextView tvDescription;

    private YelpBusiness b;
    private int numOfResultToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        List<YelpBusiness> results = new YelpBusinesses().getResults();
        numOfResultToShow = getIntent().getIntExtra("numOfResult", -1);

        b = results.get(numOfResultToShow);

        setUpInformation();


    }

    private void setUpInformation() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(b.getName());

        ivPicture = (ImageView) findViewById(R.id.ivImage);
        new ImageHelpers.DownloadImageTask(ivPicture).execute(b.getImage());

        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvDistance.setText(b.getAddress());

        ivRating = (ImageView) findViewById(R.id.ivImage);
        new ImageHelpers.DownloadImageTask(ivRating).execute(b.getRating());

        tvRatingCount = (TextView) findViewById(R.id.tvNumOfReviews);
        tvRatingCount.setText(b.getNumOfReviews());

        tvDescription = (TextView) findViewById(R.id.tvCategory);
        tvDescription.setText(b.getDescription());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
