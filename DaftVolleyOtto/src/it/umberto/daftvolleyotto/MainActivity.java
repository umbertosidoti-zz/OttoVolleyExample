package it.umberto.daftvolleyotto;

import com.squareup.otto.Subscribe;

import it.umberto.daftvolleyotto.business.BaseBusActivity;
import it.umberto.daftvolleyotto.business.FragmentDownloaderResult;
import it.umberto.daftvolleyotto.business.RetainFragmentDownloader;
import it.umberto.daftvolleyotto.volley.VolleyManagerSingletone;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseBusActivity implements OnCancelListener 
{

	private RetainFragmentDownloader fragmentDownloader;
	public static final String TAG_DOWNLOADER="FragmentDownloaderTag";
	
	private static final String URL_DEMO="https://api.daft.com/v2/json/search_sale?parameters={\"api_key\":\"261cb47575e84ab5d29356ad2818ac21a20b1f4f\",\"query\":{\"perpage\":50}}";
	private DialogManager dialogManager;
	
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
					int state=fragmentDownloader.forceDownloadJson(URL_DEMO);
					dialogManager.changeState(state);
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
					int state=fragmentDownloader.downloadJson(URL_DEMO);
					dialogManager.changeState(state);
				}
			}
		});				
		
		dialogManager= new DialogManager(this, this);
		
		FragmentManager fm = getFragmentManager();
		fragmentDownloader = (RetainFragmentDownloader) fm.findFragmentByTag(TAG_DOWNLOADER);

		if (fragmentDownloader == null) 
		{
			fragmentDownloader = new FragmentDownloader();			
			fm.beginTransaction().add(fragmentDownloader, TAG_DOWNLOADER).commit();			
		}
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);		
		dialogManager.onSaveInstanceState(outState);	
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);		
		dialogManager.onRestoreInstanceState(savedInstanceState);		
	}

	@Subscribe
	public void onDownloaderDataReceived(FragmentDownloaderResult event)
	{
				
		FragmentManager fm = getFragmentManager();
		fragmentDownloader = (RetainFragmentDownloader) fm.findFragmentByTag(TAG_DOWNLOADER);

		if (fragmentDownloader != null) 
		{
			int state =fragmentDownloader.onDataDelivered();
			dialogManager.changeState(state);		
		}
		
		if(event.getResult()==FragmentDownloaderResult.RESULT_OK)
		{
			Intent intent= new Intent(this, ListActivity.class);
			intent.putExtra(ListActivity.BUNDLE_EXTRA_PROPERTIES, event.getProperties());
			startActivity(intent);			
		}		
	}
	
	

	@Override
	public void onCancel(DialogInterface dialog) 
	{
		VolleyManagerSingletone.getInstance(getApplicationContext()).cancelPendingRequests(VolleyManagerSingletone.TAG);
				
	}
	
}
