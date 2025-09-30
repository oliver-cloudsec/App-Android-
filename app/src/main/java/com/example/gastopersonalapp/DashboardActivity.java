package com.example.gastopersonalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity implements SensorEventListener {

    Button btnAgregarGasto, btnVerGastos;
    SensorManager sensorManager;
    Sensor accelerometer;
    private long lastUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAgregarGasto = findViewById(R.id.btnAgregarGasto);
        btnVerGastos = findViewById(R.id.btnVerGastos);

        btnAgregarGasto.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        btnVerGastos.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ListExpensesActivity.class);
            startActivity(intent);
        });

        // Inicializar sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        long curTime = System.currentTimeMillis();
        if ((curTime - lastUpdate) > 1500) { // espera 1.5 segundos
            lastUpdate = curTime;
            float acceleration = Math.abs(x + y + z - 9.81f);
            if (acceleration > 5) {
                eliminarUltimoGasto();
            }
        }
    }

    private void eliminarUltimoGasto() {
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM gastos WHERE id = (SELECT MAX(id) FROM gastos)");
            Toast.makeText(this, "Último gasto eliminado por sensor", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al eliminar gasto", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se usa, pero se requiere por la interfaz
    }
}


