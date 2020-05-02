package com.medrep.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.VideoEntity;

public class AndroidPushNotification {

	private static final String ANDROID_DEVICE_TEST="ANDROID_DEVICE_TEST";

	private static final Logger logger = LoggerFactory.getLogger(AndroidPushNotification.class);
	private static final String GOOGLE_SERVER_KEY = "AIzaSyDBiV21zgFt5tFy8tFey_cy4D4ftS0h44s";//"AIzaSyDhBAqFv9ta596CDhz_L7AK0c49E-QFgdo";
	public static void main(String[] args) {
		String token="APA91bEN29syK-DNfTVCpqSzewPsWHIIqwYi9Ln2JcQxG8IYlOmrodKkt-fAyOgIsMLvk3PMB8-JT1nlHcTzdaUTdxVotym4EwUkPdvL3cEKn9AZEovXbSc";//APA91bEwc2gCi80b1QgqU9u3rB3Qw9DnKTwgciPAKqbkG2x0CdAT9FT_SW4WZyCvUZwGXpiz_DefA1fIEUYBaoEwGJk_qd9Iqm1b54PaqPRfTM5VtxsW2Ac";
		String message="ANDROID_DEVICE_TEST";
		pushMessage(token, message, null);
	}

	@SuppressWarnings("unused")
	public static String getCanonicalId(String token) {
		Sender sender = new Sender(GOOGLE_SERVER_KEY);
		Message message=null;
		try {
			 message = new Message.Builder().collapseKey("message").timeToLive(3).delayWhileIdle(true)
					.addData("message", ANDROID_DEVICE_TEST).build();

			Result result = sender.send(message,token,1);
			String canonicalRegId = result.getCanonicalRegistrationId();
			return canonicalRegId;
		} catch (Exception e) {
			logger.error("ERROR-->"+token+"-->"+(message!=null?message:""),e);
			e.printStackTrace();
		}
		return null;
	}


	@SuppressWarnings("unused")
	public static String pushMessage(String token,String messageStr, CompanyEntity companyEntity) {
		Sender sender = new Sender(GOOGLE_SERVER_KEY);
		Message message=null;
		try {
			 message = new Message.Builder().collapseKey("message").timeToLive(3).delayWhileIdle(true)
					.addData("message", companyEntity!=null?(companyEntity.getCompanyName() + messageStr):messageStr).build();

			Result result = sender.send(message,token,1);
			String canonicalRegId = result.getCanonicalRegistrationId();
			logger.info("SUCCESS-->"+token+"-->"+message);
			return canonicalRegId;
		} catch (Exception e) {
			logger.error("ERROR-->"+token+"-->"+(message!=null?message:""),e);
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	public static String pushVideoMessage(String token,String messageStr, VideoEntity videoEntity) {
		Sender sender = new Sender(GOOGLE_SERVER_KEY);
		Message message=null;
		try {
			 message = new Message.Builder().collapseKey("message").timeToLive(3).delayWhileIdle(true)
					.addData("message",messageStr).addData("videoId",videoEntity.getVideoId().toString()).addData("docToken",videoEntity.getDocToken())
					.addData("sessionId",videoEntity.getSessionId())
					.addData("apiKey",videoEntity.getApiKey().toString()).build();
			 
			Result result = sender.send(message,token,1);
			String canonicalRegId = result.getCanonicalRegistrationId();
			logger.info("SUCCESS-->"+token+"-->"+message);
			return canonicalRegId;
		} catch (Exception e) {
			logger.error("ERROR-->"+token+"-->"+(message!=null?message:""),e);
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	public static String pushChatMessage(String token,String messageStr, String chatmessage) {
		Sender sender = new Sender(GOOGLE_SERVER_KEY);
		Message message=null;
		try {
			 message = new Message.Builder().collapseKey("message").timeToLive(3).delayWhileIdle(true)
					.addData("message", chatmessage+" "+messageStr).build();

			Result result = sender.send(message,token,1);
			String canonicalRegId = result.getCanonicalRegistrationId();
			logger.info("SUCCESS-->"+token+"-->"+message);
			return canonicalRegId;
		} catch (Exception e) {
			logger.error("ERROR-->"+token+"-->"+(message!=null?message:""),e);
			e.printStackTrace();
		}
		return null;
	}


	@SuppressWarnings("unused")
	public static String push(String token,String message) {
		String canonicalId=getCanonicalId(token);
		if(!Util.isEmpty(canonicalId))
			return canonicalId;
		return pushMessage(token,message,null);
	}

	@SuppressWarnings("unused")
	private String createBody(String token, String jsonMessage) {
		String gcmJSON = null;
		try {
			gcmJSON = "{" + "\"registration_ids\" : [\"" + token + "\"]," + "\"data\" : {" + jsonMessage + "}" + "}";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return gcmJSON;
	}
}
