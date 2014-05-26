package com.example.db;

import org.mcsoxford.rss.RSSItem;

import com.example.simplerss.Feed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

public class DataHelper {

    private static final String TAG = DataHelper.class.getName();
    private SQLiteDatabase db;

    public DataHelper(Context context) {
        DbHelper openHelper = new DbHelper(context);
        db = openHelper.getWritableDatabase();
        //db.execSQL("PRAGMA foreign_keys = ON;");
    }

    public long insertFeedItem(RSSItem rssItem) {
        Feed feed = new Feed();
        feed.setTitle(rssItem.getTitle());
        feed.setContent(rssItem.getContent());
        feed.setDate(rssItem.getPubDate());
        feed.setUrl(rssItem.getLink().toString());
        feed.setDescription(rssItem.getDescription());
        if (rssItem.getThumbnails().size() != 0)
        feed.setUrlThumb(rssItem.getThumbnails().get(0).getUrl().toString());
        else feed.setUrlThumb("");

        return insertFeed(feed);
    }

    public void cleanOldFeeds() {
        db.delete(DbHelper.FEED_TABLE, null, null);
    }

    private long insertFeed(Feed feed) {
        Log.i(TAG, "insertFeed");
        ContentValues values = getFeedValues(feed);
        return db.insert(DbHelper.FEED_TABLE, null, values);
    }
    
    public boolean deleteRow(long rowId) {
        String where = DbHelper.ID + "=" + rowId;
        return db.delete(DbHelper.DATABASE_NAME, where, null) != 0;
    }
    

    private ContentValues getFeedValues(Feed feed) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.FEED_CONTENT, feed.getContent());
        values.put(DbHelper.FEED_TITLE, feed.getTitle());
        values.put(DbHelper.FEED_URL, feed.getUrl());
        values.put(DbHelper.FEED_DATE, feed.getDate().toString());
        values.put(DbHelper.FEED_DESCRIPTION, feed.getDescription());
        if (feed.getUrlThumb().toString() == "") values.putNull(DbHelper.FEED_THUMBNAIL);
        else values.put(DbHelper.FEED_THUMBNAIL, feed.getUrlThumb().toString());

        return values;
    }

    public boolean isTableEmpty(String tableName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        boolean isEmpty = cursor.getCount() == 0;
        cursor.close();
        return isEmpty;
    }

    public Cursor getCursor(String tableName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor getFeedCursor() {
        Cursor cursor = db.query(DbHelper.FEED_TABLE, null, null, null, null, null, DbHelper.FEED_DATE + " ASC");
        return cursor;
    }

    public String createSelection(String name, String value) {
        value = value.replaceAll("'", "");
        return name + " = '" + value + "'";
    }
}
