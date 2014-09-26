package com.caren.eatnow.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.caren.eatnow.R;
import com.caren.eatnow.fragments.BrowseFragment;
import com.caren.eatnow.fragments.EndOfResultsFragment;
import com.caren.eatnow.models.YelpAPI;
import com.caren.eatnow.models.YelpBusiness;
import com.caren.eatnow.helpers.ImageHelpers;
import com.caren.eatnow.models.YelpBusinesses;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends FragmentActivity {

    private RelativeLayout ivYes;
    private RelativeLayout ivNo;
    private ProgressBar pbLoading;

    private YelpBusiness b;
    private int numOfResultToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);

        numOfResultToShow = getIntent().getIntExtra("numOfResult", -1);

        setUpItems();

        List<YelpBusiness> results = new YelpBusinesses().getResults();

        if (results.size() > 0) {
            b = results.get(numOfResultToShow);
            startFragment();
        } else {
            Toast.makeText(this, "No results for your search... try something else!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(BrowseActivity.this, SearchActivity.class);
            startActivity(i);
        }
    }

    private void startFragment() {
        if (numOfResultToShow <= 5) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            BrowseFragment fragmentBrowse = BrowseFragment.newInstance(numOfResultToShow);
            ft.setCustomAnimations(R.anim.left_out, R.anim.right_in);
            ft.replace(R.id.fragmentPlaceHolder, fragmentBrowse);
            ft.commit();
        } else {
            ivYes.setVisibility(View.INVISIBLE);
            ivNo.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            EndOfResultsFragment fragmentEnd = new EndOfResultsFragment();
            ft.setCustomAnimations(R.anim.left_out, R.anim.right_in);
            ft.replace(R.id.fragmentPlaceHolder, fragmentEnd);
            ft.commit();
        }

    }

    private void setUpItems() {
        ivYes = (RelativeLayout) findViewById(R.id.rlYes);
        ivNo = (RelativeLayout) findViewById(R.id.rlNo);
    }

    public void onClickNo(View view) {
        if (numOfResultToShow <= 5) {
            numOfResultToShow++;
            startFragment();
        }
    }

    public void onClickYes(View view) {
        if (numOfResultToShow <= 5) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=" + b.getAddress() + " " + b.getCity()));
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        pbLoading.setVisibility(ProgressBar.VISIBLE);
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        pbLoading.setVisibility(ProgressBar.INVISIBLE);
        ivYes.setVisibility(View.VISIBLE);
        ivNo.setVisibility(View.VISIBLE);

    }
}
