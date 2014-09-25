package com.caren.eatnow.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caren.eatnow.R;
import com.caren.eatnow.models.YelpAPI;
import com.caren.eatnow.models.YelpBusiness;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class SearchActivity extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    TextView tvLunch;
    TextView tvDinner;
    TextView tvCustom;
    Button btnSearch;
    EditText etLocation;
    ProgressBar pbLoading;
    LocationClient mLocationClient;


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
        mLocationClient = new LocationClient(this, this, this);


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

        return "food";

    }

    public void resetAll() {
        query = -1;
        tvLunch.setBackgroundResource(R.drawable.query_circle_unselected);
        tvDinner.setBackgroundResource(R.drawable.query_circle_unselected);
        tvCustom.setBackgroundResource(R.drawable.query_circle_unselected);
        tvCustom.setText("Custom");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        pbLoading.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        Location mCurrentLocation = mLocationClient.getLastLocation();
        Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
        LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(this, "Location disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        1000);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            System.out.println(connectionResult.getErrorCode());
        }
    }



}
