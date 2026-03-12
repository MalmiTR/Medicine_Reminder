package com.example.medicine_reminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MedicineListActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    int userId;
    MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        listView = findViewById(R.id.listView);
        db = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);

        // Initialize adapter with null cursor
        adapter = new MedicineAdapter(this, null);
        listView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_list);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                Intent i = new Intent(this, DashboardActivity.class);
                i.putExtra("USER_ID", userId);
                startActivity(i);
                return true;
            } else if (itemId == R.id.nav_add) {
                Intent i = new Intent(this, AddMedicineActivity.class);
                i.putExtra("USER_ID", userId);
                startActivity(i);
                return true;
            } else if (itemId == R.id.nav_list) {
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMedicines();
    }

    private void loadMedicines() {
        Cursor cursor = db.getMedicinesByUser(userId);
        adapter.swapCursor(cursor);
    }

    private class MedicineAdapter extends CursorAdapter {
        public MedicineAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tvName = view.findViewById(R.id.tvName);
            TextView tvDosage = view.findViewById(R.id.tvDosage);
            TextView tvDesc = view.findViewById(R.id.tvDesc);
            Button btnEdit = view.findViewById(R.id.btnEdit);
            Button btnReminder = view.findViewById(R.id.btnReminder);
            Button btnDelete = view.findViewById(R.id.btnDelete);

            final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            final String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            final String dosage = cursor.getString(cursor.getColumnIndexOrThrow("dosage"));
            final String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));

            tvName.setText(name);
            tvDosage.setText(dosage);
            tvDesc.setText(desc);

            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(MedicineListActivity.this, AddMedicineActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("MED_ID", id);
                startActivity(intent);
            });

            btnReminder.setOnClickListener(v -> {
                Intent intent = new Intent(MedicineListActivity.this, SetReminderActivity.class);
                intent.putExtra("MED_ID", id);
                startActivity(intent);
            });

            btnDelete.setOnClickListener(v -> showDeleteConfirm(id));
        }
    }

    private void showDeleteConfirm(int id) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Medicine")
                .setMessage("Are you sure you want to delete this medicine?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    db.deleteMedicine(id);
                    loadMedicines(); // Refresh the list
                    Toast.makeText(this, "Medicine deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}