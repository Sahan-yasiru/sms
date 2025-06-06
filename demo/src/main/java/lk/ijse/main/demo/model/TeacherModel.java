package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoTeacher;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherModel {
    public String teacherSave(DtoTeacher dtoTeacher) throws SQLException{
        String sql="INSERT INTO Teacher VALUES(?,?,?,?,?)";
        boolean B= CRUD.executeQuery(sql,dtoTeacher.getTeacherID(),dtoTeacher.getSubjectID(),dtoTeacher.getName(),
                dtoTeacher.getClassId(),dtoTeacher.getGradeAssign());
        return B?"Successfully saved":"Failed ";
    }
    public String teacherUpdate(DtoTeacher dtoTeacher) throws SQLException {
        String sql="UPDATE Teacher SET Name= ? ,Class_ID = ? ,Grades_Assigned= ?,Subject_ID = ?  WHERE Teacher_ID = ?";
        Boolean b=CRUD.executeQuery(sql,dtoTeacher.getName(),dtoTeacher.getClassId(),dtoTeacher.getGradeAssign(),dtoTeacher.getSubjectID(),dtoTeacher.getTeacherID());
        return b?"Successfully updated":"Failed ";
    }
    public String deleteTea(DtoTeacher dtoTeacher) throws SQLException {
        Connection connection= DbController.getInstance().getConnection();
        connection.setAutoCommit(false);
        Boolean result=true;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("DELETE From Attendance_Tea where Teacher_ID = ?");
            preparedStatement.setString(1, dtoTeacher.getTeacherID());
            Boolean b=preparedStatement.executeUpdate()>=0?true:false;
            if(b){
                String sql="DELETE FROM Teacher WHERE Teacher_ID = ?";
                PreparedStatement preparedStatement1=connection.prepareStatement(sql);
                preparedStatement1.setString(1, dtoTeacher.getTeacherID());
                result=preparedStatement1.executeUpdate()>=0?true:false;
            }
        }catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
        }finally {
            if(result){
                connection.commit();
                return "Successfully deleted";
            }else  {
                connection.rollback();
                connection.setAutoCommit(true);
                return "Failed ";
            }
        }
    }
    public ObservableList<DtoTeacher> getTeacherData() throws SQLException {
        String sql="SELECT * FROM Teacher";
        ResultSet set=CRUD.executeQuery(sql);
        ObservableList<DtoTeacher> dtoTeachers = FXCollections.observableArrayList();
        while (set.next()){
            dtoTeachers.add(new DtoTeacher(set.getString(1),set.getString(2),set.getString(3),set.getString(4),set.getInt(5)));
        }
        return dtoTeachers;
    }
    public String getNumber() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT COUNT(*) FROM Teacher");
        while (set.next()){
            return set.getString(1);
        }
        return null;
    }
    public ArrayList<String> getSubjectIDs() throws SQLException {
        String sql="SELECT Subject_ID from Subject";
        ResultSet set=CRUD.executeQuery(sql);
        ArrayList<String> subjectIDs=new ArrayList<>();
        while (set.next()){
            subjectIDs.add(set.getString(1));
        }
        return subjectIDs;
    }
}
