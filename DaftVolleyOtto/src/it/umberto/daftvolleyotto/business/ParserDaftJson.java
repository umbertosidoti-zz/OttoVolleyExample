package it.umberto.daftvolleyotto.business;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserDaftJson 
{
	
	public static ArrayList<SaleProperty> jsonToArrayProperties(JSONObject sourceJson)
	{		
		ArrayList<SaleProperty> listProp = new ArrayList<SaleProperty>();

		JSONObject obj2;
		JSONArray objArray=null;

		try 
		{
			obj2 = sourceJson.getJSONObject("result");
			JSONObject obj3=obj2.getJSONObject("results");
			objArray=obj3.getJSONArray("ads");
		} 
		catch (JSONException e1) 
		{
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
	
}
