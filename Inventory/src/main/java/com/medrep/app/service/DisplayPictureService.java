package com.medrep.app.service;

import java.io.File;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.medrep.app.dao.DisplayPictureDAO;
import com.medrep.app.dao.UserDAO;
import com.medrep.app.entity.DisplayPictureEntity;
import com.medrep.app.entity.UserEntity;
import com.medrep.app.model.DisplayPicture;
import com.medrep.app.util.FileUtil;
import com.medrep.app.util.MedRepProperty;
import com.medrep.app.util.MedrepException;

@Service("dpService")
@Transactional
public class DisplayPictureService {

	@Autowired
	DisplayPictureDAO displayPictureDAO;

	@Autowired
	UserDAO userDAO;

	private static final Log log = LogFactory.getLog(DisplayPictureService.class);

	public boolean uploadDisplayPicture(Model model) throws MedrepException
	{
		boolean flag = false;
		try
		{
			DisplayPicture dp=(DisplayPicture) model.asMap().get("displayPicture");


			if(dp==null || dp.getLoginId()==null || "null".equalsIgnoreCase(dp.getLoginId().trim()) || "".equalsIgnoreCase(dp.getLoginId().trim())){
				flag=false;
			}else{
				UserEntity userEntity = userDAO.findByEmailId(dp.getLoginId().trim());

				if(userEntity != null && userEntity.getUserId() != null)
				{
					DisplayPictureEntity dpEntity = new DisplayPictureEntity();
					if(dp.getImgData()!=null){
						String _displayPic = MedRepProperty.getInstance().getProperties("medrep.home") + "static/images/displaypictures/";
						_displayPic += FileUtil.copyBinaryData(dp.getImgData().getBytes(),MedRepProperty.getInstance().getProperties("images.loc")+File.separator+"displaypictures",dp.getFileName());
						dpEntity.setImageUrl(_displayPic);
						dpEntity=new DisplayPictureEntity();
						dpEntity.setImageUrl(_displayPic);
						model.addAttribute("url",_displayPic);
				}
					displayPictureDAO.persist(dpEntity);
					userEntity.setDisplayPicture(dpEntity);
					userDAO.merge(userEntity);
					flag = true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new MedrepException(e);
		}
		return flag;
	}

	public DisplayPicture getDisplayPicture(DisplayPicture dp) throws MedrepException
	{
		try
		{
			UserEntity userEntity = userDAO.findByEmailId(dp.getLoginId());
			DisplayPictureEntity dpEntity = userEntity.getDisplayPicture();
			dp.setdPicture(dpEntity.getImageUrl());

		}
		catch(Exception e)
		{
			throw new MedrepException(e);
		}

		return dp;

	}

	public DisplayPicture getUserDisplayPicture(Integer userId)
	{
		DisplayPicture displayPicture = null;
		UserEntity userEntity = userDAO.findBySecurityId(userId);
		if(userEntity != null)
		{
			DisplayPictureEntity displayPictureEntity = userEntity.getDisplayPicture();
			if(displayPictureEntity != null)
			{
				displayPicture = new DisplayPicture();
				displayPicture.setdPicture(displayPictureEntity.getImageUrl());
				displayPicture.setDpId(displayPictureEntity.getDpId());
			}
		}

		return displayPicture;

	}

}
