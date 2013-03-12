package com.dallinc.lunchbox;

import java.text.SimpleDateFormat;
import java.util.*;
import android.view.*;
import android.widget.*;

import com.example.lunchbox.R;
import com.parse.Parse;
import android.os.Bundle;
import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;

public class AddLunch extends FragmentActivity {
	
	static Calendar eventTime;
	static Button dateandtimebutton;
	static SimpleDateFormat sdf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_lunch_view);
		
		Parse.initialize(this, "DP1JwuroXiST1NC3woYkvhrbHSR1bPHwxuJBiNZQ", "QTWcDJPMVS4B63jOzDVQ696irwA0Lyo8xpVonwvS");
		
		dateandtimebutton = (Button) findViewById(R.id.dateandtime_button);
		eventTime = Calendar.getInstance();
		sdf = new SimpleDateFormat("MMM d, yyyy, h:mm a");
		
		// TODO
		// makeEditTextsFloatToTop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_lunch_view, menu);
		return true;
	}
	
	public void showBothPickers(View v){
		showTimePickerDialog(v);
		showDatePickerDialog(v);
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	public static class DatePickerFragment extends DialogFragment
    	implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it			
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			eventTime.set(year, month, day);
			Date date = eventTime.getTime();
			dateandtimebutton.setText(sdf.format(date));
		}
	}
	
	public static class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			eventTime.set(eventTime.get(Calendar.YEAR), eventTime.get(Calendar.MONTH), eventTime.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
			Date date = eventTime.getTime();
			dateandtimebutton.setText(sdf.format(date));
		}
	}
	
	public void preview(View v){
		EditText emailText = (EditText) findViewById(R.id.email_editText);
		EditText menuText = (EditText) findViewById(R.id.menu_editText);
		EditText locationText = (EditText) findViewById(R.id.location_editText);
		EditText eventText = (EditText) findViewById(R.id.eventname_editText);
		EditText catchText = (EditText) findViewById(R.id.thecatch_editText);
		Utilities utils = new Utilities();
		
		if(!utils.isValidEmail(emailText.getText().toString())){
			Toast.makeText(getApplicationContext(), "Enter a valid email address!", Toast.LENGTH_SHORT).show();
		}
		else if(menuText.getText().toString().length() < 1){
			Toast.makeText(getApplicationContext(), "Enter a menu item!", Toast.LENGTH_SHORT).show();
		}
		else if(dateandtimebutton.getText().length() < 1){
			Toast.makeText(getApplicationContext(), "Select a time!", Toast.LENGTH_SHORT).show();
		}
		else if(locationText.getText().toString().length() < 1){
			Toast.makeText(getApplicationContext(), "Enter a location!", Toast.LENGTH_SHORT).show();
		}
		else if(eventText.getText().toString().length() < 1){
			Toast.makeText(getApplicationContext(), "Enter an event name!", Toast.LENGTH_SHORT).show();
		}
		else if(catchText.getText().toString().length() < 1){
			Toast.makeText(getApplicationContext(), "What's the catch?", Toast.LENGTH_SHORT).show();
		}
		else{
			Intent i = new Intent(getApplicationContext(), LunchEvent.class);
			String[] lunchInfo = new String[6];
			lunchInfo[0] = eventText.getText().toString();
			lunchInfo[1] = menuText.getText().toString();
			lunchInfo[2] = eventTime.getTime().toString();
			lunchInfo[3] = locationText.getText().toString();
			lunchInfo[4] = catchText.getText().toString();
			lunchInfo[5] = emailText.getText().toString();
			i.putExtra("lunch info", lunchInfo);
			i.putExtra("preview", true);
			startActivity(i);
		}
		
		
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
