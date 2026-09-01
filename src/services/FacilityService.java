package services;

import model.Facility;
import utils.SearchParser;
import model.OperationResult;
import java.util.ArrayList;
import java.util.List;

public class FacilityService {
	private static final int MAX_FACILITY = 20;
    private List<Facility> facilities = new ArrayList<>();
    //model
    public FacilityService() {
        facilities.add(new Facility("Cafeteria"));
        facilities.add(new Facility("Parking Area"));
        facilities.add(new Facility("Wheelchair Access"));
        facilities.add(new Facility("Pharmacy"));
        facilities.add(new Facility("Waiting Lounge"));
    }

    public List<Facility> getAll() {
        return new ArrayList<>(facilities);
    }

    public OperationResult<Void> add(Facility facility) {

        if (facilities.size() >= MAX_FACILITY) {
            return new OperationResult<>(
                    false,
                    "Cannot add Facility. Inventory capacity reached (Max 20).",
                    null);
        }

        if (facility == null) {
            return new OperationResult<>(
                    false,
                    "Facility cannot be null.",
                    null);
        }

        for (Facility existing : facilities) {
            if (existing.getFacility().equalsIgnoreCase(facility.getFacility())) {
                return new OperationResult<>(
                        false,
                        "Duplicate facility entry.",
                        null);
            }
        }

        facilities.add(facility);

        return new OperationResult<>(
                true,
                "Facility added successfully.",
                null);
    }

    public OperationResult<Void> delete(Facility facility) {
        if (facility == null || !facilities.contains(facility)) {
            return new OperationResult<>(false, "Facility not found.", null);
        }
        facilities.remove(facility);
        return new OperationResult<>(true, "Facility deleted successfully.", null);
    }
    public OperationResult<ArrayList<Facility>> searchFacility(String searchField) {

        ArrayList<Facility> result = new ArrayList<>();

        if (searchField == null || searchField.trim().isEmpty()) {
            result.addAll(facilities);
            return new OperationResult<>(
                    true,
                    "Search successful, " + result.size() + " entries found.",
                    result);
        }

        ArrayList<String> query = SearchParser.parseSearch(searchField);

        String facility = null;
        String generic = null;

        for (int i = 0; i < query.size(); i += 2) {
            String key = query.get(i);
            String value = query.get(i + 1);

            if (key.equalsIgnoreCase("FACILITY")) {
                facility = value;
            } else if (key.equalsIgnoreCase("GENERIC")) {
                generic = value.toLowerCase();
            }
        }

        for (Facility item : facilities) {

            boolean match = true;

            if (generic != null && !generic.isBlank()
                    && !item.getFacility().toLowerCase().contains(generic)) {
                match = false;
            }

            if (facility != null && !facility.isBlank()
                    && !item.getFacility().toLowerCase()
                            .contains(facility.toLowerCase())) {
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