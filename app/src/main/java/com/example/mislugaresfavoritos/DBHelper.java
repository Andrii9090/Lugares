package com.example.mislugaresfavoritos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    static String TABLE_NAME = "lugares";
    static String COL_ID = "id";
    static String COL_NAME = "name";
    static String COL_DESCRIPTION = "description";
    static String COL_LOCALIZATION = "localization";
    static String COL_RATING = "rating";
    static String COL_DATE_CREATE = "date_create";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (" +
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME+" VARCHAR(255) NOT NULL," +
                COL_DESCRIPTION+" TEXT DEFAULT NULL," +
                COL_LOCALIZATION+" TEXT NOT NULL," +
                COL_DATE_CREATE+" TEXT NOT NULL," +
                COL_RATING+" REAL DEFAULT 5.0) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
