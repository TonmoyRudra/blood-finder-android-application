package com.tonmoy.bloodfinder;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DevloperActivity extends Activity {

	WebView devWebView;
	
	ImageView  actionImageView;
	TextView actionTextView;
	ActionBar actionBar;
	Typeface tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devloper);
		devWebView = (WebView) findViewById(R.id.dev_WebView);
		
		actionImageView = (ImageView) findViewById(R.id.imageView1);
		actionTextView = (TextView) findViewById(R.id.textView1);
		actionBar=getActionBar();
		actionBar.hide();

		tf = Typeface.createFromAsset(this.getAssets(), "Agatha-Regular.ttf");
		actionTextView.setTypeface(tf, Typeface.BOLD);
		
		devWebView.loadUrl("file:///android_asset/www/About_developer_new.html");
		
	}

}
