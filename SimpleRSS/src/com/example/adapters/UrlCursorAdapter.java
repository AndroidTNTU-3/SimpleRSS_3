package com.example.adapters;

import com.example.db.DbHelper;
import com.example.simplerss.R;
import com.example.simplerss.Utils;
import com.example.simplerss.R.id;
import com.example.simplerss.R.layout;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UrlCursorAdapter extends CursorAdapter{
	
	 public static class ViewHolder {
		 
	        private TextView titleTextView;
	        private TextView linkTextView;
	        private String link;
	        
	        public String getLink() {
	            return link;
	        }
	        
	    }

	private LayoutInflater inflater;
	
	public UrlCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();

		String title = Utils.getStringColumn(cursor, DbHelper.RESOURCE_TITLE);
		String link = Utils.getStringColumn(cursor, DbHelper.RESOURCE_URL);
		
        holder.link = link;
		
		holder.titleTextView.setText(title);
		holder.linkTextView.setText(link);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		View view = inflater.inflate(R.layout.row_url, parent, false);
		ViewHolder holder = new ViewHolder();
		holder.titleTextView = (TextView) view.findViewById(R.id.rowUrlTitle);
        holder.linkTextView = (TextView) view.findViewById(R.id.rowLink);
        view.setTag(holder);
		return view;
	}
	
	
}
