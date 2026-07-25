package controller;

import javafx.fxml.FXML;
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
    private TextField sexField;
    @FXML
    private TextField salaryField;
    
    //Remove fields
    @FXML
    private TextField removeIdField;
    @FXML
    private Label staffRemoveDetails;
    
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
    private TextField updateSexField;
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
    private TextField searchSexField;
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
        
        welcome.managedProperty().bind(welcome.visibleProperty());
        addStaff.managedProperty().bind(addStaff.visibleProperty());
        findStaff.managedProperty().bind(findStaff.visibleProperty());
        displayStaff.managedProperty().bind(displayStaff.visibleProperty());
        searchStaff.managedProperty().bind(searchStaff.visibleProperty());
        removeStaff.managedProperty().bind(removeStaff.visibleProperty());
        updateStaff.managedProperty().bind(updateStaff.visibleProperty());
        
        //Table mapping
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void hideAll(){
        welcome.setVisible(false);
        addStaff.setVisible(false);
        findStaff.setVisible(false);
        searchStaff.setVisible(false);
        displayStaff.setVisible(false);
        removeStaff.setVisible(false);
        updateStaff.setVisible(false);
    }




    // ADD STAFF
    @FXML
    public void addStaffClicked(ActionEvent event){
        hideAll();
        addStaff.setVisible(true);
    }

    @FXML
    public void btnAddStaffClicked(ActionEvent event){
    	String newId = idField.getText();
    	String newName = nameField.getText();
    	String newDesignation = designationField.getText();
    	String newSex = sexField.getText();
    	int newSalary = Integer.parseInt(salaryField.getText());
    	
        staffService.addStaff(newId, newName, newDesignation , newSex, newSalary);

        idField.clear();
        nameField.clear();
        designationField.clear();
        sexField.clear();
        salaryField.clear();
        
        staffLog.setText("Staff ID: " + newId + " added.");
    }
    
    //REMOVE STAFF
    @FXML
    public void removeStaffClicked(ActionEvent event){
    	hideAll();
    	removeStaff.setVisible(true);
    }
    
    @FXML
    public void btnRemoveStaffClicked(ActionEvent event){
    	String id = removeIdField.getText();
    	selectedStaff = staffService.findStaff(id);

        if(selectedStaff == null){
        	staffRemoveDetails.setText("Staff not found");
            return;
        }

        staffRemoveDetails.setText(
        		"ID: " + selectedStaff.getId()
                + "\nName: " + selectedStaff.getName()
                + "\nDesignation: " + selectedStaff.getDesignation()
                + "\nSex: " + selectedStaff.getSex()
                + "\nSalary: " + selectedStaff.getSalary());
    }
    
    @FXML
    public void btnConformRemoveStaffClicked(ActionEvent event){
    	if (selectedStaff == null) {
    		return;
    	}
    	
    	staffService.removeStaff(selectedStaff.getId());
    	staffRemoveDetails.setText("");
    	staffLog.setText("Staff " + selectedStaff.getId() + " deleted. This action cannot be undone.");
        removeIdField.clear();

        selectedStaff = null;
    }
    
    //Update Staff
    @FXML
    public void updateStaffClicked(ActionEvent event){
    	hideAll();
    	updateStaffInner.setVisible(false);
    	updateStaff.setVisible(true);
    }
    
    @FXML
    public void btnUpdateStaffClicked(ActionEvent event) {
    	String id = updateIdField.getText();
    	selectedStaff = staffService.findStaff(id);

        if(selectedStaff == null){
        	staffUpdateDetails.setText("Staff not found");
            return;
        }

        staffUpdateDetails.setText(
        		"ID: " + selectedStaff.getId()
                + "\nName: " + selectedStaff.getName()
                + "\nDesignation: " + selectedStaff.getDesignation()
                + "\nSex: " + selectedStaff.getSex()
                + "\nSalary: " + selectedStaff.getSalary());
        
        updateStaffInner.setVisible(true);
        
        updateNameField.setText(selectedStaff.getName());
        updateDesignationField.setText(selectedStaff.getDesignation());
        updateSexField.setText(selectedStaff.getSex());
        updateSalaryField.setText(
            String.valueOf(selectedStaff.getSalary())
        );
    }
    
    @FXML
    public void btnConfirmUpdateStaffClicked(ActionEvent event) {
    	if (selectedStaff == null) { return; }
    	
    	String newName = updateNameField.getText();
    	String newDesignation = updateDesignationField.getText();
    	String newSex = updateSexField.getText();
    	int newSalary = Integer.parseInt(updateSalaryField.getText());
    	
    	staffService.updateStaff(selectedStaff, newName, newDesignation, newSex, newSalary);
    	
    	updateIdField.clear();
    	updateNameField.clear();
        updateDesignationField.clear();
        updateSexField.clear();
        updateSalaryField.clear();
        staffUpdateDetails.setText("");
        staffLog.setText("Staff " + selectedStaff.getId() + " fields has been updated.");
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
        Staff staff = staffService.findStaff(id);

        if(staff == null){
        	staffLog.setText("Staff " + id + " not found.");
            return;
        }
        
        findIdField.clear();
        staffTable.setItems(FXCollections.observableArrayList(staff));
        staffLog.setText("Staff " + id + " found.");
        hideAll();
        displayStaff.setVisible(true);
    }

    //SEARCH STAFF
    @FXML
    public void searchStaffClicked(ActionEvent event) {
        hideAll();
        searchStaff.setVisible(true);
    }
    
    @FXML
    public void btnSearchStaffClicked(ActionEvent event) {
    	String id = searchIdField.getText();
    	String name = searchNameField.getText();
    	String designation = searchDesignationField.getText();
    	String sex = searchSexField.getText();
    	
    	int salary = 0;

        if (!searchSalaryField.getText().isEmpty()) {
            salary = Integer.parseInt(searchSalaryField.getText());
        }
    	
    	Staff sample = new Staff(id, name, designation, sex, salary);
    	
    	ArrayList<Staff> filteredList = staffService.searchStaff(sample);
    	
    	staffTable.setItems(
    			FXCollections.observableArrayList(filteredList)
        );
    	
    	searchIdField.clear();
    	searchNameField.clear();
    	searchDesignationField.clear();
    	searchSexField.clear();
    	searchSalaryField.clear();
    	staffLog.setText("Search successful, " + filteredList.size() + " entries found.");
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