package lk.ijse.main.demo.getID;

import lk.ijse.main.demo.db.DbController;
import lk.ijse.main.demo.util.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IDGenerator {
    private Connection connection;

    public String getID(String statWith,String priKEy,String name) throws SQLException {
            String sql="SELECT "+priKEy+" FROM "+name+" ORDER BY "+priKEy+" DESC LIMIT 1 ";
            ResultSet set= CRUD.executeQuery(sql);
            int i=0;
            String temp="";
            while (set.next()){
                temp=set.getString(priKEy);
                i = Integer.parseInt(temp.replaceAll("\\D+", "")) + 1; // extract number and increment                i+=1;

            }
            return String.format(statWith+"%03d", i);

    }

}
