package services;

import model.Medical;
import model.OperationResult;
import java.util.ArrayList;
import java.util.List;

public class MedicalService {
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
        if (medical == null) {
            return new OperationResult<>(false, "Medical item cannot be null.", null);
        }
        medicals.add(medical);
        return new OperationResult<>(true, "Medical item added successfully.", null);
    }

    public OperationResult<Void> delete(Medical medical) {
        if (medical == null || !medicals.contains(medical)) {
            return new OperationResult<>(false, "Medical item not found.", null);
        }
        medicals.remove(medical);
        return new OperationResult<>(true, "Medical item deleted successfully.", null);
    }
}
