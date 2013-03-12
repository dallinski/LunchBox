package com.dallinc.lunchbox;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.example.lunchbox.R;
import com.parse.ParseObject;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import android.widget.*;

public class LunchEvent extends FragmentActivity {
	
	String[] info;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lunch_event_view);
		
		info = getIntent().getExtras().getStringArray("lunch info");
		boolean isPreview = getIntent().getExtras().getBoolean("preview");
		
		TextView title = (TextView) findViewById(R.id.titleText);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Hoefler Text Italic.ttf");
	    title.setTypeface(font);
	    title.setText(info[0]);
	    
	    TextView menu = (TextView) findViewById(R.id.menu);
		Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Hoefler Text Italic.ttf");
	    menu.setTypeface(font2);
	    menu.setText(info[1]);
	    
	    TextView time = (TextView) findViewById(R.id.time);
		Typeface font3 = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLt.ttf");
	    time.setTypeface(font3);
	    String toDisplay = "";
	    try {
			String[] partsofdate = info[2].split(" ");
			String tempstring = partsofdate[1] + " " + partsofdate[2] + ", " + partsofdate[3];
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, HH:mm:ss");
			Date date = sdf2.parse(tempstring);
			SimpleDateFormat dispsdf = new SimpleDateFormat("MMMMM dd, h:mm a");
			toDisplay = dispsdf.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    time.setText(toDisplay);
	    
	    TextView place = (TextView) findViewById(R.id.place);
		Typeface font4 = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLt.ttf");
	    place.setTypeface(font4);
	    place.setText(info[3]);
	    
	    TextView description = (TextView) findViewById(R.id.description);
		Typeface font5 = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLt.ttf");
	    description.setTypeface(font5);
	    description.setText(info[4]);
	    
	    if(!isPreview){
	    	Button submit = (Button) findViewById(R.id.submit);
	    	submit.setVisibility(View.GONE);
	    }
	    
	    int pixel=this.getWindowManager().getDefaultDisplay().getWidth();
	    int dp =pixel/(int)getResources().getDisplayMetrics().density;
	    
	    LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout1);
	    int padding = dp/11;
	    ll.setPadding(padding, padding, padding, padding);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lunch_event_view, menu);
		return true;
	}
	
	public void submit(View v) throws ParseException{
		ParseObject po = new ParseObject("Lunch");		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		Date date = sdf.parse(info[2]);
		String[] partsofdate = info[2].split("[ :]");
		date.setHours(Integer.parseInt(partsofdate[3]));
		po.put("date", date);
		po.put("menu", info[1]);
		po.put("email", info[5]);
		po.put("location", info[3]);
		po.put("description", info[4]);
		po.put("title", info[0]);
		po.saveInBackground();
		
		Toast.makeText(getApplicationContext(), "Event submitted!", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(this, Welcome.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
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
