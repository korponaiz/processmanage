package com.zolee.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestProductionSequence {

	private ProductionSequence testProductionSequence;
	private UnitSequence testUnitSequence;
	private Workstation testWorkstation;
	
	@Before
	public void init() {
		testUnitSequence = new UnitSequence();
		testWorkstation = new Workstation();
		testProductionSequence = new ProductionSequence((long)3, (long)11, "teststatus", 5, testUnitSequence, testWorkstation);
	}
	
	@Test
	public void productionSequenceTest() {
		assertEquals("Id check: ", (long)3, (long)testProductionSequence.getId());
		assertEquals("Serialnumber check: ", (long)11, (long)testProductionSequence.getSerialnumber());
		assertEquals("Status check: ", "teststatus", testProductionSequence.getStatus());
		assertEquals("Serialnumber check: ", 5, testProductionSequence.getActualHistoryId());
		assertEquals("UnitSequence check: ", testUnitSequence, testProductionSequence.getUnitSequence());
		assertEquals("Workstation check: ", testWorkstation, testProductionSequence.getWorkstation());
	}
}
