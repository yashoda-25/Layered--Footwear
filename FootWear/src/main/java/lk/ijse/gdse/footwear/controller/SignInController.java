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
import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.UserDTO;
import lk.ijse.gdse.footwear.model.UserModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInController {

    @FXML
    private AnchorPane SignInPage;

    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnSignIn;

    @FXML
    private Label lblSignUp;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    private UserModel userModel = new UserModel();

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        String name = txtName.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields are required").show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return;
        }

        if (!isValid(name, username, email, password)) {
            new Alert(Alert.AlertType.ERROR, "Please check again..! ").show();
            return;
        }

        UserDTO userDTO = new UserDTO(0, name, username, email, password);

        saveUser(userDTO);
    }

    private void saveUser(UserDTO userDTO) {
        try {
            boolean isSaved = userModel.saveUser(userDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User saved successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "User not saved..! ").show();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValid(String name, String username, String email, String password) {
        if (username ==null || username.trim().isEmpty() || username.length() < 3) {
            new Alert(Alert.AlertType.ERROR, "Invalid username..! ").show();
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if (email == null || !email.matches(emailRegex)) {
            new Alert(Alert.AlertType.ERROR, "Invalid email..! ").show();
            return false;
        }

        if (password == null || password.length() < 4) {
            new Alert(Alert.AlertType.ERROR, "Invalid password..! ").show();
            return false;
        }
        return true; // If all validation passed
    }

    @FXML
    void onClickedBack(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sign In Page");
        stage.show();
    }

    @FXML
    void txtConfirmPasswordOnAction(ActionEvent event) {
        try {
            if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
                new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        String email = txtEmail.getText();

        try {
            if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")){
                new Alert(Alert.AlertType.ERROR, "Please enter a valid email address..! ").show();
            }
        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        try {
            if (txtName.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please enter a your name..! ").show();
            }
        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        String password = txtPassword.getText();

        try {
            if (password.length() < 4) {
                new Alert(Alert.AlertType.ERROR, "Please enter at least 4 characters long.").show();
            }
        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        try {
            if (checkUsernameExists(txtUsername.getText())) {
                new Alert(Alert.AlertType.ERROR, "Username already exists!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private boolean checkUsernameExists(String text) {
        try {
            // SQL query eka danna, e.g., username eka search karanna database eka athule
            String query = "SELECT COUNT(*) FROM user WHERE username = ?";

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            String username = "";
            stmt.setString(1,username); // username eka set karanna

            ResultSet rs = stmt.executeQuery(); // query eka run karanna

            if (rs.next()) {
                // COUNT(*) result eka check karanna
                return rs.getInt(1) > 0; // username thiyenawanam true, nathi nam false
            }
        } catch (SQLException e) {
            e.printStackTrace(); // error ekak thiyenawanam print karanna
        }
        return false; // exception ekak thiyenawanam or username nathi nam false return karanna
    }
}


