package com.tr.listviewvog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		 final ListView mainListView = (ListView) findViewById(R.id.mainListView);
		 String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
		        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
		        "Android", "iPhone", "WindowsMobile" };
		 
		 ArrayList<String> phoneModels = new ArrayList<String>();
		 Collections.addAll(phoneModels, values);
		 
		 mainListView.setOnItemClickListener(new OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			    Toast.makeText(getApplicationContext(),
			      "Click ListItem Number " + position, Toast.LENGTH_LONG)
			      .show();
			  }
		 }); 
		 
		 StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, phoneModels);
		 mainListView.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class StableArrayAdapter extends ArrayAdapter<String> {
		
		HashMap<String, Integer> mIdMap;
		public StableArrayAdapter(Context context, int textViewResourceId, 
				List<String> objects) {
			super(context, textViewResourceId, objects);
			mIdMap = new HashMap<String, Integer>();
			for(int i=0;i<objects.size();i++) {
				mIdMap.put(objects.get(i), i);
			}
		}
		
		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}
		
		@Override
		public boolean hasStableIds() {
			return true;
		}
	}
}

