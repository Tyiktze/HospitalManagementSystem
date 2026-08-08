package services;

import model.OperationResult;
import model.Patient;

import java.util.ArrayList;

public class PatientService {
    private final ArrayList<Patient> patientList;
    private int nextId;

    public PatientService() {
        this.patientList = new ArrayList<>();
        initialiseData();
        nextId = patientList.size() + 1;
    }

    private void initialiseData() {
        // 第一条数据使用个人姓名与 ID（例如：P001 / BEH JIE SHEN）
        patientList.add(new Patient("P001", "BEH JIE SHEN", "Fever", "Male", "Admitted"));
        patientList.add(new Patient("P002", "Alice Smith", "Flu", "Female", "Discharged"));
        patientList.add(new Patient("P003", "Bob Lee", "Diabetes", "Male", "Admitted"));
        patientList.add(new Patient("P004", "Charlie Brown", "Fracture", "Male", "Admitted"));
        patientList.add(new Patient("P005", "Diana Prince", "Migraine", "Female", "Discharged"));
    }

    public String getPatientId() {
        return String.format("P%03d", nextId);
    }

    public ArrayList<Patient> getPatients() {
        return patientList;
    }

    public OperationResult<Void> addPatient(String id, String name, String disease, String sex, String admitStatus) {
        if (name == null || name.isBlank()) return new OperationResult<>(false, "Please enter a name", null);
        if (disease == null || disease.isBlank()) return new OperationResult<>(false, "Please enter a disease", null);
        if (sex == null || sex.isBlank()) return new OperationResult<>(false, "Please enter sex", null);
        if (admitStatus == null || admitStatus.isBlank()) return new OperationResult<>(false, "Please enter admit status", null);

        Patient patient = new Patient(id, name, disease, sex, admitStatus);
        patientList.add(patient);

        nextId++;
        return new OperationResult<>(true, "Patient ID: " + id + " added.", null);
    }

    public OperationResult<Void> removePatient(Patient patient) {
        if (patient == null) return new OperationResult<>(false, "No Patient selected", null);

        if (patientList.remove(patient)) {
            return new OperationResult<>(true, "Patient " + patient.getId() + " deleted.", null);
        }

        return new OperationResult<>(false, "Patient no longer exists.", null);
    }

    public OperationResult<Void> updatePatient(Patient patient, String name, String disease, String sex, String admitStatus) {
        if (patient == null) return new OperationResult<>(false, "Patient not found.", null);

        if (name != null && !name.isBlank()) patient.setName(name);
        if (disease != null && !disease.isBlank()) patient.setDisease(disease);
        if (sex != null && !sex.isBlank()) patient.setSex(sex);
        if (admitStatus != null && !admitStatus.isBlank()) patient.setAdmitStatus(admitStatus);

        return new OperationResult<>(true, "Patient " + patient.getId() + " updated.", null);
    }

    public OperationResult<Patient> findPatient(String id) {
        if (id == null || id.isBlank()) return new OperationResult<>(false, "Please enter an ID", null);

        for (Patient p : patientList) {
            if (id.equalsIgnoreCase(p.getId())) return new OperationResult<>(true, "Patient found", p);
        }

        return new OperationResult<>(false, "Patient not found", null);
    }

    public OperationResult<ArrayList<Patient>> searchPatient(String id, String name, String disease, String sex, String admitStatus) {
        ArrayList<Patient> result = new ArrayList<>();

        for (Patient p : patientList) {
            boolean match = true;

            if (id != null && !id.isBlank() && !id.equalsIgnoreCase(p.getId())) match = false;
            if (name != null && !name.isBlank() && !p.getName().toLowerCase().contains(name.toLowerCase())) match = false;
            if (disease != null && !disease.isBlank() && !p.getDisease().toLowerCase().contains(disease.toLowerCase())) match = false;
            if (sex != null && !sex.isBlank() && !sex.equalsIgnoreCase(p.getSex())) match = false;
            if (admitStatus != null && !admitStatus.isBlank() && !p.getAdmitStatus().toLowerCase().contains(admitStatus.toLowerCase())) match = false;

            if (match) result.add(p);
        }

        return new OperationResult<>(true, "Search successful, " + result.size() + " entries found.", result);
    }
}