/**
 * 
 */
package it.umberto.daftvolleyotto;

import it.umberto.daftvolleyotto.business.ListPropertiesAdapter;
import it.umberto.daftvolleyotto.business.SaleProperty;
import it.umberto.daftvolleyotto.volley.VolleyManagerSingletone;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Property;
import android.widget.ListView;

/**
 * @author Umberto Sidoti
 * @version 1.0 
 * 26/set/2014
 */
public class ListActivity extends Activity 
{
	
	public static final String BUNDLE_EXTRA_PROPERTIES="properties";	
	private ArrayList<SaleProperty> properties;	
	private ListPropertiesAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		Bundle extra = getIntent().getExtras();
		if(extra!=null)
		{
			properties=(ArrayList<SaleProperty>) extra.get(BUNDLE_EXTRA_PROPERTIES);	
			if(properties!=null)
				adapter=new ListPropertiesAdapter(this, properties);
		}		
		setContentView(R.layout.activity_listview);
		if(adapter!=null)
		{
			ListView list= (ListView) findViewById(R.id.list);
			list.setAdapter(adapter);			
		}	
	}


}
