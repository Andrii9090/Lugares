package com.example.mislugaresfavoritos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class PlaceList extends AppCompatActivity implements RVAdapterLugares.DataToActivityPlaceList {

    ArrayList<Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_lugar);
        places = new ArrayList<Place>();
        getPlaces();
        if(places.size()>0) {
            TextView listEmpty = (TextView)findViewById(R.id.lista_de_lugar_vacia);
            listEmpty.setVisibility(View.GONE);
            RecyclerView rv = (RecyclerView) findViewById(R.id.lista_de_lugar);
            rv.setVisibility(View.VISIBLE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            rv.setLayoutManager(layoutManager);
            RVAdapterLugares adapterLugares = new RVAdapterLugares(places);
            rv.setAdapter(adapterLugares);
        }


    }

    private void getPlaces() {
        DBHelper dbHelper = new DBHelper(this, DBHelper.TABLE_NAME, null, 1);

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.COL_NAME));
                String dateCreate = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DATE_CREATE));
                String localization = cursor.getString(cursor.getColumnIndex(DBHelper.COL_LOCALIZATION));
                String description = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DESCRIPTION));
                long id = cursor.getLong(cursor.getColumnIndex(DBHelper.COL_ID));
                double rating = cursor.getDouble(cursor.getColumnIndex(DBHelper.COL_RATING));
                places.add(new Place(id, name, description,localization, rating, dateCreate));
            }
        }finally {
            dbHelper.close();
        }

    }

    @Override
    public void getPosition(int position) {
        Intent i = new Intent(this, PlaceDetail.class);
        i.putExtra("place", (Serializable) places.get(position));

        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.removeItem(R.id.openPlaceList);
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
                editor.apply();
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}