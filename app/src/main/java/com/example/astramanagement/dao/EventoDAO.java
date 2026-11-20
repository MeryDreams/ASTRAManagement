package com.example.astramanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.astramanagement.room.Evento;

import java.util.List;

@Dao
public interface EventoDAO {
    //Metodos CRUD

    //CREATE: insertar evento
    @Insert
    long insertarEvento(Evento evento);

    //UPDATE: actualizar evento
    @Update
    void actualizarEvento(Evento evento);

    //DELETE: eliminar evento
    @Delete
    void eliminarEvento(Evento evento);

    //READ: consultar todos los eventos
    @Query("SELECT * FROM eventos ORDER BY fecha")
    List<Evento> getAll();

    //READ: consultar evento por ID
    @Query("SELECT * FROM eventos WHERE id = :id")
    Evento getById(int id);

    //READ: consultar eventos de un artista
    @Query("SELECT * FROM eventos WHERE artistaId = :artistaId ORDER BY fecha")
    List<Evento> getEventosPorArtista(int artistaId);

    //READ: buscar eventos por nombre
    @Query("SELECT * FROM eventos WHERE nombre LIKE '%' || :texto || '%' ORDER BY nombre")
    List<Evento> getEventoNombre(String texto);

    //READ: contar eventos
    @Query("SELECT COUNT(*) FROM eventos")
    int contarEventos();
}
