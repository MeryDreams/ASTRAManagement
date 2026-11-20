package com.example.astramanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astramanagement.room.Artista;

import java.util.List;

// Adaptador propio de RecyclerView: convierte cada Artista en una tarjeta
public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ArtistaViewHolder> {

    private List<Artista> dataArtista;
    private Listener listener;

    //Interfaz para comunicar los clics a ArtistasActivity
    public interface Listener{
        void onClick(Artista artista);
        void onLongClick(Artista artista);
    }

    public ArtistaAdapter(List<Artista> artistas, Listener listener){
        this.dataArtista = artistas;
        this.listener = listener;
    }


    public static class ArtistaViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView nombreArtista;
        public TextView tipoGenero;
        public TextView miembros;

        public ArtistaViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.ivArtista);
            nombreArtista = itemView.findViewById(R.id.tvItemNombre);
            tipoGenero = itemView.findViewById(R.id.tvItemTipoGenero);
            miembros = itemView.findViewById(R.id.tvItemMiembros);


        }
    }

    @NonNull
    @Override
    public ArtistaAdapter.ArtistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        View inflate = from.inflate(R.layout.item_artista, parent, false);
        return new ArtistaViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistaAdapter.ArtistaViewHolder holder, int position) {
        Artista artista = this.dataArtista.get(position);
        holder.nombreArtista.setText(artista.getNombre());
        holder.tipoGenero.setText(artista.getGenero());
        holder.miembros.setText("Miembros: "+artista.getMiembros());

        //Clic corto: abrir ficha
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(artista);
        });

        //Clic largo: solicitar eliminacion
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(artista);
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return this.dataArtista.size();
    }
}
