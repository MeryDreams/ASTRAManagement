package com.example.astramanagement.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "eventos",
        foreignKeys = @ForeignKey(
                entity = Artista.class,
                parentColumns = "id",
                childColumns = "artistaId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("artistaId")}
)
public class Evento {

    public static final String EXTRA_ID = "evento_id";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "artistaId")
    public int artistaId;

    public String nombre;
    public String fecha;
    public String hora;
    public String lugar;
    public String descripcion;
}
