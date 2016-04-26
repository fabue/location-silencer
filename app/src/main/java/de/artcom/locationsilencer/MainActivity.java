package de.artcom.locationsilencer;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int LOCATION_UPDATE_INTERVAL = 5000;
    public static final int LOCATION_UPDATE_FASTEST_INTERVAL = 2000;

    private GoogleApiClient mGoogleApiClient;
    private Location mSavedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        ToggleButton activateGeofence = (ToggleButton) findViewById(R.id.activateGeofence);
        activateGeofence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "Geofence active");
                } else {
                    Log.d(TAG, "Geofence inactive");
                }
            }
        });
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void getLocationUpdatesClicked(View view) {
        Log.d(TAG, "getLocationUpdatesClicked");

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_UPDATE_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        ((TextView) findViewById(R.id.currentLatitude)).setText(latitude);
        ((TextView) findViewById(R.id.currentLongitude)).setText(longitude);
    }

    public void saveLocationClicked(View view) {
        Log.d(TAG, "saveLocationClicked");

        mSavedLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        String latitude = String.valueOf(mSavedLocation.getLatitude());
        String longitude = String.valueOf(mSavedLocation.getLongitude());
        ((TextView) findViewById(R.id.savedLatitude)).setText(latitude);
        ((TextView) findViewById(R.id.savedLongitude)).setText(longitude);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed");
    }

}
