package services;

import model.Doctor;
import model.OperationResult;

import java.util.ArrayList;

public class DoctorService {
    private final ArrayList<Doctor> doctorList;
    private int nextId;

    public DoctorService() {
        this.doctorList = new ArrayList<>();
        initialiseData();
        nextId = doctorList.size() + 1;
    }

    private void initialiseData() {
        // 第一条数据使用个人姓名与 ID（例如：D001 / BEH JIE SHEN）
        doctorList.add(new Doctor("D001", "Dr. BEH JIE SHEN", "Surgeon", "8-11AM", "MBBS, MD"));
        doctorList.add(new Doctor("D002", "Dr. Tom Wong", "Surgeon", "8-11AM", "MBBS, MD"));
        doctorList.add(new Doctor("D003", "Dr. John Lim", "Physician", "10-3PM", "MBBS, MS"));
        doctorList.add(new Doctor("D004", "Dr. Amy Chia", "Surgeon", "7-11AM", "MBBS, MD"));
        doctorList.add(new Doctor("D005", "Dr. Sarah Tan", "Dermatologist", "2-6PM", "MBBS"));
    }

    public String getDoctorId() {
        return String.format("D%03d", nextId);
    }

    public ArrayList<Doctor> getDoctors() {
        return doctorList;
    }

    public OperationResult<Void> addDoctor(String id, String name, String specialist, String workTime, String qualification) {
        if (name == null || name.isBlank()) return new OperationResult<>(false, "Please enter a name", null);
        if (specialist == null || specialist.isBlank()) return new OperationResult<>(false, "Please enter a specialist", null);
        if (workTime == null || workTime.isBlank()) return new OperationResult<>(false, "Please enter work time", null);
        if (qualification == null || qualification.isBlank()) return new OperationResult<>(false, "Please enter qualification", null);

        Doctor doctor = new Doctor(id, name, specialist, workTime, qualification);
        doctorList.add(doctor);

        nextId++;
        return new OperationResult<>(true, "Doctor ID: " + id + " added.", null);
    }

    public OperationResult<Void> removeDoctor(Doctor doctor) {
        if (doctor == null) return new OperationResult<>(false, "No Doctor selected", null);

        if (doctorList.remove(doctor)) {
            return new OperationResult<>(true, "Doctor " + doctor.getId() + " deleted.", null);
        }

        return new OperationResult<>(false, "Doctor no longer exists.", null);
    }

    public OperationResult<Void> updateDoctor(Doctor doctor, String name, String specialist, String workTime, String qualification) {
        if (doctor == null) return new OperationResult<>(false, "Doctor not found.", null);

        if (name != null && !name.isBlank()) doctor.setName(name);
        if (specialist != null && !specialist.isBlank()) doctor.setSpecialist(specialist);
        if (workTime != null && !workTime.isBlank()) doctor.setWorkTime(workTime);
        if (qualification != null && !qualification.isBlank()) doctor.setQualification(qualification);

        return new OperationResult<>(true, "Doctor " + doctor.getId() + " updated.", null);
    }

    public OperationResult<Doctor> findDoctor(String id) {
        if (id == null || id.isBlank()) return new OperationResult<>(false, "Please enter an ID", null);

        for (Doctor doc : doctorList) {
            if (id.equalsIgnoreCase(doc.getId())) return new OperationResult<>(true, "Doctor found", doc);
        }

        return new OperationResult<>(false, "Doctor not found", null);
    }

    public OperationResult<ArrayList<Doctor>> searchDoctor(String id, String name, String specialist, String workTime, String qualification) {
        ArrayList<Doctor> result = new ArrayList<>();

        for (Doctor doc : doctorList) {
            boolean match = true;

            if (id != null && !id.isBlank() && !id.equalsIgnoreCase(doc.getId())) match = false;
            if (name != null && !name.isBlank() && !doc.getName().toLowerCase().contains(name.toLowerCase())) match = false;
            if (specialist != null && !specialist.isBlank() && !doc.getSpecialist().toLowerCase().contains(specialist.toLowerCase())) match = false;
            if (workTime != null && !workTime.isBlank() && !doc.getWorkTime().toLowerCase().contains(workTime.toLowerCase())) match = false;
            if (qualification != null && !qualification.isBlank() && !doc.getQualification().toLowerCase().contains(qualification.toLowerCase())) match = false;

            if (match) result.add(doc);
        }

        return new OperationResult<>(true, "Search successful, " + result.size() + " entries found.", result);
    }
}