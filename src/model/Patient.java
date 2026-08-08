package model;

public class Patient {
    private String id;
    private String name;
    private String disease;
    private String sex;
    private String admitStatus;
    private int age; // 新增 PDF 要求的年龄

    public Patient() {}

    public Patient(String id, String name, String disease, String sex, String admitStatus, int age) {
        this.id = id;
        this.name = name;
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

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getAdmitStatus() { return admitStatus; }
    public void setAdmitStatus(String admitStatus) { this.admitStatus = admitStatus; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
