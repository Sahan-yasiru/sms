package lk.ijse.main.demo.model;

import lk.ijse.main.demo.db.DbController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageModel {
    private Connection connection;
    public boolean chackLogin(String userName,String password) throws SQLException {
        connection= DbController.getInstance().getConnection();
            String sql="SELECT User_name,Password FROM Admin WHERE User_name=? AND Password=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);

            ResultSet set=preparedStatement.executeQuery();
            if (set.next()){
                return true;
            }
            else {
                return false;
            }

    }
}

