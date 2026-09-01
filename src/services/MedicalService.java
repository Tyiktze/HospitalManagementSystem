package services;

import model.Medical;
import utils.InputValidator;
import utils.SearchParser;
import model.OperationResult;
import java.util.ArrayList;
import java.util.List;

public class MedicalService {
	private static final int MAX_MEDICAL = 100;
	private List<Medical> medicals = new ArrayList<>();
    //model
    public MedicalService() {
        medicals.add(new Medical("Paracetamol", "Pfizer", "12/2025", 10, 100));
        medicals.add(new Medical("Ibuprofen", "GSK", "06/2025", 15, 200));
        medicals.add(new Medical("Cough Syrup", "Johnson & Johnson", "09/2024", 25, 50));
        medicals.add(new Medical("Antibiotic", "Novartis", "03/2026", 30, 5));
        medicals.add(new Medical("Vitamin C", "Bayer", "01/2025", 5, 10));
    }

    public List<Medical> getAll() {
        return new ArrayList<>(medicals);
    }

    public OperationResult<Void> add(Medical medical) {

        if (medicals.size() >= MAX_MEDICAL) {
            return new OperationResult<>(
                    false,
                    "Cannot add medicine. Inventory capacity reached (Max 100).",
                    null);
        }

        if (medical == null) {
            return new OperationResult<>(
                    false,
                    "Medical item cannot be null.",
                    null);
        }

        for (Medical existing : medicals) {
            if (existing.getName().equalsIgnoreCase(medical.getName())
                    && existing.getManufacturer().equalsIgnoreCase(medical.getManufacturer())) {

                return new OperationResult<>(
                        false,
                        "Duplicate medical entry.",
                        null);
            }
        }

        medicals.add(medical);

        return new OperationResult<>(
                true,
                "Medical item added successfully.",
                null);
    }
    public OperationResult<Void> delete(Medical medical) {
        if (medical == null || !medicals.contains(medical)) {
            return new OperationResult<>(false, "Medical item not found.", null);
        }
        medicals.remove(medical);
        return new OperationResult<>(true, "Medical item deleted successfully.", null);
    }
    public OperationResult<ArrayList<Medical>> searchMedical(String searchField) {

        ArrayList<Medical> result = new ArrayList<>();

        if (searchField == null || searchField.trim().isEmpty()) {
            result.addAll(medicals);
            return new OperationResult<>(
                    true,
                    "Search successful, " + result.size() + " entries found.",
                    result);
        }

        ArrayList<String> query = SearchParser.parseSearch(searchField);

        String name = null;
        String manufacturer = null;
        String expiryDate = null;
        Integer cost = null;
        Integer count = null;
        String generic = null;

        for (int i = 0; i < query.size(); i += 2) {
            String key = query.get(i);
            String value = query.get(i + 1);

            if (key.equalsIgnoreCase("NAME")) {
                name = value;
            } else if (key.equalsIgnoreCase("MANUFACTURER")) {
                manufacturer = value;
            } else if (key.equalsIgnoreCase("EXPIRYDATE")) {
                expiryDate = value;
            } else if (key.equalsIgnoreCase("COST")) {
                cost = InputValidator.parseInteger(value);

                if (cost == null) {
                    return new OperationResult<>(
                            false,
                            "Cost must be a number.",
                            null);
                }
            } else if (key.equalsIgnoreCase("COUNT")) {
                count = InputValidator.parseInteger(value);

                if (count == null) {
                    return new OperationResult<>(
                            false,
                            "Count must be a number.",
                            null);
                }
            } else if (key.equalsIgnoreCase("GENERIC")) {
                generic = value.toLowerCase();
            }
        }

        for (Medical medical : medicals) {

            boolean match = true;

            if (generic != null && !generic.isBlank()) {
                boolean genericMatch =
                        medical.getName().toLowerCase().contains(generic)
                        || medical.getManufacturer().toLowerCase().contains(generic)
                        || medical.getExpiryDate().toLowerCase().contains(generic)
                        || String.valueOf(medical.getCost()).contains(generic)
                        || String.valueOf(medical.getCount()).contains(generic);

                if (!genericMatch) {
                    match = false;
                }
            }

            if (name != null && !name.isBlank()
                    && !medical.getName().toLowerCase().contains(name.toLowerCase())) {
                match = false;
            }

            if (manufacturer != null && !manufacturer.isBlank()
                    && !medical.getManufacturer().toLowerCase()
                            .contains(manufacturer.toLowerCase())) {
                match = false;
            }

            if (expiryDate != null && !expiryDate.isBlank()
                    && !medical.getExpiryDate().equalsIgnoreCase(expiryDate)) {
                match = false;
            }

            if (cost != null && !cost.equals(medical.getCost())) {
                match = false;
            }

            if (count != null && !count.equals(medical.getCount())) {
                match = false;
            }

            if (match) {
                result.add(medical);
            }
        }

        return new OperationResult<>(
                true,
                "Search successful, " + result.size() + " entries found.",
                result);
    }
}
