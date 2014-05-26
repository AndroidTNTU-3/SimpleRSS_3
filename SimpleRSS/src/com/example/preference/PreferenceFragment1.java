package com.example.preference;

import com.example.simplerss.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PreferenceFragment1 extends PreferenceFragment {
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.pref);
	  }
	
}
