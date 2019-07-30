package com.zolee.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Workstation {

	@GeneratedValue
	@Id
	private Long id;
	private String workstationName;
	private String workstationDescription;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "workstation" )
	private List<UnitSequence> unitSequences;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "workstation" )
	private List<ProductionSequence> productionSequences;

	public Workstation() {
	}
	
	public Workstation(String workstationName, String workstationDescription) {
		this.workstationName = workstationName;
		this.workstationDescription = workstationDescription;
	}

	public Workstation(Long id, String workstationName, String workstationDescription) {
		this.id = id;
		this.workstationName = workstationName;
		this.workstationDescription = workstationDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkstationName() {
		return workstationName;
	}

	public void setWorkstationName(String workstationName) {
		this.workstationName = workstationName;
	}

	public String getWorkstationDescription() {
		return workstationDescription;
	}

	public void setWorkstationDescription(String workstationDescription) {
		this.workstationDescription = workstationDescription;
	}

	public List<UnitSequence> getUnitSequences() {
		return unitSequences;
	}

	public void setUnitSequences(List<UnitSequence> unitSequences) {
		this.unitSequences = unitSequences;
	}

	public List<ProductionSequence> getProductionSequences() {
		return productionSequences;
	}

	public void setProductionSequences(List<ProductionSequence> productionSequences) {
		this.productionSequences = productionSequences;
	}

	@Override
	public String toString() {
		return workstationName;
	}

}
