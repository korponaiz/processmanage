package com.zolee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zolee.domain.ProductionSequence;
import com.zolee.domain.UnitSequence;
import com.zolee.domain.Workstation;
import com.zolee.repository.ProductionSequenceRepository;

@Service
public class ProductionSequenceService {

	private ProductionSequenceRepository productionSequenceRepository;

	@Autowired
	public void setProductionSequenceRepository(ProductionSequenceRepository productionSequenceRepository) {
		this.productionSequenceRepository = productionSequenceRepository;
	}
	
	public List<ProductionSequence> findAll(){
		return productionSequenceRepository.findAll();
	}
	
	public List<ProductionSequence> findByWorkstation(Workstation workstation){
		return productionSequenceRepository.findByWorkstation(workstation);
	}

	public ProductionSequence findById(long id) {
		return productionSequenceRepository.findOne(id);
	}
	
	public ProductionSequence findBySerialnumber(long serialnumber) {
		return productionSequenceRepository.findBySerialnumber(serialnumber);
	}

	public ProductionSequence findFirstByWorkstation(Workstation workstation) {
		return productionSequenceRepository.findFirstByWorkstation(workstation);
	}

	public ProductionSequence findFirstByUnitSequence(UnitSequence unitSequence) {
		return productionSequenceRepository.findFirstByUnitSequence(unitSequence);
	}
	
	public void save(ProductionSequence productionSequence) {
		productionSequenceRepository.save(productionSequence);
	}
	
	public void delete(long id) {
		productionSequenceRepository.delete(id);
	}
}
