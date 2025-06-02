package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.dto.DtoClass;
import lk.ijse.main.demo.util.CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassModel {

    public String classSave(DtoClass dtoClass)  throws SQLException {
        String sql="INSERT INTO Class VALUES (?,?,?,?)";
        Boolean b= CRUD.executeQuery(sql,dtoClass.getClassID(),dtoClass.getGrade(),dtoClass.getTimeTableID(),dtoClass.getSubjectID());
        return b?"Successfully Saved":"Failed";
    }
    public String classUpdate(DtoClass dtoClass)  throws SQLException {
        String sql="UPDATE Class SET Grade= ? ,Time_Table_ID= ? ,Subject_ID=? WHERE Class_ID=?";
        Boolean b=CRUD.executeQuery(sql,dtoClass.getGrade(),dtoClass.getTimeTableID(),dtoClass.getSubjectID(),dtoClass.getClassID());
        return b?"Successfully Updated":"Failed";
    }
    public String deleteClass(DtoClass dtoClass)  throws SQLException {
        String sql="DELETE FROM Class WHERE Class_ID=?";
        Boolean b=CRUD.executeQuery(sql,dtoClass.getClassID());
        return b?"Successfully Deleted":"Failed";
    }
    public ObservableList<DtoClass> getClassData() throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT * FROM Class");
        ObservableList<DtoClass> dtoClasses = FXCollections.observableArrayList();
        while (set.next()){
            dtoClasses.add(new DtoClass(set.getString(1),set.getInt(2),set.getString(3),set.getString(4)));
        }
        return dtoClasses;
    }
    public String getNumber()throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT COUNT(*) FROM Class");
        while(set.next()){
            return set.getString(1);
        }
        return null;
    }
    public ObservableList<String> getSubjectIDs()  throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT Subject_ID FROM Subject");
        ObservableList<String> subjectIDs = FXCollections.observableArrayList();
        while (set.next()){
            subjectIDs.add(set.getString(1));
        }
        return subjectIDs;
    }
    public ObservableList<String> getTimeTBIDs()  throws SQLException {
        ResultSet set=CRUD.executeQuery("SELECT Time_Table_ID FROM Time_Table");
        ObservableList<String> timesIDs = FXCollections.observableArrayList();
        while (set.next()){
            timesIDs.add(set.getString(1));
        }
        return timesIDs;
    }
}
