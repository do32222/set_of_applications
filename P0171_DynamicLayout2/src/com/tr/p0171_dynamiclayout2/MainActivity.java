package com.tr.p0171_dynamiclayout2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	LinearLayout linLayoutBtns;
	RadioGroup rgGravity;
	EditText editBtnName;
	Button btnCreate;
	Button btnClear;
	
	int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		linLayoutBtns = (LinearLayout)findViewById(R.id.linLayout);
		rgGravity = (RadioGroup)findViewById(R.id.rgGravity);
		editBtnName = (EditText)findViewById(R.id.editBtnName);
		btnCreate = (Button)findViewById(R.id.btnCreate);
		btnCreate.setOnClickListener(this);
		btnClear = (Button)findViewById(R.id.btnClear);
		btnClear.setOnClickListener(this);
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
		case R.id.btnCreate:
			LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
					wrapContent, wrapContent);
			switch(rgGravity.getCheckedRadioButtonId()) {
			case R.id.radioLeft:
				lParams.gravity = Gravity.LEFT;
				break;
			case R.id.radioCenter:
				lParams.gravity = Gravity.CENTER_HORIZONTAL;
				break;
			case R.id.radioRight:
				lParams.gravity = Gravity.RIGHT;
				break;
			default: 
				lParams.gravity = Gravity.LEFT;
				break;
			}
			Button tempBtn = new Button(this);
			tempBtn.setText(editBtnName.getText().toString());
			linLayoutBtns.addView(tempBtn, lParams);
			break;
		case R.id.btnClear:
			linLayoutBtns.removeAllViews();
			Toast.makeText(this, "Clear is done", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
