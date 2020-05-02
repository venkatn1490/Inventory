package com.medrep.app.entity;
//Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
* TArea generated by hbm2java
*/
@Entity
@Table(name = "P_HEALTHTIPS_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = "ID") )
public class HealthTipCategoryEntity implements java.io.Serializable {

	private Integer categoryId;
	private String categoryName;
	private String categoryUrl;
	private Integer userId;
	private Date createdOn;
	private String sourceName;
	private String subCategory;

	/**
	 * @return the categoryId
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	public Integer getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the categoryName
	 */
	@Column(name = "CATEGORY_NAME")
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the categoryUrl
	 */
	@Column(name = "CATEGORY_URL")
	public String getCategoryUrl() {
		return categoryUrl;
	}
	/**
	 * @param categoryUrl the categoryUrl to set
	 */
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
	/**
	 * @return the userId
	 */
	@Column(name = "CREATED_BY")
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the createdOn
	 */
	@Column(name = "CREATED_ON")
	public Date getCreatedOn() {
		return createdOn;
	}
	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="SOURCE_NAME")
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	@Column(name="SUB_CATEGORY")
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}



}
