package com.zolee.uicontrol;

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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.Employee;
import com.zolee.service.EmployeeService;

@SpringView(name = EditEmployeesPage.NAME)
public class EditEmployeesPage extends AdminPagePattern{

	private static final long serialVersionUID = 1L;
	public static final String NAME = "EditEmployees";

	private EmployeeService employeeService;
	
	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	private VerticalLayout mainLayout;
	private HorizontalLayout inputLayout;
	private HorizontalLayout editButtonLayout;
	private Label titleLabel;
	private TextField userNameField;
	private TextField realNameField;
	private PasswordField passwordField;
	private NativeSelect<String> rightSelect;
	private Button saveButton;
	private Button updateButton;
	private Button deleteButton;
	private Grid<Employee> grid;
	private long selectedEmployeId;
	private String editComponentsWidth;

	public EditEmployeesPage() {
		
		mainLayout = new VerticalLayout();
		inputLayout = new HorizontalLayout();
		editButtonLayout = new HorizontalLayout();
		titleLabel = new Label("Edit employees");
		saveButton = new Button("Save");
		updateButton = new Button("Update");
		deleteButton = new Button("Delete");
		userNameField = new TextField("User Name:");
		realNameField = new TextField("Real Name:");
		passwordField = new PasswordField("Password:");
		rightSelect = new NativeSelect<>("User right");
		rightSelect.setItems("admin", "user");
		rightSelect.setValue("user");
		grid = new Grid<>(Employee.class);
		grid.setColumns("userName", "realName", "right");
		editComponentsWidth = "250px";

		userNameField.setWidth(editComponentsWidth);
		realNameField.setWidth(editComponentsWidth);
		passwordField.setWidth(editComponentsWidth);
		rightSelect.setWidth(editComponentsWidth);
		saveButton.setWidth(editComponentsWidth);
		updateButton.setWidth(editComponentsWidth);
		deleteButton.setWidth(editComponentsWidth);
		grid.setWidth("780px");
		grid.getColumn("userName").setWidth(260);
		grid.getColumn("realName").setWidth(260);
		grid.getColumn("right").setWidth(260);

		addComponent(mainLayout);
		mainLayout.addComponents(titleLabel, editButtonLayout, inputLayout, grid);
		editButtonLayout.addComponents(saveButton, updateButton, deleteButton);
		inputLayout.addComponents(userNameField, realNameField, rightSelect, passwordField);


		grid.asSingleSelect().addValueChangeListener( e -> {
			gridSelectEvent(e);
		});
		
		saveButton.setClickShortcut(KeyCode.ENTER);
		saveButton.addClickListener( e -> save());
		deleteButton.addClickListener( e -> delete());
		updateButton.addClickListener( e -> update());
	}

	private void gridSelectEvent(ValueChangeEvent<Employee> e) {
		if(e.getValue()!=null) {
			userNameField.setValue(e.getValue().getUserName());
			realNameField.setValue(e.getValue().getRealName());
			rightSelect.setValue(e.getValue().getRight());
			passwordField.setValue(e.getValue().getPassword());
			selectedEmployeId = e.getValue().getId();
		}
	}
	
	@PostConstruct
	private void init() {
		upDateGrid();
	}
	
	private void upDateGrid() {
		grid.setItems(employeeService.findAll());
	}
	
	private void save() {
		if(!checkEmptyFields()){
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else if(employeeService.findByUserName(userNameField.getValue())!=null) {
			Notification.show("Existing user!", Notification.Type.ERROR_MESSAGE);
		}else {
			employeeService.save(new Employee(userNameField.getValue(), realNameField.getValue(),
					passwordField.getValue(), rightSelect.getValue()));
		}
		upDateGrid();
	}
	
	private void update() {
		if(!checkEmptyFields()){
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else {
			employeeService.save(new Employee(selectedEmployeId, userNameField.getValue(), realNameField.getValue(),
					passwordField.getValue(), rightSelect.getValue()));
		}
		upDateGrid();
	}

	private void delete() {
		if(!checkEmptyFields()){
			Notification.show("Empty field!", Notification.Type.ERROR_MESSAGE);
		}else {
			employeeService.save(new Employee(selectedEmployeId, userNameField.getValue(), realNameField.getValue(),
					passwordField.getValue(), "deleted"));
		}
		upDateGrid();
	}
	
	private boolean checkEmptyFields() {
		if(userNameField.getValue().equals("") || realNameField.getValue().equals("") || rightSelect.getValue()==null || passwordField.getValue().equals("")) {
			return false;
		}
		return true;
	}
}
