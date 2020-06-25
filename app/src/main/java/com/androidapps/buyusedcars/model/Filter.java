package com.androidapps.buyusedcars.model;

import android.text.TextUtils;

import com.google.firebase.firestore.Query;

public class Filter {
    static Filter filter;

    public Filter() {

    }


    private String make = null;
    private String model = null;


    private String bodyType = null;

    public static Filter getFilterForCarInstance() {
        if (filter == null) {
            filter = new Filter();

        }
        return filter;
    }

    private String state = null;

    private int priceMini;
    private int priceMaxi;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {

        this.year = year;
    }

    private int year;

    private String sortBy = null;
    private Query.Direction sortDirection = null;


    public String getExteriorColor() {
        return exteriorColor;
    }

    public void setExteriorColor(String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    private String exteriorColor = null;

    public boolean hasMiniPrice() {
        return priceMini > 0;
    }

    public boolean hasMaxiPrice() {
        return priceMaxi > 0;
    }

    public boolean hasYear() {
        return year > 0;
    }


    public boolean hasSortBy() {
        return !(TextUtils.isEmpty(sortBy));
    }

    public boolean hasMake() {
        return !(TextUtils.isEmpty(make));
    }

    public boolean hasExteriorColor() {
        return !(TextUtils.isEmpty(exteriorColor));
    }

    public boolean hasModel() {
        return !(TextUtils.isEmpty(model));
    }

    public boolean hasBodyType() {
        return !(TextUtils.isEmpty(bodyType));
    }


    public String getMake() {

        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public void setPriceMini(int priceMini) {
        this.priceMini = priceMini;
    }

    public int getPriceMini() {
        return priceMini;
    }

    public int getPriceMaxi() {
        return priceMaxi;
    }

    public void setPriceMaxi(int priceMaxi) {
        this.priceMaxi = priceMaxi;
    }


    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Query.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Query.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
public void setFilter(){

}

}
