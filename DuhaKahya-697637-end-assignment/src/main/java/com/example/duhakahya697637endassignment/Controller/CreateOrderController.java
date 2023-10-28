package com.example.duhakahya697637endassignment.Controller;




import com.example.duhakahya697637endassignment.Data.Database;

import com.example.duhakahya697637endassignment.Model.Order;
import com.example.duhakahya697637endassignment.Model.Product;
import com.example.duhakahya697637endassignment.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CreateOrderController {

    private final String title = "Wim's Music Dungeon";
    @FXML
    public TableView<Product> orderTableView;
    private Database db;
    private ObservableList<Order> orders; // List to store orders
    private ScreenManager screenManager;
    @FXML
    private TextField FirstNameTextField;
    @FXML
    private TextField LastNameTextField;
    @FXML
    private TextField EmailAddressTextField;
    @FXML
    private TextField PhoneNumberTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button CreateOrderButton;
    @FXML
    private Button ProductInventoryButton;

    private MainController mainController;
    private CreateOrderController createOrderController;
    private OrderHistoryController orderHistoryController;
    private ProductInventoryController productInventoryController;

    private String userRole;
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void initialize() {
        db = new Database();
        orders = FXCollections.observableArrayList(); // Initialize the list of orders

    }

    // Voeg deze methode toe om producten aan de orderTableView toe te voegen
    public void addProductToOrder(Product productToAdd) {
        if (productToAdd != null) {
            AddProductToOrderController addProductToOrderController = screenManager.getFxmlLoader().getController();

            // Pas de hoeveelheid van het geselecteerde product aan in orderTableView
            productToAdd.setQuantity(addProductToOrderController.getQuantityValue());

            // Vernieuw de TableView na de wijziging
            orderTableView.refresh();

            // Hier kun je de productToAdd toevoegen aan de orderTableView
            orderTableView.getItems().add(new Product(productToAdd.getQuantity(), productToAdd.getName(), productToAdd.getCategory(), productToAdd.getPrice(), productToAdd.getDescription()));
        }
    }


    @FXML
    protected void addProductButton(ActionEvent e) throws IOException {
        screenManager = new ScreenManager("addProductToOrder-view.fxml", "/CSS/style-addProductToOrder.css");
        screenManager.openWindow("Add product to order");
        AddProductToOrderController addProductToOrderController = screenManager.getFxmlLoader().getController();

        // Hier wijs je de createOrderController aan addProductController toe
        addProductToOrderController.setCreateOrderController(this);
        addProductToOrderController.initialize();
        screenManager.showWindow();
    }

    @FXML
    protected void deleteProductButton(ActionEvent e) {
        // Haal de geselecteerde orders op uit de TableView
        ObservableList<Product> selectedOrders = orderTableView.getSelectionModel().getSelectedItems();

        if (!selectedOrders.isEmpty()) {
            // Verwijder de geselecteerde orders uit de TableView
            orderTableView.getItems().removeAll(selectedOrders);
        }
    }

    @FXML
    protected void createOrderButton(ActionEvent e) {
        // Check if all required fields are filled
        if (isInputValid()) {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setTitle("Create order");
            dialog.setHeaderText("Are you sure that you want to create an order?");

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Create a list of products (you need to implement this part based on your product selection logic)
                    List<Product> orderedProducts = createListOfOrderedProducts();

                    // Clear the orderTableView and textfields
                    orderTableView.getItems().clear();


                    // Create a new order with date and ordered products
                    Order newOrder = createOrderWithDateTime(orderedProducts);

                    // Add the new order to the list of orders
                    orders.add(newOrder);
                }
            });
        } else {
            // Display an error message if not all fields are filled
            errorLabel.setText("Please fill in all required fields.");
        }
    }

    // Check if all required fields are filled
    private boolean isInputValid() {
        String firstName = FirstNameTextField.getText();
        String lastName = LastNameTextField.getText();
        String email = EmailAddressTextField.getText();
        String phone = PhoneNumberTextField.getText();

        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phone.isEmpty();
    }


    private Order createOrderWithDateTime(List<Product> orderedProducts) {
        // Get the current dateTime
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String dateTime = currentTime.format(formatter);

        // Create the order with the dateTime and other customer information
        Order newOrder = new Order(
                dateTime,
                FirstNameTextField.getText(),
                LastNameTextField.getText(),
                EmailAddressTextField.getText(),
                PhoneNumberTextField.getText(),
                orderedProducts);


        return newOrder;
    }

    private List<Product> createListOfOrderedProducts() {
        List<Product> orderedProducts = new ArrayList<>();

        // Loop through the rows in the orderTableView
        for (Product product : orderTableView.getItems()) {
            orderedProducts.add(product);
        }

        return orderedProducts;
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
