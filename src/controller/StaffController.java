package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

import application.AppContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.OperationResult;
import model.Staff;
import services.StaffService;


public class StaffController {

	//UI
    @FXML
    private VBox mainContent;
    @FXML
    private VBox welcome;
    @FXML
    private GridPane addStaff;
    @FXML
    private GridPane removeStaff;
    @FXML
    private GridPane updateStaff;
    @FXML
    private GridPane findStaff;
    @FXML
    private GridPane searchStaff;
    @FXML
    private VBox displayStaff;

    //Add fields
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField designationField;
    @FXML
    private ComboBox<String> sexField;
    @FXML
    private TextField salaryField;
    
    //Remove fields
    @FXML
    private TextField removeIdField;
    @FXML
    private Label staffRemoveDetails;
    @FXML
    private javafx.scene.control.Button btnConfirmRemoveStaff;
    
    //Update fields
    @FXML
    private TextField updateIdField;
    @FXML
    private Label staffUpdateDetails;
    @FXML
    private GridPane updateStaffInner;
    @FXML
    private TextField updateNameField;
    @FXML
    private TextField updateDesignationField;
    @FXML
    private ComboBox<String> updateSexField;
    @FXML
    private TextField updateSalaryField;

    //Find field
    @FXML
    private TextField findIdField;
    
    //Search field
    @FXML
    private TextField searchIdField;
    @FXML
    private TextField searchNameField;
    @FXML
    private TextField searchDesignationField;
    @FXML
    private ComboBox<String> searchSexField;
    @FXML
    private TextField searchSalaryField;
   
    //Table
    @FXML
    private TableView<Staff> staffTable;
    @FXML
    private TableColumn<Staff,String> colId;
    @FXML
    private TableColumn<Staff,String> colName;
    @FXML
    private TableColumn<Staff,String> colDesignation;
    @FXML
    private TableColumn<Staff,String> colSex;
    @FXML
    private TableColumn<Staff,Integer> colSalary;

    //Log
    @FXML
    private Label staffLog;
    
    private StaffService staffService;
    private Staff selectedStaff;


    @FXML
    public void initialize(){

        staffService = AppContext.getInstance().getStaffService();
        
        //Bind managed property to visibility to ensure the display works
        welcome.managedProperty().bind(welcome.visibleProperty());
        addStaff.managedProperty().bind(addStaff.visibleProperty());
        findStaff.managedProperty().bind(findStaff.visibleProperty());
        displayStaff.managedProperty().bind(displayStaff.visibleProperty());
        searchStaff.managedProperty().bind(searchStaff.visibleProperty());
        removeStaff.managedProperty().bind(removeStaff.visibleProperty());
        updateStaff.managedProperty().bind(updateStaff.visibleProperty());
        if (btnConfirmRemoveStaff != null) {
            btnConfirmRemoveStaff.managedProperty().bind(btnConfirmRemoveStaff.visibleProperty());
        }
        
        //Table mapping
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        
        staffTable.setItems(FXCollections.observableArrayList(staffService.getStaff()));
        
        sexField.setItems(FXCollections.observableArrayList("Male", "Female"));
        updateSexField.setItems(FXCollections.observableArrayList("Male", "Female"));
        searchSexField.setItems(FXCollections.observableArrayList("Male", "Female"));
    }
    
    //Hide all windows and allow one to show
    private void hideAll(){
        welcome.setVisible(false);
        addStaff.setVisible(false);
        findStaff.setVisible(false);
        searchStaff.setVisible(false);
        displayStaff.setVisible(false);
        removeStaff.setVisible(false);
        updateStaff.setVisible(false);
    }
    
    //The logic for buttons, etc are typically like this:
    //xxxClicked - When the Menu Button is clicked
    //btnxxxClicked - When there is a submit action
    //btnConfirmxxxClicked - Sometimes there will be situations where the user has to search, edit / delete and another confirm message will pop up

    //ADD STAFF
    @FXML
    public void addStaffClicked(ActionEvent event){
        hideAll();
        addStaff.setVisible(true);
        idField.setText(staffService.getStaffId());
        sexField.getSelectionModel().clearSelection();
    }

    @FXML
    public void btnAddStaffClicked(ActionEvent event){
    	OperationResult<Void> res = staffService.addStaff(
					    		    idField.getText(),
					    		    nameField.getText(),
					    		    designationField.getText(),
					    		    sexField.getValue(),
					    		    salaryField.getText());
    	
    	staffLog.setText(res.getMessage());
    	
    	if (res.isSuccess()) {
    		staffTable.setItems(FXCollections.observableArrayList(staffService.getStaff()));
    		
    		idField.setText(staffService.getStaffId());
            nameField.clear();
            designationField.clear();
            sexField.getSelectionModel().clearSelection();
            salaryField.clear();
    	}
    }
    
    //REMOVE STAFF
    @FXML
    public void removeStaffClicked(ActionEvent event){
    	hideAll();
    	removeStaff.setVisible(true);
    	if (btnConfirmRemoveStaff != null) {
    		btnConfirmRemoveStaff.setVisible(false);
    	}
    	staffRemoveDetails.setText("");
    	removeIdField.clear();
    	selectedStaff = null;
    }
    
    @FXML
    public void btnRemoveStaffClicked(ActionEvent event){
    	String id = removeIdField.getText();
    	OperationResult<Staff> res = staffService.findStaff(id);

        if(!res.isSuccess()){
        	staffRemoveDetails.setText(res.getMessage());
        	if (btnConfirmRemoveStaff != null) {
        		btnConfirmRemoveStaff.setVisible(false);
        	}
        	selectedStaff = null;
            return;
        }
        
        selectedStaff = res.getData();
        staffRemoveDetails.setText(selectedStaff.getStaffInfo());
        if (btnConfirmRemoveStaff != null) {
        	btnConfirmRemoveStaff.setVisible(true);
        }
    }
    
    @FXML
    public void btnConfirmRemoveStaffClicked(ActionEvent event){
    	if (selectedStaff == null) { return; }
    	
    	OperationResult<Void> res = staffService.removeStaff(selectedStaff);
    	staffLog.setText(res.getMessage());
    	if (res.isSuccess()) {
    		staffTable.setItems(FXCollections.observableArrayList(staffService.getStaff()));
    		staffRemoveDetails.setText("");
    		removeIdField.clear();
    		if (btnConfirmRemoveStaff != null) {
    			btnConfirmRemoveStaff.setVisible(false);
    		}
    		selectedStaff = null;
    	}
    }
    
    //Update Staff
    @FXML
    public void updateStaffClicked(ActionEvent event){
    	hideAll();
    	updateStaffInner.setVisible(false);
    	updateStaff.setVisible(true);
    	updateSexField.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void btnUpdateStaffClicked(ActionEvent event) {
    	String id = updateIdField.getText();
    	OperationResult<Staff> res = staffService.findStaff(id);

    	if(!res.isSuccess()){
    		staffUpdateDetails.setText(res.getMessage());
            return;
        }
        
        selectedStaff = res.getData();
        staffUpdateDetails.setText(selectedStaff.getStaffInfo());
        
        updateStaffInner.setVisible(true);
        
        updateNameField.setText(selectedStaff.getName());
        updateDesignationField.setText(selectedStaff.getDesignation());
        updateSexField.setValue(selectedStaff.getSex());
        updateSalaryField.setText(
            String.valueOf(selectedStaff.getSalary())
        );
    }
    
    @FXML
    public void btnConfirmUpdateStaffClicked(ActionEvent event) {
    	if (selectedStaff == null) { return; }
    	
    	String newName = updateNameField.getText();
    	String newDesignation = updateDesignationField.getText();
    	String newSex = updateSexField.getValue();
    	String rawSalary = updateSalaryField.getText();
    	
    	OperationResult<Void> res = staffService.updateStaff(selectedStaff, newName, newDesignation, newSex, rawSalary);
    	
    	staffLog.setText(res.getMessage());
    	
    	if(res.isSuccess()) {
    		staffTable.setItems(FXCollections.observableArrayList(staffService.getStaff()));
    		
    		updateIdField.clear();
        	updateNameField.clear();
            updateDesignationField.clear();
            updateSexField.getSelectionModel().clearSelection();
            updateSalaryField.clear();
            staffUpdateDetails.setText("");
            
            selectedStaff = null;
    	}     
    }
    
    
    //FIND STAFF
    @FXML
    public void findStaffClicked(ActionEvent event){
        hideAll();
        findStaff.setVisible(true);
    }

    @FXML
    public void btnFindStaffClicked(ActionEvent event){
        String id = findIdField.getText();
        OperationResult<Staff> res = staffService.findStaff(id);
        
        staffLog.setText(res.getMessage());
        
        if(!res.isSuccess()){
            return;
        }
        
        Staff staff = res.getData();

        
        findIdField.clear();
        staffTable.setItems(FXCollections.observableArrayList(staff));
        hideAll();
        displayStaff.setVisible(true);
    }

    //SEARCH STAFF
    @FXML
    public void searchStaffClicked(ActionEvent event) {
        hideAll();
        searchStaff.setVisible(true);
        searchSexField.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void btnSearchStaffClicked(ActionEvent event) {
    	String id = searchIdField.getText();
    	String name = searchNameField.getText();
    	String designation = searchDesignationField.getText();
    	String sex = searchSexField.getValue();
    	String rawSalary = searchSalaryField.getText();
    	
    	OperationResult<ArrayList<Staff>> res = staffService.searchStaff(id, name, designation, sex, rawSalary);
    	
    	staffLog.setText(res.getMessage());
    	
    	if (!res.isSuccess()) {
    		return;
    	}
    	
    	ArrayList<Staff> filteredList = res.getData();
    	
    	staffTable.setItems(
    			FXCollections.observableArrayList(filteredList)
        );
    	
    	searchIdField.clear();
    	searchNameField.clear();
    	searchDesignationField.clear();
    	searchSexField.getSelectionModel().clearSelection();
    	searchSalaryField.clear();
    	
    	hideAll();
        displayStaff.setVisible(true);
    }

    // DISPLAY ALL
    @FXML
    public void displayAllStaffClicked(ActionEvent event){
        hideAll();
        displayStaff.setVisible(true);
        staffTable.setItems(FXCollections.observableArrayList(staffService.getStaff()));
    }
}