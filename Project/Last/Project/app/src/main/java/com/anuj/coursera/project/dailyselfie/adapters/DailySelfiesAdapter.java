package com.anuj.coursera.project.dailyselfie.adapters;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuj.coursera.project.dailyselfie.R;
import com.anuj.coursera.project.dailyselfie.database.DailySelfiesDatabase;
import com.anuj.coursera.project.dailyselfie.helpers.DailySelfiesPictureHelper;
import com.anuj.coursera.project.dailyselfie.models.DailySelfieRecord;

/**
 * Created by akulkarni on 029-29-11-14.
 */
public class DailySelfiesAdapter extends CursorAdapter
{
    private Context mContext;
    private String mBitmapStoragePath;

    private static LayoutInflater mLayoutInflater = null;
    private ArrayList<DailySelfieRecord> mDailySelfieRecords = new ArrayList<DailySelfieRecord>();

    private DailySelfiesDatabase mDailySelfiesDatabase;

    public DailySelfiesAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);

        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

        mDailySelfiesDatabase = new DailySelfiesDatabase(mContext);
        mDailySelfiesDatabase.open();

        mBitmapStoragePath = DailySelfiesPictureHelper.getBitmapStoragePath(mContext);

        reloadData();
    }

    /**
     * Public Methods
     */

    public void addSelfie(DailySelfieRecord newSelfie) {

        String filePath = mBitmapStoragePath + "/" + newSelfie.getName();

        if(DailySelfiesPictureHelper.storeBitmapToFile(newSelfie.getPictureBitmap(), filePath)) {
            newSelfie.setPicturePath(filePath);
        }

        mDailySelfiesDatabase.insertSelfie(newSelfie.getName(), newSelfie.getPicturePath());

        reloadData();
    }

    public void deleteSelfie(DailySelfieRecord selfie) {
        mDailySelfiesDatabase.deleteSelfie(selfie.getID());

        reloadData();
    }

    public void deleteAllSelfies() {
        //DELETE PICTURES FROM EXTERNAL CARD
        File bitmapStorageDir = new File(mBitmapStoragePath);
        deleteAllFilesRecursive(bitmapStorageDir);

        //DELETE ITEMS FROM DATABASE
        mDailySelfiesDatabase.deleteAllSelfies();

        reloadData();
    }

    public void freeResources() {
        mDailySelfiesDatabase.close();
    }

    /**
     * Private Helper Methods
     */

    public void reloadData() {
        this.swapCursor(mDailySelfiesDatabase.getAllSelfies());
    }

    // Returns a new DailySelfieRecord for the data at the cursor's current position
    private DailySelfieRecord getSelfieRecordFromCursor(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(DailySelfiesDatabase.KEY_ROWID));
        String name = cursor.getString(cursor.getColumnIndex(DailySelfiesDatabase.KEY_NAME));
        String picturePath = cursor.getString(cursor.getColumnIndex(DailySelfiesDatabase.KEY_PICTURE_PATH));

        DailySelfieRecord selfie = new DailySelfieRecord(name, picturePath);
        selfie.setID(id);

        return selfie;
    }


    private void deleteAllFilesRecursive(File fileOrDirectory) {
        if(fileOrDirectory.isDirectory()) {
            for(File child : fileOrDirectory.listFiles()) {
                deleteAllFilesRecursive(child);
            }
        }
        else {
            fileOrDirectory.delete();
        }
    }

    /**
     * View Holder Class Methods
     */

    static class ViewHolder {
        TextView name;
        ImageView picture;
    }

    /**
     * Overridden Methods
     */

    @Override
    public Cursor swapCursor(Cursor newCursor) {

        mDailySelfieRecords.clear();

        if(newCursor != null && newCursor.moveToFirst()) {
            do {
                mDailySelfieRecords.add(getSelfieRecordFromCursor(newCursor));
            } while (newCursor.moveToNext());
        }

        return super.swapCursor(newCursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(cursor.getString(cursor.getColumnIndex(DailySelfiesDatabase.KEY_NAME)));

        int dimenPix = (int)mContext.getResources().getDimension(R.dimen.selfie_row_picture_width_height);

        holder.picture.setImageBitmap(DailySelfiesPictureHelper.getScaledBitmap(cursor.getString(cursor.getColumnIndex(DailySelfiesDatabase.KEY_PICTURE_PATH)), dimenPix, dimenPix));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        View newView = mLayoutInflater.inflate(R.layout.daily_selfie_row, parent, false);
        holder.name = (TextView)newView.findViewById(R.id.selfie_name_text_view);
        holder.picture = (ImageView)newView.findViewById(R.id.selfie_picture_image_view);

        newView.setTag(holder);

        return newView;
    }

    @Override
    public int getCount() {
        return mDailySelfieRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return mDailySelfieRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
