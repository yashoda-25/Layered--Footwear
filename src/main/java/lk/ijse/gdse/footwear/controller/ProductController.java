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
import lk.ijse.gdse.footwear.bo.BOFactory;
import lk.ijse.gdse.footwear.bo.custom.ProductBO;
import lk.ijse.gdse.footwear.dto.ProductDTO;
import lk.ijse.gdse.footwear.dto.tm.ProductTM;

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

  //  ProductModel productModel = new ProductModel();
  //  InventoryModel inventoryModel = new InventoryModel();
    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);

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

    private void refreshPage() throws SQLException, ClassNotFoundException {
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

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> productDTOs = productBO.getAll();
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

    private void loadNextProductId() throws ClassNotFoundException {
        try{
            String nextProductId = productBO.getNextId();
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
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String productId = lblProductId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure? ", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = productBO.delete(productId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully..! ").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete product..! ").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (txtQuantity.getText().isEmpty() || txtPrice.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields. ").show();
            return;
        }

        String productId = lblProductId.getText();
        String productDescription = txtProductDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());


        ProductDTO productDTO = new ProductDTO(productId, productDescription, quantity, price);

      //  boolean isInventorySaved = inventoryModel.saveInventory(productDetailsDTO);
        boolean isProductSaved = productBO.save(productDTO);

        if (isProductSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Product saved successfully..! ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save product..! ").show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String productId = lblProductId.getText();
        String productDescription = txtProductDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());
      //  String inventoryDescription = txtInventoryDesc.getText();

        ProductDTO productDTO = new ProductDTO(productId, productDescription, quantity, price);
       // InventoryDTO inventoryDTO = new InventoryDTO(productDTO.getInventoryId(), inventoryDescription);


      //  boolean isInventoryUpdated = inventoryModel.updateInventory(inventoryDTO);
        boolean isProductUpdated = productBO.update(productDTO);

        if (isProductUpdated) {
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
         //   txtInventoryDesc.setText(productTM.getInventoryDescription());

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
