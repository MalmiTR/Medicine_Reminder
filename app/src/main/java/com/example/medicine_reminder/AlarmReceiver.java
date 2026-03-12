package com.example.medicine_reminder;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if ("STOP_ALARM".equals(action)) {
            if (ringtone != null && ringtone.isPlaying()) {
                ringtone.stop();
            }
            NotificationManagerCompat.from(context).cancel(100);
            return;
        }

        String medName = intent.getStringExtra("MED_NAME");

        // 1. Play Alarm Sound
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        if (ringtone != null) {
            ringtone.play();
        }

        // 2. Vibrate
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(2000);
            }
        }

        // 3. Stop Intent
        Intent stopIntent = new Intent(context, AlarmReceiver.class);
        stopIntent.setAction("STOP_ALARM");
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // 4. Show Notification with Stop button
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "MED_CHANNEL")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Medicine Reminder")
                        .setContentText("Time to take: " + medName)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_launcher_foreground, "Dismiss", stopPendingIntent);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        notificationManager.notify(100, builder.build());
    }
}