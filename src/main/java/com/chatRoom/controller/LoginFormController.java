package com.chatRoom.controller;

import com.chatRoom.bo.BOFactory;
import com.chatRoom.bo.custom.UserBO;
import com.chatRoom.client.Client;
import com.chatRoom.dto.UserDTO;
import com.chatRoom.util.NotificationUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnLaunch;

    @FXML
    private JFXTextField txtUsrField;

    @FXML
    private JFXTextField txtPassField;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    void btnLaunchOnAction(ActionEvent event) {
        try {
            UserDTO user = userBO.searchUser(txtUsrField.getText());
            if (user != null) {
                if (user.getPassword().equals(txtPassField.getText()) && user.getStatus() == 0) {
                    try {
                        Client client = new Client(new Socket("localhost", 3002), txtUsrField.getText());
                        client.readMessage();
                        txtUsrField.setText("");
                        txtPassField.setText("");
                        boolean isUpdate = userBO.updateUser(new UserDTO(user.getUserName(), user.getPassword(), 1));
                        NotificationUtil.ShowNotification("Successfully", "Successfully login to ChatRoom " + user.getUserName(), NotificationUtil.NotificationType.SUCCESS, Duration.seconds(5));

                    } catch (IOException e) {
                        NotificationUtil.ShowNotification("oops !", "Oops !!! something happened please try again", NotificationUtil.NotificationType.ERROR, Duration.seconds(5));
                    }
                } else if (user.getStatus() == 1) {
                    NotificationUtil.ShowNotification("oops !", "User is in the ChatRoom", NotificationUtil.NotificationType.INFORMATION, Duration.seconds(5));
                } else {
                    NotificationUtil.ShowNotification("Error", "Enter correct details", NotificationUtil.NotificationType.ERROR, Duration.seconds(5));
                }
            }
        } catch (SQLException | NullPointerException | ClassNotFoundException e) {

        }
    }

    @FXML
    void txtPassFieldOnAction(ActionEvent event) {
        txtPassField.requestFocus();
    }

    @FXML
    void txtUsrFieldOnAction(ActionEvent event) {
        btnLaunchOnAction(event);
    }

}
