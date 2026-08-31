package controller;

import java.util.ArrayList;

import application.AppContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OperationResult;
import model.Staff;
import services.StaffService;

public class StaffController {

    @FXML
    private TableView<Staff> staffTable;

    @FXML
    private TableColumn<Staff, String> colId;

    @FXML
    private TableColumn<Staff, String> colName;

    @FXML
    private TableColumn<Staff, String> colDesignation;

    @FXML
    private TableColumn<Staff, String> colSex;

    @FXML
    private TableColumn<Staff, Integer> colSalary;

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

    @FXML
    private TextField searchField;

    @FXML
    private Label staffLog;

    private StaffService staffService;
    private Staff selectedStaff;

    @FXML
    public void initialize() {

        staffService = AppContext.getInstance().getStaffService();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        sexField.setItems(FXCollections.observableArrayList("Select Gender", "Male", "Female"));

        idField.setText(staffService.getStaffId());
        displayAllStaff();

        staffTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    if (newValue != null) {
                        selectedStaff = newValue;

                        idField.setText(newValue.getId());
                        nameField.setText(newValue.getName());
                        designationField.setText(newValue.getDesignation());
                        sexField.setValue(newValue.getSex());
                        salaryField.setText(String.valueOf(newValue.getSalary()));
                    }
                });
    }

    @FXML
    public void addStaffClicked(ActionEvent event) {

        OperationResult<Void> res = staffService.addStaff(
                idField.getText(),
                nameField.getText(),
                designationField.getText(),
                sexField.getValue(),
                salaryField.getText());

        staffLog.setText(res.getMessage());

        if (res.isSuccess()) {
            displayAllStaff();
            clearFields();
        }
    }

    @FXML
    public void updateStaffClicked(ActionEvent event) {

        if (selectedStaff == null) {
            staffLog.setText("Please select a staff member to update.");
            return;
        }

        OperationResult<Void> res = staffService.updateStaff(
                selectedStaff,
                nameField.getText(),
                designationField.getText(),
                sexField.getValue(),
                salaryField.getText());

        staffLog.setText(res.getMessage());

        if (res.isSuccess()) {
            staffTable.refresh();
            displayAllStaff();
            clearFields();
        }
    }

    @FXML
    public void removeStaffClicked(ActionEvent event) {

        if (selectedStaff == null) {
            staffLog.setText("Please select a staff member to remove.");
            return;
        }

        OperationResult<Void> res = staffService.removeStaff(selectedStaff);

        staffLog.setText(res.getMessage());

        if (res.isSuccess()) {
            displayAllStaff();
            clearFields();
        }
    }

    @FXML
    public void searchStaffClicked(ActionEvent event) {

        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            displayAllStaff();
            staffLog.setText("Displaying all staff.");
            return;
        }

        OperationResult<ArrayList<Staff>> result = staffService.searchStaff(keyword);
        staffLog.setText(result.getMessage());

        if (result.isSuccess()) {
            staffTable.setItems(FXCollections.observableArrayList(result.getData()));
        }
    }

    @FXML
    public void displayAllStaffClicked(ActionEvent event) {

        searchField.clear();
        displayAllStaff();
        staffLog.setText("Displaying all staff.");
    }

    @FXML
    public void clearClicked(ActionEvent event) {

        clearFields();
        staffLog.setText("Input fields cleared.");
    }

    private void displayAllStaff() {

        staffTable.setItems(
                FXCollections.observableArrayList(staffService.getStaff()));
    }

    private void clearFields() {

        idField.setText(staffService.getStaffId());
        nameField.clear();
        designationField.clear();
        sexField.getSelectionModel().clearSelection();
        sexField.setValue("Select Gender");
        salaryField.clear();

        selectedStaff = null;

        staffTable.getSelectionModel().clearSelection();
    }
}