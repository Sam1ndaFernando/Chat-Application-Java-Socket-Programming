package com.chatRoom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Appinitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StartForm.fxml"))));
        stage.show();
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Start Server Form");
        Image icon = new Image(getClass().getResourceAsStream("/assets/icon.png"));
        stage.getIcons().add(icon);
    }
}