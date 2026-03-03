package com.example.medicine_reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MedicineListActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        listView = findViewById(R.id.listView);
        db = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);

        loadMedicines();
    }

    private void loadMedicines() {

        Cursor cursor = db.getMedicinesByUser(userId);

        String[] from = {"name", "dosage", "description"};
        int[] to = {R.id.tvName, R.id.tvDosage, R.id.tvDesc};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.item_medicine,
                cursor,
                from,
                to,
                0
        );

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Delete this medicine?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteMedicine((int) id);
                        loadMedicines();
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }
}