package com.example.mispeliculas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mispeliculas.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PELICULAS = "peliculas";
    public static final String COL_ID = "_id";
    public static final String COL_TITULO = "titulo";
    public static final String COL_GENERO = "genero";
    public static final String COL_ANIO = "anio";
    public static final String COL_DESCRIPCION = "descripcion";
    public static final String COL_CALIFICACION = "calificacion";
    public static final String COL_IMAGEN = "imagen";
    public static final String COL_FECHA_AGREGADO = "fecha_agregado";

    private static final String SQL_CREATE_TABLE_PELICULAS =
            "CREATE TABLE " + TABLE_PELICULAS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_TITULO + " TEXT NOT NULL, " +
                    COL_GENERO + " TEXT NOT NULL, " +
                    COL_ANIO + " INTEGER NOT NULL, " +
                    COL_DESCRIPCION + " TEXT, " +
                    COL_CALIFICACION + " INTEGER NOT NULL, " +
                    COL_IMAGEN + " TEXT NOT NULL, " +
                    COL_FECHA_AGREGADO + " TEXT NOT NULL" +
                    ");";

    private static final String SQL_DROP_TABLE_PELICULAS =
            "DROP TABLE IF EXISTS " + TABLE_PELICULAS;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PELICULAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_PELICULAS);
        onCreate(db);
    }

    public long insertarPelicula(Pelicula pelicula) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_TITULO, pelicula.getTitulo());
        values.put(COL_GENERO, pelicula.getGenero());
        values.put(COL_ANIO, pelicula.getAnio());
        values.put(COL_DESCRIPCION, pelicula.getDescripcion());
        values.put(COL_CALIFICACION, pelicula.getCalificacion());
        values.put(COL_IMAGEN, pelicula.getImagen());

        String fechaAgregado = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        values.put(COL_FECHA_AGREGADO, fechaAgregado);

        return db.insert(TABLE_PELICULAS, null, values);
    }

    public List<Pelicula> obtenerTodasLasPeliculas() {
        List<Pelicula> listaPeliculas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_PELICULAS,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_ID)
            );

            String titulo = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_TITULO)
            );

            String genero = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_GENERO)
            );

            int anio = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_ANIO)
            );

            String descripcion = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_DESCRIPCION)
            );

            int calificacion = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_CALIFICACION)
            );

            String imagen = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_IMAGEN)
            );

            String fechaAgregado = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_FECHA_AGREGADO)
            );

            Pelicula pelicula = new Pelicula(
                    id,
                    titulo,
                    genero,
                    anio,
                    descripcion,
                    calificacion,
                    imagen,
                    fechaAgregado
            );
            listaPeliculas.add(pelicula);
        }
        cursor.close();
        return listaPeliculas;
    }

    public Pelicula obtenerPeliculaPorId(int id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PELICULAS,
                null,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        Pelicula pelicula = null;

        if (cursor.moveToFirst()) {

            String titulo = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_TITULO)
            );

            String genero = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_GENERO)
            );

            int anio = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_ANIO)
            );

            String descripcion = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_DESCRIPCION)
            );

            int calificacion = cursor.getInt(
                    cursor.getColumnIndexOrThrow(COL_CALIFICACION)
            );

            String imagen = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_IMAGEN)
            );

            String fechaAgregado = cursor.getString(
                    cursor.getColumnIndexOrThrow(COL_FECHA_AGREGADO)
            );

            pelicula = new Pelicula(
                    id,
                    titulo,
                    genero,
                    anio,
                    descripcion,
                    calificacion,
                    imagen,
                    fechaAgregado
            );
        }
        cursor.close();

        return pelicula;
    }

    public int actualizarPelicula(Pelicula pelicula) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITULO, pelicula.getTitulo());
        values.put(COL_GENERO, pelicula.getGenero());
        values.put(COL_ANIO, pelicula.getAnio());
        values.put(COL_DESCRIPCION, pelicula.getDescripcion());
        values.put(COL_CALIFICACION, pelicula.getCalificacion());
        values.put(COL_IMAGEN, pelicula.getImagen());

        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {
                String.valueOf(pelicula.getId())
        };
        return db.update(
                TABLE_PELICULAS,
                values,
                whereClause,
                whereArgs
        );
    }

    public int eliminarPelicula(Pelicula pelicula) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = COL_ID + " = ?";
        String[] whereArgs = {
                String.valueOf(pelicula.getId())
        };
        return db.delete(
                TABLE_PELICULAS,
                whereClause,
                whereArgs
        );
    }
}
