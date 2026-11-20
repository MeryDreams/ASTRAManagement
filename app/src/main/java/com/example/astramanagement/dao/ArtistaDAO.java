package com.example.astramanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.astramanagement.room.Artista;
import com.example.astramanagement.room.ArtistaConEventos;

import java.util.List;

@Dao
public interface ArtistaDAO {

    //Metodos CRUD
    @Insert
    long insertarArtista(Artista artista);

    @Update
    void actualizarArtista(Artista artista);

    @Delete
    void eliminarArtista(Artista artista);

    //READ: consultar todos los artistas
    @Query("SELECT * FROM artistas ORDER BY id")
    List<Artista> getAll();

    //READ: consultar artistas ordenados por nombre
    @Query("SELECT * FROM artistas ORDER BY nombre COLLATE NOCASE")
    List<Artista> getAllPorNombre();

    //READ: consultar artista por ID
    @Query("SELECT * FROM artistas WHERE id = :id")
    Artista getById(int id);

    //READ: buscar artistas por nombre
    @Query("SELECT * FROM artistas WHERE nombre LIKE '%' || :texto || '%' ORDER BY nombre")
    List<Artista> getByName(String texto);

    //READ: filtrar artistas por tipo
    @Query("SELECT * FROM artistas WHERE tipo = :tipo ORDER BY nombre COLLATE NOCASE")
    List<Artista> getByTipo(int tipo);

    //READ: contar artistas
    @Query("SELECT COUNT(*) FROM artistas")
    int contar();

    //READ: consultar artista con sus eventos
    @Transaction
    @Query("SELECT * FROM artistas WHERE id = :id")
    ArtistaConEventos getArtistaConEventos(int id);
}
