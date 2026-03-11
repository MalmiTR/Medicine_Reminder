package com.example.medicine_reminder;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etUser, etEmail, etPass;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_register);

        etUser = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPass = findViewById(R.id.password);

        db = new DatabaseHelper(this);
    }

    public void register(View v) {

        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("username", etUser.getText().toString());
        cv.put("email", etEmail.getText().toString());
        cv.put("password", etPass.getText().toString());

        long result = database.insert("users", null, cv);

        if(result != -1){
            Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show();
        }
    }
}