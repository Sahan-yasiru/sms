package lk.ijse.main.demo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.main.demo.controller.LoginController;
import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.dto.DtoAdmin;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsModel {


    public String AdminUpdate(DtoAdmin dtoAdmin, Boolean type) throws SQLException {
        if (type) {
            String sql = "UPDATE Admin SET User_name=?,Password=?,AdminType= ?  WHERE Admin_ID=?";
            Boolean b = CRUD.executeQuery(sql, dtoAdmin.getUserName(), dtoAdmin.getPassword(), dtoAdmin.getAdminType(), dtoAdmin.getAdminID());
            return b == true ? "Success" : "Failed";
        }
        String sql = "UPDATE Admin SET User_name=?,Password=?,AdminType= ?  WHERE Admin_ID=?";
        Boolean b = CRUD.executeQuery(sql, dtoAdmin.getUserName(), dtoAdmin.getPassword(), dtoAdmin.getAdminType(), dtoAdmin.getAdminID());
        return b == true ? "Success" : "Failed";
    }

    public String deleteAdmin(String adminID) throws SQLException {
        Connection connection= DbController.getInstance().getConnection();
        if (adminID.isEmpty()) {
            return "Record is Empty";
        } else {
            String sql = "DELETE FROM Admin WHERE Admin_ID=?";
            Boolean b = CRUD.executeQuery(sql, adminID);
            return b == true ? "Success" : "Failed";
        }
    }

    public String getNumberOfAdmin() throws SQLException {
        String sql = "SELECT COUNT(Admin_ID) AS Number_of FROM Admin";

        ResultSet resultSet = CRUD.executeQuery(sql);
        String result = "";
        if (resultSet.next()) {
            result = resultSet.getString("Number_of");
        }
        return result;

    }

    public ObservableList<DtoAdmin> lordTable(Boolean type) throws SQLException {
        ResultSet set = null;
        String sql = "SELECT * FROM Admin ";
        if (type) {
            set = CRUD.executeQuery(sql);
        } else {
            sql += "WHERE User_Name = ?";
            set = CRUD.executeQuery(sql, LoginController.getLabel());
        }
        ObservableList<DtoAdmin> dtoAdmins = FXCollections.observableArrayList();
        while (set.next()) {
            String id = set.getString(1);
            String name = set.getString(2);
            String password = getNumberOfStarts(id);
            String typeS = set.getString(4);

            dtoAdmins.add(new DtoAdmin(id, name, password, typeS));
        }
        return dtoAdmins;
    }

    public String getNumberOfStarts(String id) throws SQLException {
        ResultSet set = CRUD.executeQuery("SELECT Password FROM Admin WHERE Admin_ID = ?", id);
        String star = "";
        while (set.next()) {
            for (int i = 0; i < set.getString(1).length(); i++) {
                star += "*";
            }
        }
        return star;


    }

    public ObservableList<DtoAdmin> searchUser(String anything) throws SQLException {
        String sql = "SELECT  * FROM Admin WHERE Admin_ID =? or User_name =? or Password=? or AdminType = ?";

        ResultSet resultSet = CRUD.executeQuery(sql, anything, anything, anything);
        ObservableList<DtoAdmin> dtoAdmins = FXCollections.observableArrayList();

        while (resultSet.next()) {
            dtoAdmins.add(new DtoAdmin(resultSet.getString("Admin_ID"), resultSet.getString("User_name"), resultSet.getString("Password"), resultSet.getString("AdminType")));
        }
        return dtoAdmins;

    }

    public boolean chackAdminType(String adminUserName) throws SQLException {
        String sql = "SELECT AdminType FROM Admin WHERE User_name = ?";
        ResultSet set = CRUD.executeQuery(sql, adminUserName);
        String result = null;
        while (set.next()) {
            result = set.getString(1);
        }
        return result.equals("SuperAdmin") ? true : false;

    }

    public String getpassowold(String id) throws SQLException {

        ResultSet set = CRUD.executeQuery("SELECT Password FROM Admin WHERE Admin_ID = ? ", id);

        return set.next() ? set.getString(1) : null;

    }

}
