package lk.ijse.gdse.footwear.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.footwear.bo.BOFactory;
import lk.ijse.gdse.footwear.bo.custom.CustomerBO;
import lk.ijse.gdse.footwear.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.footwear.dto.CustomerDTO;
import lk.ijse.gdse.footwear.dto.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private AnchorPane CustomerView;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSendMail;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtAddress;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colNic;

    @FXML
    private TableColumn<CustomerTM, String> colPhoneNo;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblTitleCustomer;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhoneNo;

   // CustomerModel customerModel = new CustomerModel();
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id. ").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextCustomerId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtNic.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPhoneNo.setText("");
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOs =customerBO.getAll();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO: customerDTOs){
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getCustomerId(),
                    customerDTO.getName(),
                    customerDTO.getNic(),
                    customerDTO.getAddress(),
                    customerDTO.getEmail(),
                    customerDTO.getPhoneNo()
            );
            customerTMS.add(customerTM);
        }

        tblCustomer.setItems(customerTMS);
    }

    public void loadNextCustomerId() throws SQLException, ClassNotFoundException {
        try{
            String nextCustomerId = customerBO.getNextId();
            if (nextCustomerId == null || nextCustomerId.isEmpty()){
                throw new SQLException("No customer id found in database. ");
            }
            lblCustomerId.setText(nextCustomerId);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id. ").show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String phoneNo = txtPhoneNo.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhoneNo.setStyle(txtPhoneNo.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String addressPattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phoneNoPattern = "^\\d{10}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhoneNo = phoneNo.matches(phoneNoPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
            return;
        }

        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");

        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhoneNo) {
            txtPhoneNo.setStyle(txtPhoneNo.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidAddress && isValidEmail && isValidPhoneNo) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    nic,
                    address,
                    email,
                    phoneNo
            );

            boolean isSaved = customerBO.save(customerDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully..! ").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer..! ").show();
            }
        }
    }

    @FXML
    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();
        if (customerTM != null) {
            lblCustomerId.setText(customerTM.getCustomerId());
            txtName.setText(customerTM.getName());
            txtNic.setText(customerTM.getNic());
            txtAddress.setText(customerTM.getAddress());
            txtEmail.setText(customerTM.getEmail());
            txtPhoneNo.setText(customerTM.getPhoneNo());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = customerBO.delete(customerId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer..! ").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sendmail.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");
           // stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/mail_icon.png")));

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String phoneNo = txtPhoneNo.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhoneNo.setStyle(txtPhoneNo.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String addressPattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhoneNo = phoneNo.matches(phonePattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
           return;
        }

        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");

        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhoneNo) {
            txtPhoneNo.setStyle(txtPhoneNo.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidAddress && isValidEmail && isValidPhoneNo) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    nic,
                    address,
                    email,
                    phoneNo
            );

            boolean isUpdate = customerBO.update(customerDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer update successfully..! ").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer..! ").show();
            }
        }
    }

    @FXML
    void txtAddressOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtNicOnAction(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent actionEvent) {
        txtNic.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent actionEvent) {
        txtPhoneNo.requestFocus();
    }

    @FXML
    void txtPhoneNoOnAction(ActionEvent actionEvent) {

    }
}
