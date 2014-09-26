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
import it.umberto.daftvolleyotto.business.FragmentDownloaderResult;
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
	private  ArrayList<SaleProperty> properties;

	@Override
	public void onAttach(Activity activity) 
	{
		super.onAttach(activity);
		state=STATE_INIT;
	}

	@Override
	public void forceDownloadJson(String url) 
	{
		state=STATE_PROGRESS;
		downloadJsonData(url);
	}	
	
	@Override
	public void downloadJson(String url)
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
	}
	
	
	private void downloadJsonData(String url)
	{			
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,url,null,
		    new Response.Listener<JSONObject>() {
		        @Override
		        public void onResponse(JSONObject response)
		        {
		        	properties=parseJsonObject(response);
		        	dowloadFinished();
		        	
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
	
	/**
	 * @param response
	 * @return
	 */
	protected ArrayList<SaleProperty> parseJsonObject(JSONObject response) 	
	{
		ArrayList<SaleProperty> listProp = new ArrayList<SaleProperty>();
		
		JSONObject obj2;
		JSONArray objArray=null;
		
		try 
		{
			obj2 = response.getJSONObject("result");
			JSONObject obj3=obj2.getJSONObject("results");
			objArray=obj3.getJSONArray("ads");
		} 
		catch (JSONException e1) {
		}		
		
		if(objArray!=null)
		{		
	        for (int i = 0; i < objArray.length(); i++)
	        {
	            try 
	            {               
	            	JSONObject obj=objArray.getJSONObject(i);
	                SaleProperty property = new SaleProperty(obj);               
	                listProp.add(property);
	            } 
	            catch (JSONException e)
	            {               
	            } 
	        }
		}
        return listProp;
	}

	/**
	 * 
	 */
	protected void finishedWithError() 
	{
		FragmentDownloaderResult resultObj = new FragmentDownloaderResult(properties);
		resultObj.setResult(FragmentDownloaderResult.RESULT_ERROR);
		SingletoneBus.getInstance().post(resultObj);
	}

	private void dowloadFinished()
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

	
	

}
