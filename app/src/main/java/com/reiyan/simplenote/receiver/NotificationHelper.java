package com.reiyan.simplenote.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import com.reiyan.simplenote.R;

public class NotificationHelper extends ContextWrapper {

    public static final String CHANNEL_ID = "com.reiyan.simplenote";
    private static final String CHANNEL_NAME = "Repeat Notification";
    private NotificationChannel notificationChannel;
    private NotificationManager manager;
    private Notification.Builder builder;

    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 500, 200, 500});
            getManager().createNotificationChannel(notificationChannel);
        }
    }

    public NotificationManager getManager(){
        if (manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public Notification.Builder getNotificationRepeat (String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_menu_manage)
                    .setAutoCancel(true);
        } else {
            builder = new Notification.Builder(getApplicationContext())
                    .setContentText(body)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_menu_manage)
                    .setAutoCancel(true);
        }
        return builder;
    }

}
