package com.example.astramanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.astramanagement.room.Artista;
import com.example.astramanagement.room.ArtistaDatabase;
import com.example.astramanagement.room.DatabaseClient;

import java.util.List;

//Ficha de un artista recibe id y lee los datos de room
public class FichaArtistaActivity extends AppCompatActivity {

    private Button btnEditar;
    private int idArtista;
    private DatabaseClient db;

    private TextView tvNombre, tvTipo, tvGenero, tvMiembros;
    private TextView tvAnio, tvPopularidad, tvEstado;
    private RatingBar rbFicha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_ficha_artista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Recuperar el id enviado desde ArtistasActivity
        idArtista = getIntent().getIntExtra(Artista.EXTRA_ID, -1);
        //Conectar con Room
        db = DatabaseClient.getInstance(this);

        tvNombre = findViewById(R.id.tvNombre);
        tvTipo = findViewById(R.id.tvTipo);
        tvGenero = findViewById(R.id.tvGenero);
        tvMiembros = findViewById(R.id.tvMiembros);
        tvAnio = findViewById(R.id.tvAnio);
        tvPopularidad = findViewById(R.id.tvPopularidad);
        tvEstado = findViewById(R.id.tvEstado);
        rbFicha = findViewById(R.id.rbFicha);

        btnEditar = findViewById(R.id.btnEditar);

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, AltaArtistaActivity.class);
            intent.putExtra(Artista.EXTRA_ID, idArtista);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarArtista();
    }

    private void cargarArtista(){
        ArtistaDatabase.executor.execute(() -> {
            //Buscamos el artista en Room mediante su ID
            Artista artista = db.getDb().artistaDao().getById(idArtista);
            //Mostramos los datos recuperados
            if (artista == null) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "No se encuentra el artista",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                mostrar(artista);
            }
        });
    }

    private void mostrar(Artista a) {

        runOnUiThread(() -> {

            if (a != null) {

                tvNombre.setText(a.getNombre());
                tvTipo.setText("Tipo: " + a.getTipo());
                tvGenero.setText("Género: " + a.getGenero());
                tvMiembros.setText("Miembros: " + a.getMiembros());
                tvAnio.setText("Año de debut: " + a.getFechaDebut());
                tvPopularidad.setText("Popularidad: " + a.getPopularidad());

                String[] estados = {"Activo", "Hiatus", "En comeback"};

                if (a.getEstado() >= 0 && a.getEstado() < estados.length) {
                    tvEstado.setText("Estado: " + estados[a.getEstado()]);
                }

                rbFicha.setRating(a.getValoracion());

            } else {
                Toast.makeText(this, "No se ha encontrado el artista", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}