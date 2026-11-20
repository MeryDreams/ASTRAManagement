package com.example.astramanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfirmarAsistenciaActivity extends AppCompatActivity {

    public static final String EXTRA_ASISTENCIA = "extra_asistencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_confirmar_asistencia);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);

                    return insets;
                });

        Button btnAceptar = findViewById(R.id.btnAceptar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnAceptar.setOnClickListener(v -> {
            devolverResultado(true);
        });

        btnCancelar.setOnClickListener(v -> {
            devolverResultado(false);
        });
    }

    private void devolverResultado(boolean aceptada) {

        Intent data = new Intent();

        data.putExtra(EXTRA_ASISTENCIA, aceptada);

        setResult(Activity.RESULT_OK, data);

        finish();
    }
}
