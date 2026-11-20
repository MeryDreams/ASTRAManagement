package com.example.astramanagement.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ArtistaConEventos {
    @Embedded
    public Artista artista;

    @Relation(parentColumn = "id", entityColumn = "artistaId")
    public List<Evento> eventos;

}
