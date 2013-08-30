package com.trushin.asyncrss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ru.ideast.adwired.AWView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener{

	private AWView mBanner;
	ArrayAdapter<String> adapterItems;	//àäàïòåð listItems
	ListView listItems;					//ñïèñîê îòîáðàæàåìûõ ýëåìåíòîâ RSS
	TextView textViewDownloadingStatus;	//ïîëå äëÿ îòîáðàæåíèÿ ñòàòóñà çàãðóçêè xml
	RssDownload downloadTask;			//àñèíõðîííàÿ çàäà÷à äëÿ çàãðóçêè xml
	SaxRssParser parser;				
	List<RssItem> rssItems;				//ñïèñîê ýëåìåíòîâ RSS ïðåäñòàâëåííûé îáúåêòàìè êëàññà RssItem
	String currentItem;					//òåêóùèé âûáðàííûé ýëåìåíò â ñïèñêå ýëåìåíòîâ RSS
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		listItems = (ListView)findViewById(R.id.listItems);
		adapterItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		adapterItems.clear();
		adapterItems.notifyDataSetChanged();
		listItems.setAdapter(adapterItems);
		listItems.setClickable(true);
		listItems.setOnItemClickListener(this); 		
		rssItems = new ArrayList<RssItem>();
		textViewDownloadingStatus = (TextView)findViewById(R.id.textViewDownloadingStatus);
		downloadTask = new RssDownload();						//ñîçäàåì çàäà÷ó äëÿ àñèíõðîííîé çàãðóçêè
		downloadTask.execute("http://news.yandex.ru/auto.rss");	//çàïóñêàåì çàäà÷ó ïðè ñîçäàíèè àêòèâèòè
		mBanner = (AWView) findViewById(R.id.bvBannerMain);
	}

	@Override
	protected void onResume()
	{
	super.onResume();
	postData();
	mBanner.request('1');
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//çàäà÷à ñêà÷èâàíèÿ xml, êîòîðàÿ áóäåò èñïîëíÿòüñÿ â îòäåëüíîì îò ui ïîòîêå
	class RssDownload extends AsyncTask<String, Integer, Void> {
	    
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();
	      textViewDownloadingStatus.setText("The list is filling...");	//ñîîáùàåì, ÷òî èäåò çàãðóçêà
	    }

	    @Override
	    protected Void doInBackground(String... urls) {
	      try {
	    	  String tempUrl = "";
	    	  if(urls != null && urls.length > 0)
	    		  tempUrl = urls[0];				//ïåðåäàëè åäèíñòâåííóþ ññûëêó
	          parser = new SaxRssParser(tempUrl);	//ñîçäàåì SAX ïàðñåð
	          rssItems = parser.parse();			//ïîëó÷àåì ñîäåðæèìîå ïî ññûëêå è ñðàçó ïàðñèì
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	      return null;
	    }


	    @Override
	    protected void onProgressUpdate(Integer... values) {
	      super.onProgressUpdate(values);
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	      super.onPostExecute(result);
	      if(rssItems.size() >1) {					//ïåðâûé ýëåìåíò â òýãå chanel
	    	  for(int i=1;i<rssItems.size();i++) {
	    		  adapterItems.add(rssItems.get(i).toString());	//äîáàâëÿåì title èç êàæäîãî áëîêà item â àäàïòåð
	    	  }
	    	  ((ArrayAdapter)listItems.getAdapter()).notifyDataSetChanged();
	    	  textViewDownloadingStatus.setText("Building a list is complited");	//ñîîáùàåì, ÷òî çàãðóçêà çàâåðøåíà
	      }
	    }
	  }
	
	public interface RssParser {
	    List<RssItem> parse();
	}
	
	//êëàññ ïîä ñîäåðæèìîå item
	public class RssItem {
		private String title;
	    private URL link;
	    private String description;
	    private Date date;
	    
	    public void setLink(String paramLink) {
	        try {
	            link = new URL(paramLink);
	        } catch (MalformedURLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	    public String getLink() {
	    	return link.toString();
	    }
	    
	    public void setDescription(String paramDescription) {
	    	description = new String(paramDescription);
	    }
	    
	    public String getDescription() {
	    	return description;
	    }
	    
	    public void setTitle(String paramTitle) {
	    	title = paramTitle;
	    }
	    
	    public String getTitle() {
	    	return title;
	    }
	    
	    @Override
	    public String toString() {
	    	return title;
	    }
	    
	    public String getContent() {
	    	StringBuilder sb = new StringBuilder();
	    	return sb.append(title).append("\n").append(description).append("\n\n Èñòî÷íèê: \n").append(link.toString()).toString();
	    }
	}
	
	//àáñòðàêòíûé êëàññ, ðåàëèçóþùèé èíòåðôåéñ ïàðñåðà. àáñòðàêòíûé, ïîòîìó ÷òî êðîìå SAX åñòü äðóãèå âàðèàíòû
	public abstract class BaseRssParser implements RssParser {
	    
	    final URL rssUrl;

	    protected BaseRssParser(String paramRssUrl){
	        try {
	            rssUrl = new URL(paramRssUrl);
	        } catch (MalformedURLException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    protected InputStream getInputStream() {
	        try {
	            return rssUrl.openConnection().getInputStream();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	}
	
	public class SaxRssParser extends BaseRssParser {

	    protected SaxRssParser(String paramRssUrl){
	        super(paramRssUrl);
	    }
	    
	    public List<RssItem> parse() {
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	        try {
	            SAXParser parser = factory.newSAXParser();
	            RssHandler handler = new RssHandler();			//ïàðñèíã â îáðàáîò÷èêå
	            parser.parse(this.getInputStream(), handler);
	            return handler.getMessages();
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        } 
	    }
	}
	
	public class RssHandler extends DefaultHandler{
	    private List<RssItem> messages;
	    private RssItem currentMessage;
	    private StringBuilder builder;
	    
	    public List<RssItem> getMessages(){
	        return this.messages;
	    }
	    @Override
	    public void characters(char[] ch, int start, int length)
	            throws SAXException {
	        super.characters(ch, start, length);
	        builder.append(ch, start, length);
	    }

	    @Override
	    public void endElement(String uri, String localName, String name)
	            throws SAXException {
	        super.endElement(uri, localName, name);
	        if (this.currentMessage != null){
	            if (localName.equalsIgnoreCase("TITLE")){
	                currentMessage.setTitle(builder.toString());
	            } else if (localName.equalsIgnoreCase("LINK")){
	                currentMessage.setLink(builder.toString());
	            } else if (localName.equalsIgnoreCase("DESCRIPTION")){
	                currentMessage.setDescription(builder.toString());
	            } else if (localName.equalsIgnoreCase("ITEM")){
	                messages.add(currentMessage);
	            }
	            builder.setLength(0);    
	        }
	    }

	    @Override
	    public void startDocument() throws SAXException {
	        super.startDocument();
	        messages = new ArrayList<RssItem>();
	        builder = new StringBuilder();
	    }

	    @Override
	    public void startElement(String uri, String localName, String name,
	            Attributes attributes) throws SAXException {
	        super.startElement(uri, localName, name, attributes);
	        if (localName.equalsIgnoreCase("ITEM")){
	            this.currentMessage = new RssItem();
	        }
	    }
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		currentItem = (listItems.getItemAtPosition(position)).toString(); //ïî êëèêó íà item çàïîìèíàåì òåêñò ýòîãî item
		String sendStr = "";
		Intent intent = new Intent(this, RssItemActivity.class);
		for(int i=0;i<rssItems.size();i++) {
			if(rssItems.get(i).toString().equals(currentItem))			//èùåì âûäåëåííûé item
				sendStr = new String(rssItems.get(i).getContent());		//ôîðìèðóåì ñòðîêó äëÿ ïåðåäà÷è â äðóãîå àêòèâèòè
		}
		intent.putExtra("paramContent", sendStr);
	    startActivity(intent);
	}
	
	public void postData() {
		try {
	    // Создадим HttpClient и PostHandler
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://master.adwired.mobi/services/refresh-banners");
        
	        // Добавим данные (пара - "название - значение")
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
	        nameValuePairs.add(new BasicNameValuePair("vnd", "Apple"));
	        nameValuePairs.add(new BasicNameValuePair("mdl", "iPhone"));
	        nameValuePairs.add(new BasicNameValuePair("frw", "2,3,3"));
	        nameValuePairs.add(new BasicNameValuePair("dev", "000000000000000000000000"));
	        nameValuePairs.add(new BasicNameValuePair("awuid", "000000000000000000000000"));
	        nameValuePairs.add(new BasicNameValuePair("app", "com.trushin.asyncrss-android"));
	        nameValuePairs.add(new BasicNameValuePair("ver", "3.0.1"));
	       // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Выполним запрос
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
	        HttpResponse response = httpclient.execute(httppost);
	        
	        String responseString = new String();
	        HttpEntity responseEntity = response.getEntity();
	        if(responseEntity!=null) {
	        	responseString = EntityUtils.toString(responseEntity);
	        	textViewDownloadingStatus.setText(responseString);
	        }    
	    } catch (ClientProtocolException e) {
	    	textViewDownloadingStatus.setText("prorocol");
	    } catch (IOException e) {
	    	textViewDownloadingStatus.setText("io");
	    } catch (Exception e) {
	    	textViewDownloadingStatus.setText("other");
	    }
	} 
}
