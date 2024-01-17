package com.chatRoom.util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationUtil {

    public enum NotificationType {
        NOTIFICATION,
        INFORMATION,
        ERROR,
        SUCCESS
    }

    public static void ShowNotification(String title, String text, NotificationType type, Duration duration){
        Notifications notification = Notifications.create()
        .title(title)
        .text(text)
        .hideAfter(duration);

        switch (type){
            case INFORMATION:
                notification.graphic(new ImageView(new Image(NotificationUtil.class.getResourceAsStream("/images/info.png"), 80, 80, true, true)));
                break;

            case ERROR:
                notification.graphic(new ImageView(new Image(NotificationUtil.class.getResourceAsStream("/images/info.png"), 80, 80, true, true)));
                break;

            case SUCCESS:
                notification.graphic(new ImageView(new Image(NotificationUtil.class.getResourceAsStream("/images/info.png"), 80, 80, true, true)));
                break;

            case NOTIFICATION:
                notification.graphic(new ImageView(new Image(NotificationUtil.class.getResourceAsStream("/images/info.png"), 80, 80, true, true)));
                break;

        }

        notification.position(Pos.BOTTOM_LEFT);
        notification.show();
    }
}
