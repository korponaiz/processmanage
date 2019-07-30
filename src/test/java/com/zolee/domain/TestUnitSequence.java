package com.zolee.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestUnitSequence {

	private UnitSequence testUnitSequence;
	private ProductUnit testProductUnit;
	private Workstation testWorkstation;
	
	@Before
	public void iit() {
		testProductUnit = new ProductUnit();
		testWorkstation = new Workstation();
		testUnitSequence = new UnitSequence((long)34, "testunitsequencename", 113, testProductUnit, testWorkstation);
	}
	
	@Test
	public void unitSequenceTest() {
		assertEquals("Id check: ", (long)34, (long)testUnitSequence.getId());
		assertEquals("Unitsequencename check: ", "testunitsequencename", testUnitSequence.getUnitsequencename());
		assertEquals("Unitsequencepositon check: ", 113, testUnitSequence.getUnitsequencepositon());
		assertEquals("ProductUnit check: ", testProductUnit, testUnitSequence.getProductUnit());
		assertEquals("Workstation check: ", testWorkstation, testUnitSequence.getWorkstation());
	}
	
}
