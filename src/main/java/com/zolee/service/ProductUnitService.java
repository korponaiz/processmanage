package com.zolee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zolee.domain.ProductUnit;
import com.zolee.repository.ProductUnitRepository;

@Service
public class ProductUnitService {

	private ProductUnitRepository productUnitRepository;
	
	@Autowired
	public void setProductUnitRepository(ProductUnitRepository productUnitRepository) {
		this.productUnitRepository = productUnitRepository;
	}

	public List<ProductUnit> findAll(){
		return productUnitRepository.findAll();
	}

	public ProductUnit findByUnitName(String name) {
		return productUnitRepository.findByUnitName(name);
	}
	
	public ProductUnit findByPreserialnumber(long preserial) {
		return productUnitRepository.findByPreserialnumber(preserial);
	}
	
	public ProductUnit findById(long id) {
		return productUnitRepository.findOne(id);
	}
	
	public void save(ProductUnit productUnit) {
		productUnitRepository.save(productUnit);
	}
	
	public void delete(long id) {
		productUnitRepository.delete(id);
	}
}
