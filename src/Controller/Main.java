package Controller;

import Model.MYSQLDatabaseOp;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Main extends Application {

    private static Stage stageRef;
    private static String role = "";
    private static String imgURL = "";
    private static String DoctorID = "";

    File file = new File("confedintioal.data");

    private void handleLoginAutomatically() throws Exception {
        Scanner scanner = new Scanner(file);
        String fileEmail = "";
        String filePassword = "";
        if (scanner.hasNextLine()) {
            fileEmail = scanner.nextLine();
        }
        if (scanner.hasNextLine()) {
            filePassword = scanner.nextLine();
        }
        if (fileEmail == null || "".equals(fileEmail) || filePassword == null || "".equals(filePassword)) {
            System.out.println("No data found");
            return;
        }
        MYSQLDatabaseOp dbOp = new MYSQLDatabaseOp();
        dbOp.handleQueryLogin(fileEmail, filePassword);
    }

    private void autoLogin() {
        try {
            if (!file.exists()) {
                FileWriter fWriter = new FileWriter(file);
                fWriter.close();
            }
            PauseTransition pause = new PauseTransition(Duration.millis(1));
            pause.setOnFinished(event -> {
                try {
                    handleLoginAutomatically();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            });
            pause.play();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Stage getStageRef() {return stageRef;}
    public static void setStageRef(Stage stageRef1) {stageRef = stageRef1;}
    public static String getRole() {return role;}
    public static void setRole(String role1) {role = role1;}
    public static String getDoctorID() {return DoctorID;}
    public static void setDoctorID(String doctorID1){DoctorID=doctorID1;}
    public static String getImgURL() {return imgURL;}
    public static void setImgURL(String imgURL1) {imgURL = imgURL1;}

    @Override

    //database testing
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/View/Auth/Base.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setMaxHeight(593);
        primaryStage.setMaxWidth(990);
        primaryStage.setMinWidth(930);
        primaryStage.setMinHeight(490);
        primaryStage.setTitle("Hospital Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
        stageRef = primaryStage;
        autoLogin();
    }


    public static void main(String[] args) {

        launch(args);
    }

}
