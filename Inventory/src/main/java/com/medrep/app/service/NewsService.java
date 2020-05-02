package com.medrep.app.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.controller.rest.DoctorPlusController;
import com.medrep.app.dao.NewsDAO;
import com.medrep.app.dao.TherapeuticAreaDAO;
import com.medrep.app.dao.TransformDAO;
import com.medrep.app.dao.TransfromDAO;
import com.medrep.app.entity.NewsEntity;
import com.medrep.app.entity.NewsSourceEntity;
import com.medrep.app.entity.TherapeuticAreaEntity;
import com.medrep.app.entity.TransformCategoryEntity;
import com.medrep.app.entity.TransformEntity;
import com.medrep.app.entity.TransformSourceEntity;
import com.medrep.app.model.News;
import com.medrep.app.model.NewsSource;
import com.medrep.app.model.TransformCategory;
import com.medrep.app.model.TransformModel;
import com.medrep.app.util.DateConvertor;
import com.medrep.app.util.FileIOUtil;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;
import com.medrep.app.util.Util;

@Service("newsService")
@Transactional
public class NewsService {


	private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

	@Autowired
	NewsDAO newsDAO;

	@Autowired
	TransfromDAO transformDAO;

	@Autowired
	TransformDAO transformSourceDao;

	@Autowired
	TherapeuticAreaDAO therapeuticAreaDAO;

	public List<News> getAllNews(Long timestamp) throws MedrepException {
		List<NewsEntity> list = timestamp!=null?newsDAO.getNewsList(new Date(timestamp)):newsDAO.getNewsList();
		List<News> modelList = new ArrayList<News>();
		for(NewsEntity entity : list) {
			News news = BeanUtils.instantiateClass(News.class);
			BeanUtils.copyProperties(entity, news, "createdOn");
			news.setCreatedOn(DateConvertor.convertDateToString(entity.getCreatedOn(),"yyyy-MM-dd'T'HH:mm:ss"));
			if(entity.getVideoUrl()!=null)
				news.setContentType(3);
			else if(entity.getInnerImgUrl()!=null && entity.getCoverImgUrl()!=null)
				news.setContentType(1);
			else
				news.setContentType(4);
			setNames(news);
			modelList.add(news);
		}

		return modelList;
	}

	public void uploadNews(News news) throws MedrepException, IOException {
		NewsEntity entity = BeanUtils.instantiateClass(NewsEntity.class);
		BeanUtils.copyProperties(news, entity, "createdOn");
		entity.setCreatedOn(Calendar.getInstance().getTime());
		newsDAO.persist(entity);

		if(!news.getCoverImgFile().isEmpty()) {
			String coverImage = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/news/";
			coverImage += FileUtil.copyBytesToFile(news.getCoverImgFile().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "news",news.getCoverImgFile().getOriginalFilename());
			entity.setCoverImgUrl(coverImage);
		}

		if(!news.getInnerImgFile().isEmpty()) {
			String innerImage = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/news/";
			innerImage += FileUtil.copyBytesToFile(news.getInnerImgFile().getBytes(),MedRepProperty.getInstance().getProperties("images.loc") + File.separator + "news",news.getInnerImgFile().getOriginalFilename());
			entity.setInnerImgUrl(innerImage);
		}

		if(!news.getVideoFile().isEmpty()) {
			String videoUrl = MedRepProperty.getInstance().getProperties("static.resources.url") + "static/videos/news/";
			videoUrl += FileUtil.copyBytesToFile(news.getVideoFile().getBytes(),MedRepProperty.getInstance().getProperties("videos.loc") + File.separator + "news",news.getVideoFile().getOriginalFilename());
			entity.setVideoUrl(videoUrl);
		}
		newsDAO.merge(entity);
	}

	public News getNewsById(Integer id){
		try {
			NewsEntity entity = newsDAO.findById(NewsEntity.class,id);
			News news = BeanUtils.instantiateClass(News.class);
			BeanUtils.copyProperties(entity, news, "createdOn");
			news.setCreatedOn(entity.getCreatedOn().toString());
			setNames(news);
			return news;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new News();
	}

	public void createNewsSource(NewsSource source) {
		NewsSourceEntity entity = BeanUtils.instantiateClass(NewsSourceEntity.class);
		BeanUtils.copyProperties(source, entity);
		newsDAO.createNewsSource(entity);

	}

	public List<NewsSource> getAllNewsSources() {
		List<NewsSource> list = new ArrayList<NewsSource>();
		for(NewsSourceEntity data : newsDAO.getAllNewsSources())
		{
			NewsSource news = new NewsSource();
			news.setSourceId(data.getSourceId());
			news.setSourceName(data.getSourceName());
			news.setSourceUrl(data.getSourceUrl());
			list.add(news);
		}
		return list;
	}


	public void deleteNews(String newsId) {
		NewsEntity entity = newsDAO.findById(NewsEntity.class, Integer.valueOf(newsId));
		newsDAO.remove(entity);
	}

	private String getNewsFileUrl(MultipartFile mfile,String newsId) {
		File file = FileIOUtil.convert(mfile);
		FileIOUtil util = new FileIOUtil();
		return  util.writeNewsContent(file, newsId);
	}

	private void setNames(News news) {
		try {
			if(!Util.isZeroOrNull(news.getTherapeuticId())){
				TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,news.getTherapeuticId());
				news.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");

			}

			if(!Util.isZeroOrNull(news.getSourceId())){
				logger.info("<>sourceId"+news.getSourceId());
				NewsSourceEntity source=newsDAO.getNewsSourceById(news.getSourceId());
				if(!Util.isEmpty(source) && !Util.isEmpty(source.getSourceName())){
					logger.info("<>sourcename"+source.getSourceName());
				news.setSourceName(source.getSourceName());
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

	}


	public News getnewsSourceById(News news) {
		try {
			if(news.getTherapeuticId()!=null){
			TherapeuticAreaEntity therapeuticAreaEntity = therapeuticAreaDAO.findById(TherapeuticAreaEntity.class,news.getTherapeuticId());
			news.setTherapeuticName(therapeuticAreaEntity !=null ? therapeuticAreaEntity.getTherapeuticName() : "");
			}
			for(NewsSource source : getAllNewsSources()) {
				if(source.getSourceId() == news.getSourceId()) {
					news.setSourceName(source.getSourceName());
					break;
				}
			}


		}catch(Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public Integer deleteAllNews() {
		int result = 0;
		try {
			result = newsDAO.deleteAllNews();
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}


	public Integer deleteAllTransform() {
		int result = 0;
		try {
			result = transformDAO.deleteAllTranform();
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}


	public List<TransformCategory> getAllTransformSources() {
		List<TransformCategory> list = new ArrayList<TransformCategory>();
		for(TransformCategoryEntity data : transformDAO.getAllTransformSources())
		{
			TransformCategory news = new TransformCategory();
			news.setCategoryId(data.getCategoryId());
			news.setCategoryName(data.getSourceName());
			news.setCategoryUrl(data.getCategoryUrl());
			list.add(news);
		}
		return list;
	}

	public List<Integer> getTransformCategoryId(String categoryName) {
		List<Integer> idList = new ArrayList<Integer>();
		try {
			List<TransformCategoryEntity> categoryEntity = transformDAO.getIdByTransformCategory(categoryName);
			for (TransformCategoryEntity transformCategoryEntity : categoryEntity) {
				idList.add(transformCategoryEntity.getCategoryId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idList;
	}


	public List<TransformModel> getAllTransformsById(List<Integer> id, String category, Long timestamp) {
		List<TransformModel> transformModels = new ArrayList<TransformModel>();
		try {
			List<TransformEntity> transformEntity=new ArrayList<TransformEntity>();

			if(!Util.isEmpty(id))
			for(Integer categoryId:id){
				List<TransformEntity> transformEntities = timestamp==null?transformDAO.findByCategoryId(categoryId):transformDAO.findByCategoryId(categoryId,new Date(timestamp));
				if(!Util.isEmpty(transformEntities))
					transformEntity.addAll(transformEntities);
			}

			if(!Util.isEmpty(transformEntity)){

				Collections.sort(transformEntity,new Comparator<TransformEntity>() {
					@Override
					public int compare(TransformEntity o1, TransformEntity o2) {
						return (o1.getCreatedOn()!=null && o2.getCreatedOn()!=null)?(o2.getCreatedOn().compareTo(o1.getCreatedOn())):-1;
					}
				});
				for (TransformEntity entity : transformEntity) {
					TransformModel transformModel = new TransformModel();
					BeanUtils.copyProperties(entity, transformModel,"createdOn");

					if(Util.isZeroOrNull(entity.getCategoryId())){
						TransformCategoryEntity cat=transformDAO.getCategoryById(entity.getSourceId());
						transformModel.setSourceName(cat.getSourceName());
						transformModel.setSubCategory(cat.getSubCategory());
					}else if(!Util.isZeroOrNull(entity.getSourceId())){//when categoryId is not null and sourceId is also not null
						TransformSourceEntity sourceEntity=transformSourceDao.getTransformSourceById(entity.getSourceId());
						if(!Util.isEmpty(sourceEntity)){
							transformModel.setSourceName(sourceEntity.getTransformSourceName());
						}
						TransformCategoryEntity cat=transformDAO.getCategoryById(entity.getCategoryId());
						if(!Util.isEmpty(cat))
							transformModel.setSubCategory(cat.getSubCategory());

					}

					if(!Util.isZeroOrNull(entity.getTherapeuticId())){
						TherapeuticAreaEntity therapeutic=therapeuticAreaDAO.findById(TherapeuticAreaEntity.class, entity.getTherapeuticId());
						if(!Util.isEmpty(therapeutic))
						transformModel.setSubCategory(therapeutic.getTherapeuticName());;
					}
					transformModel.setCreatedOn(DateConvertor.convertDateToString(entity.getCreatedOn(),"yyyy-MM-dd'T'HH:mm:ss"));
//							kTransformContentTypeNone = 0,
//						    kTransformContentTypeImage,
//						    kTransformContentTypePDF,
//						    kTransformContentTypeVideo,
//						    kTransformContentTypeText
					if(entity.getVideoUrl()!=null)
						transformModel.setContentType(3);
					else if(entity.getInnerImgUrl()!=null && entity.getCoverImgUrl()!=null)
						transformModel.setContentType(1);
					else
						transformModel.setContentType(4);


					transformModels.add(transformModel);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return transformModels;
	}


	public List<String> getAllSubCategories(String category) {
		return transformDAO.getSubCategories(category);

	}
}