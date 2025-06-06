package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoClass;
import lk.ijse.main.demo.dto.DtoStudent;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudetModel {
    public ObservableList<String> getclassIDs() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT Class_ID FROM Class");
        ObservableList<String> list= FXCollections.observableArrayList();
        while (set.next()){
            list.add(set.getString(1));
        }
        return list;
    }
    public ObservableList<DtoStudent> getStudentData() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT * FROM Student");
        ObservableList<DtoStudent> students= FXCollections.observableArrayList();
        while (set.next()){
            students.add(new DtoStudent(set.getString(1),set.getInt(2),set.getString(3),set.getString(4),set.getInt(5),set.getString(6)));
        }
        return students;
    }
    public String getNumber() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT COUNT(*) FROM Student");
        while (set.next()) {
            return set.getString(1);
        }
        return null;
    }
    public String studentSave(DtoStudent dtoStudent) throws SQLException {
        String sql="INSERT INTO Student VALUES(?,?,?,?,?,?)";
        Boolean b=CRUD.executeQuery(sql,dtoStudent.getStudentID(),dtoStudent.getTelNO(),dtoStudent.getClassID(),dtoStudent.getName(),dtoStudent.getGrade(),dtoStudent.getAddress());
        return b?"SuccessFully Saved":"Failed";

    }
    public String updateStudent(DtoStudent dtoStudent) throws SQLException {
        String sql="UPDATE Student SET Tel_No= ? ,Class_ID = ?,Name= ? ,Grade = ? ,Address = ?   WHERE  Student_ID = ?";
        Boolean b=CRUD.executeQuery(sql,dtoStudent.getTelNO(),dtoStudent.getClassID(),dtoStudent.getName(),dtoStudent.getGrade(),dtoStudent.getAddress(),dtoStudent.getStudentID());
        return b?"Successfully Updated":"Failed";

    }
    public String deleteStu(DtoStudent dtoStudent) throws SQLException {
        Connection connection = DbController.getInstance().getConnection();
        connection.setAutoCommit(false);

        boolean success = false;

        try {
            String studentID = dtoStudent.getStudentID();

            // Delete Attendance_Stu records if any
            PreparedStatement psAttendance = connection.prepareStatement("DELETE FROM Attendance_Stu WHERE Student_ID = ?");
            psAttendance.setString(1, studentID);
            psAttendance.executeUpdate();


            // Delete from Student table
            PreparedStatement psStudent = connection.prepareStatement("DELETE FROM Student WHERE Student_ID = ?");
            psStudent.setString(1, studentID);
            int studentDeleted = psStudent.executeUpdate();

            if (studentDeleted > 0) {
                connection.commit();
                success = true;
            } else {
                connection.rollback();
            }

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }

        return success ? "Successfully Deleted" : "Failed";
    }


}
