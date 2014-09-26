package it.umberto.daftvolleyotto.business;

import android.app.Activity;


public abstract class BaseBusActivity extends Activity {

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SingletoneBus.getInstance().register(this);
	}	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SingletoneBus.getInstance().unregister(this);
	}			 	
	
	abstract public void onDownloaderDataReceived(FragmentDownloaderResult event);	
}
