package com.example.medicine_reminder;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine_reminder.DatabaseHelper;
import com.example.medicine_reminder.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.example.medicine_reminder.SetReminderActivity;

public class AddMedicineActivity extends AppCompatActivity {

    EditText etName, etDosage, etDescription;
    Button btnSave;
    DatabaseHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        etName = findViewById(R.id.etName);
        etDosage = findViewById(R.id.etDosage);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        db = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);

        btnSave.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String dosage = etDosage.getText().toString().trim();
            String desc = etDescription.getText().toString().trim();

            if(name.isEmpty() || dosage.isEmpty()){
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = db.addMedicine(userId, name, dosage, desc);

            if(id > 0){
                Toast.makeText(this, "Medicine Saved!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, SetReminderActivity.class);
                intent.putExtra("MED_ID", (int) id);
                startActivity(intent);

                finish();
            }
        });

        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.nav_dashboard){
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            } if(item.getItemId() == R.id.nav_dashboard){
                return true;
            }

            if(item.getItemId() == R.id.nav_add){
                startActivity(new Intent(this, AddMedicineActivity.class));
                return true;
            }

            if(item.getItemId() == R.id.nav_list){
                startActivity(new Intent(this, MedicineListActivity.class));
                return true;
            }

            return false;
        });

    }
}
