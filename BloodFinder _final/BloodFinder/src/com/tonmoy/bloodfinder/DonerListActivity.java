package com.tonmoy.bloodfinder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DonerListActivity extends Activity {

	ImageView actionImageView, refreshImageView;
	TextView actionTextView;
	ActionBar actionBar;
	Typeface tf;
	
	List<DonerInfo> infos;
	
	JSONParser jsonParser = new JSONParser();
	private JSONArray donerInfo;
	
	private static String url_add_blood_doner_info = "http://blooddonate.web44.net/get_all_data.php";
	private static String url_get_filtered_blood_info = "http://blooddonate.web44.net/get_specific_blood_info.php";
	private static final String TAG_DONER_INFO = "donerInfo";
	
	ProgressDialog pDialog;
	
	String bloodgroupString;
	String areaString;

	Spinner bloodgroupSpinner, areaSpinner;
	Button filterButton;
	TextView name, bg, area;
	ListView dataListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donerlist_activity);

		actionImageView = (ImageView) findViewById(R.id.actionBar_ImageView);
		refreshImageView = (ImageView) findViewById(R.id.refreshimageView);
		actionTextView = (TextView) findViewById(R.id.actionText_view);
		actionBar = getActionBar();
		actionBar.hide();

		tf = Typeface.createFromAsset(this.getAssets(), "Agatha-Regular.ttf");
		actionTextView.setTypeface(tf, Typeface.BOLD);

		bloodgroupSpinner = (Spinner) findViewById(R.id.blood_group_Spinner);
		areaSpinner = (Spinner) findViewById(R.id.area_Spinner);

		filterButton = (Button) findViewById(R.id.filter_Button);
		dataListView = (ListView) findViewById(R.id.data_ListView);

		final String[] BloodGroup = { "Blood Group", "A+", "A-", "B+", "B-", "AB+",
				"AB-", "O+", "O-" };
		bloodgroupSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, BloodGroup));

		final String[] area = getResources().getStringArray(R.array.area);
		areaSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, area));

		bloodgroupSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				bloodgroupString = BloodGroup[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		areaSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				areaString = area[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		filterButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (InternetConnection.haveNetworkConnection(DonerListActivity.this)) {
					new GetFilteredInfo().execute();
				} else {
					Toast.makeText(DonerListActivity.this, "Internet connection is not available.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		refreshImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (InternetConnection.haveNetworkConnection(DonerListActivity.this)) {
					new GetBloodInfo().execute();
				} else {
					Toast.makeText(DonerListActivity.this, "Internet connection is not available.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		dataListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(DonerListActivity.this, DonerDetailsActivity.class);
				intent.putExtra("name", infos.get(position).getName());
				intent.putExtra("height", infos.get(position).getHeight());
				intent.putExtra("weight", infos.get(position).getWeight());
				intent.putExtra("age", infos.get(position).getAge());
				intent.putExtra("bloodgroup", infos.get(position).getBloodgroup());
				intent.putExtra("area", infos.get(position).getPlace());
				intent.putExtra("mobile", infos.get(position).getNumber());
				intent.putExtra("gender", infos.get(position).getGender());
				
				startActivity(intent);
				
			}
		
		});
		

		if (InternetConnection.haveNetworkConnection(this)) {
			new GetBloodInfo().execute();
		} else {
			Toast.makeText(this, "Internet connection is not available.", Toast.LENGTH_SHORT).show();
		}
		
	}

	class GetBloodInfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute(
					);
			pDialog = new ProgressDialog(DonerListActivity.this);
			pDialog.setMessage("Loading Donor List...Please wait a moment");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
			infos = new ArrayList<DonerInfo>();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			JSONObject json = jsonParser.makeHttpRequest(
					url_add_blood_doner_info, "POST", params);
			
			
			try {
				int success = json.getInt("success");
				Log.i("test", json.toString());
				if (success == 1) {
					donerInfo = json.getJSONArray(TAG_DONER_INFO);

					for (int i = 0; i < donerInfo.length(); i++) {
						JSONObject c = donerInfo.getJSONObject(i);
						
						DonerInfo info = new DonerInfo();
						info.setName(c.getString("name"));
						info.setBloodgroup(c.getString("bloodgroup"));
						info.setPlace(c.getString("area"));
						info.setAge(c.getString("age"));
						info.setHeight(c.getString("height"));
						info.setWeight(c.getString("weight"));
						info.setNumber(c.getString("mobile"));
						info.setGender(c.getString("gender"));
						
						
						infos.add(info);
					}
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();
			dataListView.setAdapter(new CustomAdapter());
		}

	}
	
	class GetFilteredInfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DonerListActivity.this);
			pDialog.setMessage("Loading FilterLIst...please Wait a moment");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(String... args) {
			infos = new ArrayList<DonerInfo>();
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("area", areaString));
			params.add(new BasicNameValuePair("bloodgroup", bloodgroupString));
			
			JSONObject json = jsonParser.makeHttpRequest(
					url_get_filtered_blood_info, "GET", params);
			
			try {
				int success = json.getInt("success");
				if (success == 1) {
					donerInfo = json.getJSONArray(TAG_DONER_INFO);

					for (int i = 0; i < donerInfo.length(); i++) {
						JSONObject c = donerInfo.getJSONObject(i);
						
						DonerInfo info = new DonerInfo();
						info.setName(c.getString("name"));
						info.setBloodgroup(c.getString("bloodgroup"));
						info.setPlace(c.getString("area"));
						info.setAge(c.getString("age"));
						info.setHeight(c.getString("height"));
						info.setWeight(c.getString("weight"));
						info.setNumber(c.getString("mobile"));
						info.setGender(c.getString("gender"));
						
						infos.add(info);
					}
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();
			Log.i("test", infos.toString());
			dataListView.setAdapter(new CustomAdapter());
		}

	}

	class CustomAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return infos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView nameTextView;
			TextView bloodgroupTextView;
			TextView areaTextView;
			if (convertView == null) {
				LayoutInflater inflater = DonerListActivity.this
						.getLayoutInflater();
				convertView = inflater.inflate(R.layout.listitem_donerlist,
						null);
				nameTextView = (TextView) convertView
						.findViewById(R.id.nameListTextview);
				bloodgroupTextView = (TextView) convertView
						.findViewById(R.id.bloodGroupListTextView);
				areaTextView = (TextView) convertView
						.findViewById(R.id.areaListTextView);
			} else {
				nameTextView = (TextView) convertView
						.findViewById(R.id.nameListTextview);
				bloodgroupTextView = (TextView) convertView
						.findViewById(R.id.bloodGroupListTextView);
				areaTextView = (TextView) convertView
						.findViewById(R.id.areaListTextView);
			}
			
			nameTextView.setText(infos.get(position).getName());
			bloodgroupTextView.setText(infos.get(position).getBloodgroup());
			areaTextView.setText(infos.get(position).getPlace());

			return convertView;
		}

	}

	class DonerInfo {
		String name;
		String weight;
		String age;
		String number; 
		String bloodgroup;
		String place;
		String height;
		String gender;
		
		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getHeight() {
			return height;
		}

		public void setHeight(String height) {
			this.height = height;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getBloodgroup() {
			return bloodgroup;
		}

		public void setBloodgroup(String bloodgroup) {
			this.bloodgroup = bloodgroup;
		}

		public String getPlace() {
			return place;
		}

		public void setPlace(String place) {
			this.place = place;
		}
	}

}
