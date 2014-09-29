/**
 * 
 */
package it.umberto.daftvolleyotto.business;

import android.app.Fragment;
import android.os.Bundle;

/**
 * @author Umberto Sidoti
 * @version 1.0 
 * 26/set/2014
 */
public abstract class RetainFragmentDownloader extends Fragment
{
	protected int state;
	
	public abstract int forceDownloadJson(String url);
	public abstract int downloadJson(String url);
	public abstract int onDataDelivered();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}	
}
