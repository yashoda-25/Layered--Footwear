package lk.ijse.gdse.footwear.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.footwear.dto.EmployeeDTO;
import lk.ijse.gdse.footwear.dto.tm.EmployeeTM;
import lk.ijse.gdse.footwear.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private AnchorPane EmployeeView;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenerateAllReport;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSendMail;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<EmployeeTM, String> colAddress;

    @FXML
    private TableColumn<EmployeeTM, String> colEmail;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeId;

    @FXML
    private TableColumn<EmployeeTM, String> colName;

    @FXML
    private TableColumn<EmployeeTM, String> colNic;

    @FXML
    private TableColumn<EmployeeTM, String> colPhoneNo;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblTitleEmployee;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPhoneNo;

    EmployeeModel employeeModel = new EmployeeModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load employee id. ").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextEmployeeId();
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

    private void loadTableData() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeModel.getAllEmployees();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeId(),
                    employeeDTO.getName(),
                    employeeDTO.getNic(),
                    employeeDTO.getAddress(),
                    employeeDTO.getEmail(),
                    employeeDTO.getPhoneNo()
            );
            employeeTMS.add(employeeTM);
        }
       tblEmployee.setItems(employeeTMS);
    }

    private void loadNextEmployeeId() {
        try {
            String nextEmplyeeId = employeeModel.getNextEmployeeId();
            if (nextEmplyeeId == null || nextEmplyeeId.isEmpty()) {
                throw new SQLException("No employee id found in database. ");
            }
            lblEmployeeId.setText(nextEmplyeeId);
        }catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load employee id. ").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();
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
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    employeeId,
                    name,
                    nic,
                    address,
                    email,
                    phoneNo
            );

            boolean isSaved = employeeModel.saveEmployee(employeeDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to save employee..! ").show();
            }
        }

    }

    public void onClickedTable(MouseEvent mouseEvent) {
        EmployeeTM employeeTM = tblEmployee.getSelectionModel().getSelectedItem();
        if (employeeTM != null) {
            lblEmployeeId.setText(employeeTM.getEmployeeId());
            txtName.setText(employeeTM.getName());
            txtNic.setText(employeeTM.getNic());
            txtAddress.setText(employeeTM.getAddress());
            txtEmail.setText(employeeTM.getEmail());
            txtPhoneNo.setText(employeeTM.getPhoneNo());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();
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
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    employeeId,
                    name,
                    nic,
                    address,
                    email,
                    phoneNo
            );

            boolean isUpdate = employeeModel.updateEmployee(employeeDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee update successfully..! ").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update employee..! ").show();
            }
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = employeeModel.deleteEmployee(employeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer..! ").show();
            }
        }

    }

    @FXML
    void btnGenerateAllReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        txtPhoneNo.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtNic.requestFocus();
    }

    @FXML
    void txtNicOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtPhoneNoOnAction(ActionEvent event) {

    }



}
