package com.tr.p0271_getintentaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		Intent intent = getIntent();
		String action = intent.getAction();
		
		String format = "";
		String strInfo = "";
		
		if(action.equals("com.tr.intent.action.showDate")) {
			format = "dd.MM.yyyy";
			strInfo = "Date: ";
		} else if(action.equals("com.tr.intent.action.showTime")) {
			format = "HH:mm:ss";
			strInfo = "Time: ";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String value = sdf.format(new Date(System.currentTimeMillis()));
		
		TextView textInfo = (TextView)findViewById(R.id.textInfo);
		textInfo.setText(strInfo + value);
	}

}
