package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoTimeTable;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeTableModel {

    public ObservableList<DtoTimeTable> getTimeTBData()throws SQLException {
        String sql="SELECT * FROM Time_Table";
        ResultSet set= CRUD.executeQuery(sql);
        ObservableList<DtoTimeTable> list = FXCollections.observableArrayList();
        while (set.next()){
            list.add(new DtoTimeTable(set.getString(1),set.getString(2),set.getString(3),set.getString(4),set.getString(5),null));
        }
        return list;
    }
    public String saveTimeTB(DtoTimeTable dtoTimeTable)throws SQLException {
        String sql="INSERT INTO Time_Table VALUES (?,?,?,?,?)";
        Boolean b=CRUD.executeQuery(sql,dtoTimeTable.getTimeTableID(),dtoTimeTable.getSubjectID(),dtoTimeTable.getStartTime(),dtoTimeTable.getEndTime(),dtoTimeTable.getDayOfWeek());
        return b? "Successfully saved":"Failed to save";
    }
    public ObservableList<String> getsubjectIDs() throws SQLException {
        String sql="SELECT Subject_ID FROM Subject";
        ResultSet set= CRUD.executeQuery(sql);
        ObservableList<String> list = FXCollections.observableArrayList();
        while (set.next()){
            list.add(set.getString(1));
        }
        return list;
    }
    public String updateTimeTB(DtoTimeTable dtoTimeTable)throws SQLException {
        String sql="UPDATE Time_Table SET Subject_ID = ? ,Start_Time = ?,End_Time = ? ,day_of_week= ? where Time_Table_ID = ?";
        Boolean b=CRUD.executeQuery(sql,dtoTimeTable.getSubjectID(),dtoTimeTable.getStartTime(),dtoTimeTable.getEndTime(),dtoTimeTable.getDayOfWeek(),dtoTimeTable.getTimeTableID());
        return b? "Successfully Updated":"Failed to update";
    }
    public String deleteTimeTB(DtoTimeTable dtoTimeTable)throws SQLException {
        Connection connection= DbController.getInstance().getConnection();

        Boolean b=CRUD.executeQuery("DELETE FROM  Time_Table WHERE Time_Table_ID = ?",dtoTimeTable.getTimeTableID());
        return  b? "Successfully Deleted":"Failed to delete Time_Table";

    }
}
