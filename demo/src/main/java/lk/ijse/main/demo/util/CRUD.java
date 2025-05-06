package lk.ijse.main.demo.util;

import lk.ijse.main.demo.db.DbController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CRUD {
    public static <T>T executeQuery(String sql,Object...objects) throws SQLException {
        Connection connection= DbController.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql);

        for (int i = 0; i <objects.length ; i++) {
            preparedStatement.setObject(i+1,objects[i]);
        }

        if(sql.startsWith("SELECT")||sql.startsWith("select")){
            ResultSet resultSet=preparedStatement.executeQuery();
            return (T)resultSet;
        }
        else {
            Boolean b=preparedStatement.executeUpdate()>0?true:false;
            return (T)b;
        }

    }
}
