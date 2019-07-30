package com.zolee.uicontrol;

import java.util.ArrayList;
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
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.ProductUnit;
import com.zolee.domain.UnitSequence;
import com.zolee.domain.Workstation;
import com.zolee.service.ProductUnitService;
import com.zolee.service.ProductionHistoryService;
import com.zolee.service.ProductionSequenceService;
import com.zolee.service.UnitSequenceService;
import com.zolee.service.WorkstationService;

@SpringView(name = EditUnitSequencePage.NAME)
public class EditUnitSequencePage extends  AdminPagePattern{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "EditUnitSequence";
	
	private UnitSequenceService unitSequenceService;

	@Autowired
	public void setUnitSequenceService(UnitSequenceService unitSequenceService) {
		this.unitSequenceService = unitSequenceService;
	}
	
	private ProductUnitService productUnitService;
	
	@Autowired
	public void setProductUnitService(ProductUnitService productUnitService) {
		this.productUnitService = productUnitService;
	}

	private WorkstationService workstationService;

	@Autowired
	public void setWorkstationService(WorkstationService workstationService) {
		this.workstationService = workstationService;
	}

	private ProductionSequenceService productionSequenceService;
	
	@Autowired
	public void setProductionSequenceService(ProductionSequenceService productionSequenceService) {
		this.productionSequenceService = productionSequenceService;
	}

	private ProductionHistoryService productionHistoryService;
	
	@Autowired
	public void setProductionHistoryService(ProductionHistoryService productionHistoryService) {
		this.productionHistoryService = productionHistoryService;
	}

	private VerticalLayout mainLayout;
	private HorizontalLayout editButtonLayout;
	private HorizontalLayout inputLayout;
	private Label titleLabel;
	private TextField unitsequencenameField;
	private TextField unitsequencepositonField;
	private NativeSelect<String> productunitSelect;
	private NativeSelect<String> workstationSelect;
	private Button saveButton;
	private Button updateButton;
	private Button deleteButton;
	private Grid<UnitSequence> grid;
	private List<String> productUnitNameList;
	private List<String> workstationNameList;
	private long selectedUnitSequenceId;
	private String editComponentsWidth;
	
	public EditUnitSequencePage() {
		
		productUnitNameList = new ArrayList<>();
		workstationNameList = new ArrayList<>();
		mainLayout = new VerticalLayout();
		editButtonLayout = new HorizontalLayout();
		inputLayout = new HorizontalLayout();
		titleLabel = new Label("Edit unitsequences");
		saveButton = new Button("Save");
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");
		unitsequencenameField = new TextField("Unitsqeunce Name:");
		unitsequencepositonField = new TextField("Unitsequence Position:");
		productunitSelect = new NativeSelect<>("Production Unit:");
		workstationSelect = new NativeSelect<>("Workstation:");
		grid = new Grid<>(UnitSequence.class);
		grid.setColumns("productUnit", "unitsequencepositon", "unitsequencename", "workstation");
		editComponentsWidth = "250px";

		unitsequencenameField.setWidth(editComponentsWidth);
		unitsequencepositonField.setWidth(editComponentsWidth);
		productunitSelect.setWidth(editComponentsWidth);
		workstationSelect.setWidth(editComponentsWidth);
		saveButton.setWidth(editComponentsWidth);
		updateButton.setWidth(editComponentsWidth);
		deleteButton.setWidth(editComponentsWidth);
		grid.setWidth("1040px");
		grid.getColumn("unitsequencename").setWidth(260);
		grid.getColumn("unitsequencepositon").setWidth(260);
		grid.getColumn("productUnit").setWidth(260);
		grid.getColumn("workstation").setWidth(260);

		addComponent(mainLayout);
		mainLayout.addComponents(titleLabel, editButtonLayout, inputLayout, grid);
		editButtonLayout.addComponents(saveButton, updateButton, deleteButton);
		inputLayout.addComponents(productunitSelect, unitsequencepositonField, unitsequencenameField, workstationSelect);

		grid.asSingleSelect().addValueChangeListener( e -> {
			gridSelectEvent(e);
		});

		saveButton.setClickShortcut(KeyCode.ENTER);
		saveButton.addClickListener( e -> save());
		updateButton.addClickListener( e -> update());
		deleteButton.addClickListener( e -> delete());
		unitsequencepositonField.addValueChangeListener(e -> checkNumberFormat());
	}

	private void gridSelectEvent(ValueChangeEvent<UnitSequence> e) {
		if(e.getValue()!=null) {
			unitsequencenameField.setValue(e.getValue().getUnitsequencename());
			unitsequencepositonField.setValue(""+e.getValue().getUnitsequencepositon());
			productunitSelect.setValue(e.getValue().getProductUnit().getUnitName());
			workstationSelect.setValue(e.getValue().getWorkstation().getWorkstationName());
			selectedUnitSequenceId = e.getValue().getId();
		}
	}

	@PostConstruct
	private void init() {
		for(ProductUnit productUnit : productUnitService.findAll()) {
			productUnitNameList.add(productUnit.getUnitName());
		}
		for(Workstation workstation : workstationService.findAll()) {
			workstationNameList.add(workstation.getWorkstationName());
		}
		workstationSelect.setItems(workstationNameList);
		productunitSelect.setItems(productUnitNameList);
		upDateGrid();
	}
	
	private void upDateGrid() {
		List<UnitSequence> resultList = unitSequenceService.findAll();
		if(resultList!=null) {
			grid.setItems(resultList);
		}
	}

	private void save() {
		if(!checkEmptyFields()) {
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else if(checkNumberFormat()) {
			Notification.show("Only number!", Notification.Type.ERROR_MESSAGE);
		}else if(checkExistingSequence()) {
			Notification.show("Existing unitsequence!", Notification.Type.ERROR_MESSAGE);
		}else {
			unitSequenceService.save(new UnitSequence(unitsequencenameField.getValue(),
					Integer.parseInt(unitsequencepositonField.getValue()),
					productUnitService.findByUnitName(productunitSelect.getValue()),
					workstationService.findByWorkstationName(workstationSelect.getValue())));
		}
		upDateGrid();
	}
	
	private void update() {
		if(!checkEmptyFields()) {
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else if(checkNumberFormat()) {
			Notification.show("Only number!", Notification.Type.ERROR_MESSAGE);
		}else {
			unitSequenceService.save(new UnitSequence(selectedUnitSequenceId, unitsequencenameField.getValue(),
					Integer.parseInt(unitsequencepositonField.getValue()),
					productUnitService.findByUnitName(productunitSelect.getValue()),
					workstationService.findByWorkstationName(workstationSelect.getValue())));
		}
		upDateGrid();
	}
	
	private void delete() {
		UnitSequence tempUnitSequence = unitSequenceService.findById(selectedUnitSequenceId);
		if(productionHistoryService.findFirstByUnitSequence(tempUnitSequence)==null &&
				productionSequenceService.findFirstByUnitSequence(tempUnitSequence)==null) {
			unitSequenceService.delete(selectedUnitSequenceId);
		}else {
			Notification.show("Existing relationship!", Notification.Type.ERROR_MESSAGE);
		}
		upDateGrid();
	}

	private boolean checkEmptyFields() {
		if(unitsequencenameField.getValue().equals("") || unitsequencepositonField.getValue().equals("") || productunitSelect.getValue()==null || workstationSelect.getValue()==null) {
			return false;
		}
		return true;
	}

	private boolean checkExistingSequence() {
		if(unitSequenceService.findByProductUnitAndUnitsequencepositon(productUnitService.findByUnitName(productunitSelect.getValue()),
				Integer.parseInt(unitsequencepositonField.getValue()))!=null) {
			return true;
		}
		return false;
	}

	private boolean checkNumberFormat() {
		if(unitsequencepositonField.getValue().length()!=0) {
			try{
				Integer.parseInt(unitsequencepositonField.getValue());
				}
			catch(NumberFormatException e){
				Notification.show("Only number!", Notification.Type.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}
}
