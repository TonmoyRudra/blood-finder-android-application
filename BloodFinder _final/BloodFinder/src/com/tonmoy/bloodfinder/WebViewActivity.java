package com.tonmoy.bloodfinder;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class WebViewActivity extends Activity{
	WebView webView; 
	
	
	ImageView  actionImageView;
	TextView actionTextView;
	ActionBar actionBar;
	Typeface tf;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);

		webView = (WebView) findViewById(R.id.faq_webView);
		
		actionImageView = (ImageView) findViewById(R.id.imageView1);
		actionTextView = (TextView) findViewById(R.id.textView1);
		actionBar=getActionBar();
		actionBar.hide();

		tf = Typeface.createFromAsset(this.getAssets(), "Agatha-Regular.ttf");
		actionTextView.setTypeface(tf, Typeface.BOLD); 
		
		//String dataString = getIntent().getExtras().getString("Key");
		
		Intent intent =getIntent();
		int position =intent.getIntExtra("Key", -1);
		
		if (position == 0) {
			webView.loadUrl("file:///android_asset/www/benifit_of_blood.html");
		}
		else if (position == 1) {
			webView.loadUrl("file:///android_asset/www/joggota.html");
		}
		else if (position == 2) {
			webView.loadUrl("file:///android_asset/www/who_donate_blood.html");
		}
		else if (position == 3) {
			webView.loadUrl("file:///android_asset/www/group/Blood_group.html");
		}
		else if (position == 4) {
			webView.loadUrl("file:///android_asset/www/after_and_before_blood.html");
		}
		else if (position == 5) {
			webView.loadUrl("file:///android_asset/www/sabdhanota.html");
		}
		else if (position == 6) {
			webView.loadUrl("file:///android_asset/www/negetive_sence.html");
		}
		
		
	}

	
}
