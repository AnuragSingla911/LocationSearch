package com.example.travel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LoadPlaces extends AsyncTask<String, String, PlaceList> {

	public ProgressDialog pDialog;
	public Context mContext;
	public GooglePlaces googlePlaces;
	String types;
	double radius = 1000;
	public ListView list;
	public GPSTracker gps;
	public PlaceList nearPlaces;
	public static String KEY_REFERENCE = "reference"; // id of the place
	public static String KEY_NAME = "name"; 
	public static String KEY_ICON = "icon";
	public static String KEY_VICINITY = "vicinity"; // Place area name 
	ArrayList<Place> placesListItems = new ArrayList<Place>(); 
	HttpImageLoader mImageLoader = null;
	
	public LoadPlaces(Context context, GPSTracker gps, ListView mList, String placetype, double radius) {
		mContext = context;
		this.gps = gps;
		list = mList;
		types = placetype;
		this.radius = radius;
		mImageLoader = new HttpImageLoader(mContext);
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pDialog = new ProgressDialog(mContext);
		pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show(); 
		
	}
	@Override
	protected PlaceList doInBackground(String... params) {
		// TODO Auto-generated method stub
		googlePlaces = new GooglePlaces();
		try {
			nearPlaces = googlePlaces.search(gps.getLatitude(),
					gps.getLongitude(), radius, types);
			} catch (Exception e) {
				e.printStackTrace();
				}
		return nearPlaces;
		
	}
	
	
	@Override
	protected void onPostExecute(final PlaceList result) {
		// TODO Auto-generated method stub
		//super.onPostExecute(result);
		pDialog.dismiss();
		new Handler().post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String status = result.status;
				if(status.equals("OK")) {
					if(result.results != null) {
						for(Place p:result.results) {
							placesListItems.add(p);
						}
						CustomAdapter adapter = new CustomAdapter();
						list.setAdapter(adapter);
						list.setVisibility(View.VISIBLE);
						list.setOnItemClickListener(itemClicklistener);
					}
				}
				else if(status.equals("ZERO_RESULTS")) {
					Toast.makeText(mContext, "No place has found", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(mContext, "Error in finding.."+status, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	class CustomAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return placesListItems.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			if(arg1 == null){
				LayoutInflater inflater = LayoutInflater.from(mContext);
				arg1 = inflater.inflate(R.layout.list_item, null);
			}
			
			TextView textview = (TextView)arg1.findViewById(R.id.name);
			textview.setText(placesListItems.get(arg0).name);
			
			TextView ref = (TextView)arg1.findViewById(R.id.reference);
			ref.setText(placesListItems.get(arg0).reference);
			
			
			ImageView imageView = (ImageView)arg1.findViewById(R.id.icon);
			mImageLoader.display(placesListItems.get(arg0).icon, imageView, android.R.drawable.progress_horizontal);
			return arg1;
		}
		
	}
	
	
	
	private OnItemClickListener itemClicklistener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			TextView tv = (TextView) view.findViewById(R.id.reference);
			String reference = (String) tv.getText();
			Intent in = new Intent(mContext,SinglePlaceActivity.class);
			in.putExtra(LoadPlaces.KEY_REFERENCE, reference);
			mContext.startActivity(in);
			
		}
	};

}
