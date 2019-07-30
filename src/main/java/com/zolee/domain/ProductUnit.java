package com.zolee.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class ProductUnit {

	@GeneratedValue
	@Id
	private Long id;
	private String unitName;
	private long preserialnumber;
	private String unitDescription;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "productUnit")
	private List<UnitSequence> unitSequences;

	public ProductUnit() {
	}

	public ProductUnit(String unitName, long preserialnumber, String unitDescription) {
		this.unitName = unitName;
		this.preserialnumber = preserialnumber;
		this.unitDescription = unitDescription;
	}

	public ProductUnit(Long id, String unitName, long preserialnumber, String unitDescription) {
		this.id = id;
		this.unitName = unitName;
		this.preserialnumber = preserialnumber;
		this.unitDescription = unitDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public long getPreserialnumber() {
		return preserialnumber;
	}

	public void setPreserialnumber(long preserialnumber) {
		this.preserialnumber = preserialnumber;
	}

	public String getUnitDescription() {
		return unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public List<UnitSequence> getUnitSequences() {
		return unitSequences;
	}

	public void setUnitSequences(List<UnitSequence> unitSequences) {
		this.unitSequences = unitSequences;
	}

	@Override
	public String toString() {
		return unitName;
	}

}
