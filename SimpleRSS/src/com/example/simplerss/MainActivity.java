package com.example.simplerss;

import java.util.List;

import org.mcsoxford.rss.*;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.example.adapters.FeedCursorAdapter;
import com.example.adapters.FeedCursorAdapter.ViewHolder;
import com.example.db.DataHelper;
import com.example.db.DataHelperUrl;
import com.example.simplerss.DialogUrl.DialogCallBack;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends SherlockFragmentActivity implements DialogCallBack{
	
    private ListView listView;
    private DataHelper dataHelper;
    private DataHelperUrl dataHelperUrl;
    private FeedCursorAdapter adapter;
    private static final String TAG = MainActivity.class.getName();
    
    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    
    private Menu optionsMenu;
    
    DialogUrl dialogUrl;
    SharedPreferences sp;
    
    //private String url = "http://riotpixels.com/feed/";
    private String url = "http://feeds.bbci.co.uk/news/rss.xml";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dialogUrl = new DialogUrl();
		dialogUrl.setDialogCallBack(this);

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		dataHelper = new DataHelper(getApplicationContext());
		dataHelperUrl = new DataHelperUrl(getApplicationContext());
		listView = (ListView) findViewById(R.id.listView);	
		init();

	}
	
    @Override
    protected void onResume() {
 	
       super.onResume();
       
       String address = sp.getString("url", "");
       
       refreshList();
       
    }
	

	private void refreshList() {
		new LoadFeeds().execute();	
	}
	
	private void init() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
                ViewHolder holder = (ViewHolder) view.getTag();
                Intent intent = new Intent(MainActivity.this, RssDetailActivity.class);
                intent.putExtra(EXTRA_TITLE, holder.getTitle());
                intent.putExtra(EXTRA_URL, holder.getLink());
                startActivity(intent);
            }
        });
        
        refreshAdapter();
        
    }
	
    private void refreshAdapter() {
    	
        Cursor feedCursor = dataHelper.getFeedCursor();
        adapter = new FeedCursorAdapter(MainActivity.this, feedCursor, true);
        listView.setAdapter(adapter);

    }
    
   	
	 private class LoadFeeds extends AsyncTask<Object, Void, Integer>{
		 
		public RSSFeed feed;
		private ProgressDialog progressDialog;
		private ProgressDialog progressDialogRefresh;
		
		 @Override
	        protected void onPreExecute() {
	            if (adapter.isEmpty()) {
	                progressDialog = new ProgressDialog(MainActivity.this);
	                progressDialog.setMessage(getString(R.string.cache_empty));
	                progressDialog.show();
	            }
	            
	            Cursor cursor = dataHelper.getFeedCursor();
	         
	            if (cursor.getCount() == 0 && progressDialog == null){
	            	setRefreshActionButtonState(true);
	            	/*progressDialogRefresh = new ProgressDialog(MainActivity.this);
	            	progressDialogRefresh.setMessage(getString(R.string.cursor_empty));
	            	progressDialogRefresh.show();*/
	            }
	            	
	        }
		
		@Override
		protected Integer doInBackground(Object... params){

			try {
				RSSReader reader = new RSSReader();	 
				feed = reader.load(url);
				List<RSSItem> rssItems = feed.getItems();
				Log.i("Size of RSSItem", String.valueOf(rssItems.size()) );
				if (rssItems.isEmpty()) {					
	               return 0;
				}
					
				dataHelper.cleanOldFeeds();

					for (RSSItem rssItem : rssItems) {
						Log.i("RSS Reader", rssItem.getTitle());
						Log.i("Thumb Size: ",  String.valueOf(rssItem.getThumbnails().size()));
						dataHelper.insertFeedItem(rssItem);
					}					
				
				} catch (RSSReaderException e) {
				e.printStackTrace();
				return 0;
				}
	        return 1;
			}
		
		@Override
        protected void onPostExecute(Integer result) {
          if (progressDialog != null) {
                progressDialog.dismiss();
            }
         /* if (progressDialogRefresh != null) {
        	  progressDialogRefresh.dismiss();
          }*/
          setRefreshActionButtonState(false);
            if (result == 1) {
                refreshAdapter();
            } else {
                showError();
            }
        }
		
		private void showError() {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.no_network).setTitle(R.string.info).setCancelable(false)
                    .setNeutralButton(R.string.accept, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
		
	 }	 
	 
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	       MenuInflater inflater = getSupportMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	        optionsMenu = menu;
	        return true;
	    }
	 
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.action_set_url:
	        	dialogUrl.show(getSupportFragmentManager(), "dlg1");
	            return true;
	        case R.id.action_refresh:
	        	dataHelper.cleanOldFeeds();
	        	setRefreshActionButtonState(true);
	        	refreshList();
	        	refreshAdapter();
	            return true;
	        case R.id.action_list_url:
	        	Intent intentUrl = new Intent(this, RssListActivity.class);
	        	startActivityForResult(intentUrl, 1);
	            return true;
	        case R.id.action_pref:
	        	Intent intentPref = new Intent(this, PrefActivity.class);
	        	startActivity(intentPref);
	            return true;
	        default:

	        }
	        return super.onOptionsItemSelected(item);
	    }
	 
	 @Override
	 public void setUrl(RssResource resource){
		 this.url = resource.getUrl();
		 dataHelperUrl.insertResource(resource);
		 refreshList();
     	 refreshAdapter();
	 }

	
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (data == null) {return;}
	    this.url = data.getStringExtra("url");
	  }
	
	public void setRefreshActionButtonState(final boolean refreshing) {

	         final MenuItem refreshItem = optionsMenu
	             .findItem(R.id.action_refresh);
	         if (refreshItem != null) {
	             if (refreshing) {
	                 refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
	             } else {
	                 refreshItem.setActionView(null);
	             }
	         }

	}

}
