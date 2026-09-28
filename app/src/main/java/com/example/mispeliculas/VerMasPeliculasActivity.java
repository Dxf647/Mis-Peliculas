package com.example.mispeliculas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VerMasPeliculasActivity extends AppCompatActivity {

    private RecyclerView rvTodasLasPeliculas;
    private Button btnAgregarPelicula;

    private Spinner spFiltroGenero;
    private Spinner spFiltroAnio;
    private Spinner spFiltroClasificacion;

    private DatabaseHelper dbHelper;
    private PeliculaAdapter adapter;

    private List<Pelicula> listaPeliculas;
    private List<Pelicula> listaFiltrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mas_peliculas);

        dbHelper = new DatabaseHelper(this);

        rvTodasLasPeliculas = findViewById(R.id.rvTodasLasPeliculas);
        btnAgregarPelicula = findViewById(R.id.btnAgregarPelicula);
        spFiltroGenero = findViewById(R.id.spFiltroGenero);
        spFiltroAnio = findViewById(R.id.spFiltroAnio);
        spFiltroClasificacion = findViewById(R.id.spFiltroClasificacion);
        rvTodasLasPeliculas.setLayoutManager(new GridLayoutManager(this, 3));

        configurarFiltros();
        cargarPeliculas();

        btnAgregarPelicula.setOnClickListener(v -> {abrirFormulario();});

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
                    boolean formularioAbierto = getSupportFragmentManager().getBackStackEntryCount() > 0;

                    if (formularioAbierto) {
                        rvTodasLasPeliculas.setVisibility(View.GONE);
                    } else {
                        rvTodasLasPeliculas.setVisibility(View.VISIBLE);
                        findViewById(R.id.contenedorFormulario).setVisibility(View.GONE);
                        cargarPeliculas();
                    }
                });
    }

    private void configurarFiltros() {

        String[] generos = {
                "Todos los géneros",
                "Acción",
                "Animación",
                "Ciencia ficción",
                "Comedia",
                "Romance",
                "Terror"
        };

        ArrayAdapter<String> adapterGenero = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, generos
        );
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFiltroGenero.setAdapter(adapterGenero);

        String[] clasificaciones = {
                "Todas las clasificaciones",
                "5",
                "4",
                "3",
                "2",
                "1"
        };

        ArrayAdapter<String> adapterClasificacion = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, clasificaciones
        );

        adapterClasificacion.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spFiltroClasificacion.setAdapter(
                adapterClasificacion
        );

        List<String> anios = new ArrayList<>();

        anios.add("Todos los años");

        for (int i = 2026; i >= 1900; i--) {
            anios.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapterAnio =
                new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_item, anios
                );

        adapterAnio.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spFiltroAnio.setAdapter(adapterAnio);

        spFiltroGenero.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        aplicarFiltros();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        spFiltroAnio.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent, View view, int position, long id) {
                        aplicarFiltros();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        spFiltroClasificacion.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        aplicarFiltros();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );
    }

    private void cargarPeliculas() {

        listaPeliculas = dbHelper.obtenerTodasLasPeliculas();
        aplicarFiltros();
    }

    private void aplicarFiltros() {

        if (listaPeliculas == null) {
            return;
        }

        listaFiltrada = new ArrayList<>(listaPeliculas);

        String generoSeleccionado = spFiltroGenero.getSelectedItem().toString();
        String anioSeleccionado = spFiltroAnio.getSelectedItem().toString();
        String clasificacionSeleccionada = spFiltroClasificacion.getSelectedItem().toString();

        if (!generoSeleccionado.equals("Todos los géneros")) {

            List<Pelicula> resultado = new ArrayList<>();

            for (Pelicula pelicula : listaFiltrada) {

                if (pelicula.getGenero().equals(generoSeleccionado)) {
                    resultado.add(pelicula);
                }
            }

            listaFiltrada = resultado;
        }

        if (!anioSeleccionado.equals("Todos los años")) {

            int anio = Integer.parseInt(anioSeleccionado);

            List<Pelicula> resultado = new ArrayList<>();

            for (Pelicula pelicula : listaFiltrada) {

                if (pelicula.getAnio() == anio) {
                    resultado.add(pelicula);
                }
            }
            listaFiltrada = resultado;
        }

        if (!clasificacionSeleccionada.equals("Todas las clasificaciones")) {

            int clasificacion = Integer.parseInt(
                    clasificacionSeleccionada
            );

            List<Pelicula> resultado = new ArrayList<>();

            for (Pelicula pelicula : listaFiltrada) {

                if (pelicula.getCalificacion() == clasificacion) {
                    resultado.add(pelicula);
                }
            }

            listaFiltrada = resultado;
        }

        Collections.sort(listaFiltrada, new Comparator<Pelicula>() {
            @Override
            public int compare(Pelicula p1, Pelicula p2) {
                return p2.getAnio() - p1.getAnio();
            }
        });

        adapter = new PeliculaAdapter(listaFiltrada, pelicula -> {
            Intent intent = new Intent(
                    VerMasPeliculasActivity.this,
                    DetallePeliculaActivity.class
            );
            intent.putExtra("id_pelicula", pelicula.getId());
            startActivity(intent);
        });

        rvTodasLasPeliculas.setAdapter(adapter);
    }

    private void abrirFormulario() {

        FormularioPeliculaFragment formulario = new FormularioPeliculaFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorFormulario, formulario)
                .addToBackStack(null)
                .commit();

        rvTodasLasPeliculas.setVisibility(View.GONE);
        findViewById(R.id.contenedorFormulario).setVisibility(View.VISIBLE);
    }
}