package com.medrep.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.dao.DoctorRepDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.DoctorRepEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.Doctor;
import com.medrep.app.model.PharmaRep;
import com.medrep.app.util.Util;

@Service("doctorRepService")
@Transactional
public class DoctorRepService {

	@Autowired
	DoctorRepDAO doctorRepDAO;

	@Autowired
	DoctorDAO doctorDAO;

	@Autowired
	PharmaRepDAO pharmaRepDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	CompanyDAO companyDAO;

	private static final Log log = LogFactory.getLog(DoctorRepService.class);

	public List<PharmaRep> getDoctorReps(String loginId)
	{
		List<PharmaRep> reps = new ArrayList<PharmaRep>();
		DoctorEntity doctor = doctorDAO.findByLoginId(loginId);
		if(doctor != null)
		{
			List<DoctorRepEntity> doctorRepEntities = doctorRepDAO.findByDoctorId(doctor.getDoctorId());
			for(DoctorRepEntity doctorRepEntity : doctorRepEntities)
			{
				if(doctorRepEntity.getRep() != null)
				{
					PharmaRepEntity pharmaRepEntity = doctorRepEntity.getRep();
					PharmaRep rep = new PharmaRep();
					UserEntity user = pharmaRepEntity.getUser();

					if (user != null)
					{
						rep.setEmailId(user.getEmailId());
						rep.setFirstName(user.getFirstName());
//						rep.setMiddleName(user.getMiddleName());
						rep.setLastName(user.getLastName());
						rep.setPhoneNo(user.getPhoneNo());
						rep.setAlias(user.getAlias());
						rep.setTitle(user.getTitle());
						rep.setAlternateEmailId(user.getAlternateEmailId());
						rep.setMobileNo(user.getMobileNo());
					}
					rep.setCompanyId(pharmaRepEntity.getCompanyId());
					rep.setCoveredArea(pharmaRepEntity.getCoveredArea());
					rep.setCoveredZone(pharmaRepEntity.getCoveredZone());
					rep.setManagerId(pharmaRepEntity.getManagerId());
					rep.setTherapeuticId(pharmaRepEntity.getTherapeuticId());
					if (pharmaRepEntity.getTherapeuticId() != null) {
						try {
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO
									.findById(TherapeuticAreaEntity.class,Integer.parseInt(pharmaRepEntity.getTherapeuticId()));
							if (therapeuticAreaEntity != null) {
								rep.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						} catch (Exception e) {
							// Ignore
						}
					}
					if (pharmaRepEntity.getCompanyId() != null) {
						try {
							CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,pharmaRepEntity.getCompanyId());
							if (companyEntity != null) {
								rep.setCompanyName(companyEntity.getCompanyName());
							}
						} catch (Exception e) {
							// Ignore
						}
					}

					reps.add(rep);
				}
			}
		}


		return reps;

	}

	public List<Doctor> getRepDoctors(String loginId)
	{
		List<Doctor> doctors = new ArrayList<Doctor>();
		PharmaRepEntity pharmaRep = pharmaRepDAO.findByLoginId(loginId);
		if(pharmaRep != null)
		{
			List<DoctorRepEntity> doctorRepEntities = doctorRepDAO.findByRepId(pharmaRep.getRepId());
			for(DoctorRepEntity doctorRepEntity : doctorRepEntities)
			{
				if(doctorRepEntity.getDoctor() != null)
				{
					DoctorEntity doctorEntity = doctorRepEntity.getDoctor();
					Doctor doctor = new Doctor();
					UserEntity user = doctorEntity.getUser();

					if (user != null)
					{
						doctor.setEmailId(user.getEmailId());
						doctor.setFirstName(Util.getTitle(user.getTitle())+user.getFirstName());
//						doctor.setMiddleName(user.getMiddleName());
						doctor.setLastName(user.getLastName());
						doctor.setPhoneNo(user.getPhoneNo());
						doctor.setAlias(user.getAlias());
						doctor.setTitle(user.getTitle());
						doctor.setAlternateEmailId(user.getAlternateEmailId());
						doctor.setMobileNo(user.getMobileNo());
					}
					doctor.setQualification(doctorEntity.getQualification());
					doctor.setRegistrationNumber(doctorEntity.getRegistrationNumber());
					doctor.setRegistrationYear(doctorEntity.getRegistrationYear());
					doctor.setStateMedCouncil(doctorEntity.getStateMedCouncil());
					doctor.setTherapeuticId(doctorEntity.getTherapeuticId());
					if(doctorEntity.getTherapeuticId() != null)
					{
						try
						{
							TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,Integer.parseInt(doctorEntity.getTherapeuticId()));
							if(therapeuticAreaEntity != null)
							{
								doctor.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
							}
						}
						catch(Exception e)
						{
							//Ignore
						}
					}

					doctors.add(doctor);
				}
			}
		}


		return doctors;

	}

	public PharmaRep getPharmaRep(String loginId) {
		PharmaRepEntity pharmaRepEntity = pharmaRepDAO.findByLoginId(loginId);
		PharmaRep pharmaRep=new PharmaRep();
		if(!Util.isEmpty(pharmaRepEntity))
			pharmaRep.setStatus(pharmaRepEntity.getStatus());
		return pharmaRep;
	}

}
