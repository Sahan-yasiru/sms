package lk.ijse.main.demo.dto;

public class DtoAttendenceStu {
    private String attendID;
    private String Date;
    private String adminID;
    private String studentID;
    private String name;
    private Boolean status;
    private String classID;

    public DtoAttendenceStu(String attendID, String date, String adminID, String studentID, String name, Boolean status, String classID) {
        this.attendID = attendID;
        this.Date = date;
        this.adminID = adminID;
        this.studentID = studentID;
        this.name=name;
        this.status = status;
        this.classID = classID;
    }

    public String getAttendID() {
        return attendID;
    }

    public void setAttendID(String attendID) {
        this.attendID = attendID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
