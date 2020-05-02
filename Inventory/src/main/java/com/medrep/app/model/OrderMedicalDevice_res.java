package com.medrep.app.model;

import java.util.List;

import com.medrep.app.entity.MedicalDeviceEntity;
import com.medrep.app.entity.UserEntity;

public class OrderMedicalDevice_res  implements java.io.Serializable   {
	
	
	private String orderOn;

	private List<MedicalDevicesModel> medicaldevicemodel;
	public List<MedicalDevicesModel> getMedicalDevicesModel() {
		return medicaldevicemodel;
	}
	public void setMedicalDevicesModel(List<MedicalDevicesModel> medicaldevicemodel) {
		this.medicaldevicemodel = medicaldevicemodel;
	}
	
	public String getOrderOn() {
		return orderOn;
	}
	public void setOrderOn(String orderOn) {
		this.orderOn = orderOn;
	}
	
}
