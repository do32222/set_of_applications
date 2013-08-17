package com.trushin.rss.habrarss;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RssItemActivity extends Activity implements OnClickListener {
	Button goBack;
	TextView textContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_item_activity);
		goBack = (Button)findViewById(R.id.btnBackToRss);
		goBack.setOnClickListener(this);
		textContent = (TextView)findViewById(R.id.textItemContent);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    String value = extras.getString("paramContent");	//извлекаем информацию для отображения из главного активити
		    textContent.setText(value);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
}

class RssItem {
	private String title;
	private String description;
	private String pubDate;
	private String link;
	
	public RssItem() { }
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String paramTitle) {
		title = paramTitle;
	}
	  
	public String getLink() {
		return link;
	}
	
	public void setLink(String paramLink) {
		link = paramLink;
	}
	
	public String getDescription() {
		return description;
	}
	  
	public void setDescription(String paramDescription) {
		description = paramDescription;
	}
	
	public String getPubDate() {
	  return pubDate;
	}
	
	public void setPubDate(Date paramDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss"); 
		pubDate = sdf.format(paramDate);
	}
	
	@Override
	public String toString() {
	    return  title + "\n  ( " + this.pubDate + " )\n" + description;
	}
	
	public static ArrayList<RssItem> getRssItems(String feedUrl) {
	    
		ArrayList<RssItem> rssItems = new ArrayList<RssItem>();

	    try {
	      //open an URL connection make GET to the server and
	      //take xml RSS data
	      URL url = new URL(feedUrl);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	      if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	        InputStream is = conn.getInputStream();
	        //DocumentBuilderFactory, DocumentBuilder are used for
	        //xml parsing
	        DocumentBuilderFactory dbf = DocumentBuilderFactory
	            .newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();

	        //using db (Document Builder) parse xml data and assign
	        //it to Element
	        Document document = db.parse(is);
	        Element element = document.getDocumentElement();
	        //take rss nodes to NodeList
	        NodeList nodeList = element.getElementsByTagName("item");

	        if (nodeList.getLength() > 0) {
	          for (int i = 0; i < nodeList.getLength(); i++) {
	            //take each entry (corresponds to <item></item> tags in
	            //xml data
	            Element entry = (Element) nodeList.item(i);

	            Element _titleE = (Element) entry.getElementsByTagName(
	                "title").item(0);
	            Element _descriptionE = (Element) entry
	                .getElementsByTagName("description").item(0);
	            Element _pubDateE = (Element) entry
	                .getElementsByTagName("pubDate").item(0);
	            Element _linkE = (Element) entry.getElementsByTagName(
	                "link").item(0);

	            String _title = _titleE.getFirstChild().getNodeValue();
	            String _description = _descriptionE.getFirstChild().getNodeValue();
	            Date _pubDate = new Date(_pubDateE.getFirstChild().getNodeValue());
	            String _link = _linkE.getFirstChild().getNodeValue();

	            //create RssItemObject and add it to the ArrayList
	            RssItem rssItem = new RssItem();
	            rssItem.setTitle(_title);
	            rssItem.setLink(_link);
	            rssItem.setPubDate(_pubDate);
	            rssItem.setDescription(_description);

	            rssItems.add(rssItem);
	          }
	        }
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return rssItems;
	  }
}
