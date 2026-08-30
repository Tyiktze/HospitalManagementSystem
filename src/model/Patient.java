package model;

public class Patient extends Person {
    private String disease;
    private String sex;
    private String admitStatus;
    private int age; 

    public Patient() {
        super();
    }

    public Patient(String id, String name, String disease, String sex, String admitStatus, int age) {
        super(id, name);
        this.disease = disease;
        this.sex = sex;
        this.admitStatus = admitStatus;
        this.age = age;
    }

    public void showPatientInfo() {
        System.out.printf("[%s]\t[%s]\t[%s]\t[%s]\t[%s]\t[%d]%n",
                id, name, disease, sex, admitStatus, age);
    }

    public String getPatientInfo() {
        return String.format("ID: %s | Name: %s | Disease: %s | Sex: %s | Admit Status: %s | Age: %d",
                id, name, disease, sex, admitStatus, age);
    }

    @Override
    public String getDetails() {
        return getPatientInfo();
    }

    // Getters & Setters
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getAdmitStatus() { return admitStatus; }
    public void setAdmitStatus(String admitStatus) { this.admitStatus = admitStatus; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
