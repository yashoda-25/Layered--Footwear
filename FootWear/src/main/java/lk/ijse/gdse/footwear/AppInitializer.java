package lk.ijse.gdse.footwear;

//import com.sun.javafx.tk.quantum.PaintRenderJob;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }
  //  private AnchorPane ANKLoginPage;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"))));
        stage.setTitle("Login Page");
        stage.centerOnScreen();
        stage.show();


       // Stage Stage = (Stage) ANKLoginPage.getScene().getWindow();
      //  Stage.close();

//        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setTitle("ABC Exam Management System");
//        Image image = new Image(getClass().getResourceAsStream("/imege/logo-removebg-preview.png"));
//        stage.getIcons().add(image);
//        stage.setScene(scene);
//        stage.show();

    }
}