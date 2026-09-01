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
import model.Lab;
import model.OperationResult;
import services.LabService;

public class LabController {

    private LabService labService;

    @FXML
    private TableView<Lab> labTable;

    @FXML
    private TableColumn<Lab, String> colLab;

    @FXML
    private TableColumn<Lab, Integer> colCost;

    @FXML
    private TextField labField;

    @FXML
    private TextField costField;

    @FXML
    private TextField searchField;

    @FXML
    private Label labLog;

    private Lab selectedLab;

    @FXML
    public void initialize() {

        labService = AppContext.getInstance().getLabService();

        colLab.setCellValueFactory(
                new PropertyValueFactory<>("lab"));

        colCost.setCellValueFactory(
                new PropertyValueFactory<>("cost"));

        displayAllLabs();

        labTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    if (newValue != null) {
                        selectedLab = newValue;

                        labField.setText(newValue.getLab());
                        costField.setText(
                                String.valueOf(newValue.getCost()));
                    }
                });
    }

    @FXML
    public void addLabClicked(ActionEvent event) {

        String lab = labField.getText().trim();

        if (lab.isEmpty()
                || costField.getText().trim().isEmpty()) {

            labLog.setText("Please fill in all laboratory information.");
            return;
        }

        try {

            int cost = Integer.parseInt(
                    costField.getText().trim());

            if (cost < 0) {
                labLog.setText("Cost cannot be negative.");
                return;
            }

            Lab newLab = new Lab(lab, cost);

            OperationResult<Void> result =
                    labService.add(newLab);

            labLog.setText(result.getMessage());

            if (result.isSuccess()) {
                displayAllLabs();
                clearFields();
            }

        } catch (NumberFormatException e) {

            labLog.setText("Cost must be a whole number.");
        }
    }

    @FXML
    public void updateLabClicked(ActionEvent event) {

        if (selectedLab == null) {
            labLog.setText(
                    "Please select a laboratory item to update.");
            return;
        }

        String lab = labField.getText().trim();

        if (lab.isEmpty()
                || costField.getText().trim().isEmpty()) {

            labLog.setText(
                    "Please fill in all laboratory information.");
            return;
        }

        try {

            int cost = Integer.parseInt(
                    costField.getText().trim());

            if (cost < 0) {
                labLog.setText("Cost cannot be negative.");
                return;
            }

            selectedLab.setLab(lab);
            selectedLab.setCost(cost);

            labTable.refresh();

            labLog.setText(
                    "Laboratory item updated successfully.");

            clearFields();

        } catch (NumberFormatException e) {

            labLog.setText("Cost must be a whole number.");
        }
    }

    @FXML
    public void deleteLabClicked(ActionEvent event) {

        if (selectedLab == null) {
            labLog.setText(
                    "Please select a laboratory item to delete.");
            return;
        }

        OperationResult<Void> result =
                labService.delete(selectedLab);

        labLog.setText(result.getMessage());

        if (result.isSuccess()) {
            displayAllLabs();
            clearFields();
        }
    }

    @FXML
    public void searchLabClicked(ActionEvent event) {

        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            displayAllLabs();
            labLog.setText("Displaying all laboratory items.");
            return;
        }

        OperationResult<ArrayList<Lab>> result =
                labService.searchLab(keyword);

        labLog.setText(result.getMessage());

        if (result.isSuccess()) {
            labTable.setItems(
                    FXCollections.observableArrayList(result.getData()));
        }
    }
    
    @FXML
    public void displayAllClicked(ActionEvent event) {

        searchField.clear();
        displayAllLabs();

        labLog.setText(
                "Displaying all laboratory items.");
    }

    @FXML
    public void clearClicked(ActionEvent event) {

        clearFields();
        labLog.setText("Input fields cleared.");
    }

    private void displayAllLabs() {

        labTable.setItems(
                FXCollections.observableArrayList(
                        labService.getAll()));
    }

    private void clearFields() {

        labField.clear();
        costField.clear();

        selectedLab = null;

        labTable
                .getSelectionModel()
                .clearSelection();
    }
}