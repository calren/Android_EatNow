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
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.caren.eatnow.R;
import com.caren.eatnow.fragments.BrowseFragment;
import com.caren.eatnow.models.YelpAPI;
import com.caren.eatnow.models.YelpBusiness;
import com.caren.eatnow.helpers.ImageHelpers;
import com.caren.eatnow.models.YelpBusinesses;
import android.support.v4.app.FragmentActivity;


import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends FragmentActivity {

    private RelativeLayout ivYes;
    private RelativeLayout ivNo;

    private YelpBusiness b;
    private int numOfResultToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        numOfResultToShow = getIntent().getIntExtra("numOfResult", -1);
        b = new YelpBusinesses().getResults().get(numOfResultToShow);

        setUpItems();
        startFragment();
    }

    private void startFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BrowseFragment fragmentBrowse = BrowseFragment.newInstance(numOfResultToShow);
        ft.setCustomAnimations(R.anim.left_out, R.anim.right_in);
        ft.replace(R.id.fragmentPlaceHolder, fragmentBrowse);
        ft.commit();
    }

    private void setUpItems() {

        ivYes = (RelativeLayout) findViewById(R.id.rlYes);
        ivNo = (RelativeLayout) findViewById(R.id.rlNo);
    }

    public void onClickNo(View view) {
        numOfResultToShow++;
        startFragment();
    }

    public void onClickYes(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + "840 battery street san francisco"));
        startActivity(intent);
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
