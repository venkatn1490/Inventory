package com.medrep.app.model;

public class DoctorNotificationStat {
	
	private Integer notificationId;
	private String notificationName;
	private String totalCount = "0.0";
	private String ratingAverage = "0.0";
	private String prescribeYes = "0.0";
	private String prescribeNo = "0.0";
	private String favoriteYes = "0.0";
	private String favoriteNo = "0.0";
	private String recomendYes = "0.0";
	private String recomendNo = "0.0";
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	public String getNotificationName() {
		return notificationName;
	}
	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getRatingAverage() {
		return ratingAverage;
	}
	public void setRatingAverage(String ratingAverage) {
		this.ratingAverage = ratingAverage;
	}
	public String getPrescribeYes() {
		return prescribeYes;
	}
	public void setPrescribeYes(String prescribeYes) {
		this.prescribeYes = prescribeYes;
	}
	public String getPrescribeNo() {
		return prescribeNo;
	}
	public void setPrescribeNo(String prescribeNo) {
		this.prescribeNo = prescribeNo;
	}
	public String getFavoriteYes() {
		return favoriteYes;
	}
	public void setFavoriteYes(String favoriteYes) {
		this.favoriteYes = favoriteYes;
	}
	public String getFavoriteNo() {
		return favoriteNo;
	}
	public void setFavoriteNo(String favoriteNo) {
		this.favoriteNo = favoriteNo;
	}
	public String getRecomendYes() {
		return recomendYes;
	}
	public void setRecomendYes(String recomendYes) {
		this.recomendYes = recomendYes;
	}
	public String getRecomendNo() {
		return recomendNo;
	}
	public void setRecomendNo(String recomendNo) {
		this.recomendNo = recomendNo;
	}
	
	

}
