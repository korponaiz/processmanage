package com.zolee.uicontrol;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.Employee;
import com.zolee.domain.ProductUnit;
import com.zolee.domain.ProductionHistory;
import com.zolee.domain.Workstation;
import com.zolee.service.EmployeeService;
import com.zolee.service.ProductUnitService;
import com.zolee.service.ProductionHistoryService;
import com.zolee.service.WorkstationService;

import javassist.expr.NewArray;

@SpringView(name = AllHistoryPage.NAME)
public class AllHistoryPage extends AdminPagePattern {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "AllHistory";

	private ProductionHistoryService productionHistoryService;
	
	@Autowired
	public void setProductionHistoryService(ProductionHistoryService productionHistoryService) {
		this.productionHistoryService = productionHistoryService;
	}

	private EmployeeService employeeService;
	
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	private WorkstationService workstationService;

	@Autowired
	public void setWorkstationService(WorkstationService workstationService) {
		this.workstationService = workstationService;
	}

	private ProductUnitService productUnitService;
	
	@Autowired
	public void setProductUnitService(ProductUnitService productUnitService) {
		this.productUnitService = productUnitService;
	}

	private VerticalLayout mainLayout;
	private HorizontalLayout selectLayout;
	private NativeSelect<Employee> employeeSelect;
	private NativeSelect<Workstation> workstationSelect;
	private NativeSelect<ProductUnit> productunitSelect;
	private Grid<ProductionHistory> grid;
	private TextField totalTimeField;
	private String editComponentsWidth;
	private List<ProductionHistory> productionHistoryResultList;
	private long tempTime = 0;
	private long tempTime2 = 0;

	
	public AllHistoryPage() {
		
		productionHistoryResultList = new ArrayList<>();
		mainLayout = new VerticalLayout();
		selectLayout = new HorizontalLayout();
		employeeSelect = new NativeSelect<>("Employee:");
		workstationSelect = new NativeSelect<>("Workstation:");
		productunitSelect = new NativeSelect<>("Product Unit:");
		totalTimeField = new TextField("Total Time: ");
		grid = new Grid<>(ProductionHistory.class);
		grid.setColumns("unitSequence" , "productionSequence", "actiontype", "actualdate", "startTime", "stopTime", "employee", "description");
		editComponentsWidth = "200px";
		employeeSelect.setWidth(editComponentsWidth);
		workstationSelect.setWidth(editComponentsWidth);
		productunitSelect.setWidth(editComponentsWidth);
		totalTimeField.setWidth(editComponentsWidth);
		
		grid.setWidth("1000px");
		grid.setHeight("1000px");

		addComponent(mainLayout);
		mainLayout.addComponents(selectLayout, grid);
		selectLayout.addComponents(employeeSelect, workstationSelect, productunitSelect, totalTimeField);
		
		employeeSelect.addSelectionListener( e -> SelectEvent());
		workstationSelect.addSelectionListener( e -> SelectEvent());
		productunitSelect.addSelectionListener( e -> SelectEvent());
	}
	
	private void SelectEvent() {
		if(employeeSelect.getValue()==null) {
			productionHistoryResultList = productionHistoryService.findAll();
		}else {
			productionHistoryResultList = productionHistoryService.findByEmployee(employeeSelect.getValue());
		}
		if(workstationSelect.getValue()!=null) {
			Iterator<ProductionHistory> iterator = productionHistoryResultList.iterator();
			while(iterator.hasNext()) {
				ProductionHistory productionHistory = iterator.next();
				if(!(productionHistory.getProductionSequence().getUnitSequence().getWorkstation().getWorkstationName()).equals(workstationSelect.getValue().getWorkstationName())) {
					iterator.remove();
				}
			}
		}
		if(productunitSelect.getValue()!=null) {
			Iterator<ProductionHistory> iterator = productionHistoryResultList.iterator();
			while(iterator.hasNext()) {
				ProductionHistory productionHistory = iterator.next();
				if(!(productionHistory.getProductionSequence().getUnitSequence().getProductUnit().getUnitName()).equals(productunitSelect.getValue().getUnitName())) {
					iterator.remove();
				}
			}
		}
		grid.setItems(productionHistoryResultList);
		setTotalTimeField();
	}

	@PostConstruct
	private void init() {
		productionHistoryResultList = productionHistoryService.findAll();
		employeeSelect.setItems(employeeService.findAll());
		workstationSelect.setItems(workstationService.findAll());
		productunitSelect.setItems(productUnitService.findAll());
		grid.setItems(productionHistoryResultList);
		setTotalTimeField();
	}
	
	private void setTotalTimeField() {
		tempTime = 0;
		tempTime2 = 0;
		for(ProductionHistory productionHistory : productionHistoryResultList) {
			tempTime = productionHistory.getStartTime().until(productionHistory.getStopTime(), ChronoUnit.MILLIS);
			tempTime2 += tempTime; 
		}
		totalTimeField.setValue(String.format("%02d:%02d:%02d", 
				TimeUnit.MILLISECONDS.toHours(tempTime2),
				TimeUnit.MILLISECONDS.toMinutes(tempTime2) -  
				TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(tempTime2)), 
				TimeUnit.MILLISECONDS.toSeconds(tempTime2) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tempTime2))));
	}
}
