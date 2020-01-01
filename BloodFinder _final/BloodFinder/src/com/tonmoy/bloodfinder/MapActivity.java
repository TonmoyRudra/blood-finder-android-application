package com.tonmoy.bloodfinder;

import java.io.IOException;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tonmoy.bloodfinder.maphospital.DownloadTask;
import com.tonmoy.bloodfinder.maphospital.GooglePlacesReadTask;
import com.tonmoy.bloodfinder.maphospital.MapStateManager;


public class MapActivity extends FragmentActivity implements
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener, OnClickListener {

	ImageView actionImageView, actionImageView_manu;
	Button manuButton;
	TextView actionTextview;
	ActionBar actionBar;
	Typeface tf;

	
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	private static final float DEFAULTZOOM = 10;
	private static final float CHITTAGONG_LAT = 22.371088f;
	private static final float CHITTAGONG_LNG = 91.791694f;

	private int PROXIMITY_RADIUS = 6000;
	private static final String GOOGLE_API_KEY = "AIzaSyAkun-3v9OWpI2amecQp67HznXwwcbZ5m8";

	private com.google.android.gms.maps.GoogleMap mMap;
	private Marker mMarker;

	private GoogleApiClient apiClient;

	private Button nearestHospital_button;
	private Button myLocationButton;
	private Button refreshButton;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionImageView = (ImageView) findViewById(R.id.actionImageView);

		actionTextview = (TextView) findViewById(R.id.actionTextView);
		actionBar = getActionBar();
		actionBar.hide();

		//tf = Typeface.createFromAsset(this.getAssets(), "Agatha-Regular.ttf");
		//Log.i("test", tf.toString());
		//actionTextview.setTypeface(tf, Typeface.BOLD);

		if (servicesOK()) {
			setContentView(R.layout.map_activity);
			
			

			nearestHospital_button = (Button) findViewById(R.id.nearestHospital_Button);
			myLocationButton = (Button) findViewById(R.id.myLocation_Button);
			refreshButton = (Button) findViewById(R.id.refreshButton);
			manuButton = (Button) findViewById(R.id.menu_Button);

			nearestHospital_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Location currentLocation= null;

					currentLocation = LocationServices.FusedLocationApi
							.getLastLocation(apiClient);
					if (currentLocation == null) {
						Toast.makeText(getApplicationContext(),
								"Nearest Hospital isn't available,Please Check your Internet Setting",
								Toast.LENGTH_SHORT).show();
					} else {
						
						Toast.makeText(getApplicationContext(),
								"Nearest Hospital is Loading, Please 'Zoom Out or In' On this map",Toast
								.LENGTH_SHORT).show();
						StringBuilder googlePlacesUrl = new StringBuilder(
								"https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
						googlePlacesUrl.append("location="
								+ currentLocation.getLatitude() + ","
								+ currentLocation.getLongitude());
						googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
						googlePlacesUrl.append("&types=" + "hospital");
						googlePlacesUrl.append("&sensor=true");
						googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

						GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
						Object[] toPass = new Object[3];
						toPass[0] = mMap;
						toPass[1] = googlePlacesUrl.toString();
						toPass[2] = new LatLng(currentLocation.getLatitude(),
								currentLocation.getLongitude());
						googlePlacesReadTask.execute(toPass);

					}

				}
			});

			myLocationButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Location currentLocation = LocationServices.FusedLocationApi
							.getLastLocation(apiClient);
					if (currentLocation == null) {
						Toast.makeText(getApplicationContext(),
								"Current location isn't available,Please Check your Internet Setting",
								Toast.LENGTH_SHORT).show();
					} else {
						gotoLocation(currentLocation.getLatitude(),
								currentLocation.getLongitude(), 16);
						setMarkers("I Am Here", "",
								currentLocation.getLatitude(),
								currentLocation.getLongitude(),
								R.drawable.my_location_marker);
					}

				}
			});

			refreshButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mMap.clear();
				}
			});

			manuButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final String mapTypes[] = { "None", "Normal", "Satellite",
							"Terrain", "Hybrid" };
					AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
					builder.setTitle("Set Map Type");
					builder.setCancelable(false);
					builder.setItems(mapTypes, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int possition) {
							switch (possition) {
							case 0:
								mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
								break;

							case 1:
								mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
								break;

							case 2:
								mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
								break;

							case 3:
								mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
								break;

							case 4:
								mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
								break;

							default:
								break;
							}
						}
					});
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int possition) {
							dialog.dismiss();
						}
					});
					AlertDialog mapTypeDialog = builder.create();
					mapTypeDialog.show();

				}
			});

			if (initMap()) {
				gotoLocation(CHITTAGONG_LAT, CHITTAGONG_LNG, DEFAULTZOOM);
				apiClient = new GoogleApiClient.Builder(this)
				.addApi(LocationServices.API)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).build();
				apiClient.connect();
			} else {
				Toast.makeText(this, "Map not available!", Toast.LENGTH_SHORT)
				.show();
			}
		} else {
			Toast.makeText(this, "Google Play Services is not available",
					Toast.LENGTH_LONG).show();
			finish();
		}
	}

	private boolean initMap() {
		if (mMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			mMap = mapFrag.getMap();

			if (mMap != null) {

				mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

					@Override
					public void onInfoWindowClick(Marker marker) {

					}
				});

				mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					@Override
					public boolean onMarkerClick(Marker marker) {
						if (mMarker != null) {
							String str_origin = "origin="
									+ mMarker.getPosition().latitude + ","
									+ mMarker.getPosition().longitude;
							// Destination of route
							String str_dest = "destination="
									+ marker.getPosition().latitude + ","
									+ marker.getPosition().longitude;
							// Sensor enabled
							String sensor = "sensor=false";
							// Building the parameters to the web service
							String parameters = str_origin + "&" + str_dest
									+ "&" + sensor;
							// Output format
							String output = "json";
							// Building the url to the web service
							String url = "https://maps.googleapis.com/maps/api/directions/"
									+ output + "?" + parameters;
							DownloadTask downloadTask = new DownloadTask();
							// Start downloading json data from Google
							// Directions
							// API
							Object[] toPass = new Object[2];
							toPass[0] = mMap;
							toPass[1] = url;
							downloadTask.execute(toPass);
						}

						return false;
					}
				});
			}
		}
		return (mMap != null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		} else {
			Toast.makeText(this, "Can't connect to Google Play services",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	private void gotoLocation(double lat, double lng, float zoom) {
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.animateCamera(update);
	}

	public void geoLocate(View v) throws IOException {

	}

	private void setMarkers(String locality, String countryName, double lat,
			double lng, int markerIconRes) {

		if (mMarker != null) {
			mMarker.remove();
		}
		MarkerOptions options = new MarkerOptions().title(locality)
				.position(new LatLng(lat, lng))
				.icon(BitmapDescriptorFactory.fromResource(markerIconRes));

		if (markerIconRes == 0) {
			options.icon(BitmapDescriptorFactory.defaultMarker());
		}

		if (countryName.length() > 0) {
			options.snippet(countryName);
		}

		mMarker = mMap.addMarker(options);
	}

	private void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}



	@Override
	protected void onResume() {
		super.onResume();
		// datasource.open();
		MapStateManager mgr = new MapStateManager(this);
		CameraPosition position = mgr.getSavedCameraPosition();
		if (position != null) {
			CameraUpdate update = CameraUpdateFactory
					.newCameraPosition(position);
			mMap.moveCamera(update);
			mMap.setMapType(mgr.getSavedMapType());
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// datasource.close();
	}

	@Override
	protected void onStop() {
		super.onStop();
		MapStateManager mgr = new MapStateManager(this);
		mgr.saveMapState(mMap);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
	}

	@Override
	public void onConnected(Bundle arg0) {
	}

	@Override
	public void onConnectionSuspended(int arg0) {
	}

	private LatLng getCurrentLocation() {
		Location currentLocation = LocationServices.FusedLocationApi
				.getLastLocation(apiClient);
		return new LatLng(currentLocation.getLatitude(),
				currentLocation.getLongitude());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
