package com.tr.customview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	WebView browser;
	Button btn;
	LinearLayout lay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		try {
			lay = (LinearLayout)findViewById(R.id.layout);
			lay.setBackgroundColor(Color.BLACK);
			setAlphaForView(lay, 0f);
		btn = (Button)findViewById(R.id.btn);
		setAlphaForView(btn, 1f);
		
		//btn.setVisibility(View.INVISIBLE);

//		browser = (WebView)findViewById(R.id.browser);
//		setAlphaForView(browser, 0f);
//		browser.loadUrl("http://www.google.com");
//		browser.setVisibility(android.view.View.GONE);
//		setAlphaForView(browser, 0f);
//		//browser.invalidate();
//		browser.setLayoutParams(new LinearLayout.LayoutParams(0,0));
//		//browser.invalidate();
//		browser.removeAllViews();
//		browser.invalidate();
//		browser.setBackgroundColor(Color.WHITE);
//		browser.reload();
//		browser.refreshDrawableState();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setAlphaForView(View v, float alpha) {
		AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
		animation.setDuration(0);
		animation.setFillAfter(true);
		v.startAnimation(animation);
	}

}
