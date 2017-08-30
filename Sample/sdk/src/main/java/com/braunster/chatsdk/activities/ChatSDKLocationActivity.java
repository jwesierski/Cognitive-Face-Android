/*
 * Created by Itzik Braun on 12/3/2015.
 * Copyright (c) 2015 deluge. All rights reserved.
 *
 * Last Modification at: 3/12/15 4:27 PM
 */

package com.braunster.chatsdk.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.braunster.chatsdk.R;
import com.braunster.chatsdk.Utils.Debug;
import com.braunster.chatsdk.Utils.ImageUtils;
import com.braunster.chatsdk.dao.core.DaoCore;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

import timber.log.Timber;

/**
 * Created by braunster on 19/06/14.
 */
public class ChatSDKLocationActivity extends FragmentActivity {

    public static final String TAG = ChatSDKLocationActivity.class.getSimpleName();
    public static final boolean DEBUG = Debug.LocationActivity;

    public static final String ERROR = "Error";
    public static final String ERROR_SNAPSHOT = "error getting snapshot";
    public static final String ERROR_SAVING_IMAGE = "error saving image";

    private GoogleMap map;
    private Button btnSendLocation;
    private Marker selectedLocation;

    public static final String LANITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String SNAP_SHOT_PATH = "snap_shot_path";
    public static final String ZOOM = "zoom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_sdk_activity_locaction);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        initMap();
                    }
                });

//        if (map == null)
//        {
//            reportError("Map is null");
//            return;
//        }
        //initMap();


    }

    private void initMap() {
        // Add find my location button
        map.setMyLocationEnabled(true);

        btnSendLocation = (Button) findViewById(R.id.chat_sdk_btn_send_location);

        btnSendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLocationSnapshot();
            }
        });
        setLocation(getUserLocation());

    }

    private void createLocationSnapshot() {
        takeSnapShot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                Bitmap bitmapLocation = snapshot;
                try {
                    File savedFile = File.createTempFile(DaoCore.generateEntity(), ".jpg", ChatSDKLocationActivity.this.getCacheDir());

                    ImageUtils.saveBitmapToFile(savedFile, bitmapLocation);
//                            File savedFile = Utils.LocationImageHandler.saveLocationImage(ChatSDKLocationActivity.this, bitmapLocation, null);
                    if (savedFile == null)
                        reportError(ERROR_SAVING_IMAGE);
                    else
                        reportSuccess(savedFile);

                } catch (Exception e) {
                    e.printStackTrace();
                    reportError(ERROR_SNAPSHOT);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (map == null)
            return;

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (selectedLocation != null) {
                    selectedLocation.remove();
                    selectedLocation = null;
                }

                // TODO show the adress of that position http://stackoverflow.com/questions/9409195/how-to-get-complete-address-from-latitude-and-longitude
                selectedLocation = map.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            }
        });


    }

    private void reportError(String error) {
        if (DEBUG) Timber.e("Failed");
        Intent intent = new Intent();
        intent.putExtra(ERROR, error);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void reportSuccess(File file) {
        // Reporting to the caller activity of the location picked and the snapshot image taken file location.
        Intent intent = new Intent();

        // If no marker has been added send the user current location.
        if (selectedLocation == null) {



            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }



            intent.putExtra(LANITUDE, 0) ;
            intent.putExtra(LONGITUDE, 0);
        }
        else
        {
            intent.putExtra(LANITUDE, selectedLocation.getPosition().latitude);
            intent.putExtra(LONGITUDE, selectedLocation.getPosition().longitude);
        }

        intent.putExtra(SNAP_SHOT_PATH, file.getPath());
        intent.putExtra(ZOOM, map.getCameraPosition().zoom);

        setResult(RESULT_OK, intent);
        finish();
    }

    private LatLng getUserLocation(){
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        return new LatLng(latitude,longitude);
    }

    private void setLocation(Location location){
        setLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void setLocation(LatLng location){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)      // Sets the center of the map to Mountain View
                .zoom(13)                   // Sets the zoom
                .build();
                            // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void takeSnapShot(GoogleMap.SnapshotReadyCallback callback){
        map.snapshot(callback);
    }
}
