package com.zolee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zolee.domain.Workstation;
import com.zolee.repository.WorkstationRepository;

@Service
public class WorkstationService {

	private WorkstationRepository workstationRepository;

	@Autowired
	public void setWorkstationRepository(WorkstationRepository workstationRepository) {
		this.workstationRepository = workstationRepository;
	}
	
	public List<Workstation> findAll(){
		return workstationRepository.findAll();
	}
	
	public Workstation findByWorkstationName(String workstationName) {
		return workstationRepository.findByWorkstationName(workstationName);
	}
	
	public Workstation findById(long id) {
		return workstationRepository.findOne(id);
	}
	
	public void save(Workstation workstation) {
		workstationRepository.save(workstation);
	}

	public void delete(long id) {
		workstationRepository.delete(id);
	}
}
