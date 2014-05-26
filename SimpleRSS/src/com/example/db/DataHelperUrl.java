package com.example.db;

import org.mcsoxford.rss.RSSItem;

import com.example.simplerss.RssResource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataHelperUrl {

	private SQLiteDatabase db;
    private static final String TAG = DataHelperUrl.class.getName();
    
    public DataHelperUrl(Context context) {
        DbHelper openHelper = new DbHelper(context);
        db = openHelper.getWritableDatabase();
        //db.execSQL("PRAGMA foreign_keys = ON;");
    }
    
    public void cleanOldResource() {
        db.delete(DbHelper.RESOURCE_TABLE, null, null);
    }

    public long insertResource(RssResource resource) {
        Log.i(TAG, "insertResource");
        ContentValues values = getResourceValues(resource);
        return db.insert(DbHelper.RESOURCE_TABLE, null, values);
    }
    
    public boolean deleteRow(long rowId) {
        String where = DbHelper.ID + "=" + rowId;
        return db.delete(DbHelper.RESOURCE_TABLE, where, null) != 0;
    }
    
    public void updateRow(RssResource resource, long rowId) {
    	
    	 ContentValues values = new ContentValues();
         values.put(DbHelper.RESOURCE_TITLE, resource.getTitle());
         values.put(DbHelper.RESOURCE_URL, resource.getUrl());
         
        String where = DbHelper.ID + "=" + rowId;
        db.update(DbHelper.RESOURCE_TABLE, values, "_id = ?",
                new String[] { String.valueOf(rowId) });
       /* long rowID = db.update(DbHelper.RESOURCE_TABLE, values, "_id = ?",
                new String[] { String.valueOf(rowId) });*/

        // Insert it into the database.
       // return db.update(DbHelper.RESOURCE_TABLE, values, where, null) != 0;
    }
    
    private ContentValues getResourceValues(RssResource resource) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.RESOURCE_TITLE, resource.getTitle());
        values.put(DbHelper.RESOURCE_URL, resource.getUrl());

        return values;
    }
	
    public Cursor getCursor(String tableName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }
    
    public RssResource getResource(long rowId) {
    	RssResource resource = new RssResource();
    	String where = DbHelper.ID + "=" + rowId;
    	
    	Cursor cursor = db.query(DbHelper.RESOURCE_TABLE, null, where, null, null, null, null);
    	cursor.moveToFirst();
    	resource.setTitle(cursor.getString(cursor.getColumnIndex(DbHelper.RESOURCE_TITLE)));
    	resource.setUrl(cursor.getString(cursor.getColumnIndex(DbHelper.RESOURCE_URL)));

    	return resource;
    	
    }
}
