package com.example.simplerss;

import com.example.db.DataHelperUrl;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UrlEditActivity extends Activity{
	Button butSet;
	
	EditText textUrl;
	EditText textUrlTitle;
	
	DataHelperUrl dataHelperUrl;
	RssResource resource;
	
	String title;
	String url; 
	long id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_url_edit);
		
		Intent intent = getIntent();
		resource = new RssResource();
		
		dataHelperUrl = new DataHelperUrl(getApplicationContext());
	    
	    title = intent.getStringExtra("title");
	    url = intent.getStringExtra("url");
	    id = intent.getLongExtra("id",0);
	    
	    textUrlTitle = (EditText) findViewById(R.id.editTitle);
	    textUrl = (EditText) findViewById(R.id.editUrl);
	    butSet = (Button) findViewById(R.id.buttonEditSet);
	    butSet.setOnClickListener(new ButtonListener());
	    textUrlTitle.setText(title);
	    textUrl.setText(url);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.url_edit, menu);
		return true;
	}

	private class ButtonListener implements OnClickListener{
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.buttonEditSet:
			resource.setTitle(textUrlTitle.getText().toString());
			resource.setUrl(textUrl.getText().toString());
			dataHelperUrl.updateRow(resource, id);
			finish();
		break;
		}
		
	}
	}

}
