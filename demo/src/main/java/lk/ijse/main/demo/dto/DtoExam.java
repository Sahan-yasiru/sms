package lk.ijse.main.demo.dto;

public class DtoExam {
    private String examID;
    private String SubjectID;
    private String studentID;
    private String exmaDate;
    private String teacherID;
    private int marks;

    public DtoExam(String examID, String subjectID, String studentID, String exmaDate, String teacherID, int marks) {
        this.examID = examID;
        this.SubjectID = subjectID;
        this.studentID = studentID;
        this.exmaDate = exmaDate;
        this.teacherID = teacherID;
        this.marks = marks;
    }
    public DtoExam() {}

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

    public String getStudentID() {
        return this.studentID;
    }

    public void setStuentID(String stuentID) {
        this.studentID = stuentID;
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

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
