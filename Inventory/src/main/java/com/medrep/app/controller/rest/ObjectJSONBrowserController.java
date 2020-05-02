package com.medrep.app.controller.rest;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medrep.app.model.AppResponse;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.DoctorAppointment;
import com.medrep.app.model.DoctorNotification;
import com.medrep.app.model.ForgotPassword;
import com.medrep.app.model.Location;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.model.Survey;
import com.medrep.app.model.User;

@Controller
@RequestMapping("/preapi/jsonbrowser")
public class ObjectJSONBrowserController {

	public static final String DOCTOR_JSON = "/doctorJSON";
	public static final String MEDREP_JSON = "/medrepJSON";
	public static final String DP_JSON = "/dpJSON";
	public static final String DOCAPPT_JSON = "/docAppointmentJSON";
	public static final String FORGOT_PASSWORD_JSON = "/forgotPasswordJSON";
	public static final String SURVEY_JSON = "/surveyJSON";
	public static final String UPDATE_NOTIFICATION_JSON = "/updateNotificationJSON";


	@RequestMapping(value = ObjectJSONBrowserController.UPDATE_NOTIFICATION_JSON, method = RequestMethod.GET)
    public @ResponseBody DoctorNotification updateNotificationJSON()
	{
		DoctorNotification doctorNotification = new DoctorNotification();

		doctorNotification.setCompanyId(1);
		doctorNotification.setDoctorId(1);
		doctorNotification.setFavourite(true);
		doctorNotification.setNotificationId(1);
		doctorNotification.setViewedOn("20150905011030");
		doctorNotification.setViewStatus("Y");
		doctorNotification.setUserNotificationId(1);

        return doctorNotification;
    }




	@RequestMapping(value = ObjectJSONBrowserController.DOCTOR_JSON, method = RequestMethod.GET)
    public @ResponseBody Doctor doctorJSON()
	{
		Doctor doctor = new Doctor();
		doctor.setFirstName("Doctor");
		doctor.setMiddleName("");
		doctor.setLastName("Doctor");
		doctor.setUsername("doctor.doctor@dummy.com");
		doctor.setPassword("passpwrd12345");
		doctor.setPhoneNo("1213456789");
		doctor.setMobileNo("55555555");
		doctor.setTitle("Dr. ");
		doctor.setQualification("Doctor");
		doctor.setRegistrationNumber("123456789");
		doctor.setRegistrationYear("2014");
		doctor.setStateMedCouncil("XP");
		doctor.setEmailId("doctor.doctor@dummy.com");
		doctor.setTherapeuticId("1");
		doctor.setRoleId(1);
		doctor.setRoleName("Dcotor");
		Location location = new Location();
		location.setAddress1("Address 1");
		location.setAddress2("Address 2");
		location.setCity("Gurgaon");
		location.setZipcode("123456");
		location.setState("AP");
		doctor.getLocations().add(location);
		doctor.getLocations().add(location);
        return doctor;
    }


	@RequestMapping(value = ObjectJSONBrowserController.MEDREP_JSON, method = RequestMethod.GET)
	public @ResponseBody PharmaRep medrepJSON()
	{
		PharmaRep rep = new PharmaRep();
		Location location = new Location();

		location.setAddress1("Address 1");
		location.setAddress2("Address 2");
		location.setCity("Gurgaon");
		location.setZipcode("123456");
		location.setState("AP");

		rep.getLocations().add(location);
		rep.getLocations().add(location);
		rep.setTitle("Mr.");
		rep.setUsername("ravish.jha@dummy.com");
		rep.setRoleName("pharmarep");
		rep.setPhoneNo("9650622655");
		rep.setPassword("password");
		rep.setMobileNo("9650622655");
		rep.setFirstName("Ravish");
		rep.setMiddleName("Kumar");
		rep.setLastName("Jha");
		rep.setAlias("Ravish");
		rep.setEnabled(true);
		rep.setAlternateEmailId("some@some.com");
		rep.setLoginTime(new Date());
		rep.setUserSecurityId(123445876);
		rep.setRoleId(3);
		rep.setRepId(123);
		rep.setManagerId(1234);
		rep.setCoveredZone("Zone1");
		rep.setCoveredArea("Gurgaon");
		rep.setCompanyId(123);

		return rep;
	}

	@RequestMapping(value = ObjectJSONBrowserController.DOCAPPT_JSON, method = RequestMethod.GET)
    public @ResponseBody DoctorAppointment docAppointmentJSON()
	{
		DoctorAppointment doctorAppt = new DoctorAppointment();

		doctorAppt.setAppointmentDesc("Test Description");
		doctorAppt.setAppointmentId(0);
		doctorAppt.setCreatedOn(new Date());
		doctorAppt.setDoctorId(2);
		doctorAppt.setPharmaRepId(1);
		doctorAppt.setDuration(30);
		doctorAppt.setFeedback("Feedback");
		doctorAppt.setNotificationId(1);
		doctorAppt.setStartDate("201508325");
		doctorAppt.setStatus("New");
		doctorAppt.setTitle("Mr.");
		return doctorAppt;
    }


	@RequestMapping(value = ObjectJSONBrowserController.DP_JSON, method = RequestMethod.GET)
    public @ResponseBody DisplayPicture dpJSON()
	{
		DisplayPicture dp = new DisplayPicture();
		dp.setLoginId("umar.ashraf@gmail.com");
		dp.setdPicture("http://122.175.50.252:8080/static/images/blank.jpg");
		return dp;

    }

	@RequestMapping(value = ObjectJSONBrowserController.FORGOT_PASSWORD_JSON, method = RequestMethod.GET)
    public @ResponseBody ForgotPassword forgotPasswordJSON()
	{
		ForgotPassword password = new ForgotPassword();
		password.setConfirmPassword(":P@ssword1");
		password.setNewPassword(":P@ssword1");
		password.setOtp("12345");
		password.setUserName("umar.ashraf@gmail.com");
		password.setVerificationType("SMS");

		return password;
    }

	@RequestMapping(value = ObjectJSONBrowserController.SURVEY_JSON, method = RequestMethod.GET)
    public @ResponseBody Survey surveyJSON()
	{
		Survey survey = new Survey();
		survey.setDoctorId(14);
		survey.setSurveyId(1);
		survey.setDoctorSurveyId(1);
		survey.setStatus("NEW");
		return survey;
    }

}
