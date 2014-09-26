/**
 * 
 */
package it.umberto.daftvolleyotto;


import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import android.app.Activity;
import it.umberto.daftvolleyotto.business.RetainFragmentDownloader;
import it.umberto.daftvolleyotto.business.SingletoneBus;
import it.umberto.daftvolleyotto.volley.VolleyManagerSingletone;

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
		downloadSynkData(url);
	}	
	
	
	private void downloadSynkData(String url)
	{
		// Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
 
//                                JSONObject obj = response.getJSONObject(i);
//                                Movie movie = new Movie();
//                                movie.setTitle(obj.getString("title"));
//                                movie.setThumbnailUrl(obj.getString("image"));
//                                movie.setRating(((Number) obj.get("rating"))
//                                        .doubleValue());
//                                movie.setYear(obj.getInt("releaseYear"));
// 
//                                // Genre is json array
//                                JSONArray genreArry = obj.getJSONArray("genre");
//                                ArrayList<String> genre = new ArrayList<String>();
//                                for (int j = 0; j < genreArry.length(); j++) {
//                                    genre.add((String) genreArry.get(j));
//                                }
//                                movie.setGenre(genre);
// 
//                                // adding movie to movies array
//                                movieList.add(movie);
 
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
 
                        }
 
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
//                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d(TAG, "Error: " + error.getMessage());
//                        hidePDialog();
 
                    }
                });
 
        // Adding request to request queue
        VolleyManagerSingletone.getInstance(getActivity().getApplicationContext()).addToRequestQueue(movieReq,VolleyManagerSingletone.TAG);
		
	}
	
	private void dowloadFinished()
	{
		 FragmentDownloaderResult resultObj = new FragmentDownloaderResult(result);
		 SingletoneBus.getInstance().post(resultObj);
	}
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		VolleyManagerSingletone.getInstance(getActivity().getApplicationContext()).cancelPendingRequests(VolleyManagerSingletone.TAG);
		
	}
	

}
