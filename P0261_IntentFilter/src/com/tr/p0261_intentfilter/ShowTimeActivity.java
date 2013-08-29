package com.tr.p0261_intentfilter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowTimeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showtime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(new Date(System.currentTimeMillis()));
		
		TextView textShowTime = (TextView)findViewById(R.id.textShowTime);
		textShowTime.setText(time);
	}

}
