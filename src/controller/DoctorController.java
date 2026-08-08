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
import model.Doctor;
import model.OperationResult;
import services.DoctorService;

public class DoctorController {

    @FXML private VBox mainContent;
    @FXML private VBox welcome;
    @FXML private GridPane addDoctor;
    @FXML private GridPane removeDoctor;
    @FXML private GridPane updateDoctor;
    @FXML private GridPane findDoctor;
    @FXML private GridPane searchDoctor;
    @FXML private VBox displayDoctor;

    // Add Fields
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField specialistField;
    @FXML private TextField workTimeField;
    @FXML private TextField qualificationField;

    // Remove Fields
    @FXML private TextField removeIdField;
    @FXML private Label doctorRemoveDetails;

    // Update Fields
    @FXML private TextField updateIdField;
    @FXML private Label doctorUpdateDetails;
    @FXML private GridPane updateDoctorInner;
    @FXML private TextField updateNameField;
    @FXML private TextField updateSpecialistField;
    @FXML private TextField updateWorkTimeField;
    @FXML private TextField updateQualificationField;

    // Find Field
    @FXML private TextField findIdField;

    // Search Fields
    @FXML private TextField searchIdField;
    @FXML private TextField searchNameField;
    @FXML private TextField searchSpecialistField;
    @FXML private TextField searchWorkTimeField;
    @FXML private TextField searchQualificationField;

    // Table
    @FXML private TableView<Doctor> doctorTable;
    @FXML private TableColumn<Doctor, String> colId;
    @FXML private TableColumn<Doctor, String> colName;
    @FXML private TableColumn<Doctor, String> colSpecialist;
    @FXML private TableColumn<Doctor, String> colWorkTime;
    @FXML private TableColumn<Doctor, String> colQualification;

    // Log
    @FXML private Label doctorLog;

    private DoctorService doctorService;
    private Doctor selectedDoctor;

    @FXML
    public void initialize() {
        doctorService = AppContext.getInstance().getDoctorService();

        welcome.managedProperty().bind(welcome.visibleProperty());
        addDoctor.managedProperty().bind(addDoctor.visibleProperty());
        findDoctor.managedProperty().bind(findDoctor.visibleProperty());
        displayDoctor.managedProperty().bind(displayDoctor.visibleProperty());
        searchDoctor.managedProperty().bind(searchDoctor.visibleProperty());
        removeDoctor.managedProperty().bind(removeDoctor.visibleProperty());
        updateDoctor.managedProperty().bind(updateDoctor.visibleProperty());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSpecialist.setCellValueFactory(new PropertyValueFactory<>("specialist"));
        colWorkTime.setCellValueFactory(new PropertyValueFactory<>("workTime"));
        colQualification.setCellValueFactory(new PropertyValueFactory<>("qualification"));
    }

    private void hideAll() {
        welcome.setVisible(false);
        addDoctor.setVisible(false);
        findDoctor.setVisible(false);
        searchDoctor.setVisible(false);
        displayDoctor.setVisible(false);
        removeDoctor.setVisible(false);
        updateDoctor.setVisible(false);
    }

    @FXML
    public void addDoctorClicked(ActionEvent event) {
        hideAll();
        addDoctor.setVisible(true);
        idField.setText(doctorService.getDoctorId());
    }

    @FXML
    public void btnAddDoctorClicked(ActionEvent event) {
        OperationResult<Void> res = doctorService.addDoctor(
                idField.getText(),
                nameField.getText(),
                specialistField.getText(),
                workTimeField.getText(),
                qualificationField.getText()
        );

        doctorLog.setText(res.getMessage());

        if (res.isSuccess()) {
            doctorTable.refresh();
            idField.setText(doctorService.getDoctorId());
            nameField.clear();
            specialistField.clear();
            workTimeField.clear();
            qualificationField.clear();
        }
    }

    @FXML
    public void removeDoctorClicked(ActionEvent event) {
        hideAll();
        removeDoctor.setVisible(true);
    }

    @FXML
    public void btnRemoveDoctorClicked(ActionEvent event) {
        String id = removeIdField.getText();
        OperationResult<Doctor> res = doctorService.findDoctor(id);

        if (!res.isSuccess()) {
            doctorRemoveDetails.setText(res.getMessage());
            return;
        }

        selectedDoctor = res.getData();
        doctorRemoveDetails.setText(selectedDoctor.getDoctorInfo());
    }

    @FXML
    public void btnConfirmRemoveDoctorClicked(ActionEvent event) {
        if (selectedDoctor == null) return;

        OperationResult<Void> res = doctorService.removeDoctor(selectedDoctor);
        doctorLog.setText(res.getMessage());
        if (res.isSuccess()) {
            doctorTable.refresh();
            doctorRemoveDetails.setText("");
            removeIdField.clear();
            selectedDoctor = null;
        }
    }

    @FXML
    public void updateDoctorClicked(ActionEvent event) {
        hideAll();
        updateDoctorInner.setVisible(false);
        updateDoctor.setVisible(true);
    }

    @FXML
    public void btnUpdateDoctorClicked(ActionEvent event) {
        String id = updateIdField.getText();
        OperationResult<Doctor> res = doctorService.findDoctor(id);

        if (!res.isSuccess()) {
            doctorUpdateDetails.setText(res.getMessage());
            return;
        }

        selectedDoctor = res.getData();
        doctorUpdateDetails.setText(selectedDoctor.getDoctorInfo());

        updateDoctorInner.setVisible(true);

        updateNameField.setText(selectedDoctor.getName());
        updateSpecialistField.setText(selectedDoctor.getSpecialist());
        updateWorkTimeField.setText(selectedDoctor.getWorkTime());
        updateQualificationField.setText(selectedDoctor.getQualification());
    }

    @FXML
    public void btnConfirmUpdateDoctorClicked(ActionEvent event) {
        if (selectedDoctor == null) return;

        OperationResult<Void> res = doctorService.updateDoctor(
                selectedDoctor,
                updateNameField.getText(),
                updateSpecialistField.getText(),
                updateWorkTimeField.getText(),
                updateQualificationField.getText()
        );

        doctorLog.setText(res.getMessage());

        if (res.isSuccess()) {
            doctorTable.refresh();
            updateIdField.clear();
            updateNameField.clear();
            updateSpecialistField.clear();
            updateWorkTimeField.clear();
            updateQualificationField.clear();
            doctorUpdateDetails.setText("");
            selectedDoctor = null;
        }
    }

    @FXML
    public void findDoctorClicked(ActionEvent event) {
        hideAll();
        findDoctor.setVisible(true);
    }

    @FXML
    public void btnFindDoctorClicked(ActionEvent event) {
        String id = findIdField.getText();
        OperationResult<Doctor> res = doctorService.findDoctor(id);

        doctorLog.setText(res.getMessage());

        if (!res.isSuccess()) return;

        findIdField.clear();
        doctorTable.setItems(FXCollections.observableArrayList(res.getData()));
        hideAll();
        displayDoctor.setVisible(true);
    }

    @FXML
    public void searchDoctorClicked(ActionEvent event) {
        hideAll();
        searchDoctor.setVisible(true);
    }

    @FXML
    public void btnSearchDoctorClicked(ActionEvent event) {
        OperationResult<ArrayList<Doctor>> res = doctorService.searchDoctor(
                searchIdField.getText(),
                searchNameField.getText(),
                searchSpecialistField.getText(),
                searchWorkTimeField.getText(),
                searchQualificationField.getText()
        );

        doctorLog.setText(res.getMessage());

        if (!res.isSuccess()) return;

        doctorTable.setItems(FXCollections.observableArrayList(res.getData()));

        searchIdField.clear();
        searchNameField.clear();
        searchSpecialistField.clear();
        searchWorkTimeField.clear();
        searchQualificationField.clear();

        hideAll();
        displayDoctor.setVisible(true);
    }

    @FXML
    public void displayAllDoctorClicked(ActionEvent event) {
        hideAll();
        displayDoctor.setVisible(true);
        doctorTable.setItems(FXCollections.observableArrayList(doctorService.getDoctors()));
    }
}