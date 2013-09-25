package com.tr.webclient;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

	WebView mWebView;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	            
	    mWebView = (WebView) findViewById(R.id.webview);
	    mWebView.setWebViewClient(new HelloWebViewClient());
		// включаем поддержку JavaScript
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    
	    String data = "<link rel=\"stylesheet\" href=\"http://static-testdev.adwired.mobi/content/tests/adv2.css\" type=\"text/css\" media=\"screen\" />" +
"<script src=\"http://static-testdev.adwired.mobi/content/tests/adv2.js\"></script" + 

"<iframe src=\"http://jollytail.ru/\" width=\"50\" height=\"50\" align=\"left\"> " +
"<table id=\"PortTable\">" + 
	"<tr>	 " + 
	"	<td><input type=\"button\" value=\"CLOSE\" onClick=\"document.location='adwired:close;'\"></td>	" + 
	"	<td><input type=\"button\" value=\"CANCEL\" onClick=\"document.location='adwired:cancel;'\"></td>	" + 
	"</tr>" + 

	"<tr>	" + 
	"	<td><a href=\"adwired:tel;+7 (495) 555-0102\">CALL</a></td>	" + 
	"	<td><a href=\"adwired:map;48.708621,44.524562\">MAP</a></td>	" + 
	"</tr>" + 

	"<tr>	" + 
	"	<td><a href=\"adwired:callback;max@adwired.net\">CALLBACK</a></td>	" + 
	"	<td><a href=\"adwired:mailto;max@adwired.net\">MAIL TO</a></td>	" + 
	"</tr>" + 

	"<tr>	" + 
	"	<td><a href=\"adwired:video;http://static.adwired.mobi/content/video/d4_10_droid.mp4\">VIDEO</a></td>" + 
	"	<td><a href=\"adwired:http;http://adwired.net\">ADWIRED HTTP</a></td>" + 
	"</tr>" + 

	"<tr>	" + 
	"	<td><input type=\"button\" value=\"ADWIRED OnClick\" onClick=\"document.location='http://adwired.net'\"></td>" + 
	"	<td><a href=\"http://adwired.net\">ADWIRED</a></td>" + 
	"</tr>" + 
"</table>";
	    String data1= "<iframe src=\"http://jollytail.ru/\" width=\"600\" height=\"600\" align=\"left\"> ";

		// указываем страницу загрузки
	    mWebView.loadData(data1, "text/html", "utf-8");
	    //mWebView.loadUrl(\"http://developer.alexanderklimov.ru/android\"); 
	}
	
	private class HelloWebViewClient extends WebViewClient 
	{
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) 
	    {
	        view.loadUrl(url);
	        return true;
	    }
	}
}

