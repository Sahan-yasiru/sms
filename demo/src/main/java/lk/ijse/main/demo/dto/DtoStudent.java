package lk.ijse.main.demo.dto;

public class DtoStudent {
    private String studentID;
    private int telNO;
    private String classID;
    private String name;
    private int grade;
    private String address;

    public DtoStudent(String studentID, int telNO, String classID, String name, int grade, String address) {
        this.studentID = studentID;
        this.telNO = telNO;
        this.classID = classID;
        this.name = name;
        this.grade = grade;
        this.address = address;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getTelNO() {
        return telNO;
    }

    public void setTelNO(int telNO) {
        this.telNO = telNO;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
