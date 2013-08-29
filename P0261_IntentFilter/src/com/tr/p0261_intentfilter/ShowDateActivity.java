package com.tr.p0261_intentfilter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowDateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showdate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		
		TextView textDate = (TextView)findViewById(R.id.textShowDate);
		textDate.setText(date);
	}

}
