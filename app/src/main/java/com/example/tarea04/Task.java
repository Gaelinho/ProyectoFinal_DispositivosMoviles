package com.example.tarea04;
import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private String nombre;
    private String materia;
    private String descripcion;
    private String fecha;
    private String hora;
    private boolean prioridad;

    public Task(String nombre, String materia, String descripcion, String fecha, String hora, boolean prioridad) {
        this.nombre = nombre;
        this.materia = materia;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.prioridad = prioridad;
    }

    protected Task(Parcel in) {
        nombre = in.readString();
        materia = in.readString();
        descripcion = in.readString();
        fecha = in.readString();
        hora = in.readString();
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
        dest.writeString(fecha);
        dest.writeString(hora);
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

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMateria() {
        return materia;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public boolean isPrioridad() {
        return prioridad;
    }

    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }
}
