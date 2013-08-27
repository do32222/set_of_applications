package com.tr.p0161_dynamiclayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linLayout = new LinearLayout(this);
		linLayout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams linLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setContentView(linLayout, linLayoutParams);
		
		LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		TextView tv = new TextView(this);
		tv.setText("Some text");
		tv.setLayoutParams(lpView);
		linLayout.addView(tv);
		
		LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftMarginParams.leftMargin = 50;
		Button btn = new Button(this);
		btn.setText("button");
		linLayout.addView(btn, leftMarginParams);
		
		LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rightGravityParams.gravity = Gravity.RIGHT;
		Button btn2 = new Button(this);
		btn2.setText("btn2");
		linLayout.addView(btn2, rightGravityParams);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
