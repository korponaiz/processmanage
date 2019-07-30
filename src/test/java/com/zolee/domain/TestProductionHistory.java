package com.zolee.domain;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

public class TestProductionHistory {
	
	private ProductionHistory testProductionHistory;
	private ProductionSequence testProductionSequence;
	private Employee testEmployee;
	private UnitSequence testUnitSequence;

	@Before
	public void init() {
		testProductionSequence = new ProductionSequence();
		testEmployee = new Employee();
		testUnitSequence = new UnitSequence();
		testProductionHistory = new ProductionHistory((long)2, LocalDate.now(), LocalTime.of(11, 11), LocalTime.of(12, 34),
				"testaction", "testdescription", testProductionSequence, testEmployee, testUnitSequence);
	}

	@Test
	public void productionHistoryTest() {
		assertEquals("Id check: ", (long)2, (long)testProductionHistory.getId());
		assertEquals("Actualdate check: ", LocalDate.now(), testProductionHistory.getActualdate());
		assertEquals("StartTime check: ", LocalTime.of(11, 11), testProductionHistory.getStartTime());
		assertEquals("StopTime check: ", LocalTime.of(12, 34), testProductionHistory.getStopTime());
		assertEquals("Actiontype check: ", "testaction", testProductionHistory.getActiontype());
		assertEquals("Description check: ", "testdescription", testProductionHistory.getDescription());
		assertEquals("Description check: ", testProductionSequence, testProductionHistory.getProductionSequence());
		assertEquals("Description check: ", testEmployee, testProductionHistory.getEmployee());
		assertEquals("Description check: ", testUnitSequence, testProductionHistory.getUnitSequence());
	}
}
