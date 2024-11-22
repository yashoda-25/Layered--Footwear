package lk.ijse.gdse.footwear.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private AnchorPane LoginPage;

    @FXML
    private Button btnLogin;

    @FXML
    private AnchorPane Main;

    @FXML
    private ImageView iconPassword;

    @FXML
    private ImageView iconUsername;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private Label lblSignIn;

    @FXML
    private Label lblText;

    @FXML
    private Label lblWelcome;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    private String UserName = "yashoda";
    private String Password = "1234";

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(username.equals(UserName) && password.equals(Password)){
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayout.fxml"));
            LoginPage.getChildren().clear();
            LoginPage.getChildren().add(load);
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Username or Password").show();
        }

    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        String password = txtPassword.getText();

        if(password.equals(Password)) {
            txtPassword.setStyle(txtPassword.getStyle() +"; -fx-border-color: null");
            btnLoginOnAction(event);
        }else{
            txtPassword.setStyle(txtPassword.getStyle() +"; -fx-border-color: red");
        }
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) throws IOException {
        String username = txtUsername.getText();

        try {
            if (username.equals(UserName)) {
                txtUsername.setStyle(txtUsername.getStyle() + ";-fx-border-color: null");
                txtPassword.setStyle(" -fx-border-color: null");
                txtPassword.requestFocus();
            } else {
                txtUsername.setStyle(txtUsername.getStyle() +";-fx-border-color: red");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void navigateSignInOnAction(MouseEvent mouseEvent) {
        try {
            // SignIn form eka load karanna
            AnchorPane signInForm = FXMLLoader.load(getClass().getResource("/view/SignIn.fxml"));
            LoginPage.getChildren().clear(); // Purana content eka clear karala
            LoginPage.getChildren().add(signInForm); // SignIn form eka add karanna
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading sign-in form").show();
        }
    }

    public void onActionForgotPassword(MouseEvent mouseEvent) {
        try {
            // SignIn form eka load karanna
            AnchorPane signInForm = FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml"));
            Main.getChildren().clear(); // Purana content eka clear karala
            Main.getChildren().add(signInForm); // SignIn form eka add karanna
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading forgot password form").show();
        }

    }
}