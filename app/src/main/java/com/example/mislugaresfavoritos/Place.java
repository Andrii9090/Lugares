package com.example.mislugaresfavoritos;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Place implements Serializable {
    private long id;
    private String name;
    private String description;
    private String localization;
    private String date_create;
    private double rating;
    Context context;

    Place(long id, String name, String description, String localization, double rating, String date_create){
        this.id = id;
        this.name = name;
        this.date_create = date_create;
        this.description = description;
        this.localization = localization;
        this.rating = rating;
    }

    Place(Context context, String name, String description, String localization, double rating){
        this.context = context;
        this.name = name;
        this.description = description;
        this.localization = localization;
        this.rating = rating;
        this.id = 0;
        this.date_create = String.valueOf(new Date().getTime());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLocalization() {
        return this.localization;
    }

    public double getRating() {
        return rating;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public long getId(){
        return this.id;
    }

    public String getDateFormat(){
        Date dateCreate = new Date(Long.parseLong(this.date_create));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(dateCreate);
    }
}
