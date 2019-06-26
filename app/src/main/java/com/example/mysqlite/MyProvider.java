package com.example.mysqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.mysqlite.dao.MySqliteOpenHelper;

public class MyProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.mysqlite.MyProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/users";
    static final Uri CONTENT_URI = Uri.parse(URL);

    SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        MySqliteOpenHelper helper = new MySqliteOpenHelper(getContext());
        db = helper.getWritableDatabase();
        return (db == null)? false:true;
    }

    
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        String selectQuery = "SELECT  * FROM user";
        Cursor cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }

    
    @Override
    public String getType( Uri uri) {
        return null;
    }

    
    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
