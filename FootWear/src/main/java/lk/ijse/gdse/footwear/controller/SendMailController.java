package lk.ijse.gdse.footwear.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

public class SendMailController {

    @FXML
    private Button btnSendUsingGmail;

    @FXML
    private Button btnSendUsingSendgrid;

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @Setter
    private String customerEmail;


    @FXML
    void SendUsingGmailOnAction(ActionEvent event) {

    }

    @FXML
    void sendUsingSendgridOnAction(ActionEvent event) {
        if (customerEmail == null) {
            return;
        }


    }

    @FXML
    void txtBodyOnAction(MouseEvent event) {

    }

    @FXML
    void txtSubjectOnAction(ActionEvent event) {

    }

}
