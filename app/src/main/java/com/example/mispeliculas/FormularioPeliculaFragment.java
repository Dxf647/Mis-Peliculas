package com.example.mispeliculas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FormularioPeliculaFragment extends Fragment {

    private EditText etTitulo;
    private Spinner spGenero;
    private EditText etAnio;
    private EditText etDescripcion;
    private Spinner spCalificacion;
    private Spinner spImagen;
    private Button btnGuardar;
    private Button btnCancelar;

    private DatabaseHelper dbHelper;

    private Pelicula peliculaEditar;
    private boolean modoEditar = false;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(
                R.layout.fragment_formulario_pelicula,
                container,
                false
        );

        etTitulo = vista.findViewById(R.id.etTitulo);
        spGenero = vista.findViewById(R.id.spGenero);
        etAnio = vista.findViewById(R.id.etAnio);
        etDescripcion = vista.findViewById(R.id.etDescripcion);
        spCalificacion = vista.findViewById(R.id.spCalificacion);
        spImagen = vista.findViewById(R.id.spImagen);
        btnGuardar = vista.findViewById(R.id.btnGuardar);
        btnCancelar = vista.findViewById(R.id.btnCancelar);

        dbHelper = new DatabaseHelper(requireContext());

        configurarSpinnerGenero();
        configurarSpinnerCalificacion();
        configurarSpinnerImagen();

        Bundle argumentos = getArguments();

        if (argumentos != null) {
            int idPelicula = argumentos.getInt("id_pelicula", -1);

            if (idPelicula != -1) {
                modoEditar = true;
                peliculaEditar = dbHelper.obtenerPeliculaPorId(idPelicula);

                if (peliculaEditar != null) {
                    cargarDatosPelicula();
                }
            }
        }
        btnGuardar.setOnClickListener(v -> guardarPelicula());
        btnCancelar.setOnClickListener(v -> { requireActivity().getSupportFragmentManager().popBackStack();});
        return vista;
    }

    private void configurarSpinnerGenero() {

        ArrayAdapter<CharSequence> adaptadorGenero = ArrayAdapter.createFromResource(
                requireContext(), R.array.generos, android.R.layout.simple_spinner_item);

        adaptadorGenero.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        
        spGenero.setAdapter(adaptadorGenero);
    }

    private void configurarSpinnerCalificacion() {

        ArrayAdapter<CharSequence> adaptadorCalificacion =
                ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.calificaciones,
                        android.R.layout.simple_spinner_item
                );

        adaptadorCalificacion.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCalificacion.setAdapter(adaptadorCalificacion);
    }

    private void configurarSpinnerImagen() {

        List<String> imagenes = new ArrayList<>();

        imagenes.add("a_2_metros_de_ti");
        imagenes.add("a_500_dias_con_ella");
        imagenes.add("asi_en_la_tierra_como_en_el_infierno");
        imagenes.add("backrooms");
        imagenes.add("barbaro");
        imagenes.add("blue_valentine");
        imagenes.add("chainsaw_man");
        imagenes.add("chiikawa");
        imagenes.add("coco");
        imagenes.add("coyote_vs_acme");
        imagenes.add("deadpool_2");
        imagenes.add("dejame_salir");
        imagenes.add("destino_final_lazos_de_sangre");
        imagenes.add("donde_estan_las_rubias");
        imagenes.add("edge_of_tomorrow");
        imagenes.add("el_conjuro");
        imagenes.add("el_diablo_viste_a_la_moda");
        imagenes.add("el_motin");
        imagenes.add("el_origen_de_los_guardianes");
        imagenes.add("el_viaje_de_chihiro");
        imagenes.add("ella");
        imagenes.add("explicacion");
        imagenes.add("gladiador");
        imagenes.add("hatsune_miku_no_puede_cantar");
        imagenes.add("immortal_combat");
        imagenes.add("intensamente");
        imagenes.add("john_wick");
        imagenes.add("la_burbuja_de_la_ia");
        imagenes.add("la_casa_de_cera");
        imagenes.add("la_huerfana");
        imagenes.add("la_odisea");
        imagenes.add("la_peor_persona_del_mundo");
        imagenes.add("la_senial_del_apocalipsis");
        imagenes.add("la_sustancia");
        imagenes.add("la_tumba_de_las_luciernagas");
        imagenes.add("la_ultima_casa");
        imagenes.add("lalaland");
        imagenes.add("los_juegos_del_ambre");
        imagenes.add("los_paraguas_de_cherburgo");
        imagenes.add("mama");
        imagenes.add("maquina_de_guerra");
        imagenes.add("mi_hijo_es_un_therian");
        imagenes.add("mi_pobre_angelito");
        imagenes.add("minions_y_monsters");
        imagenes.add("obsesion");
        imagenes.add("oculus");
        imagenes.add("parasitos");
        imagenes.add("predator_2");
        imagenes.add("que_paso_ayer");
        imagenes.add("quiero_comer_tu_pancreas");
        imagenes.add("scary_movie");
        imagenes.add("sixseven");
        imagenes.add("spider_man");
        imagenes.add("spiderman_brand_new_day");
        imagenes.add("ted_2");
        imagenes.add("the_mandalorian_and_grogu");
        imagenes.add("the_maze_runner");
        imagenes.add("the_thing");
        imagenes.add("your_name");
        imagenes.add("zona_cero");

        ArrayAdapter<String> adaptadorImagenes =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        imagenes
                );

        adaptadorImagenes.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spImagen.setAdapter(adaptadorImagenes);
    }

    private void cargarDatosPelicula() {

        etTitulo.setText(peliculaEditar.getTitulo());
        etAnio.setText(String.valueOf(peliculaEditar.getAnio()));
        etDescripcion.setText(peliculaEditar.getDescripcion());
        seleccionarSpinner(spGenero, peliculaEditar.getGenero());
        seleccionarSpinner(spCalificacion, String.valueOf(peliculaEditar.getCalificacion()));
        seleccionarSpinner(spImagen, peliculaEditar.getImagen());
        btnGuardar.setText("Guardar cambios");
    }

    private void seleccionarSpinner(Spinner spinner, String valor) {

        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();

        int posicion = adapter.getPosition(valor);

        if (posicion >= 0) {
            spinner.setSelection(posicion);
        }
    }

    private void guardarPelicula() {

        String titulo = etTitulo.getText().toString().trim();
        String anioTexto = etAnio.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        if (titulo.isEmpty()) {
            etTitulo.setError("Ingresa el título");
            etTitulo.requestFocus();
            return;
        }

        if (anioTexto.isEmpty()) {
            etAnio.setError("Ingresa el año");
            etAnio.requestFocus();
            return;
        }

        if (anioTexto.length() != 4) {
            etAnio.setError("El año debe tener 4 dígitos");
            etAnio.requestFocus();
            return;
        }

        int anio;

        try {
            anio = Integer.parseInt(anioTexto);
        } catch (NumberFormatException e) {
            etAnio.setError("Ingresa un año válido");
            etAnio.requestFocus();
            return;
        }

        String genero = spGenero.getSelectedItem().toString();

        int calificacion = Integer.parseInt(spCalificacion.getSelectedItem().toString());

        String imagen = spImagen.getSelectedItem().toString();

        if (modoEditar && peliculaEditar != null) {
            Pelicula peliculaActualizada = new Pelicula(
                    peliculaEditar.getId(),
                    titulo,
                    genero,
                    anio,
                    descripcion,
                    calificacion,
                    imagen,
                    peliculaEditar.getFechaAgregado()
            );

            int resultado = dbHelper.actualizarPelicula(peliculaActualizada);

            if (resultado > 0) {
                Toast.makeText(requireContext(), "Película actualizada correctamente", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(requireContext(), "No se pudo actualizar la película", Toast.LENGTH_SHORT).show();
            }

        } else {
            Pelicula nuevaPelicula = new Pelicula(
                    titulo,
                    genero,
                    anio,
                    descripcion,
                    calificacion,
                    imagen
            );

            long resultado = dbHelper.insertarPelicula(nuevaPelicula);

            if (resultado != -1) {
                Toast.makeText(requireContext(), "Película agregada correctamente", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(requireContext(), "No se pudo guardar la película", Toast.LENGTH_SHORT).show();
            }
        }
    }
}