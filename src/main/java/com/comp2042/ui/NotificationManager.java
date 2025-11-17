package com.comp2042.ui;

import javafx.scene.Group;

import javax.swing.*;

public class NotificationManager {

    private final Group groupNotification;

    public NotificationManager(Group groupNotification) {
        this.groupNotification = groupNotification;
    }

    public void showScore(String text){
        NotificationPanel notificationPanel = new NotificationPanel(text);
        groupNotification.getChildren().add(notificationPanel);
        notificationPanel.showScore(groupNotification.getChildren());
    }
}
