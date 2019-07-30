package com.zolee.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductionHistory {

	@GeneratedValue
	@Id
	private Long id;
	private LocalDate actualdate;
	private LocalTime startTime;
	private LocalTime stopTime;
	private String actiontype;
	private String description;
	@ManyToOne
	private ProductionSequence productionSequence; 
	@ManyToOne
	private Employee employee;
	@ManyToOne
	private UnitSequence unitSequence;

	public ProductionHistory() {
	}

	public ProductionHistory(LocalDate actualdate, LocalTime startTime, LocalTime stopTime, String actiontype,
			String description, ProductionSequence productionSequence, Employee employee, UnitSequence unitSequence) {
		this.actualdate = actualdate;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.actiontype = actiontype;
		this.description = description;
		this.productionSequence = productionSequence;
		this.employee = employee;
		this.unitSequence = unitSequence;
	}

	public ProductionHistory(Long id, LocalDate actualdate, LocalTime startTime, LocalTime stopTime, String actiontype,
			String description, ProductionSequence productionSequence, Employee employee, UnitSequence unitSequence) {
		this.id = id;
		this.actualdate = actualdate;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.actiontype = actiontype;
		this.description = description;
		this.productionSequence = productionSequence;
		this.employee = employee;
		this.unitSequence = unitSequence;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getActualdate() {
		return actualdate;
	}

	public void setActualdate(LocalDate actualdate) {
		this.actualdate = actualdate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getStopTime() {
		return stopTime;
	}

	public void setStopTime(LocalTime stopTime) {
		this.stopTime = stopTime;
	}

	public ProductionSequence getProductionSequence() {
		return productionSequence;
	}

	public void setProductionSequence(ProductionSequence productionSequence) {
		this.productionSequence = productionSequence;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UnitSequence getUnitSequence() {
		return unitSequence;
	}

	public void setUnitSequence(UnitSequence unitSequence) {
		this.unitSequence = unitSequence;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

}
