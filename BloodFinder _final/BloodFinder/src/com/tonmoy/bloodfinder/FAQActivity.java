package com.tonmoy.bloodfinder;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FAQActivity extends Activity {

	//WebView webView;
	ListView faqListView;
	ImageView  actionImageView;
	TextView actionTextView;
	ActionBar actionBar;
	Typeface tf;
	Typeface tfBangla;
	
	
	ArrayList<String> faqList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq);
		
		actionImageView = (ImageView) findViewById(R.id.imageView1);
		actionTextView = (TextView) findViewById(R.id.textView1);
		//webView = (WebView) findViewById(R.id.webView1);
		faqListView = (ListView) findViewById(R.id.faq_ListView);
		
		actionBar=getActionBar();
		actionBar.hide();

		tf = Typeface.createFromAsset(this.getAssets(), "Agatha-Regular.ttf");
		actionTextView.setTypeface(tf, Typeface.BOLD);
	     
//		webView.getSettings().getJavaScriptEnabled();
//	    webView.getSettings().getBuiltInZoomControls();   
//	    webView.loadUrl("file:///android_asset/www/faq.html");
		
		tfBangla = Typeface.createFromAsset(this.getAssets(), "SIYAMRUPALI.TTF");
		
		
		faqList= new ArrayList<String>();
		
		faqList.add("১। রক্তদানের  উপকারিতা");
		faqList.add("২। রক্ত দানের যোগ্যতা");
		faqList.add("৩। যারা রক্ত দান করতে পারবেন");
		faqList.add("৪। রক্তের গ্রুপ  সম্পর্কে ধারণা");
		faqList.add("৫। রক্তদানের আগে ও পরে করনীয়");
		faqList.add("৬। রক্তদাতাদের কিছু সাবধানতা ");
		faqList.add("৭। রক্তদান সম্পর্কিত কিছু ভুল ধারণা।"); 
		CustomAdapter adapter = new CustomAdapter(this,faqList);
		faqListView.setAdapter(adapter);

		
		faqListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3)
			{
				
				Intent intent = new Intent(FAQActivity.this,WebViewActivity.class);
				intent.putExtra("Key", position);
				startActivity(intent);
				//Log.i("test", "reched");
				
			}
		});
		
		
		
	}


}
