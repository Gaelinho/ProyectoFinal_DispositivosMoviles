package com.example.proyectofinal;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class TaskBDD {

    private static final int VERSION_BD = 1;
    private static final String NOMBRE_BD = "task.db";

    public static final String TABLA_TASKS = "tabla_task";
    public static final String COL_ID = "ID";
    public static final int NUM_COL_ID = 0;
    public static final String COL_NOMBRE = "NOMBRE";
    public static final int NUM_COL_NOMBRE = 1;
    public static final String COL_MATERIA = "MATERIA";
    public static final int NUM_COL_MATERIA = 2;
    public static final String COL_DESCRIPCION = "DESCRIPCION";
    public static final int NUM_COL_DESCRIPCION = 3;
    public static final String COL_FECHA = "FECHA";
    public static final int NUM_COL_FECHA = 4;
    public static final String COL_HORA = "HORA";
    public static final int NUM_COL_HORA = 5;
    public static final String COL_PRIORIDAD = "PRIORIDAD";
    public static final int NUM_COL_PRIORIDAD = 6;
    public static final String COL_COMPLETED = "COMPLETED";
    public static final int NUM_COL_COMPLETED = 7;

    private SQLiteDatabase bdd;
    private TaskBaseSQLite tasks;

    public TaskBDD(Context context) {
        tasks = new TaskBaseSQLite(context, NOMBRE_BD, null, VERSION_BD);
    }

    public void openForWrite() {
        bdd = tasks.getWritableDatabase();
    }

    public void openForRead() {
        bdd = tasks.getReadableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    public long insertTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, task.getNombre());
        values.put(COL_MATERIA, task.getMateria());
        values.put(COL_DESCRIPCION, task.getDescripcion());
        values.put(COL_FECHA, task.getFecha());
        values.put(COL_HORA, task.getHora());
        values.put(COL_PRIORIDAD, task.isPrioridad() ? 1 : 0);
        values.put(COL_COMPLETED, 0);
        return bdd.insert(TABLA_TASKS, null, values);
    }

    public int updateTask(int id, Task task) {
        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, task.getNombre());
        values.put(COL_MATERIA, task.getMateria());
        values.put(COL_DESCRIPCION, task.getDescripcion());
        values.put(COL_FECHA, task.getFecha());
        values.put(COL_HORA, task.getHora());
        values.put(COL_PRIORIDAD, task.isPrioridad() ? 1 : 0);
        values.put(COL_COMPLETED, task.isCompleted() ? 1 : 0);
        return bdd.update(TABLA_TASKS, values, COL_ID + " = " + id, null);
    }

    public int deleteTask(int id) {
        return bdd.delete(TABLA_TASKS, COL_ID + " = " + id, null);
    }

    public Task getTask(int id) {
        Cursor c = bdd.query(TABLA_TASKS, new String[]{COL_ID, COL_NOMBRE, COL_MATERIA, COL_DESCRIPCION, COL_FECHA, COL_HORA, COL_PRIORIDAD, COL_COMPLETED},
                COL_ID + " = " + id, null, null, null, null);
        c.moveToNext();
        return cursorToTask(c);
    }

    public Task cursorToTask(Cursor c) {
        if(c.getCount() == 0) {
            c.close();
            return null;
        } else {
            Task task = new Task(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NOMBRE), c.getString(NUM_COL_MATERIA), c.getString(NUM_COL_DESCRIPCION), c.getString(NUM_COL_FECHA), c.getString(NUM_COL_HORA), c.getInt(NUM_COL_PRIORIDAD) == 1, c.getInt(NUM_COL_COMPLETED) == 1);
            c.close();
            return task;
        }
    }

    public ArrayList<Task> getAllTasks() {
        Cursor c = bdd.query(TABLA_TASKS, new String[]{COL_ID, COL_NOMBRE, COL_MATERIA, COL_DESCRIPCION, COL_FECHA, COL_HORA, COL_PRIORIDAD, COL_COMPLETED},
                null, null, null, null, null);
        if(c.getCount() == 0) {
            c.close();
            return new ArrayList<Task>();
        } else {
            ArrayList<Task> tasks = new ArrayList<>();
            while(c.moveToNext()) {
                Task task = new Task(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NOMBRE), c.getString(NUM_COL_MATERIA), c.getString(NUM_COL_DESCRIPCION), c.getString(NUM_COL_FECHA), c.getString(NUM_COL_HORA), c.getInt(NUM_COL_PRIORIDAD) == 1, c.getInt(NUM_COL_COMPLETED) == 1);
                tasks.add(task);
            }
            c.close();
            return tasks;
        }
    }
}
