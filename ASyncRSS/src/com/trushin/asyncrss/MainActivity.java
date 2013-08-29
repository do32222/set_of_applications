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
	ArrayAdapter<String> adapterItems;	//адаптер listItems
	ListView listItems;					//список отображаемых элементов RSS
	TextView textViewDownloadingStatus;	//поле для отображения статуса загрузки xml
	RssDownload downloadTask;			//асинхронная задача для загрузки xml
	SaxRssParser parser;				
	List<RssItem> rssItems;				//список элементов RSS представленный объектами класса RssItem
	String currentItem;					//текущий выбранный элемент в списке элементов RSS
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
		downloadTask = new RssDownload();						//создаем задачу для асинхронной загрузки
		downloadTask.execute("http://news.yandex.ru/auto.rss");	//запускаем задачу при создании активити
		mBanner = (AWView) findViewById(R.id.bvBannerMain);
	}

	@Override
	protected void onResume()
	{
	super.onResume();
	mBanner.request('1',"hello,world");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//задача скачивания xml, которая будет исполняться в отдельном от ui потоке
	class RssDownload extends AsyncTask<String, Integer, Void> {
	    
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();
	      textViewDownloadingStatus.setText("The list is filling...");	//сообщаем, что идет загрузка
	    }

	    @Override
	    protected Void doInBackground(String... urls) {
	      try {
	    	  String tempUrl = "";
	    	  if(urls != null && urls.length > 0)
	    		  tempUrl = urls[0];				//передали единственную ссылку
	          parser = new SaxRssParser(tempUrl);	//создаем SAX парсер
	          rssItems = parser.parse();			//получаем содержимое по ссылке и сразу парсим
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
	      if(rssItems.size() >1) {					//первый элемент в тэге chanel
	    	  for(int i=1;i<rssItems.size();i++) {
	    		  adapterItems.add(rssItems.get(i).toString());	//добавляем title из каждого блока item в адаптер
	    	  }
	    	  ((ArrayAdapter)listItems.getAdapter()).notifyDataSetChanged();
	    	  textViewDownloadingStatus.setText("Building a list is complited");	//сообщаем, что загрузка завершена
	      }
	    }
	  }
	
	public interface RssParser {
	    List<RssItem> parse();
	}
	
	//класс под содержимое item
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
	    	return sb.append(title).append("\n").append(description).append("\n\n Источник: \n").append(link.toString()).toString();
	    }
	}
	
	//абстрактный класс, реализующий интерфейс парсера. абстрактный, потому что кроме SAX есть другие варианты
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
	            RssHandler handler = new RssHandler();			//парсинг в обработчике
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
		currentItem = (listItems.getItemAtPosition(position)).toString(); //по клику на item запоминаем текст этого item
		String sendStr = "";
		Intent intent = new Intent(this, RssItemActivity.class);
		for(int i=0;i<rssItems.size();i++) {
			if(rssItems.get(i).toString().equals(currentItem))			//ищем выделенный item
				sendStr = new String(rssItems.get(i).getContent());		//формируем строку для передачи в другое активити
		}
		intent.putExtra("paramContent", sendStr);
	    startActivity(intent);
	}
}
