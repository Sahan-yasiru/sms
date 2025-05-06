package lk.ijse.main.demo.model;

import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageModel {

    public boolean chackLogin(String userName,String password) throws SQLException {
            String sql="SELECT User_name,Password FROM Admin WHERE User_name=? AND Password=?";
            ResultSet resultSet=CRUD.executeQuery(sql,userName,password);

            if (resultSet.next()){
                return true;
            }
            else {
                return false;
            }

    }
}

