package com.example.astramanagement.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppDatabase  extends SQLiteOpenHelper {

    public static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static final String DB_NAME = "artistas.db";
    private static final int DB_VERSION = 1;

    //Singleton: unica instancia
    private static AppDatabase instance;

    //Constructor privado: impide crear objetos fuera de esta clase
    public AppDatabase(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    //Metodo estático para obtener la instancia unica
    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crear tablas
        //Activar claves foraneas

        //Crear tabla usuarios
        db.execSQL("CREATE TABLE usuarios(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL)");

        //Crear tabla lista personal
        db.execSQL("CREATE TABLE lista_personal(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario_id INTEGER NOT NULL," +
                "elemento TEXT NOT NULL," +
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE)");

        // Insertar usuarios iniciales
        db.execSQL("INSERT INTO usuarios (nombre, email, password) VALUES " + "('María', 'maria@astra.com', '1234')");

        db.execSQL("INSERT INTO usuarios (nombre, email, password) VALUES " + "('Carlos', 'carlos@astra.com', '1234')");

        db.execSQL("INSERT INTO usuarios (nombre, email, password) VALUES " + "('Laura', 'laura@astra.com', '1234')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Migraciones
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
