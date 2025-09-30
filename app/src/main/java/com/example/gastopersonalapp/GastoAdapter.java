package com.example.gastopersonalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GastoAdapter extends RecyclerView.Adapter<GastoAdapter.GastoViewHolder> {

    private List<Gasto> listaGastos;

    public GastoAdapter(List<Gasto> listaGastos) {
        this.listaGastos = listaGastos;
    }

    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gasto, parent, false);
        return new GastoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GastoViewHolder holder, int position) {
        Gasto gasto = listaGastos.get(position);
        holder.tvDescripcion.setText(gasto.getDescripcion());
        holder.tvMonto.setText(String.valueOf(gasto.getMonto()));
    }

    @Override
    public int getItemCount() {
        return listaGastos.size();
    }

    public static class GastoViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescripcion, tvMonto;

        public GastoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvMonto = itemView.findViewById(R.id.tvMonto);
        }
    }
}


