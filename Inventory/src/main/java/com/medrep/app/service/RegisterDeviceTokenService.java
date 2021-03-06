package com.medrep.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.DeviceTokenDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorPlusDAO;
import com.medrep.app.dao.GroupDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserSecurityDAO;

@Service("registerDeviceTokenService")
@Transactional
public class RegisterDeviceTokenService {
	
	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	DoctorPlusDAO doctorPlusDAO;

	@Autowired
	UserSecurityDAO securityDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;
	
	@Autowired
	GroupDAO groupDAO;
	
	@Autowired
	DeviceTokenDAO deviceTokenDAO;

}
