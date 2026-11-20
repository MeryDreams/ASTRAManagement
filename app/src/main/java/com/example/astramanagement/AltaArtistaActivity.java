
package com.example.astramanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
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

public class AltaArtistaActivity extends AppCompatActivity {

    private static final int[] ESTADO_ART = {
            R.id.rbActivo, R.id.rbHiatus, R.id.rbComeback
    };

    private EditText etNombre, etGenero, etMiembros, etFecha;
    private Spinner spTipo;
    private RadioGroup rgEstado;
    private RatingBar rbValoracion;

    private Switch swActivo;
    private ProgressBar progressBar;
    private SeekBar seekBarPopularidad;
    private TextView tvPopularidad;

    private Button btnReset, btnEnviar;

    private int idArtista = -1;
    private DatabaseClient db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_alta_artista);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        // Conectar con la base de datos
        db = DatabaseClient.getInstance(this);
        idArtista = getIntent().getIntExtra(Artista.EXTRA_ID, -1);

        // Conectar los elementos del formulario
        etNombre = findViewById(R.id.etNombre);
        etGenero = findViewById(R.id.etGenero);
        etMiembros = findViewById(R.id.etMiembros);
        etFecha = findViewById(R.id.etFecha);

        spTipo = findViewById(R.id.spTipo);
        rgEstado = findViewById(R.id.rgEstado);
        rbValoracion = findViewById(R.id.rbValoracion);

        swActivo = findViewById(R.id.swActivo);
        progressBar = findViewById(R.id.progressBar);
        seekBarPopularidad = findViewById(R.id.seekBarPopularidad);
        tvPopularidad = findViewById(R.id.tvPopularidad);

        btnReset = findViewById(R.id.btnReset);
        btnEnviar = findViewById(R.id.btnEnviar);

        // La barra de progreso empieza oculta
        progressBar.setVisibility(View.GONE);

        // Cargar tipos en el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.Grupos,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spTipo.setAdapter(adapter);

        // Mostrar el valor de popularidad seleccionado
        seekBarPopularidad.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        tvPopularidad.setText("Popularidad: " + (progress + 1) + "/10");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );

        // Sincronizar el Switch con el RadioButton Activo
        rgEstado.setOnCheckedChangeListener((group, checkedId) -> {
            swActivo.setChecked(checkedId == R.id.rbActivo);
        });

        swActivo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rgEstado.check(R.id.rbActivo);
            } else if (rgEstado.getCheckedRadioButtonId() == R.id.rbActivo) {
                rgEstado.clearCheck();
            }
        });

        // Limpiar datos
        btnReset.setOnClickListener(v -> {
            etNombre.setText("");
            spTipo.setSelection(0);
            etGenero.setText("");
            etMiembros.setText("");
            etFecha.setText("");

            rgEstado.clearCheck();
            swActivo.setChecked(false);

            rbValoracion.setRating(0);
            seekBarPopularidad.setProgress(4);

            progressBar.setVisibility(View.GONE);
        });

        // Guardar o actualizar artista
        btnEnviar.setOnClickListener(v -> {

            // Recoger los datos introducidos
            String nombre = etNombre.getText().toString().trim();
            String genero = etGenero.getText().toString().trim();
            String miembrosTexto = etMiembros.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();

            // Comprobar que los campos obligatorios no estén vacíos
            if (nombre.isEmpty() || genero.isEmpty()
                    || miembrosTexto.isEmpty() || fecha.isEmpty()) {

                Toast.makeText(this, "Rellena todos los campos",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Comprobar que se ha seleccionado un estado
            int estado = -1;

            for (int i = 0; i < ESTADO_ART.length; i++) {
                if (rgEstado.getCheckedRadioButtonId() == ESTADO_ART[i]) {
                    estado = i;
                    break;
                }
            }

            if (estado == -1) {
                Toast.makeText(this, "Selecciona el estado del artista",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Recoger los datos restantes
            int tipo = spTipo.getSelectedItemPosition();
            int miembros;
            int popularidad = seekBarPopularidad.getProgress() + 1;
            float valoracion = rbValoracion.getRating();

            try {
                miembros = Integer.parseInt(miembrosTexto);

                if (miembros <= 0) {
                    Toast.makeText(this, "Comprueba el número de miembros",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Introduce un número de miembros válido",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear el objeto artista
            Artista artista = new Artista(
                    idArtista == -1 ? 0 : idArtista,
                    nombre,
                    tipo,
                    genero,
                    miembros,
                    fecha,
                    popularidad,
                    estado,
                    valoracion
            );

            // Mostrar la barra de progreso
            progressBar.setVisibility(View.VISIBLE);
            btnEnviar.setEnabled(false);

            // Ejecutar la operación en segundo plano
            ArtistaDatabase.executor.execute(() -> {

                try {

                    if (idArtista == -1) {
                        // Insertar un artista nuevo
                        db.getDb().artistaDao().insertarArtista(artista);
                    } else {
                        // Actualizar un artista existente
                        db.getDb().artistaDao().actualizarArtista(artista);
                    }

                    // Volver al hilo principal
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Artista guardado correctamente",
                                Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);
                        finish();
                    });

                } catch (Exception e) {
                    e.printStackTrace();

                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnEnviar.setEnabled(true);

                        Toast.makeText(this, "Error al guardar",
                                Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });

        // Crear artista, modo edición
        if (idArtista != -1) {

            ((TextView) findViewById(R.id.tvAltaTitulo))
                    .setText("Editar Artista");

            if (savedInstanceState == null) {

                ArtistaDatabase.executor.execute(() -> {

                    Artista artista = db.getDb().artistaDao().getById(idArtista);

                    runOnUiThread(() -> {

                        if (artista != null) {
                            etNombre.setText(artista.getNombre());
                            etGenero.setText(artista.getGenero());
                            etMiembros.setText(String.valueOf(artista.getMiembros()));
                            etFecha.setText(artista.getFechaDebut());

                            rgEstado.check(ESTADO_ART[artista.getEstado()]);
                            rbValoracion.setRating(artista.getValoracion());
                            spTipo.setSelection(artista.getTipo());

                            // Recuperar la popularidad
                            seekBarPopularidad.setProgress(
                                    artista.getPopularidad() - 1
                            );

                            // Recuperar el estado del Switch
                            swActivo.setChecked(artista.getEstado() == 0);
                        }
                    });
                });
            }
        }
    }
}
