package com.zolee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zolee.domain.ProductUnit;
import com.zolee.domain.ProductionSequence;
import com.zolee.domain.UnitSequence;
import com.zolee.domain.Workstation;
import com.zolee.repository.UnitSequenceRepository;

@Service
public class UnitSequenceService {

	private UnitSequenceRepository unitSequenceRepository;

	@Autowired
	public void setUnitSequenceRepository(UnitSequenceRepository unitSequenceRepository) {
		this.unitSequenceRepository = unitSequenceRepository;
	}
	
	public List<UnitSequence> findAll(){
		return unitSequenceRepository.findAll();
	}
	
	public 	List<UnitSequence> findByWorkstation(Workstation workstation){
		return unitSequenceRepository.findByWorkstation(workstation);
	}
	
	public 	List<UnitSequence> findByProductUnit(ProductUnit productUnit){
		return unitSequenceRepository.findByProductUnit(productUnit);
	}
	
	public List<UnitSequence> findByUnitsequencename(String unitsequencename) {
		return unitSequenceRepository.findByUnitsequencename(unitsequencename);
	}
	
	public Long countByProductUnit(ProductUnit productUnit) {
		return unitSequenceRepository.countByProductUnit(productUnit);
	}
	
	public UnitSequence findByProductionSequencesAndUnitsequencepositon(ProductionSequence productionSequence, int unitsequencepositon) {
		return unitSequenceRepository.findByProductionSequencesAndUnitsequencepositon(productionSequence, unitsequencepositon);
	}
	
	public UnitSequence findByUnitsequencenameAndUnitsequencepositon(String unitsequencename, int unitsequencepositon) {
		return unitSequenceRepository.findByUnitsequencenameAndUnitsequencepositon(unitsequencename, unitsequencepositon);
	}

	public UnitSequence findByProductUnitAndUnitsequencepositon(ProductUnit productUnit, int unitsequencepositon) {
		return unitSequenceRepository.findByProductUnitAndUnitsequencepositon(productUnit, unitsequencepositon);
	}

	public UnitSequence findById(long id) {
		return unitSequenceRepository.findOne(id);
	}
	
	public UnitSequence findFirstByProductUnit(ProductUnit productUnit) {
		return unitSequenceRepository.findFirstByProductUnit(productUnit);
	}
	
	public UnitSequence findFirstByWorkstation(Workstation workstation){
		return unitSequenceRepository.findFirstByWorkstation(workstation);
	}
	
	public List<UnitSequence> findAllById(List<Workstation> id){
		return unitSequenceRepository.findAllById(id);
	}
	
	public void save(UnitSequence unitSequence) {
		unitSequenceRepository.save(unitSequence);
	}
	
	public void delete(long id) {
		unitSequenceRepository.delete(id);
	}
}
