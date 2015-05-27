package com.example.travel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ShowList extends Activity {
	
	public ListView placeList;
	public GPSTracker gps;
	public String placeType;
	public double radius;
	public LoadPlaces mLoadPlaces;
	private Button showOnMap;
	public PlaceList nearPlaces;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list);
		placeList = (ListView) findViewById(R.id.place_list);
		showOnMap = (Button) findViewById(R.id.btn_show_map);
		showOnMap.setOnClickListener(showmaplistener);
		placeList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) view.findViewById(R.id.reference);
				String reference = (String) tv.getText();
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						SinglePlaceActivity.class);
				// Sending place refrence id to single place activity
				// place refrence id used to get "Place full details"
				in.putExtra(LoadPlaces.KEY_REFERENCE, reference);
				startActivity(in);
			}
		});
		Intent intent = getIntent();
		gps = new GPSTracker(this);
		if(intent != null) {
			placeType = intent.getStringExtra("type");
			radius = intent.getDoubleExtra("radius", 1000);
		}
		mLoadPlaces = new LoadPlaces(this, gps, placeList, placeType, radius);
		mLoadPlaces.execute();
	}

	private OnClickListener showmaplistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mLoadPlaces == null)
				return;
			nearPlaces = mLoadPlaces.nearPlaces;
			Intent i = new Intent(getApplicationContext(),
			PlacesMapActivity.class);
			i.putExtra("user_latitude", Double.toString(gps.getLatitude()));
			i.putExtra("user_longitude", Double.toString(gps.getLongitude()));
			i.putExtra("near_places", nearPlaces);
			startActivity(i);
		}
	};
	
	/*private OnItemClickListener placeclicklistener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			TextView tv = (TextView) view.findViewById(R.id.reference);
			String reference = (String) tv.getText();
			// Starting new intent
			Intent in = new Intent(getApplicationContext(),
					SinglePlaceActivity.class);
			// Sending place refrence id to single place activity
			// place refrence id used to get "Place full details"
			in.putExtra(LoadPlaces.KEY_REFERENCE, reference);
			startActivity(in);
		}
	};*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_list, menu);
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
