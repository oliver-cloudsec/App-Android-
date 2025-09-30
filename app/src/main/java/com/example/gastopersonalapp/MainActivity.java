package com.example.gastopersonalapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;

    // Usuario y contraseña predefinidos
    final String USER = "admin@gmail.com";
    final String PASS = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Insertar gasto de prueba (solo la primera vez)
        insertarGastoDePrueba();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            String inputEmail = emailEditText.getText().toString();
            String inputPass = passwordEditText.getText().toString();

            if (inputEmail.equals(USER) && inputPass.equals(PASS)) {
                Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertarGastoDePrueba() {
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Verifica si ya hay gastos registrados para evitar duplicar
        long cantidad = android.database.DatabaseUtils.queryNumEntries(db, "gastos");
        if (cantidad == 0) {
            ContentValues values = new ContentValues();
            values.put("descripcion", "Gasto de prueba");
            values.put("monto", 99.99);
            db.insert("gastos", null, values);
        }

        db.close();
    }
}

