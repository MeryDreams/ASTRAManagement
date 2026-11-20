package com.example.astramanagement.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import com.example.astramanagement.dao.ArtistaDAO;
import com.example.astramanagement.dao.EventoDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Base datos Room (singleton)
//Todas las consultas se lanzan con ArtistaDatabase.executor, nunca en hilo principal
@Database(
        entities = {Artista.class, Evento.class},
        version = 1,
        exportSchema = false
)
public abstract class ArtistaDatabase extends RoomDatabase {

    public abstract ArtistaDAO artistaDao();

    public abstract EventoDAO eventoDAO();

    public static final ExecutorService executor = Executors.newSingleThreadExecutor();
}