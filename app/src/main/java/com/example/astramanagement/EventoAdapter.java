
package com.example.astramanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.astramanagement.room.Evento;

import java.util.List;

// Adaptador propio de RecyclerView: convierte cada Evento en una tarjeta
public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private List<Evento> dataEvento;
    private Listener listener;

    // Interfaz para comunicar los clics a EventosActivity
    public interface Listener {
        void onClick(Evento evento);
        void onLongClick(Evento evento);
    }

    public EventoAdapter(List<Evento> eventos, Listener listener) {
        this.dataEvento = eventos;
        this.listener = listener;
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreEvento;
        public TextView fecha;
        public TextView hora;
        public TextView lugar;
        public TextView descripcion;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreEvento = itemView.findViewById(R.id.tvItemNombreEvento);
            fecha = itemView.findViewById(R.id.tvItemFecha);
            hora = itemView.findViewById(R.id.tvItemHora);
            lugar = itemView.findViewById(R.id.tvItemLugar);
            descripcion = itemView.findViewById(R.id.tvItemDescripcion);
        }
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater from = LayoutInflater.from(parent.getContext());

        View inflate = from.inflate(R.layout.item_evento, parent, false);

        return new EventoViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {

        Evento evento = this.dataEvento.get(position);

        holder.nombreEvento.setText(evento.nombre);
        holder.fecha.setText("Fecha: " + evento.fecha);
        holder.hora.setText("Hora: " + evento.hora);
        holder.lugar.setText("Lugar: " + evento.lugar);
        holder.descripcion.setText(evento.descripcion);

        // Clic corto: abrir formulario para editar
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(evento);
        });

        // Clic largo: solicitar eliminación
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(evento);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return this.dataEvento.size();
    }
}

