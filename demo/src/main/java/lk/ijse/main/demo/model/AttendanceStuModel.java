package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.dto.DtoAttendenceStu;
import lk.ijse.main.demo.util.CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        ObservableList<DtoAttendenceStu> dtoAttendenceStus=FXCollections.observableArrayList();
        class GrabData{
            public void collectData(String row,String keyword) throws SQLException{
                String sql="SELECT * FROM Attendance_Stu WHERE "+row+" =? ";
                ResultSet set=CRUD.executeQuery(sql,keyword);
                while (set.next()){
                    dtoAttendenceStus.add(new DtoAttendenceStu(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(6), set.getBoolean(5), set.getString(7)));
                }
            }
        }
        new GrabData().collectData("Attend_ID",anything);
        new GrabData().collectData("Admin_ID",anything);
        new GrabData().collectData("Student_ID",anything);
        new GrabData().collectData("Stu_name",anything);
        new GrabData().collectData("Class_ID",anything);
        return dtoAttendenceStus;
    }

    public Boolean chackDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            simpleDateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}


