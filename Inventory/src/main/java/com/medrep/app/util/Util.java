package com.medrep.app.util;

import java.util.Collection;
import java.util.Date;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class Util {
	public static String getLocationType(String locationType) {
		String location = "Hospital";
		try {
			if (locationType != null) {
				switch (Integer.parseInt(locationType)) {
				case 1:
					location = "Hospital";
					break;
				case 2:
					location = "Private Clinic";
					break;
				default:
					location="Hospital";
				}
			}
		} catch (NumberFormatException e) {
			if("Hospital".equalsIgnoreCase(locationType))
				location="Hospital";
			else if("Private Clinic".equalsIgnoreCase(locationType))
				location="Private Clinic";
		}
		return location;
	}

	public static boolean isEmpty(String str){
		return str==null || (str!=null && str.trim().length()==0);
	}

	public static boolean isEmpty(Object str){
		return str==null;
	}

	public static boolean isZeroOrNull(Object str){
		try{
		if(str==null)
			return true;
		if(str instanceof Integer)
			return ((Integer)str).intValue()==0;
		if(str instanceof String)
			return Integer.parseInt((String)str)==0;
		}catch(Exception e){}

		return false;
	}



	public static boolean hasLength(String str,int length){
		return str!=null && str.trim().length()>=length;
	}

	public static boolean isEmpty(Collection collection) {
		return collection==null || collection.isEmpty();
	}

	public static String subString(String str, int length) {
		if(!isEmpty(str)){
			return str.length()>=length?str.substring(0, length):str;
		}
		return null;
	}

	public static String getTitle(String title) {
		if(isEmpty(title))
			return "";
		return title+". ";
	}

	public static String subStringWithEllipsis(String str, int length) {
		if(!isEmpty(str)){
			return str.length()>=length?str.substring(0, length)+"...":str;
		}
		return null;
	}

	public static String stripHtml(String html){
		if(isEmpty(html))
			return "";
		return html.replaceAll("\\<.*?>","");
	}

	public static int daysBetween(Date d1, Date d2) {
		return Days.daysBetween(new LocalDate(d1.getTime()), new LocalDate(d2.getTime())).getDays();
	}

}