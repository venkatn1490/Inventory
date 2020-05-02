package com.medrep.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.model.TherapeuticArea;

@Service("therapeuticArea")
@Transactional
public class TherapeuticAreaService {
	
	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;
	
	private static final Log log = LogFactory.getLog(TherapeuticAreaService.class);
			
	public TherapeuticArea findTherapeuticAreaById(Integer id){
		TherapeuticAreaEntity therapeuticAreaEntity = null;
		TherapeuticArea therapeuticArea = new TherapeuticArea();
		if(id!=null)
		{
			therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,id);
			therapeuticArea.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
			therapeuticArea.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
			therapeuticArea.setTherapeuticDesc(therapeuticAreaEntity.getTherapeuticDesc());
		}
		return therapeuticArea;
	}
	
	public List<TherapeuticArea> getAll(){
		List<TherapeuticArea> areaList = new ArrayList<TherapeuticArea>();
		List<TherapeuticAreaEntity> entities = therapeuticAreaDAO.findAll();
		for(TherapeuticAreaEntity therapeuticAreaEntity : entities) {
			TherapeuticArea therapeuticArea = new TherapeuticArea();
			therapeuticArea.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
			therapeuticArea.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
			therapeuticArea.setTherapeuticDesc(therapeuticAreaEntity.getTherapeuticDesc());
			areaList.add(therapeuticArea);
		}
	  return areaList;
	}

}
