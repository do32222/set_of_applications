package com.tr.p0331_sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	EditText editInputStr;
	Button btnSave, btnLoad;
	SharedPreferences sPref;
	
	final String SAVED_TEXT = "saved_text";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		editInputStr = (EditText)findViewById(R.id.editInputStr);
		btnSave = (Button)findViewById(R.id.btnSave);
		btnLoad = (Button)findViewById(R.id.btnLoad);
		
		btnSave.setOnClickListener(this);
		btnLoad.setOnClickListener(this);
		
		loadText();
	}
	
	@Override
	protected void onDestroy() {
		saveText();
	    super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View paramView) {
		// TODO Auto-generated method stub
		switch(paramView.getId()) {
		case R.id.btnSave:
			saveText();
			break;
		case R.id.btnLoad:
			loadText();
			break;
		default:
			break;
		}
	}
	
	void saveText() {
		sPref = getPreferences(MODE_PRIVATE);
		Editor ed = sPref.edit();
		ed.putString(SAVED_TEXT, editInputStr.getText().toString());
		ed.commit();	
		Toast.makeText(this, "Text is saved", Toast.LENGTH_SHORT).show();
	}
	
	void loadText() {
		sPref = getPreferences(MODE_PRIVATE);
		String savedText = sPref.getString(SAVED_TEXT, "");
		editInputStr.setText(savedText);
		Toast.makeText(this, "Text is loaded", Toast.LENGTH_SHORT).show();
	}
}
