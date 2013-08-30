package com.trushin.asyncrss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RssItemActivity extends Activity implements OnClickListener{

	Button goBack;
	TextView textContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_item_layout);
		goBack = (Button)findViewById(R.id.btnBack);
		goBack.setOnClickListener(this);
		textContent = (TextView)findViewById(R.id.content);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("paramContent");	//извлекаем информацию для отображения из главного активити
		    textContent.setText(value);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
}
