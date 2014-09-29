/**
 * 
 */
package it.umberto.daftvolleyotto;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import it.umberto.daftvolleyotto.business.FragmentDownloaderResult;
import it.umberto.daftvolleyotto.business.ParserDaftJson;
import it.umberto.daftvolleyotto.business.RetainFragmentDownloader;
import it.umberto.daftvolleyotto.business.SaleProperty;
import it.umberto.daftvolleyotto.business.SingletoneBus;
import it.umberto.daftvolleyotto.volley.VolleyManagerSingletone;

/**
 * @author Umberto Sidoti
 * @version 1.0 08/set/2014
 */
public class FragmentDownloader extends RetainFragmentDownloader 
{
	public static final int STATE_INIT=0;
	public static final int STATE_PROGRESS=1;
	public static final int STATE_FINSHED=2;
	public static final int STATE_DELIVERED=3;
	
	private  ArrayList<SaleProperty> properties;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		state=STATE_INIT;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public int forceDownloadJson(String url) 
	{
		state=STATE_PROGRESS;
		downloadJsonData(url);
		return state;
	}	
	
	@Override
	public int downloadJson(String url)
	{
		state=STATE_PROGRESS;
		if(properties!=null)
		{
			state=STATE_FINSHED;
			FragmentDownloaderResult resultObj = new FragmentDownloaderResult(properties);
			SingletoneBus.getInstance().post(resultObj);
		}
		else
		{
			downloadJsonData(url);
		}
		return state;
	}
	
	
	private void downloadJsonData(String url)
	{			
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,url,null,
		    new Response.Listener<JSONObject>() {
		        @Override
		        public void onResponse(JSONObject response)
		        {
		        	properties=ParserDaftJson.jsonToArrayProperties(response);
		        	downloadFinished();
		        	
		        }
		    },
		    new Response.ErrorListener() {
		        @Override
		        public void onErrorResponse(VolleyError error)
		        {
		        	finishedWithError();   
		        }
		    });		
		
        VolleyManagerSingletone.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsObjRequest,VolleyManagerSingletone.TAG);
		
	}
	
	protected void finishedWithError() 
	{
		FragmentDownloaderResult resultObj = new FragmentDownloaderResult(properties);
		resultObj.setResult(FragmentDownloaderResult.RESULT_ERROR);
		SingletoneBus.getInstance().post(resultObj);
	}

	private void downloadFinished()
	{
		state=STATE_FINSHED;
		FragmentDownloaderResult resultObj = new FragmentDownloaderResult(properties);
		SingletoneBus.getInstance().post(resultObj);
	}
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		VolleyManagerSingletone.getInstance(getActivity().getApplicationContext()).cancelPendingRequests(VolleyManagerSingletone.TAG);
		
	}

	@Override
	public int onDataDelivered()
	{
		state =STATE_DELIVERED;
		return state;
	}

	
	

}
