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
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.dto.tm.ProductTM;
import lk.ijse.gdse.footwear.model.ProductModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private AnchorPane ProductView;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<ProductTM, Double> colPrice;

    @FXML
    private TableColumn<ProductTM, String> colProductDescription;

    @FXML
    private TableColumn<ProductTM, String> colProductId;

    @FXML
    private TableColumn<ProductTM, Integer> colQuantity;

    @FXML
    private Label lblProductId;

    @FXML
    private Label lblTitleProduct;

    @FXML
    private TableView<ProductTM> tblProduct;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductDescription;

    @FXML
    private TextField txtQuantity;

    ProductModel productModel = new ProductModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load product id. ");
        }
    }

    private void refreshPage() throws SQLException {
        loadNextProductId();
        loadTableData();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        txtProductDescription.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<ProductDTO> productDTOs = productModel.getAllProducts();
        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();

        for (ProductDTO productDTO: productDTOs){
            ProductTM productTM = new ProductTM(
                    productDTO.getProductId(),
                    productDTO.getProductDescription(),
                    productDTO.getQuantity(),
                    productDTO.getPrice()
            );
            productTMS.add(productTM);
        }

        tblProduct.setItems(productTMS);
    }

    private void loadNextProductId() {
        try{
            String nextProductId = productModel.getNextProductId();
            if (nextProductId == null || nextProductId.isEmpty()){
                throw new SQLException("No product id found in database. ");
            }
            lblProductId.setText(nextProductId);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load product id. ").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String productId = lblProductId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = productModel.deleteProduct(productId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete product..! ").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String productId = lblProductId.getText();
        String productDescription = txtProductDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());

        ProductDTO productDTO = new ProductDTO(productId, productDescription, quantity, price);

        boolean isSaved = productModel.saveProduct(productDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Product saved successfully..! ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save product..! ").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String productId = lblProductId.getText();
        String productDescription = txtProductDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());

        ProductDTO productDTO = new ProductDTO(productId, productDescription, quantity, price);

        boolean isUpdate = productModel.updateProduct(productDTO);
        if (isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Product update successfully..! ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update product..! ").show();
        }
    }


    @FXML
    void onClickedTable(MouseEvent event) {
        ProductTM productTM = tblProduct.getSelectionModel().getSelectedItem();
        if (productTM != null) {
            lblProductId.setText(productTM.getProductId());
            txtProductDescription.setText(productTM.getProductDescription());
            txtQuantity.setText(String.valueOf(productTM.getQuantity()));
            txtPrice.setText(String.valueOf(productTM.getPrice()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void txtPriceOnAction(ActionEvent event) {

    }

    @FXML
    void txtQuantityOnAction(ActionEvent event) {
        txtPrice.requestFocus();
    }

    @FXML
    void txtProductDescOnAction(ActionEvent event) {
        txtQuantity.requestFocus();
    }

}
