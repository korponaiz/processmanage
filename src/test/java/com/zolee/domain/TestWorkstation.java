package com.zolee.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestWorkstation {

	private Workstation testWorkstation;
	
	@Before
	public void init() {
		testWorkstation = new Workstation((long)53434, "testworkstationName", "testworkstationDescription");
	}
	
	@Test
	public void workstationTest() {
		assertEquals("Id check: ", (long)53434, (long)testWorkstation.getId());
		assertEquals("WorkstationName check: ", "testworkstationName", testWorkstation.getWorkstationName());
		assertEquals("WorkstationDescription check: ", "testworkstationDescription", testWorkstation.getWorkstationDescription());
	}
}
