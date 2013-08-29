package com.tr.p0261_intentfilter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowDateEx extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showdate_ex);
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		
		TextView textDateEx = (TextView)findViewById(R.id.textDateEx);
		textDateEx.setText(date);
	}
	
}
