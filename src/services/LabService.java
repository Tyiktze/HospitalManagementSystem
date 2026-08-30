package services;

import model.Lab;
import model.OperationResult;
import java.util.ArrayList;
import java.util.List;

public class LabService {
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
}