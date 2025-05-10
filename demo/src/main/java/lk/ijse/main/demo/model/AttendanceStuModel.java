package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoAttendenceStu;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AttendanceStuModel {

    public ObservableList<String> getIDs() throws SQLException {

        String sql = "SELECT Student_ID FROM Student";
        ResultSet set = CRUD.executeQuery(sql);
        ObservableList<String> observableList = FXCollections.observableArrayList();

        while (set.next()) {
            observableList.add(set.getString(1));
        }
        return observableList;

    }

    public String getAdminName(String id) throws SQLException {
        String sql = "SELECT Admin_ID FROM Admin WHERE User_name=?";
        ResultSet resultSet = CRUD.executeQuery(sql, id);
        String result = "";
        while (resultSet.next()) {
            result = resultSet.getString(1);
        }
        return result;

    }

    public ObservableList<String> getStuNames() throws SQLException {
        String sql = "SELECT Name FROM Student";
        ResultSet set = CRUD.executeQuery(sql);
        ObservableList<String> observableList = FXCollections.observableArrayList();

        while (set.next()) {
            observableList.add(set.getString(1));
        }
        return observableList;
    }

    public String saveStuAttend(DtoAttendenceStu dtoAttendenceStu) throws SQLException {
        String sql = "INSERT INTO Attendance_Stu VALUES (?,?,?,?,?,?,?)";
        Boolean b = CRUD.executeQuery(sql, dtoAttendenceStu.getAttendID(), dtoAttendenceStu.getDate(), dtoAttendenceStu.getAdminID(), dtoAttendenceStu.getStudentID(), dtoAttendenceStu.getStatus(), dtoAttendenceStu.getName(), dtoAttendenceStu.getClassID());
        return b == true ? "successful" : "Failed";
    }

    public ObservableList<DtoAttendenceStu> lordTable() throws SQLException {
        String sql = "SELECT * FROM Attendance_Stu";
        ResultSet set = CRUD.executeQuery(sql);
        ObservableList<DtoAttendenceStu> dtoAttendenceStus = FXCollections.observableArrayList();

        while (set.next()) {
            dtoAttendenceStus.add(new DtoAttendenceStu(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(6), set.getBoolean(5), set.getString(7)));
        }
        return dtoAttendenceStus;


    }

    public ObservableList<String> getclassIDs() throws SQLException {
        String sql = "SELECT Class_ID FROM Class";
        ResultSet set = CRUD.executeQuery(sql);

        ObservableList<String> observableList = FXCollections.observableArrayList();
        while (set.next()) {
            observableList.add(set.getString("Class_ID"));
        }
        return observableList;

    }

    public String updateAttendStu(DtoAttendenceStu dtoAttendenceStu) throws SQLException {
        String sql = "update Attendance_Stu set Date=?,Admin_ID=?,Student_ID=?,Status=?,Stu_Name=?,Class_ID=? where Attend_ID=?";
        Boolean b = CRUD.executeQuery(sql, dtoAttendenceStu.getDate(), dtoAttendenceStu.getAdminID(), dtoAttendenceStu.getStudentID(),
                dtoAttendenceStu.getStatus(), dtoAttendenceStu.getName(), dtoAttendenceStu.getClassID(), dtoAttendenceStu.getAttendID());
        return b == true ? "Successfully Updated" : "Something Went Wrong";

    }

    public ObservableList<DtoAttendenceStu> serchAttendStu(String anything) throws SQLException {
        ResultSet set=null;
        String []coloums={"Admin_ID","Student_ID","Stu_Name","Class_ID","Attend_ID"};
        ObservableList<DtoAttendenceStu> dtoAttendenceStus=FXCollections.observableArrayList();

        for (String colum:coloums){
            String sql="SELECT * FROM Attendance_Stu WHERE "+colum+" = ?";
            set=CRUD.executeQuery(sql,anything);

            while (set.next()){
                dtoAttendenceStus.add(new DtoAttendenceStu(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(6), set.getBoolean(5), set.getString(7)));
            }
            System.gc();
        }
        return dtoAttendenceStus;

    }

    public Date chackDate(Object date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 =simpleDateFormat.parse(date.toString());
            return date1;
        } catch (ParseException e) {
            return null;
        }
    }
    public Boolean chackBoolean(Object b) {
        try {
            Boolean result =Boolean.parseBoolean(b.toString());
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public String presentNumSet() throws SQLException{
        String sql="SELECT COUNT(Status) AS Num FROM Attendance_Stu WHERE Status = ? ";
        ResultSet set=CRUD.executeQuery(sql,true);
        String s="";

        while (set.next()){
            s=set.getString("Num");
        }
        return s;

    }
    public String absentNumSet() throws SQLException{
        String sql="SELECT COUNT(Status) AS Num FROM Attendance_Stu WHERE Status = ? ";
        ResultSet set=CRUD.executeQuery(sql,false);
        String s="";

        while (set.next()){
            s=set.getString("Num");
        }
        return s;

    }
    public String deleteAttendStu(String attendID) throws SQLException{
        String sql="DELETE FROM Attendance_Stu WHERE Attend_ID = ? ";
        return CRUD.executeQuery(sql,attendID)?"Successfully Updated" : "Something Went Wrong";
    }

}


