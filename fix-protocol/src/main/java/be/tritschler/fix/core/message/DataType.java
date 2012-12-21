package be.tritschler.fix.core.message;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataType {

	public static final String DATE_FORMAT = "yyyyMMdd";
	public static final String TIME_FORMAT = "yyyyMMdd-HH:mm:ss";	
	
	public static Integer getInt(String str) {
		try {
			int i = Integer.parseInt(str);
			if (i > -99999 && i < 99999) return i;
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static BigDecimal getFloat(String str) {
		try {
			BigDecimal b = new BigDecimal(str);
			return b;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date getDate(String date) {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			return dateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String setDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			return dateFormat.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date getTime(String time) {
		DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
		try {
			return dateFormat.parse(time);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String setTime(Date time) {
		DateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
		try {
			return dateFormat.format(time);
		} catch (Exception e) {
			return null;
		}
	}
}
