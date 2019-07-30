package com.zolee.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestProductUnit {

	private ProductUnit testProductUnit;
	
	@Before
	public void init() {
		testProductUnit = new ProductUnit((long)5, "testunitName", 11, "testunitDescription");
	}
	
	@Test
	public void productUnitTest() {
		assertEquals("Id check: ", (long)5, (long)testProductUnit.getId());
		assertEquals("UnitName check: ", "testunitName", testProductUnit.getUnitName());
		assertEquals("Preserialnumber check: ", 11, testProductUnit.getPreserialnumber());
		assertEquals("UnitDescription check: ", "testunitDescription", testProductUnit.getUnitDescription());
	}
}
