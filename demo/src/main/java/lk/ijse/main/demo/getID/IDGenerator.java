package lk.ijse.main.demo.getID;

import lk.ijse.main.demo.db.DbController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IDGenerator {
    private Connection connection;

    public String getID(String priKEy,String name) throws SQLException {
        connection= DbController.getInstance().getConnection();
        try {
            String sql="SELECT "+priKEy+" FROM "+name+" ORDER BY "+priKEy+" DESC LIMIT 1 ";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet set=preparedStatement.executeQuery();

            int i=0;
            String temp="";
            while (set.next()){
                temp=set.getString(priKEy);
                i=Integer.parseInt(temp.substring(1));
                i+=1;

            }
            return String.format(temp.charAt(0)+"%03d", i);

        }catch (Exception e){
            return e.getMessage();
        }

    }

}
