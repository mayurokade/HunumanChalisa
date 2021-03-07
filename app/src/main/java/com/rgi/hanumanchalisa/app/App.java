package com.rgi.hanumanchalisa.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.MediaPlayer;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "hanumanchalisachannel";
    public static MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Hanuman Chaliesa Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

        }
        mediaPlayer = new MediaPlayer();


    }
}
