package lk.ijse.main.demo.dto;

public class DtoAttendenceTea {
    private String attendID;
    private String Date;
    private String adminID;
    private String teacherID;

    public DtoAttendenceTea(String attendID, String date, String adminID, String teacherID) {
        this.attendID = attendID;
        Date = date;
        this.adminID = adminID;
        this.teacherID = teacherID;
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

    public String getTeacherIDID() {
        return teacherID;
    }

    public void setTeacherIDID(String studentID) {
        this.teacherID = studentID;
    }
}
