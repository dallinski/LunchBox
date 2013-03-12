package com.dallinc.lunchbox;

import java.text.SimpleDateFormat;
import java.util.*;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.*;
import android.content.*;
import android.content.res.ColorStateList;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.*;
import android.text.style.RelativeSizeSpan;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.example.lunchbox.R;
import com.parse.*;

public class RootTable extends FragmentActivity {
	List<ParseObject> lunchList;
	ArrayList<ParseObject> todaysLunches;
	ArrayList<ParseObject> tomorrowsLunches;
	ArrayList<ParseObject> weeksLunches;
	ArrayList<ParseObject> monthsLunches;
	boolean noWeekLunches;
	boolean noMonthLunches;
	int weeksLunchSection;
	int monthsLunchSection;
	ParseObject selectedLunch;
	View activityInd;
	int TODAYS_LUNCHES_SECTION = 0;
	int TOMORROWS_LUNCHES_SECTION = 1;
	int WEEKS_LUNCHES_SECTION = 2;
	int MONTHS_LUNCHES_SECTION = 3;
	int NEW_LUNCH_SECTION = 4;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.root_table_view);
		
		final ProgressBar pbar = (ProgressBar) findViewById(R.id.progressBar1);
		
		Parse.initialize(this, "DP1JwuroXiST1NC3woYkvhrbHSR1bPHwxuJBiNZQ", "QTWcDJPMVS4B63jOzDVQ696irwA0Lyo8xpVonwvS");
		
		lunchList = new ArrayList<ParseObject>();
		
		ParseQuery eventQuery = new ParseQuery("Lunch");
		Date d1 = new Date();
		d1.setHours(0);
		d1.setMinutes(0);
		eventQuery.whereGreaterThanOrEqualTo("date", d1);
		eventQuery.orderByAscending("date");
		eventQuery.findInBackground(new FindCallback() {
			  public void done(List<ParseObject> objects, ParseException e) {
			    if (e == null) {
			        // The query was successful.
			    	lunchList = objects;
			    	todaysLunches = new ArrayList<ParseObject>();
		            tomorrowsLunches = new ArrayList<ParseObject>();
		            weeksLunches = new ArrayList<ParseObject>();
		            monthsLunches = new ArrayList<ParseObject>();
		            noMonthLunches = false;
		            noWeekLunches = false;
		            
		            for (int i = 0; i < lunchList.size(); i++){
		                ParseObject event = lunchList.get(i);
		                Date date = (Date) event.get("date");
		                Utilities u = new Utilities();
		                if(u.isToday(date)) todaysLunches.add(event);
		                else if(u.isTomorrow(date)) tomorrowsLunches.add(event);
		                else if(u.isThisWeek(date)) weeksLunches.add(event);
		                else if(u.isThisMonth(date)) monthsLunches.add(event);
		            }
		            
		            weeksLunchSection = WEEKS_LUNCHES_SECTION;
		            monthsLunchSection = MONTHS_LUNCHES_SECTION;
		            
		            if(weeksLunches.size() == 0){
		            	noWeekLunches = true;
		            	weeksLunchSection = -1;
		            	monthsLunchSection--;
		            }
		            if(monthsLunches.size() == 0){
		            	noMonthLunches = true;
		            	monthsLunchSection = -1;
		            }
		            cellForRowAtIndexPath(1);
			    } else {
			        // Something went wrong.
			    	System.out.println("Didn't get the parse objects!");
			    }
			    pbar.setVisibility(ProgressBar.GONE);
			  }
			 
			});
		
		
		Drawable byu_logo = getResources().getDrawable(R.drawable.byu_logo);
		byu_logo.setAlpha(70); // translucent
		
		Button addlunch = (Button) findViewById(R.id.addLunch);
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
		getMenuInflater().inflate(R.menu.root_table_view, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		DialogFragment newFragment = new AboutFragment();
	    newFragment.show(getSupportFragmentManager(), "About");
		return true;
	}
	
	private void cellForRowAtIndexPath(int indexPath) {
	    ListView listlayout = (ListView) findViewById(R.id.listView);
	    
	    ArrayAdapter adapter = new ParseObjectAdapter(this, android.R.layout.simple_list_item_1, lunchList);
	    listlayout.setAdapter(adapter);
	    listlayout.setDivider(null);
	    listlayout.setDividerHeight(0);
	    listlayout.setCacheColorHint(0);
	}
	
	class ParseObjectAdapter extends ArrayAdapter{

		@SuppressWarnings("unchecked")
		public ParseObjectAdapter(Context context, int textViewResourceId,
				List<ParseObject> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LinearLayout cell = new LinearLayout(getApplicationContext());
			stylizeCell(cell);
			
			TextView label1 = new TextView(getApplicationContext());
			stylizeLabel(label1);
		    cell.addView(label1);
			
			ParseObject lunch;
			if(position == TODAYS_LUNCHES_SECTION){
				label1.setText("Today");
				if (todaysLunches.size() == 0){
					Button b1 = new Button(getApplicationContext());
				    stylizeButton(b1);
					cell.addView(b1);
		            b1.setText("Today is Vacant");
		            return cell;
		        }
				for(int i=0; i<todaysLunches.size(); i++){
					lunch = todaysLunches.get(i);
			        addButtonToCell(lunch, cell, false, false);
				}
			}
			else if (position == TOMORROWS_LUNCHES_SECTION){
				label1.setText("Tomorrow");
				if (tomorrowsLunches.size() == 0){
					Button b1 = new Button(getApplicationContext());
				    stylizeButton(b1);
					cell.addView(b1);
		            b1.setText("Tomorrow is Vacant");
		            return cell;
		        }
				for(int i=0; i<tomorrowsLunches.size(); i++){
					lunch = tomorrowsLunches.get(i);
			        addButtonToCell(lunch, cell, false, false);
				}
		        
			}
			else if (position == weeksLunchSection){
				label1.setText("This Week");
				for(int i=0; i<weeksLunches.size(); i++){
					lunch = weeksLunches.get(i);
			        addButtonToCell(lunch, cell, true, false);
				}
		        
			}
			else if (position == monthsLunchSection){
				label1.setText("Upcoming");
				for(int i=0; i<monthsLunches.size(); i++){
					lunch = monthsLunches.get(i);
			        addButtonToCell(lunch, cell, false, true);
				}
		        
			}
			else{ 
				TextView temp = new TextView(getApplicationContext());
				temp.setHeight(3);
				return temp;
			}
			
			return cell;
		}

		private void stylizeLabel(TextView label){
			Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Hoefler Text Italic.ttf");
		    label.setTypeface(font);
		    label.setTextSize(25);
		    label.setTextColor(Color.parseColor("#800000"));
		}
		
		private void stylizeCell(LinearLayout cell){
			cell.setPadding(10, 30, 10, 0);
			cell.setOrientation(LinearLayout.VERTICAL);
		}
		
		
	};
	
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
	
	@SuppressLint("SimpleDateFormat")
	public void addButtonToCell(ParseObject lunch, LinearLayout cell, boolean week, boolean month){
		final String[] lunchInfo = new String[5];
		setLunchInfo(lunchInfo, lunch);
		Button b1 = new Button(getApplicationContext());
	    stylizeButton(b1);
		cell.addView(b1);
        
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        if(week){ sdf = new SimpleDateFormat("EEE, h:mm a"); }
        else if(month){ sdf = new SimpleDateFormat("MMM d, h:mm a"); }
        
        setButtonText(b1, lunch.getString("menu"), sdf.format(lunch.getDate("date")));
        
        b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), LunchEvent.class);
				i.putExtra("lunch info", lunchInfo);
				i.putExtra("preview", false);
				startActivity(i);
			}
		});
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void stylizeButton(Button button){
		button.setBackgroundResource(R.drawable.edittext_rounded_corners);
		button.setHeight(130);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			button.setAlpha((float) 0.6);
		button.setTextColor(Color.parseColor("#800000"));
	    button.setTextSize(20);
	    button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
	}
	
	private void setButtonText(Button b, String top, String bottom){
		Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLt.ttf");
		Spannable span = new SpannableString(top + "\n" +  bottom);
		//Big font till you find `\n`
		span.setSpan(new CustomTypefaceSpan("", customFont), 0, top.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		//Small font from `\n` to the end
		span.setSpan(new RelativeSizeSpan(0.7f), top.length(), (top.length()+bottom.length()+1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		b.setText(span);
	}
	
	private void setLunchInfo(String[] info, ParseObject lunch){
		info[0] = lunch.getString("title");
		info[1] = lunch.getString("menu");
		info[2] = lunch.getDate("date").toString();
		info[3] = lunch.getString("location");
		info[4] = lunch.getString("description");
	}

}
