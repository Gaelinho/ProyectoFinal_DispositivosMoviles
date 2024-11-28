package com.example.proyectofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskBaseSQLite extends SQLiteOpenHelper {
    private static final String TABLA_TASKS = "tabla_task";
    private static final String COL_ID = "ID";
    private static final String COL_NOMBRE = "NOMBRE";
    private static final String COL_MATERIA = "MATERIA";
    private static final String COL_DESCRIPCION = "DESCRIPCION";
    private static final String COL_FECHA = "FECHA";
    private static final String COL_HORA = "HORA";
    private static final String COL_PRIORIDAD = "PRIORIDAD";
    private static final String COL_COMPLETED = "COMPLETED";

    private static final String CREATE_TABLE = "CREATE TABLE " +
            TABLA_TASKS + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOMBRE + " TEXT NOT NULL, " + COL_MATERIA + " TEXT NOT NULL, " +
            COL_DESCRIPCION + " TEXT NOT NULL, " + COL_FECHA + " TEXT NOT NULL, " +
            COL_HORA + " TEXT NOT NULL, " + COL_PRIORIDAD + " INTEGER NOT NULL," +
            COL_COMPLETED + " INTEGER NOT NULL)";

    public TaskBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TASKS);
        onCreate(db);
    }
}