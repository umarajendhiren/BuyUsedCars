package com.androidapps.buyusedcars.model;

public class MakeModel {
    /*this pojo class, We need getters function to access variable in our xml and setters are used when you want two way DataBinding.*/
    private String mName;
    private String mImageUrl;
    private String documentKey;

    public MakeModel() {
        //empty constructor needed
    }

    public MakeModel(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }
}


