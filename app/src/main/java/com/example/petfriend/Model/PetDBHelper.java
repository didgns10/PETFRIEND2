package com.example.petfriend.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class PetDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pet.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "Pet";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MOUNTAIN_NAME = "name";
    public static final String COLUMN_MOUNTAIN_ELEVATION = "elevation";
    public static final String COLUMN_MOUNTAIN_PHOTO= "photo";
    public static final String COLUMN_MOUNTAIN_DESCRIPTION = "description";
    public static final String COLUMN_MOUNTAIN_LOCATION = "location";

    public PetDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MOUNTAIN_NAME + " TEXT NOT NULL, " +
                COLUMN_MOUNTAIN_ELEVATION + " TEXT NOT NULL, "+
                COLUMN_MOUNTAIN_PHOTO + " TEXT NOT NULL, " +
                COLUMN_MOUNTAIN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_MOUNTAIN_LOCATION + " TEXT NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void saveNewMountain(Pet pet) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOUNTAIN_NAME, pet.getName());
        values.put(COLUMN_MOUNTAIN_ELEVATION, pet.getElevation());
        values.put(COLUMN_MOUNTAIN_PHOTO, pet.getPhoto());
        values.put(COLUMN_MOUNTAIN_DESCRIPTION, pet.getDescription());
        values.put(COLUMN_MOUNTAIN_LOCATION, pet.getLocation());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Pet> petList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Pet> mountainLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pet pet;

        if (cursor.moveToFirst()) {
            do {
                pet = new Pet();

                pet.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                pet.setName(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_NAME)));
                pet.setElevation(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_ELEVATION)));
                pet.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_PHOTO)));
                pet.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_DESCRIPTION)));
                pet.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_LOCATION)));
                mountainLinkedList.add(pet);
            } while (cursor.moveToNext());
        }


        return mountainLinkedList;
    }

    /**Query only 1 record**/
    public Pet getPet(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Pet receivedPet = new Pet();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedPet.setName(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_NAME)));
            receivedPet.setElevation(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_ELEVATION)));
            receivedPet.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_PHOTO)));
            receivedPet.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_DESCRIPTION)));
            receivedPet.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_MOUNTAIN_LOCATION)));
        }



        return receivedPet;

    }

    /**delete record**/
    public void deletePetRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();

    }

    /**update record**/
    public void updatePetRecord(long mountainId, Context context, Pet updatePet) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET name ='"+ updatePet.getName() + "', elevation ='" + updatePet.getElevation()+ "', photo ='"+ updatePet.getPhoto() + "', description ='"+ updatePet.getDescription() + "' , location ='"+ updatePet.getLocation() + "' WHERE _id='" + mountainId + "'");
        Toast.makeText(context, "업데이트가 완료되었습니다.", Toast.LENGTH_SHORT).show();


    }


}
