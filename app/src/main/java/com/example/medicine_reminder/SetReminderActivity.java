package com.example.medicine_reminder;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class SetReminderActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;

    TimePicker timePicker;
    Button btnSet;
    int medicineId;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        checkNotificationPermission();
        createNotificationChannel();

        timePicker = findViewById(R.id.timePicker);
        btnSet = findViewById(R.id.btnSetReminder);

        db = new DatabaseHelper(this);

        medicineId = getIntent().getIntExtra("MED_ID", -1);

        btnSet.setOnClickListener(v -> {

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            db.saveReminderTime(medicineId, hour, minute);

            setAlarm(hour, minute);

            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }


    private void checkNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_CODE);
            }
        }
    }


    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    "MED_CHANNEL",
                    "Medicine Reminder Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void setAlarm(int hour, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // If time already passed today → set for tomorrow
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

         Intent intent = new Intent(this, AlarmReceiver.class);
         intent.putExtra("MED_NAME", db.getMedicineName(medicineId));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                medicineId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }
}
