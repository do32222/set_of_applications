package com.trushin.rss.habrarss;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener {

	public static RssItem selectedRssItem = null;
	String feedUrl = "";
	ListView rssListView = null;
	ArrayList<RssItem> rssItems = new ArrayList<RssItem>();
	ArrayAdapter<RssItem> aa = null;
	String currentItem;
	TextView rssURLTV;
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	  
	  // get textview from our layout.xml
	  rssURLTV = (TextView)findViewById(R.id.textUrl);

	  // get button from layout.xml
	  Button fetchRss = (Button)findViewById(R.id.fetch_rss);

	  // define the action that will be executed when the button is clicked.
	  fetchRss.setOnClickListener(this);
	  // get the listview from layout.xml
	  rssListView = (ListView)findViewById(R.id.listViewRSS);
	  // here we specify what to execute when individual list items clicked
	  rssListView.setOnItemClickListener(this);
	  //adapters are used to populate list. they take a collection,
	  //a view (in our example R.layout.list_item
	  aa = new ArrayAdapter<RssItem>(this, R.layout.list_view_rss_item, rssItems);
	  //here we bind array adapter to the list
	  rssListView.setAdapter(aa);
	  feedUrl = rssURLTV.getText().toString();
	  refressRssList();
}

	  private void refressRssList() {

	    ArrayList<RssItem> newItems = RssItem.getRssItems(feedUrl);

	    rssItems.clear();
	    rssItems.addAll(newItems);
	    aa.notifyDataSetChanged();
	  }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			// TODO Auto-generated method stub
			currentItem = (rssListView.getItemAtPosition(position)).toString(); //по клику на item запоминаем текст этого item
			String sendStr = "";
			Intent intent = new Intent(this, RssItemActivity.class);
			for(int i=0;i<rssItems.size();i++) {
				if(rssItems.get(i).toString().equals(currentItem))			//ищем выделенный item
					sendStr = new String(rssItems.get(i).toString());		//формируем строку для передачи в другое активити
			}
			intent.putExtra("paramContent", sendStr);
		    startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		feedUrl = rssURLTV.getText().toString();
		aa.notifyDataSetChanged();
		refressRssList();
	}


}
