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
import model.Patient;
import services.PatientService;

public class PatientController {

    @FXML private TextField searchTextField;

    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> idCol;
    @FXML private TableColumn<Patient, String> nameCol;
    @FXML private TableColumn<Patient, String> diseaseCol;
    @FXML private TableColumn<Patient, String> genderCol;
    @FXML private TableColumn<Patient, String> admitStatusCol;
    @FXML private TableColumn<Patient, Integer> ageCol;

    @FXML private TextField patientIdTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField diseaseTextField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private ComboBox<String> admitStatusComboBox;
    @FXML private TextField ageTextField;

    @FXML private Label patientLog;

    private PatientService patientService;
    private Patient selectedPatient;

    @FXML
    public void initialize() {
        patientService = AppContext.getInstance().getPatientService();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        diseaseCol.setCellValueFactory(new PropertyValueFactory<>("disease"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        admitStatusCol.setCellValueFactory(new PropertyValueFactory<>("admitStatus"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        admitStatusComboBox.setItems(FXCollections.observableArrayList("Admitted", "Discharged"));

        patientIdTextField.setText(patientService.getPatientId());
        displayAllPatients();

        patientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedPatient = newVal;
                patientIdTextField.setText(newVal.getId());
                nameTextField.setText(newVal.getName());
                diseaseTextField.setText(newVal.getDisease());
                genderComboBox.setValue(newVal.getSex());
                admitStatusComboBox.setValue(newVal.getAdmitStatus());
                ageTextField.setText(String.valueOf(newVal.getAge()));
            }
        });
    }

    @FXML
    public void handleAdd(ActionEvent event) {
        OperationResult<Void> res = patientService.addPatient(
                patientIdTextField.getText(),
                nameTextField.getText(),
                diseaseTextField.getText(),
                genderComboBox.getValue(),
                admitStatusComboBox.getValue(),
                ageTextField.getText()
        );
        patientLog.setText(res.getMessage());
        if (res.isSuccess()) {
            displayAllPatients();
            clearFields();
        }
    }

    @FXML
    public void handleUpdate(ActionEvent event) {
        if (selectedPatient == null) {
            patientLog.setText("Please select a patient to update.");
            return;
        }
        OperationResult<Void> res = patientService.updatePatient(
                selectedPatient,
                nameTextField.getText(),
                diseaseTextField.getText(),
                genderComboBox.getValue(),
                admitStatusComboBox.getValue(),
                ageTextField.getText()
        );
        patientLog.setText(res.getMessage());
        if (res.isSuccess()) {
            patientTable.refresh();
            displayAllPatients();
            clearFields();
        }
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        if (selectedPatient == null) {
            patientLog.setText("Please select a patient to delete.");
            return;
        }
        OperationResult<Void> res = patientService.removePatient(selectedPatient);
        patientLog.setText(res.getMessage());
        if (res.isSuccess()) {
            displayAllPatients();
            clearFields();
        }
    }

    @FXML
    public void handleSearch(ActionEvent event) {
        String keyword = searchTextField.getText() == null ? "" : searchTextField.getText().trim();
        if (keyword.isEmpty()) {
            displayAllPatients();
            patientLog.setText("Displaying all patients.");
            return;
        }
        ArrayList<Patient> result = new ArrayList<>();
        for (Patient p : patientService.getPatients()) {
            if (p.getId().toLowerCase().contains(keyword.toLowerCase())
                    || p.getName().toLowerCase().contains(keyword.toLowerCase())
                    || p.getDisease().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        patientTable.setItems(FXCollections.observableArrayList(result));
        patientLog.setText("Search successful, " + result.size() + " entries found.");
    }

    @FXML
    public void handleRefresh(ActionEvent event) {
        searchTextField.clear();
        displayAllPatients();
        patientLog.setText("Displaying all patients.");
    }

    @FXML
    public void handleClear(ActionEvent event) {
        clearFields();
        patientLog.setText("Input fields cleared.");
    }

    private void displayAllPatients() {
        patientTable.setItems(FXCollections.observableArrayList(patientService.getPatients()));
    }

    private void clearFields() {
        patientIdTextField.setText(patientService.getPatientId());
        nameTextField.clear();
        diseaseTextField.clear();
        genderComboBox.setValue(null);
        admitStatusComboBox.setValue(null);
        ageTextField.clear();
        selectedPatient = null;
        patientTable.getSelectionModel().clearSelection();
    }
}
