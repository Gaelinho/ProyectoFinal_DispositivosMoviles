package com.example.proyectofinal;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task implements Parcelable {
    private int id;
    private String nombre;
    private String materia;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private boolean prioridad;
    private boolean completed;

    public Task(String nombre, String materia, String descripcion, LocalDate fecha, LocalTime hora, boolean prioridad) {
        this.nombre = nombre;
        this.materia = materia;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.prioridad = prioridad;
        this.completed = false;
    }

    public Task(int id, String nombre, String materia, String descripcion, LocalDate fecha, LocalTime hora, boolean prioridad, boolean completed) {
        this.id = id;
        this.nombre = nombre;
        this.materia = materia;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.prioridad = prioridad;
        this.completed = completed;
    }

    protected Task(Parcel in) {
        nombre = in.readString();
        materia = in.readString();
        descripcion = in.readString();
        fecha = LocalDate.parse(in.readString());
        hora = LocalTime.parse(in.readString());
        prioridad = in.readByte() != 0;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(materia);
        dest.writeString(descripcion);
        dest.writeString(fecha.toString());
        dest.writeString(hora.toString());
        dest.writeByte((byte) (prioridad ? 1 : 0));
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>(){
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMateria() {
        return materia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getFormattedFecha() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getFormattedHora() {
        return hora.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public boolean isPrioridad() {
        return prioridad;
    }

    public boolean isCompleted() {
        return completed;
    }

}
