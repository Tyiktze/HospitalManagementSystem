package services;

import model.Lab;
import utils.InputValidator;
import utils.SearchParser;
import model.OperationResult;
import java.util.ArrayList;
import java.util.List;

public class LabService {
	private static final int MAX_LABS = 25;
    private List<Lab> labs = new ArrayList<>();
    //model
    public LabService() {
        labs.add(new Lab("Blood Test", 100));
        labs.add(new Lab("X-Ray", 250));
        labs.add(new Lab("MRI Scan", 1200));
        labs.add(new Lab("Urine Test", 80));
        labs.add(new Lab("CT Scan", 1500));
    }

    public List<Lab> getAll() {
        return new ArrayList<>(labs);
    }

    public OperationResult<Void> add(Lab lab) {
    	if (labs.size() >= MAX_LABS) {
            return new OperationResult<>(false, "Cannot add lab. Hospital capacity reached (Max 25).", null);
        }
        if (lab == null) {
            return new OperationResult<>(false, "Lab cannot be null.", null);
        }
        labs.add(lab);
        return new OperationResult<>(true, "Lab added successfully.", null);
    }

    public OperationResult<Void> delete(Lab lab) {
        if (lab == null || !labs.contains(lab)) {
            return new OperationResult<>(false, "Lab not found.", null);
        }
        labs.remove(lab);
        return new OperationResult<>(true, "Lab deleted successfully.", null);
    }
    public OperationResult<ArrayList<Lab>> searchLab(String searchField) {

        ArrayList<Lab> result = new ArrayList<>();

        if (searchField == null || searchField.trim().isEmpty()) {
            result.addAll(labs);
            return new OperationResult<>(
                    true,
                    "Search successful, " + result.size() + " entries found.",
                    result);
        }

        ArrayList<String> query = SearchParser.parseSearch(searchField);

        String lab = null;
        Integer cost = null;
        String generic = null;

        for (int i = 0; i < query.size(); i += 2) {
            String key = query.get(i);
            String value = query.get(i + 1);

            if (key.equalsIgnoreCase("LAB")) {
                lab = value;
            } else if (key.equalsIgnoreCase("COST")) {
                cost = InputValidator.parseInteger(value);

                if (cost == null) {
                    return new OperationResult<>(
                            false,
                            "Cost must be a number.",
                            null);
                }
            } else if (key.equalsIgnoreCase("GENERIC")) {
                generic = value.toLowerCase();
            }
        }

        for (Lab item : labs) {

            boolean match = true;

            if (generic != null && !generic.isBlank()) {
                boolean genericMatch =
                        item.getLab().toLowerCase().contains(generic)
                        || String.valueOf(item.getCost()).contains(generic);

                if (!genericMatch) {
                    match = false;
                }
            }

            if (lab != null && !lab.isBlank()
                    && !item.getLab().toLowerCase()
                            .contains(lab.toLowerCase())) {
                match = false;
            }

            if (cost != null && !cost.equals(item.getCost())) {
                match = false;
            }

            if (match) {
                result.add(item);
            }
        }

        return new OperationResult<>(
                true,
                "Search successful, " + result.size() + " entries found.",
                result);
    }
}