package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.dao.HealthTipDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.entity.HealthTipCategoryEntity;
import com.medrep.app.entity.HealthTipEntity;
import com.medrep.app.entity.HealthTipSourceEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.TransformCategoryEntity;
import com.medrep.app.entity.TransformSourceEntity;
import com.medrep.app.model.HealthTipCategory;
import com.medrep.app.model.HealthTipModel;
import com.medrep.app.model.HealthTipSource;
import com.medrep.app.model.NewsSource;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.Util;

@Service("healthtipService")
@Transactional
public class HealthTipService {

	private static final Log LOG = LogFactory.getLog(TransformService.class);

	@Autowired
	HealthTipDAO healthtipDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	NewsService newsService;
	
	
	public List<HealthTipModel> getAllHealthTipById(Integer id, String category, Long timestamp) {
		List<HealthTipModel> healthtipModels = new ArrayList<HealthTipModel>();
		try {
			List<HealthTipEntity> healthtipEntity=new ArrayList<HealthTipEntity>();

			if(!Util.isEmpty(id))
			{	List<HealthTipEntity> healthtipEntities = timestamp==null?healthtipDAO.findByCategoryId(id):healthtipDAO.findByCategoryId(id,new Date(timestamp));
				if(!Util.isEmpty(healthtipEntities))
					healthtipEntity.addAll(healthtipEntities);
			}

			if(!Util.isEmpty(healthtipEntity)){

				Collections.sort(healthtipEntity,new Comparator<HealthTipEntity>() {
					@Override
					public int compare(HealthTipEntity o1, HealthTipEntity o2) {
						return (o1.getCreatedOn()!=null && o2.getCreatedOn()!=null)?(o2.getCreatedOn().compareTo(o1.getCreatedOn())):-1;
					}
				});
				for (HealthTipEntity entity : healthtipEntity) {
					HealthTipModel healthtipModel = new HealthTipModel();
					BeanUtils.copyProperties(entity, healthtipModel,"createdOn");

					if(Util.isZeroOrNull(entity.getCategoryId())){
						HealthTipCategoryEntity cat=healthtipDAO.getCategoryById(entity.getSourceId());
						healthtipModel.setSourceName(cat.getSourceName());
						healthtipModel.setSubCategory(cat.getSubCategory());
					}else if(!Util.isZeroOrNull(entity.getSourceId())){//when categoryId is not null and sourceId is also not null
						HealthTipSourceEntity  sourceEntity=healthtipDAO.getHealthTipSourceById(entity.getSourceId());
						if(!Util.isEmpty(sourceEntity)){
							healthtipModel.setSourceName(sourceEntity.getHealthtipSourceName());
						}
						HealthTipCategoryEntity cat=healthtipDAO.getCategoryById(entity.getCategoryId());
						if(!Util.isEmpty(cat))
							healthtipModel.setSubCategory(cat.getSubCategory());

					}

					if(!Util.isZeroOrNull(entity.getTherapeuticId())){
						TherapeuticAreaEntity therapeutic=therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, entity.getTherapeuticId());
						if(!Util.isEmpty(therapeutic))
							healthtipModel.setSubCategory(therapeutic.getTherapeuticName());;
					}
					healthtipModel.setCreatedOn(DateConvertor.convertDateToString(entity.getCreatedOn(),"yyyy-MM-dd'T'HH:mm:ss"));
					if(entity.getVideoUrl()!=null)
						healthtipModel.setContentType(3);
					else if(entity.getInnerImgUrl()!=null && entity.getCoverImgUrl()!=null)
						healthtipModel.setContentType(1);
					else
						healthtipModel.setContentType(4);


					healthtipModels.add(healthtipModel);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return healthtipModels;
	}

	
	
	public List<HealthTipModel> getAllHealthTips() throws MedrepException {
		List<HealthTipEntity> list = healthtipDAO.getHealthTipList();
		List<HealthTipModel> modelList = new ArrayList<HealthTipModel>();
		for(HealthTipEntity entity : list) {
			HealthTipModel model = BeanUtils.instantiateClass(HealthTipModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			/*HealthTipCategoryEntity healthtipCategoryEntity=healthtipDAO.getNameByHealthTipCategory(entity.getCategoryId());
			if (healthtipCategoryEntity!= null)
				model.setCategoryName(healthtipCategoryEntity.getCategoryName());*/
			/*HealthTipSourceEntity healthtipSoruceEntity=healthtipDAO.getHealthTipSourceById(entity.getSourceId());
			if (healthtipSoruceEntity!= null)
				model.setSourceName(healthtipSoruceEntity.getHealthtipSourceName());*/
			model.setCreatedOn(entity.getCreatedOn().toString());
			setNames(model);
			modelList.add(model);
		}
		return modelList;
	}


	public List<Integer> getHealthTipCategoryId(String categoryName) {
		List<Integer> idList = new ArrayList<Integer>();
		try {
			List<HealthTipCategoryEntity> categoryEntity = healthtipDAO.getIdByHealthTipCategory(categoryName);
			for (HealthTipCategoryEntity healthTipCategoryEntity : categoryEntity) {
				idList.add(healthTipCategoryEntity.getCategoryId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idList;
	}
	
	public String getNameByHealthTipCategory(Integer categoryId) {
		String idCategory = null;
		try {
			HealthTipCategoryEntity categoryEntity = healthtipDAO.getNameByHealthTipCategory(categoryId);
			idCategory=categoryEntity.getCategoryName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idCategory;
	}
	public void uploadHealthTip(HealthTipModel healthTip) throws MedrepException, IOException {
		HealthTipEntity entity = BeanUtils.instantiateClass(HealthTipEntity.class);
		BeanUtils.copyProperties(healthTip, entity, "createdOn");
		entity.setCreatedOn(Calendar.getInstance().getTime());
		healthtipDAO.persist(entity);

		if(!healthTip.getCoverImgFile().isEmpty()) {
			entity.setCoverImgUrl(getCategoryFileUrl(healthTip.getCoverImgFile(),String.valueOf(entity.getHealthtipId())));
		}

		if(!healthTip.getInnerImgFile().isEmpty()) {
			entity.setInnerImgUrl(getCategoryFileUrl(healthTip.getInnerImgFile(),String.valueOf(entity.getHealthtipId())));
		}

		if(!healthTip.getVideoFile().isEmpty()) {
			entity.setVideoUrl(getCategoryFileUrl(healthTip.getVideoFile(),String.valueOf(entity.getHealthtipId())));
		}
		healthtipDAO.merge(entity);
	}


	public HealthTipModel getHealthTipById(Integer id){
		try {
			HealthTipEntity entity = healthtipDAO.findById(HealthTipEntity.class,id);
			HealthTipModel model = BeanUtils.instantiateClass(HealthTipModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			setNames(model);
			return model;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new HealthTipModel();
	}

	public List<HealthTipCategory> getAllHealthTipCategories() {
		List<HealthTipCategory> list = new ArrayList<HealthTipCategory>();
		for(String data : healthtipDAO.getDistinctHealthTipCategories())
		{
			HealthTipCategory category = new HealthTipCategory();
			Integer id=healthtipDAO.getCategoryId(data);
		   if(!Util.isZeroOrNull(id))
			category.setCategoryId(id);
			category.setCategoryName(data);
//			category.setSourceName(data.getSourceName());
			list.add(category);   
		}
		return list;
	}

	public void createHealthTipCategory(HealthTipCategory model) {
		HealthTipCategoryEntity entity = BeanUtils.instantiateClass(HealthTipCategoryEntity.class);
		BeanUtils.copyProperties(model, entity);
		healthtipDAO.createHealthTipCategory(entity);

	}

	public void deleteHealthTip(String healthtipId) {
		HealthTipEntity entity = healthtipDAO.findById(HealthTipEntity.class, Integer.valueOf(healthtipId));
		healthtipDAO.remove(entity);
	}

	private String getCategoryFileUrl(MultipartFile mfile,String categoryId) throws IOException {
		String healthtipUrl="";
		if(mfile!=null){
			healthtipUrl = MedRepProperty.getInstance().getProperties("static.resources.url") + "static/healthtips/"+categoryId+"/";
			healthtipUrl += FileUtil.copyBytesToFile(mfile.getBytes(),MedRepProperty.getInstance().getProperties("medrep.healthtip.basepath")+File.separator+categoryId,mfile.getOriginalFilename());
			
		}
		return  healthtipUrl;
	}

	private void setNames(HealthTipModel model) {
		try {
			TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,model.getTherapeuticId());
			model.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
			/*for(NewsSource source : newsService.getAllNewsSources()) {
				if(source.getSourceId() == model.getSourceId()) {
					model.setSourceName(source.getSourceName());
					break;
				}
			}*/
			for(HealthTipCategory category : getAllHealthTipCategories()) {
				if(category.getCategoryId() == model.getCategoryId()) {
					model.setCategoryName(category.getCategoryName());
					break;
				}
			}
			for(HealthTipSource source : getAllHealthTipSources()) {
				if(source.getHealthtipId() == model.getSourceId()) {
					model.setSourceName(source.getHealthtipSourceName());
					break;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public List<HealthTipSource> getAllHealthTipSources() {
		LOG.info("In getAllHealthTipSources " );
		List<HealthTipSource> list = new ArrayList<HealthTipSource>();
		for(HealthTipSourceEntity data : healthtipDAO.getAllHealthTipSources())
		{
			HealthTipSource source = new HealthTipSource();
			source.setHealthtipId(data.getHealthtipid());
			source.setHealthtipSourceName(data.getHealthtipSourceName());
			list.add(source);
		}
		LOG.info("In getAllTransformSources  : size "+ list.size());
		return list;
	}

	public void createHealthTipSource(HealthTipSource model) {
		LOG.info("In createTransformSource ");
		HealthTipSourceEntity entity = BeanUtils.instantiateClass(HealthTipSourceEntity.class);
		BeanUtils.copyProperties(model, entity);
		healthtipDAO.createHealthTipSource(entity);

	}
}