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
	public static final String SHOWING_DIALOG="SHOW_DIALOG";
	private static final String URL_DEMO="https://api.daft.com/v2/json/search_sale?parameters={\"api_key\":\"261cb47575e84ab5d29356ad2818ac21a20b1f4f\",\"query\":{\"perpage\":50}}";
	private ProgressDialog dialog;
	
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
					showDialog();
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
					showDialog();
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
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if((dialog!=null)&&(dialog.isShowing()))
		{
			outState.putBoolean(SHOWING_DIALOG,true);
			dismissDialog();
		}
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		boolean showdialog = savedInstanceState.getBoolean(SHOWING_DIALOG, false);
		if(showdialog)
			showDialog();		
	}

	@Subscribe
	public void onDownloaderDataReceived(FragmentDownloaderResult event)
	{
		dismissDialog();
		
		FragmentManager fm = getFragmentManager();
		fragmentDownloader = (RetainFragmentDownloader) fm.findFragmentByTag(TAG_DOWNLOADER);

		if (fragmentDownloader == null) 
			fragmentDownloader.setState(FragmentDownloader.STATE_DELIVERED);		
		
		if(event.getResult()==FragmentDownloaderResult.RESULT_OK)
		{
			Intent intent= new Intent(this, ListActivity.class);
			intent.putExtra(ListActivity.BUNDLE_EXTRA_PROPERTIES, event.getProperties());
			startActivity(intent);			
		}		
	}
	
	private void dismissDialog()
	{
		if((dialog!=null)&&(dialog.isShowing()))
				dialog.dismiss();
	}
	
	private void showDialog()
	{
		dialog=DialogManager.createDialog(this,this);
		dialog.show();
	}

	@Override
	public void onCancel(DialogInterface dialog) 
	{
		VolleyManagerSingletone.getInstance(getApplicationContext()).cancelPendingRequests(VolleyManagerSingletone.TAG);
				
	}
	
}
