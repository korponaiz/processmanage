package com.zolee.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class ProductionSequence {

	@GeneratedValue	
	@Id
	private Long id;
	private Long serialnumber;
	private String status;
	private long actualHistoryId;
	@ManyToOne
	private UnitSequence unitSequence;
	@ManyToOne
	private Workstation workstation;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "productionSequence")
	private List<ProductionHistory> productionHistories;
	
	public ProductionSequence() {
	}

	public ProductionSequence(Long serialnumber, String status, long actualHistory, UnitSequence unitSequence,
			Workstation workstation) {
		this.serialnumber = serialnumber;
		this.status = status;
		this.actualHistoryId = actualHistory;
		this.unitSequence = unitSequence;
		this.workstation = workstation;
	}

	public ProductionSequence(Long id, Long serialnumber, String status, long actualHistory, UnitSequence unitSequence,
			Workstation workstation) {
		this.id = id;
		this.serialnumber = serialnumber;
		this.status = status;
		this.actualHistoryId = actualHistory;
		this.unitSequence = unitSequence;
		this.workstation = workstation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(Long serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getActualHistoryId() {
		return actualHistoryId;
	}

	public void setActualHistoryId(long actualHistoryId) {
		this.actualHistoryId = actualHistoryId;
	}

	public Workstation getWorkstation() {
		return workstation;
	}

	public void setWorkstation(Workstation workstation) {
		this.workstation = workstation;
	}

	public List<ProductionHistory> getProductionHistories() {
		return productionHistories;
	}

	public void setProductionHistories(List<ProductionHistory> productionHistories) {
		this.productionHistories = productionHistories;
	}

	public UnitSequence getUnitSequence() {
		return unitSequence;
	}

	public void setUnitSequence(UnitSequence unitSequence) {
		this.unitSequence = unitSequence;
	}

	@Override
	public String toString() {
		return "" + serialnumber;
	}
	
}
