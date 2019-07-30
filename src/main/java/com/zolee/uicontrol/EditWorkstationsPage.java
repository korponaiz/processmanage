package com.zolee.uicontrol;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.Workstation;
import com.zolee.service.ProductionSequenceService;
import com.zolee.service.UnitSequenceService;
import com.zolee.service.WorkstationService;

@SpringView(name = EditWorkstationsPage.NAME)
public class EditWorkstationsPage extends AdminPagePattern{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "EditWorkstations";
	
	private WorkstationService workstationService;
	
	@Autowired
	public void setWorkstationService(WorkstationService workstationService) {
		this.workstationService = workstationService;
	}

	private UnitSequenceService unitSequenceService;
	
	@Autowired
	public void setUnitSequenceService(UnitSequenceService unitSequenceService) {
		this.unitSequenceService = unitSequenceService;
	}
	
	private ProductionSequenceService productionSequenceService;
	
	@Autowired
	public void setProductionSequenceService(ProductionSequenceService productionSequenceService) {
		this.productionSequenceService = productionSequenceService;
	}

	private VerticalLayout mainLayout;
	private HorizontalLayout editButtonLayout;
	private HorizontalLayout inputLayout;
	private Label titleLabel;
	private TextField workstationNameField;
	private TextField workstationDescriptionField;
	private Button saveButton;
	private Button updateButton;
	private Button deleteButton;
	private Grid<Workstation> grid;
	private long selectedWorkstationId;
	private String editComponentsWidth;

	public EditWorkstationsPage() {
		
		mainLayout = new VerticalLayout();
		editButtonLayout = new HorizontalLayout();
		inputLayout = new HorizontalLayout();
		titleLabel = new Label("Edit workstations");
		workstationNameField = new TextField("Workstation name:");
		workstationDescriptionField = new TextField("Workstation description");
		saveButton = new Button("Save");
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");
		grid = new Grid<>(Workstation.class);
		grid.setColumns("workstationName", "workstationDescription");
		editComponentsWidth = "250px";

		workstationNameField.setWidth(editComponentsWidth);
		workstationDescriptionField.setWidth(editComponentsWidth);
		saveButton.setWidth(editComponentsWidth);
		updateButton.setWidth(editComponentsWidth);
		deleteButton.setWidth(editComponentsWidth);
		grid.setWidth("520px");
		grid.getColumn("workstationName").setWidth(260);
		grid.getColumn("workstationDescription").setWidth(260);

		addComponent(mainLayout);
		mainLayout.addComponents(titleLabel, editButtonLayout, inputLayout, grid);
		editButtonLayout.addComponents(saveButton, updateButton, deleteButton);
		inputLayout.addComponents(workstationNameField, workstationDescriptionField);

		
		grid.asSingleSelect().addValueChangeListener( e -> {
			gridSelectEvent(e);
		});
		
		saveButton.setClickShortcut(KeyCode.ENTER);
		saveButton.addClickListener( e -> save());
		updateButton.addClickListener( e -> update());
		deleteButton.addClickListener( e -> delete());
	}

	private void gridSelectEvent(ValueChangeEvent<Workstation> e) {
		if(e.getValue()!=null) {
			workstationNameField.setValue( e.getValue().getWorkstationName());
			workstationDescriptionField.setValue(e.getValue().getWorkstationDescription());
			selectedWorkstationId = e.getValue().getId();
		}
	}

	@PostConstruct
	private void init() {
		upDateGrid();
	}
	
	private void upDateGrid() {
		List<Workstation> resultList = workstationService.findAll();
		if(resultList!=null)
			grid.setItems(resultList);
	}
	
	private void save() {
		if(!checkEmptyFields()){
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else if(workstationService.findByWorkstationName(workstationNameField.getValue())!=null) {
			Notification.show("Existing workstation!", Notification.Type.ERROR_MESSAGE);
		}else {
			workstationService.save(new Workstation(workstationNameField.getValue(), workstationDescriptionField.getValue()));
		}
		upDateGrid();
	}
	
	private void update() {
		if(!checkEmptyFields()){
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else {
			workstationService.save(new Workstation(selectedWorkstationId, workstationNameField.getValue(), workstationDescriptionField.getValue()));
		}
		upDateGrid();
	}
	
	private void delete() {
		Workstation tempWorkstation = workstationService.findById(selectedWorkstationId);
		if(unitSequenceService.findFirstByWorkstation(tempWorkstation)==null && productionSequenceService.findFirstByWorkstation(tempWorkstation)==null) {
			workstationService.delete(selectedWorkstationId);
		}else {
			Notification.show("Existing relationship!", Notification.Type.ERROR_MESSAGE);
		}
		upDateGrid();
	}

	private boolean checkEmptyFields() {
		if(workstationNameField.getValue().equals("") || workstationDescriptionField.getValue().equals("")) {
			return false;
		}
		return true;
	}
}
