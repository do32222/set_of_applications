package com.trushin.twitter.twittersearch;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends Activity implements OnClickListener, DialogInterface.OnClickListener {

	private SharedPreferences savedSearches;
	private TableLayout tabLayoutQueries;
	private EditText editQuery;
	private EditText editTag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		savedSearches = getSharedPreferences("searches",MODE_PRIVATE);
		tabLayoutQueries = (TableLayout)findViewById(R.id.tabLayoutQueries);
		editQuery = (EditText)findViewById(R.id.editQuery);
		editTag = (EditText)findViewById(R.id.editTag);
		
		Button btnSave = (Button)findViewById(R.id.btnSave);
		Button btnClear = (Button)findViewById(R.id.btnClear);
		btnSave.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		refreshButtons(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	void refreshButtons(String paramNewTag) {
		String[] tags = savedSearches.getAll().keySet().toArray(new String[0]);
		Arrays.sort(tags, String.CASE_INSENSITIVE_ORDER);
		
		if(paramNewTag != null) {
			makeTagGUI(paramNewTag, Arrays.binarySearch(tags, paramNewTag));
		} else {
			for(int i=0;i<tags.length;i++) {
				makeTagGUI(tags[i],i);
			}
		}
	}
	
	void makeTag(String paramQuery, String paramTag) {
		String originalQuery = savedSearches.getString(paramTag, null);
		SharedPreferences.Editor prefEdit = savedSearches.edit();
		prefEdit.putString(paramTag, paramQuery);
		prefEdit.commit();
		
		if(originalQuery == null) {
			refreshButtons(paramTag);
		}
	}
	
	void makeTagGUI(String paramTag, int paramIndex) {
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newTagView = inflater.inflate(R.layout.new_tag_view, null);
		
		Button btnTag =(Button)newTagView.findViewById(R.id.btnTag);
		Button btnEdit = (Button)newTagView.findViewById(R.id.btnEdit);
		btnTag.setText(paramTag);
		btnTag.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
		
		tabLayoutQueries.addView(newTagView, paramIndex);
	}
	
	void clearButtons() {
		tabLayoutQueries.removeAllViews();
	}
	
	void save() {
		String tempQuery = editQuery.getText().toString();
		String tempTag = editTag.getText().toString();
		if(tempQuery.length() > 0 && tempTag.length() > 0) {
			makeTag(tempQuery, tempTag);
			editQuery.setText("");
			editTag.setText("");
			((InputMethodManager) getSystemService(
				Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				editTag.getWindowToken(), 0);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.missingTitle);
			builder.setPositiveButton(R.string.ok, null);
			builder.setMessage(R.string.missingMessage);
		}
	}
	
	void clear() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.confirmTitle);
		builder.setPositiveButton(R.string.erase, this);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.cancel, null);
		builder.setMessage(R.string.confirmMess);
		AlertDialog confirmDialog = builder.create();
		confirmDialog.show();
	}
	
	@Override
	public void onClick(View paramView) {
		// TODO Auto-generated method stub
		switch(paramView.getId()) {
		case R.id.btnSave:
			save();
			break;
		case R.id.btnClear:
			clear();
			break;
		case R.id.btnTag:
			query(paramView);
			break;
		case R.id.btnEdit:
			edit(paramView);
			break;
		default: 
			break;
		}
	}

	void edit(View paramView) {
		TableRow row = (TableRow)paramView.getParent();
		Button btnTag = (Button)row.findViewById(R.id.btnTag);
		String tagStr = btnTag.getText().toString();
		
		editTag.setText(tagStr);
		editQuery.setText(savedSearches.getString(tagStr, null));
	}
	void query(View paramView) {
		String tag = ((Button)paramView).getText().toString();
		String query = savedSearches.getString(tag, null);
		
		String url = getString(R.string.searchUrl) + query;
		Intent getUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(getUrl);
	}
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		clearButtons();
		SharedPreferences.Editor prefEdit = savedSearches.edit();
		prefEdit.clear();
		prefEdit.commit();
	}

}
