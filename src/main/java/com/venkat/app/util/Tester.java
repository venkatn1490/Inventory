package com.venkat.app.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Tester {

	    static final String FROM = "info@venkat.in";   // Replace with your "From" address. This address must be verified.
	    static final String TO = "email.ashrafumar@gmail.com";  // Replace with a "To" address. If your account is still in the 
	                                                       // sandbox, this address must be verified.
	    
	    static final String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
	    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
	    
	    // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
	    static final String SMTP_USERNAME = "info@venkat.in";  // Replace with your SMTP username.
	    static final String SMTP_PASSWORD = "Erfolg@123";//venkat@1  // Replace with your SMTP password.
	    
	    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
	    static final String HOST = "smtp.gmail.com";    
	    
	    // Port we will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use
	    // STARTTLS to encrypt the connection.
	    static final int PORT = 587;
	    
	    public static void main(String[] args) throws Exception {

	        // Create a Properties object to contain connection configuration information.
	    	Properties props = System.getProperties();
	    	props.put("mail.transport.protocol", "smtp");
	    	props.put("mail.smtp.host", HOST); 
	    	props.put("mail.smtp.port", "587"); 
	    	props.put("mail.debug", "true");
	    	
	    	
	    	// Set properties indicating that we want to use STARTTLS to encrypt the connection.
	    	// The SMTP session will begin on an unencrypted connection, and then the client
	        // will issue a STARTTLS command to upgrade to an encrypted connection.
	    	props.put("mail.smtp.auth", "true");
	    	props.put("mail.smtp.starttls.enable", "true");
	    	props.put("mail.smtp.starttls.required", "true");

	        // Create a Session object to represent a mail session with the specified properties. 
	    	Session session = Session.getDefaultInstance(props);

	        // Create a message with the specified information. 
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(FROM));
	        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
	        msg.setSubject(SUBJECT);
	        msg.setContent(BODY,"text/plain");
	            
	        // Create a transport.        
	        Transport transport = session.getTransport();
	                    
	        // Send the message.
	        try
	        {
	            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
	            
	            // Connect to Amazon SES using the SMTP username and password you specified above.
	            transport.connect(SMTP_USERNAME, SMTP_PASSWORD);
	        	
	            // Send the email.
	            transport.sendMessage(msg, msg.getAllRecipients());
	            System.out.println("Email sent!");
	        }
	        catch (Exception ex) {
	            System.out.println("The email was not sent.");
	            ex.printStackTrace();
	            System.out.println("Error message: " + ex.getMessage());
	        }
	        finally
	        {
	            // Close and terminate the connection.
	            transport.close();        	
	        }
	    }
}
