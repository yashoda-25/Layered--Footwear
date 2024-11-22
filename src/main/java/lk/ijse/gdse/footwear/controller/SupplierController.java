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
import lk.ijse.gdse.footwear.dto.SupplierDTO;
import lk.ijse.gdse.footwear.dto.tm.SupplierTM;
import lk.ijse.gdse.footwear.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private AnchorPane SupplierView;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnGenerateAllReport;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierTM, String> colAddress;

    @FXML
    private TableColumn<SupplierTM, String> colEmail;

    @FXML
    private TableColumn<SupplierTM, String> colName;

    @FXML
    private TableColumn<SupplierTM, String> colNic;

    @FXML
    private TableColumn<SupplierTM, String> colPhoneNo;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;

    @FXML
    private Label lblSupplierId;

    @FXML
    private Label lblTitleSupplier;

    @FXML
    private TableView<SupplierTM> tblSupplier;

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


    SupplierModel supplierModel = new SupplierModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        try {
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load supplier id. ").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextSupplierID();
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

    private void loadNextSupplierID() {
        try{
            String nextSupplierId = supplierModel.getNextSupplierId();
            if(nextSupplierId == null || nextSupplierId.isEmpty()){
                throw new SQLException("No supplier id found in database. ");
            }
            lblSupplierId.setText(nextSupplierId);
        }catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load supplier id. ").show();
        }
    }

    private void loadTableData() throws SQLException {
        ArrayList<SupplierDTO> supplierDTOS = supplierModel.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOS) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplierId(),
                    supplierDTO.getName(),
                    supplierDTO.getNic(),
                    supplierDTO.getAddress(),
                    supplierDTO.getEmail(),
                    supplierDTO.getPhoneNo()
            );
            supplierTMS.add(supplierTM);
        }
        tblSupplier.setItems(supplierTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierModel.deleteSupplier(supplierId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete supplier..! ").show();
            }
        }
    }

    @FXML
    void btnGenerateAllReport(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();
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

        if(isValidName && isValidNic && isValidAddress && isValidEmail && isValidPhoneNo){
            SupplierDTO supplierDTO = new SupplierDTO(
                    supplierId,
                    name,
                    nic,
                    address,
                    email,
                    phoneNo
            );
            boolean isSaved = supplierModel.saveSupplier(supplierDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save supplier..! ").show();
            }
        }

    }

    @FXML
    void onClickedTable(MouseEvent event) {
        SupplierTM supplierTM = tblSupplier.getSelectionModel().getSelectedItem();
        if (supplierTM != null) {
            lblSupplierId.setText(supplierTM.getSupplierId());
            txtName.setText(supplierTM.getName());
            txtNic.setText(supplierTM.getNic());
            txtAddress.setText(supplierTM.getAddress());
            txtEmail.setText(supplierTM.getEmail());
            txtPhoneNo.setText(supplierTM.getPhoneNo());

            btnSave.setDisable(true);

            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierId.getText();
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

        if(isValidName && isValidNic && isValidAddress && isValidEmail && isValidPhoneNo){
            SupplierDTO supplierDTO = new SupplierDTO(
                    supplierId,
                    name,
                    nic,
                    address,
                    email,
                    phoneNo
            );
            boolean isUpdate = supplierModel.updateSupplier(supplierDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier update successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to update supplier..! ").show();
            }
        }
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
