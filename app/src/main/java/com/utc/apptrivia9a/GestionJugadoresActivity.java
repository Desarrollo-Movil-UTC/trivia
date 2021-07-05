package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/*
@autores:Sandoval,sanchez,Robayo
@creación/ 05/07/2021
@fModificación 05/17/2021
@descripción: gestion de jugadores
*/
public class GestionJugadoresActivity extends AppCompatActivity {
    BaseDatos miBdd;// creando un objeto para acceder a los procesos de la BDD SQlite
    //Salida
    ListView listJugadores;
    ArrayList<String> listaJugadores = new ArrayList<>(); //cargar los datos de la BDD
    //declaracion global
    Cursor jugadoresObtenidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_jugadores);
        //mapeo de elementos
        listJugadores = (ListView) findViewById(R.id.listJugadores);
        //instanciar /construir la base de datos en el objeto mi bdd
        miBdd = new BaseDatos(getApplicationContext());
        consultarJugadores(); //invoca al metodo de listar las preguntas que ya esten registradas
    }

    public void consultarJugadores() {
        listaJugadores.clear(); //vaciando el listado del ArrayList
        jugadoresObtenidos = miBdd.obtenerJugadores(); //consultando cursos y guardandolos en un cursor

        if (jugadoresObtenidos != null) { //verificando que realmente haya datos dentro de SQLite
            //proceso cuando si se encuentren cursos almacenados en la BDD
            do {
                String id = jugadoresObtenidos.getString(0).toString();
                String nombre = jugadoresObtenidos.getString(1).toString();
                String apellido = jugadoresObtenidos.getString(2).toString();
                String puntuacion = jugadoresObtenidos.getString(3).toString();
                //construyendo las filas para presentar datos en el ListView
                listaJugadores.add(id + ": " +nombre+" "+apellido+" Puntuacion: "+puntuacion );

                //crear un adaptador para presentar los datos del listado de cursos (Java) en una lista simple (XML)
                ArrayAdapter<String> adaptadorJugadores = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaJugadores);
                listJugadores.setAdapter(adaptadorJugadores); //presentando el adaptador de cursos dentro del listview

            } while (jugadoresObtenidos.moveToNext()); //validando si aun existen jugadores dentro del cursor
        } else {
            //presentando un mensaje de informacion cuando no existan jugadores
            Toast.makeText(getApplicationContext(), "No existen jugadores", Toast.LENGTH_LONG).show();
        }
    }

    //Boton Salir
    public void salirVentana(View vista) { //metodo para cerrar
        Intent ventanaIniciar = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(ventanaIniciar);
        finish(); //cerrando la activity
    }

}