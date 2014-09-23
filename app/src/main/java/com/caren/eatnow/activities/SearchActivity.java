package com.caren.eatnow.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.caren.eatnow.R;
import com.caren.eatnow.models.YelpAPI;
import com.caren.eatnow.models.YelpBusiness;

import java.util.List;

public class SearchActivity extends Activity {

    TextView tvLunch;
    TextView tvDinner;
    TextView tvCustom;
    Button btnSearch;
    EditText etLocation;
    ProgressBar pbLoading;

    String customQuery = "Custom";
    int query = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tvLunch = (TextView) findViewById(R.id.tvLunch);
        tvDinner = (TextView) findViewById(R.id.tvDinner);
        tvCustom = (TextView) findViewById(R.id.tvCustom);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        etLocation = (EditText) findViewById(R.id.etLocation);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);

        setUpListeners();

        btnSearch.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                pbLoading.setVisibility(ProgressBar.VISIBLE);
                new YelpAPI(SearchActivity.this).search(getSelectedQuery(), etLocation.getText().toString());

            }
        });
    }

    public void setUpListeners() {

        tvLunch.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged = false;

            public void onClick(View view) {
                setSelected(1);
            }
        });

        tvDinner.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged = false;

            public void onClick(View view) {
                setSelected(2);
            }
        });

        tvCustom.setOnClickListener(new View.OnClickListener() {
            private boolean stateChanged = false;
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

                alert.setTitle("Custom Search");
                alert.setMessage("(eg: sushi, burgers)");

                final EditText input = new EditText(view.getContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        customQuery = input.getText().toString();
                        tvCustom.setText(customQuery);
                        stateChanged = true;
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();

                setSelected(3);
            }
        });


    }


    public void setSelected(int selected) {
        if (query != selected) {
            switch (selected) {
                case 1:
                    resetAll();
                    tvLunch.setBackgroundResource(R.drawable.query_circle_selected);
                    query = 1;
                    break;
                case 2:
                    resetAll();
                    tvDinner.setBackgroundResource(R.drawable.query_circle_selected);
                    query = 2;
                    break;
                case 3:
                    resetAll();
                    tvCustom.setBackgroundResource(R.drawable.query_circle_selected);
                    query = 3;
                    break;
            }
        } else {
            resetAll();
        }

    }

    public String getSelectedQuery() {
        switch (query) {
            case 1:
                return "lunch";
            case 2:
                return "dinner";
            case 3:
                return customQuery;
        }

        return "";

    }

    public void resetAll() {
        query = -1;
        tvLunch.setBackgroundResource(R.drawable.query_circle_unselected);
        tvDinner.setBackgroundResource(R.drawable.query_circle_unselected);
        tvCustom.setBackgroundResource(R.drawable.query_circle_unselected);
        tvCustom.setText("Custom");
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

    @Override
    public void onResume() {
        super.onResume();
        pbLoading.setVisibility(ProgressBar.INVISIBLE);
    }
}
