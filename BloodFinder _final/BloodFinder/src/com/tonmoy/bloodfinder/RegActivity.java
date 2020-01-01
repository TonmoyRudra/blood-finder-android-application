package com.tonmoy.bloodfinder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.tonmoy.bloodfinder.DonerListActivity.GetFilteredInfo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegActivity extends Activity {

	Button save;
	EditText nameEditText, dobEditText, heightEditText, weightEditText,
			ageEditText, mobileEditText;
	Spinner genderSpinner, bloodGroupSpinner, areaSpinner;
	ImageView actionImageView, refreshImageView;
	TextView actionTextView;

	JSONParser jsonParser = new JSONParser();

	ProgressDialog pDialog;

	private static String url_add_blood_doner_info = "http://blooddonate.web44.net/add_new_blood_info.php";

	String genderString;
	String bloodGroupString;
	String areaString;
	String nameString;
	String heightString;
	String weightString;
	String ageString;
	String mobileString;

	int genderItemPosition = 0;
	int areaItemPosition = 0;
	int bloodgroupItemPosition = 0;

	ActionBar actionBar;
	Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);

		save = (Button) findViewById(R.id.submit_Button);

		nameEditText = (EditText) findViewById(R.id.nameEdittext);
		heightEditText = (EditText) findViewById(R.id.height_EditText);
		weightEditText = (EditText) findViewById(R.id.weight_EditText);
		ageEditText = (EditText) findViewById(R.id.age_EditText);
		mobileEditText = (EditText) findViewById(R.id.mobileNumber_EditText);

		genderSpinner = (Spinner) findViewById(R.id.gender_Spinner);
		bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroup_Spinner);
		areaSpinner = (Spinner) findViewById(R.id.area_Spinner);

		actionImageView = (ImageView) findViewById(R.id.actionBar_ImageView);
		refreshImageView = (ImageView) findViewById(R.id.refreshimageView);
		actionTextView = (TextView) findViewById(R.id.actionBar_textView);
		actionBar = getActionBar();
		actionBar.hide();

		tf = Typeface.createFromAsset(this.getAssets(), "Agatha-Regular.ttf");
		actionTextView.setTypeface(tf, Typeface.BOLD);

		final String[] gender = { "Gender", "Male", "Female" };
		genderSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, gender));

		final String[] BloodGroup = { "Blood Group", "A+", "A-", "B+", "B-",
				"AB+", "AB-", "O+", "O-" };
		bloodGroupSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, BloodGroup));

		final String[] area = getResources().getStringArray(R.array.area);
		areaSpinner.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, area));

		genderSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				genderString = gender[position];
				genderItemPosition = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		bloodGroupSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						bloodGroupString = BloodGroup[position];
						bloodgroupItemPosition = position;
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
				areaItemPosition = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (InternetConnection.haveNetworkConnection(RegActivity.this)) {
					new AddNewBloodInfo().execute();
				} else {
					Toast.makeText(RegActivity.this, "Internet connection is not available.", Toast.LENGTH_SHORT).show();
				}
				

			}
		});

		refreshImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nameEditText.setText("");
				weightEditText.setText("");
				heightEditText.setText("");
				ageEditText.setText("");
				mobileEditText.setText("");
				genderSpinner.setSelection(0);
				areaSpinner.setSelection(0);
				bloodGroupSpinner.setSelection(0);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reg, menu);
		return true;
	}

	class AddNewBloodInfo extends AsyncTask<String, String, String> {
		boolean flag = false;
		String serverResponse;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegActivity.this);
			pDialog.setMessage("Saving data..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			nameString = nameEditText.getText().toString();
			ageString = ageEditText.getText().toString();
			heightString = heightEditText.getText().toString();
			weightString = weightEditText.getText().toString();
			mobileString = mobileEditText.getText().toString();
			if (nameString.length() == 0 || ageString.length() == 0
					|| heightString.length() == 0 || weightString.length() == 0
					|| mobileString.length() == 0 || genderItemPosition == 0
					|| areaItemPosition == 0 || bloodgroupItemPosition == 0) {
				return null;
			}
			flag = true;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", nameString));
			params.add(new BasicNameValuePair("age", ageString));
			params.add(new BasicNameValuePair("height", heightString));
			params.add(new BasicNameValuePair("weight", weightString));
			params.add(new BasicNameValuePair("mobile", mobileString));
			params.add(new BasicNameValuePair("area", areaString));
			params.add(new BasicNameValuePair("bloodgroup", bloodGroupString));
			params.add(new BasicNameValuePair("gender", genderString));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(
					url_add_blood_doner_info, "POST", params);
			// check for success tag
			try {
				int success = json.getInt("success");
				serverResponse = json.getString("message");
				if (success == 1) {
				} else {
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String string) {
			pDialog.dismiss();
			if (!flag) {
				Toast.makeText(RegActivity.this, "Fill all fields correctly", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(RegActivity.this, serverResponse, Toast.LENGTH_SHORT).show();
			}
		}

	}

}
