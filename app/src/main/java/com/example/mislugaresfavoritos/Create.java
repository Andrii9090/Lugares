package com.example.mislugaresfavoritos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.zip.Inflater;

public class Create extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private EditText name;
    private EditText description;
    private RatingBar ratingBar;
    private Button btnCreate;
    private GoogleMap mapGoogle;
    private LatLng latAndLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        name = (EditText) findViewById(R.id.create_name_new_place);
        description = (EditText) findViewById(R.id.create_description_new_place);

        btnCreate = (Button) findViewById(R.id.create_btn_new_place);
        btnCreate.setOnClickListener(onClickBtnCreate);

        ratingBar = (RatingBar) findViewById(R.id.create_rating);
        ratingBar.setOnRatingBarChangeListener(onClickRating);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    View.OnClickListener onClickBtnCreate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isValid())
                create();
        }
    };

    private boolean isValid() {
        if (name.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private void create() {
        Place place = new Place(this, name.getText().toString(), description.getText().toString().isEmpty() ? null : description.getText().toString(), latAndLng.toString(), ratingBar.getRating());
        PlaceModel placeModel = new PlaceModel(new DBHelper(this, DBHelper.TABLE_NAME, null, 1), place);
        if(placeModel.create()>0) {
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
    public void onMapLongClick(@NonNull LatLng latLng) {
        mapGoogle.clear();
        String title = getString(R.string.nuevo_lugar_title);
        if (!name.getText().toString().isEmpty()) {
            title = name.getText().toString();
        }
        latAndLng = latLng;
        btnCreate.setEnabled(true);
        mapGoogle.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapGoogle = googleMap;
        mapGoogle.clear();
        mapGoogle.setOnMapLongClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
            case R.id.logOut:
                i = new Intent(this, MainActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences("misLugares", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logged", false);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}