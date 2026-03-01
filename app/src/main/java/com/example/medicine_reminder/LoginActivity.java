package com.example.medicine_reminder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUser, etPass;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.username);
        etPass = findViewById(R.id.password);
        db = new DatabaseHelper(this);
    }

    public void login(View v) {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor c = database.rawQuery(
                "SELECT id FROM users WHERE username=? AND password=?",
                new String[]{etUser.getText().toString(), etPass.getText().toString()}
        );

        if (c.moveToFirst()) {
            int userId = c.getInt(0);
            // Temporarily comment out until HomeActivity is created
            // Intent i = new Intent(this, HomeActivity.class);
            // i.putExtra("USER_ID", userId);
            // startActivity(i);
            // finish();
        } else {
            Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
        }
    }

    public void goRegister(View v) {
        // Temporarily comment out until RegisterActivity is created
        // startActivity(new Intent(this, RegisterActivity.class));
    }
}