package lk.ijse.main.demo.dto;

public class DtoAttendenceStu {
    private String attendID;
    private String Date;
    private String adminID;
    private String studentID;

    public DtoAttendenceStu(String attendID, String date, String adminID, String studentID) {
        this.attendID = attendID;
        Date = date;
        this.adminID = adminID;
        this.studentID = studentID;
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
}
