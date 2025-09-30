package com.example.gastopersonalapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etDescripcion, etMonto;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etDescripcion = findViewById(R.id.etDescripcion);
        etMonto = findViewById(R.id.etMonto);
        btnGuardar = findViewById(R.id.btnGuardarGasto);

        btnGuardar.setOnClickListener(view -> {
            String descripcion = etDescripcion.getText().toString();
            String montoStr = etMonto.getText().toString();

            if (descripcion.isEmpty() || montoStr.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double monto = Double.parseDouble(montoStr);

                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
                    SQLiteDatabase db = admin.getWritableDatabase();

                    db.execSQL("INSERT INTO gastos (descripcion, monto) VALUES (?, ?)",
                            new Object[]{descripcion, monto});

                    db.close();
                    Toast.makeText(this, "Gasto guardado correctamente", Toast.LENGTH_SHORT).show();
                    finish(); // Regresa al Dashboard
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

