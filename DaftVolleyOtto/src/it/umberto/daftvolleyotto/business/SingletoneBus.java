/**
 * 
 */
package it.umberto.daftvolleyotto.business;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * @author Umberto Sidoti
 * @version 1.0 
 * 22/lug/2014
 */
public class SingletoneBus
{	
	 private static Bus BUS;
	
	 public static Bus getInstance()
	 {
		 if(BUS==null)
		 {
			 //ONLY MAIN THREAD Bus(ThreadEnforcer.MAIN)
			 //ANY THREAD Bus(ThreadEnforcer.ANY);	
			 
			 BUS = new Bus(ThreadEnforcer.ANY);	 
		 }
		 return BUS;
	 }
}
