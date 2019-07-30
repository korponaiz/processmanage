package com.zolee.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestEmployee {

	private Employee testEmployee;
	
	@Before
	public void init() {
		testEmployee = new Employee((long)4, "testusername", "testrealname", "testpassword", "testright");
	}

	@Test
	public void employeeTest() {
		assertEquals("Id check: ", (long)4, (long)testEmployee.getId());
		assertEquals("UserName check: ", "testusername", testEmployee.getUserName());
		assertEquals("RealName check: ", "testrealname", testEmployee.getRealName());
		assertEquals("Password check: ", "testpassword", testEmployee.getPassword());
		assertEquals("Right check: ", "testright", testEmployee.getRight());
	}
	
}
