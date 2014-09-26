package it.umberto.daftvolleyotto;

import com.squareup.otto.Subscribe;

import it.umberto.daftvolleyotto.business.BaseBusActivity;
import it.umberto.daftvolleyotto.business.RetainFragmentDownloader;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseBusActivity 
{

	private RetainFragmentDownloader fragmentDownloader;
	public static final String TAG_DOWNLOADER="FragmentDownloaderTag";
	private static final String URL_DEMO="http://www.test.it";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);	
		
		
		Button buttonStart=(Button) findViewById(R.id.buttonStart);
		buttonStart.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				if((fragmentDownloader!=null)&&(fragmentDownloader.getState()==RetainFragmentDownloader.STATE_INIT))
				{
					fragmentDownloader.downloadJsonObject(URL_DEMO);
				}
			}
		});		
		
		FragmentManager fm = getFragmentManager();
		fragmentDownloader = (RetainFragmentDownloader) fm.findFragmentByTag(TAG_DOWNLOADER);

		if (fragmentDownloader == null) 
		{
			fragmentDownloader = new FragmentDownloader();			
			fm.beginTransaction().add(fragmentDownloader, TAG_DOWNLOADER).commit();			
		}		
	}
	
	
	@Subscribe
	public void onDownloaderDataReceived(FragmentDownloaderResult event)
	{
		
	}
	
}
