package services;

import model.Facility;
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
            return new OperationResult<>(false, "Cannot add Facility. Inventory capacity reached (Max 20).", null);
        }
        if (facility == null) {
            return new OperationResult<>(false, "Facility cannot be null.", null);
        }
        facilities.add(facility);
        return new OperationResult<>(true, "Facility added successfully.", null);
    }

    public OperationResult<Void> delete(Facility facility) {
        if (facility == null || !facilities.contains(facility)) {
            return new OperationResult<>(false, "Facility not found.", null);
        }
        facilities.remove(facility);
        return new OperationResult<>(true, "Facility deleted successfully.", null);
    }
}