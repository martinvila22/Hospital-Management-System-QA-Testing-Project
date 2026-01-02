package Controller;

import Model.MYSQLDatabaseOp;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.nio.charset.StandardCharsets;


public class Main extends Application {


    private static Stage stage;
    private static String role = "";
    private static String imgURL = "";
    private static String doctorID = "";

    File file = new File("confedintioal.data");

    private void handleLoginAutomatically() throws Exception {
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            String fileEmail = "";
            String filePassword = "";

            if (scanner.hasNextLine()) {
                fileEmail = scanner.nextLine();
            }
            if (scanner.hasNextLine()) {
                filePassword = scanner.nextLine();
            }

            if (fileEmail.isEmpty() || filePassword.isEmpty()) {
                System.out.println("No data found");
                return;
            }

            MYSQLDatabaseOp dbOp = new MYSQLDatabaseOp();
            dbOp.handleQueryLogin(fileEmail, filePassword);
        }
    }

    private void autoLogin() {
        try {
            if (!file.exists()) {
                Files.write(
                        file.toPath(),
                        new byte[0],
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );

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


    public  Stage getPrimaryStage() {
        return this.stage;
    }
    public static String getRole() {return role;}
    public static void setRole(String role1) {role = role1;}
    public static String getDoctorID() {return doctorID;}
    public static void setDoctorID(String doctorID1){
        doctorID =doctorID1;}
    public static String getImgURL() {return imgURL;}
    public static void setImgURL(String imgURL1) {imgURL = imgURL1;}

    @Override

    //database testing
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/View/Auth/Base.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hospital Management System");
        primaryStage.show();

        autoLogin();
    }



    public static void main(String[] args) {

        launch(args);
    }

}
