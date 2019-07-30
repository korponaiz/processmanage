package com.zolee.authenticating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zolee.domain.Employee;
import com.zolee.service.EmployeeService;

@Service
public class Authentication {

	private EmployeeService employeeService;

	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	public boolean authenticate(String name, String password) {
		Employee employee = employeeService.findByUserName(name);
		if(employee!=null && employee.getUserName().equals(name) && employee.getPassword().equals(password) &&
				!(employee.getRight().equals("deleted")))
			return true;
		return false;
	}
}
