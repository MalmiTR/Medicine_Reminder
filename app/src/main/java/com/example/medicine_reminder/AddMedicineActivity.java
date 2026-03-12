package com.example.medicine_reminder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddMedicineActivity extends AppCompatActivity {

    EditText etName, etDosage, etDescription;
    Button btnSave;
    TextView tvTitle;
    DatabaseHelper db;
    int userId;
    int medicineId = -1; // -1 means adding new, otherwise editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        tvTitle = findViewById(R.id.tvAddMedicineTitle); // Note: I need to check if this ID exists or update layout
        etName = findViewById(R.id.etName);
        etDosage = findViewById(R.id.etDosage);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);
        medicineId = getIntent().getIntExtra("MED_ID", -1);

        if (medicineId != -1) {
            // Edit Mode
            if (tvTitle != null) tvTitle.setText("Edit Medicine");
            btnSave.setText("Update Medicine");
            loadMedicineData();
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String dosage = etDosage.getText().toString().trim();
            String desc = etDescription.getText().toString().trim();

            if (name.isEmpty() || dosage.isEmpty()) {
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (medicineId == -1) {
                // Add new medicine
                long id = db.addMedicine(userId, name, dosage, desc);
                if (id > 0) {
                    Toast.makeText(this, "Medicine Saved!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, SetReminderActivity.class);
                    intent.putExtra("MED_ID", (int) id);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
                    finish();
                }
            } else {
                // Update existing medicine
                db.updateMedicine(medicineId, name, dosage, desc);
                Toast.makeText(this, "Medicine Updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_add);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                Intent i = new Intent(this, DashboardActivity.class);
                i.putExtra("USER_ID", userId);
                startActivity(i);
                return true;
            } else if (itemId == R.id.nav_add) {
                return true;
            } else if (itemId == R.id.nav_list) {
                Intent i = new Intent(this, MedicineListActivity.class);
                i.putExtra("USER_ID", userId);
                startActivity(i);
                return true;
            }
            return false;
        });
    }

    private void loadMedicineData() {
        Cursor cursor = db.getMedicine(medicineId);
        if (cursor != null && cursor.moveToFirst()) {
            etName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            etDosage.setText(cursor.getString(cursor.getColumnIndexOrThrow("dosage")));
            etDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow("description")));
            cursor.close();
        }
    }
}