package example.comtest.cloudsensor_android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by AlbinSkola on 2017-05-11.
 */

public class LocationHandler implements LocationListener {

    private TextView locationText;
    private TextView locationListenText;

    private final Context mContext;

    private static final int REQUEST_LOCATION = 0;

    boolean isListenerEnabled = false;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location; // location

    double latitude; // latitude
    double longitude; // longitude
    private int INDEX_SIZE = 20;
    private int count = 0;
    private double[] latArr = new double[INDEX_SIZE];
    private double[] longArr = new double[INDEX_SIZE];


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 5000; // 10sec

    // Declaring a Location Manager
    protected LocationManager mLocationManager;

    public LocationHandler(Context context) {
        this.mContext = context;
        mLocationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);


        //Check Permission
        if(requestLocationPermission()==true) {
            try{
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }catch(SecurityException ex){
                String msg = "SecurityException";

                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void regListener(){
        //Check Permission
        if(requestLocationPermission()==true) {
            try{
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                isListenerEnabled = true;
            }catch(SecurityException ex){
                String msg = "SecurityException";

                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void unregListener(){
        mLocationManager.removeUpdates(this);

        isListenerEnabled = false;

        String msg = "Stopped LocationRequest";

        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    public double getLongtitude() {

        return longitude;
    }

    public double getLatitude() {

        return latitude;
    }

    public boolean getListenerStatus() {

        return isListenerEnabled;
    }

    @Override
    public void onLocationChanged(Location location) {

        // ((Activity)mContext) use that to point at the TextView from MainActivity
        locationText = (TextView)((Activity)mContext).findViewById(R.id.locationText);

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        if(count<INDEX_SIZE){
            latArr[count] = latitude;
            longArr[count] = longitude;
            count++;
        }else{
            Toast.makeText(mContext, "POSTING LOCATION DATA", Toast.LENGTH_SHORT).show();
            PostData pad = new PostData(mContext);

            String loggedEmail = PreferenceManager.getDefaultSharedPreferences(mContext).getString("EMAIL", "FAILED");

            pad.postLocation(loggedEmail, latArr, longArr);
            count = 0;
        }

        String tmpLat = Double.toString(latitude);
        String tmpLong = Double.toString(longitude);

        locationText.setText("Latitude: " + tmpLat + " Longitude: " + tmpLong );
    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mContext.startActivity(intent);
        Toast.makeText(mContext, "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(mContext, "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    private boolean requestLocationPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(mContext, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, ACCESS_FINE_LOCATION)) {

            Snackbar.make(((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content),
                    ((Activity)mContext).getResources().getString(R.string.location_permission_rationale), Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions((Activity) mContext, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        }
                    });
        } else {
            requestPermissions((Activity) mContext, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        return false;
    }
}
