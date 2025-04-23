package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoSubject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectPageModel {
    private Connection connection;


    public String subjectSave(DtoSubject dtoSubject){
        if(dtoSubject.getSubjectID().isEmpty()||dtoSubject.getName().isEmpty()){
            return "Records are Empty";
        }
        try {
            connection = DbController.getInstance().getConnection();
            String sql="INSERT INTO Subject VALUES (?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,dtoSubject.getSubjectID());
            preparedStatement.setString(2,dtoSubject.getName());

            return preparedStatement.executeUpdate()>0?"Success":"Failed";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String updateSubject(DtoSubject dtoSubject){
        if(dtoSubject.getName().isEmpty()||dtoSubject.getSubjectID().isEmpty()){
            return "Name Recode is Empty";
        }
        try {
            connection = DbController.getInstance().getConnection();
            String sql = "UPDATE Subject SET Name=? WHERE Subject_ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,dtoSubject.getName());
            preparedStatement.setString(2,dtoSubject.getSubjectID());

            return preparedStatement.executeUpdate()>0?"Success":"Failed";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public String deleteSubject(DtoSubject dtoSubject){
        if(dtoSubject.getSubjectID().isEmpty()){
            return "Subject Id record is empty ";
        }
        try {
            connection = DbController.getInstance().getConnection();
            String sql="DELETE FROM Subject WHERE Subject_id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,dtoSubject.getSubjectID());

            return preparedStatement.executeUpdate()>0?"Success":"Failed";
        }catch (Exception e){
            return e.getMessage();
        }

    }
    public ObservableList<DtoSubject> getSubjectData(){
        try {
            connection=DbController.getInstance().getConnection();
            String sql="SELECT * FROM Subject";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            ResultSet set=preparedStatement.executeQuery();
            ObservableList<DtoSubject> dtoSubjects= FXCollections.observableArrayList();
            while (set.next()){
                dtoSubjects.add(new DtoSubject(set.getString("Subject_ID"),set.getString("Name")));
            }
            return dtoSubjects;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public String getNumber(){
        try {
            connection=DbController.getInstance().getConnection();
            String sql="SELECT COUNT(Subject_ID) AS Num FROM Subject";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);

            ResultSet resultSet=preparedStatement.executeQuery();
            String result="";
            while (resultSet.next()){
                 result=resultSet.getString("Num");
            }
            return result;
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public ObservableList<DtoSubject> searchSubject(String adminID){
        try {
            connection=DbController.getInstance().getConnection();
            String sql="SELECT * FROM Subject WHERE Subject_ID=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,adminID);

            ObservableList<DtoSubject> dtoSubjects=FXCollections.observableArrayList();
            ResultSet set=preparedStatement.executeQuery();

            while (set.next()){
                dtoSubjects.add(new DtoSubject(set.getString("Subject_ID"),set.getString("Name")));
            }
            return dtoSubjects;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
