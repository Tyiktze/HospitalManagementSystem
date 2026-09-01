package services;

import model.OperationResult;
import model.Patient;
import utils.IDCalculator;
import utils.InputValidator;

import java.util.ArrayList;

public class PatientService {
	private static final int MAX_PATIENTS = 100;
    private final ArrayList<Patient> patientList;
    private int nextId;

    public PatientService() {
        this.patientList = new ArrayList<>();
        initialiseData();
        this.nextId = IDCalculator.calculateNextId(patientList, Patient::getId);
    }

    private void initialiseData() {
        patientList.add(new Patient("P001", "Teoh Ah Kau", "Fever", "Male", "Admitted", 20));
        patientList.add(new Patient("P002", "Alice Smith", "Flu", "Female", "Discharged", 25));
        patientList.add(new Patient("P003", "Bob Lee", "Diabetes", "Male", "Admitted", 40));
        patientList.add(new Patient("P004", "Charlie Brown", "Fracture", "Male", "Admitted", 15));
        patientList.add(new Patient("P005", "Diana Prince", "Migraine", "Female", "Discharged", 30));
    }

    public String getPatientId() {
        return String.format("P%03d", nextId);
    }

    public ArrayList<Patient> getPatients() {
        return patientList;
    }

    public OperationResult<Void> addPatient(String id, String name, String disease, String sex, String admitStatus, String rawAge) {
    	if (patientList.size() >= MAX_PATIENTS) {
            return new OperationResult<>(false, "Cannot add patient. Patient capacity reached (Max 100).", null);
        }
        if (id == null || id.isBlank()) return new OperationResult<>(false, "Please enter a valid Patient ID", null);
        for (Patient p : patientList) {
            if (id.equalsIgnoreCase(p.getId())) {
                return new OperationResult<>(false, "Patient ID " + id + " already exists.", null);
            }
        }
        if (name == null || name.isBlank()) return new OperationResult<>(false, "Please enter a name", null);
        if (disease == null || disease.isBlank()) return new OperationResult<>(false, "Please enter a disease", null);
        if (sex == null || sex.isBlank() || sex.equalsIgnoreCase("Select Gender")) return new OperationResult<>(false, "Please enter sex", null);
        if (admitStatus == null || admitStatus.isBlank() || admitStatus.equalsIgnoreCase("Select Status")) return new OperationResult<>(false, "Please enter admit status", null);

        Integer age = InputValidator.parseInteger(rawAge);
        if (age == null) return new OperationResult<>(false, "Age must be a number.", null);
        if (!InputValidator.isNonNegative(age)) return new OperationResult<>(false, "Age cannot be negative.", null);

        Patient patient = new Patient(id, name, disease, sex, admitStatus, age);
        patientList.add(patient);
        nextId = Math.max(nextId + 1, IDCalculator.calculateNextId(patientList, Patient::getId));
        return new OperationResult<>(true, "Patient ID: " + id + " added.", null);
    }

    public OperationResult<Void> removePatient(Patient patient) {
        if (patient == null) return new OperationResult<>(false, "No Patient selected", null);
        if (patientList.remove(patient)) {
            return new OperationResult<>(true, "Patient " + patient.getId() + " deleted.", null);
        }
        return new OperationResult<>(false, "Patient no longer exists.", null);
    }

    public OperationResult<Void> updatePatient(Patient patient, String name, String disease, String sex, String admitStatus, String rawAge) {
        if (patient == null) return new OperationResult<>(false, "Patient not found.", null);

        Integer age = null;
        if (rawAge != null && !rawAge.isBlank()) {
            age = InputValidator.parseInteger(rawAge);
            if (age == null) return new OperationResult<>(false, "Age must be a number.", null);
            if (!InputValidator.isNonNegative(age)) return new OperationResult<>(false, "Age cannot be negative.", null);
        }

        if (name != null && !name.isBlank()) patient.setName(name);
        if (disease != null && !disease.isBlank()) patient.setDisease(disease);
        if (sex != null && !sex.isBlank() && !sex.equalsIgnoreCase("Select Gender")) patient.setSex(sex);
        if (admitStatus != null && !admitStatus.isBlank() && !admitStatus.equalsIgnoreCase("Select Status")) patient.setAdmitStatus(admitStatus);
        if (age != null) patient.setAge(age);

        return new OperationResult<>(true, "Patient " + patient.getId() + " updated.", null);
    }

    public OperationResult<Patient> findPatient(String id) {
        if (id == null || id.isBlank()) return new OperationResult<>(false, "Please enter an ID", null);
        for (Patient p : patientList) {
            if (id.equalsIgnoreCase(p.getId())) return new OperationResult<>(true, "Patient found", p);
        }
        return new OperationResult<>(false, "Patient not found", null);
    }

    public OperationResult<ArrayList<Patient>> searchPatient(String searchField) {
        ArrayList<Patient> result = new ArrayList<>();

        if (searchField == null || searchField.trim().isEmpty()) {
            result.addAll(patientList);
            return new OperationResult<>(true, "Search successful, " + result.size() + " entries found.", result);
        }

        ArrayList<String> query = utils.SearchParser.parseSearch(searchField);

        String id = null;
        String name = null;
        String disease = null;
        String sex = null;
        String admitStatus = null;
        Integer age = null;
        String generic = null;

        for (int i = 0; i < query.size(); i += 2) {
            String key = query.get(i);
            String value = query.get(i + 1);

            if (key.equalsIgnoreCase("ID")) {
                id = value;
            } else if (key.equalsIgnoreCase("NAME")) {
                name = value;
            } else if (key.equalsIgnoreCase("DISEASE")) {
                disease = value;
            } else if (key.equalsIgnoreCase("SEX") || key.equalsIgnoreCase("GENDER")) {
                sex = value;
            } else if (key.equalsIgnoreCase("ADMITSTATUS") || key.equalsIgnoreCase("STATUS")) {
                admitStatus = value;
            } else if (key.equalsIgnoreCase("AGE")) {
                age = InputValidator.parseInteger(value);
                if (age == null) {
                    return new OperationResult<>(false, "Age must be a number.", null);
                }
            } else if (key.equalsIgnoreCase("GENERIC")) {
                generic = value.toLowerCase();
            }
        }

        for (Patient p : patientList) {
            boolean match = true;

            if (generic != null && !generic.isBlank()) {
                boolean genericMatch = p.getId().toLowerCase().contains(generic)
                        || p.getName().toLowerCase().contains(generic)
                        || p.getDisease().toLowerCase().contains(generic)
                        || p.getSex().toLowerCase().contains(generic)
                        || p.getAdmitStatus().toLowerCase().contains(generic)
                        || String.valueOf(p.getAge()).contains(generic);
                if (!genericMatch) {
                    match = false;
                }
            }

            if (id != null && !id.isBlank() && !id.equalsIgnoreCase(p.getId())) {
                match = false;
            }
            if (name != null && !name.isBlank() && !p.getName().toLowerCase().contains(name.toLowerCase())) {
                match = false;
            }
            if (disease != null && !disease.isBlank() && !p.getDisease().toLowerCase().contains(disease.toLowerCase())) {
                match = false;
            }
            if (sex != null && !sex.isBlank() && !p.getSex().equalsIgnoreCase(sex)) {
                match = false;
            }
            if (admitStatus != null && !admitStatus.isBlank() && !p.getAdmitStatus().equalsIgnoreCase(admitStatus)) {
                match = false;
            }
            if (age != null && !age.equals(p.getAge())) {
                match = false;
            }
            
            if (match) {
                result.add(p);
            }
        }
        return new OperationResult<>(true, "Search successful, " + result.size() + " entries found.", result);
    }
}
