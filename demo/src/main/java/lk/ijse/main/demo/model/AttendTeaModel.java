package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.controller.LoginController;
import lk.ijse.main.demo.dto.DtoAttendenceTea;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.toggleButton.ToggleSwitch;
import lk.ijse.main.demo.util.CRUD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AttendTeaModel {

    public ObservableList<DtoAttendenceTea> loadTable() throws SQLException {
        ArrayList[] arrays = checkRegistered();
        String[] logs = autoSaveItems(arrays);

        if (logs != null) {
            System.out.println(Arrays.toString(logs));
        }

        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Tea");
        ObservableList<DtoAttendenceTea> attendanceList = FXCollections.observableArrayList();

        while (set.next()) {
            DtoAttendenceTea attendenceTea = new DtoAttendenceTea();
            attendenceTea.setAttendID(set.getString(1));
            attendenceTea.setDate(set.getString(2));
            attendenceTea.setAdminID(set.getString(3));
            attendenceTea.setTeacherID(set.getString(4));
            attendenceTea.setToggleSwitch(new ToggleSwitch(set.getBoolean(5)));
            attendenceTea.setClassID(set.getString(6));

            attendanceList.add(attendenceTea);
        }

        return attendanceList;
    }

    public ArrayList[] checkRegistered() throws SQLException {
        String dayOfWeek = new SimpleDateFormat("EEEE").format(new Date());
        ResultSet teacherSet = CRUD.executeQuery(
                "SELECT DISTINCT t.Teacher_ID FROM Teacher t JOIN Class c ON t.Class_ID = c.Class_ID " +
                        "JOIN Time_Table tt ON c.Time_Table_ID = tt.Time_Table_ID WHERE tt.day_of_week = ?",
                dayOfWeek);

        ResultSet classSet = CRUD.executeQuery(
                "SELECT DISTINCT c.Class_ID FROM Class c JOIN Time_Table t ON c.Time_Table_ID = t.Time_Table_ID WHERE t.day_of_week = ?",
                dayOfWeek);

        ArrayList<String> teacherIDs = new ArrayList<>();
        ArrayList<String> classIDs = new ArrayList<>();

        while (teacherSet.next()) {
            teacherIDs.add(teacherSet.getString(1));
        }

        while (classSet.next()) {
            classIDs.add(classSet.getString(1));
        }

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (String teacherID : teacherIDs) {
            for (String classID : classIDs) {
                ResultSet chackSet = CRUD.executeQuery("SELECT * FROM Attendance_Tea  WHERE Teacher_ID = ? AND Class_ID = ? AND Date = ?", teacherID, classID, today);
                if (chackSet.next()) {
                    return null;
                }
            }
        }
        return new ArrayList[]{teacherIDs, classIDs};
    }

    public String[] autoSaveItems(ArrayList... arrayLists) throws SQLException {
        ArrayList<String> insertedLogs = new ArrayList<>();
        if (arrayLists == null || arrayLists.length < 2 || arrayLists[0] == null || arrayLists[1] == null) {
            return null;
        } else {
            ArrayList<String> teacherIDs = arrayLists[0];
            ArrayList<String> classIDs = arrayLists[1];

            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            for (String teacherID : teacherIDs) {
                ResultSet classSet = CRUD.executeQuery("SELECT Class_ID FROM Teacher WHERE Teacher_ID = ?", teacherID);
                if (classSet.next()) {
                    String classID = classSet.getString("Class_ID");
                    ResultSet exists = CRUD.executeQuery(
                            "SELECT * FROM Attendance_Tea WHERE Date = ? AND Teacher_ID = ? AND Class_ID = ?", today, teacherID, classID);
                    if (!exists.next()) {
                        boolean inserted = CRUD.executeQuery("INSERT INTO Attendance_Tea VALUES (?, ?, ?, ?, ?, ?)",
                                new IDGenerator().getID("AT", "Attend_ID", "Attendance_Tea"),
                                today,
                                AttendStuModel.getAdminName(LoginController.getLabel()),
                                teacherID,
                                false,
                                classID);
                        if (inserted) {
                            insertedLogs.add(teacherID + " , " + classID + " , " + today);
                        }
                    }
                }
            }
        }
        return insertedLogs.toArray(new String[0]);
    }


    public void setAttendance(String attendID, Boolean state) throws SQLException {
        String sql = "UPDATE Attendance_Tea SET Status = ? WHERE Attend_ID = ?";
        CRUD.executeQuery(sql, state, attendID);
    }

    public static String getAdminName(String id) throws SQLException {
        String sql = "SELECT Admin_ID FROM Admin WHERE User_name = ?";
        ResultSet resultSet = CRUD.executeQuery(sql, id);
        String result = "";

        while (resultSet.next()) {
            result = resultSet.getString(1);
        }

        return result;
    }

    public ObservableList<String> lordClassIDS() throws SQLException {
        String sql = "SELECT Class_ID FROM Class";
        ResultSet resultSet = CRUD.executeQuery(sql);
        ObservableList<String> classIDs = FXCollections.observableArrayList();

        while (resultSet.next()) {
            classIDs.add(resultSet.getString(1));
        }

        return classIDs;
    }

    public ObservableList<String> lordTeaIDs() throws SQLException {
        String sql = "SELECT Teacher_ID FROM Teacher";
        ResultSet resultSet = CRUD.executeQuery(sql);
        ObservableList<String> teacherIDs = FXCollections.observableArrayList();

        while (resultSet.next()) {
            teacherIDs.add(resultSet.getString(1));
        }

        return teacherIDs;
    }


    public String saveAttedTea(DtoAttendenceTea dtoAttendenceTea) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Tea WHERE Date = ? AND Teacher_ID = ? AND Class_ID = ?", dtoAttendenceTea.getDate(), dtoAttendenceTea.getTeacherID(), dtoAttendenceTea.getClassID());
        if (!set.next()) {
            String sql = "INSERT INTO Attendance_Tea VALUES (?, ?, ?, ?, ?, ? )";
            Boolean b = CRUD.executeQuery(sql, dtoAttendenceTea.getAttendID(), dtoAttendenceTea.getDate(), dtoAttendenceTea.getAdminID(), dtoAttendenceTea.getTeacherID(),
                    dtoAttendenceTea.getStatus(),dtoAttendenceTea.getClassID() );

            return b == true ? "Saved" : "something went wrong !";
        } else {
            return "Already saved !";
        }

    }

    public String deleteAttedTea(String attendID) throws SQLException {
        Boolean b = CRUD.executeQuery("DELETE FROM Attendance_Tea WHERE Attend_ID = ?", attendID);
        return b == true ? "Deleted" : "something went wrong !";
    }

    public String updateAtted(DtoAttendenceTea dtoAttendenceTea) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Tea WHERE Date = ? AND Teacher_ID = ? AND Class_ID = ?", dtoAttendenceTea.getDate(), dtoAttendenceTea.getTeacherID(), dtoAttendenceTea.getClassID());
        if (!set.next()) {
            String sql = "UPDATE Attendance_Tea SET Date = ? ,Admin_ID = ? ,Teacher_ID = ? ,Class_ID = ? , Status = ? WHERE Attend_ID = ?";
            Boolean b = CRUD.executeQuery(sql, dtoAttendenceTea.getDate(), dtoAttendenceTea.getAdminID(), dtoAttendenceTea.getTeacherID(),
                    dtoAttendenceTea.getClassID(), dtoAttendenceTea.getStatus(), dtoAttendenceTea.getAttendID());

            return b == true ? "updated" : "something went wrong !";
        } else {
            return "Already saved !";
        }

    }

}