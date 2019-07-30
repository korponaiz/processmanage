package com.zolee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zolee.domain.ProductionSequence;
import com.zolee.domain.UnitSequence;
import com.zolee.domain.Workstation;

public interface ProductionSequenceRepository extends CrudRepository<ProductionSequence, Long> {
	
	List<ProductionSequence> findAll();
	
	List<ProductionSequence> findByStatus(String status);

	List<ProductionSequence> findByUnitSequence(UnitSequence unitSequence);

	List<ProductionSequence> findByWorkstation(Workstation workstation);
	
	ProductionSequence findBySerialnumber(Long serialnumber);
	
	ProductionSequence findFirstByWorkstation(Workstation workstation);
	
	ProductionSequence findFirstByUnitSequence(UnitSequence unitSequence);
}
