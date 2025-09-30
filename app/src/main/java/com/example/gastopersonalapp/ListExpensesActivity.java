package com.example.gastopersonalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class ListExpensesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Gasto> listaGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expenses);

        recyclerView = findViewById(R.id.recyclerViewGastos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaGastos = new ArrayList<>();

        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT descripcion, monto FROM gastos", null);

            if (cursor.moveToFirst()) {
                do {
                    String descripcion = cursor.getString(0);
                    double monto = cursor.getDouble(1);
                    listaGastos.add(new Gasto(descripcion, monto));
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace(); // Ayuda para Logcat
        }

        db.close();

        GastoAdapter adapter = new GastoAdapter(listaGastos);
        recyclerView.setAdapter(adapter);
    }
}

