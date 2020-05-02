package com.medrep.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.medrep.app.entity.AmbulancesEntity;
import com.medrep.app.entity.DiagnosticsEntity;
import com.medrep.app.entity.HospitalEntity;
@Repository
public class DiagnosticsDAO extends MedRepDAO<DiagnosticsEntity>{
	
	private static final Log LOG = LogFactory.getLog(DiagnosticsDAO.class);

	
	public List<DiagnosticsEntity> getDiagnosticsBasedOnLocatity(String locatity,String city){
		List<DiagnosticsEntity> diagnosticslist =new ArrayList<DiagnosticsEntity>();
		LOG.info("getting Device list");
		try
		{
			diagnosticslist = entityManager.createQuery("SELECT p FROM DiagnosticsEntity p  where p.locatity = :locatity and p.city = :city  ", DiagnosticsEntity.class)
					.setParameter("locatity", locatity).setParameter("city", city).getResultList();


		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return diagnosticslist;
	}
	
	public int findByDiagnosticsConnections  (String locatity,String city) {
		List result= getDiagnosticsBasedOnLocatity(locatity,city);
		return result!=null?result.size():0;
	}		
	
	public List<DiagnosticsEntity> getDiagnosticsList(){
		List<DiagnosticsEntity> diagnosticsList =new ArrayList<DiagnosticsEntity>();
		LOG.info("getting Device list");
		try
		{
			diagnosticsList = entityManager.createQuery("SELECT p FROM DiagnosticsEntity p   order by p.createdOn desc  ", DiagnosticsEntity.class)
					.setFirstResult(0).setMaxResults(500).getResultList();

		}catch(RuntimeException ex)
		{
			LOG.error("get failed", ex);
		}
		return diagnosticsList;
	}
}
