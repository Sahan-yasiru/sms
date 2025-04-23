package lk.ijse.main.demo.dto;

public class DtoExam {
    private String examID;
    private String SubjectID;
    private String StuentID;
    private String exmaDate;
    private String teacherID;
    private String marks;

    public DtoExam(String examID, String subjectID, String stuentID, String exmaDate, String teacherID, String marks) {
        this.examID = examID;
        SubjectID = subjectID;
        StuentID = stuentID;
        this.exmaDate = exmaDate;
        this.teacherID = teacherID;
        this.marks = marks;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    public String getStuentID() {
        return StuentID;
    }

    public void setStuentID(String stuentID) {
        StuentID = stuentID;
    }

    public String getExmaDate() {
        return exmaDate;
    }

    public void setExmaDate(String exmaDate) {
        this.exmaDate = exmaDate;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
