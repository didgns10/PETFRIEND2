package com.example.petfriend.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class DiaryDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "Diary";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DIARY_TITLE = "title";
    public static final String COLUMN_DIARY_TIME = "time";
    public static final String COLUMN_DIARY_PHOTO= "photo";
    public static final String COLUMN_DIARY_CONTENT = "content";

    public DiaryDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DIARY_TITLE + " TEXT NOT NULL, " +
                COLUMN_DIARY_TIME + " TEXT NOT NULL, "+
                COLUMN_DIARY_PHOTO + " TEXT NOT NULL, " +
                COLUMN_DIARY_CONTENT + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void saveNewDiary(Diary diary) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DIARY_TITLE, diary.getTitle());
        values.put(COLUMN_DIARY_TIME, diary.getTime());
        values.put(COLUMN_DIARY_PHOTO, diary.getImage());
        values.put(COLUMN_DIARY_CONTENT, diary.getContent());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Diary> diaryList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Diary> diaryLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Diary diary;

        if (cursor.moveToFirst()) {
            do {
                diary = new Diary();

                diary.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                diary.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_DIARY_TITLE)));
                diary.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_DIARY_TIME)));
                diary.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_DIARY_PHOTO)));
                diary.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_DIARY_CONTENT)));
                diaryLinkedList.add(diary);
            } while (cursor.moveToNext());
        }


        return diaryLinkedList;
    }

    /**Query only 1 record**/
    public Diary getDiary(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Diary receivedDiary = new Diary();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedDiary.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_DIARY_TITLE)));
            receivedDiary.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_DIARY_TIME)));
            receivedDiary.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_DIARY_PHOTO)));
            receivedDiary.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_DIARY_CONTENT)));
        }



        return receivedDiary;

    }

    /**delete record**/
    public void deleteDiaryRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();

    }

    /**update record**/
    public void updateDiaryRecord(long diaryId, Context context, Diary updateDiary) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET title ='"+ updateDiary.getTitle() + "', time ='" + updateDiary.getTime()+ "', photo ='"+ updateDiary.getImage() + "', content ='"+ updateDiary.getContent()  + "' WHERE _id='" + diaryId + "'");
        Toast.makeText(context, "업데이트가 완료되었습니다.", Toast.LENGTH_SHORT).show();
    }


}


