/**
 * 
 */
package it.umberto.daftvolleyotto.business;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Umberto Sidoti
 * @version 1.0 
 * 26/set/2014
 */
public class SaleProperty implements Serializable
{
	private static final long serialVersionUID = -2700016538928162156L;
	private String urlThumb;
	private String address;
	
	/**
	 * @param obj
	 * @throws JSONException 
	 */
	public SaleProperty(JSONObject obj) throws JSONException
	{
		address=obj.getString("full_address");
		urlThumb=obj.getString("small_thumbnail_url");		
	}
	public String getUrlThumb() 
	{
		return urlThumb;
	}
	public void setUrlThumb(String urlThumb) {
		this.urlThumb = urlThumb;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
