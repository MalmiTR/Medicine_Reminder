package com.example.medicine_reminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView tvWelcome;
    Button btnAdd, btnView, btnLogout;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnAdd = findViewById(R.id.btnAddMedicine);
        btnView = findViewById(R.id.btnViewMedicine);
        btnLogout = findViewById(R.id.btnLogout);

        userId = getIntent().getIntExtra("USER_ID", -1);

        tvWelcome.setText("Welcome 👋");

        btnAdd.setOnClickListener(v -> {
//            Intent i = new Intent(this, AddMedicineActivity.class); // Sithu screen
//            i.putExtra("USER_ID", userId);
//            startActivity(i);
        });

        btnView.setOnClickListener(v -> {
            Intent i = new Intent(this, MedicineListActivity.class);
            i.putExtra("USER_ID", userId);
            startActivity(i);
        });

        btnLogout.setOnClickListener(v -> {
            finish();
        });
    }
}