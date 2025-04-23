package lk.ijse.main.demo.dto;

public class DtoTimeTable {
    private String timeTableID;
    private String subjectID;
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String subjectName;

    public DtoTimeTable(String timeTableID, String subjectID, String startTime, String endTime, String dayOfWeek,String subjectName) {
        this.timeTableID = timeTableID;
        this.subjectID = subjectID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.subjectName=subjectName;

    }

    public String getTimeTableID() {
        return timeTableID;
    }

    public void setTimeTableID(String timeTableID) {
        this.timeTableID = timeTableID;
    }
    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
