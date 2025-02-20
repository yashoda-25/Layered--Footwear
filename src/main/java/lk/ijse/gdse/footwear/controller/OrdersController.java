package lk.ijse.gdse.footwear.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.footwear.bo.BOFactory;
import lk.ijse.gdse.footwear.bo.custom.*;
import lk.ijse.gdse.footwear.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.footwear.db.DBConnection;
import lk.ijse.gdse.footwear.dto.*;
import lk.ijse.gdse.footwear.dto.tm.CartTM;
import lk.ijse.gdse.footwear.entity.Payment;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

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
    private Button btnBill;

    @FXML
    private TextField txtDiscount;

    private double netAmount = 0;
    private double netTotal = 0;
    //   private DecimalFormat decimalFormat = new DecimalFormat("0000.00");

    OrderDetailsBO orderDetailsBO = (OrderDetailsBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER_DETAILS);
    // private final OrderModel orderModel = new OrderModel();
    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    //  private final CustomerModel customerModel = new CustomerModel();
    //  private final ProductModel productModel = new ProductModel();
    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);
    // private final PaymentModel paymentModel = new PaymentModel();
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..! ").show();
        }
        cmbPaymentMethod.getItems().addAll("cash", "Card");
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        // set order details
        lblOrderId.setText(orderBO.getNextId());
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
        netTotal = 0;
        netAmount = 0;
    }

    private void loadProductDesc() throws SQLException, ClassNotFoundException {
        ArrayList<String> productDesc = productBO.getAllProductsDesc();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(productDesc);
        cmbProductDescription.setItems(observableList);

    }

    private void loadPhoneNo() throws SQLException, ClassNotFoundException {
        ArrayList<String> phoneNos = customerBO.getAllPhoneNos();
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
    void btnAddToCartOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        // Inputs
        String qtyPattern = "^[0-9]+$";
        String cartQtyString = txtAddToCart.getText();
        String selectedPhoneNo = cmbPhoneNo.getValue();
        String selectedPaymentMethod = cmbPaymentMethod.getValue();
        String selectedProductDesc = cmbProductDescription.getValue();

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

        String productId = orderBO.getProductIdByDescription(selectedProductDesc);
        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblProductQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());

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

        // Apply discount to item total
        double discountedAmount = amount * (1 - discount / 100);
        // System.out.println("Discounted Amount: " + total);
        // netTotal += discountedAmount;
        // System.out.println("Updated Total: " + netTotal);

        netTotal += discountedAmount;
        lblTotal.setText(String.format("%.2f", netTotal));
        txtDiscount.setText(String.format("%.2f", discount));

        // Apply discount and update netTotal
        //  netTotal += netAmount * (1 - discount / 100);
        //  lblTotal.setText(String.format("%.2f", netTotal));
        // txtDiscount.setText(String.format("%.2f", discount));


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
                discountedAmount,
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
        txtDiscount.clear();

    }

    private void updateTotalLabel() {
        double totalAmount = 0;
        for (CartTM cartTM : cartTMS) {
            totalAmount += cartTM.getTotal();
        }
        lblTotal.setText(String.format("%.2f", totalAmount));
    }


    @FXML
    void cmbPhoneNoOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedPhoneNo = cmbPhoneNo.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerBO.findCustomerByPhoneNumber(selectedPhoneNo);

        if (customerDTO != null) {
            lblCustomerName.setText(customerDTO.getName());
        }
    }

    @FXML
    void cmbProductDescriptionOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedProductDesc = cmbProductDescription.getSelectionModel().getSelectedItem();
        ProductDTO productDTO = productBO.findByProductionDescripton(selectedProductDesc);

        if (productDTO != null) {
            lblProductQty.setText(String.valueOf(productDTO.getQuantity()));
            lblUnitPrice.setText(String.valueOf(productDTO.getPrice()));
        }
    }

    @FXML
    void cmbPaymentMethodOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedPaymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem();
        PaymentDTO paymentDTO = paymentBO.findById(selectedPaymentMethod);

//        if (paymentDTO != null) {
//            lblAmount.setText(String.format("%.2f", paymentDTO.getAmount()));
//            txtDiscount.setText(String.format("%.2f", paymentDTO.getDiscount()));
//            lblTotal.setText(String.format("%.2f", paymentDTO.getAmount()));
//        }
    }

    @FXML
    void btnConfirmOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

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
        String paymentId = paymentBO.getNextId();
        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(lblOrderDate.getText());

        CustomerDTO selectedCustomerDTO = customerBO.findCustomerByPhoneNumber(phoneNo);
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


        boolean isOrderSaved = orderBO.saveOrderWithPayment(placeOrderDTO, paymentDTO);
        if (!isOrderSaved) {
            System.out.println("Failed to save order or payment.");
            new Alert(Alert.AlertType.ERROR, "Fail to save order..!").show();
            return;
        }
//        boolean isOrderDetailsSaved = orderDetailsBO.saveOrderDetailsList(orderDetailsDTOS);

//        if (isOrderDetailsSaved) {
        new Alert(Alert.AlertType.INFORMATION, "Order details successfully added..!").show();
        refreshPage();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Fail to save order..!").show();
//        }
    }


    @FXML
    void btnBillOnAction(ActionEvent event) {
        try {
            // Load Jasper Report Design
            InputStream reportStream = getClass().getResourceAsStream("/Report/Receipt.jrxml");
            if (reportStream == null) {
                new Alert(Alert.AlertType.ERROR, "Report file not found!").show();
                return;
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Check if an order is selected
            if (tblCart.getSelectionModel().getSelectedItem() == null) {
                new Alert(Alert.AlertType.WARNING, "Please select an order first!").show();
                return;
            }

            // Get Selected Order ID
            Object selectedOrderId = tblCart.getSelectionModel().getSelectedItem().getOrderId();

            // Pass Parameters to the Report
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("order_id", selectedOrderId);

            // Get Database Connection
            try (Connection connection = DBConnection.getInstance().getConnection()) {
                // Fill Report
                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parameters,
                        connection
                );

                // View the Report
                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                viewer.setVisible(true);

            }

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Error generating bill report: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database connection error: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }



  /*  @FXML
    void btnBillOnAction(ActionEvent event) {
        try {
            // Load Jasper Report Design from the .jrxml file
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Report/Receipt.jrxml");

            // Compile the Jasper report into a report object
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            // Prepare data to pass to the report, using a Map to store the report parameters
            Map<String, Object> data = new HashMap<>();
            data.put("Order", lblOrderId.getText()); // Put the order ID value from a label into the report data
            System.out.println(data); // For debugging: prints the data map to console

            // Fill the report with data from the database
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DBConnection.getInstance().getConnection());

            // Optionally view the report (commented out)
            // JasperViewer.viewReport(jasperPrint, true);

            // Send the report directly to the printer
            JasperPrintManager.printReport(jasperPrint, true);

        } catch (NullPointerException e) {
            // Show an alert if an order is not selected
            new Alert(Alert.AlertType.WARNING, "Please select an order first!").show();
        } catch (JRException e) {
            // Show an alert if there is an error generating the report
            new Alert(Alert.AlertType.ERROR, "Error generating bill report...!").show();
        } catch (SQLException e) {
            // Show an alert if there is a database connection error
            new Alert(Alert.AlertType.ERROR, "Database connection error...!").show();
        }
    } */


    @FXML
    void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }


}




