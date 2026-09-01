package controller;

import java.util.ArrayList;

import application.AppContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Doctor;
import model.OperationResult;
import services.DoctorService;
import utils.SearchParser;

public class DoctorController {

    @FXML private TextField searchTextField;

    @FXML private TableView<Doctor> doctorTable;
    @FXML private TableColumn<Doctor, String> idCol;
    @FXML private TableColumn<Doctor, String> nameCol;
    @FXML private TableColumn<Doctor, String> specialistCol;
    @FXML private TableColumn<Doctor, String> timingCol;
    @FXML private TableColumn<Doctor, String> qualificationCol;
    @FXML private TableColumn<Doctor, Integer> roomCol;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField specialistField;
    @FXML private TextField timingField;
    @FXML private TextField qualificationField;
    @FXML private TextField roomField;

    @FXML private Label doctorLog;

    private DoctorService doctorService;
    private Doctor selectedDoctor;

    @FXML
    public void initialize() {
        doctorService = AppContext.getInstance().getDoctorService();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        specialistCol.setCellValueFactory(new PropertyValueFactory<>("specialist"));
        timingCol.setCellValueFactory(new PropertyValueFactory<>("workTime"));
        qualificationCol.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));

        idField.setText(doctorService.getDoctorId());
        displayAllDoctors();

        doctorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedDoctor = newVal;
                idField.setText(newVal.getId());
                nameField.setText(newVal.getName());
                specialistField.setText(newVal.getSpecialist());
                timingField.setText(newVal.getWorkTime());
                qualificationField.setText(newVal.getQualification());
                roomField.setText(String.valueOf(newVal.getRoom()));
            }
        });
    }

    @FXML
    public void handleAdd(ActionEvent event) {
        OperationResult<Void> res = doctorService.addDoctor(
                idField.getText(),
                nameField.getText(),
                specialistField.getText(),
                timingField.getText(),
                qualificationField.getText(),
                roomField.getText()
        );
        doctorLog.setText(res.getMessage());
        if (res.isSuccess()) {
            displayAllDoctors();
            clearFields();
        }
    }

    @FXML
    public void handleUpdate(ActionEvent event) {
        if (selectedDoctor == null) {
            doctorLog.setText("Please select a doctor to update.");
            return;
        }
        OperationResult<Void> res = doctorService.updateDoctor(
                selectedDoctor,
                nameField.getText(),
                specialistField.getText(),
                timingField.getText(),
                qualificationField.getText(),
                roomField.getText()
        );
        doctorLog.setText(res.getMessage());
        if (res.isSuccess()) {
            doctorTable.refresh();
            displayAllDoctors();
            clearFields();
        }
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        if (selectedDoctor == null) {
            doctorLog.setText("Please select a doctor to delete.");
            return;
        }
        OperationResult<Void> res = doctorService.removeDoctor(selectedDoctor);
        doctorLog.setText(res.getMessage());
        if (res.isSuccess()) {
            displayAllDoctors();
            clearFields();
        }
    }

    @FXML
    public void handleSearch(ActionEvent event) {
        String keyword = searchTextField.getText() == null ? "" : searchTextField.getText().trim();
        if (keyword.isEmpty()) {
            displayAllDoctors();
            doctorLog.setText("Displaying all doctors.");
            return;
        }
        ArrayList<Doctor> result = new ArrayList<>();
        for (Doctor d : doctorService.getDoctors()) {
            if (d.getId().toLowerCase().contains(keyword.toLowerCase())
                    || d.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(d);
            }
        }
        doctorTable.setItems(FXCollections.observableArrayList(result));
        doctorLog.setText("Search successful, " + result.size() + " entries found.");
    }

    @FXML
    public void handleRefresh(ActionEvent event) {
        searchTextField.clear();
        displayAllDoctors();
        doctorLog.setText("Displaying all doctors.");
    }

    @FXML
    public void handleClear(ActionEvent event) {
        clearFields();
        doctorLog.setText("Input fields cleared.");
    }

    private void displayAllDoctors() {
        doctorTable.setItems(FXCollections.observableArrayList(doctorService.getDoctors()));
    }

    private void clearFields() {
        idField.setText(doctorService.getDoctorId());
        nameField.clear();
        specialistField.clear();
        timingField.clear();
        qualificationField.clear();
        roomField.clear();
        selectedDoctor = null;
        doctorTable.getSelectionModel().clearSelection();
    }
}
