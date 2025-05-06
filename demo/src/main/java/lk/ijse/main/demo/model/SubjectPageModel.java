package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoSubject;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectPageModel {
    private Connection connection;


    public String subjectSave(DtoSubject dtoSubject) throws SQLException {
        if (dtoSubject.getSubjectID().isEmpty() || dtoSubject.getName().isEmpty()) {
            return "Records are Empty";
        } else {
            String sql = "INSERT INTO Subject VALUES (?,?)";
            Boolean b = CRUD.executeQuery(sql, dtoSubject.getSubjectID(), dtoSubject.getName());
            return b == true ? "Success" : "Failed";
        }
    }

    public String updateSubject(DtoSubject dtoSubject) throws SQLException {
        if (dtoSubject.getName().isEmpty() || dtoSubject.getSubjectID().isEmpty()) {
            return "Name Recode is Empty";
        } else {
            String sql = "UPDATE Subject SET Name=? WHERE Subject_ID=?";
            Boolean b = CRUD.executeQuery(sql, dtoSubject.getName(), dtoSubject.getSubjectID());
            return b == true ? "Success" : "Failed";
        }
    }

    public String deleteSubject(DtoSubject dtoSubject) throws SQLException {
        if (dtoSubject.getSubjectID().isEmpty()) {
            return "Subject Id record is empty ";
        } else {
            String sql = "DELETE FROM Subject WHERE Subject_id=?";
            Boolean b = CRUD.executeQuery(sql, dtoSubject.getSubjectID());
            return b == true ? "Success" : "Failed";

        }
    }

    public ObservableList<DtoSubject> getSubjectData() throws SQLException {
        String sql = "SELECT * FROM Subject";
        ResultSet set = CRUD.executeQuery(sql);
        ObservableList<DtoSubject> dtoSubjects = FXCollections.observableArrayList();
        while (set.next()) {
            dtoSubjects.add(new DtoSubject(set.getString("Subject_ID"), set.getString("Name")));
        }
        return dtoSubjects;

    }

    public String getNumber() throws SQLException{
            String sql = "SELECT COUNT(Subject_ID) AS Num FROM Subject";
            ResultSet resultSet = CRUD.executeQuery(sql);
            String result = "";
            while (resultSet.next()) {
                result = resultSet.getString("Num");
            }
            return result;
    }

    public ObservableList<DtoSubject> searchSubject(String anything) throws SQLException {
        String sql = "SELECT  * FROM Subject WHERE Subject_ID= ? OR Name= ?";

        ObservableList<DtoSubject> dtoSubjects = FXCollections.observableArrayList();
        ResultSet set = CRUD.executeQuery(sql,anything,anything);

        while (set.next()) {
            dtoSubjects.add(new DtoSubject(set.getString("Subject_ID"), set.getString("Name")));
        }
        return dtoSubjects;

    }


}
