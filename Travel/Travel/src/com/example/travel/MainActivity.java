package com.example.travel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button mShowList;
	private Context mContext;
	GPSTracker gps;
	private SeekBar mSeekBar;
	private TextView mRadiusText;
	private LinearLayout mPlacesTypeLayout;
	private TextView mTextViews[];
	private ConnectionManager mConnectionManager;
	private ActionBar mActionBar;
	private int radius = 0;
	private StringBuilder mPlaceType;
	private boolean toggle[];
	private List<String> mTypeList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		mTextViews = new TextView[14];
		initializeTextViews();
		setClickListenerOnTextViews();
		mShowList = (Button) findViewById(R.id.showResultButton);
		mRadiusText = (TextView) findViewById(R.id.radiusText);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar1);
		mPlacesTypeLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		
		mShowList.setOnClickListener(onclicklistener);
		gps = new GPSTracker(mContext);
		mPlaceType = new StringBuilder();
		toggle = new boolean[14];
		mTypeList = new ArrayList<String>();
		initilizeToggle();
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				radius = progress;
				mRadiusText.setText("Enter Radius is: "+progress);
			}
		});
		mConnectionManager = new ConnectionManager(mContext);
		mActionBar = getActionBar();
	}
	private OnClickListener onclicklistener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				showListofPlaces();
		}
	};
	protected void onResume() {
		super.onResume();
		if(!mConnectionManager.checkNetworkConnection()) {
			hideLayout();
			showConnectionErrorDialog();
		}
		else {
			gps.getLocation();
			if(!gps.canGetLocation) {
				hideLayout();
				gps.showSettingsAlert();
				}
			else
				showLayout();
		}
	};
	
	private void initializeTextViews() {
		mTextViews[0] = (TextView) findViewById(R.id.tvText1);
		mTextViews[1] = (TextView) findViewById(R.id.tvText2);
		mTextViews[2] = (TextView) findViewById(R.id.tvText3);
		mTextViews[3] = (TextView) findViewById(R.id.tvText4);
		mTextViews[4] = (TextView) findViewById(R.id.tvText5);
		
		
		mTextViews[5] = (TextView) findViewById(R.id.tvText6);
		mTextViews[6] = (TextView) findViewById(R.id.tvText7);
		mTextViews[7] = (TextView) findViewById(R.id.tvText8);
		mTextViews[8] = (TextView) findViewById(R.id.tvText9);
		mTextViews[9] = (TextView) findViewById(R.id.tvText10);
		
		mTextViews[10] = (TextView) findViewById(R.id.tvText11);
		mTextViews[11] = (TextView) findViewById(R.id.tvText12);
		mTextViews[12] = (TextView) findViewById(R.id.tvText13);
		mTextViews[13] = (TextView) findViewById(R.id.tvText14);
	}
	
	private void setClickListenerOnTextViews() {
		mTextViews[0].setOnClickListener(textClickListener);
		mTextViews[1].setOnClickListener(textClickListener);
		mTextViews[2].setOnClickListener(textClickListener);
		mTextViews[3].setOnClickListener(textClickListener);
		mTextViews[4].setOnClickListener(textClickListener);
		
		mTextViews[5].setOnClickListener(textClickListener);
		mTextViews[6].setOnClickListener(textClickListener);
		mTextViews[7].setOnClickListener(textClickListener);
		mTextViews[8].setOnClickListener(textClickListener);
		mTextViews[9].setOnClickListener(textClickListener);
		
		mTextViews[10].setOnClickListener(textClickListener);
		mTextViews[11].setOnClickListener(textClickListener);
		mTextViews[12].setOnClickListener(textClickListener);
		mTextViews[13].setOnClickListener(textClickListener);
	}
	
	private OnClickListener textClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch(arg0.getId()) {
			case R.id.tvText1:
				if(toggle[0]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[0]);
					toggle[0] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[0]);
					toggle[0] = true;
				}
				break;
			case R.id.tvText2:
				if(toggle[1]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[1]);
					toggle[1] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[1]);
					toggle[1] = true;
				}
				break;
			case R.id.tvText3:
				if(toggle[2]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[2]);
					toggle[2] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[2]);
					toggle[2] = true;
				}
				break;
			case R.id.tvText4:
				if(toggle[3]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[3]);
					toggle[3] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[3]);
					toggle[3] = true;
				}
				break;
			case R.id.tvText5:
				if(toggle[4]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[4]);
					toggle[4] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[4]);
					toggle[4] = true;
				}
				break;
				
			case R.id.tvText6:
				if(toggle[5]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[5]);
					toggle[5] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[5]);
					toggle[5] = true;
				}
				break;
			case R.id.tvText7:
				if(toggle[6]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[6]);
					toggle[6] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[6]);
					toggle[6] = true;
				}
				break;
			case R.id.tvText8:
				if(toggle[7]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[7]);
					toggle[7] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[7]);
					toggle[7] = true;
				}
				break;
			case R.id.tvText9:
				if(toggle[8]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[8]);
					toggle[8] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[8]);
					toggle[8] = true;
				}
				break;
			case R.id.tvText10:
				if(toggle[9]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[9]);
					toggle[9] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[9]);
					toggle[9] = true;
				}
				break;
				
			case R.id.tvText11:
				if(toggle[10]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[10]);
					toggle[10] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[10]);
					toggle[10] = true;
				}
				break;
			case R.id.tvText12:
				if(toggle[11]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[11]);
					toggle[11] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[11]);
					toggle[11] = true;
				}
				break;
			case R.id.tvText13:
				if(toggle[12]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[12]);
					toggle[12] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[12]);
					toggle[12] = true;
				}
				break;
			case R.id.tvText14:
				if(toggle[13]) {
					mTypeList.add(((TextView)arg0).getText().toString());
					changeColor(mTextViews[13]);
					toggle[13] = false;
				}
				else {
					mTypeList.remove(((TextView)arg0).getText().toString());
					changeToDefault(mTextViews[13]);
					toggle[13] = true;
				}
				break;
			default:
				break;
			}
		}
	};
	
	private void initilizeToggle() {
		for(int i=0;i<14;i++) {
			toggle[i] = true;
		}
	}
	
	
	private void changeColor(TextView tv) {
		tv.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD_ITALIC);
	}
	
	private void changeToDefault(TextView tv) {
		tv.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
	}

	private void showConnectionErrorDialog() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
		alertDialog.setTitle("Error");
		alertDialog
		.setMessage("No internet connection found. You want to try again");
		alertDialog.setPositiveButton("YES", 
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(!mConnectionManager.checkNetworkConnection() )
					showConnectionErrorDialog();
				else {
					gps.getLocation();
					if(!gps.canGetLocation)
						gps.showSettingsAlert();
					else
						showLayout();
				}
			}
		});
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
				}
			});
		AlertDialog mDialog = alertDialog.create();
		mDialog.show();
	}
	
	protected void showListofPlaces() {
		// TODO Auto-generated method stub
		showList(gps);
		
	}
	private void hideLayout() {
		mShowList.setVisibility(View.GONE);
		mRadiusText.setVisibility(View.GONE);
		mSeekBar.setVisibility(View.GONE);
		mPlacesTypeLayout.setVisibility(View.GONE);
	}
	
	private void showLayout() {
		mShowList.setVisibility(View.VISIBLE);
		mRadiusText.setVisibility(View.VISIBLE);
		mSeekBar.setVisibility(View.VISIBLE);
		mPlacesTypeLayout.setVisibility(View.VISIBLE);
	}
	public void showList(GPSTracker gps) {

		for(int i=0;i<mTypeList.size();i++) {
			if(i==mTypeList.size()-1)
				mPlaceType.append(mTypeList.get(i));
			else
				mPlaceType.append(mTypeList.get(i)+"|");
		}
		double radius1 = (double) radius;
		Intent intent = new Intent(this, ShowList.class);
		intent.putExtra("type", mPlaceType.toString());
		intent.putExtra("radius", radius1);
		startActivity(intent);
		mPlaceType.delete(0, mPlaceType.length());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
