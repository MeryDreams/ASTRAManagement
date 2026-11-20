package com.example.astramanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.astramanagement.bd.AppDatabase;
import com.example.astramanagement.room.Artista;
import com.example.astramanagement.room.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

//Filtra los artistas que leen de room
//Boton solo se activa si hay tipo, artista y sede seleccionados

public class BuscarArtista extends AppCompatActivity {

    private Spinner spTipoSede, spArtista;
    private ListView lvSede;
    private Button btnBuscar;
    private DatabaseClient db;

    private final List<Artista> artistasDelTipo = new ArrayList<>();
    private final List<String> nombres = new ArrayList<>();
    private ArrayAdapter<String> adapterArtistas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_buscar_artista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = DatabaseClient.getInstance(this);

        spTipoSede = findViewById(R.id.spTipoSede);
        spArtista = findViewById(R.id.spArtista);
        lvSede = findViewById(R.id.lvSede);
        btnBuscar = findViewById(R.id.btnBuscar);


        // Spinner de tipos
        List<String> tipos = new ArrayList<>();
        tipos.add("Elige tipo");
        for (String t : getResources().getStringArray(R.array.Grupos)) tipos.add(t);
        ArrayAdapter<String> adapterTipos =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoSede.setAdapter(adapterTipos);

        // Spinner de artistas (se rellena según el tipo)
        List<String> nombres = new ArrayList<>();
        nombres.add("Elige artista");
        adapterArtistas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapterArtistas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spArtista.setAdapter(adapterArtistas);


        // ListView con RadioButtons para la sede
        lvSede.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvSede.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,
                getResources().getStringArray(R.array.Sedes)));
        lvSede.setOnItemClickListener((parent, view, position, id) -> actualizarBoton());

        //Al seleccionar un tipo, cargar sus artistas
        spTipoSede.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarArtistaPorTipo(position-1); //posición 0 = "Elige tipo"
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Al seleccionar un artista, comprobar el botón
        spArtista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualizarBoton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBuscar.setOnClickListener(v -> mostrarResumen());
    }

    private void cargarArtistaPorTipo(int position) {
        artistasDelTipo.clear();
        nombres.clear();
        if (position < 0) {
            adapterArtistas.notifyDataSetChanged();
            spArtista.setSelection(0);
            actualizarBoton();
            return;
        }
        AppDatabase.executor.execute(() -> {
            List<Artista> lista = db.getDb().artistaDao().getByTipo(position);
            runOnUiThread(() -> {
                artistasDelTipo.addAll(lista);
                for (Artista a : lista) nombres.add(a.getNombre());
                adapterArtistas.notifyDataSetChanged();
                spArtista.setSelection(0);
                actualizarBoton();
            });
        });

    }

    private void actualizarBoton() {
        boolean todoElegido = spTipoSede.getSelectedItemPosition() > 0 && spArtista.getSelectedItemPosition() > 0 && lvSede.getCheckedItemPosition() != ListView.INVALID_POSITION;
        btnBuscar.setEnabled(todoElegido);
    }

    private void mostrarResumen() {
        Artista a = artistasDelTipo.get(spArtista.getSelectedItemPosition() - 1);
        String[] tipos = getResources().getStringArray(R.array.Grupos);
        String[] sedes = getResources().getStringArray(R.array.Sedes);
        String mensaje = a.toString()+"\n Sede:"+lvSede.getCheckedItemPosition();
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}