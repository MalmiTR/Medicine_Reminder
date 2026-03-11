package com.example.medicine_reminder;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String medName = intent.getStringExtra("MED_NAME");

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "MED_CHANNEL")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Medicine Reminder")
                        .setContentText("Time to take: " + medName)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // ✅ Permission check for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                return; // Stop if permission not granted
            }
        }

        notificationManager.notify(100, builder.build());
    }
}
