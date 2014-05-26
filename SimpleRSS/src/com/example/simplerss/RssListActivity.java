package com.example.simplerss;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.adapters.UrlCursorAdapter;
import com.example.adapters.UrlCursorAdapter.ViewHolder;
import com.example.db.DataHelperUrl;
import com.example.db.DbHelper;
import com.example.simplerss.DialogUrl.DialogCallBack;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class RssListActivity extends SherlockFragmentActivity implements OnItemClickListener, DialogCallBack{

	private ListView listView;
	DataHelperUrl dataHelperUrl;
	UrlCursorAdapter adapter;
	
	protected static final int CONTEXTMENU_OPTION1 = 1;
	protected static final int CONTEXTMENU_OPTION2 = 2;
	protected static final int CONTEXTMENU_OPTION3 = 3;
	
    DialogUrl dialogUrl;
    
    private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		dialogUrl = new DialogUrl();
		dialogUrl.setDialogCallBack(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss_list);
		dataHelperUrl = new DataHelperUrl(getApplicationContext());
		listView = (ListView) findViewById(R.id.listViewUrl);
		listView.setOnItemClickListener(this);
		registerForContextMenu(listView);
		refreshAdapter();

	}

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			
			ViewHolder holder = (ViewHolder) view.getTag();
			Intent intent = new Intent();					//set url into MainActivity
			intent.putExtra("url", holder.getLink());
		    setResult(RESULT_OK, intent);
		    finish();
		    		
		}
	
	private void refreshAdapter() {
		Cursor rssListCursor = dataHelperUrl.getCursor(DbHelper.RESOURCE_TABLE);
		adapter = new UrlCursorAdapter(RssListActivity.this, rssListCursor,
				true);
		listView.setAdapter(adapter);
	}

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
		 com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
	        inflater.inflate(R.menu.rss_list, menu);

	        return true;
	    }
		
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.action_add_url:
	        	dialogUrl.show(getSupportFragmentManager(), "dlg1");
	            return true;
	        default:

	        }
	        return super.onOptionsItemSelected(item);
	    }
	 
	 @Override
	    public void onCreateContextMenu(ContextMenu menu, View v,
	            ContextMenuInfo menuInfo) {
	        // TODO Auto-generated method stub
		 super.onCreateContextMenu(menu, v, menuInfo);
		 /*MenuInflater inflater = getSupportMenuInflater();
	        inflater.inflate(R.menu.rss_list_context, menu);*/
		 	menu.add(Menu.NONE, CONTEXTMENU_OPTION1, 0, "Delete"); 
		    menu.add(Menu.NONE, CONTEXTMENU_OPTION2, 1, "Edit"); 
	     }
	 
	 public boolean onContextItemSelected(android.view.MenuItem item) {
	     AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
	             .getMenuInfo();
	     switch (item.getItemId()) {
	      case CONTEXTMENU_OPTION1:
	    	 dataHelperUrl.deleteRow(info.id);
	    	 refreshAdapter();
	        break;
	      case CONTEXTMENU_OPTION2:
	    	  Intent editIntent = new Intent(this, UrlEditActivity.class);

	    	  String title = (dataHelperUrl.getResource(info.id)).getTitle();
	    	  String url = (dataHelperUrl.getResource(info.id)).getUrl();
	    	  
	    	 editIntent.putExtra("title", title);
	    	 editIntent.putExtra("url", url);
	    	 editIntent.putExtra("id", info.id);
	    	 
	    	 startActivity(editIntent);
	    	  
	        break;
	      }

	     return true;
	 }
	 
	 @Override
	  protected void onResume() {
	    super.onResume();
	    refreshAdapter();
	  }

	@Override
	public void setUrl(RssResource resource) {
		 this.url = resource.getUrl();
		 dataHelperUrl.insertResource(resource);
    	 refreshAdapter();
		
	}
}
