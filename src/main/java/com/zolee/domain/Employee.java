package com.zolee.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Employee {

	@GeneratedValue
	@Id
	private Long id;
	private String userName;
	private String realName;
	private String password;
	private String right;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "employee")
	private List<ProductionHistory> productionHistories;
	
	public Employee() {
	}

	public Employee(String userName, String realName, String password, String right) {
		this.userName = userName;
		this.realName = realName;
		this.password = password;
		this.right = right;
	}

	public Employee(Long id, String userName, String realName, String password, String right) {
		this.id = id;
		this.userName = userName;
		this.realName = realName;
		this.password = password;
		this.right = right;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public List<ProductionHistory> getProductionHistories() {
		return productionHistories;
	}

	public void setProductionHistories(List<ProductionHistory> productionHistories) {
		this.productionHistories = productionHistories;
	}

	@Override
	public String toString() {
		return "" + userName;
	}

	
}
