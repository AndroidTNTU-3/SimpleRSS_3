package com.example.simplerss;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class RssDetailActivity extends SherlockFragmentActivity {
	
	WebView web;
	WebViewClient webclient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss_detail);
		Bundle extras = getIntent().getExtras();
        
		webclient = new Webclient();
		
        String title = extras.getString(MainActivity.EXTRA_TITLE);
        String url = extras.getString(MainActivity.EXTRA_URL);
               
        web = (WebView) findViewById(R.id.webView);
        web.loadUrl(url);
        web.setWebViewClient(webclient);
                
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
                
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:

        }
        return super.onOptionsItemSelected(item);
    }
	
	private class Webclient extends WebViewClient{
		@Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
	}
	
}
