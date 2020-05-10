package com.home.atm;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

// 當 Map 準備好的時候通知
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION = 100;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;

    // onCreate 方法不能進行 Map 操作
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        // Google Map 採非同步呼叫
        mapFragment.getMapAsync(this);
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
    // onMapReady 方法，已取得 Map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        setupMap();
    }

    private void setupMap() {
        // 建立經緯度物件
        // new LatLng(Latitude 經度, Longitude 緯度)
        LatLng taipei101 = new LatLng(25.034641, 121.564738);
        // 設定地圖移到指定的經緯
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taipei101, 17));
        // 新增地圖標記, 設定標記位置, 標題
        mMap.addMarker(new MarkerOptions()
                .position(taipei101)
                .title("Taipei 101"));

        // 取得定位資訊，要先取得使用者同意
        // 權限檢查機制
        if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.GET_PERMISSIONS
//            &&
//            ActivityCompat.checkSelfPermission(this,
//            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.GET_PERMISSIONS
        ) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            // 建立 FusedLocationProviderClient 物件，存取位置資訊
            FusedLocationProviderClient client =
                    LocationServices.getFusedLocationProviderClient(this);

            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        // 取得 Location 結果, location 物件含經緯度資訊
                        Location location =  task.getResult();

                        LatLng myLatLng = new LatLng(location.getLatitude(),
                                location.getLongitude());


                        mMap.addMarker(new MarkerOptions()
                                .position(myLatLng).title("Zhong-shan Station"));
                    }
                }
            });
        }
    }
}
