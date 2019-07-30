package com.zolee.uicontrol;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.ProductUnit;
import com.zolee.domain.ProductionHistory;
import com.zolee.domain.ProductionSequence;
import com.zolee.domain.UnitSequence;
import com.zolee.service.EmployeeService;
import com.zolee.service.ProductUnitService;
import com.zolee.service.ProductionHistoryService;
import com.zolee.service.ProductionSequenceService;
import com.zolee.service.UnitSequenceService;

@SpringView(name = EditProductionSequencePage.NAME)
public class EditProductionSequencePage extends AdminPagePattern{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "EditProductionSequence";
	
	private ProductionSequenceService productionSequenceService;

	@Autowired
	public void setProductionSequenceService(ProductionSequenceService productionSequenceService) {
		this.productionSequenceService = productionSequenceService;
	}

	private ProductUnitService productUnitService;
	
	@Autowired
	public void setProductUnitService(ProductUnitService productUnitService) {
		this.productUnitService = productUnitService;
	}

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

	private UnitSequenceService unitSequenceService;
	
	@Autowired
	public void setUnitSequenceService(UnitSequenceService unitSequenceService) {
		this.unitSequenceService = unitSequenceService;
	}

	private VerticalLayout mainLayout;
	private HorizontalLayout editButtonLayout;
	private HorizontalLayout inputLayout;
	private HorizontalLayout serialNumberLayout;
	private Label titleLabel;
	private TextField preSerialNumberField;
	private TextField subSerialNumberField;
	private NativeSelect<String> productunitSelect;
	private Button saveButton;
	private Button updateButton;
	private Button deleteButton;
	private List<String> productUnitNameList;
	private Grid<ProductionSequence> grid;
	private long selectedProductionSequenceId;
	private long actualHistoryId;
	private String editComponentsWidth;

	public EditProductionSequencePage() {
		
		productUnitNameList = new ArrayList<>();
		mainLayout = new VerticalLayout();
		editButtonLayout = new HorizontalLayout();
		inputLayout = new HorizontalLayout();
		serialNumberLayout = new HorizontalLayout();
		titleLabel = new Label("Edit productionsequences");
		saveButton = new Button("Save");
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");
		preSerialNumberField = new TextField("Preserial:");
		subSerialNumberField = new TextField("Subserial:");
		productunitSelect = new NativeSelect<>("Production Unit:");
		editComponentsWidth = "250px";
		preSerialNumberField.setEnabled(false);
		grid = new Grid<>(ProductionSequence.class);
		grid.setColumns("unitSequence", "serialnumber");

		productunitSelect.setWidth(editComponentsWidth);
		saveButton.setWidth(editComponentsWidth);
		updateButton.setWidth(editComponentsWidth);
		deleteButton.setWidth(editComponentsWidth);
		preSerialNumberField.setWidth("100px");
		subSerialNumberField.setWidth("150px");
		grid.setWidth("520px");
		serialNumberLayout.setSpacing(false);
	
		addComponent(mainLayout);
		mainLayout.addComponents(titleLabel, editButtonLayout, inputLayout, grid);
		editButtonLayout.addComponents(saveButton, updateButton, deleteButton);
		inputLayout.addComponents(productunitSelect, serialNumberLayout);
		serialNumberLayout.addComponents(preSerialNumberField, subSerialNumberField);

		saveButton.setClickShortcut(KeyCode.ENTER);
		productunitSelect.addSelectionListener( e -> productUnitSelectEvent(e));
		grid.asSingleSelect().addValueChangeListener( e -> gridSelectEvent(e));
		subSerialNumberField.addValueChangeListener( e -> subSerialNumberFieldEvent());
		saveButton.addClickListener( e -> save());
		updateButton.addClickListener( e -> update());
		deleteButton.addClickListener( e -> delete());

	}
	
	@PostConstruct
	private void init() {
		for(ProductUnit productUnit : productUnitService.findAll()) {
			productUnitNameList.add(productUnit.getUnitName());
		}
		productunitSelect.setItems(productUnitNameList);
		upDateGrid();
	}

	private void upDateGrid() {
		grid.setItems(productionSequenceService.findAll());
	}
	
	private void gridSelectEvent(ValueChangeEvent<ProductionSequence> e) {
		if(e.getValue()!=null) {
			selectedProductionSequenceId = e.getValue().getId();
			actualHistoryId = e.getValue().getActualHistoryId();
			productunitSelect.setValue(e.getValue().getUnitSequence().getProductUnit().getUnitName());
			subSerialNumberField.setValue(""+(e.getValue().getSerialnumber()-(e.getValue().getUnitSequence().getProductUnit().getPreserialnumber()*1000000)));
		}
	}

	private void productUnitSelectEvent(ValueChangeEvent<String> e){
		if(e.getValue()!=null) {
			preSerialNumberField.setValue("" + productUnitService.findByUnitName(e.getValue()).getPreserialnumber());
		}
	}
	
	private void subSerialNumberFieldEvent() {
		if(!checkEmptyField()) {
			if(checkNumberFormat()) {
				Notification.show("Subserial is only number!", Notification.Type.ERROR_MESSAGE);
			}
		}
	}
	
	private void save() {
		if(checkEmptyField()) {
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else if(checkNumberFormat()) {
			Notification.show("Subserial is only number!", Notification.Type.ERROR_MESSAGE);
		}else if(checkExistingSerial()){
			Notification.show("Existing serial number!", Notification.Type.ERROR_MESSAGE);
		}else {
			UnitSequence unitSequence = unitSequenceService.findByProductUnitAndUnitsequencepositon(productUnitService.findByUnitName(productunitSelect.getValue()), 1);
			ProductionSequence productionSequence = new ProductionSequence(getSerialNumber(), "start", 0,
					unitSequence, unitSequence.getWorkstation()); 
			productionSequenceService.save(productionSequence);
			ProductionHistory tempProductionHistory = new ProductionHistory(LocalDate.now(), LocalTime.now(), LocalTime.now(), "start", "",
					productionSequenceService.findBySerialnumber(getSerialNumber()),
					employeeService.findByUserName(VaadinSession.getCurrent().getAttribute("employeename").toString()),
					unitSequenceService.findByProductUnitAndUnitsequencepositon(productUnitService.findByUnitName(productunitSelect.getValue()), 1));
			productionHistoryService.save(tempProductionHistory);
			productionSequenceService.save(new ProductionSequence(productionSequence.getId(), getSerialNumber(), "start", tempProductionHistory.getId(),
					unitSequence, unitSequence.getWorkstation()));
		}
		subSerialNumberField.clear();
		upDateGrid();
	}
	
	private void update() {
		if(selectedProductionSequenceId!=0){
			if(checkEmptyField()) {
				Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
			}else if(checkNumberFormat()) {
				Notification.show("Subserial is only number!", Notification.Type.ERROR_MESSAGE);
			}else {
				UnitSequence unitSequence = unitSequenceService.findByProductUnitAndUnitsequencepositon(productUnitService.findByUnitName(productunitSelect.getValue()), 1); 
				productionSequenceService.save(new ProductionSequence(selectedProductionSequenceId, getSerialNumber(), "start", actualHistoryId,
						unitSequence, unitSequence.getWorkstation()));
			}
		}
		upDateGrid();
	}
	
	private void delete() {
		if(productionHistoryService.findFirstByProductionSequence(productionSequenceService.findById(selectedProductionSequenceId))==null) {
			if(selectedProductionSequenceId!=0){
				productionSequenceService.delete(selectedProductionSequenceId);
			}
		}else {
			Notification.show("Existing relationship!", Notification.Type.ERROR_MESSAGE);
		}
		upDateGrid();
	}
	
	private long getSerialNumber() {
		long serialnumber = Long.parseLong(preSerialNumberField.getValue())*1000000 + Long.parseLong(subSerialNumberField.getValue());
		return (serialnumber);
	}
	
	private boolean checkEmptyField() {
		if((productunitSelect.getValue()==null) || subSerialNumberField.getValue().equals("")){
			return true;
		}
		return false;
	}

	private boolean checkNumberFormat() {
			try{
				Integer.parseInt(subSerialNumberField.getValue());
				}
			catch(NumberFormatException e){
				return true;
			}
		return false;
	}
	
	private boolean checkExistingSerial() {
		if(productionSequenceService.findBySerialnumber(getSerialNumber())!=null) {
			return true;
		}
		return false;
	}
}
