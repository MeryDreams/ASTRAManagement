package com.example.astramanagement.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.astramanagement.MainActivity;
import com.example.astramanagement.R;
import com.example.astramanagement.bd.AppDatabase;
import com.example.astramanagement.dao.UsuarioDAO;
import com.example.astramanagement.modelo.Usuario;

public class LoginActivity extends AppCompatActivity {

    // Usuario fijo provisional. En la práctica 12 se sustituye por SQLite.
    //private static final String CORREO_OK = "ceo@astra.com";
    //private static final String CLAVE_OK = "astra123";

    private EditText etCorreo, etContrasena;

    private Button btnAcceder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        btnAcceder = findViewById(R.id.btnAcceder);

        /*btnAcceder.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String clave = etContrasena.getText().toString();

            if (correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Rellenar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (correo.equals(CORREO_OK) && clave.equals(CLAVE_OK)) {
                Intent intent = new Intent(this, MainActivity.class);
                // Enviar el nombre del usuario a MainActivity
                intent.putExtra("nombre_usuario", nombreUsuario);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }

        });*/



        btnAcceder.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String clave = etContrasena.getText().toString();

            if (correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Rellenar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            //Obtener la base de datos y el DAO
            AppDatabase bd = AppDatabase.getInstance(this);
            UsuarioDAO usuarioDAO = new UsuarioDAO(bd);

            //Consultar SQLite en segundo plano
            AppDatabase.executor.execute(() -> {

                try{
                    Usuario usuario = usuarioDAO.login(correo, clave);

                    runOnUiThread(() -> {

                        if (usuario != null) {
                            // Recuperar el nombre de SQLite
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("nombre", usuario.getNombre());

                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                    runOnUiThread(() ->
                            Toast.makeText(LoginActivity.this,
                                    "Error SQLite: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show()
                    );
                }

            });
        });
    }
}