package model;

public class Patient {
    private String id;
    private String name;
    private String disease;
    private String sex;
    private String admitStatus;


    //constructor
    public Patient() {}

    public Patient(String id, String name, String specialist, String workTime, String qualification) {
        this.id = id;
        this.name = name;
        this.disease = specialist;
        this.sex = workTime;
        this.admitStatus = qualification;
    }
    //print the Patient info
    public void showPatientInfo() {
        System.out.printf("[%s]\t[%s]\t[%s]\t[%s]\t[%s]%n",
                id, name, disease, sex, admitStatus);


    }


}