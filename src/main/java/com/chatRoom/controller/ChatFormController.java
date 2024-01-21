package com.chatRoom.controller;

import animatefx.animation.Jello;
import animatefx.animation.Pulse;
import com.chatRoom.bo.BOFactory;
import com.chatRoom.bo.custom.UserBO;
import com.chatRoom.client.Client;
import com.chatRoom.dto.UserDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChatFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnDashBoard;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXTextField txtField;

    @FXML
    private VBox vbox;

    @FXML
    private AnchorPane emojiBar;

    @FXML
    private GridPane emojiPane;

    @FXML
    private JFXButton btnImage;

    @FXML
    private JFXButton btnEmoji;

    private final String[] emojis = {
            "\uD83D\uDE03", // 🤣
            "\uD83D\uDE04", // 😄
            "\uD83D\uDE00", // 😀
            "\uD83D\uDE02", // 😂
            "\uD83D\uDE0C", // 😌
            "\uD83D\uDE0D", // 😍
            "\uD83D\uDE0E", // 😎
            "\uD83D\uDE0F", // 😏
            "\uD83D\uDE10", // 😐
            "\uD83D\uDE11", // 😑
            "\uD83D\uDE12", // 😒
            "\uD83D\uDE05", // 😅
            "\uD83D\uDE06", // 😆
            "\uD83D\uDE08", // 😈
            "\uD83D\uDE09", // 😉
            "\uD83D\uDE0A", // 😊
            "\uD83D\uDE13", // 😓
            "\uD83D\uDE0B", // 😋
            "\uD83D\uDE01", // 😁
            "\uD83D\uDE07"  // 😇
    };

    private Client client;
    private String userName;
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnEmojiOnAction(ActionEvent event) {
        emojiBar.setVisible(!emojiBar.isVisible());
    }

    @FXML
    void btnImageOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                // set selected image for sender's chat room
                ImageView imageView = new ImageView(new Image(new FileInputStream(file)));
                imageView.setFitHeight(200);
                imageView.setFitWidth(200);

                // create hbox for add properties
                HBox hBox = new HBox();
                hBox.setStyle("-fx-alignment: center-right; -fx-fill-height: true; -fx-min-height: 50px; -fx-pref-width: 520px; -fx-max-width: 520px; -fx-padding: 10px; ");

                System.out.println("Send image to ChatRoom");

                // create byte array and send it to client
                byte[] bytes = Files.readAllBytes(file.toPath());
                client.sendImage(bytes);

                // add the image view to the hbox and hbox to the vbox
                hBox.getChildren().add(imageView);
                vbox.getChildren().add(hBox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void btnSendOnAction(ActionEvent event) {
        client.sendMessage(txtField.getText());

        // create hbox and set the style properties of the hbox
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right; -fx-fill-height: true; -fx-min-height: 50; -fx-pref-width: 520; -fx-max-width: 520; -fx-padding: 10");

        // create label and add it to the HBox
        Label label = new Label(txtField.getText());
        label.setStyle(" -fx-alignment: center-left; -fx-background-color: #006865; -fx-background-radius:15px 0px 15px 15px; -fx-font-size: 18; -fx-text-fill: #FFFFFF; -fx-wrap-text: true; -fx-content-display: left; -fx-max-width: 350; -fx-padding: 10;");
        hBox.getChildren().add(label);
        vbox.getChildren().add(hBox);
        txtField.setText("");
    }

    @FXML
    void txtFieldOnAction(ActionEvent event) {
        btnSendOnAction(event);
    }

    public void writeMessage(String message) {
        // create hbox and set the style properties of the hbox
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left; -fx-fill-height: true; -fx-min-height: 50px; -fx-pref-width: 520px; -fx-max-width: 520px; -fx-padding: 10px");

        // create label and add it to the HBox
        Label label = new Label(message);
        label.setStyle(" -fx-alignment: center-left; -fx-background-color:  #A537CA; -fx-background-radius:0px 15px 15px 15px; -fx-font-size: 18px; -fx-text-fill: #FFFFFF; -fx-wrap-text: true; -fx-content-display: left; -fx-max-width: 350px; -fx-padding: 10px;");
        hBox.getChildren().add(label);
        Platform.runLater(() -> vbox.getChildren().add(hBox));
        System.out.println(message);
    }

    public void setData(Client client, String userName) {
        this.client = client;
        this.userName = userName;
    }

    public void writeImage(String sender, byte[] bytes) {
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-left;-fx-fill-height: true; -fx-min-height: 50px; -fx-pref-width: 520px; -fx-max-width: 520px; -fx-padding: 10px");

        Label label = new Label(sender);
        label.setStyle(" -fx-alignment: center-left; -fx-background-color: #45bea8; -fx-background-radius:15px; -fx-font-size: 18px; -fx-text-fill: #ffffff; -fx-wrap-text: true; -fx-content-display: left; -fx-max-width: 350px; -fx-padding: 10px;");
        Platform.runLater(() -> {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            hBox.getChildren().addAll(label, imageView);
            vbox.getChildren().add(hBox);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emojiBar.setVisible(false);
        createEmojiBar();
        new Jello(root).play();
    }

    /*private void createEmojiBar() {
        int btnIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 5; column++) {
                if (btnIndex < emojis.length) {
                    JFXButton button = new JFXButton(emojis[btnIndex]);
                    button.setStyle("-fx-background-radius:20px;-fx-text-alignment: center; -fx-background-color: #B1FFECFF;");
                    button.setAlignment(Pos.CENTER);
                    emojiPane.add(button, column, row);
                    button.setOnAction(event -> {
                        txtField.appendText(button.getText());
                    });
                    btnIndex++;
                }
            }
        }
    }*/
    private void createEmojiBar() {
        int btnIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 5; column++) {
                if (btnIndex < emojis.length) {
                    JFXButton button = new JFXButton(emojis[btnIndex]);

                    // apply different styles to each button
                    if (btnIndex % 2 == 0) {
                        button.setStyle("-fx-background-radius:10px;-fx-text-alignment: center; -fx-alignment: center; -fx-background-color: #B1FFECFF; -fx-text-fill: #000000;");
                    } else {
                        button.setStyle("-fx-background-radius:10px;-fx-text-alignment: center; -fx-alignment: center; -fx-background-color: #FFD700; -fx-text-fill: #000000;");

                    }
                    button.setAlignment(Pos.CENTER);
                    emojiPane.add(button, column, row);

                    button.setOnAction(event -> {            // add event handler to each button
                        txtField.appendText(button.getText());
                    });
                    btnIndex++;
                }
            }
        }
    }

    public void btnDashBoardOnAction(ActionEvent event) {
        /*try {
            ArrayList<UserDTO> userDTOS = userBO.getAllUsers();

            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setPadding(new Insets(10));

            for (UserDTO dto : userDTOS) {
                if(dto.getStatus() != 0){
                    Label label = new Label(dto.getUserName());
                    label.setStyle("-fx-text-alignment: center; -fx-text-fill: #ffffff; -fx-background-color: green; -fx-font-weight: bold; -fx-font-size: 15px; -fx-background-radius: 20px; -fx-border-width: 5px;  -fx-min-height: 50px; -fx-min-width: 70px; -fx-padding: 5px;");
                    hBox.getChildren().add(label);
                }
            }

            Scene scene = new Scene(hBox);
            Stage stage = new Stage();
            stage.setTitle("Online members");
            stage.setScene(scene);
            stage.show();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public void btnLogoutOnActon(ActionEvent event) throws SQLException, ClassNotFoundException {
        UserDTO dto = userBO.searchUser(userName);
        userBO.updateUser(new UserDTO(dto.getUserName(), dto.getPassword(), 0));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
