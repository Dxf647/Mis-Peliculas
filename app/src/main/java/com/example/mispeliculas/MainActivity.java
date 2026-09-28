package com.example.mispeliculas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView rvPeliculasRecientes;
    private RecyclerView rvPeliculasValoradas;
    private TextView btnVerMasRecientes;
    private TextView btnVerMasValoradas;
    private PeliculaAdapter adapterRecientes;
    private PeliculaAdapter adapterValoradas;
    private List<Pelicula> listaPeliculas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        rvPeliculasRecientes=findViewById(R.id.rvPeliculasRecientes);
        rvPeliculasValoradas=findViewById(R.id.rvPeliculasValoradas);
        btnVerMasRecientes=findViewById(R.id.btnVerMasRecientes);
        btnVerMasValoradas=findViewById(R.id.btnVerMasValoradas);
        rvPeliculasRecientes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvPeliculasValoradas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btnVerMasRecientes.setOnClickListener(v -> {
            abrirVerMas();
        });

        btnVerMasValoradas.setOnClickListener(v -> {
            abrirVerMas();
        });

        cargarPeliculas();
    }

    private void cargarPeliculas() {

        listaPeliculas=dbHelper.obtenerTodasLasPeliculas();
        List<Pelicula> peliculasRecientes=new ArrayList<>(listaPeliculas);
        List<Pelicula> peliculasValoradas= new ArrayList<>(listaPeliculas);

        Collections.sort(
                peliculasRecientes,
                new Comparator<Pelicula>() {
                    @Override
                    public int compare(
                            Pelicula pelicula1,
                            Pelicula pelicula2) {

                        Date fecha1 = convertirFecha(pelicula1.getFechaAgregado());
                        Date fecha2 = convertirFecha(pelicula2.getFechaAgregado());
                        if (fecha1 == null || fecha2 == null) {
                            return 0;
                        }
                        return fecha2.compareTo(fecha1);
                    }
                }
        );

        Collections.sort(
                peliculasValoradas,
                new Comparator<Pelicula>() {
                    @Override
                    public int compare(
                            Pelicula pelicula1,
                            Pelicula pelicula2) {

                        return Integer.compare(
                                pelicula2.getCalificacion(),
                                pelicula1.getCalificacion()
                        );
                    }
                }
        );

        if (peliculasRecientes.size() > 5) {
            peliculasRecientes = new ArrayList<>(peliculasRecientes.subList(0, 5));
        }

        if (peliculasValoradas.size() > 5) {
            peliculasValoradas = new ArrayList<>(peliculasValoradas.subList(0, 5));
        }

        adapterRecientes = new PeliculaAdapter(peliculasRecientes, pelicula -> abrirDetalle(pelicula));

        rvPeliculasRecientes.setAdapter(adapterRecientes);

        adapterValoradas = new PeliculaAdapter(peliculasValoradas, pelicula -> abrirDetalle(pelicula));

        rvPeliculasValoradas.setAdapter(adapterValoradas);
    }

    private void abrirDetalle(Pelicula pelicula) {

        Intent intent = new Intent(
                MainActivity.this,
                DetallePeliculaActivity.class
        );

        intent.putExtra("id_pelicula", pelicula.getId());
        startActivity(intent);
    }

    private void abrirVerMas() {

        Intent intent = new Intent(
                MainActivity.this, VerMasPeliculasActivity.class
        );
        startActivity(intent);
    }

    private Date convertirFecha(String fechaTexto) {

        if (fechaTexto == null) {
            return null;
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            return formato.parse(fechaTexto);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dbHelper != null) {
            cargarPeliculas();
        }
    }
}