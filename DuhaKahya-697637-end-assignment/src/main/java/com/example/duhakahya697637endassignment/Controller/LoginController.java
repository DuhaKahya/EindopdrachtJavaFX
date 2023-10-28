package com.example.duhakahya697637endassignment.Controller;


import com.example.duhakahya697637endassignment.Data.Database;

import com.example.duhakahya697637endassignment.ScreenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private final String title = "Wim's Music Dungeon";
    @FXML
    TextField usernameField;
    @FXML
    Label errorLabel;
    @FXML
    private PasswordField passwordField;
    private String userRole;
    private Database database = new Database();
    private MainController mainController;

    @FXML
    protected void onLoginButtonClick(ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (database.isValidUser(username, password)) {

            userRole = database.getUserRole(username, password);


            ScreenManager screenManager = new ScreenManager("main-view.fxml", "/CSS/style-main.css");
            screenManager.openWindow(title);

            mainController = screenManager.getFxmlLoader().getController();
            mainController.setUserRole(userRole);
            mainController.initialize();


            screenManager.showWindow();

            closeCurrentWindow(e);


        } else {
            // Invalid user, display an error message
            errorLabel.setText("Invalid username or password combination");
        }

    }




    protected void closeCurrentWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }

}
