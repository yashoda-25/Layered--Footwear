package lk.ijse.gdse.footwear.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse.footwear.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML
    private Pane Dashboard;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnBatch;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnProduct;

    @FXML
    private Button btnSupplier;

    @FXML
    private AnchorPane content;

    @FXML
    private Label lblHome;

    @FXML
    private Button btnLogOut;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblOrdersCount;

    @FXML
    private Label lblProductsCount;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblCustomerCount;

    private int customerCount;
    private int ordersCount;
    private int productsCount;
    private String userName;

    @Override
    public void initialize(URL url, ResourceBundle resourcesBundle) {
        setDate();
        setTime();

        try {
            customerCount = getCustomerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(customerCount);


        try {
            ordersCount = getOrderCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setOrderCount(ordersCount);


        try {
            productsCount = getProductsCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setProductsCount(productsCount);


        try {
            String userName = getUserName(1); // Pass userId as the parameter
            if (userName != null) {
                setUserName(userName); // Update the label with the fetched username
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception
        }
    }

    private void setUserName(String userName) {
        lblUserName.setText(String.valueOf(userName));
    }

    private String getUserName(int userId) throws SQLException {
        String sql = "SELECT user_name FROM user WHERE user_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setObject(1, userId);
        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            return rst.getString("user_name");
        }

        // Return null if no record is found
        return null;
    }

    private void setProductsCount(int productsCount) {
        lblProductsCount.setText(String.valueOf(productsCount));
    }

    private int getProductsCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS products_count FROM Products";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    private void setOrderCount(int ordersCount) {
        lblOrdersCount.setText(String.valueOf(ordersCount));
    }

    private int getOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS order_count FROM Orders";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }

    private void setCustomerCount(int customerCount) {
        lblCustomerCount.setText(String.valueOf(customerCount));
    }

    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM Customer";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rst = pst.executeQuery();

        if (rst.next()) {
            return rst.getInt( 1);
        }
        return 0;
    }

    private void setTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        lblTime.setText(now.format(formatter));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void navigateCustomerOnAction(ActionEvent event) {
        navigateTo("/view/CustomerView.fxml");
    }

    @FXML
    void navigateEmployeeOnAction(ActionEvent event) {
        navigateTo("/view/EmployeeView.fxml");
    }

    @FXML
    void navigateInventoryOnAction(ActionEvent event) {
        navigateTo("/view/InventoryView.fxml");
    }

    @FXML
    void navigateOrdersOnAction(ActionEvent event) {
        navigateTo("/view/OrdersView.fxml");
    }

    @FXML
    void navigateProductOnAction(ActionEvent event) {
        navigateTo("/view/ProductView.fxml");
    }

    @FXML
    void navigateSupplierOnAction(ActionEvent event) {
        navigateTo("/view/SupplierView.fxml");
    }



    @FXML
    void onClickedLogOut(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Layout");
        stage.show();
    }

    public void navigateTo(String fxmlPath){
        try{
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            content.getChildren().add(load);
        }catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Fail to load page! ").show();
        }
    }

    @FXML
    void btnSettingOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ForgotPassword.fxml"));
        AnchorPane anchorPane = loader.load();

        ForgotPasswordController forgotPasswordController =loader.getController();
        ActionEvent username = null;
        forgotPasswordController.txtUsernameOnAction(username);

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setScene(anchorPane.getScene());
        stage.centerOnScreen();
        stage.setTitle("Main Layout");
        stage.show();

        Stage currentStage = (Stage) content.getScene().getWindow();
        currentStage.hide();
    }




}
