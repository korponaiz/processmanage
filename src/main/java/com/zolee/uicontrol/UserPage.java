package com.zolee.uicontrol;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.ProductionHistory;
import com.zolee.domain.ProductionSequence;
import com.zolee.domain.Workstation;
import com.zolee.service.EmployeeService;
import com.zolee.service.ProductionHistoryService;
import com.zolee.service.ProductionSequenceService;
import com.zolee.service.UnitSequenceService;
import com.zolee.service.WorkstationService;

@SpringView(name = UserPage.NAME)
public class UserPage extends HorizontalLayout implements View{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "User";

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

	private UnitSequenceService unitSequenceService;
	
	@Autowired
	public void setUnitSequenceService(UnitSequenceService unitSequenceService) {
		this.unitSequenceService = unitSequenceService;
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

	private VerticalLayout mainButtonLayout;
	private VerticalLayout mainLayout;
	private HorizontalLayout controlButtonLayout;
	private HorizontalLayout selectedSequenceLayout;
	private Label currentUser;
	private TextField unitSequenceNameField;
	private TextField serialnumberField;
	private TextField statusField;
	private TextField descriptionField;
	private TextField nextWorkstationfield;
	private Button logoutButton;
	private Button homeButton;
	private Button settingsButton;
	private Button startButton;
	private Button pauseButton;
	private Button sendButton;
	private String componentWidth;
	private Long selectedProductionSequenceId;
	private Long selectedUnitSequenceSteps;
	private Grid<ProductionSequence> grid;
	private NativeSelect<Workstation> workstationSelect;
	private ProductionSequence  selectedProductionSequence;
	private ProductionHistory selectedProductionHistory;

	public UserPage() {
		
		mainLayout = new VerticalLayout();
		mainButtonLayout = new VerticalLayout();
		controlButtonLayout = new HorizontalLayout();
		selectedSequenceLayout = new HorizontalLayout();
		currentUser = new Label("" );
		unitSequenceNameField = new TextField();
		serialnumberField = new TextField();
		statusField = new TextField();
		descriptionField = new TextField();
		nextWorkstationfield = new TextField();
		settingsButton = new Button("Settings");
		logoutButton = new Button("Logout");
		homeButton= new Button("Home");
		startButton= new Button("Start");
		pauseButton= new Button("Pause");
		sendButton= new Button("Send");
		workstationSelect = new NativeSelect<>("Workstation:");
		grid = new Grid<>(ProductionSequence.class);
		grid.setColumns("unitSequence", "serialnumber", "status");
		grid.addColumn(ProductionSequence::getUnitSequence).setCaption("Workstation");
		componentWidth = "180px";
		grid.setWidth("1000px");
		grid.setHeight("800px");
		logoutButton.setWidth(componentWidth);
		settingsButton.setWidth(componentWidth);
		homeButton.setWidth(componentWidth);
		workstationSelect.setWidth(componentWidth);
		startButton.setWidth(componentWidth);
		pauseButton.setWidth(componentWidth);
		sendButton.setWidth(componentWidth);
		unitSequenceNameField.setWidth(componentWidth);
		serialnumberField.setWidth(componentWidth);
		statusField.setWidth(componentWidth);
		descriptionField.setWidth("300px");
		unitSequenceNameField.setEnabled(false);
		serialnumberField.setEnabled(false);
		statusField.setEnabled(false);
		nextWorkstationfield.setEnabled(false);
		controlButtonEnable("allfalse");
		currentUser.setValue("Current user : " + VaadinSession.getCurrent().getAttribute("employeename").toString());
		
		addComponents(mainButtonLayout, mainLayout);
		mainButtonLayout.addComponents(currentUser, logoutButton, homeButton, settingsButton, workstationSelect);
		mainLayout.addComponents(controlButtonLayout, selectedSequenceLayout, grid);
		controlButtonLayout.addComponents(startButton, pauseButton, sendButton, nextWorkstationfield);
		selectedSequenceLayout.addComponents(unitSequenceNameField, serialnumberField, statusField, descriptionField);
		
		grid.asSingleSelect().addValueChangeListener( e -> gridSelectEvent(e));
		workstationSelect.addSelectionListener(( e -> workstationSelectEvent(e)));
		settingsButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+UserSettingsPage.NAME));
		homeButton.addClickListener( e -> Page.getCurrent().setUriFragment("!"+UserPage.NAME));
		logoutButton.addClickListener( e -> logout());
		startButton.addClickListener( e -> startEvent());
		pauseButton.addClickListener( e -> pauseEvent());
		sendButton.addClickListener( e -> sendEvent());
	}

	private void startEvent() {
		ProductionHistory tempProductionHistory;
		ProductionSequence tempProductionSequence;
		tempProductionSequence = new ProductionSequence(selectedProductionSequenceId, Long.parseLong(serialnumberField.getValue()), "open",
				selectedProductionSequence.getActualHistoryId(), selectedProductionSequence.getUnitSequence(), selectedProductionSequence.getWorkstation());
		tempProductionHistory = new ProductionHistory(LocalDate.now(), LocalTime.now(), 
				LocalTime.of(22, 0), "start", descriptionField.getValue(), tempProductionSequence, 
				employeeService.findByUserName(VaadinSession.getCurrent().getAttribute("employeename").toString()), selectedProductionSequence.getUnitSequence());
		if(selectedProductionSequence.getUnitSequence().getUnitsequencepositon() == selectedUnitSequenceSteps) {
			nextWorkstationfield.setValue("Sequence end!");
		}else {
			nextWorkstationfield.setValue("to " + unitSequenceService.findByProductUnitAndUnitsequencepositon(selectedProductionSequence.getUnitSequence().getProductUnit(),
					(selectedProductionSequence.getUnitSequence().getUnitsequencepositon()+1)).getWorkstation());
		}
		controlButtonEnable("startfalse");
		productionSequenceService.save(tempProductionSequence);
		productionHistoryService.save(tempProductionHistory);
		selectedProductionHistory = tempProductionHistory;
		statusField.setValue("" + productionHistoryService.findById(tempProductionHistory.getId()).getActiontype());
		updateGrid();
	}

	private void pauseEvent() {
		ProductionHistory tempProductionHistory;
		ProductionSequence tempProductionSequence;
		int unitSequencePosition = 0;
		if(selectedProductionSequence.getUnitSequence().getUnitsequencepositon() == selectedUnitSequenceSteps) {
			unitSequencePosition = selectedProductionSequence.getUnitSequence().getUnitsequencepositon();
		}else {
			unitSequencePosition = selectedProductionSequence.getUnitSequence().getUnitsequencepositon() + 1;
		}
		tempProductionSequence = new ProductionSequence(selectedProductionSequenceId, Long.parseLong(serialnumberField.getValue()), "close",
				selectedProductionSequence.getActualHistoryId(), selectedProductionSequence.getUnitSequence(), selectedProductionSequence.getWorkstation());
		tempProductionHistory = new ProductionHistory(selectedProductionHistory.getId(), LocalDate.now(), selectedProductionHistory.getStartTime(), 
				LocalTime.now(), "pause", descriptionField.getValue(), tempProductionSequence, 
				employeeService.findByUserName(VaadinSession.getCurrent().getAttribute("employeename").toString()), selectedProductionSequence.getUnitSequence());
		nextWorkstationfield.setValue("to " + unitSequenceService.findByProductUnitAndUnitsequencepositon(tempProductionSequence.getUnitSequence().getProductUnit(),
				unitSequencePosition).getWorkstation());
		controlButtonEnable("starttrue");
		productionSequenceService.save(tempProductionSequence);
		productionHistoryService.save(tempProductionHistory);
		statusField.setValue("" + productionHistoryService.findById(tempProductionHistory.getId()).getActiontype());
		updateGrid();
	}

	private void sendEvent() {
		ProductionHistory tempProductionHistory;
		ProductionSequence tempProductionSequence;
		String status = "close";
		int unitSequencePosition = 0;
		if(selectedProductionSequence.getUnitSequence().getUnitsequencepositon() == selectedUnitSequenceSteps) {
			nextWorkstationfield.setValue("Sequence end!");
			status = "end";
			unitSequencePosition = selectedProductionSequence.getUnitSequence().getUnitsequencepositon();
		}else {
			nextWorkstationfield.setValue("to " + unitSequenceService.findByProductUnitAndUnitsequencepositon(selectedProductionSequence.getUnitSequence().getProductUnit(),
					(selectedProductionSequence.getUnitSequence().getUnitsequencepositon()+1)).getWorkstation());
			unitSequencePosition = selectedProductionSequence.getUnitSequence().getUnitsequencepositon() + 1;
		}
		tempProductionSequence = new ProductionSequence(selectedProductionSequenceId, Long.parseLong(serialnumberField.getValue()), status,
				selectedProductionSequence.getActualHistoryId(),
				unitSequenceService.findByProductUnitAndUnitsequencepositon(selectedProductionSequence.getUnitSequence().getProductUnit(),
						unitSequencePosition),
				unitSequenceService.findByProductUnitAndUnitsequencepositon(selectedProductionSequence.getUnitSequence().getProductUnit(),
						unitSequencePosition).getWorkstation());
		tempProductionHistory = new ProductionHistory(selectedProductionHistory.getId(), LocalDate.now(), selectedProductionHistory.getStartTime(), 
				LocalTime.now(), "start", descriptionField.getValue(), tempProductionSequence, 
				employeeService.findByUserName(VaadinSession.getCurrent().getAttribute("employeename").toString()), selectedProductionSequence.getUnitSequence());
		nextWorkstationfield.setValue("to " + unitSequenceService.findByProductUnitAndUnitsequencepositon(selectedProductionSequence.getUnitSequence().getProductUnit(),
				unitSequencePosition).getWorkstation());
		productionSequenceService.save(tempProductionSequence);
		productionHistoryService.save(tempProductionHistory);
		controlButtonEnable("allfalse");
		updateGrid();
	}

	private void gridSelectEvent(ValueChangeEvent<ProductionSequence> e) {
		if(e.getValue()!=null) {
			selectedProductionSequence = e.getValue();
			selectedUnitSequenceSteps = unitSequenceService.countByProductUnit(selectedProductionSequence.getUnitSequence().getProductUnit());
			selectedProductionSequenceId = e.getValue().getId();
			unitSequenceNameField.setValue(e.getValue().getUnitSequence().toString());
			serialnumberField.setValue("" + e.getValue().getSerialnumber());
			statusField.setValue(e.getValue().getStatus().toString());
			selectedProductionHistory = productionHistoryService.findById(selectedProductionSequenceId);
			if((e.getValue().getStatus().equals("start")||e.getValue().getStatus().equals("close"))) {
				controlButtonEnable("starttrue");
			}else if((e.getValue().getStatus().equals("open"))) {
				controlButtonEnable("startfalse");
			}else if(e.getValue().getStatus().equals("end")){
				controlButtonEnable("allfalse");
				nextWorkstationfield.setValue("Sequence end!");
			}
		}
	}

	private void workstationSelectEvent(SingleSelectionEvent<Workstation> e) {
		selectedProductionHistory = null;
		selectedProductionSequence = null;
		controlButtonEnable("allfalse");
		updateGrid();
	}

	@PostConstruct
	private void init() {
		workstationSelect.setItems(workstationService.findAll());
		updateGrid();
	}

	private void controlButtonEnable(String mode) {
		if(mode.equals("allfalse")) {
			startButton.setEnabled(false);
			pauseButton.setEnabled(false);
			sendButton.setEnabled(false);
		}else if (mode.equals("starttrue")) {
			startButton.setEnabled(true);
			pauseButton.setEnabled(false);
			sendButton.setEnabled(false);
		}else if(mode.equals("startfalse")) {
			startButton.setEnabled(false);
			pauseButton.setEnabled(true);
			sendButton.setEnabled(true);
		}
	}

	private void updateGrid() {
		if(workstationSelect.getValue()!=null) {
			grid.setItems(productionSequenceService.findByWorkstation(workstationSelect.getValue()));
		}else {
			grid.setItems(productionSequenceService.findAll());
		}
	}

	private void logout() {
		getUI().getNavigator().removeView(UserPage.NAME);
		getUI().getNavigator().removeView(UserSettingsPage.NAME);
		VaadinSession.getCurrent().setAttribute("employeename", null);
		Page.getCurrent().setUriFragment("");
	}

}
