package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.dao.CompanyDAO;
import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.LocationDAO;
import com.medrep.app.dao.PharmaRepDAO;
import com.medrep.app.dao.PharmaTherapeuticsDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.entity.CompanyEntity;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.LocationEntity;
import com.medrep.app.entity.PharmaRepEntity;
import com.medrep.app.entity.PharmaTherapeuticsEntity;
import com.medrep.app.entity.PharmaTherapeuticsIdEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.model.Company;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.model.Location;
import com.medrep.app.model.TherapeuticArea;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.Util;

@Service("companyService")
@Transactional
public class CompanyService {

	@Autowired
	CompanyDAO companyDAO;
	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;
	@Autowired
	LocationDAO locationDAO;
	@Autowired
	DisplayPictureDAO displayPictureDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	PharmaTherapeuticsDAO pharmaTherapeuticsDAO;
	@Autowired
	PharmaRepDAO pharmaRepDAO;


	private static final Log log = LogFactory.getLog(CompanyService.class);


	public List<Company> fetchAllPharmaCompanies()
	{
			List<Company> companies = new ArrayList<Company>();
			Set<TherapeuticAreaEntity> therapeuticAreaEntities = new HashSet<TherapeuticAreaEntity>();
			List<CompanyEntity> companyEntities = companyDAO.findAllActive();
			for(CompanyEntity  companyEntity : companyEntities)
			{
				Company company = new Company();
				if(companyEntity.getDpId() != null)
				{
					DisplayPictureEntity displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,companyEntity.getDpId());
					if(displayPictureEntity != null)
					{
						DisplayPicture displayPicture = new DisplayPicture();
						displayPicture.setDpId(displayPictureEntity.getDpId());
						displayPicture.setdPicture(displayPictureEntity.getImageUrl());
						company.setDisplayPicture(displayPicture);
					}

				}
				if(companyEntity.getLocationId() != null)
				{
					LocationEntity locationEntity = locationDAO.findById(LocationEntity.class,companyEntity.getLocationId());
					if(locationEntity != null)
					{
						Location location = new Location();
						location.setLocationId(locationEntity.getLocationId());
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setState(locationEntity.getState());
						location.setZipcode(locationEntity.getZipcode());
						company.setLocation(location);
					}
				}
				Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();
				company.setCompanyDesc(companyEntity.getCompanyDesc());
				company.setCompanyId(companyEntity.getCompanyId());
				company.setCompanyName(companyEntity.getCompanyName());
				company.setContactNo(companyEntity.getContactNo());
				company.setContactName(companyEntity.getContactName());
				company.setCompanyUrl(companyEntity.getCompanyUrl());
				company.setStatus(companyEntity.getStatus());
				therapeuticAreaEntities=pharmaTherapeuticsDAO.findTherapeuticAreasByCompanyId(companyEntity.getCompanyId());
				if(therapeuticAreaEntities !=null)
				{
					for(TherapeuticAreaEntity  therapeuticAreaEntity : therapeuticAreaEntities)
					{
						TherapeuticArea therapeuticArea = new TherapeuticArea();
						therapeuticArea.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
						therapeuticArea.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						therapeuticArea.setTherapeuticDesc(therapeuticAreaEntity.getTherapeuticDesc());
						therapeuticAreas.add(therapeuticArea);
					}
					company.setTherapeuticAreas(therapeuticAreas);
				}
				companies.add(company);
			}

			return companies;
		}

		public List<Company> fetchAllPharmaCompaniesWithDeleted()
		{
			List<Company> companies = new ArrayList<Company>();
			Set<TherapeuticAreaEntity> therapeuticAreaEntities = new HashSet<TherapeuticAreaEntity>();
			List<CompanyEntity> companyEntities = companyDAO.findAll();
			for(CompanyEntity  companyEntity : companyEntities)
			{
				Company company = new Company();
				if(companyEntity.getDpId() != null)
				{
					DisplayPictureEntity displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,companyEntity.getDpId());
					if(displayPictureEntity != null)
					{
						DisplayPicture displayPicture = new DisplayPicture();
						displayPicture.setDpId(displayPictureEntity.getDpId());
						displayPicture.setdPicture(displayPictureEntity.getImageUrl());
						company.setDisplayPicture(displayPicture);
					}

				}
				if(companyEntity.getLocationId() != null)
				{
					LocationEntity locationEntity = locationDAO.findById(LocationEntity.class,companyEntity.getLocationId());
					if(locationEntity != null)
					{
						Location location = new Location();
						location.setLocationId(locationEntity.getLocationId());
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setState(locationEntity.getState());
						location.setZipcode(locationEntity.getZipcode());
						company.setLocation(location);
					}
				}
				Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();
				company.setCompanyDesc(companyEntity.getCompanyDesc());
				company.setCompanyId(companyEntity.getCompanyId());
				company.setCompanyName(companyEntity.getCompanyName());
				company.setContactNo(companyEntity.getContactNo());
				company.setContactName(companyEntity.getContactName());
				company.setStatus(companyEntity.getStatus());
				company.setCompanyUrl(companyEntity.getCompanyUrl());
				therapeuticAreaEntities=pharmaTherapeuticsDAO.findTherapeuticAreasByCompanyId(companyEntity.getCompanyId());
				if(therapeuticAreaEntities !=null)
				{
					for(TherapeuticAreaEntity  therapeuticAreaEntity : therapeuticAreaEntities)
					{
						TherapeuticArea therapeuticArea = new TherapeuticArea();
						therapeuticArea.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
						therapeuticArea.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						therapeuticArea.setTherapeuticDesc(therapeuticAreaEntity.getTherapeuticDesc());
						therapeuticAreas.add(therapeuticArea);
					}
					company.setTherapeuticAreas(therapeuticAreas);
				}
				companies.add(company);
			}

			return companies;
		}

		public void alterPharmaStatusByCompanyId(Integer companyId, String status)
		{

				CompanyEntity companyEntity  = companyDAO.findById(CompanyEntity.class,companyId);
				companyEntity.setStatus(status);
				companyDAO.merge(companyEntity);
		}

		public List<TherapeuticArea> fetchAllTherapeuticAreas()
		{
			List<TherapeuticArea> therapeuticAreas = new ArrayList<TherapeuticArea>();
			List<TherapeuticAreaEntity> therapeuticAreaEntities = therapeuticAreaDAO.findAll();
			for(TherapeuticAreaEntity  therapeuticAreaEntity : therapeuticAreaEntities)
			{
				TherapeuticArea therapeuticArea = new TherapeuticArea();
				therapeuticArea.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
				therapeuticArea.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
				therapeuticArea.setTherapeuticDesc(therapeuticAreaEntity.getTherapeuticDesc());
				therapeuticAreas.add(therapeuticArea);
			}
			return therapeuticAreas;
		}

		public Company fetchPharmaCompanyById(String Id)
		{
			Set<TherapeuticAreaEntity> therapeuticAreaEntities = new HashSet<TherapeuticAreaEntity>();
			CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,Integer.parseInt(Id));
			Company company = null;
			if(companyEntity != null)
			{
				company = new Company();
				if(companyEntity.getDpId() != null)
				{
					DisplayPictureEntity displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,companyEntity.getDpId());
					if(displayPictureEntity != null)
					{
						DisplayPicture displayPicture = new DisplayPicture();
						displayPicture.setDpId(displayPictureEntity.getDpId());
						displayPicture.setdPicture(displayPictureEntity.getImageUrl());
						company.setDisplayPicture(displayPicture);
					}
				}
				if(companyEntity.getLocationId() != null)
				{
					LocationEntity locationEntity = locationDAO.findById(LocationEntity.class,companyEntity.getLocationId());
					if(locationEntity != null)
					{
						Location location = new Location();
						location.setLocationId(locationEntity.getLocationId());
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setState(locationEntity.getState());
						location.setZipcode(locationEntity.getZipcode());
					company.setLocation(location);
					}
				}
				Set<TherapeuticArea> therapeuticAreas = new HashSet<TherapeuticArea>();
				company.setCompanyDesc(companyEntity.getCompanyDesc());
				company.setCompanyId(companyEntity.getCompanyId());
				company.setCompanyName(companyEntity.getCompanyName());
				company.setContactNo(companyEntity.getContactNo());
				company.setContactName(companyEntity.getContactName());
				company.setStatus(companyEntity.getStatus());
				company.setCompanyUrl(companyEntity.getCompanyUrl());
				therapeuticAreaEntities=pharmaTherapeuticsDAO.findTherapeuticAreasByCompanyId(companyEntity.getCompanyId());
				if(therapeuticAreaEntities!=null)
				{
					for(TherapeuticAreaEntity  therapeuticAreaEntity : therapeuticAreaEntities)
					{
						TherapeuticArea therapeuticArea = new TherapeuticArea();
						therapeuticArea.setTherapeuticId(therapeuticAreaEntity.getTherapeuticId());
						therapeuticArea.setTherapeuticName(therapeuticAreaEntity.getTherapeuticName());
						therapeuticArea.setTherapeuticDesc(therapeuticAreaEntity.getTherapeuticDesc());
						therapeuticAreas.add(therapeuticArea);
					}
					company.setTherapeuticAreas(therapeuticAreas);
				}
			}
			return company;
		}


		public void updatePharmaCompany(Model model) throws IOException
		{
			Company company=(Company) model.asMap().get("company");
			if(company.getCompanyId()!=null)
			{
			    CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class,company.getCompanyId());
			    if(company.getCompanyId()!=null)
			    {
			    	LocationEntity locationEntity = locationDAO.findById(LocationEntity.class,companyEntity.getLocationId());
			    	if(company.getLocation()!=null)
			    	{
						Location location = company.getLocation();
				    	locationEntity.setAddress1(location.getAddress1());
						locationEntity.setAddress2(location.getAddress2());
						locationEntity.setCity(location.getCity());
						locationEntity.setCountry(location.getCountry());
						locationEntity.setState(location.getState());
						locationEntity.setZipcode(location.getZipcode());
						locationEntity.setType(null);
			    	}
					locationDAO.persist(locationEntity);
					companyEntity.setLocationId(locationEntity.getLocationId());
			    }

			    DisplayPictureEntity displayPictureEntity=new DisplayPictureEntity();
			    MultipartFile companyLogo=(MultipartFile) model.asMap().get("companyLogo");
			    if(companyLogo!=null){
						String _companyLogo = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/companies/";
						_companyLogo += FileUtil.copyImage(companyLogo,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"companies");
						if(companyEntity.getDpId()!=null){
							 displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,companyEntity.getDpId());
							 String fileName = displayPictureEntity.getImageUrl()
										.substring(displayPictureEntity.getImageUrl().lastIndexOf("/"));
								fileName = MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"companies"+ File.separator + fileName;
								FileUtil.delete(fileName);
						}
						 displayPictureEntity.setImageUrl(_companyLogo);
						 companyEntity.setDpId(displayPictureEntity.getDpId());
			    }

				companyEntity.setCompanyDesc(company.getCompanyDesc());
				companyEntity.setCompanyName(company.getCompanyName());
				companyEntity.setContactNo(company.getContactNo());
				companyEntity.setContactName(company.getContactName());
				companyEntity.setStatus(company.getStatus());
				companyEntity.setCompanyUrl(company.getCompanyUrl());
				companyDAO.persist(companyEntity);
				if(companyEntity.getCompanyId()!=null)
					pharmaTherapeuticsDAO.removeByCompanyId(companyEntity.getCompanyId());
				for(TherapeuticArea therapeuticArea : company.getTherapeuticAreas())
				{
					PharmaTherapeuticsIdEntity pharmaTherapeuticsIdEntity = new PharmaTherapeuticsIdEntity();
					pharmaTherapeuticsIdEntity.setCompanyId(companyEntity.getCompanyId());
					pharmaTherapeuticsIdEntity.setTherapeuticId(therapeuticArea.getTherapeuticId());
					PharmaTherapeuticsEntity pharmaTherapeuticsEntity = new PharmaTherapeuticsEntity();
					pharmaTherapeuticsEntity.setId(pharmaTherapeuticsIdEntity);
					pharmaTherapeuticsDAO.persist(pharmaTherapeuticsEntity);
				}
			}

		}

		public void createPharmaCompany(Model model) throws IOException
		{
				Company company=(Company) model.asMap().get("company");
			    CompanyEntity companyEntity = new CompanyEntity();
				LocationEntity locationEntity = new LocationEntity();
				DisplayPictureEntity displayPictureEntity = new DisplayPictureEntity();
				Location location = company.getLocation();
				DisplayPicture displayPicture = company.getDisplayPicture();

				companyEntity.setCompanyDesc(company.getCompanyDesc());
				companyEntity.setCompanyName(company.getCompanyName());
				companyEntity.setContactNo(company.getContactNo());
				companyEntity.setContactName(company.getContactName());

				MultipartFile displayPic=(MultipartFile) model.asMap().get("companyLogo");
				if (displayPic != null) {
					String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/companies/";
					_displayPic += FileUtil.copyImage(displayPic,MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "displaypictures"+File.separator+"companies");
					displayPictureEntity.setImageUrl(_displayPic);
				}


				displayPictureDAO.persist(displayPictureEntity);
				companyEntity.setDpId(displayPictureEntity.getDpId());

				locationEntity.setAddress1(location.getAddress1());
				locationEntity.setAddress2(location.getAddress2());
				locationEntity.setCity(location.getCity());
				locationEntity.setCountry(location.getCountry());
				locationEntity.setLocationId(location.getLocationId());
				locationEntity.setState(location.getState());
				locationEntity.setZipcode(location.getZipcode());
				locationEntity.setType(null);
				locationDAO.persist(locationEntity);
				companyEntity.setCompanyUrl(company.getCompanyUrl());
				companyEntity.setLocationId(locationEntity.getLocationId());
				companyEntity.setStatus(company.getStatus());
				companyDAO.persist(companyEntity);
				for(TherapeuticArea therapeuticArea : company.getTherapeuticAreas())
				{
					PharmaTherapeuticsIdEntity pharmaTherapeuticsIdEntity = new PharmaTherapeuticsIdEntity();
					pharmaTherapeuticsIdEntity.setCompanyId(companyEntity.getCompanyId());
					pharmaTherapeuticsIdEntity.setTherapeuticId(therapeuticArea.getTherapeuticId());
					PharmaTherapeuticsEntity pharmaTherapeuticsEntity = new PharmaTherapeuticsEntity();
					pharmaTherapeuticsEntity.setId(pharmaTherapeuticsIdEntity);
					pharmaTherapeuticsDAO.persist(pharmaTherapeuticsEntity);
				}

		}

	public Company getCompanyDetail(Integer companyId){
		Company companyInstance=null;
		try
		{
			CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, companyId);

				if(companyEntity != null)
				{
					log.info("CompanyEntitys is not null");

					companyInstance = new Company();
					Location location = new Location();
					LocationEntity locationEntity = locationDAO.findById(LocationEntity.class,companyEntity.getLocationId());

					if(locationEntity!=null){
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setLocationId(locationEntity.getLocationId());
						location.setState(locationEntity.getState());
						location.setType(Util.getLocationType(locationEntity.getType()));
						location.setZipcode(locationEntity.getZipcode());
					}
					DisplayPicture displayPicture = new DisplayPicture();
					DisplayPictureEntity displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,companyEntity.getDpId());
					if(displayPictureEntity!=null)
					{
						displayPicture.setdPicture(displayPictureEntity.getImageUrl());
						displayPicture.setDpId(displayPictureEntity.getDpId());
					}
					companyInstance.setCompanyDesc(companyEntity.getCompanyDesc());
					companyInstance.setCompanyId(companyEntity.getCompanyId());
					companyInstance.setCompanyName(companyEntity.getCompanyName());
					companyInstance.setContactName(companyEntity.getContactName());
					companyInstance.setContactNo(companyEntity.getContactNo());
					companyInstance.setLocation(location);
					companyInstance.setDisplayPicture(displayPicture);
					companyInstance.setStatus(companyEntity.getStatus());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return companyInstance;

	}


	public Company getCompanyDetailByRepId(String loginId){

		Company companyInstance = null;
		try
		{

			PharmaRepEntity repEntity = pharmaRepDAO.findByLoginId(loginId);
			if(repEntity != null && repEntity.getRepId() != null && repEntity.getCompanyId() != null)
			{
				CompanyEntity companyEntity = companyDAO.findById(CompanyEntity.class, repEntity.getCompanyId());

				if(companyEntity != null)
				{

					companyInstance = new Company();
					Location location = new Location();
					LocationEntity locationEntity = locationDAO.findById(LocationEntity.class,companyEntity.getLocationId());

					if(locationEntity!=null){
						location.setAddress1(locationEntity.getAddress1());
						location.setAddress2(locationEntity.getAddress2());
						location.setCity(locationEntity.getCity());
						location.setCountry(locationEntity.getCountry());
						location.setLocationId(locationEntity.getLocationId());
						location.setState(locationEntity.getState());
						location.setType(Util.getLocationType(locationEntity.getType()));
						location.setZipcode(locationEntity.getZipcode());
					}
					DisplayPicture displayPicture = new DisplayPicture();
					DisplayPictureEntity displayPictureEntity = displayPictureDAO.findById(DisplayPictureEntity.class,companyEntity.getDpId());
					if(displayPictureEntity!=null)
					{
						displayPicture.setdPicture(displayPictureEntity.getImageUrl());
						displayPicture.setDpId(displayPictureEntity.getDpId());
					}
					companyInstance.setCompanyDesc(companyEntity.getCompanyDesc());
					companyInstance.setCompanyId(companyEntity.getCompanyId());
					companyInstance.setCompanyName(companyEntity.getCompanyName());
					companyInstance.setContactName(companyEntity.getContactName());
					companyInstance.setContactNo(companyEntity.getContactNo());
					companyInstance.setLocation(location);
					companyInstance.setDisplayPicture(displayPicture);
					companyInstance.setStatus(companyEntity.getStatus());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return companyInstance;

	}


}
