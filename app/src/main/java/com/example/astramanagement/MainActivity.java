package com.example.astramanagement;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.astramanagement.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static final String WEB_OFICIAL = "https://hybecorp.com";

    private TextView tvArrastrar, tvSoltar, tvBienvenido;

    private Button btnVerArtitas, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvBienvenido = findViewById(R.id.tvBienvenido);
        //Recoger el nombre usuario que ha iniciado sesion
        String nombreUsuario = getIntent().getStringExtra("nombre");

        tvBienvenido.setText("Bienvenido "+nombreUsuario);

        //BOTON SALIR: CERRAR SESION
        btnSalir = findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(v -> {

            //Eliminar los datos de la sesion
            SharedPreferences preferencias = getSharedPreferences(
                    "sesion", MODE_PRIVATE
            );

            preferencias.edit().clear().apply();

            //Volver a LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

            //Evitar volver al MainActivity con el boton atrás
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        });

        btnVerArtitas = findViewById(R.id.btnVerArtistas);

        btnVerArtitas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ArtistasActivity.class);
            startActivity(intent);
        });

        //BOTÓN ALTAS
        LinearLayout cardAltas = findViewById(R.id.cardAltas);

        cardAltas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AltaArtistaActivity.class);
            startActivity(intent);
        });

        // BOTÓN BUSCAR
        LinearLayout cardBuscar = findViewById(R.id.cardBuscar);

        cardBuscar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarArtista.class);
            startActivity(intent);
        });

        //BOTÓN EVENTOS
        LinearLayout cardEventos = findViewById(R.id.cardEventos);

        cardEventos.setOnClickListener(v -> {
           Intent intent = new Intent(MainActivity.this, EventosActivity.class);
            startActivity(intent);
        });

        //BOTÓN ASISTENCIA
        LinearLayout cardAdmin = findViewById(R.id.cardAsistencia);

        cardAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AsistenciaActivity.class);
            startActivity(intent);
        });

        configurarDragAndDrop();
    }

    private void configurarDragAndDrop(){
        tvArrastrar = findViewById(R.id.tvArrastrar);

        tvArrastrar.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("destino", "AcercaDe");
            v.startDragAndDrop(data, new View.DragShadowBuilder(v),v,0);
            return true;
        });

        tvSoltar = findViewById(R.id.tvSoltar);
        tvSoltar.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(getColor(R.color.astra_purple));
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(getColor(R.color.astra_dark_gray));
                    return true;
                case DragEvent.ACTION_DROP:
                    v.setBackgroundColor(getColor(R.color.astra_dark_gray));
                    startActivity(new Intent(MainActivity.this, AcercaDeActivity.class));
                    return true;
                default:
                    return true;
            }
        });
    }
}