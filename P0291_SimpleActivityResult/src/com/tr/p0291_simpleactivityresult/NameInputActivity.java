package com.tr.p0291_simpleactivityresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NameInputActivity extends Activity implements OnClickListener{

	EditText editFullName;
	Button btnSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.name);
		
		editFullName = (EditText)findViewById(R.id.editFullName);
		btnSend = (Button)findViewById(R.id.btnSend);
		btnSend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("name", editFullName.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
}
