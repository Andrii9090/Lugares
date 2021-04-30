package com.example.mislugaresfavoritos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class PlaceDetail extends AppCompatActivity implements OnMapReadyCallback {
    TextView name;
    TextView description;
    TextView dateCreate;
    RatingBar rating;
    GoogleMap googleMap;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        Intent i = getIntent();
        place = (Place) i.getSerializableExtra("place");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        name = findViewById(R.id.place_detail_value_name);
        description = findViewById(R.id.place_detail_value_description);
        dateCreate = findViewById(R.id.place_detail_value_date_create);
        rating = findViewById(R.id.place_detail_value_rating);

    }

    private void addDataToView() {
        name.setText(place.getName());
        description.setVisibility(View.VISIBLE);
        description.setText(place.getDescription());
        dateCreate.setText(place.getDateFormat());
        rating.setRating((float) place.getRating());

        String[] coordinats = place.getLocalization().replace("(", "").replace(")", "").replace("lat/lng:", "").split(",");
        double latitude = Double.parseDouble(coordinats[0]);
        double longitude = Double.parseDouble(coordinats[1]);

        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        addDataToView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_place_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.createPlace:
                i = new Intent(this, Create.class);
                startActivity(i);
                return true;
            case R.id.openPlaceList:
                i = new Intent(this, PlaceList.class);
                startActivity(i);
                return true;
            case R.id.deletePlace:
                deletePlace();
                return true;
            case R.id.logOut:
                i = new Intent(this, MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("misLugares", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logged", false);
                editor.apply();
                startActivity(i);
                return true;
            case R.id.changePlace:
                i = new Intent(this, UpdatePlace.class);
                i.putExtra("place", (Serializable) place);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deletePlace() {
        DBHelper dbHelper = new DBHelper(this,DBHelper.TABLE_NAME, null, 1);
        PlaceModel placeModel = new PlaceModel(dbHelper, place);

        if(placeModel.delete()){
            Intent i = new Intent(this, PlaceList.class);
            startActivity(i);
        }else{
            Toast t = Toast.makeText(getApplicationContext(),R.string.error_delete_place, Toast.LENGTH_LONG);
            t.show();
        }
    }
}