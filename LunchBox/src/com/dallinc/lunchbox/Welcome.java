package com.dallinc.lunchbox;

import com.example.lunchbox.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Welcome extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_view);
		
		Button whatsforlunch = (Button) findViewById(R.id.button1);
		whatsforlunch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), RootTable.class);
				startActivity(i);
			}
		});
		
		Button addlunch = (Button) findViewById(R.id.button2);
		addlunch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), AddLunch.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_view, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		DialogFragment newFragment = new AboutFragment();
	    newFragment.show(getSupportFragmentManager(), "About");
		return true;
	}
	
	public static class AboutFragment extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	setRetainInstance(true);
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        TextView myMsg = new TextView(getActivity());
	        myMsg.setText(R.string.about);
	        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
	        builder.setView(myMsg);
	        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {}
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	}

}
