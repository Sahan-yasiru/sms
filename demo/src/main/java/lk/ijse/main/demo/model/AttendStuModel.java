package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.controller.LoginController;
import lk.ijse.main.demo.dto.DtoAttendenceStu;
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

public class AttendStuModel {

    public ObservableList<DtoAttendenceStu> loadTable() throws SQLException {
        ArrayList[] arrays = checkRegistered();
        String[] logs = autoSaveItems(arrays);

        if (logs != null) {
            System.out.println(Arrays.toString(logs));
        }

        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Stu");
        ObservableList<DtoAttendenceStu> attendanceList = FXCollections.observableArrayList();

        while (set.next()) {
            attendanceList.add(new DtoAttendenceStu(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5),
                    new ToggleSwitch(set.getBoolean(6))
            ));
        }

        return attendanceList;
    }

    public ArrayList[] checkRegistered() throws SQLException {
        String dayOfWeek = new SimpleDateFormat("EEEE").format(new Date());
        ResultSet teacherSet = CRUD.executeQuery(
                "SELECT DISTINCT S.Student_ID FROM Student S JOIN Class c ON c.Class_ID = S.Class_ID  " +
                        " JOIN Time_Table tt ON c.Time_Table_ID = tt.Time_Table_ID WHERE tt.day_of_week = ? ",
                dayOfWeek);

        ResultSet classSet = CRUD.executeQuery(
                "SELECT DISTINCT c.Class_ID FROM Class c JOIN Time_Table t ON c.Time_Table_ID = t.Time_Table_ID WHERE t.day_of_week = ?",
                dayOfWeek);

        ArrayList<String> studentIds = new ArrayList<>();
        ArrayList<String> classIDs = new ArrayList<>();

        while (teacherSet.next()) {
            studentIds.add(teacherSet.getString(1));
        }

        while (classSet.next()) {
            classIDs.add(classSet.getString(1));
        }

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (String studentID : studentIds) {
            for (String classID : classIDs) {
                ResultSet chackSet = CRUD.executeQuery("SELECT * FROM Attendance_Stu  WHERE Student_ID = ? AND Class_ID = ? AND Date = ?", studentID, classID, today);
                if (chackSet.next()) {
                    return null;
                }
            }
        }
        return new ArrayList[]{studentIds, classIDs};
    }

    public String[] autoSaveItems(ArrayList... arrayLists) throws SQLException {
        ArrayList<String> insertedLogs = new ArrayList<>();
        if (arrayLists == null || arrayLists.length < 2 || arrayLists[0] == null || arrayLists[1] == null) {
            return null;
        } else {
            ArrayList<String> studentIDs = arrayLists[0];
            ArrayList<String> classIDs = arrayLists[1];

            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            for (String studentID : studentIDs) {
                ResultSet classSet = CRUD.executeQuery("SELECT Class_ID FROM Student WHERE Student_ID = ?", studentID);
                if (classSet.next()) {
                    String classID = classSet.getString("Class_ID");
                    ResultSet exists = CRUD.executeQuery(
                            "SELECT * FROM Attendance_Stu WHERE Date = ? AND Student_ID = ? AND Class_ID = ?", today, studentID, classID);
                    if (!exists.next()) {
                        boolean inserted = CRUD.executeQuery("INSERT INTO Attendance_Stu VALUES (?, ?, ?, ?, ?, ?)",
                                new IDGenerator().getID("AT", "Attend_ID", "Attendance_Stu"),
                                today,
                                AttendStuModel.getAdminName(LoginController.getLabel()),
                                studentID,
                                classID,
                                false);
                        if (inserted) {
                            insertedLogs.add(studentID + " , " + classID + " , " + today);
                        }
                    }
                }
            }
        }
        return insertedLogs.toArray(new String[0]);
    }


    public void setAttendance(String attendID, Boolean state) throws SQLException {
        String sql = "UPDATE Attendance_Stu SET Status = ? WHERE Attend_ID = ?";
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

    public ObservableList<String> lordStuIDs() throws SQLException {
        String sql = "SELECT Student_ID FROM Student";
        ResultSet resultSet = CRUD.executeQuery(sql);
        ObservableList<String> studentIDs = FXCollections.observableArrayList();

        while (resultSet.next()) {
            studentIDs.add(resultSet.getString(1));
        }

        return studentIDs;
    }


    public String saveAttedStu(DtoAttendenceStu dtoAttendenceStu) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Stu WHERE Date = ? AND Student_ID = ? AND Class_ID = ?", dtoAttendenceStu.getDate(), dtoAttendenceStu.getStudentID(), dtoAttendenceStu.getClassID());
        if (!set.next()) {
            String sql = "INSERT INTO Attendance_Stu VALUES (?, ?, ?, ?, ?, ? )";
            Boolean b = CRUD.executeQuery(sql, dtoAttendenceStu.getAttendID(), dtoAttendenceStu.getDate(), dtoAttendenceStu.getAdminID(), dtoAttendenceStu.getStudentID(),
                    dtoAttendenceStu.getClassID(), dtoAttendenceStu.getStatus());

            return b == true ? "Saved" : "something went wrong !";
        } else {
            return "Already saved !";
        }

    }

    public String deleteAttedStu(String attendID) throws SQLException {
        Boolean b = CRUD.executeQuery("DELETE FROM Attendance_Stu WHERE Attend_ID = ?", attendID);
        return b == true ? "Deleted" : "something went wrong !";
    }

    public String updateAtted(DtoAttendenceStu dtoAttendenceStu) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT * FROM Attendance_Stu WHERE Date = ? AND Student_ID = ? AND Class_ID = ?", dtoAttendenceStu.getDate(), dtoAttendenceStu.getStudentID(), dtoAttendenceStu.getClassID());
        if (!set.next()) {
            String sql = "UPDATE Attendance_Stu SET Date = ? ,Admin_ID = ? ,Student_ID = ? ,Class_ID = ? , Status = ? WHERE Attend_ID = ?";
            Boolean b = CRUD.executeQuery(sql, dtoAttendenceStu.getDate(), dtoAttendenceStu.getAdminID(), dtoAttendenceStu.getStudentID(),
                    dtoAttendenceStu.getClassID(), dtoAttendenceStu.getStatus(), dtoAttendenceStu.getAttendID());

            return b == true ? "updated" : "something went wrong !";
        } else {
            return "Already saved !";

        }

    }

}