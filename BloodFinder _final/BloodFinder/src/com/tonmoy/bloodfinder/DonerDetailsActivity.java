package com.tonmoy.bloodfinder;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DonerDetailsActivity extends Activity {

	TextView nameTextView, ageTextView, heighTextView, weighTextView, areaTextView, bloodGropuTextView, mobileTextView,genderTextView;
	ImageView actionImageView , callImageView;
	TextView actionTextview;
	ActionBar actionBar;
	Typeface tf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doner_details);
		
		nameTextView = (TextView) findViewById(R.id.nameTextView2);
		ageTextView = (TextView) findViewById(R.id.ageTextView2);
		heighTextView = (TextView) findViewById(R.id.heightTextView2);
		weighTextView = (TextView) findViewById(R.id.weightTextView2);
		bloodGropuTextView = (TextView) findViewById(R.id.bloodGroupTextView2);
		mobileTextView = (TextView) findViewById(R.id.mobileTextView2);
		areaTextView = (TextView) findViewById(R.id.areaTextView2);
		genderTextView = (TextView) findViewById(R.id.genderTextView2);
		
		
		actionImageView = (ImageView) findViewById(R.id.actionBar_ImageView);
		callImageView = (ImageView) findViewById(R.id.CallImageView);
		actionTextview = (TextView) findViewById(R.id.actionBar_textView);
		
		actionBar = getActionBar();
		actionBar.hide();
		
		tf = Typeface.createFromAsset(this.getAssets(), "Agatha-Regular.ttf");
		actionTextview.setTypeface(tf, Typeface.BOLD);
		
		
		
		Intent intent = getIntent();
		
		nameTextView.setText(intent.getStringExtra("name"));
		ageTextView.setText(intent.getStringExtra("age") + " years");
		heighTextView.setText(intent.getStringExtra("height") + " inch");
		weighTextView.setText(intent.getStringExtra("weight") + " Kg");
		bloodGropuTextView.setText(intent.getStringExtra("bloodgroup"));
		mobileTextView.setText(intent.getStringExtra("mobile"));
		areaTextView.setText(intent.getStringExtra("area"));
		genderTextView.setText(intent.getStringExtra("gender"));
		
		actionTextview.setText(intent.getStringExtra("name"));
		final String mobile =  intent.getStringExtra("mobile");
		
		callImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+mobile));
				Log.i("test", mobile);
				startActivity(intent);
			}
		});
	}
}
