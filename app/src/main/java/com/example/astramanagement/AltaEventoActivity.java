
package com.example.astramanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.astramanagement.room.Artista;
import com.example.astramanagement.room.Evento;
import com.example.astramanagement.room.ArtistaDatabase;
import com.example.astramanagement.room.DatabaseClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AltaEventoActivity extends AppCompatActivity {

    private EditText etNombre, etLugar, etDescripcion;

    private DatePicker dpFechaEvento;
    private TimePicker tpHoraEvento;

    private Spinner spArtista;

    private Button btnReset, btnEnviar;

    private int idEvento = -1; // -1 = alta nueva

    private DatabaseClient db;

    private List<Artista> listaArtistas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_alta_evento);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);

                    return insets;
                });

        // Conectar con la base de datos
        db = DatabaseClient.getInstance(this);

        idEvento = getIntent().getIntExtra(Evento.EXTRA_ID, -1);

        // Conectar con los elementos del formulario
        etNombre = findViewById(R.id.etNombreEvento);
        etLugar = findViewById(R.id.etLugarEvento);
        etDescripcion = findViewById(R.id.etDescripcionEvento);

        dpFechaEvento = findViewById(R.id.dpFechaEvento);
        tpHoraEvento = findViewById(R.id.tpHoraEvento);

        spArtista = findViewById(R.id.spArtista);

        btnReset = findViewById(R.id.btnReset);
        btnEnviar = findViewById(R.id.btnEnviar);

        // Utilizar formato de 24 horas
        tpHoraEvento.setIs24HourView(true);

        // Cargar los artistas en el Spinner
        cargarArtistas();

        // Limpiar datos
        btnReset.setOnClickListener(v -> {

            etNombre.setText("");
            etLugar.setText("");
            etDescripcion.setText("");

            // Volver a la fecha actual
            java.util.Calendar calendario =
                    java.util.Calendar.getInstance();

            dpFechaEvento.updateDate(
                    calendario.get(java.util.Calendar.YEAR),
                    calendario.get(java.util.Calendar.MONTH),
                    calendario.get(java.util.Calendar.DAY_OF_MONTH)
            );

            // Restablecer la hora
            tpHoraEvento.setHour(20);
            tpHoraEvento.setMinute(0);

            if (!listaArtistas.isEmpty()) {
                spArtista.setSelection(0);
            }
        });

        // Guardar o actualizar evento
        btnEnviar.setOnClickListener(v -> {

            // Recoger los datos introducidos
            String nombre = etNombre.getText().toString().trim();
            String lugar = etLugar.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            // Recoger la fecha seleccionada
            int dia = dpFechaEvento.getDayOfMonth();
            int mes = dpFechaEvento.getMonth() + 1;
            int anio = dpFechaEvento.getYear();

            String fecha = String.format(
                    Locale.getDefault(),
                    "%02d/%02d/%04d",
                    dia, mes, anio
            );

            // Recoger la hora seleccionada
            int horaSeleccionada = tpHoraEvento.getHour();
            int minutoSeleccionado = tpHoraEvento.getMinute();

            String hora = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    horaSeleccionada, minutoSeleccionado
            );

            // Comprobar campos obligatorios
            if (nombre.isEmpty() || lugar.isEmpty()) {

                Toast.makeText(this,
                        "Rellena todos los campos obligatorios",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            // Comprobar que hay artistas disponibles
            if (listaArtistas.isEmpty()) {

                Toast.makeText(this,
                        "Primero debes crear un artista",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            // Recoger el artista seleccionado
            int posicion = spArtista.getSelectedItemPosition();

            Artista artistaSeleccionado =
                    listaArtistas.get(posicion);

            // Crear el objeto Evento
            Evento evento = new Evento();

            evento.id = idEvento == -1 ? 0 : idEvento;
            evento.artistaId = artistaSeleccionado.getId();
            evento.nombre = nombre;
            evento.fecha = fecha;
            evento.hora = hora;
            evento.lugar = lugar;
            evento.descripcion = descripcion;

            // Ejecutar la operación en segundo plano
            ArtistaDatabase.executor.execute(() -> {

                try {

                    if (idEvento == -1) {

                        // Insertar un evento nuevo
                        db.getDb().eventoDAO().insertarEvento(evento);

                    } else {

                        // Actualizar un evento existente
                        db.getDb().eventoDAO().actualizarEvento(evento);
                    }

                    // Volver al hilo principal
                    runOnUiThread(() -> {

                        Toast.makeText(this,
                                "Evento guardado correctamente",
                                Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);
                        finish();
                    });

                } catch (Exception e) {

                    e.printStackTrace();

                    runOnUiThread(() -> {

                        Toast.makeText(this,
                                "Error al guardar el evento",
                                Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    // Cargar los artistas disponibles
    private void cargarArtistas() {

        ArtistaDatabase.executor.execute(() -> {

            listaArtistas = db.getDb().artistaDao().getAll();

            List<String> nombresArtistas = new ArrayList<>();

            for (Artista artista : listaArtistas) {
                nombresArtistas.add(artista.getNombre());
            }

            runOnUiThread(() -> {

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        nombresArtistas
                );

                adapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item
                );

                spArtista.setAdapter(adapter);

                if (listaArtistas.isEmpty()) {

                    Toast.makeText(this,
                            "No hay artistas registrados",
                            Toast.LENGTH_SHORT).show();
                }

                // Si estamos editando, cargar los datos del evento
                if (idEvento != -1) {
                    cargarEvento();
                }
            });
        });
    }

    // Cargar los datos de un evento para editarlo
    private void cargarEvento() {

        ArtistaDatabase.executor.execute(() -> {

            Evento evento = db.getDb().eventoDAO().getById(idEvento);

            runOnUiThread(() -> {

                if (evento != null) {

                    ((TextView) findViewById(R.id.tvAltaTitulo))
                            .setText("Editar Evento");

                    etNombre.setText(evento.nombre);
                    etLugar.setText(evento.lugar);
                    etDescripcion.setText(evento.descripcion);

                    // Separar la fecha guardada
                    String[] partesFecha = evento.fecha.split("/");

                    int dia = Integer.parseInt(partesFecha[0]);
                    int mes = Integer.parseInt(partesFecha[1]) - 1;
                    int anio = Integer.parseInt(partesFecha[2]);

                    dpFechaEvento.updateDate(anio, mes, dia);

                    // Separar la hora guardada
                    String[] partesHora = evento.hora.split(":");

                    int hora = Integer.parseInt(partesHora[0]);
                    int minuto = Integer.parseInt(partesHora[1]);

                    tpHoraEvento.setHour(hora);
                    tpHoraEvento.setMinute(minuto);

                    // Seleccionar el artista asociado al evento
                    for (int i = 0; i < listaArtistas.size(); i++) {

                        if (listaArtistas.get(i).getId() == evento.artistaId) {

                            spArtista.setSelection(i);
                            break;
                        }
                    }
                }
            });
        });
    }
}
