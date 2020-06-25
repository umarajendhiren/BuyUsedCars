package com.androidapps.buyusedcars.model;

public class UsedCarDetailsModel {

    public static final String FIELD_MAKE = "make";
    public static final String FIELD_MODEL = "model";
    public static final String FIELD_STATE = "state";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_YEAR = "year";
    public static final String FIELD_COLOR = "exteriorColor";
    public static final String FIELD_BODY = "style";


    public static final String FIELD_STYLE = "style";
    public static final String FIELD_TRANSMISSION = "transmission";
    public static final String FIELD_EXTERIOR = "exteriorColor";
    public static final String FIELD_INTERIOR = "interiorColor";
    public static final String FIELD_DRIVETYPE = "driveType";
    public static final String FIELD_FUELTYPE = "fuelType";
    public static final String ACCIDRNT = "accident";


    private String state, make, model, style, transmission, exteriorColor, interiorColor, driveType, fuelType, accident;
    private String imageUri;
    private int year, numberOfOwner, numberOfServiceHistoryRecord, price;
    private Float mileage;


    private boolean wish;

    private String documentKey;

    public UsedCarDetailsModel() {


    }


    public UsedCarDetailsModel(String state, String make, String model, String style, String transmission, String exteriorColor, String interiorColor, String driveType, String fuelType, String accident,
                               int year, Float mileage, int numberOfOwner, int numberOfServiceHistoryRecord, int price, String imageUri, boolean wish) {

        this.state = state;
        this.make = make;
        this.model = model;
        this.style = style;
        this.transmission = transmission;
        this.exteriorColor = exteriorColor;
        this.interiorColor = interiorColor;
        this.driveType = driveType;
        this.fuelType = fuelType;
        this.accident = accident;
        this.year = year;
        this.mileage = mileage;
        this.wish = wish;
        this.numberOfOwner = numberOfOwner;
        this.numberOfServiceHistoryRecord = numberOfServiceHistoryRecord;
        this.price = price;
        this.imageUri = imageUri;
    }

    public String getState() {
        return state;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getStyle() {
        return style;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getExteriorColor() {
        return exteriorColor;
    }

    public String getInteriorColor() {
        return interiorColor;
    }

    public String getDriveType() {
        return driveType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getaccident() {
        return accident;
    }

    public String getImageUri() {
        return imageUri;
    }

    public int getYear() {
        return year;
    }

    public Float getMileage() {
        return mileage;
    }

    public int getNumberOfOwner() {
        return numberOfOwner;
    }

    public int getNumberOfServiceHistoryRecord() {
        return numberOfServiceHistoryRecord;
    }

    public int getPrice() {
        return price;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public boolean getWish() {
        return wish;
    }


}
