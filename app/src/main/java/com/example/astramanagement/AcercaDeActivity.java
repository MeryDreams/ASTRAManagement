package com.example.astramanagement;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class AcercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_acerca_de);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View root = findViewById(R.id.main);
        TextView tvPropietario = findViewById(R.id.tvPropietario);

        tvPropietario.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("propietario", tvPropietario.getText());
            v.startDragAndDrop(data, new View.DragShadowBuilder(v),v,0);
            return true;
        });

        //Drop al soltar en cualquier parte, se cierra y vuelve a MainActivity
        root.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DROP:
                    finish();
                    return true;
                default:
                    return true;
            }
        });
    }
}