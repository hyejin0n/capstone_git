package com.example.weightpet0604;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weights.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "weight_records";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_DATETIME = "dateTime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WEIGHT + " REAL, " +
                COLUMN_DATETIME + " TEXT)";
        db.execSQL(createTable);
        Log.d("DBHelper", "Database table created: " + createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<Weight> getLast10Weights() {
        List<Weight> weightList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DATETIME + " DESC LIMIT 10";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATETIME));
                weightList.add(new Weight(weight, dateTime));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return weightList;
    }

    public List<Weight> getAllWeights() {
        List<Weight> weights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATETIME));
                weights.add(new Weight(weight, dateTime));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return weights;
    }
}
