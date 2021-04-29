package com.example.mislugaresfavoritos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UpdatePlace extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    Place place;
    private EditText name;
    private EditText description;
    private RatingBar ratingBar;
    private Button btnUpdate;
    private GoogleMap mapGoogle;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        place = (Place) i.getSerializableExtra("place");
        if (place != null) {
            setContentView(R.layout.activity_create);
            TextView headTitle = findViewById(R.id.create_text_logo);
            headTitle.setText(R.string.update_place_logo);
            name = (EditText) findViewById(R.id.create_name_new_place);
            description = (EditText) findViewById(R.id.create_description_new_place);

            btnUpdate = (Button) findViewById(R.id.create_btn_new_place);
            btnUpdate.setText(R.string.update_place);
            btnUpdate.setOnClickListener(onClickBtnCreate);

            ratingBar = (RatingBar) findViewById(R.id.create_rating);
            ratingBar.setOnRatingBarChangeListener(onClickRating);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            addDataToForm();
        } else {
            Intent intent = new Intent(this, PlaceList.class);
            startActivity(intent);
        }
    }

    private void addDataToForm() {
    }

    View.OnClickListener onClickBtnCreate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isValid())
                update();
        }
    };

    private boolean isValid() {
        if (name.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private void update() {
        Place place = new Place(this.place.getId(), name.getText().toString(), description.getText().toString().isEmpty() ? null : description.getText().toString(), latLng.toString(), ratingBar.getRating(), this.place.getDate_create());
        PlaceModel placeModel = new PlaceModel(new DBHelper(this, DBHelper.TABLE_NAME, null, 1), place);
        if(placeModel.update()) {
            Intent i = new Intent(this, PlaceList.class);
            startActivity(i);
        }else{
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.error_create_place), Toast.LENGTH_SHORT);
            toast1.show();
        }
    }

    RatingBar.OnRatingBarChangeListener onClickRating = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            Log.e("RATING", String.valueOf(rating));
        }
    };

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mapGoogle = googleMap;
        addStartData();
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        mapGoogle.clear();
        String title = getString(R.string.nuevo_lugar_title);
        if (!name.getText().toString().isEmpty()) {
            title = name.getText().toString();
        }
        this.latLng = latLng;
        mapGoogle.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        mapGoogle.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    private void addStartData(){
        String[] coordinats = place.getLocalization().replace("(", "").replace(")", "").replace("lat/lng:", "").split(",");
        double latitude = Double.parseDouble(coordinats[0]);
        double longitude = Double.parseDouble(coordinats[1]);

        name.setText(place.getName());
        description.setText(place.getDescription());
        ratingBar.setRating((float) place.getRating());
        btnUpdate.setEnabled(true);

        latLng = new LatLng(latitude, longitude);
        mapGoogle.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
        mapGoogle.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }
}