package model;

public class Doctor {
    private String id;
    private String name;
    private String specialist;
    private String workTime;
    private String qualification;
    private int room; // 新增 PDF 要求的房间号

    public Doctor() {}

    public Doctor(String id, String name, String specialist, String workTime, String qualification, int room) {
        this.id = id;
        this.name = name;
        this.specialist = specialist;
        this.workTime = workTime;
        this.qualification = qualification;
        this.room = room;
    }

    public void showDoctorInfo() {
        System.out.printf("[%s]\t[%s]\t[%s]\t[%s]\t[%s]\t[%d]%n",
                id, name, specialist, workTime, qualification, room);
    }

    public String getDoctorInfo() {
        return String.format("ID: %s | Name: %s | Specialist: %s | WorkTime: %s | Qualification: %s | Room: %d",
                id, name, specialist, workTime, qualification, room);
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialist() { return specialist; }
    public void setSpecialist(String specialist) { this.specialist = specialist; }

    public String getWorkTime() { return workTime; }
    public void setWorkTime(String workTime) { this.workTime = workTime; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public int getRoom() { return room; }
    public void setRoom(int room) { this.room = room; }
}
