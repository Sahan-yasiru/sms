package lk.ijse.main.demo.dto;

import lk.ijse.main.demo.toggleButton.ToggleSwitch;

public class DtoAttendenceStu {
    private String attendID;
    private String Date;
    private String adminID;
    private String studentID;
    private String classID;
    private ToggleSwitch toggleSwitch;
    private Boolean state;

    public DtoAttendenceStu(String attendID, String date, String adminID, String studentID, String classID, ToggleSwitch toggleSwitch) {
        this.attendID = attendID;
        this.Date = date;
        this.adminID = adminID;
        this.studentID = studentID;
        this.classID = classID;
        this.toggleSwitch = toggleSwitch;
    }
    public DtoAttendenceStu() {}

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

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public Boolean getStatus() {
        return this.toggleSwitch.getSwitchedOn();
    }

    public void setStatus(Boolean status) {

        this.toggleSwitch.setSwitchedOn(status);
    }
    public ToggleSwitch getToggleSwitch() {
        return this.toggleSwitch;
    }
    public void setToggleSwitch(ToggleSwitch toggleSwitch){
        this.toggleSwitch=toggleSwitch;
    }

    @Override
    public String toString() {
        return this.attendID+" "+this.Date+" "+this.adminID+" "+this.studentID +" "+this.classID+" "+this.getStatus();
    }
    public Boolean getState(){
        return this.state;
    }
}
