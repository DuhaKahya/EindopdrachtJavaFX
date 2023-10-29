package com.example.duhakahya697637endassignment.Controller;


import com.example.duhakahya697637endassignment.Model.Order;
import com.example.duhakahya697637endassignment.Model.Product;
import com.example.duhakahya697637endassignment.ScreenManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class OrderHistoryController {
    private final String title = "Wim's Music Dungeon";
    @FXML
    private TableView<Order> orderHistoryTableView;
    @FXML
    private TableView<Product> productsTableViewInventory;
    private ObservableList<Order> orders;
    private ScreenManager screenManager;
    private MainController mainController;
    private CreateOrderController createOrderController;
    private OrderHistoryController orderHistoryController;
    private ProductInventoryController productInventoryController;
    @FXML
    private Button CreateOrderButton;
    @FXML
    private Button ProductInventoryButton;

    private String userRole;
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void initialize(ObservableList<Order> orders) {

        orderHistoryTableView.setItems(orders);
        orderHistoryTableView.setOnMouseClicked(this::onOrderHistoryRowClick);
    }

    @FXML
    protected void onOrderHistoryRowClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            // Zorg ervoor dat een enkele klik op een rij is gebeurd

            // Haal de geselecteerde order op
            Order selectedOrder = orderHistoryTableView.getSelectionModel().getSelectedItem();

            if (selectedOrder != null) {
                // Haal de lijst van bestelde producten op uit de geselecteerde order
                List<Product> orderedProducts = selectedOrder.getOrderedProducts();

                // Wis de bestaande gegevens in productsTableViewInventory
                productsTableViewInventory.getItems().clear();

                // Voeg de bestelde producten één voor één toe aan productsTableViewInventory
                for (Product product : orderedProducts) {
                    productsTableViewInventory.getItems().add(product);
                }
            }
        }
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
