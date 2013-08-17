package com.trushin.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnSeekBarChangeListener, TextWatcher{
	
	private static final String BILL_TOTAL = "BILL_TOTAL";
	private static final String USER_PERCENT = "USER_PERCENT";
	
	EditText editTip10;
	EditText editTip15;
	EditText editTip20;
	
	EditText editTotal10;
	EditText editTotal15;
	EditText editTotal20;
	
	TextView textUserTipProc;
	SeekBar seekBarUserProc;
	
	EditText editInputBillTotal;
	EditText editTipValue;
	EditText editTotalValue; //inputValue + tipValue
	
	TextWatcher watcherEditTotalBill;
	double customBillTotal;
	int customTipProc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if(savedInstanceState == null) {
			customBillTotal = 0;
			customTipProc = 18;
		}
		else {
			customBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
			customTipProc = savedInstanceState.getInt(USER_PERCENT);
		}
		
		editTip10 = (EditText)findViewById(R.id.editTip10);
		editTip15 = (EditText)findViewById(R.id.editTip15);
		editTip20 = (EditText)findViewById(R.id.editTip20);
		
		editTotal10 = (EditText)findViewById(R.id.editTotal10);
		editTotal15 = (EditText)findViewById(R.id.editTotal15);
		editTotal20 = (EditText)findViewById(R.id.editTotal20);
		
		textUserTipProc = (TextView)findViewById(R.id.textUserTipProc);
		editInputBillTotal = (EditText)findViewById(R.id.editBillTotal);
		editTipValue = (EditText)findViewById(R.id.editTipValue);
		editTotalValue = (EditText)findViewById(R.id.editTotalValue);
		
		editInputBillTotal.addTextChangedListener(this);
		seekBarUserProc = (SeekBar)findViewById(R.id.seekBarUserProc);
		seekBarUserProc.setOnSeekBarChangeListener(this);
	}

	void updateStaticProcValues() {
		double totalBill = Double.parseDouble(editInputBillTotal.getText().toString());
		double tip10 = totalBill * 0.1;
		double tip15 = totalBill * 0.15;
		double tip20 = totalBill * 0.20;
		editTip10.setText(String.format(" %.02f", tip10));
		editTip15.setText(String.format(" %.02f", tip15));
		editTip20.setText(String.format(" %.02f", tip20));
		
		editTotal10.setText(String.format(" %.02f", totalBill + tip10));
		editTotal15.setText(String.format(" %.02f", totalBill + tip15));
		editTotal20.setText(String.format(" %.02f", totalBill + tip20));
	}
	
	void updateUserProcValues() {
		int seekBarValue = seekBarUserProc.getProgress();
		textUserTipProc.setText(String.valueOf(seekBarValue));
		double totalBill = Double.parseDouble(editInputBillTotal.getText().toString());
		double userTipValue = totalBill * seekBarValue * 0.01;
		editTipValue.setText(String.format(" %.02f", userTipValue));
		editTotalValue.setText(String.format(" %.02f", totalBill + userTipValue));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		double totalBill = Double.parseDouble(editInputBillTotal.getText().toString());
		int seekBarValue = seekBarUserProc.getProgress();
		outState.putDouble(BILL_TOTAL, totalBill);
		outState.putInt(USER_PERCENT, seekBarValue);
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		updateUserProcValues();
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		try {
			updateStaticProcValues();
			updateUserProcValues();
		}
		catch (NumberFormatException e) {
			editInputBillTotal.setText("0.0");
		}
	}}
