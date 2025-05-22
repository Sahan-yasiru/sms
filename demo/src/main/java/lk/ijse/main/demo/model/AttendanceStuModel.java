package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.dto.DtoAttendenceStu;
import lk.ijse.main.demo.util.CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AttendanceStuModel {

    public ObservableList<String> getIDs(String classID) throws SQLException {

        String sql = "SELECT Student_ID FROM Student WHERE Class_ID= ? ";
        ResultSet set = CRUD.executeQuery(sql, classID);
        ObservableList<String> observableList = FXCollections.observableArrayList();

        while (set.next()) {
            observableList.add(set.getString(1));
        }
        return observableList;

    }

    public static String getAdminName(String id) throws SQLException {
        String sql = "SELECT Admin_ID FROM Admin WHERE User_name=?";
        ResultSet resultSet = CRUD.executeQuery(sql, id);
        String result = "";
        while (resultSet.next()) {
            result = resultSet.getString(1);
        }
        return result;

    }

    public ObservableList<String> getStuNames(String classID) throws SQLException {
        String sql = "SELECT Name FROM Student WHERE Class_ID= ? ";
        ResultSet set = CRUD.executeQuery(sql, classID);
        ObservableList<String> observableList = FXCollections.observableArrayList();

        while (set.next()) {
            observableList.add(set.getString(1));
        }
        return observableList;
    }

    public String saveStuAttend(DtoAttendenceStu dtoAttendenceStu) throws SQLException, ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dtoAttendenceStu.getDate());
        if (!date.after(new Date())) {
            String sql = "INSERT INTO Attendance_Stu VALUES (?,?,?,?,?,?,?)";
            Boolean b = CRUD.executeQuery(sql, dtoAttendenceStu.getAttendID(), dtoAttendenceStu.getDate(), dtoAttendenceStu.getAdminID(), dtoAttendenceStu.getStudentID(), dtoAttendenceStu.getName(), dtoAttendenceStu.getClassID(), dtoAttendenceStu.getStatus());
            return b == true ? "successful" : "Failed";
        } else {
            return "your Date is invalid";
        }
    }

    public ObservableList<DtoAttendenceStu> lordTable() throws SQLException {
        String sql = "SELECT * FROM Attendance_Stu";
        ResultSet set = CRUD.executeQuery(sql);
        ObservableList<DtoAttendenceStu> dtoAttendenceStus = FXCollections.observableArrayList();

        while (set.next()) {
            dtoAttendenceStus.add(new DtoAttendenceStu(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getBoolean(7), set.getString(6)));
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

    public ObservableList<DtoAttendenceStu> serchAttendStu(DtoAttendenceStu dtoAttendenceStu) throws SQLException {
        System.out.println("word");
        StringBuilder sql = new StringBuilder("SELECT * FROM Attendance_Stu WHERE ");
        String[] sqlPlus = {" Date = ? ", " Status = ? ", " Class_ID  = ? ", " Stu_Name  = ? ", " Student_ID = ? "};
        ArrayList<Object> list =new ArrayList<>();
        boolean isWorked=false;
        String gateAnd=" AND ";
        if(dtoAttendenceStu.getDate()!=null){
            sql.append(sqlPlus[0]).append(gateAnd);
            list.add(dtoAttendenceStu.getDate());
            isWorked=true;
        }
        if(dtoAttendenceStu.getStatus()!=null){
            sql.append(sqlPlus[1]).append(gateAnd);
            list.add(dtoAttendenceStu.getStatus());
            isWorked=true;
        }
        if(dtoAttendenceStu.getClassID()!=null){
            sql.append(sqlPlus[2]).append(gateAnd);
            list.add(dtoAttendenceStu.getClassID());
            isWorked=true;
        }
        if(dtoAttendenceStu.getName()!=null){
            sql.append(sqlPlus[3]).append(gateAnd);
            list.add(dtoAttendenceStu.getName());
            isWorked=true;
        }
        if(dtoAttendenceStu.getStudentID()!=null){
            sql.append(sqlPlus[4]).append(gateAnd);
            list.add(dtoAttendenceStu.getStudentID());
            isWorked=true;
        }
        if(sql.lastIndexOf(gateAnd) == sql.length() - gateAnd.length()) {
            sql.delete(sql.length() - gateAnd.length(), sql.length());
        }
        if(isWorked){
            ResultSet set=CRUD.executeQuery(sql.toString(),list.toArray(new Object[list.size()]));
            ObservableList<DtoAttendenceStu> dtoAttendenceStus=FXCollections.observableArrayList();
            while (set.next()){
                dtoAttendenceStus.add(new DtoAttendenceStu(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getBoolean(7), set.getString(6)));
            }
            return dtoAttendenceStus;
        }else {
            return null;
        }

    }


    public Date chackDate(Object date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = simpleDateFormat.parse(date.toString());
            return date1;
        } catch (ParseException e) {
            return null;
        }
    }

    public Boolean chackBoolean(Object b) {
        try {
            Boolean result = Boolean.parseBoolean(b.toString());
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public String presentNumSet() throws SQLException {
        String sql = "SELECT COUNT(Status) AS Num FROM Attendance_Stu WHERE Status = ? ";
        ResultSet set = CRUD.executeQuery(sql, true);
        String s = "";

        while (set.next()) {
            s = set.getString("Num");
        }
        return s;

    }

    public String absentNumSet() throws SQLException {
        String sql = "SELECT COUNT(Status) AS Num FROM Attendance_Stu WHERE Status = ? ";
        ResultSet set = CRUD.executeQuery(sql, false);
        String s = "";

        while (set.next()) {
            s = set.getString("Num");
        }
        return s;

    }

    public String deleteAttendStu(String attendID) throws SQLException {
        String sql = "DELETE FROM Attendance_Stu WHERE Attend_ID = ? ";
        return CRUD.executeQuery(sql, attendID) ? "Successfully Updated" : "Something Went Wrong";
    }

    public String setAutoStuID(String name) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT Student_ID FROM Student WHERE Name = ?", name);
        String s = "";
        while (set.next()) {
            s = set.getString(1);
        }
        return s;

    }

    public String setAutoStuName(String stuID) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT Name FROM Student WHERE Student_ID = ?", stuID);
        String s = "";
        while (set.next()) {
            s = set.getString(1);
        }
        return s;

    }

    public boolean chackAlreadyAttend(String stuID, String classID, String date) throws SQLException {
        String sql = "SELECT * FROM Attendance_Stu WHERE Class_ID=? AND Student_ID = ? AND  Date = ? ";
        ResultSet set = CRUD.executeQuery(sql, classID, stuID, date);

        while (set.next()) {
            return true;
        }
        return false;

    }


}


