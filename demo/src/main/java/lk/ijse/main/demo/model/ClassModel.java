package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoClass;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassModel {

    public String classSave(DtoClass dtoClass) throws SQLException {
        String sql = "INSERT INTO Class VALUES (?,?,?,?)";
        Boolean b = CRUD.executeQuery(sql, dtoClass.getClassID(), dtoClass.getGrade(), dtoClass.getTimeTableID(), dtoClass.getSubjectID());
        return b ? "Successfully Saved" : "Failed";
    }

    public String classUpdate(DtoClass dtoClass) throws SQLException {
        String sql = "UPDATE Class SET Grade= ? ,Time_Table_ID= ? ,Subject_ID=? WHERE Class_ID=?";
        Boolean b = CRUD.executeQuery(sql, dtoClass.getGrade(), dtoClass.getTimeTableID(), dtoClass.getSubjectID(), dtoClass.getClassID());
        return b ? "Successfully Updated" : "Failed";
    }

    public ObservableList<DtoClass> getClassData() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Class");
        ObservableList<DtoClass> dtoClasses = FXCollections.observableArrayList();
        while (set.next()) {
            dtoClasses.add(new DtoClass(set.getString(1), set.getInt(2), set.getString(3), set.getString(4)));
        }
        return dtoClasses;
    }

    public String getNumber() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT COUNT(*) FROM Class");
        while (set.next()) {
            return set.getString(1);
        }
        return null;
    }

    public ObservableList<String> getSubjectIDs() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT Subject_ID FROM Subject");
        ObservableList<String> subjectIDs = FXCollections.observableArrayList();
        while (set.next()) {
            subjectIDs.add(set.getString(1));
        }
        return subjectIDs;
    }

    public ObservableList<String> getTimeTBIDs() throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT Time_Table_ID FROM Time_Table");
        ObservableList<String> timesIDs = FXCollections.observableArrayList();
        while (set.next()) {
            timesIDs.add(set.getString(1));
        }
        return timesIDs;
    }

    public String deleteClass(DtoClass dtoClass) throws SQLException {
        Connection connection = DbController.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            String classID = dtoClass.getClassID();

            PreparedStatement getStudents = connection.prepareStatement("SELECT Student_ID FROM Student WHERE Class_ID = ?");
            getStudents.setString(1, classID);
            ResultSet studentRS = getStudents.executeQuery();

            ArrayList<String> studentIDs = new ArrayList<>();
            while (studentRS.next()) {
                studentIDs.add(studentRS.getString("Student_ID"));
            }

            if (!studentIDs.isEmpty()) {
                PreparedStatement deleteStuAttendance = connection.prepareStatement("DELETE FROM Attendance_Stu WHERE Student_ID = ?");
                for (String studentID : studentIDs) {
                    deleteStuAttendance.setString(1, studentID);
                    deleteStuAttendance.addBatch();
                }
                deleteStuAttendance.executeBatch();
            }

            PreparedStatement deleteStudents = connection.prepareStatement("DELETE FROM Student WHERE Class_ID = ?");
            deleteStudents.setString(1, classID);
            deleteStudents.executeUpdate();

            PreparedStatement getTeachers = connection.prepareStatement("SELECT Teacher_ID FROM Teacher WHERE Class_ID = ?");
            getTeachers.setString(1, classID);
            ResultSet teacherRS = getTeachers.executeQuery();

            ArrayList<String> teacherIDs = new ArrayList<>();
            while (teacherRS.next()) {
                teacherIDs.add(teacherRS.getString("Teacher_ID"));
            }

            if (!teacherIDs.isEmpty()) {
                PreparedStatement deleteTeaAttendance = connection.prepareStatement("DELETE FROM Attendance_Tea WHERE Teacher_ID = ?");
                for (String teacherID : teacherIDs) {
                    deleteTeaAttendance.setString(1, teacherID);
                    deleteTeaAttendance.addBatch();
                }
                deleteTeaAttendance.executeBatch();
            }

            PreparedStatement deleteTeachers = connection.prepareStatement("DELETE FROM Teacher WHERE Class_ID = ?");
            deleteTeachers.setString(1, classID);
            deleteTeachers.executeUpdate();

            PreparedStatement deleteClass = connection.prepareStatement("DELETE FROM Class WHERE Class_ID = ?");
            deleteClass.setString(1, classID);
            int rows = deleteClass.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            return rows > 0 ? "Successfully Deleted" : "Failed";

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return "Failed";
        }
    }

}
