package lk.ijse.main.demo.dto;

public class DtoAttendenceTea {
    private String attendID;
    private String Date;
    private String adminID;
    private String teacherID;
    private String classID;
    private Boolean status;

    public DtoAttendenceTea(String attendID, String date, String adminID, String teacherID, String classID, Boolean status) {
        this.attendID = attendID;
        this.Date = date;
        this.adminID = adminID;
        this.teacherID = teacherID;
        this.classID = classID;
        this.status = status;
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

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String studentID) {
        this.teacherID = studentID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
