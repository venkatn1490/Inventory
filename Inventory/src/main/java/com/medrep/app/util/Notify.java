package com.medrep.app.util;

import java.util.ArrayList;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class Notify {

	public static void main(String args[]) {

		ArrayList<String> devicesList = new ArrayList<String>();
		devicesList.add("dYj11N-x0Hs:APA91bFNgGZC_njbeMGdIz7mULahXjDLHKsitKVmQooDdwdL8sjXeqyRy4wrwlZ8ZVfvwm5GoevUsqr-FlC6N-WKBK3RoQ3IqzeYUV3mP14WG1dbpFh9pJVR2ctRfxoILiJeXWPw4nOH");
		//devicesList.add("APA91bFEDcATuAhY1_10utK6vYT9A9Hp6OXmI9JwCSqT6Iapil5VGl_zA1iziCCgpsJtWkTS4v1PwNuOkL6oaPZIcpULO73hwThDaePHg8sz2OfEGhiegz8Wfc59zc7PGvOga5iKIU1fB1jrlaN2bEHoQn-6CgywIw");
		Notify.sendNotification("Helloo raju.", devicesList);
	}
	
	public static void sendNotification(String notification,ArrayList<String> devicesList){
		//String SENDER_ID=PropertiesUtil.getProperty("SENDER_ID");
		
		
		String SENDER_ID="AIzaSyCk427bwQv1aEPFOcx0kOBb8eVaXYwaXLc";
		System.out.println("notification="+notification);
		System.out.println("devicesList="+devicesList);
		System.out.println("SENDER_ID="+SENDER_ID);
		try {
			Sender sender = new Sender(SENDER_ID);
			
			
			// Use this line to send message without payload data
			// Message message = new Message.Builder().build();

			// use this line to send message with payload data
			Message message = new Message.Builder()
			.collapseKey("1")
			.timeToLive(10) // 10 days (secs)
			.delayWhileIdle(true)
			.addData("message",notification)
			.build();

			// Use this code to send to a single device
			/*Result result = sender
			 .send(message,
			 "APA91bFVl73p_FrjZOUULcAy1sgJklwI8mQuiN0-IvNI3SFkY3rin_KiIqiTSQhsV4QKvCxzQ30mGYlJWqq9P2Izs9XOOvw9vPmATsPwVUElaMutuG4HBg_7dUjdAoxUzah1tXgWtdkdrAgBkMAoC4l8m60sLs9IOg",
			 10);*/

			// Use this for multicast messages
			MulticastResult result = sender.send(message, devicesList, 10);
			//sender.send(message, devicesList, 10);

			System.out.println(result.toString());
			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {
				}
			} else {
				int error = result.getFailure();
				System.out.println(error);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
