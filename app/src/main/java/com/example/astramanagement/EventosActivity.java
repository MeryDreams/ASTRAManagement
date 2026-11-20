
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

import com.example.astramanagement.room.Evento;
import com.example.astramanagement.room.ArtistaDatabase;
import com.example.astramanagement.room.DatabaseClient;

import java.util.List;

// Listado de Eventos RecyclerView + CardView (item_evento.xml)
// Clic corto: abrir formulario para editar
// Clic largo: borrar evento

public class EventosActivity extends AppCompatActivity
        implements EventoAdapter.Listener {

    private RecyclerView rvEventos;
    private Button btnAnadir;
    private DatabaseClient db;

    private EventoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_eventos);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);

                    return insets;
                });

        rvEventos = findViewById(R.id.rvEventos);
        btnAnadir = findViewById(R.id.btnAnadirEvento);

        db = DatabaseClient.getInstance(this);

        rvEventos.setLayoutManager(new LinearLayoutManager(this));

        btnAnadir.setOnClickListener(v -> {
            Intent intent = new Intent(this, AltaEventoActivity.class);
            startActivity(intent);
        });
    }

    // Cuando se vuelva después de añadir un evento,
    // se volverá a consultar Room y se actualizará el listado
    @Override
    protected void onResume() {
        super.onResume();
        cargarEventos();
    }

    private void cargarEventos() {

        ArtistaDatabase.executor.execute(() -> {

            // Consultamos a Room: obtenemos todos los eventos guardados
            List<Evento> lista = db.getDb().eventoDAO().getAll();

            // Mostramos los eventos en el RecyclerView
            runOnUiThread(() -> {

                // Creamos el adaptador: le asignamos la lista de eventos
                adapter = new EventoAdapter(lista, this);

                // Conectamos el adaptador al RecyclerView
                rvEventos.setAdapter(adapter);
            });
        });
    }

    @Override
    public void onClick(Evento evento) {

        Intent intent = new Intent(this, AltaEventoActivity.class);

        // Enviamos el id del evento para editarlo
        intent.putExtra(Evento.EXTRA_ID, evento.id);

        startActivity(intent);
    }

    @Override
    public void onLongClick(Evento evento) {

        new AlertDialog.Builder(this)
                .setTitle("Eliminar evento")
                .setMessage("¿Seguro que quieres eliminar "
                        + evento.nombre + "?")
                .setPositiveButton("Eliminar",
                        (d, w) -> eliminar(evento))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminar(Evento evento) {

        ArtistaDatabase.executor.execute(() -> {

            // Eliminamos el evento de Room
            db.getDb().eventoDAO().eliminarEvento(evento);

            runOnUiThread(() -> {

                Toast.makeText(this,
                        evento.nombre + " eliminado",
                        Toast.LENGTH_SHORT).show();

                // Actualizamos el listado
                cargarEventos();
            });
        });
    }
}
