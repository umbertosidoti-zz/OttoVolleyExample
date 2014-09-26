/**
 * 
 */
package it.umberto.daftvolleyotto.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * @author Umberto Sidoti
 * @version 1.0 
 * 12/set/2014
 */
public class VolleyManagerSingletone {

	
	public static final String TAG = VolleyManagerSingletone.class.getSimpleName();
	private static VolleyManagerSingletone mInstance;	 
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;
    
    
    public static synchronized VolleyManagerSingletone getInstance(Context context) 
    {
		if (mInstance == null)
		{
			mInstance = new VolleyManagerSingletone(context);
		}
		return mInstance;
	}

    private VolleyManagerSingletone(Context context)
    {
        mCtx = context;     
    }  

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {        
            mRequestQueue = Volley.newRequestQueue(mCtx);
        }
        return mRequestQueue;
    }


    public ImageLoader getImageLoader() 
    {
        getRequestQueue();
        if (mImageLoader == null) 
        {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
    
    public <T> void addToRequestQueue(Request<T> req, String tag) 
    {      	
        req.setTag(tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) 
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) 
    {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
