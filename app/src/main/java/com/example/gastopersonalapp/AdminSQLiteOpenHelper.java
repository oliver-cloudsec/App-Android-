package com.example.gastopersonalapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context) {
        super(context, "GastosDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE gastos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descripcion TEXT," +
                "monto REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Por ahora solo elimina y recrea
        db.execSQL("DROP TABLE IF EXISTS gastos");
        onCreate(db);
    }
}
