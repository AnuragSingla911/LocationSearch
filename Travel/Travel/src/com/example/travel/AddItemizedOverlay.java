package com.example.travel;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class AddItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	private Context context;
	
	public AddItemizedOverlay(Drawable arg0) {
		super(boundCenterBottom(arg0));
		// TODO Auto-generated constructor stub
	}
	
	public AddItemizedOverlay(Drawable arg0, Context context) {
		this(arg0);
		this.context = context;
	}

	@Override
	protected OverlayItem createItem(int arg0) {
		// TODO Auto-generated method stub
		return mapOverlays.get(arg0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mapOverlays.size();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView)
	{
		if (event.getAction() == 1) {
			GeoPoint geopoint = mapView.getProjection().fromPixels(
					(int) event.getX(),
					(int) event.getY());
			double lat = geopoint.getLatitudeE6() / 1E6;
			double lon = geopoint.getLongitudeE6() / 1E6;
			}
		return false;
		}  
		
	@Override
	protected boolean onTap(int arg0) {
		// TODO Auto-generated method stub
		OverlayItem item = mapOverlays.get(arg0);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this.context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
			});
		dialog.show();
		return true; 
	}
	
	public void addOverlay(OverlayItem overlay) {
		mapOverlays.add(overlay);
		}
	
	public void populateNow(){
		this.populate();
		} 

}
