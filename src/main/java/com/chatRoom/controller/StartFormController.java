package com.chatRoom.controller;

import com.chatRoom.bo.BOFactory;
import com.chatRoom.bo.custom.UserBO;
import com.chatRoom.dto.UserDTO;
import com.chatRoom.server.Server;
import com.chatRoom.util.NotificationUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;

public class StartFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnStart;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnStartOnAction(ActionEvent event) {
        try {
            launchServer();
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
            stage.setTitle("Login Form");
            stage.setOnCloseRequest(windowEvent -> {
                try {
                    //check which users there online before close server, If there is users then set user status 0
                    ArrayList<UserDTO> userDTOS = userBO.getAllUsers();
                    for (UserDTO dto : userDTOS) {
                        if(dto.getStatus() == 1){
                            userBO.updateUser(new UserDTO(dto.getUserName(), dto.getPassword(), 0));
                        }
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    NotificationUtil.ShowNotification("Ooooops !", "Oops !!! something happened", NotificationUtil.NotificationType.ERROR, Duration.seconds(5));
                }
                System.exit(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void launchServer() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(3002);
                Server server = new Server(serverSocket);
                server.startServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
