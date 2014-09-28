package it.umberto.daftvolleyotto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;

public class DialogManager 
{
	public static final String SHOWING_DIALOG="SHOW_DIALOG";
	private Context ctx;
	private OnCancelListener listener;
	private ProgressDialog dialog;
	
	public DialogManager(Context ctx,OnCancelListener listener)
	{
		this.listener=listener;
		this.ctx=ctx;
	}
	
	public void changeState(int state)
	{
		switch(state)
		{
			case FragmentDownloader.STATE_PROGRESS:
				showDialog();
				break;
			case FragmentDownloader.STATE_DELIVERED:
				dismissDialog();
				break;
			default:
				dismissDialog();
				break;
		}
		
	}
	
	private void showDialog()
	{
		dialog = new ProgressDialog(ctx);
		dialog.setTitle("");
		dialog.setMessage("Wait..");
		dialog.setCancelable(true);
		dialog.setOnCancelListener(listener);
		dialog.show();
	}
	
	private void dismissDialog()
	{
		if((dialog!=null)&&(dialog.isShowing()))
				dialog.dismiss();
	}

	public void onSaveInstanceState(Bundle outState)
	{
		if((dialog!=null)&&(dialog.isShowing()))
		{
			outState.putBoolean(SHOWING_DIALOG,true);
			dismissDialog();
		}
		
	}

	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		boolean checkIfVisible = savedInstanceState.getBoolean(SHOWING_DIALOG, false);
		if(checkIfVisible)
			showDialog();	
	}

}
