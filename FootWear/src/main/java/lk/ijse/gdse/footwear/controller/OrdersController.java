package lk.ijse.gdse.footwear.controller;

//import com.ctc.wstx.shaded.msv.org_jp_gr_xml.dom.XMLMaker;
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
import lk.ijse.gdse.footwear.model.CustomerModel;
import lk.ijse.gdse.footwear.model.OrderModel;
import lk.ijse.gdse.footwear.model.PaymentModel;
import lk.ijse.gdse.footwear.model.ProductModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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
    private ComboBox<String> cmbProductId;

    @FXML
    private TableColumn<CartTM, Double> colAmount;

    @FXML
    private TableColumn<CartTM, Integer> colCartQty;

    @FXML
    private TableColumn<CartTM, Double> colDiscount;

    @FXML
    private TableColumn<CartTM, String> colPaymentMethod;

    @FXML
    private TableColumn<CartTM, String> colPhoneNo;

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
    private Label lblProductName;

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
        lblOrderId.setText(orderModel.getNextOrderId());

        lblOrderDate.setText(LocalDate.now().toString());

        loadPhoneNo();
        loadProductId();

        cmbPhoneNo.getSelectionModel().clearSelection();
        cmbProductId.getSelectionModel().clearSelection();
        lblProductName.setText("");
        lblProductQty.setText("");
        txtAddToCart.setText("");
        lblUnitPrice.setText("");
        lblCustomerName.setText("");
        txtAddToCart.setText("");

        cartTMS.clear();

        tblCart.refresh();
    }

    private void loadProductId() throws SQLException {
        ArrayList<String> productIds = productModel.getAllProductIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(productIds);
        cmbProductId.setItems(observableList);

    }

    private void loadPhoneNo() throws SQLException {
        ArrayList<String> phoneNos = customerModel.getAllPhoneNos();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(phoneNos);
        cmbPhoneNo.setItems(observableList);

    }

    private void setCellValues() {
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductDesc.setCellValueFactory(new PropertyValueFactory<>("productDesc"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("cartQty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblCart.setItems(cartTMS);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedPhoneNo = cmbPhoneNo.getValue();

        if (selectedPhoneNo == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer phone number..!").show();
            return;
        }

        String selectedProductId = cmbProductId.getValue();

        if (selectedProductId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select product..! ").show();
            return;
        }

        String selectedPaymentMethod = cmbPaymentMethod.getValue();
     //   cmbPaymentMethod.getItems().addAll("Cash", "Card");

        if (selectedPaymentMethod == null) {
           new Alert(Alert.AlertType.ERROR, "Please select payment method.! ").show();
           return;
        }

        String cartQtyString = txtAddToCart.getText();

        String qtyPattern = "^[0-9]+$";

        if (!cartQtyString.matches(qtyPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid quantity..!").show();
            return;
        }

        String productDesc = lblProductName.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblProductQty.getText());

        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough products..!").show();
            return;
        }

        txtAddToCart.setText("");

        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double amount = unitPrice * cartQty;
        lblAmount.setText(String.valueOf(amount));
        double discount = txtDiscount.getText().isEmpty() ? 0.0 : Double.parseDouble(txtDiscount.getText());
      //  String payMethod = cmbPaymentMethod.getValue();
        double total = amount * (1 - discount / 100);



        for (CartTM cartTM : cartTMS) {

            if (cartTM.getProductId().equals(selectedProductId)) {
                int newQty = cartTM.getCartQty() +cartQty;
                cartTM.setCartQty(newQty);
                cartTM.setTotal((unitPrice * newQty) - discount);

                tblCart.refresh();
                updateTotalLabel();
                return;
            }
        }

        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
            selectedPhoneNo,
            selectedProductId,
            productDesc,
            cartQty,
            unitPrice,
            amount,
            discount,
            selectedPaymentMethod,
            total,
            btn
        );

        btn.setOnAction(actionEvent -> {
            cartTMS.remove(newCartTM);
            tblCart.refresh();
            updateTotalLabel();
        });

        cartTMS.add(newCartTM);
        updateTotalLabel();
    }

    private void updateTotalLabel() {
        double totalAmount = 0;
        for (CartTM cartTM : cartTMS) {
            totalAmount += cartTM.getTotal();
        }
        lblTotal.setText(String.valueOf(totalAmount));
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) throws SQLException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add products to cart..! ").show();
            return;
        }

        if (cmbPhoneNo.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer phone number for place order..!").show();
            return;
        }

        if (cmbPaymentMethod.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment method for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(lblOrderDate.getText());
        String phoneNo = cmbPhoneNo.getSelectionModel().getSelectedItem();
        Double amount = Double.parseDouble(lblAmount.getText());
        Double discount = Double.parseDouble(txtDiscount.getText());
       // String productId = cmbProductId.getSelectionModel().getSelectedItem();
        String paymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem();

        ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();

        for (CartTM cartTM : cartTMS) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(
                    orderId,
                    cartTM.getProductId(),
                    cartTM.getCartQty(),
                    cartTM.getUnitPrice()
            );

            orderDetailsDTOS.add(orderDetailsDTO);
        }

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                qty,
             //   amount,
            //    discount,
            //    paymentMethod,
                orderDetailsDTOS
        );

        boolean isSaved = orderModel.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order successfully saved..! ").show();
            refreshPage();
        }else {
            new Alert(Alert.AlertType.ERROR, "Order is fail..!").show();
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void cmbPhoneNoOnAction(ActionEvent event) throws SQLException {
        String selectedPhoneNo = cmbPhoneNo.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findById(selectedPhoneNo);

        if (customerDTO != null) {
            lblCustomerName.setText(customerDTO.getName());
        }
    }

    @FXML
    void cmbProductIdOnAction(ActionEvent event) throws SQLException {
        String selectedProductId = cmbProductId.getSelectionModel().getSelectedItem();
        ProductDTO productDTO = productModel.findById(selectedProductId);

        if (productDTO != null) {
            lblProductName.setText(productDTO.getProductDescription());
            lblProductQty.setText(String.valueOf(productDTO.getQuantity()));
            lblUnitPrice.setText(String.valueOf(productDTO.getPrice()));
        }
    }

    @FXML
    void cmbPaymentMethodOnAction(ActionEvent event) throws SQLException {
        String selectedPaymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem();
        PaymentDTO paymentDTO = paymentModel.findById(selectedPaymentMethod) ;

        if (paymentDTO != null) {
            lblAmount.setText(String.valueOf(paymentDTO.getAmount()));
            lblTotal.setText(String.valueOf(paymentDTO.getAmount()));
            txtDiscount.setText(String.valueOf(paymentDTO.getDiscount()));
        }
    }
}
