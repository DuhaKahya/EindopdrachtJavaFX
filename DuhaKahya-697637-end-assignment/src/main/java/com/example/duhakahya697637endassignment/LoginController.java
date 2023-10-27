package com.example.duhakahya697637endassignment;

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
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label errorLabel;
    private String userRole;
    private MainController mainController;

    @FXML
    protected void onLoginButtonClick(ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        UserDatabase userDatabase = new UserDatabase();

        if (userDatabase.isValidUser(username, password)) {

            userRole = userDatabase.getUserRole(username, password);

            ScreenManager screenManager = new ScreenManager("main-view.fxml", "/CSS/style-main.css");
            screenManager.openWindow(title);

            mainController = screenManager.getFxmlLoader().getController();
            mainController.initializeLabels();

            screenManager.showWindow();

            closeCurrentWindow(e);


        } else {
            // Invalid user, display an error message
            errorLabel.setText("Invalid username or password combination");
        }

    }

    public void closeCurrentWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }

}
