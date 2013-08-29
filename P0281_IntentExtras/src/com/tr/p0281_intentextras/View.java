package com.tr.p0281_intentextras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class View extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		
		TextView textFullName = (TextView)findViewById(R.id.textFullName);
		Intent intent = getIntent();
		
		String firstName = intent.getStringExtra("fName");
		String secondName = intent.getStringExtra("sName");
		
		textFullName.setText("Full name is " + firstName + " " + secondName);
	}

}
