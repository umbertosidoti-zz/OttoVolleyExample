/**
 * 
 */
package it.umberto.daftvolleyotto.business;

import java.util.ArrayList;

/**
 * @author Umberto Sidoti
 * @version 1.0 
 * 26/set/2014
 */
public class FragmentDownloaderResult
{
	public static final int RESULT_OK=1;
	public static final int RESULT_ERROR=10;
	
	private int result;
	
	
	private  ArrayList<SaleProperty> properties;
	
	
	public FragmentDownloaderResult()
	{
		result=RESULT_OK;
	}
	
	public FragmentDownloaderResult( ArrayList<SaleProperty> properties)
	{
		this.properties=properties;
		result=RESULT_OK;
	}

	public ArrayList<SaleProperty> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<SaleProperty> properties) {
		this.properties = properties;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
