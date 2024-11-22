package lk.ijse.gdse.footwear.controller;

import com.ctc.wstx.io.BaseInputSource;
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

import java.io.IOException;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourcesBundle) {
       // navigateTo("/view/CustomerView.fxml");
    }

    @FXML
    void navigateBatchOnAction(ActionEvent event) {
        navigateTo("/view/Batch.fxml");
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
    void navigatePaymentOnAction(ActionEvent event) {
        navigateTo("/view/PaymentView.fxml");
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
        stage.setTitle("Sign In Page");
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


}
