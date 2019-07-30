package com.zolee.uicontrol;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.ProductUnit;
import com.zolee.service.ProductUnitService;
import com.zolee.service.UnitSequenceService;

import aj.org.objectweb.asm.Type;

@SpringView(name = EditUnitsPage.NAME)
public class EditUnitsPage extends AdminPagePattern{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "EditUnits";

	private ProductUnitService productUnitService;
	
	@Autowired
	public void setUnitService(ProductUnitService productUnitService) {
		this.productUnitService = productUnitService;
	}

	private UnitSequenceService unitSequenceService;
	
	@Autowired
	public void setUnitSequenceService(UnitSequenceService unitSequenceService) {
		this.unitSequenceService = unitSequenceService;
	}

	private VerticalLayout mainLayout;
	private HorizontalLayout editButtonLayout;
	private HorizontalLayout inputLayout;
	private Label titleLabel;
	private TextField preserialnumberField;
	private TextField unitNameField;
	private TextField unitDescriptionField;
	private Button saveButton;
	private Button updateButton;
	private Button deleteButton;
	private Grid<ProductUnit> grid;
	private long selectedUnitId;
	private String editComponentsWidth;

	public EditUnitsPage() {
		
		mainLayout = new VerticalLayout();
		editButtonLayout = new HorizontalLayout();
		inputLayout = new HorizontalLayout();
		titleLabel = new Label("Edit units");
		saveButton = new Button("Save");
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");
		unitNameField = new TextField("Unit name:");
		preserialnumberField = new TextField("Preserial:");
		unitDescriptionField = new TextField("Unit description:");
		grid = new Grid<>(ProductUnit.class);
		grid.setColumns("unitName", "preserialnumber", "unitDescription");
		editComponentsWidth = "250px";

		unitNameField.setWidth(editComponentsWidth);
		preserialnumberField.setWidth(editComponentsWidth);
		unitDescriptionField.setWidth(editComponentsWidth);
		saveButton.setWidth(editComponentsWidth);
		updateButton.setWidth(editComponentsWidth);
		deleteButton.setWidth(editComponentsWidth);
		grid.setWidth("780px");
		grid.getColumn("unitName").setWidth(260);
		grid.getColumn("preserialnumber").setWidth(260);
		grid.getColumn("unitDescription").setWidth(260);

		addComponent(mainLayout);
		mainLayout.addComponents(titleLabel, editButtonLayout, inputLayout, grid);
		editButtonLayout.addComponents(saveButton, updateButton, deleteButton);
		inputLayout.addComponents(unitNameField, preserialnumberField, unitDescriptionField);

		grid.asSingleSelect().addValueChangeListener( e -> {
			gridSelectEvent(e);
		});
		
		saveButton.addClickListener( e -> save());
		updateButton.addClickListener( e -> update());
		deleteButton.addClickListener( e -> delete());
		preserialnumberField.addValueChangeListener( e -> checkNumberFormat());
		
	}

	private void gridSelectEvent(ValueChangeEvent<ProductUnit> e) {
		if(e.getValue()!=null) {
			unitNameField.setValue(e.getValue().getUnitName());
			preserialnumberField.setValue("" + e.getValue().getPreserialnumber());
			unitDescriptionField.setValue(e.getValue().getUnitDescription());
			selectedUnitId = e.getValue().getId();
		}
	}

	@PostConstruct
	private void init() {
		upDateGrid();
	}
	
	private void upDateGrid() {
		List<ProductUnit> resultList = productUnitService.findAll();
		if(resultList!=null)
			grid.setItems(resultList);
	}
	
	private void save() {
		if(checkEmptyfields()) {
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else if(checkNumberFormat()){
			Notification.show("Preserial is only number!", Notification.Type.ERROR_MESSAGE);
		}else if(checkExistingUnit()){
			Notification.show("Existing unit!", Notification.Type.ERROR_MESSAGE);
		}else if(checkExistingPreserial()) {
			Notification.show("Existing preserial!", Notification.Type.ERROR_MESSAGE);
		}else {
			productUnitService.save(new ProductUnit(unitNameField.getValue(), Integer.parseInt(preserialnumberField.getValue()), 
					unitDescriptionField.getValue()));
		}
		upDateGrid();
	}

	private void update() {
		if(checkEmptyfields()) {
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else if(checkNumberFormat()){
			Notification.show("Preserial is only number!", Notification.Type.ERROR_MESSAGE);
		}else {
			productUnitService.save(new ProductUnit(selectedUnitId, unitNameField.getValue(), Integer.parseInt(preserialnumberField.getValue()),
					unitDescriptionField.getValue()));
		}
		upDateGrid();
	}
	
	private void delete() {
		if(unitSequenceService.findFirstByProductUnit(productUnitService.findById(selectedUnitId))==null) {
			productUnitService.delete(selectedUnitId);
		}else {
			Notification.show("Existing relationship!", Notification.Type.ERROR_MESSAGE);
		}
		upDateGrid();
	}
	
	private boolean checkEmptyfields() {
		if(unitNameField.getValue().equals("") || preserialnumberField.getValue().equals("")) {
			return true;
		}
		return false;
	}
	
	private boolean checkExistingUnit() {
		if(productUnitService.findByUnitName(unitNameField.getValue())!=null) {
			return true;
		}
		return false;
	}
	
	private boolean checkExistingPreserial() {
		if(productUnitService.findByPreserialnumber(Long.parseLong(preserialnumberField.getValue()))!=null){
			return true;
		}
		return false;
	}
	
	private boolean checkNumberFormat() {
		if(preserialnumberField.getValue().length()!=0) {
			try{
				Integer.parseInt(preserialnumberField.getValue());
				}
			catch(NumberFormatException e){
				Notification.show("Preserial is only number!", Notification.Type.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}

}
