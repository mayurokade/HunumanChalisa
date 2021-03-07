package com.rgi.hanumanchalisa.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.activities.MainActivity;

import static com.rgi.hanumanchalisa.app.App.CHANNEL_ID;
import static com.rgi.hanumanchalisa.app.App.mediaPlayer;

public class MediaplayerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID).
                setContentTitle("Hanuman Chalisa").
                setContentText("Music Player is Running.")
                .setSmallIcon(R.drawable.image_two)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
               stopSelf();
            }
        });

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
