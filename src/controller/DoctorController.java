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
    @FXML private Button searchBtn;
    @FXML private Button displayAllBtn;

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

    @FXML private Button addBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;
    @FXML private Button clearBtn;

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
    ArrayList<String> parsed = SearchParser.parseSearch(searchTextField.getText());

    if (parsed.isEmpty()) {
        handleDisplayAll(event);
        return;
    }

    ArrayList<Doctor> result = new ArrayList<>(doctorService.getDoctors());

    for (int i = 0; i < parsed.size() - 1; i += 2) {
        String key = parsed.get(i).toLowerCase();
        String value = parsed.get(i + 1).toLowerCase();

        ArrayList<Doctor> filtered = new ArrayList<>();
        for (Doctor d : result) {
            boolean match = switch (key) {
                case "id" -> d.getId().toLowerCase().contains(value);
                case "name" -> d.getName().toLowerCase().contains(value);
                case "specialist" -> d.getSpecialist().toLowerCase().contains(value);
                case "timing", "worktime" -> d.getWorkTime().toLowerCase().contains(value);
                case "qualification" -> d.getQualification().toLowerCase().contains(value);
                case "room" -> String.valueOf(d.getRoom()).equals(value);
                case "generic" -> d.getId().toLowerCase().contains(value)
                        || d.getName().toLowerCase().contains(value);
                default -> true;
            };
            if (match) filtered.add(d);
        }
        result = filtered;
    }

    doctorTable.setItems(FXCollections.observableArrayList(result));
    doctorLog.setText("Search successful, " + result.size() + " entries found.");
}

    @FXML
    public void handleDisplayAll(ActionEvent event) {
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
