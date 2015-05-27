package com.example.travel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager {
	private Context mContext;
	public ConnectionManager(Context context){
		mContext = context;
	}

	public boolean checkNetworkConnection() {
		ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE); 
		if(connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if(info != null) {
				for(int i=0;i<info.length;i++) {
					if(info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
