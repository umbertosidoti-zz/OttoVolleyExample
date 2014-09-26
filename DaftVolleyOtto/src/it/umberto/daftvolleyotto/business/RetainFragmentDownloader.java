/**
 * 
 */
package it.umberto.daftvolleyotto.business;

import android.app.Activity;
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
	//private IFragmentListener mCallbacks;

	public abstract void forceDownloadJson(String url);
	public abstract void downloadJson(String url);

		
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		//mCallbacks = (IFragmentListener) activity;
	}
	
	/**
	 * This method will only be called once when the retained Fragment is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	/**
	 * Set the callback to null so we don't accidentally leak the Activity instance.
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		//mCallbacks = null;
	}
	
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
}
