package com.example.duhakahya697637endassignment.Controller;


import com.example.duhakahya697637endassignment.Data.Database;
import com.example.duhakahya697637endassignment.Model.Order;
import com.example.duhakahya697637endassignment.Model.Product;
import com.example.duhakahya697637endassignment.ScreenManager;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductInventoryController {
    private final String title = "Wim's Music Dungeon";
    @FXML
    public TableView<Product> productsTableViewInventory;
    private Database database;
    private ObservableList<Product> products;
    @FXML
    private Button editProductButton;
    @FXML
    private Button addProductButton;
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

    public void initialize() {
        database = new Database();
        products = FXCollections.observableArrayList(database.getProducts());

        // Clear the existing data in the TableView
        productsTableViewInventory.getItems().clear();

        // Add the new data to the TableView
        productsTableViewInventory.setItems(products);

        // Enable multiple row selection
        productsTableViewInventory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void setOrdersList(ObservableList<Order> orders) {
        this.orders = orders;
    }

    @FXML
    protected void onAddProductButtonClick(ActionEvent e) throws IOException {
        ScreenManager screenManager = new ScreenManager("addProductToInventory-view.fxml", "/CSS/style-addProductToInventory.css");
        screenManager.openWindow("Add Product");

        AddProductToInventoryController addProductToInventoryController = screenManager.getFxmlLoader().getController();
        addProductToInventoryController.setProductToInventoryController(this);

        screenManager.showWindow();

    }

    @FXML
    protected void onEditProductButtonClick(ActionEvent e) throws IOException {
        Product selectedProduct = productsTableViewInventory.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            ScreenManager screenManager = new ScreenManager("editProductToInventory-view.fxml", "/CSS/style-editProductToInventory.css");
            screenManager.openWindow("Edit Product");

            EditProductToInventoryController editProductToInventoryController = screenManager.getFxmlLoader().getController();
            editProductToInventoryController.editProductToInventoryController(this);
            editProductToInventoryController.setProductForEditing(selectedProduct);  // Pass the selected product

            screenManager.showWindow();
        }

    }

    @FXML
    protected void onDeleteProductButtonClick(ActionEvent e) {
        Product selectedProducts = productsTableViewInventory.getSelectionModel().getSelectedItem();

        if (selectedProducts != null) {
            if (showDeleteConfirmation()) {
                products.removeAll(selectedProducts);
                productsTableViewInventory.getItems().removeAll(selectedProducts);
            }
        }

    }

    private boolean showDeleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected product(s)?");
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
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
