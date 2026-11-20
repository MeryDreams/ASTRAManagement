package com.example.astramanagement.room;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static DatabaseClient instance;
    private final ArtistaDatabase db;

    private DatabaseClient(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(), ArtistaDatabase.class, "artistas_room.db").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if (instance==null){
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public ArtistaDatabase getDb(){
        return db;
    }
}
