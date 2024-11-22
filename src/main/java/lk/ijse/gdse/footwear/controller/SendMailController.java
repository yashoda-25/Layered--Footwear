package lk.ijse.gdse.footwear.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @Setter
    private String customerEmail;


    @FXML
    void SendUsingGmailOnAction(ActionEvent event) {
        if (customerEmail == null || customerEmail.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Customer email is not set!").show();
            return;
        }

        final String FROM = "yashodagunawardhana15@gmail.com";
        final String PASSWORD = "qeli hiiz auct ajf";

        String subject = txtSubject.getText();
        String body = txtBody.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Subject and body must not be empty").show();
            return;
        }
        sendEmailWithGmail(FROM, PASSWORD, customerEmail, subject, body);

    }

    private void sendEmailWithGmail(String from, String password, String to, String subject, String messageBody) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email. Please check the details and try again.").show();
        }
    }


    @FXML
    void txtBodyOnAction(MouseEvent event) {

    }

    @FXML
    void txtSubjectOnAction(ActionEvent event) {

    }

}



