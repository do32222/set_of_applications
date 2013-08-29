package com.tr.p0281_intentextras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{

	EditText editFirstName;
	EditText editSecondName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(this);
		
		editFirstName = (EditText)findViewById(R.id.editFirstName);
		editSecondName = (EditText)findViewById(R.id.editSecondName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, com.tr.p0281_intentextras.View.class);
		intent.putExtra("fName", editFirstName.getText().toString());
		intent.putExtra("sName", editSecondName.getText().toString());
		startActivity(intent);
	}

}
