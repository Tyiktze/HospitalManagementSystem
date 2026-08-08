package model;

public class Doctor {
    private String id;
    private String name;
    private String specialist;
    private String workTime;
    private String qualification;


    //constructor
    public Doctor() {}

    public Doctor(String id, String name, String specialist, String workTime, String qualification) {
        this.id = id;
        this.name = name;
        this.specialist = specialist;
        this.workTime = workTime;
        this.qualification = qualification;
    }
    //print the doctor info
    public void showDoctorInfo() {
        System.out.printf("[%s]\t[%s]\t[%s]\t[%s]\t[%s]%n",
                id, name, specialist, workTime, qualification);


    }


}