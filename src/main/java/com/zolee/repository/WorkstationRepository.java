package com.zolee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zolee.domain.Workstation;

public interface WorkstationRepository extends CrudRepository<Workstation, Long> {

	List<Workstation> findAll();
	
	Workstation findByWorkstationName(String workstationname); 
}
