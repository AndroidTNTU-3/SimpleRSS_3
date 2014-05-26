package com.example.simplerss;

import java.util.List;

import com.example.preference.PreferenceFragment1;

import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class PrefActivity extends PreferenceActivity implements OnPreferenceChangeListener {

	EditTextPreference ed;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			   addPreferencesFromResource(R.xml.pref);
			  }else{ 
			  getFragmentManager().beginTransaction().replace(android.R.id.content,
			          new PreferenceFragment1()).commit();		  
		}

	}
	
	/*protected void onResume() {
		ed = (EditTextPreference) findPreference("url");
		if (ed.getText().toString() != null){
		String s = ed.getText().toString();
		Log.i("URL", s);
		}
	    super.onResume();
	  }*/

	@Override
	public boolean onPreferenceChange(Preference pref, Object arg1) {
		sp = pref.getPreferenceManager().getDefaultSharedPreferences(this);
		String s = sp.getString("url", "");
		Log.i("URL", s);
		return false;
	}
}
