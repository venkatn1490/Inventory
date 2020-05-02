package com.medrep.app.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class OTPUtil {

	public static void main(String[] args) throws Exception{
		
		for(int i = 0; i<10;i++)
		System.out.println(generateIntToken());

	}
	
	public static String  generateToken()
	{
		return String.valueOf(UUID.randomUUID());
	}
	
	public static String  generateIntToken()
	{
		SecureRandom prng=null;
		try 
		{
			prng = SecureRandom.getInstance("SHA1PRNG");
		} 
		catch (NoSuchAlgorithmException e) 
		{
			
			e.printStackTrace();
		}
		
		int number = prng.nextInt(999999);
		if(number < 99999)
		{
			number = number + 900000;
		}
	    String randomNum = Integer.toString( number);	    
		return randomNum;
	}
	
	
}
