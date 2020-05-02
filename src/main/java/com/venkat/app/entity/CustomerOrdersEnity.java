package com.venkat.app.entity;
// Generated Aug 2, 2015 5:40:52 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.criteria.CriteriaBuilder.In;

/**
 * TUser generated by hbm2java
 */
@Entity
@Table(name = "t_cutomer_orders")
public class CustomerOrdersEnity implements java.io.Serializable {

	private Integer id;
	private Integer salesmanId;
	private Integer purchaserId;
	private Integer customerId;
	private Date createdDate;
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "PURCHASER_ID")
	public Integer getPurchaserId() {
		return purchaserId;
	}
	public void setPurchaserId(Integer purchaserId) {
		this.purchaserId = purchaserId;
	}
	@Column(name = "SALESMAN_ID")
	public Integer getSalesmanId() {
		return salesmanId;
	}
	public void setSalesmanId(Integer salesmanId) {
		this.salesmanId = salesmanId;
	}
	@Column(name = "CUSTOMER_ID")
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

}
