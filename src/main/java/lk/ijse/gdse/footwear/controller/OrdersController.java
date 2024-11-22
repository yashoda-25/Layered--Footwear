package lk.ijse.gdse.footwear.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.footwear.dto.*;
import lk.ijse.gdse.footwear.dto.tm.CartTM;
import lk.ijse.gdse.footwear.model.*;

import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController  implements Initializable {

    @FXML
    private AnchorPane OrdersView;

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<String> cmbPaymentMethod;

    @FXML
    private ComboBox<String> cmbPhoneNo;

    @FXML
    private ComboBox<String> cmbProductDescription;

    @FXML
    private TableColumn<CartTM, Integer> colCartQty;

    @FXML
    private TableColumn<CartTM, String> colProductDesc;

    @FXML
    private TableColumn<CartTM, String> colProductId;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private TableColumn<CartTM, Double> colUnitPrice;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblProductQty;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtAddToCart;

    @FXML
    private TextField txtDiscount;

    private double netAmount = 0;
    private double netTotal = 0;
 //   private DecimalFormat decimalFormat = new DecimalFormat("0000.00");

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ProductModel productModel = new ProductModel();
    private final PaymentModel paymentModel = new PaymentModel();

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, "Fail to load data..! ").show();
        }
        cmbPaymentMethod.getItems().addAll("cash", "Card");
    }

    private void refreshPage() throws SQLException {
        // set order details
        lblOrderId.setText(orderModel.getNextOrderId());
        lblOrderDate.setText(LocalDate.now().toString());

        // load combo box data
        loadPhoneNo();
        loadProductDesc();

        // reset ui fields
        cmbPhoneNo.getSelectionModel().clearSelection();
        cmbProductDescription.getSelectionModel().clearSelection();
        cmbPaymentMethod.getSelectionModel().clearSelection();
        lblProductQty.setText("");
        txtAddToCart.setText("");
        lblUnitPrice.setText("");
        lblCustomerName.setText("");
        txtAddToCart.setText("");
        lblTotal.setText("0.00");
        txtDiscount.setText("");
        lblAmount.setText("0.00");
        cartTMS.clear();

        tblCart.refresh();
    }

    private void loadProductDesc() throws SQLException {
        ArrayList<String> productDesc = productModel.getAllProductsDesc();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(productDesc);
        cmbProductDescription.setItems(observableList);

    }

    private void loadPhoneNo() throws SQLException {
        ArrayList<String> phoneNos = customerModel.getAllPhoneNos();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(phoneNos);
        cmbPhoneNo.setItems(observableList);

    }

    private void setCellValues() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductDesc.setCellValueFactory(new PropertyValueFactory<>("productDesc"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("cartQty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblCart.setItems(cartTMS);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) throws SQLException {

        // Inputs
        String qtyPattern = "^[0-9]+$";
        String cartQtyString = txtAddToCart.getText();
        String selectedPhoneNo = cmbPhoneNo.getValue();
        String selectedPaymentMethod = cmbPaymentMethod.getValue();
        String selectedProductDesc = cmbProductDescription.getValue();
        String productId = orderModel.getProductIdByDescription(selectedProductDesc);
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblProductQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());

        // Validate phone number, payment method, product description selections
        if (selectedPhoneNo == null || selectedPhoneNo.isEmpty() || selectedPaymentMethod == null || selectedPaymentMethod.isEmpty() || selectedProductDesc == null || selectedProductDesc.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select all fields!").show();
            return;
        }

        // Validate and parse quantity
        if (!cartQtyString.matches(qtyPattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity. Please enter a numeric value.").show();
            return;
        }

        // Check for sufficient stock
        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough products..!").show();
            return;
        }

        txtAddToCart.setText("");

        // Calculate amount and net total
        double amount = unitPrice * cartQty;
        netAmount += amount;
        lblAmount.setText(String.format("%.2f", netAmount));


        String discountText = txtDiscount.getText();
        double discount = 0.0; // Default discount
        // Parse discount
        try {
            if (!txtDiscount.getText().trim().isEmpty()) {
                discount = Double.parseDouble(txtDiscount.getText().trim());
                if (discount < 0 || discount > 100) {
                    new Alert(Alert.AlertType.ERROR, "Invalid discount value! Enter a percentage between 0-100.").show();
                    return;
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid discount value!").show();
            return;
        }

        // Apply discount and update netTotal
        netTotal += netAmount * (1 - discount / 100);
        lblTotal.setText(String.format("%.2f", netTotal));
        txtDiscount.setText(String.format("%.2f", discount));

        // Check if the product is already in the cart and update it
        for (CartTM cartTM : cartTMS) {

            if (cartTM.getProductDesc().equals(selectedProductDesc)) {
                int updateQty = cartTM.getCartQty() + cartQty;
                cartTM.setCartQty(updateQty);
                cartTM.setTotal((unitPrice * updateQty) * (1 - discount / 100));

                tblCart.refresh();
                updateTotalLabel();
                return;
            }
        }

        // Add new product to cart
        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
                productId,
                selectedProductDesc,
                cartQty,
                unitPrice,
                netTotal,
                btn
        );

        btn.setOnAction(actionEvent -> {
            cartTMS.remove(newCartTM);
            tblCart.refresh();
            updateTotalLabel();
        });

            cartTMS.add(newCartTM);
            tblCart.refresh();
            updateTotalLabel();

        // Clear input fields
            txtAddToCart.clear();
    }

    private void updateTotalLabel () {
        double totalAmount = 0;
        for (CartTM cartTM : cartTMS) {
            totalAmount += cartTM.getTotal();
        }
            lblTotal.setText(String.format("%.2f", totalAmount));
    }

    @FXML
    void cmbPhoneNoOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedPhoneNo = cmbPhoneNo.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findCustomerByPhoneNumber(selectedPhoneNo);

        if (customerDTO != null) {
            lblCustomerName.setText(customerDTO.getName());
        }
    }

    @FXML
    void cmbProductDescriptionOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedProductDesc = cmbProductDescription.getSelectionModel().getSelectedItem();
        ProductDTO productDTO = productModel.findByProductionDescripton(selectedProductDesc);

        if (productDTO != null) {
            lblProductQty.setText(String.valueOf(productDTO.getQuantity()));
            lblUnitPrice.setText(String.valueOf(productDTO.getPrice()));
        }
    }

    @FXML
    void cmbPaymentMethodOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedPaymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem();
        PaymentDTO paymentDTO = paymentModel.findById(selectedPaymentMethod);

        if (paymentDTO != null) {
            lblAmount.setText(String.format("%.2f", paymentDTO.getAmount()));
            txtDiscount.setText(String.format("%.2f", paymentDTO.getDiscount()));
            lblTotal.setText(String.format("%.2f", paymentDTO.getAmount()));
        }
    }

    @FXML
    void btnConfirmOnAction(ActionEvent actionEvent) throws SQLException {

        // Validate cart is not empty
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add products to cart..! ").show();
            return;
        }

        // Validate customer and product selection
        String phoneNo = cmbPhoneNo.getValue();
        String productDesc = cmbProductDescription.getValue();
        if (phoneNo == null || phoneNo.isEmpty() || productDesc == null || productDesc.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer phone and product description!");
            return;
        }

        // Validate total amount
        double total;
        try {
            total = Double.parseDouble(lblTotal.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid total amount! Please check the total field.").show();
            return;
        }

        // Validate discount
        double discount = 0.0;
        try {
            String discountText = txtDiscount.getText().trim();
            if (!discountText.isEmpty()) {
                discount = Double.parseDouble(discountText);
                if (discount < 0 || discount > 100) {
                    new Alert(Alert.AlertType.ERROR, "Discount must be between 0% and 100%.").show();
                    return;
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid discount value! Please enter a numeric percentage.").show();
            return;
        }

        // Get order details
        String paymentId = paymentModel.getNextPaymentId();
        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(lblOrderDate.getText());

        CustomerDTO selectedCustomerDTO = customerModel.findCustomerByPhoneNumber(phoneNo);
        if (selectedCustomerDTO == null) {
            new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
            return;
        }

        // Prepare order details
        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
        for (CartTM cartTM : cartTMS) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getProductId(),
                    cartTM.getProductDesc(),
                    cartTM.getCartQty(),
                    cartTM.getUnitPrice(),
                    cartTM.getTotal()
            );
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        // Create PlaceOrderDTO
        PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO(
                orderId,
                selectedCustomerDTO.getCustomerId(),
                dateOfOrder,
                orderDetailsDTOS
        );

        PaymentDTO paymentDTO = new PaymentDTO(
                paymentId,
                netTotal,
                discount,
                cmbPaymentMethod.getValue(),
                Date.valueOf(LocalDate.now()),
                orderId
        );


        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
        boolean isOrderDetailsSaved = orderDetailsModel.saveOrderDetailsList(orderDetailsDTOS);
        boolean isOrderSaved = orderModel.saveOrderWithPayment(placeOrderDTO,paymentDTO);

        if (isOrderDetailsSaved && isOrderSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order successfully added..!").show();
            refreshPage();
        }else {
            new Alert(Alert.AlertType.ERROR, "Fail to save order..!").show();
        }


      /*  boolean isUpdated = orderDetailsModel.saveOrderDetailsWithStockUpdate(orderDetailsDTOS);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Order updated successfully!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Order update failed!").show();
        } */
    }

    @FXML
    void btnResetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }
}




