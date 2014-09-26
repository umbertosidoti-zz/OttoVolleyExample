/**
 * 
 */
package it.umberto.daftvolleyotto;


import android.app.Activity;
import it.umberto.daftvolleyotto.business.RetainFragmentDownloader;
import it.umberto.daftvolleyotto.business.SingletoneBus;

/**
 * @author Umberto Sidoti
 * @version 1.0 08/set/2014
 */
public class FragmentDownloader extends RetainFragmentDownloader 
{
	
	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		state=STATE_INIT;
	}

	@Override
	public void downloadJsonObject(String url) 
	{
		state=STATE_PROGRESS;
		
	}	
	
	private void dowloadFinished()
	{
		 FragmentDownloaderResult resultObj = new FragmentDownloaderResult(result);
		 SingletoneBus.getInstance().post(resultObj);
	}
	

}
