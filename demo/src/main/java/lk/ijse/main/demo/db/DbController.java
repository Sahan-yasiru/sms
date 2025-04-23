package lk.ijse.main.demo.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbController{
    private static DbController dbController;
    private Connection connection;

    private DbController() throws SQLException {
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/SMS","root","082004");
    }
    public static DbController getInstance()throws SQLException{
        if(dbController==null){
            return dbController=new DbController();
        }
        return dbController;
    }
    public Connection getConnection(){
        return connection;
    }
}
