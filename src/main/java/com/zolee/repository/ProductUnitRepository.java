package com.zolee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zolee.domain.ProductUnit;

public interface ProductUnitRepository extends CrudRepository<ProductUnit, Long> {
	
	List<ProductUnit> findAll();
	
	ProductUnit findByUnitName(String name);
	
	ProductUnit findByPreserialnumber(long preserial);

}
