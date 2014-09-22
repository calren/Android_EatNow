package com.caren.eatnow.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.caren.eatnow.R;
import com.caren.eatnow.models.YelpAPI;
import com.caren.eatnow.models.YelpBusiness;

import java.util.List;

public class SearchActivity extends Activity {

    EditText etQuery;
    EditText etLocation;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etQuery = (EditText) findViewById(R.id.etQuery);
        etLocation = (EditText) findViewById(R.id.etLocation);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new YelpAPI(SearchActivity.this).search(etQuery.getText().toString(), etLocation.getText().toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
