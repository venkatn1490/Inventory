
package com.medrep.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.medrep.app.dao.DoctorConsultingDAO;
import com.medrep.app.dao.DoctorDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DoctorAppointmentEntity;
import com.medrep.app.entity.DoctorConsultingsEntity;
import com.medrep.app.entity.DoctorEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.NotificationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.model.DoctorAppointment;
import com.medrep.app.model.DoctorConsultingsModel;
import com.medrep.app.model.HospitalModel;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.Util;

@Service("ConsultingsService")
@Transactional
public class ConsultingsService {

	@Autowired
	DoctorConsultingDAO doctorConsultingDAo;

	@Autowired
	DoctorDAO doctorDAO;
	
	public void createDoctorConsulting(DoctorConsultingsModel doctorConsultingmodel ,String loginId) {
		
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		
		DoctorConsultingsEntity doctorConsultingEntity=new DoctorConsultingsEntity();
		if(doctorEntity.getDoctorId() !=null){
			doctorConsultingEntity.setDoctorId(doctorEntity.getDoctorId());
			doctorConsultingEntity.setConsultingsType(doctorConsultingmodel.getConsultingsType());
			doctorConsultingEntity.setContactNumber(doctorConsultingmodel.getContactNumber());
			doctorConsultingEntity.setMaxAppointments(doctorConsultingmodel.getMaxAppointments());
			doctorConsultingEntity.setFee(doctorConsultingmodel.getFee());
			doctorConsultingEntity.setConsultingsFrom(doctorConsultingmodel.getConsultingsFrom());
			doctorConsultingEntity.setConsultingsTo(doctorConsultingmodel.getConsultingsTo());
			doctorConsultingEntity.setConsultingsDays(doctorConsultingmodel.getConsultingsDays());
			doctorConsultingEntity.setResponsetimefrom(doctorConsultingmodel.getResponsetimefrom());
			doctorConsultingEntity.setResponsetimeto(doctorConsultingmodel.getResponsetimeto());
			doctorConsultingEntity.setAddress1(doctorConsultingmodel.getAddress1());
			doctorConsultingEntity.setAddress2(doctorConsultingmodel.getAddress2());
			doctorConsultingEntity.setLocatity(doctorConsultingmodel.getLocatity());
			doctorConsultingEntity.setCity(doctorConsultingmodel.getCity());
			doctorConsultingEntity.setState(doctorConsultingmodel.getState());

			doctorConsultingDAo.persist(doctorConsultingEntity);
		}
		

	}
	
	public Boolean removeDoctorConsulting(Integer ConsultingId){

		DoctorConsultingsEntity doctorConsultingentity=doctorConsultingDAo.findById(DoctorConsultingsEntity.class,ConsultingId);
		
		if(doctorConsultingentity != null){
			doctorConsultingDAo.remove(doctorConsultingentity);
			return true;

		}else{
			return false;

		}
	}
	
	public List<DoctorConsultingsModel> getMyConsultings(String loginId,String type) {
		DoctorEntity doctorEntity = doctorDAO.findByLoginId(loginId);
		List<DoctorConsultingsModel> doctorConsultingModels = new ArrayList<DoctorConsultingsModel>();
		List<DoctorConsultingsEntity> doctorConsultingEntity = null;
		try
		{
			doctorConsultingEntity = doctorConsultingDAo.findByDoctorId(doctorEntity.getDoctorId(),type);

		} catch (Exception e)
		{
			System.out.println("Entry not found");
		}
		for (DoctorConsultingsEntity doctorConsultingentites : doctorConsultingEntity)
		{
			DoctorConsultingsModel model = BeanUtils.instantiateClass(DoctorConsultingsModel.class);
			BeanUtils.copyProperties(doctorConsultingentites, model);
			doctorConsultingModels.add(model);	
		}
			return doctorConsultingModels;
	}
}
