package com.tr.p0271_getintentaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button btnShowTime = (Button)findViewById(R.id.btnShowTime);
		Button btnShowDate = (Button)findViewById(R.id.btnShowDate);
		
		btnShowTime.setOnClickListener(this);
		btnShowDate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(v.getId()) {
		case R.id.btnShowTime:
			intent = new Intent("com.tr.intent.action.showTime");
			startActivity(intent);
			break;
		case R.id.btnShowDate:
			intent = new Intent("com.tr.intent.action.showDate");
			startActivity(intent);
			break;
		}
	}

}
