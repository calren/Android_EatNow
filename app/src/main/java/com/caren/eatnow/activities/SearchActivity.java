package com.caren.eatnow.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
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

import java.util.ArrayList;
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
    Location mCurrentLocation;
    String currentZipCode;


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

        mLocationClient = new LocationClient(this, this, this);

        setUpListeners();

    }

    public void setUpListeners() {
        btnSearch.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (etLocation.getText().toString().length() > 5) {
                    pbLoading.setVisibility(ProgressBar.VISIBLE);
                    new YelpAPI(SearchActivity.this).search(getSelectedQuery(), etLocation.getText().toString());
                } else {
                    Toast toast = Toast.makeText(getBaseContext(), "Where are you eating?", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }


            }
        });

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

                alert.setPositiveButton("GO", new DialogInterface.OnClickListener() {
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
        mCurrentLocation = mLocationClient.getLastLocation();
        Geocoder gCoder = new Geocoder(this);
        List<Address> addresses = new ArrayList<Address>();
        try {
            addresses = gCoder.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
        } catch (Exception e ) {
        }

        if (addresses != null && addresses.size() > 0) {
            etLocation.setText(addresses.get(0).getAddressLine(1));
        }
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(this, "Location disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(
                        this,
                        1000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {

            System.out.println(connectionResult.getErrorCode());
        }
    }



}
