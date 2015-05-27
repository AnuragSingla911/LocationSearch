package com.example.travel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class SinglePlaceActivity extends Activity {
	 Boolean isInternetPresent = false;
	 GooglePlaces googlePlaces;
	 PlaceDetails placeDetails;
	 ProgressDialog pDialog;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_place);
		Intent i = getIntent();
		String reference = i.getStringExtra(LoadPlaces.KEY_REFERENCE);
		LoadSinglePlaceDetails mLoadSinglePlaceDetails = new LoadSinglePlaceDetails(reference);
		mLoadSinglePlaceDetails.execute();
	}

	class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {

		private String reference;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SinglePlaceActivity.this);
			pDialog.setMessage("Loading profile ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			} 
		
		public LoadSinglePlaceDetails(String reference) {
			this.reference = reference;
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			googlePlaces = new GooglePlaces();
			try {
				placeDetails = googlePlaces.getPlaceDetails(reference);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(placeDetails != null) {
						String status = placeDetails.status; 
						if(status.equals("OK")) {
							String name = placeDetails.result.name;
							String address = placeDetails.result.formatted_address;
							String phone = placeDetails.result.formatted_phone_number;
							String latitude = Double.toString(placeDetails.result.geometry.location.lat);
							String longitude = Double.toString(placeDetails.result.geometry.location.lng);
							
							
							TextView lbl_name = (TextView) findViewById(R.id.name);
							TextView lbl_address = (TextView) findViewById(R.id.address);
							TextView lbl_phone = (TextView) findViewById(R.id.phone);
							TextView lbl_location = (TextView) findViewById(R.id.location); 
							
							
							name = name == null ? "Not present" : name;
							address = address == null ? "Not present" : address;
							phone = phone == null ? "Not present" : phone;
							latitude = latitude == null ? "Not present" : latitude;
							longitude = longitude == null ? "Not present" : longitude; 
							
							 lbl_name.setText(name);
							 lbl_address.setText(address);
							 lbl_phone.setText(Html.fromHtml("<b>Phone:</b> " + phone));
							 lbl_location.setText(Html.fromHtml("<b>Latitude:</b> " + latitude + ", <b>Longitude:</b> " + longitude));
						}
						
						else if(status.equals("ZERO_RESULTS")) {
							Toast.makeText(getApplicationContext(), "No place found", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getApplicationContext(), "Error in place detail", Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_place, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
