package com.example.mispeliculas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder> {

    private List<Pelicula> listaPeliculas;
    private OnPeliculaClickListener listener;

    public interface OnPeliculaClickListener {
        void onPeliculaClick(Pelicula pelicula);
    }

    public PeliculaAdapter(
            List<Pelicula> listaPeliculas,
            OnPeliculaClickListener listener) {

        this.listaPeliculas = listaPeliculas;
        this.listener = listener;
    }

    public static class PeliculaViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPelicula;
        TextView tvTituloPelicula;
        TextView tvGeneroPelicula;
        TextView tvAnioPelicula;

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
            //item_pelicula
            imgPelicula = itemView.findViewById(R.id.imgPelicula);
            tvTituloPelicula = itemView.findViewById(R.id.tvTituloPelicula);
            tvGeneroPelicula = itemView.findViewById(R.id.tvGeneroPelicula);
            tvAnioPelicula = itemView.findViewById(R.id.tvAnioPelicula);
        }
    }

    @Override
    public PeliculaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pelicula, parent, false);

        return new PeliculaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PeliculaViewHolder holder,
            int position) {

        Pelicula pelicula = listaPeliculas.get(position);

        holder.tvTituloPelicula.setText(pelicula.getTitulo());
        holder.tvGeneroPelicula.setText(pelicula.getGenero());
        holder.tvAnioPelicula.setText(String.valueOf(pelicula.getAnio()));

        int idImagen = holder.itemView.getContext()
                .getResources()
                .getIdentifier(
                        pelicula.getImagen(),
                        "drawable",
                        holder.itemView.getContext().getPackageName()
                );

        holder.imgPelicula.setImageResource(idImagen);

        holder.itemView.setOnClickListener(v -> {
            listener.onPeliculaClick(pelicula);
        });
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }
}