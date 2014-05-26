package com.example.simplerss;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DialogUrl extends DialogFragment implements OnClickListener{
Button button;
EditText textUrl;
EditText textUrlTitle;
RssResource resource;

	private DialogCallBack dialogCallBack;

	public static interface DialogCallBack{
		public void setUrl(RssResource resource);
	}
	
	
	@Override
	public void onClick(View v) {
		String urlText = textUrl.getText().toString();
		String urlTitle = textUrlTitle.getText().toString();
		RssResource resource = new RssResource();
		resource.setUrl(urlText);
		resource.setTitle(urlTitle);
		
		if (dialogCallBack != null){
		dialogCallBack.setUrl(resource);
		
		dismiss();
		}
		
	}

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		    View v = inflater.inflate(R.layout.dialog_url, null);
		    getDialog().setTitle(R.string.dialog_label);
		    
		    button = (Button) v.findViewById(R.id.button_set_url);
		    textUrl = (EditText) v.findViewById(R.id.editText_url);
		    textUrlTitle = (EditText) v.findViewById(R.id.editText_urlTitle);
		    
		    button.setOnClickListener(this);

		    return v;
		  }
	
	public void setDialogCallBack(DialogCallBack dialogCallBack){
		this.dialogCallBack = dialogCallBack;
	}
	
	
}
