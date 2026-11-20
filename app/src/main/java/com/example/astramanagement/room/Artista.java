package com.example.astramanagement.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "artistas")
public class Artista {

    public static final String EXTRA_ID = "artista_id";


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private int tipo;
    private String genero;
    private int miembros;
    private String fechaDebut; // dd/MM/yyyy
    private int popularidad; //1-10
    private int estado; //0 activo 1 hiatus 2 en comeback
    private float valoracion;

    public Artista(int id, String nombre, int tipo, String genero, int miembros, String fechaDebut, int popularidad, int estado, float valoracion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.genero = genero;
        this.miembros = miembros;
        this.fechaDebut = fechaDebut;
        this.popularidad = popularidad;
        this.estado = estado;
        this.valoracion = valoracion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public String getGenero() {
        return genero;
    }

    public int getMiembros() {
        return miembros;
    }

    public String getFechaDebut() {
        return fechaDebut;
    }

    public int getPopularidad() {
        return popularidad;
    }

    public int getEstado() {
        return estado;
    }

    public float getValoracion() {
        return valoracion;
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                ", genero='" + genero + '\'' +
                ", miembros=" + miembros +
                ", fechaDebut='" + fechaDebut + '\'' +
                ", popularidad=" + popularidad +
                ", estado=" + estado +
                ", valoracion=" + valoracion +
                '}';
    }
}
