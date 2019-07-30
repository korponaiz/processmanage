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
public class UnitSequence {

	@GeneratedValue
	@Id
	private Long id;
	private String unitsequencename;
	private int unitsequencepositon;
	@ManyToOne
	private ProductUnit productUnit;
	@ManyToOne
	private Workstation workstation;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "unitSequence")
	private List<ProductionHistory> productionHistories;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "unitSequence")
	private List<ProductionSequence> productionSequences;
	
	public UnitSequence() {
	}
	
	public UnitSequence(String unitsequencename, int unitsequencepositon, ProductUnit productUnit, Workstation workstation) {
		this.unitsequencename = unitsequencename;
		this.unitsequencepositon = unitsequencepositon;
		this.productUnit = productUnit;
		this.workstation = workstation;
	}

	public UnitSequence(Long id, String unitsequencename, int unitsequencepositon, ProductUnit productUnit, Workstation workstation) {
		this.id = id;
		this.unitsequencename = unitsequencename;
		this.unitsequencepositon = unitsequencepositon;
		this.productUnit = productUnit;
		this.workstation = workstation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitsequencename() {
		return unitsequencename;
	}

	public void setUnitsequencename(String unitsequencename) {
		this.unitsequencename = unitsequencename;
	}

	public int getUnitsequencepositon() {
		return unitsequencepositon;
	}

	public void setUnitsequencepositon(int unitsequencepositon) {
		this.unitsequencepositon = unitsequencepositon;
	}

	public ProductUnit getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(ProductUnit productUnit) {
		this.productUnit = productUnit;
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

	public List<ProductionSequence> getProductionSequences() {
		return productionSequences;
	}

	public void setProductionSequences(List<ProductionSequence> productionSequences) {
		this.productionSequences = productionSequences;
	}

	@Override
	public String toString() {
		return (productUnit.getUnitName());
	}

}
