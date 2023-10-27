package com.example.duhakahya697637endassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductToOrderController {
    @FXML
    public TableView<Product> allProductsTableView;
    private Database database;
    private ObservableList<Product> products;
    private CreateOrderController createOrderController;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Label errorLabel;

    public void setCreateOrderController(CreateOrderController createOrderController) {
        this.createOrderController = createOrderController;
    }

    protected void initialize() {
        database = new Database();
        products = FXCollections.observableArrayList(database.getProducts());

        // Clear the existing data in the TableView
        allProductsTableView.getItems().clear();

        // Add the new data to the TableView
        allProductsTableView.setItems(products);
    }

    public int getQuantityValue() {
        String quantityText = quantityTextField.getText().trim(); // Trim leading/trailing white spaces

        if (quantityText.isEmpty()) {
            errorLabel.setText("Please enter a quantity.");
            return -1; // Indicate an error condition
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                errorLabel.setText("Quantity must be greater than zero.");
                return -1; // Indicate an error condition
            }
            return quantity;
        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter a valid integer for the quantity.");
            return -1; // Indicate an error condition
        }
    }

    @FXML
    protected void addToOrderButtonClick(ActionEvent e) {
        // Haal de geselecteerde rij op uit allProductsTableView
        Product selectedProductToAdd = allProductsTableView.getSelectionModel().getSelectedItem();

        if (selectedProductToAdd != null) {

            int quantity = getQuantityValue();

            if (quantity > 0 && quantity <= selectedProductToAdd.getQuantity()) {
                // Maak een kopie van het geselecteerde product
                Product productToAdd = new Product(selectedProductToAdd.getQuantity(), selectedProductToAdd.getName(),
                        selectedProductToAdd.getCategory(), selectedProductToAdd.getPrice(), selectedProductToAdd.getDescription());

                // Roep een methode in CreateOrderController aan om het product toe te voegen
                createOrderController.addProductToOrder(productToAdd);

                // Pas de hoeveelheid van het geselecteerde product aan
                selectedProductToAdd.setQuantity(selectedProductToAdd.getQuantity() - quantity);

                // Vernieuw de TableView na de wijziging
                allProductsTableView.refresh();
            } else {
                errorLabel.setText("Please enter a valid integer for the quantity.");
                return;
            }
            // Als de hoeveelheid nul is, verwijder de rij uit allProductsTableView
            if (selectedProductToAdd.getQuantity() == 0) {
                products.remove(selectedProductToAdd);
            }

            // Maak quantityTextField leeg
            quantityTextField.clear();
        }
    }

    @FXML
    protected void cancelButtonClick(ActionEvent e) {
        // Close the current window
        closeCurrentWindow(e);
    }

    public void closeCurrentWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }
}
