package com.zolee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zolee.domain.Employee;
import com.zolee.domain.ProductionHistory;
import com.zolee.domain.ProductionSequence;
import com.zolee.domain.UnitSequence;

public interface ProductionHistoryRepository extends CrudRepository<ProductionHistory, Long> {

	List<ProductionHistory> findAll();
	
	List<ProductionHistory> findByUnitSequence(UnitSequence unitSequence);

	List<ProductionHistory> findByEmployee(Employee employee);

	ProductionHistory findFirstByUnitSequence(UnitSequence unitSequence);
	
	ProductionHistory findFirstByProductionSequence(ProductionSequence productionSequence);
}
