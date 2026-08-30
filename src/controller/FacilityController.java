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
import model.Facility;
import model.OperationResult;
import services.FacilityService;

public class FacilityController {

    private static final FacilityService facilityService =
            new FacilityService();

    @FXML
    private TableView<Facility> facilityTable;

    @FXML
    private TableColumn<Facility, String> colFacility;

    @FXML
    private TextField facilityField;

    @FXML
    private TextField searchField;

    @FXML
    private Label facilityLog;

    private Facility selectedFacility;

    @FXML
    public void initialize() {

        colFacility.setCellValueFactory(
                new PropertyValueFactory<>("facility"));

        displayAllFacilities();

        facilityTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    if (newValue != null) {
                        selectedFacility = newValue;
                        facilityField.setText(
                                newValue.getFacility());
                    }
                });
    }

    @FXML
    public void addFacilityClicked(ActionEvent event) {

        String facility =
                facilityField.getText().trim();

        if (facility.isEmpty()) {
            facilityLog.setText(
                    "Please enter a facility name.");
            return;
        }

        Facility newFacility =
                new Facility(facility);

        OperationResult<Void> result =
                facilityService.add(newFacility);

        facilityLog.setText(result.getMessage());

        if (result.isSuccess()) {
            displayAllFacilities();
            clearFields();
        }
    }

    @FXML
    public void updateFacilityClicked(ActionEvent event) {

        if (selectedFacility == null) {
            facilityLog.setText(
                    "Please select a facility to update.");
            return;
        }

        String facility =
                facilityField.getText().trim();

        if (facility.isEmpty()) {
            facilityLog.setText(
                    "Please enter a facility name.");
            return;
        }

        selectedFacility.setFacility(facility);

        facilityTable.refresh();

        facilityLog.setText(
                "Facility updated successfully.");

        clearFields();
    }

    @FXML
    public void deleteFacilityClicked(ActionEvent event) {

        if (selectedFacility == null) {
            facilityLog.setText(
                    "Please select a facility to delete.");
            return;
        }

        OperationResult<Void> result =
                facilityService.delete(selectedFacility);

        facilityLog.setText(result.getMessage());

        if (result.isSuccess()) {
            displayAllFacilities();
            clearFields();
        }
    }

    @FXML
    public void searchFacilityClicked(ActionEvent event) {

        String keyword =
                searchField.getText()
                        .trim()
                        .toLowerCase();

        if (keyword.isEmpty()) {
            displayAllFacilities();

            facilityLog.setText(
                    "Displaying all facilities.");
            return;
        }

        List<Facility> result =
                facilityService.getAll()
                        .stream()
                        .filter(facility ->
                                facility.getFacility()
                                        .toLowerCase()
                                        .contains(keyword))
                        .toList();

        facilityTable.setItems(
                FXCollections.observableArrayList(result));

        if (result.isEmpty()) {
            facilityLog.setText(
                    "No matching facility found.");
        } else {
            facilityLog.setText(
                    result.size()
                    + " matching facility item(s) found.");
        }
    }

    @FXML
    public void displayAllClicked(ActionEvent event) {

        searchField.clear();

        displayAllFacilities();

        facilityLog.setText(
                "Displaying all facilities.");
    }

    @FXML
    public void clearClicked(ActionEvent event) {

        clearFields();

        facilityLog.setText(
                "Input fields cleared.");
    }

    private void displayAllFacilities() {

        facilityTable.setItems(
                FXCollections.observableArrayList(
                        facilityService.getAll()));
    }

    private void clearFields() {

        facilityField.clear();

        selectedFacility = null;

        facilityTable.getSelectionModel()
                .clearSelection();
    }
}