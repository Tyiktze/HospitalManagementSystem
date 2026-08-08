package services;

import model.Doctor;
import model.OperationResult;
import utils.InputValidator;

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
        doctorList.add(new Doctor("D001", "Dr. BEH JIE SHEN", "Surgeon", "8-11AM", "MBBS, MD", 11));
        doctorList.add(new Doctor("D002", "Dr. Tom Wong", "Surgeon", "8-11AM", "MBBS, MD", 12));
        doctorList.add(new Doctor("D003", "Dr. John Lim", "Physician", "10-3PM", "MBBS, MS", 45));
        doctorList.add(new Doctor("D004", "Dr. Amy Chia", "Surgeon", "7-11AM", "MBBS, MD", 8));
        doctorList.add(new Doctor("D005", "Dr. Sarah Tan", "Dermatologist", "2-6PM", "MBBS", 23));
    }

    public String getDoctorId() {
        return String.format("D%03d", nextId);
    }

    public ArrayList<Doctor> getDoctors() {
        return doctorList;
    }

    public OperationResult<Void> addDoctor(String id, String name, String specialist, String workTime, String qualification, String rawRoom) {
        if (name == null || name.isBlank()) return new OperationResult<>(false, "Please enter a name", null);
        if (specialist == null || specialist.isBlank()) return new OperationResult<>(false, "Please enter a specialist", null);
        if (workTime == null || workTime.isBlank()) return new OperationResult<>(false, "Please enter work time", null);
        if (qualification == null || qualification.isBlank()) return new OperationResult<>(false, "Please enter qualification", null);
        
        Integer room = InputValidator.parseInteger(rawRoom);
        if (room == null) return new OperationResult<>(false, "Room must be a number.", null);

        Doctor doctor = new Doctor(id, name, specialist, workTime, qualification, room);
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

    public OperationResult<Void> updateDoctor(Doctor doctor, String name, String specialist, String workTime, String qualification, String rawRoom) {
        if (doctor == null) return new OperationResult<>(false, "Doctor not found.", null);

        if (name != null && !name.isBlank()) doctor.setName(name);
        if (specialist != null && !specialist.isBlank()) doctor.setSpecialist(specialist);
        if (workTime != null && !workTime.isBlank()) doctor.setWorkTime(workTime);
        if (qualification != null && !qualification.isBlank()) doctor.setQualification(qualification);
        
        if (rawRoom != null && !rawRoom.isBlank()) {
            Integer room = InputValidator.parseInteger(rawRoom);
            if (room == null) return new OperationResult<>(false, "Room must be a number.", null);
            doctor.setRoom(room);
        }
        return new OperationResult<>(true, "Doctor " + doctor.getId() + " updated.", null);
    }

    public OperationResult<Doctor> findDoctor(String id) {
        if (id == null || id.isBlank()) return new OperationResult<>(false, "Please enter an ID", null);
        for (Doctor doc : doctorList) {
            if (id.equalsIgnoreCase(doc.getId())) return new OperationResult<>(true, "Doctor found", doc);
        }
        return new OperationResult<>(false, "Doctor not found", null);
    }

    public OperationResult<ArrayList<Doctor>> searchDoctor(String id, String name, String specialist, String workTime, String qualification, String rawRoom) {
        ArrayList<Doctor> result = new ArrayList<>();
        Integer room = null;
        
        if (rawRoom != null && !rawRoom.isBlank()) {
            room = InputValidator.parseInteger(rawRoom);
            if (room == null) return new OperationResult<>(false, "Room must be a number.", null);
        }

        for (Doctor doc : doctorList) {
            boolean match = true;
            if (id != null && !id.isBlank() && !id.equalsIgnoreCase(doc.getId())) match = false;
            if (name != null && !name.isBlank() && !doc.getName().toLowerCase().contains(name.toLowerCase())) match = false;
            if (specialist != null && !specialist.isBlank() && !doc.getSpecialist().toLowerCase().contains(specialist.toLowerCase())) match = false;
            if (workTime != null && !workTime.isBlank() && !doc.getWorkTime().toLowerCase().contains(workTime.toLowerCase())) match = false;
            if (qualification != null && !qualification.isBlank() && !doc.getQualification().toLowerCase().contains(qualification.toLowerCase())) match = false;
            if (room != null && !room.equals(doc.getRoom())) match = false;
            
            if (match) result.add(doc);
        }
        return new OperationResult<>(true, "Search successful, " + result.size() + " entries found.", result);
    }
}
