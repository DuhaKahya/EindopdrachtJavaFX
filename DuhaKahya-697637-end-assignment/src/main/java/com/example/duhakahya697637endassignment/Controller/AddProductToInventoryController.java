package com.example.duhakahya697637endassignment.Controller;

import com.example.duhakahya697637endassignment.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductToInventoryController {

    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Label quantityErrorLabel;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label categoryErrorLabel;
    @FXML
    private Label priceErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    private ProductInventoryController productInventoryController;


    public void setProductToInventoryController(ProductInventoryController productInventoryController) {
        this.productInventoryController = productInventoryController;
    }

    @FXML
    protected void onConfirmButtonClick(ActionEvent e) {
        boolean validationFailed = false;

        String quantityText = quantityTextField.getText();
        String priceText = priceTextField.getText();
        String name = nameTextField.getText().trim();
        String category = categoryTextField.getText().trim();
        String description = descriptionTextField.getText().trim();

        if (!isInteger(quantityText)) {
            quantityErrorLabel.setText("Quantity must be a valid integer.");
            validationFailed = true;
        }

        if (!isDouble(priceText)) {
            priceErrorLabel.setText("Price must be a valid decimal number.");
            validationFailed = true;
        }

        if (name.isEmpty()) {
            nameErrorLabel.setText("Name is empty, please enter a name.");
            validationFailed = true;
        }

        if (category.isEmpty()) {
            categoryErrorLabel.setText("Category is empty, please enter a category.");
            validationFailed = true;
        }

        if (description.isEmpty()) {
            descriptionErrorLabel.setText("Description is empty, please enter a description.");
            validationFailed = true;
        }

        if (!validationFailed) {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);

            Product newProduct = new Product(quantity, name, category, price, description);

            TableView<Product> tableView = productInventoryController.productsTableViewInventory;
            tableView.getItems().add(newProduct);

            quantityTextField.clear();
            nameTextField.clear();
            categoryTextField.clear();
            priceTextField.clear();
            descriptionTextField.clear();

            closeCurrentWindow(e);
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void closeCurrentWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }

}
