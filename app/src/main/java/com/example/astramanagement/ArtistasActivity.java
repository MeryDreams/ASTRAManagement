package com.example.astramanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astramanagement.bd.AppDatabase;
import com.example.astramanagement.room.Artista;
import com.example.astramanagement.room.ArtistaDatabase;
import com.example.astramanagement.room.DatabaseClient;

import java.util.List;

//Listado de Artistas RecyclerView + CardView (item_artista.xml)
//Clic corto: ficha para editar
//Clic largo: borrar artista

public class ArtistasActivity extends AppCompatActivity implements ArtistaAdapter.Listener{

    private RecyclerView rvArtistas;
    private Button btnAnadir;
    private DatabaseClient db;

    private ArtistaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_artistas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvArtistas = findViewById(R.id.rvArtistas);
        btnAnadir = findViewById(R.id.btnAnadir);

        db = DatabaseClient.getInstance(this);

        rvArtistas.setLayoutManager(new LinearLayoutManager(this));

        btnAnadir.setOnClickListener(v -> {
            Intent intent = new Intent(this, AltaArtistaActivity.class);
            startActivity(intent);
        });

    }

    //Cuando se vuelva despues de añadir un artista, se volvera a consultar Room y se actualizara el listado
    @Override
    protected void onResume() {
        super.onResume();
        cargarArtistas();
    }

    private void cargarArtistas(){
        ArtistaDatabase.executor.execute(() -> {

            //Consultamos a Room: obtenemos todos los artistas guardados en la base de datos
            List<Artista> lista = db.getDb().artistaDao().getAll();

            //Mostramos los artistas en el RecyclerView
            runOnUiThread(() -> {
                //Creamos el adaptador: le asiganmos la lista de artistas
                adapter = new ArtistaAdapter(lista, this);

                //Conectamos el adaptador: le indicamos al RecyclerView que adaptador debe utilizar para mostrar las tarjetas
                rvArtistas.setAdapter(adapter);
            });
        });
    }

    @Override
    public void onClick(Artista artista) {
        Intent intent = new Intent(this, FichaArtistaActivity.class);
        //Recibe el id del artista
        intent.putExtra(Artista.EXTRA_ID, artista.getId());
        startActivity(intent);
    }

    @Override
    public void onLongClick(Artista artista) {

    }

    private void eliminar(Artista artista) {
        ArtistaDatabase.executor.execute(() -> {
            db.getDb().artistaDao().eliminarArtista(artista);new AlertDialog.Builder(this)
                    .setTitle("Eliminar artista")
                    .setMessage("¿Seguro que quieres eliminar a "+artista.getNombre()+"?")
                    .setPositiveButton("Eliminar",(d,w) -> eliminar(artista))
                    .setNegativeButton("Cancelar", null)
                    .show();

            runOnUiThread(() -> {
                Toast.makeText(this, artista.getNombre()+" eliminado", Toast.LENGTH_SHORT).show();
                cargarArtistas();
            });
        });
    }
}