package com.trushin.simplecalculator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{

	boolean isShownResult = false; 	//было ли предыдущим шагом отображение результата
	boolean inputFirstArg = true;	//режим ввода первого аргумента (из двух возможных)
	String firstArg = "";
	String secondArg = "";
	String sign = "";
	EditText resultField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultField = (EditText)findViewById(R.id.resultField);
        Button btn0 = (Button)findViewById(R.id.btn0);
        Button btn1 = (Button)findViewById(R.id.btn1);
        Button btn2 = (Button)findViewById(R.id.btn2);
        Button btn3 = (Button)findViewById(R.id.btn3);
        Button btn4 = (Button)findViewById(R.id.btn4);
        Button btn5 = (Button)findViewById(R.id.btn5);
        Button btn6 = (Button)findViewById(R.id.btn6);
        Button btn7 = (Button)findViewById(R.id.btn7);
        Button btn8 = (Button)findViewById(R.id.btn8);
        Button btn9 = (Button)findViewById(R.id.btn9);
        Button btnPlus = (Button)findViewById(R.id.btnPlus);
        Button btnMinus = (Button)findViewById(R.id.btnMinus);
        Button btnMult = (Button)findViewById(R.id.btnMult);
        Button btnDiv = (Button)findViewById(R.id.btnDiv);
        Button btnResult = (Button)findViewById(R.id.btnResult);
        
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnResult.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View paramView) {
			switch (paramView.getId()) {
			case R.id.btn0:
				this.saveValueAndDisplay(0);
				break;
			case R.id.btn1:
				this.saveValueAndDisplay(1);
				break;
			case R.id.btn2:
				this.saveValueAndDisplay(2);
				break;
			case R.id.btn3:
				this.saveValueAndDisplay(3);
				break;
			case R.id.btn4:
				this.saveValueAndDisplay(4);
				break;
			case R.id.btn5:
				this.saveValueAndDisplay(5);
				break;
			case R.id.btn6:
				this.saveValueAndDisplay(6);
				break;
			case R.id.btn7:
				this.saveValueAndDisplay(7);
				break;
			case R.id.btn8:
				this.saveValueAndDisplay(8);
				break;
			case R.id.btn9:
				this.saveValueAndDisplay(9);
				break;
			case R.id.btnPlus:
				this.onSignClick("+");
				break;
			case R.id.btnMinus:
				this.onSignClick("-");
				break;
			case R.id.btnMult:
				this.onSignClick("*");
				break;
			case R.id.btnDiv:
				this.onSignClick("/");
				break;
			case R.id.btnResult:
				this.onResultClick();
				break;
			}
		
	}
    
	void saveValueAndDisplay(int paramVal) {
		if(inputFirstArg) {
			firstArg += paramVal;
			resultField.setText(firstArg);
		}
		else {
			secondArg += paramVal;
			resultField.setText(resultField.getText() + String.valueOf(paramVal));
		}
	}
	
	void onSignClick(String paramSign) {
		if(firstArg !=null && !firstArg.equals("")) {
		if(inputFirstArg) {
			sign = paramSign;
			resultField.setText(firstArg + " " + sign + " ");
			inputFirstArg = false;
		}
		else { //вводили второй аргумент и нажат знак
			firstArg = this.calculateResult();
			sign = paramSign;
			secondArg = "";
			resultField.setText(firstArg + " " + sign + " " );
		}
		}
	}
	
	String calculateResult(){
		if(firstArg != null && secondArg != null && sign != null
				&& !firstArg.equals("") && !secondArg.equals("") && !sign.equals("")) {
			double firstNumb = Double.parseDouble(firstArg);
			double secondNumb = Double.parseDouble(secondArg);
			double result;
			if(sign.equals("+")) {
				result = firstNumb + secondNumb;
			} else if(sign.equals("-")) {
						result = firstNumb - secondNumb;
				   }
				   else if(sign.equals("*")) {
					   		result = firstNumb * secondNumb;
				   	    }
				   		else if(sign.equals("/")) {
				   				result = firstNumb / secondNumb;
				   		}
				   		else result = Double.NaN;
			return String.valueOf(result);		
		}
		return "";
	}
	
	void onResultClick() {
		String tempResult = this.calculateResult();
		resultField.setText(tempResult);
		firstArg = "";
		secondArg = "";
		sign = "";
		inputFirstArg = true;
	}
}
