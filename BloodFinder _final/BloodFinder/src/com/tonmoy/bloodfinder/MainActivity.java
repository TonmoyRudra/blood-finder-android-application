package com.tonmoy.bloodfinder;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button donerList, mapButton, regButton,faqButton, devButton;
	ImageView  actionImageView, dev_ImageView;
	TextView actionTextView;
	ActionBar actionBar;
	Typeface tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		donerList = (Button) findViewById(R.id.donerList);
		mapButton = (Button) findViewById(R.id.mapButton);
		regButton = (Button) findViewById(R.id.regButton);
		faqButton = (Button) findViewById(R.id.faqButton);
		devButton = (Button) findViewById(R.id.devButton);
		
		dev_ImageView = (ImageView) findViewById(R.id.dev_WebView);
		
		actionImageView = (ImageView) findViewById(R.id.imageView1);
		actionTextView = (TextView) findViewById(R.id.textView1);
		actionBar=getActionBar();
		actionBar.hide();

		tf = Typeface.createFromAsset(this.getAssets(), "Agatha-Regular.ttf");
		actionTextView.setTypeface(tf, Typeface.BOLD);
		
		donerList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this,DonerListActivity.class);
				startActivity(intent);
			}
		});
		
		mapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this,MapActivity.class);
				startActivity(intent);
			}
		});
		regButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this, RegActivity.class);
				startActivity(intent);
				
			}
		});
		faqButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FAQActivity.class);
				startActivity(intent);
			}
		});
		
		devButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,DevloperActivity.class);
				startActivity(intent);
				
				
				
			}
		});
	}

}
