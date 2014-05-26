package com.example.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rssreader";
    public static final String FEED_TABLE = "feed";
    public static final String RESOURCE_TABLE = "resource";

    public static final String FEED_TITLE = "feedTitle";
    public static final String FEED_CONTENT = "feedContent";
    public static final String FEED_URL = "feedUrl";
    public static final String FEED_DATE = "feedDate";
    public static final String FEED_DESCRIPTION = "feedDescription";
    public static final String FEED_THUMBNAIL = "feedThumbnail";
    public static final String ID = "_id";
    
    public static final String RESOURCE_TITLE = "resourceTitle";
    public static final String RESOURCE_URL = "resourceUrl";
    


    public static final String CREATE_STATION_TABLE = "CREATE TABLE IF NOT EXISTS " + FEED_TABLE
            + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + FEED_URL + " TEXT," + FEED_TITLE + " TEXT, "
            + FEED_CONTENT + " TEXT, " + FEED_DATE + " TEXT, " + FEED_DESCRIPTION + " TEXT, " + FEED_THUMBNAIL
            + " TEXT);";

    
    public static final String CREATE_TABLE_URL = "CREATE TABLE IF NOT EXISTS " + RESOURCE_TABLE
            + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + RESOURCE_URL + " TEXT, " + RESOURCE_TITLE + " TEXT);";
    
    public DbHelper(Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATION_TABLE);
        db.execSQL(CREATE_TABLE_URL);  	
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database, adding new changes from " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + FEED_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESOURCE_TABLE);
        onCreate(db);
    }
}