package com.example.duhakahya697637endassignment.Controller;

import com.example.duhakahya697637endassignment.Model.Order;
import com.example.duhakahya697637endassignment.ScreenManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MainController {

    private final String title = "Wim's Music Dungeon";
    @FXML
    private Label roleLabel;
    @FXML
    private Label dateTimeMain;
    @FXML
    private Button CreateOrderButton;
    @FXML
    private Button ProductInventoryButton;
    private ScreenManager screenManager;
    private ObservableList<Order> orders;
    private MainController mainController;
    private CreateOrderController createOrderController;
    private OrderHistoryController orderHistoryController;
    private ProductInventoryController productInventoryController;

    private String userRole;
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void initialize() {
        roleLabel.setText("Your role is: " + userRole);

        // Get the current date and time and format it
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDateTime = now.format(formatter);

        dateTimeMain.setText("It is now: " + formattedDateTime);
        DisableButtons();
    }

    public void setOrdersList(ObservableList<Order> orders) {
        this.orders = orders;
    }

    @FXML
    protected void onDashboardButtonClick(ActionEvent e) throws IOException {
        screenManager = new ScreenManager("main-view.fxml", "/CSS/style-main.css");
        screenManager.openWindow(title);

        mainController = screenManager.getFxmlLoader().getController();
        mainController.setUserRole(userRole);
        mainController.initialize();

        // Hier geef je de orders-lijst door aan de MainController
        mainController.setOrdersList(orders);

        screenManager.showWindow();
        closeCurrentWindow(e);

    }

    @FXML
    protected void onCreateOrderButtonClick(ActionEvent e) throws IOException {

        screenManager = new ScreenManager("createOrder-view.fxml", "/CSS/style-createOrder.css");
        screenManager.openWindow(title);

        createOrderController = screenManager.getFxmlLoader().getController();
        createOrderController.setUserRole(userRole);
        createOrderController.DisableButtons();
        createOrderController.initialize();

        screenManager.showWindow();
        closeCurrentWindow(e);

    }

    @FXML
    protected void onProductInventoryButtonClick(ActionEvent e) throws IOException {
        screenManager = new ScreenManager("productInventory-view.fxml", "/CSS/style-productInventory.css");
        screenManager.openWindow(title);

        productInventoryController = screenManager.getFxmlLoader().getController();
        productInventoryController.setUserRole(userRole);
        productInventoryController.DisableButtons();
        productInventoryController.initialize();

        screenManager.showWindow();
        closeCurrentWindow(e);
    }

    @FXML
    protected void onOrderHistoryButtonClick(ActionEvent e) throws IOException {
        screenManager = new ScreenManager("orderHistory-view.fxml", "/CSS/style-orderHistory.css");
        screenManager.openWindow(title);

        orderHistoryController = screenManager.getFxmlLoader().getController();
        orderHistoryController.setUserRole(userRole);
        orderHistoryController.DisableButtons();
        orderHistoryController.initialize(orders);

        screenManager.showWindow();
        closeCurrentWindow(e);

    }

    @FXML
    protected void onLogoutButtonClick(ActionEvent e) throws IOException {
        screenManager = new ScreenManager("login-view.fxml", "/CSS/style-login.css");
        screenManager.openWindow(title);

        screenManager.showWindow();
        // Close the current window
        closeCurrentWindow(e);
    }
    public void DisableButtons(){
        // Disable or hide buttons based on user role
        if ("Sales".equals(userRole)) {
            // User with Sales role
            ProductInventoryButton.setDisable(true);
        } else if ("Manager".equals(userRole)) {
            // User with Manager role
            CreateOrderButton.setDisable(true);
        }
    }

    protected void closeCurrentWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }

}


