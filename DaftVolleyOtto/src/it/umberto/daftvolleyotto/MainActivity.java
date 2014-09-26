package it.umberto.daftvolleyotto;

import com.squareup.otto.Subscribe;

import it.umberto.daftvolleyotto.business.BaseBusActivity;
import it.umberto.daftvolleyotto.business.FragmentDownloaderResult;
import it.umberto.daftvolleyotto.business.RetainFragmentDownloader;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
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
	private static final String URL_DEMO="https://api.daft.com/v2/json/search_sale?";
	private static final String KEY="261cb47575e84ab5d29356ad2818ac21a20b1f4f";
	
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
					fragmentDownloader.downloadJsonObject(KEY, URL_DEMO);
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
		if(event.getResult()==FragmentDownloaderResult.RESULT_OK)
		{
			Intent intent= new Intent(this, ListActivity.class);
			intent.putExtra(ListActivity.BUNDLE_EXTRA_PROPERTIES, event.getProperties());
			startActivity(intent);			
		}		
	}
	
}
