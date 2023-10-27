package com.example.duhakahya697637endassignment;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {
    private final String fxmlPath;
    private final String cssPath;
    private Stage stage;
    private FXMLLoader fxmlLoader;

    public ScreenManager(String fxmlPath, String cssPath) {
        this.fxmlPath = fxmlPath;
        this.cssPath = cssPath;
    }

    public void openWindow(String title) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
    }

    public void showWindow() {
        stage.show();
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }
}
