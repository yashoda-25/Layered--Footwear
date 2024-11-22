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
import lk.ijse.gdse.footwear.dto.InventoryDTO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.dto.ProductDetailsDTO;
import lk.ijse.gdse.footwear.dto.tm.ProductTM;
import lk.ijse.gdse.footwear.model.InventoryModel;
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
    private TableColumn<ProductTM, String> colInventoryDesc;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductDescription;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtInventoryDesc;

    ProductModel productModel = new ProductModel();
  //  InventoryModel inventoryModel = new InventoryModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    //    colInventoryDesc.setCellValueFactory(new PropertyValueFactory<>("inventoryDescription"));

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
     //   txtInventoryDesc.setText("");
    }

    private void loadTableData() throws SQLException {
        ArrayList<ProductDTO> productDTOs = productModel.getAllProducts();
        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();

        for (ProductDTO productDTO : productDTOs) {
        //    for (ProductDetailsDTO productDetailsDTO : productDTO.getProductDetailsDTOS()){
                ProductTM productTM = new ProductTM(
                        productDTO.getProductId(),
                        productDTO.getProductDescription(),
                        productDTO.getQuantity(),
                        productDTO.getPrice()
                      //  productDTO.get


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

        if (txtQuantity.getText().isEmpty() || txtPrice.getText().isEmpty() || txtInventoryDesc.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields. ").show();
            return;
        }

        String productId = lblProductId.getText();
        String productDescription = txtProductDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());
    //    String inventoryDescription = txtInventoryDesc.getText();


        // Create the ProductDTO and InventoryDTO
     //   String inventoryId = inventoryModel.getNextInventoryId();
     //   ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO(inventoryId, inventoryDescription, quantity, price);

      //  ArrayList<ProductDetailsDTO> productDetailsDTO = new ArrayList<>();
     //   ProductDetailsDTO.add(productDetailsDTO);

        ProductDTO productDTO = new ProductDTO(productId, productDescription, quantity, price);

      //  boolean isInventorySaved = inventoryModel.saveInventory(productDetailsDTO);
        boolean isProductSaved = productModel.saveProduct(productDTO);

        if (isProductSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Product saved successfully..! ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save product..! ").show();
        }

     /*   if (isInventorySaved) {
            new Alert(Alert.AlertType.INFORMATION, "Product saved successfully..! ").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Fail to save product. ").show();
        } */


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String productId = lblProductId.getText();
        String productDescription = txtProductDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());
      //  String inventoryDescription = txtInventoryDesc.getText();

        ProductDTO productDTO = new ProductDTO(productId, productDescription, quantity, price);
       // InventoryDTO inventoryDTO = new InventoryDTO(productDTO.getInventoryId(), inventoryDescription);


      //  boolean isInventoryUpdated = inventoryModel.updateInventory(inventoryDTO);
        boolean isProductUpdated = productModel.updateProduct(productDTO);

        if (isProductUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Product update successfully..! ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update product..! ").show();
        }

    /*    if (isInventoryUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Product updated successfully..! ").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Fail to update product. ").show();
        } */

    }


    @FXML
    void onClickedTable(MouseEvent event) {
        ProductTM productTM = tblProduct.getSelectionModel().getSelectedItem();
        if (productTM != null) {
            lblProductId.setText(productTM.getProductId());
            txtProductDescription.setText(productTM.getProductDescription());
            txtQuantity.setText(String.valueOf(productTM.getQuantity()));
            txtPrice.setText(String.valueOf(productTM.getPrice()));
         //   txtInventoryDesc.setText(productTM.getInventoryDescription());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void txtPriceOnAction(ActionEvent event) {
        txtInventoryDesc.requestFocus();
    }

    @FXML
    void txtQuantityOnAction(ActionEvent event) {
        txtPrice.requestFocus();
    }

    @FXML
    void txtProductDescOnAction(ActionEvent event) {
        txtQuantity.requestFocus();
    }

    @FXML
    void txtInventoryDesc(ActionEvent event) {

    }


}
