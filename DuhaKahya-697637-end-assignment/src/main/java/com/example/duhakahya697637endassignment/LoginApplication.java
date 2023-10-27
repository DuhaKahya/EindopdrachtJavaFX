package com.example.duhakahya697637endassignment;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application {
    private final String title = "Wim's Music Dungeon";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager screenManager = new ScreenManager("login-view.fxml", "/CSS/style-login.css");
        screenManager.openWindow(title);
        screenManager.showWindow();
    }

}