package com.chatRoom.client;

import com.chatRoom.controller.ChatFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Client {

    private Socket socket;
    private String userName;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ChatFormController chatFormController;

    public Client(Socket socket, String userName) {
        try {
            this.socket = socket;
            this.userName = userName;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(userName);
            dataOutputStream.flush();
            launchScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void launchScene() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ChatForm.fxml"));
            stage.setScene(new Scene(fxmlLoader.load()));
            Image icon = new Image(getClass().getResourceAsStream("/assets/icon.png"));
            stage.getIcons().add(icon);
            stage.setTitle(userName + "' Room ");
            stage.setResizable(false);
            stage.centerOnScreen();

            stage.show();
            chatFormController = fxmlLoader.getController();
            chatFormController.setData(this, userName);
            stage.setOnCloseRequest(windowEvent -> {
                close();
            });
        } catch (IOException e) {
            close();
        }
    }

    private void close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readMessage() {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String message = dataInputStream.readUTF();
                    if ("--image--".equals(message)) {
                        readImage();
                    } else {
                        chatFormController.writeMessage(message);
                    }
                } catch (IOException e) {
                    close();
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            close();
        }
    }

    public void sendImage(byte[] bytes) {
        try {
            dataOutputStream.writeUTF("--image--");
            dataOutputStream.writeInt(bytes.length);
            dataOutputStream.write(bytes);
            dataOutputStream.flush();
        } catch (IOException e) {
            close();
        }
    }

    private void readImage() {
        System.out.println("image receive successfully");
        try {
            String sender = dataInputStream.readUTF();
            int length = dataInputStream.readInt();
            byte[] bytes = new byte[length];
            dataInputStream.readFully(bytes);
            chatFormController.writeImage(sender, bytes);
        } catch (IOException e) {
            close();
        }
    }
}