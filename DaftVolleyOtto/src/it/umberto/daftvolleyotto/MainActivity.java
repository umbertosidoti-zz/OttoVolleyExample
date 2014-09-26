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
	private static final String URL_DEMO="https://api.daft.com/v2/json/search_sale?parameters={\"api_key\":\"261cb47575e84ab5d29356ad2818ac21a20b1f4f\",\"query\":{\"perpage\":50}}";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);	
		
		
		Button buttonForce=(Button) findViewById(R.id.buttonForce);
		buttonForce.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				if(fragmentDownloader!=null)
				{
					fragmentDownloader.forceDownloadJson(URL_DEMO);
				}
			}
		});		
		
		Button buttonCache=(Button) findViewById(R.id.buttonCache);
		buttonCache.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				if(fragmentDownloader!=null)
				{
					fragmentDownloader.downloadJson(URL_DEMO);
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
		FragmentManager fm = getFragmentManager();
		fragmentDownloader = (RetainFragmentDownloader) fm.findFragmentByTag(TAG_DOWNLOADER);

		if (fragmentDownloader == null) 
			fragmentDownloader.setState(RetainFragmentDownloader.STATE_DELIVERED);		
		
		if(event.getResult()==FragmentDownloaderResult.RESULT_OK)
		{
			Intent intent= new Intent(this, ListActivity.class);
			intent.putExtra(ListActivity.BUNDLE_EXTRA_PROPERTIES, event.getProperties());
			startActivity(intent);			
		}		
	}
	
}
