package com.tonmoy.bloodfinder;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{

	ArrayList<String> faqNameArrayList;
	Typeface tfBangla;
	//Context context;
	private Activity context;
	LayoutInflater inflater;

	public CustomAdapter(Activity context,ArrayList<String> faqNameArrayList) {
		this.context= context;
		this.faqNameArrayList = faqNameArrayList;
		tfBangla = Typeface.createFromAsset(context.getAssets(), "SIYAMRUPALI.TTF");
		//inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return faqNameArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//convertView = inflater.inflate(R.layout.listitem, null);
		convertView =LayoutInflater.from(context).inflate(R.layout.listitem, parent, false);
		TextView nameTextView = (TextView) convertView.findViewById(R.id.faqName);
		nameTextView.setTypeface(tfBangla);
	
	     
		
		String name = faqNameArrayList.get(position);
		nameTextView.setText(name);
		
		
		return convertView;
		
	}

}
