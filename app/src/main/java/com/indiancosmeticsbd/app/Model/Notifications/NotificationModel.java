package com.indiancosmeticsbd.app.Model.Notifications;

public class NotificationModel
{
    private String notificationType, notificationText, link, status;

    public NotificationModel(String notificationType, String notificationText, String link, String status) {
        this.notificationType = notificationType;
        this.notificationText = notificationText;
        this.link = link;
        this.status = status;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
