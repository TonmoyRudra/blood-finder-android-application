package com.tonmoy.bloodfinder.maphospital;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tonmoy.bloodfinder.R;

public class PlacesDisplayTask extends
		AsyncTask<Object, Integer, List<HashMap<String, String>>> {

	JSONObject googlePlacesJson;
	GoogleMap googleMap;
	LatLng latLng;

	@Override
	protected List<HashMap<String, String>> doInBackground(Object... inputObj) {

		List<HashMap<String, String>> googlePlacesList = null;
		Places placeJsonParser = new Places();

		try {
			googleMap = (GoogleMap) inputObj[0];
			googlePlacesJson = new JSONObject((String) inputObj[1]);
			latLng = (LatLng) inputObj[2];
			googlePlacesList = placeJsonParser.parse(googlePlacesJson);
			Log.i("test", googlePlacesList +"");
		} catch (Exception e) {
			Log.d("Exception", e.toString());
		}
		return googlePlacesList;
	}

	@Override
	protected void onPostExecute(List<HashMap<String, String>> list) {
		for (int i = 0; i < list.size(); i++) {
			MarkerOptions markerOptions = new MarkerOptions();
			HashMap<String, String> googlePlace = list.get(i);
			double lat = Double.parseDouble(googlePlace.get("lat"));
			double lng = Double.parseDouble(googlePlace.get("lng"));

			String placeName = googlePlace.get("place_name");
			String vicinity = googlePlace.get("vicinity");

			markerOptions.position(new LatLng(lat, lng));
			markerOptions.title(placeName + " : " + vicinity);
			markerOptions.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.hospital_marker));
			googleMap.addMarker(markerOptions);
		}
		
	}
}
