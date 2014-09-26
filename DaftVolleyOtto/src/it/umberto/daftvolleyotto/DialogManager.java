package it.umberto.daftvolleyotto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public class DialogManager 
{
			
	public static ProgressDialog createDialog(Context ctx,OnCancelListener listener)
	{
		ProgressDialog dialog = new ProgressDialog(ctx);
		dialog.setTitle("");
		dialog.setMessage("Wait..");
		dialog.setCancelable(true);
		dialog.setOnCancelListener(listener);		
		return dialog;
	}

}
