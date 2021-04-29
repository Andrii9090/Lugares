package com.example.mislugaresfavoritos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

public class PlaceModel {
    private Place place;
    DBHelper dbHelper;
    ContentValues cv;

    PlaceModel(DBHelper dbHelper, Place place){
        this.place = place;
        this.dbHelper = dbHelper;
        cv = new ContentValues();
        getContentValues();
    }

    public long create(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(DBHelper.TABLE_NAME, null, cv);

        if(id>0){
            return id;
        }else {
            return 0;
        }
    }

    public boolean update(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(DBHelper.TABLE_NAME, cv, "id=?", new String[]{String.valueOf(place.getId())});
        return 1 > 0;
    }

    public boolean delete(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DBHelper.TABLE_NAME, "id=?", new String[]{String.valueOf(place.getId())}) > 0;
    }



    private void getContentValues(){
        cv.put(DBHelper.COL_NAME, place.getName());
        cv.put(DBHelper.COL_DESCRIPTION, place.getDescription());
        cv.put(DBHelper.COL_LOCALIZATION, place.getLocalization());
        cv.put(DBHelper.COL_DATE_CREATE, place.getDate_create());
        cv.put(DBHelper.COL_RATING, place.getRating());
    }
}
