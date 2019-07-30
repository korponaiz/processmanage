package com.zolee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zolee.domain.ProductUnit;
import com.zolee.domain.ProductionSequence;
import com.zolee.domain.UnitSequence;
import com.zolee.domain.Workstation;

public interface UnitSequenceRepository extends CrudRepository<UnitSequence, Long> {
	
	List<UnitSequence> findAll();

	List<UnitSequence> findByProductUnit(ProductUnit productUnit);
	
	List<UnitSequence> findByWorkstation(Workstation workstation);

	List<UnitSequence> findAllById(List<Workstation> id);
	
	List<UnitSequence> findByUnitsequencename(String unitsequencename);
	
	Long countByProductUnit(ProductUnit productUnit);
	
	UnitSequence findByUnitsequencenameAndUnitsequencepositon(String unitsequencename, int unitsequencepositon);

	UnitSequence findByProductUnitAndUnitsequencepositon(ProductUnit productUnit, int unitsequencepositon);
	
	UnitSequence findByProductionSequencesAndUnitsequencepositon(ProductionSequence productionSequence, int unitsequencepositon);
	
	UnitSequence findFirstByProductUnit(ProductUnit productUnit);

	UnitSequence findFirstByWorkstation(Workstation workstation);
}
