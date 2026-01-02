/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Patient;

import Controller.Main;
import Model.Admin;
import Model.Doctor;
import Model.User;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Sakib
 */
public class BaseUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView image;
    @FXML
    private static AnchorPane activeUI;
    @FXML
    private AnchorPane dynamicOption;
    private  static final String DOCTOR="doctor";
    private  static final String ADMIN="admin";

    @FXML
    private void changeScenePrescription(ActionEvent e) throws Exception {
        Parent prescription = FXMLLoader.load(getClass().getResource("/View/Patient/Prescriptions/Prescription.fxml"));
        activeUI.getChildren().setAll(prescription);
    }

    @FXML
    private void handleUpdateProfile(ActionEvent e) throws Exception {

        if (ADMIN.equals(Main.getRole())) {
            Parent register = FXMLLoader.load(getClass().getResource("/View/User/UpdateProfile/AdminProfile.fxml"));
            activeUI.getChildren().setAll(register);
        } else if (DOCTOR.equals(Main.getRole())) {
            Parent register = FXMLLoader.load(getClass().getResource("/View/User/UpdateProfile/DoctorProfile.fxml"));
            activeUI.getChildren().setAll(register);
        } else {
            
            Parent register = FXMLLoader.load(getClass().getResource("/View/User/UpdateProfile/UserProfile.fxml"));
            activeUI.getChildren().setAll(register);
        }

    }

    @FXML
    private void changeSceneAppoinment(ActionEvent e) throws Exception {
        appoinment();
    }

    @FXML
    private void handleSceneTakeBed(ActionEvent e) throws Exception {
        Parent register = FXMLLoader.load(getClass().getResource("/View/Patient/AllAppoinment/AllAppoinment.fxml"));
        activeUI.getChildren().setAll(register);
    }

    public void setImage(String imageUrl) {
        // Create a new Image object with the URL
        Image imagee = new Image(imageUrl);

        // Set the Image object in the ImageView
        image.setImage(imagee);
    }

    @FXML
    private void handleLogOut(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Auth/Base.fxml"));
        Scene change = new Scene(root);
        String role = Main.getRole();

        //After logout reset all Data from software
        if (DOCTOR.equals(role)) {
            Doctor.resetDoctor();
        } else if (ADMIN.equals(role)) {
            Admin.resetAdmin();
        } else {
            User.resetUser();
        }
        Main.getStageRef.setScene(change);
        try {
            Files.write(
                    Path.of("confedintioal.data"),
                    "".getBytes(StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            System.err.println("Failed to clear file");
            e.printStackTrace();
        }

    }
    

    public void addFeature() throws Exception {
        if (DOCTOR.equals(Main.getRole())) {
            Parent register = FXMLLoader.load(getClass().getResource("/View/Doctor/BaseFeature.fxml"));
            dynamicOption.getChildren().setAll(register);
        } else if (ADMIN.equals(Main.getRole())) {
            Parent register = FXMLLoader.load(getClass().getResource("/View/Admin/FXML.fxml"));
            dynamicOption.getChildren().setAll(register);
        }
    }

    public void appoinment() throws Exception {
        Parent register = FXMLLoader.load(getClass().getResource("/View/Patient/Appoinment/Appoinment.fxml"));
        activeUI.getChildren().setAll(register);
    }

    public  static void setActiveContent(Parent content) {
        activeUI.getChildren().setAll(content);
    }
    public static AnchorPane getActiveUI() {return activeUI;}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setActiveContent(activeUI);
        String result = (Main.getImgURL()!= null && !"".equals(Main.getImgURL())) ? Main.getImgURL(): "/View/images/person.png";
        setImage(result);
        try{
        if("".equals(User.getName()) || User.getName() == null || "".equals(User.getEmail()) || User.getEmail() == null ||"".equals(User.getAge()) || User.getAge() == null || "".equals(User.getGender()) || User.getGender() == null){
        Parent register = FXMLLoader.load(getClass().getResource("/View/User/UpdateProfile/UserProfile.fxml"));
            activeUI.getChildren().setAll(register);
            return;
        }}catch(Exception e){
            System.out.println(e);
        }
        try {
            addFeature();
        } catch (Exception ex) {
            Logger.getLogger(BaseUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            appoinment();
        } catch (Exception ex) {
            Logger.getLogger(BaseUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
