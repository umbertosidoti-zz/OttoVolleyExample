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
	    private List<SaleProperty> movieItems;
	    private ImageLoader imageLoader;
	 
	    static class ViewHolder
	    {
	    	TextView address;
	    	NetworkImageView thumb;
	    }
	    
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
	        
	        if (convertView == null)
	        {
	        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 	        ViewHolder viewH= new ViewHolder();	        	
	        	convertView = inflater.inflate(R.layout.list_row, null);
	        	viewH.thumb = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
	        	viewH.address = (TextView) convertView.findViewById(R.id.address);	
	        	convertView.setTag(viewH);
	        }
	 
	        ViewHolder holder = (ViewHolder) convertView.getTag();
	        
	        if (imageLoader == null)
	            imageLoader = VolleyManagerSingletone.getInstance(activity.getApplicationContext()).getImageLoader();
	       	      
	        SaleProperty m = movieItems.get(position);	 
	        holder.thumb.setImageUrl(m.getUrlThumb(), imageLoader);       
	        holder.address.setText(m.getAddress());     	       
	 
	        return convertView;
	    }

}
