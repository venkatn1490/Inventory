package com.venkat.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertor
{

	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDD1 = "yyyy/MM/dd";
	public static final String YYYYMMDD2 = "yyyy-MM-dd";
	public static final String YYYYMMDDHM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDDHHMISS2="yyyy-MM-dd HH:mm:ss";

	public static final String HHmmss = "HH:mm";
	public static final String YYYYMMDDHHMISS1="yyyy/MM/dd HH:mm";
	public static final String YYYYMMDDHHMISS = "yyyyMMddHHmmss";
	public static final String TIME_IN_AM_PM="MMM dd,yyyy KK:mm a";
	public static String convertDateToString(Date date, String pattern)
	{
		String dateStr = "";
		if(date!= null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			dateStr = sdf.format(date);
		}
		return dateStr;
	}

	public static Date convertStringToDate(String date, String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date _date = null;
		if(date!=null){
			try {
				_date = sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return _date;
	}


	public static String convertStringToDate(String date, String inPattern,String toPattern)
	{
		return convertDateToString(convertStringToDate(date, inPattern),toPattern);
	}

	public static void main(String[] args) {
		System.out.println(convertDateToString(convertStringToDate("20170201110527", YYYYMMDDHHMISS),TIME_IN_AM_PM));
	}

}
