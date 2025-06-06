package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.dto.DtoExam;
import lk.ijse.main.demo.util.CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamModel {

    public ObservableList<DtoExam>  lordTable() throws SQLException {
        String sql = "SELECT * FROM Exam";
        ResultSet set= CRUD.executeQuery(sql);
        ObservableList<DtoExam> list = FXCollections.observableArrayList();
        while (set.next()){
            DtoExam dtoExam = new DtoExam();
            dtoExam.setExamID(set.getString(1));
            dtoExam.setExmaDate(set.getString(2));
            dtoExam.setMarks(set.getInt(3));
            dtoExam.setSubjectID(set.getString(4));
            dtoExam.setTeacherID(set.getString(5));
            dtoExam.setStuentID(set.getString(6));
            list.add(dtoExam);
        }
        return list;
    }
    public ObservableList<String>  getSubjectIDs() throws SQLException {
        String sql="SELECT Subject_ID FROM Subject";
        ResultSet set= CRUD.executeQuery(sql);
        ObservableList<String> list = FXCollections.observableArrayList();
        while (set.next()){
            list.add(set.getString(1));
        }
        return list;
    }
    public ObservableList<String>  getStudentIDs() throws SQLException {
        String sql="SELECT Student_ID FROM Student";
        ResultSet set= CRUD.executeQuery(sql);
        ObservableList<String> list = FXCollections.observableArrayList();
        while (set.next()){
            list.add(set.getString(1));
        }
        return list;
    }
    public ObservableList<String>  getTeacherIDs() throws SQLException {
        String sql="SELECT Teacher_ID FROM Teacher";
        ResultSet set= CRUD.executeQuery(sql);
        ObservableList<String> list = FXCollections.observableArrayList();
        while (set.next()){
            list.add(set.getString(1));
        }
        return list;
    }
    public String saveExam(DtoExam dtoExam) throws SQLException {
        String sql="INSERT INTO Exam VALUES (?,?,?,?,?,?)";
        Boolean b=CRUD.executeQuery(sql,dtoExam.getExamID(),dtoExam.getExmaDate(),dtoExam.getMarks(),dtoExam.getSubjectID(),dtoExam.getTeacherID(),dtoExam.getStudentID());
        return  (b)?"Successfully Saved":"failed to save";
    }
    public String updateExam(DtoExam dtoExam) throws SQLException {
        String sql="UPDATE Exam SET Subject_ID= ? , Student_ID = ?,Exam_Date = ?,Teacher_ID = ? ,Marks =  ? WHERE Exam_ID = ?";
        Boolean b=CRUD.executeQuery(sql,dtoExam.getSubjectID(),dtoExam.getStudentID(),dtoExam.getExmaDate(),dtoExam.getTeacherID(),dtoExam.getMarks(),dtoExam.getExamID());
        return  (b)?"Successfully updated":"failed to update";
    }
    public String deleteExam(DtoExam dtoExam) throws SQLException {
        String sql="DELETE FROM Exam WHERE Exam_ID = ?";
        Boolean b=CRUD.executeQuery(sql,dtoExam.getExamID());
        return  (b)?"Successfully Deleted":"failed to delete";
    }



}
