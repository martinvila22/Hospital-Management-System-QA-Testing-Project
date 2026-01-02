/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Auth.Register;

import Controller.Auth.BaseController;
import Model.MYSQLDatabaseOp;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Sakib
 */
public class RegisterController implements Initializable {

    @FXML
    private static Label faildmsg;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;

    @FXML
    private void handleLogin() throws Exception {
        BaseController.goToLogin();
    }

    @FXML
    private void resetLable(){
    faildmsg.setText("");
    }
    
    
    @FXML
    private void handleRegister() throws Exception {
        String email = emailField.getText();
        String pass = passField.getText();

        if ("".equals(email) || "".equals(pass)) {
            faildmsg.setText("Enter Email and Password Properly");
            return;
        }

        if (isValidEmail(email)) {


            MYSQLDatabaseOp dbOp = new MYSQLDatabaseOp();
            dbOp.handleRegister(email, pass);
        } else {
            faildmsg.setText("Invalid Email! Enter a valid email.");
        }
    }





        private static final Pattern EMAIL_PATTERN = Pattern.compile(
                "^[A-Za-z0-9_+&*-]+(?:\\.[A-Za-z0-9_+&*-]+)?@" +
                        "[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,7}$"
        );

        public static boolean isValidEmail(String email) {
            return email != null && EMAIL_PATTERN.matcher(email).matches();
        }

    public static void showError(String message) {
        faildmsg.setText(message);
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showError(faildmsg.getText());
        // TODO
    }

}
