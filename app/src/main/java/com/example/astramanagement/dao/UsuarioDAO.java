package com.example.astramanagement.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.astramanagement.bd.AppDatabase;
import com.example.astramanagement.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private AppDatabase helper;

    public UsuarioDAO(AppDatabase helper){
        this.helper = helper;
    }

    //CREATE : insertar usuario
    public long insertar(String nombre, String email, String password){

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("email", email);
        valores.put("password", password);

        return db.insert("usuarios", null, valores);
    }

    /*
    * db.query(
    * tabla,
    * columnas,
    * seleccion,
    * argumentosSeleccion,
    * groupBy,
    * having,
    * orderBy
    *  );*/

    //READ: consultar todos los usuarios
    public Cursor getAll(){
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(
                "usuarios",
                null,
                null,
                null,
                null,
                null,
                "nombre ASC"

        );
    }

    //READ: consultar usuario por id
    public Cursor getById(int id){
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(
                "usuarios",
                null,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
    }

    //UPDATE: actualizar usuario
    public int actualizar(int id, String nombre, String email, String password){
        SQLiteDatabase db = helper.getWritableDatabase();

        //Valores que queremos actualizar
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("email", email);
        valores.put("password", password);

        //WHERE
        String where = "id = ?";
        //Valor para ?
        String[] whereArgs = {String.valueOf(id)};

        int filas = db.update("usuarios", valores, where, whereArgs);

        return filas;
    }

    //DELETE: eliminar usuario
    public int eliminar(int id){
        SQLiteDatabase db = helper.getWritableDatabase();

        int filas = db.delete(
                "usuarios",
                "id=?",
                new String[]{String.valueOf(id)}
        );

        return filas;
    }

    //LoginActivity: buscar usuario por email y contraseña
    public Usuario login(String email, String password) {

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(
                "usuarios",
                null,
                "email = ? AND password = ?",
                new String[]{email, password},
                null,
                null,
                null
        );

        Usuario usuario = null;

        if (c.moveToFirst()) {
            usuario = cursoToUsuario(c);
        }

        c.close();

        return usuario;
    }

    public List<Usuario> getAll(SQLiteDatabase db){

        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        Cursor c = db.query(
                "usuarios",
                null,
                null,
                null,
                null,
                null,
                "nombre ASC"
        );

        while(c.moveToNext()){
            listaUsuarios.add(cursoToUsuario(c));
        }

        c.close();
        return listaUsuarios;

    }

    //Convertir una fila del Cursor en un Usuario
    private Usuario cursoToUsuario(Cursor c) {
        int id = c.getInt(c.getColumnIndexOrThrow("id"));
        String nombre = c.getString(c.getColumnIndexOrThrow("nombre"));
        String email = c.getString(c.getColumnIndexOrThrow("email"));
        String password = c.getString(c.getColumnIndexOrThrow("password"));

        return new Usuario(id, nombre, email, password);
    }


}
