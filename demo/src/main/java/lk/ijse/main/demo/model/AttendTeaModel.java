package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.controller.LoginController;
import lk.ijse.main.demo.dto.DtoAttendenceTea;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.util.CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AttendTeaModel {
    public ObservableList<DtoAttendenceTea> lordTable() throws SQLException {
        if (!chackRegiterd()) {
            String [][] results=autoSaveItems();
            System.out.println(Arrays.toString(results));
            ResultSet set=CRUD.executeQuery("SELECT * FROM Attendance_Tea ");
            ObservableList<DtoAttendenceTea> attendTeaModels= FXCollections.observableArrayList();
            while (set.next()){
                attendTeaModels.add(new DtoAttendenceTea(set.getString(1),set.getString(2),set.getString(3),set.getString(4),set.getString(5),set.getBoolean(5)));
            }
            return attendTeaModels;

        }else {
            String [][] results=autoSaveItems();
            System.out.println(Arrays.toString(results));
            ResultSet set=CRUD.executeQuery("SELECT * FROM Attendance_Tea ");
            ObservableList<DtoAttendenceTea> attendTeaModels= FXCollections.observableArrayList();
            while (set.next()){
                attendTeaModels.add(new DtoAttendenceTea(set.getString(1),set.getString(2),set.getString(3),set.getString(4),set.getString(5),set.getBoolean(5)));
            }
            return attendTeaModels;
        }

    }

    public boolean chackRegiterd() throws SQLException {
        String date = new SimpleDateFormat("EEEE").format(new Date());
        ResultSet set = CRUD.executeQuery("SELECT Teacher.Teacher_ID FROM Teacher join SMS.Class c on Teacher.Class_ID = c.Class_ID JOIN SMS.Time_Table T ON c.Subject_ID=T.Subject_ID WHERE T.day_of_week= ? ", date);
        ResultSet set1=CRUD.executeQuery("SELECT Class.Class_ID from Class join SMS.Time_Table T on Class.Time_Table_ID = T.Time_Table_ID where T.day_of_week= ? ",date);
        String sql="SELECT * FROM Attendance_Tea WHERE Date = ? AND Teacher_ID = ? AND classID = ? ";
        ArrayList<String> teacherIDs = new ArrayList<>();
        ArrayList<String> classIDs = new ArrayList<>();

        while (set.next()) {
            teacherIDs.add(set.getString(1));
            System.out.println("            teacherIDs.add(set.getString(1));");
        }
        while (set1.next()){
            classIDs.add(set1.getString(1));
        }
        for (int i = 0; i <classIDs.size() ; i++) {
            for (int j = 0; j < teacherIDs.size(); j++) {
                ResultSet set2=CRUD.executeQuery("SELECT * FROM Attendance_Tea WHERE Date = ? AND Teacher_ID = ? AND classID = ? ",date,teacherIDs.get(j),classIDs.get(i));
                while (set2.next()){
                    return true;
                }
            }
        }
        return false;

    }
    public String[][] autoSaveItems() throws SQLException{
        System.out.println("autoSaveItems");
        String date = new SimpleDateFormat("EEEE").format(new Date());
        System.out.println(date);
        ResultSet set = CRUD.executeQuery("SELECT T.Teacher_ID From Teacher T join Class C on C.Class_ID = T.Class_ID  join Time_Table TB on C.Time_Table_ID = TB.Time_Table_ID where TB.day_of_week = ?", date);
        ResultSet set1=CRUD.executeQuery("SELECT Class.Class_ID from Class join SMS.Time_Table T on Class.Time_Table_ID = T.Time_Table_ID where T.day_of_week= ? ",date);
        String sql="SELECT * FROM Attendance_Tea WHERE Date = ? AND Teacher_ID = ? AND classID = ? ";
        ArrayList<String> teacherIDs = new ArrayList<>();
        ArrayList<String> classIDs = new ArrayList<>();

        while (set.next()) {
            System.out.println("set");
            teacherIDs.add(set.getString(1));
        }
        while (set1.next()){
            System.out.println("set2");
            classIDs.add(set1.getString(1));
        }
        String [][] result=new String[teacherIDs.size()][classIDs.size()];
        for (int i = 0; i < teacherIDs.size(); i++) {
            for (int j = 0; j < classIDs.size(); j++) {
                System.out.println("Woring");
                Boolean b=CRUD.executeQuery("INSERT INTO Attendance_Tea VALUES (?,?,?,?,?,?)", new IDGenerator().getID("AT","Attend_ID","Attendance_Tea"),new SimpleDateFormat("yyyy-MM-dd").format(new Date()),AttendanceStuModel.getAdminName(LoginController.getLabel()), teacherIDs.get(i), classIDs.get(j), true);
                result[i][j]=b==true?"Inserted":"failed";

            }
        }

        return result;
    }

    public static void main(String[] args) throws SQLException{
        AttendTeaModel attendTeaModel=new AttendTeaModel();
        System.out.println(Arrays.toString(attendTeaModel.autoSaveItems()));
    }

}


