package com.comp2042.ui.components;

import javafx.scene.Group;

public class NotificationManager {

    private final Group groupNotification;
    private final NotificationAnimator animator;

    public NotificationManager(Group groupNotification) {

        this.groupNotification = groupNotification;
        this.animator = new NotificationAnimator();
    }

    public void showScore(String text){
        NotificationPanel notificationPanel = new NotificationPanel(text);
        groupNotification.getChildren().add(notificationPanel);


        Runnable cleanup = () -> {groupNotification.getChildren().remove(notificationPanel);};
        animator.animate(notificationPanel, cleanup);
    }
}
