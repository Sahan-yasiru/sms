package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoSubject;
import lk.ijse.main.demo.dto.DtoTimeTable;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

public class DashboardModel {
    private Connection connection;
    private java.util.Date date;


    public  String getNumber(String priKey,String table) {
        try {
            connection = DbController.getInstance().getConnection();
            String sql = "SELECT COUNT(" + priKey + ") as num_of FROM " + table;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
               String num=resultSet.getString("num_of");
               return num;
            }
            else {
                return "not avalable";
            }

        } catch (Exception e) {
           new Alert(Alert.AlertType.ERROR, e.getMessage());
           return null;

        }

    }
    public String getDate(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        date=new Date();
        return simpleDateFormat.format(date);
    }
    public ObservableList<DtoTimeTable> getTimeDatableData() throws Exception{
        LocalDate localDate=LocalDate.now();
        DayOfWeek dayOfWeek=localDate.getDayOfWeek();
        String date=dayOfWeek.name();
            connection = DbController.getInstance().getConnection();
            String sql="SELECT t.day_of_week,Subject.Name,t.start_time,t.End_time  FROM Time_Table,Subject join Time_Table t on Subject.Subject_ID = t.Subject_ID having t.day_of_week=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,date);

            ResultSet set=preparedStatement.executeQuery();
            ObservableList<DtoTimeTable> dtoTimeTables= FXCollections.observableArrayList();
            while (set.next()){
                dtoTimeTables.add(new DtoTimeTable(null,null,set.getString("start_time"),set.getString("End_time"),set.getString("Day_of_week"),set.getString("Name")));
            }
            return dtoTimeTables;
    }
}
