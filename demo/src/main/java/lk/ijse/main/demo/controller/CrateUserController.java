package lk.ijse.main.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lk.ijse.main.demo.dto.DtoAdmin;
import lk.ijse.main.demo.getID.IDGenerator;
import lk.ijse.main.demo.model.AddUserModel;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class CrateUserController implements Initializable {

    @FXML
    private Label labelAdminID;
    @FXML
    private TextField txtEmail,txtOpt,txtUserName,txtPassword;
    private AddUserModel addUserModel;
    private IDGenerator idGenerator;
    private String Email ="sahanyasiru460@gmail.com";
    private String passworld ="wzcy roga qusf cnaf";
    @FXML
    private Button sendEmail,optChack,btnSign;
    @FXML
    private HBox boxUser,boxPassword;
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static int opt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reLord();



    }
    public void reLord(){
        clear();
        lordID();
        txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: black;");
        txtOpt.setStyle(txtOpt.getStyle()+"-fx-border-color: black;");
        txtPassword.setStyle(txtPassword.getStyle()+"-fx-border-color: black;");
        txtUserName.setStyle(txtUserName.getStyle()+"-fx-border-color: black;");
        boxUser.setDisable(true);
        boxPassword.setDisable(true);
        btnSign.setDisable(true);

    }

    public void lordID(){
        try {
            idGenerator=new IDGenerator();
            labelAdminID.setText(idGenerator.getID("A","Admin_ID","Admin"));
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void addUser(ActionEvent actionEvent)  {
        try {
            addUserModel =new AddUserModel();
            DtoAdmin dtoAdmin=new DtoAdmin(labelAdminID.getText(),txtUserName.getText(),txtPassword.getText(),"ADMIN");
            String outPut=addUserModel.addUser(dtoAdmin);
            new Alert(Alert.AlertType.INFORMATION,outPut).show();
            reLord();
        } catch (Exception e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
           reLord();

        }

    }
    public void clear(){
        txtUserName.setText("");
        txtPassword.setText("");
        txtOpt.setText("");
        txtEmail.setText("");
    }
    public void sendEmail(ActionEvent actionEvent) {
        if(!txtEmail.getText().isEmpty()){
            Boolean flag=txtEmail.getText().matches(emailPattern);
            if (flag) {
                opt =new Random().nextInt(1001)+1000;
                boolean b=sendEmail(txtEmail.getText());
                if(b){
                    new Alert(Alert.AlertType.INFORMATION, "Mail send successfully..!").show();
                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Mail send failed...").show();
                }

            }else {
                txtEmail.setStyle(txtEmail.getStyle()+"-fx-border-color: red;");
            }
        }
    }
    public boolean sendEmail(String email) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        // create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            // override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, passworld);
            }
        };

        Session session = Session.getInstance(props, auth);
        try {
            Message mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(email));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            mimeMessage.setSubject("EDUMASTER");
            mimeMessage.setText("Welcome to EDUMASTER \t your opt number is "+opt);

            Transport.send(mimeMessage);
            return true;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to send Mail..!").show();
            e.printStackTrace();
            return false;
        }
    }
    public void chackOpt(ActionEvent actionEvent) {
        if(txtOpt.getText().equals(opt+"")){
            boxUser.setDisable(false);
            boxPassword.setDisable(false);
            btnSign.setDisable(false);

        }else {
            txtOpt.setStyle(txtOpt.getStyle()+"-fx-border-color: red;");
        }
    }

}
