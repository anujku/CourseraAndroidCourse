package com.anuj.coursera.project.dailyselfie.models;

import android.graphics.Bitmap;

/**
 * Created by akulkarni on 029-29-11-14.
 */
public class DailySelfieRecord
{
    private int mID;
    private String mName;
    private String mPicturePath;

    private Bitmap mPictureBitmap;

    public DailySelfieRecord(String name, String picturePath) {
        super();

        mName = name;
        mPicturePath = picturePath;
    }

    public int getID() {
        return mID;
    }
    public void setID(int ID) {
        mID = ID;
    }
    public String getName() {
        return mName;
    }
    public void setDate(String name) {
        mName = name;
    }
    public String getPicturePath() {
        return mPicturePath;
    }
    public void setPicturePath(String picturePath) {
        mPicturePath = picturePath;
    }
    public Bitmap getPictureBitmap() {
        return mPictureBitmap;
    }
    public void setPictureBitmap(Bitmap pictureBitmap) {
        mPictureBitmap = pictureBitmap;
    }
}