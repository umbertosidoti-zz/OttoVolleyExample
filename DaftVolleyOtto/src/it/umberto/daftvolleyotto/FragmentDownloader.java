/**
 * 
 */
package it.umberto.daftvolleyotto;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

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
	public void downloadJsonObject(String url) 
	{
		state=STATE_PROGRESS;
		downloadJsonData(url);
	}	
	
	
	private void downloadJsonData(String url)
	{
		// Creating volley request obj
				
		
        JsonArrayRequest movieReq = new JsonArrayRequest
        (
        		url,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) 
                    {                       	
                    	properties=parseJsonArray(response);                       
                        dowloadFinished();
                    }
                }, 
                new Response.ErrorListener() 
                {
                    @Override
                    public void onErrorResponse(VolleyError error) 
                    {
                       finishedWithError(); 
                    }
                }
        );       
        VolleyManagerSingletone.getInstance(getActivity().getApplicationContext()).addToRequestQueue(movieReq,VolleyManagerSingletone.TAG);
		
	}
	
	/**
	 * @param response
	 * @return
	 */
	protected ArrayList<SaleProperty> parseJsonArray(JSONArray response) 	
	{
		ArrayList<SaleProperty> listProp = new ArrayList<SaleProperty>();
		
        for (int i = 0; i < response.length(); i++)
        {
            try 
            {
                JSONObject obj = response.getJSONObject(i);
                SaleProperty property = new SaleProperty(obj);               
                listProp.add(property);
            } 
            catch (JSONException e)
            {               
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
