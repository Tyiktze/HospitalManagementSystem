package controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Medical;
import model.OperationResult;
import services.MedicalService;

public class MedicalController {

    private static final MedicalService medicalService = new MedicalService();

    @FXML
    private TableView<Medical> medicalTable;

    @FXML
    private TableColumn<Medical, String> colName;

    @FXML
    private TableColumn<Medical, String> colManufacturer;

    @FXML
    private TableColumn<Medical, String> colExpiryDate;

    @FXML
    private TableColumn<Medical, Integer> colCost;

    @FXML
    private TableColumn<Medical, Integer> colCount;

    @FXML
    private TextField nameField;

    @FXML
    private TextField manufacturerField;

    @FXML
    private TextField expiryDateField;

    @FXML
    private TextField costField;

    @FXML
    private TextField countField;

    @FXML
    private TextField searchField;

    @FXML
    private Label medicalLog;

    private Medical selectedMedical;

    @FXML
    public void initialize() {

        colName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        colManufacturer.setCellValueFactory(
                new PropertyValueFactory<>("manufacturer"));

        colExpiryDate.setCellValueFactory(
                new PropertyValueFactory<>("expiryDate"));

        colCost.setCellValueFactory(
                new PropertyValueFactory<>("cost"));

        colCount.setCellValueFactory(
                new PropertyValueFactory<>("count"));

        displayAllMedicals();

        medicalTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    if (newValue != null) {
                        selectedMedical = newValue;

                        nameField.setText(newValue.getName());
                        manufacturerField.setText(newValue.getManufacturer());
                        expiryDateField.setText(newValue.getExpiryDate());
                        costField.setText(String.valueOf(newValue.getCost()));
                        countField.setText(String.valueOf(newValue.getCount()));
                    }
                });
    }

    @FXML
    public void addMedicalClicked(ActionEvent event) {

        String name = nameField.getText().trim();
        String manufacturer = manufacturerField.getText().trim();
        String expiryDate = expiryDateField.getText().trim();

        if (name.isEmpty()
                || manufacturer.isEmpty()
                || expiryDate.isEmpty()
                || costField.getText().trim().isEmpty()
                || countField.getText().trim().isEmpty()) {

            medicalLog.setText("Please fill in all medical information.");
            return;
        }

        try {

            int cost = Integer.parseInt(costField.getText().trim());
            int count = Integer.parseInt(countField.getText().trim());

            if (cost < 0 || count < 0) {
                medicalLog.setText("Cost and count cannot be negative.");
                return;
            }

            Medical medical = new Medical(
                    name,
                    manufacturer,
                    expiryDate,
                    cost,
                    count);

            OperationResult<Void> result =
                    medicalService.add(medical);

            medicalLog.setText(result.getMessage());

            if (result.isSuccess()) {
                displayAllMedicals();
                clearFields();
            }

        } catch (NumberFormatException e) {

            medicalLog.setText(
                    "Cost and count must be whole numbers.");
        }
    }

    @FXML
    public void updateMedicalClicked(ActionEvent event) {

        if (selectedMedical == null) {
            medicalLog.setText(
                    "Please select a medical item to update.");
            return;
        }

        String name = nameField.getText().trim();
        String manufacturer = manufacturerField.getText().trim();
        String expiryDate = expiryDateField.getText().trim();

        if (name.isEmpty()
                || manufacturer.isEmpty()
                || expiryDate.isEmpty()
                || costField.getText().trim().isEmpty()
                || countField.getText().trim().isEmpty()) {

            medicalLog.setText(
                    "Please fill in all medical information.");
            return;
        }

        try {

            int cost = Integer.parseInt(costField.getText().trim());
            int count = Integer.parseInt(countField.getText().trim());

            if (cost < 0 || count < 0) {
                medicalLog.setText(
                        "Cost and count cannot be negative.");
                return;
            }

            selectedMedical.setName(name);
            selectedMedical.setManufacturer(manufacturer);
            selectedMedical.setExpiryDate(expiryDate);
            selectedMedical.setCost(cost);
            selectedMedical.setCount(count);

            medicalTable.refresh();

            medicalLog.setText(
                    "Medical item updated successfully.");

            clearFields();

        } catch (NumberFormatException e) {

            medicalLog.setText(
                    "Cost and count must be whole numbers.");
        }
    }

    @FXML
    public void deleteMedicalClicked(ActionEvent event) {

        if (selectedMedical == null) {
            medicalLog.setText(
                    "Please select a medical item to delete.");
            return;
        }

        OperationResult<Void> result =
                medicalService.delete(selectedMedical);

        medicalLog.setText(result.getMessage());

        if (result.isSuccess()) {
            displayAllMedicals();
            clearFields();
        }
    }

    @FXML
    public void searchMedicalClicked(ActionEvent event) {

        String keyword =
                searchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            displayAllMedicals();
            medicalLog.setText(
                    "Displaying all medical items.");
            return;
        }

        List<Medical> result =
                medicalService.getAll()
                        .stream()
                        .filter(medical ->
                                medical.getName()
                                        .toLowerCase()
                                        .contains(keyword)
                                ||
                                medical.getManufacturer()
                                        .toLowerCase()
                                        .contains(keyword))
                        .toList();

        medicalTable.setItems(
                FXCollections.observableArrayList(result));

        if (result.isEmpty()) {
            medicalLog.setText(
                    "No matching medical item found.");
        } else {
            medicalLog.setText(
                    result.size()
                    + " matching medical item(s) found.");
        }
    }

    @FXML
    public void displayAllClicked(ActionEvent event) {

        searchField.clear();
        displayAllMedicals();

        medicalLog.setText(
                "Displaying all medical items.");
    }

    @FXML
    public void clearClicked(ActionEvent event) {

        clearFields();
        medicalLog.setText(
                "Input fields cleared.");
    }

    private void displayAllMedicals() {

        medicalTable.setItems(
                FXCollections.observableArrayList(
                        medicalService.getAll()));
    }

    private void clearFields() {

        nameField.clear();
        manufacturerField.clear();
        expiryDateField.clear();
        costField.clear();
        countField.clear();

        selectedMedical = null;

        medicalTable
                .getSelectionModel()
                .clearSelection();
    }
}