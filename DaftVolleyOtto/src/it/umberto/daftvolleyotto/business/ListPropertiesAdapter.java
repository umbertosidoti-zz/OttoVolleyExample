package it.umberto.daftvolleyotto.business;

 
import it.umberto.daftvolleyotto.R;
import it.umberto.daftvolleyotto.volley.VolleyManagerSingletone;

import java.util.List;
 


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * @author Umberto Sidoti
 * @version 1.0 
 * 26/set/2014
 */
public class ListPropertiesAdapter extends BaseAdapter {
	    private Activity activity;
	    private LayoutInflater inflater;
	    private List<SaleProperty> movieItems;
	    private ImageLoader imageLoader;
	 
	    public ListPropertiesAdapter(Activity activity, List<SaleProperty> movieItems) 
	    {
	    	imageLoader = VolleyManagerSingletone.getInstance(activity.getApplicationContext()).getImageLoader();
	        this.activity = activity;
	        this.movieItems = movieItems;
	    }
	 
	    @Override
	    public int getCount() {
	        return movieItems.size();
	    }
	 
	    @Override
	    public Object getItem(int location) {
	        return movieItems.get(location);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent)
	    {
	 
	        if (inflater == null)
	            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null)
	            convertView = inflater.inflate(R.layout.list_row, null);
	 
	        if (imageLoader == null)
	            imageLoader = VolleyManagerSingletone.getInstance(activity.getApplicationContext()).getImageLoader();
	       
	        NetworkImageView thumbNail = (NetworkImageView) convertView
	                .findViewById(R.id.thumbnail);
	        TextView address = (TextView) convertView.findViewById(R.id.address);	       
	 
	        // getting movie data for the row
	        SaleProperty m = movieItems.get(position);	 
	        thumbNail.setImageUrl(m.getUrlThumb(), imageLoader);       
	        address.setText(m.getAddress());     	       
	 
	        return convertView;
	    }

}
