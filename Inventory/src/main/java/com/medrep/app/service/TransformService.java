package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.TransformDAO;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.TransformCategoryEntity;
import com.medrep.app.entity.TransformEntity;
import com.medrep.app.entity.TransformSourceEntity;
import com.medrep.app.model.NewsSource;
import com.medrep.app.model.TransformCategory;
import com.medrep.app.model.TransformModel;
import com.medrep.app.model.TransformSource;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.Util;

@Service("transformService")
@Transactional
public class TransformService {

	private static final Log LOG = LogFactory.getLog(TransformService.class);

	@Autowired
	TransformDAO transformDAO;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	@Autowired
	NewsService newsService;

	public List<TransformModel> getAllTransforms() throws MedrepException {
		List<TransformEntity> list = transformDAO.getTransformList();
		List<TransformModel> modelList = new ArrayList<TransformModel>();
		for(TransformEntity entity : list) {
			TransformModel model = BeanUtils.instantiateClass(TransformModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			model.setCreatedOn(entity.getCreatedOn().toString());
			setNames(model);
			modelList.add(model);
		}
		return modelList;
	}


	public void uploadTransform(TransformModel transform) throws MedrepException, IOException {
		TransformEntity entity = BeanUtils.instantiateClass(TransformEntity.class);
		BeanUtils.copyProperties(transform, entity, "createdOn");
		entity.setCreatedOn(Calendar.getInstance().getTime());
		transformDAO.persist(entity);

		//if(Util.isZeroOrNull(entity.getSourceId()))
//		entity.setSourceId(transform.getCategoryId());

		if(!transform.getCoverImgFile().isEmpty()) {
			entity.setCoverImgUrl(getCategoryFileUrl(transform.getCoverImgFile(),String.valueOf(entity.getTransformId())));
		}

		if(!transform.getInnerImgFile().isEmpty()) {
			entity.setInnerImgUrl(getCategoryFileUrl(transform.getInnerImgFile(),String.valueOf(entity.getTransformId())));
		}

		if(!transform.getVideoFile().isEmpty()) {
			entity.setVideoUrl(getCategoryFileUrl(transform.getVideoFile(),String.valueOf(entity.getTransformId())));
		}
		transformDAO.merge(entity);
	}


	public TransformModel getTransformById(Integer id){
		try {
			TransformEntity entity = transformDAO.findById(TransformEntity.class,id);
			TransformModel model = BeanUtils.instantiateClass(TransformModel.class);
			BeanUtils.copyProperties(entity, model, "createdOn");
			setNames(model);
			return model;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new TransformModel();
	}

	public List<TransformCategory> getAllTransformCategories() {
		List<TransformCategory> list = new ArrayList<TransformCategory>();
		for(String data : transformDAO.getDistinctTransformCategories())
		{
			TransformCategory category = new TransformCategory();
			Integer id=transformDAO.getCategoryId(data);
		   if(!Util.isZeroOrNull(id))
			category.setCategoryId(id);
			category.setCategoryName(data);
//			category.setSourceName(data.getSourceName());
			list.add(category);
		}
		return list;
	}

	public void createTransformCategory(TransformCategory model) {
		TransformCategoryEntity entity = BeanUtils.instantiateClass(TransformCategoryEntity.class);
		BeanUtils.copyProperties(model, entity);
		transformDAO.createTransformCategory(entity);

	}

	public void deleteTransform(String transformId) {
		TransformEntity entity = transformDAO.findById(TransformEntity.class, Integer.valueOf(transformId));
		transformDAO.remove(entity);
	}

	private String getCategoryFileUrl(MultipartFile mfile,String categoryId) throws IOException {
		String transformUrl="";
		if(mfile!=null){
			 transformUrl = MedRepProperty.getInstance().getProperties("static.resources.url") + "static/transform/"+categoryId+"/";
			 transformUrl += FileUtil.copyBytesToFile(mfile.getBytes(),MedRepProperty.getInstance().getProperties("medrep.transform.basepath")+File.separator+categoryId,mfile.getOriginalFilename());
		}
		return  transformUrl;
	}

	private void setNames(TransformModel model) {
		try {
			if(model.getTherapeuticId() != null) {
				TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,model.getTherapeuticId());
				model.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");

			}
			for(NewsSource source : newsService.getAllNewsSources()) {
				if(source.getSourceId() == model.getSourceId()) {
					model.setSourceName(source.getSourceName());
					break;
				}
			}
			for(TransformCategory category : getAllTransformCategories()) {
				if(category.getCategoryId() == model.getCategoryId()) {
					model.setCategoryName(category.getSourceName());
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public List<TransformSource> getAllTransformSources() {
		LOG.info("In getAllTransformSources " );
		List<TransformSource> list = new ArrayList<TransformSource>();
		for(TransformSourceEntity data : transformDAO.getAllTransformSources())
		{
			TransformSource source = new TransformSource();
			source.setTransformId(data.getTransformId());
			source.setTransformSourceName(data.getTransformSourceName());
			list.add(source);
		}
		LOG.info("In getAllTransformSources  : size "+ list.size());
		return list;
	}

	public void createTransformSource(TransformSource model) {
		LOG.info("In createTransformSource ");
		TransformSourceEntity entity = BeanUtils.instantiateClass(TransformSourceEntity.class);
		BeanUtils.copyProperties(model, entity);
		transformDAO.createTransformSource(entity);

	}
}