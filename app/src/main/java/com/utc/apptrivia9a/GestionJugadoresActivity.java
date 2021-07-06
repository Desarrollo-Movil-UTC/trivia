package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_jugadores);
        //mapeo de elementos
        listJugadores = (ListView) findViewById(R.id.listJugadores);
        //instanciar /construir la base de datos en el objeto mi bdd
        miBdd = new BaseDatos(getApplicationContext());
        consultarJugadores(); //invoca al metodo de listar las preguntas que ya esten registradas
        listJugadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eliminarJugador(null);
            }
        });
    }

    public void consultarJugadores() {
        listaJugadores.clear(); //vaciando el listado del ArrayList
        jugadoresObtenidos = miBdd.obtenerJugadores(); //consultando cursos y guardandolos en un cursor

        if (jugadoresObtenidos != null) { //verificando que realmente haya datos dentro de SQLite
            //proceso cuando si se encuentren cursos almacenados en la BDD
            do {
                id = jugadoresObtenidos.getString(0).toString();
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
    public void eliminarJugador(View vista){
        AlertDialog.Builder estructuraConfirmacion = new AlertDialog.Builder(this)
                .setTitle("CONFIRMACIÓN")
                .setMessage("¿Está seguro de eliminar el jugador seleccionado?")
                .setPositiveButton("Si, Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //id viene de las variables declaradas que contiene el putExtra
                        miBdd.eliminarJugador(id); //procesa la eliminacion con base al id del productp
                        Toast.makeText(getApplicationContext(), "Eliminacion existosa",Toast.LENGTH_SHORT).show();
                        consultarJugadores();
                    }
                })
                .setNegativeButton("No, cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Eliminacion cancelada",Toast.LENGTH_SHORT).show();

                    }
                }).setCancelable(true); //es posible que se pueda cancelar el proceso
        //construir el cuadro de dialogo
        AlertDialog cuadroDialogo = estructuraConfirmacion.create();
        cuadroDialogo.show(); //se presenta en pantalla
    }

}