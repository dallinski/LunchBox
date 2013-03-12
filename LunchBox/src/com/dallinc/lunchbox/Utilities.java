package com.dallinc.lunchbox;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utilities {
	@SuppressWarnings("deprecation")
	public boolean isToday(Date date) {
		Date current = Calendar.getInstance().getTime();
		
		return (date.getDate() == current.getDate() 
			 && date.getMonth() == current.getMonth() 
			 && date.getYear() == current.getYear());
	}
	@SuppressWarnings("deprecation")
	public boolean isTomorrow(Date date) {
		Date tomorrow = Calendar.getInstance().getTime();
		tomorrow.setDate(tomorrow.getDate()+1);
		
		return (date.getDate() == tomorrow.getDate() 
				 && date.getMonth() == tomorrow.getMonth() 
				 && date.getYear() == tomorrow.getYear());
	}
	@SuppressWarnings("deprecation")
	public boolean isThisWeek(Date date) {
		Date inaweek = Calendar.getInstance().getTime();
		inaweek.setDate(inaweek.getDate()+7);
		
		return (date.before(inaweek) && date.after(Calendar.getInstance().getTime()));
	}
	@SuppressWarnings("deprecation")
	public boolean isThisMonth(Date date) {
		Date upcoming = Calendar.getInstance().getTime();
		upcoming.setDate(upcoming.getDate()+45);
		
		return (date.before(upcoming) && date.after(Calendar.getInstance().getTime()));
	}
	public boolean isValidEmail (String candidate) {
	    String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	    					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$";
	    return Pattern.matches(emailRegex, candidate);
	}
}
