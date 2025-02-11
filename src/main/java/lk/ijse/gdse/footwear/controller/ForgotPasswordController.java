package lk.ijse.gdse.footwear.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.footwear.dao.custom.ForgotPasswordDAO;
import lk.ijse.gdse.footwear.dao.custom.impl.ForgotPasswordDAOImpl;
import lk.ijse.gdse.footwear.entity.ForgotPassword;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML
    private AnchorPane Main;

    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnChangedPassword;

    @FXML
    private Label lblCancel;

    @FXML
    private Label lblConfirmNewPassword;

    @FXML
    private Label lblNewPassword;

    @FXML
    private Label lblResetPassword;

    @FXML
    private TextField txtConfirmNewPassword;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private TextField txtUsername;

    //private ForgotPassword forgotPasswordModel = new ForgotPassword();
    ForgotPasswordDAO forgotPasswordDAO = new ForgotPasswordDAOImpl();

    @FXML
    void btnChangedPasswordOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String newPassword = txtNewPassword.getText();
        String confirmNewPassword = txtConfirmNewPassword.getText();

        // Check if fields are empty
        if ( username.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter all fields").show();
            return;
        }

        if (!isValidPassword(newPassword)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid password").show();
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return;
        }

        try {
            boolean isChangedPassword = false;
            if (newPassword.equals(confirmNewPassword)) {
                isChangedPassword = forgotPasswordDAO.changedPassword(username, confirmNewPassword);
            }else {
                new Alert(Alert.AlertType.ERROR, "Passwords do not match ").show();
                btnChangedPassword.setVisible(false);

            }if (isChangedPassword) {
                new Alert(Alert.AlertType.INFORMATION, "Password changed successfully..! ").show();

            }else {
                new Alert(Alert.AlertType.ERROR, "Password not changed ").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean isValidPassword(String newPassword) {
        if (newPassword == null || newPassword.length() < 4) {
            new Alert(Alert.AlertType.ERROR, "Password must be at least 4 characters").show();
            return false;
        }
        return true;
    }

    @FXML
    void onClickedBack(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("ELITE Footwear Management System");
        stage.show();
    }

    @FXML
    void onClickedCancel(MouseEvent event) {
        txtUsername.clear();
        txtNewPassword.clear();
        txtConfirmNewPassword.clear();
    }

    @FXML
    void txtConfirmPasswordOnAction(ActionEvent event) {
        btnChangedPasswordOnAction(event);
    }

    @FXML
    void txtNewPasswordOnAction(ActionEvent event) {
        txtConfirmNewPassword.requestFocus();
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtNewPassword.requestFocus();
    }

}
