package com.example.mispeliculas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetallePeliculaActivity extends AppCompatActivity {

    private ImageView imgDetallePelicula;
    private TextView tvDetalleTitulo;
    private TextView tvDetalleAnio;
    private TextView tvDetalleGenero;
    private TextView tvDetalleCalificacion;
    private TextView tvDetalleDescripcion;

    private Button btnEditar;
    private Button btnEliminar;

    private DatabaseHelper dbHelper;
    private Pelicula pelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);

        imgDetallePelicula = findViewById(R.id.imgDetallePelicula);
        tvDetalleTitulo = findViewById(R.id.tvDetalleTitulo);
        tvDetalleAnio = findViewById(R.id.tvDetalleAnio);
        tvDetalleGenero = findViewById(R.id.tvDetalleGenero);
        tvDetalleCalificacion = findViewById(R.id.tvDetalleCalificacion);
        tvDetalleDescripcion = findViewById(R.id.tvDetalleDescripcion);

        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        dbHelper = new DatabaseHelper(this);

        int idPelicula = getIntent().getIntExtra("id_pelicula", -1);

        if (idPelicula != -1) {
            pelicula = dbHelper.obtenerPeliculaPorId(idPelicula);

            if (pelicula != null) {
                mostrarDatosPelicula();
            }
        }

        btnEditar.setOnClickListener(v -> abrirFormularioEditar());
        btnEliminar.setOnClickListener(v -> mostrarDialogoEliminar());
    }

    private void mostrarDatosPelicula() {

        tvDetalleTitulo.setText(pelicula.getTitulo());
        tvDetalleAnio.setText("Año: " + pelicula.getAnio());
        tvDetalleGenero.setText("Género: " + pelicula.getGenero());
        tvDetalleCalificacion.setText("Calificación: " + pelicula.getCalificacion() + "/5");
        tvDetalleDescripcion.setText(pelicula.getDescripcion());

        int idImagen = getResources().getIdentifier(
                pelicula.getImagen(),
                "drawable",
                getPackageName()
        );

        imgDetallePelicula.setImageResource(idImagen);
    }

    private void abrirFormularioEditar() {

        FormularioPeliculaFragment formulario = new FormularioPeliculaFragment();

        Bundle argumentos = new Bundle();

        argumentos.putInt("id_pelicula", pelicula.getId());

        formulario.setArguments(argumentos);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, formulario)
                .addToBackStack(null)
                .commit();
    }

    private void mostrarDialogoEliminar() {

        new AlertDialog.Builder(this)
                .setTitle("Eliminar película")
                .setMessage("¿Estás segur@ que quieres borrar esta película?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar", (dialog, which) -> {

                            int resultado = dbHelper.eliminarPelicula(pelicula);

                            if (resultado > 0) {
                                Toast.makeText(this, "Película eliminada exitosamente", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "No se pudo eliminar la película", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .show();
    }
}