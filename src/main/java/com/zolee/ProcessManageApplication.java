package com.zolee;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zolee.domain.Employee;
import com.zolee.service.EmployeeService;

@SpringBootApplication
public class ProcessManageApplication {

	private static EmployeeService employeeService;
	
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProcessManageApplication.class, args);
		if(employeeService.findByUserName("root")==null) {
			employeeService.save(new Employee("root", "root", "root", "admin"));
		}
	}
	
}
