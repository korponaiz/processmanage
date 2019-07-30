package com.zolee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zolee.domain.Employee;
import com.zolee.domain.ProductionHistory;
import com.zolee.domain.ProductionSequence;
import com.zolee.domain.UnitSequence;
import com.zolee.repository.ProductionHistoryRepository;

@Service
public class ProductionHistoryService {

	private ProductionHistoryRepository productionHistoryRepository;

	@Autowired
	public void setProductionHistoryRepository(ProductionHistoryRepository productionHistoryRepository) {
		this.productionHistoryRepository = productionHistoryRepository;
	}
	
	public List<ProductionHistory> findAll(){
		return productionHistoryRepository.findAll();
	}
	
	public List<ProductionHistory> findByUnitSequence(UnitSequence unitSequence){
		return productionHistoryRepository.findByUnitSequence(unitSequence);
	}

	public 	List<ProductionHistory> findByEmployee(Employee employee){
		return productionHistoryRepository.findByEmployee(employee);
	}

	public ProductionHistory findById(Long id) {
		return productionHistoryRepository.findOne(id);
	}

	public ProductionHistory findFirstByUnitSequence(UnitSequence unitSequence) {
		return productionHistoryRepository.findFirstByUnitSequence(unitSequence);
	}
	
	public ProductionHistory findFirstByProductionSequence(ProductionSequence productionSequence) {
		return productionHistoryRepository.findFirstByProductionSequence(productionSequence);
	}
	
	public void save(ProductionHistory productionHistory) {
		productionHistoryRepository.save(productionHistory);
	}
}
