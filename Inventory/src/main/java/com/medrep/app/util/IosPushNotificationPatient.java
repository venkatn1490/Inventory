package com.medrep.app.util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.VideoEntity;
import com.medrep.app.model.VideoModel;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;
import com.notnoop.apns.internal.Utilities;

public class IosPushNotificationPatient {

	private static final Logger logger = LoggerFactory.getLogger(IosPushNotificationPatient.class);

	public static void main(String[] args) {
//		pushMessage("4bc92e13bdd7dd21186d11227adc5ed3737c3d47410928259a8a29462eed8a00", "New IOS Notification");
	}
	
	
	

	public static void pushMessage(String token,String message, CompanyEntity companyEntity) {
		ApnsService service = null;
		String payload="";
		try {
			String certStream = IosPushNotificationPatient.class.getResource("MedRepPatients.p12").getPath();

			FileInputStream stream = new FileInputStream(certStream);
			service = APNS.newService().withSSLContext(Utilities.newSSLContext(stream, "venkatmedrep123", "PKCS12", "sunx509"))
					.withSandboxDestination().build();
			service.start();
			service.testConnection();
			try {
				PayloadBuilder payloadBuilder = APNS.newPayload();
				if(companyEntity!=null)
					payloadBuilder = payloadBuilder.badge(1).sound("default").alertBody(companyEntity.getCompanyName() +message);
				else
					payloadBuilder = payloadBuilder.badge(1).sound("default").alertBody(message);

				if (payloadBuilder.isTooLong()) {
					payloadBuilder = payloadBuilder.shrinkBody();
				}
				 payload = payloadBuilder.build();
				try {
					service.push(token, payload);
					logger.info("SUCCESS-->"+token+"-->"+payload);
				} catch (Exception e) {
					logger.error("FAILED-->"+token+"-->"+payload,e);
					e.printStackTrace();
				}
			} catch (Exception ex) {
				logger.error("FAILED-->"+token+"-->"+payload,ex);
				ex.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("FAILED-->"+token+"-->"+payload,e);
			e.printStackTrace();
		} finally {
			if (service != null) {
				service.stop();
			}
		}
	}
	
	
	
	public static void pushChatMessage(String token,String messageFrom, String  message) {
		ApnsService service = null;
		String payload="";
		try {
			String certStream = IosPushNotificationPatient.class.getResource("MedRepPatients.p12").getPath();

			FileInputStream stream = new FileInputStream(certStream);
			service = APNS.newService().withSSLContext(Utilities.newSSLContext(stream, "venkatmedrep123", "PKCS12", "sunx509"))
					.withSandboxDestination().build();
			service.start();
			
			service.testConnection();
			try {
				
				PayloadBuilder payloadBuilder = APNS.newPayload();
				payloadBuilder = payloadBuilder.badge(1).sound("default").customField("chatMessage", message).alertBody(messageFrom);
				if (payloadBuilder.isTooLong()) {
					payloadBuilder = payloadBuilder.shrinkBody();
				}
				 payload = payloadBuilder.build();
				try {
					service.push(token, payload);
					logger.info("SUCCESS-->"+token+"-->"+payload);
				} catch (Exception e) {
					logger.error("FAILED-->"+token+"-->"+payload,e);
					e.printStackTrace();
				}
			} catch (Exception ex) {
				logger.error("FAILED-->"+token+"-->"+payload,ex);
				ex.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("FAILED-->"+token+"-->"+payload,e);
			e.printStackTrace();
		} finally {
			if (service != null) {
				service.stop();
			}
		}
	}

	/*public static void push(String token,String message) {
		pushMessage(token, message,null);
	}*/
}
