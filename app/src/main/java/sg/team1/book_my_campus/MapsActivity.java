package sg.team1.book_my_campus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import sg.team1.book_my_campus.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {
    String title = "mapactivity";
    Boolean PermissionGranted;

    private GoogleMap mMap;
    Location currentLocation;
    private ActivityMapsBinding binding;
    Button button,buttonRoom;

    FusedLocationProviderClient mLocationClient;
    SupportMapFragment supportMapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String roomName = getIntent().getExtras().getString("RoomName");
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mLocationClient = (FusedLocationProviderClient) LocationServices.getFusedLocationProviderClient(this);

        checkPermission();
        initialise();
        button = findViewById(R.id.buttonDirection);
        buttonRoom = findViewById(R.id.buttonDirect2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLoc();
            }
        });

        buttonRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(roomName.matches("iSpace"))
              {
                  gotoLocation(1.3337153060893068, 103.77680598179548);
              }
              else if (roomName.matches("Smart Room 1") || roomName.matches("Smart Room 2") || roomName.matches("Smart room 3") || roomName.matches("Smart room 4")){
                  gotoLocation(1.334247772539461, 103.77549871381547);
              }

            }
        });



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String roomName = getIntent().getExtras().getString("RoomName");
        // Add a marker in NP and move the camera

        LatLng NP = new LatLng(1.33242473248, 103.777698548);
        LatLng ISpace = new LatLng(1.3337153060893068, 103.77680598179548);
        LatLng Smartroom = new LatLng(1.334247772539461, 103.77549871381547);

        if (roomName.matches("iSpace")) {

            //mMap.addMarker(new MarkerOptions().position(NP).title("NP Entrance"));
            Log.v(title, "onsucess " + roomName);
            mMap.addMarker(new MarkerOptions().position(ISpace).title("iSpace rooms"));
        }
        if (roomName.matches("Smart Room 1") || roomName.matches("Smart Room 2") || roomName.matches("Smart room 3") || roomName.matches("Smart room 4")) {

            //mMap.addMarker(new MarkerOptions().position(NP).title("NP Entrance"));
            mMap.addMarker(new MarkerOptions().position(Smartroom).title("Smart Rooms"));

        }


        mMap.moveCamera(CameraUpdateFactory.newLatLng(NP));
        //add plus minus to zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //add compass to move around
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);

    }

    public void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(MapsActivity.this, "Location Services Enabled", Toast.LENGTH_SHORT).show();
                PermissionGranted = true;



            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MapsActivity.this, "Please Enable Location Services", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }

    public void initialise() {
        if (PermissionGranted = true) {


                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);


        }

    }
    /*public boolean GpsEnabled()
    {
        LocationManager locationManager =(LocationManager) getSystemService(LOCATION_SERVICE);
    }*/

    public void getCurrentLoc() {
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
        Task<Location> task = mLocationClient.getLastLocation();
        mMap.setMyLocationEnabled(true);
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                            if (location != null) {
                                LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("Current Location");
                                mMap.addMarker(markerOptions);
                                gotoLocation(location.getLatitude(), location.getLongitude());
                            }



                        else{Toast.makeText(MapsActivity.this,"Please enable location services",Toast.LENGTH_SHORT).show();
                            Log.v(title,"hi" +location.getLatitude());
                        }
                    }
                });
            }
        });


        }
        private void gotoLocation(double latitude,double longitude){
        LatLng Latlng = new LatLng(latitude,longitude);
            CameraUpdate cameraUpdate =CameraUpdateFactory.newLatLngZoom(Latlng,18);
            mMap.animateCamera(cameraUpdate);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }



        @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}