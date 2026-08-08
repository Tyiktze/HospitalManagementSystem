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
import model.OperationResult;
import model.Patient;
import services.PatientService;

public class PatientController {

    @FXML private VBox mainContent;
    @FXML private VBox welcome;
    @FXML private GridPane addPatient;
    @FXML private GridPane removePatient;
    @FXML private GridPane updatePatient;
    @FXML private GridPane findPatient;
    @FXML private GridPane searchPatient;
    @FXML private VBox displayPatient;

    // Add Fields
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField diseaseField;
    @FXML private TextField sexField;
    @FXML private TextField admitStatusField;
    @FXML private TextField ageField; 

    // Remove Fields
    @FXML private TextField removeIdField;
    @FXML private Label patientRemoveDetails;

    // Update Fields
    @FXML private TextField updateIdField;
    @FXML private Label patientUpdateDetails;
    @FXML private GridPane updatePatientInner;
    @FXML private TextField updateNameField;
    @FXML private TextField updateDiseaseField;
    @FXML private TextField updateSexField;
    @FXML private TextField updateAdmitStatusField;
    @FXML private TextField updateAgeField; 

    // Find Field
    @FXML private TextField findIdField;

    // Search Fields
    @FXML private TextField searchIdField;
    @FXML private TextField searchNameField;
    @FXML private TextField searchDiseaseField;
    @FXML private TextField searchSexField;
    @FXML private TextField searchAdmitStatusField;
    @FXML private TextField searchAgeField; 

    // Table
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> colId;
    @FXML private TableColumn<Patient, String> colName;
    @FXML private TableColumn<Patient, String> colDisease;
    @FXML private TableColumn<Patient, String> colSex;
    @FXML private TableColumn<Patient, String> colAdmitStatus;
    @FXML private TableColumn<Patient, Integer> colAge; 

    // Log
    @FXML private Label patientLog;
    private PatientService patientService;
    private Patient selectedPatient;

    @FXML
    public void initialize() {
        patientService = AppContext.getInstance().getPatientService();
        welcome.managedProperty().bind(welcome.visibleProperty());
        addPatient.managedProperty().bind(addPatient.visibleProperty());
        findPatient.managedProperty().bind(findPatient.visibleProperty());
        displayPatient.managedProperty().bind(displayPatient.visibleProperty());
        searchPatient.managedProperty().bind(searchPatient.visibleProperty());
        removePatient.managedProperty().bind(removePatient.visibleProperty());
        updatePatient.managedProperty().bind(updatePatient.visibleProperty());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDisease.setCellValueFactory(new PropertyValueFactory<>("disease"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colAdmitStatus.setCellValueFactory(new PropertyValueFactory<>("admitStatus"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age")); 
    }

    private void hideAll() {
        welcome.setVisible(false);
        addPatient.setVisible(false);
        findPatient.setVisible(false);
        searchPatient.setVisible(false);
        displayPatient.setVisible(false);
        removePatient.setVisible(false);
        updatePatient.setVisible(false);
    }

    
    @FXML
    public void addPatientClicked(ActionEvent event) {
        hideAll();
        addPatient.setVisible(true);
        idField.setText(patientService.getPatientId());
    }

    @FXML
    public void btnAddPatientClicked(ActionEvent event) {
        OperationResult<Void> res = patientService.addPatient(
                idField.getText(),
                nameField.getText(),
                diseaseField.getText(),
                sexField.getText(),
                admitStatusField.getText(),
                ageField.getText()
        );
        patientLog.setText(res.getMessage());
        if (res.isSuccess()) {
            patientTable.refresh();
            idField.setText(patientService.getPatientId());
            nameField.clear();
            diseaseField.clear();
            sexField.clear();
            admitStatusField.clear();
            ageField.clear();
        }
    }

    @FXML
    public void removePatientClicked(ActionEvent event) {
        hideAll();
        removePatient.setVisible(true);
    }

    @FXML
    public void btnRemovePatientClicked(ActionEvent event) {
        String id = removeIdField.getText();
        OperationResult<Patient> res = patientService.findPatient(id);
        if (!res.isSuccess()) {
            patientRemoveDetails.setText(res.getMessage());
            return;
        }
        selectedPatient = res.getData();
        patientRemoveDetails.setText(selectedPatient.getPatientInfo());
    }

    @FXML
    public void btnConfirmRemovePatientClicked(ActionEvent event) {
        if (selectedPatient == null) return;
        OperationResult<Void> res = patientService.removePatient(selectedPatient);
        patientLog.setText(res.getMessage());
        if (res.isSuccess()) {
            patientTable.refresh();
            patientRemoveDetails.setText("");
            removeIdField.clear();
            selectedPatient = null;
        }
    }

    @FXML
    public void updatePatientClicked(ActionEvent event) {
        hideAll();
        updatePatientInner.setVisible(false);
        updatePatient.setVisible(true);
    }

    @FXML
    public void btnUpdatePatientClicked(ActionEvent event) {
        String id = updateIdField.getText();
        OperationResult<Patient> res = patientService.findPatient(id);
        if (!res.isSuccess()) {
            patientUpdateDetails.setText(res.getMessage());
            return;
        }
        selectedPatient = res.getData();
        patientUpdateDetails.setText(selectedPatient.getPatientInfo());
        
        updatePatientInner.setVisible(true);
        updateNameField.setText(selectedPatient.getName());
        updateDiseaseField.setText(selectedPatient.getDisease());
        updateSexField.setText(selectedPatient.getSex());
        updateAdmitStatusField.setText(selectedPatient.getAdmitStatus());
        updateAgeField.setText(String.valueOf(selectedPatient.getAge()));
    }

    @FXML
    public void btnConfirmUpdatePatientClicked(ActionEvent event) {
        if (selectedPatient == null) return;
        OperationResult<Void> res = patientService.updatePatient(
                selectedPatient,
                updateNameField.getText(),
                updateDiseaseField.getText(),
                updateSexField.getText(),
                updateAdmitStatusField.getText(),
                updateAgeField.getText()
        );
        patientLog.setText(res.getMessage());
        if (res.isSuccess()) {
            patientTable.refresh();
            updateIdField.clear();
            updateNameField.clear();
            updateDiseaseField.clear();
            updateSexField.clear();
            updateAdmitStatusField.clear();
            updateAgeField.clear();
            patientUpdateDetails.setText("");
            selectedPatient = null;
        }
    }

    @FXML
    public void findPatientClicked(ActionEvent event) {
        hideAll();
        findPatient.setVisible(true);
    }

    @FXML
    public void btnFindPatientClicked(ActionEvent event) {
        String id = findIdField.getText();
        OperationResult<Patient> res = patientService.findPatient(id);
        patientLog.setText(res.getMessage());
        if (!res.isSuccess()) return;
        findIdField.clear();
        patientTable.setItems(FXCollections.observableArrayList(res.getData()));
        hideAll();
        displayPatient.setVisible(true);
    }

    @FXML
    public void searchPatientClicked(ActionEvent event) {
        hideAll();
        searchPatient.setVisible(true);
    }

    @FXML
    public void btnSearchPatientClicked(ActionEvent event) {
        OperationResult<ArrayList<Patient>> res = patientService.searchPatient(
                searchIdField.getText(),
                searchNameField.getText(),
                searchDiseaseField.getText(),
                searchSexField.getText(),
                searchAdmitStatusField.getText(),
                searchAgeField.getText()
        );
        patientLog.setText(res.getMessage());
        if (!res.isSuccess()) return;
        patientTable.setItems(FXCollections.observableArrayList(res.getData()));
        searchIdField.clear();
        searchNameField.clear();
        searchDiseaseField.clear();
        searchSexField.clear();
        searchAdmitStatusField.clear();
        searchAgeField.clear();
        hideAll();
        displayPatient.setVisible(true);
    }

    @FXML
    public void displayAllPatientClicked(ActionEvent event) {
        hideAll();
        displayPatient.setVisible(true);
        patientTable.setItems(FXCollections.observableArrayList(patientService.getPatients()));
    }
}
