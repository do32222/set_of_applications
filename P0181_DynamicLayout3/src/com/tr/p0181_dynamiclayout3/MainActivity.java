package com.tr.p0181_dynamiclayout3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnSeekBarChangeListener{

	SeekBar seekBar;
	Button btnLeft;
	Button btnRight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		seekBar = (SeekBar)findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(this);
		btnLeft = (Button)findViewById(R.id.btnLeft);
		btnRight = (Button)findViewById(R.id.btnRight);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		int tempProgress = seekBar.getProgress();
		LinearLayout.LayoutParams linParams1 = new LinearLayout.LayoutParams(
				0,LinearLayout.LayoutParams.WRAP_CONTENT);
		linParams1.weight = tempProgress;
		btnLeft.setLayoutParams(linParams1);
		btnLeft.setText(String.valueOf(tempProgress));
		
		LinearLayout.LayoutParams linParams2 = new LinearLayout.LayoutParams(
				0,LinearLayout.LayoutParams.WRAP_CONTENT);
		linParams2.weight = 100-tempProgress;
		btnRight.setLayoutParams(linParams2);
		btnRight.setText(String.valueOf(linParams2.weight));
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
	}

}
