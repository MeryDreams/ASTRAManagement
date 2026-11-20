
package com.example.astramanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class AsistenciaActivity extends AppCompatActivity {

    public static final String EXTRA_ASISTENCIA = "extra_asistencia";

    private EditText etNombreStaff;
    private TextView tvResultado;
    private Button btnAcceder;

    private String nombre = "";

    // Lanzador para abrir la pantalla de confirmación
    private final ActivityResultLauncher<Intent> lanzarConfirmacion =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent datos = result.getData();

                            if (datos != null) {

                                boolean haAceptado = datos.getBooleanExtra(
                                        EXTRA_ASISTENCIA, false);

                                String estadoAsistencia =
                                        haAceptado ? "ACEPTADO" : "CANCELADO";

                                tvResultado.setText("De acuerdo "+nombre+", usted ha "+estadoAsistencia+" la asistencia al showcase de ASTRA"
                                );
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_asistencia);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);

                    return insets;
                });

        // Conectar con los elementos del XML
        etNombreStaff = findViewById(R.id.etNombreStaff);
        tvResultado = findViewById(R.id.tvResultado);
        btnAcceder = findViewById(R.id.btnContinuar);

        // Abrir confirmación
        btnAcceder.setOnClickListener(v -> {

            nombre = etNombreStaff.getText().toString().trim();

            if (nombre.isEmpty()) {

                Toast.makeText(this,
                        "Por favor, introduce tu nombre",
                        Toast.LENGTH_LONG).show();

            } else {

                Intent intent = new Intent(
                        AsistenciaActivity.this,
                        ConfirmarAsistenciaActivity.class);

                lanzarConfirmacion.launch(intent);
            }
        });
    }
}
