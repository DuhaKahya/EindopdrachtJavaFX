package com.example.duhakahya697637endassignment;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    public void initializeOrders(ObservableList<Order> orders) {

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

        MainController mainController = screenManager.getFxmlLoader().getController();
        mainController.initializeLabels();

        // Hier geef je de orders-lijst door aan de MainController
        mainController.setOrdersList(orders);

        screenManager.showWindow();
        closeCurrentWindow(e);

    }

    @FXML
    protected void onCreateOrderButtonClick(ActionEvent e) throws IOException {

        screenManager = new ScreenManager("createOrder-view.fxml", "/CSS/style-createOrder.css");
        screenManager.openWindow(title);

        CreateOrderController createOrderController = screenManager.getFxmlLoader().getController();
        createOrderController.initialize();

        screenManager.showWindow();
        closeCurrentWindow(e);

    }

    @FXML
    protected void onProductInventoryButtonClick(ActionEvent e) throws IOException {
        screenManager = new ScreenManager("productInventory-view.fxml", "/CSS/style-productInventory.css");
        screenManager.openWindow(title);

        ProductInventoryController productInventoryController = screenManager.getFxmlLoader().getController();
        productInventoryController.initialize();

        screenManager.showWindow();
        closeCurrentWindow(e);
    }

    @FXML
    protected void onOrderHistoryButtonClick(ActionEvent e) throws IOException {
        screenManager = new ScreenManager("orderHistory-view.fxml", "/CSS/style-orderHistory.css");
        screenManager.openWindow(title);

        OrderHistoryController orderHistoryController = screenManager.getFxmlLoader().getController();
        orderHistoryController.initializeOrders(orders);

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
    public void closeCurrentWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }


}
