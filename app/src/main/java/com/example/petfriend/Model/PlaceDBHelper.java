package com.example.petfriend.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlaceDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "place.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "Place";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PLACE_NAME = "name";
    public static final String COLUMN_PLACE_CATEGORY = "category";
    public static final String COLUMN_PLACE_PHOTO= "photo";
    public static final String COLUMN_PLACE_LOCATION = "location";
    public static final String COLUMN_PLACE_LINK = "link";

    public PlaceDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLACE_NAME + " TEXT NOT NULL, " +
                COLUMN_PLACE_CATEGORY + " TEXT NOT NULL, "+
                COLUMN_PLACE_PHOTO + " TEXT NOT NULL, " +
                COLUMN_PLACE_LOCATION + " TEXT NOT NULL, " +
                COLUMN_PLACE_LINK + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void saveNewPlace(Place place) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLACE_NAME, place.getName());
        values.put(COLUMN_PLACE_CATEGORY, place.getCategory());
        values.put(COLUMN_PLACE_PHOTO, place.getPhoto());
        values.put(COLUMN_PLACE_LOCATION, place.getLocation());
        values.put(COLUMN_PLACE_LINK, place.getUrl());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Place> placeList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Place> placeLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Place place;

        if (cursor.moveToFirst()) {
            do {
                place = new Place();

                place.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                place.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_NAME)));
                place.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_CATEGORY)));
                place.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_PHOTO)));
                place.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_LOCATION)));
                place.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_LINK)));
                placeLinkedList.add(place);
            } while (cursor.moveToNext());
        }


        return placeLinkedList;
    }

    /**Query only 1 record**/
    public Place getPlace(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Place receivedPlace = new Place();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedPlace.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_NAME)));
            receivedPlace.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_CATEGORY)));
            receivedPlace.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_PHOTO)));
            receivedPlace.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_LOCATION)));
            receivedPlace.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PLACE_LINK)));
        }
     return receivedPlace;

    }

    /**delete record**/
    public void deletePlaceRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();

    }

    /**update record**/
    public void updatePlaceRecord(long placeId, Context context, Place updatePlace) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET name ='"+ updatePlace.getName() + "', category ='" + updatePlace.getCategory()+ "', photo ='"+ updatePlace.getPhoto() + "', location ='"+ updatePlace.getLocation() + "' , link ='"+ updatePlace.getUrl() + "' WHERE _id='" + placeId + "'");
        Toast.makeText(context, "업데이트가 완료되었습니다.", Toast.LENGTH_SHORT).show();


    }

}
