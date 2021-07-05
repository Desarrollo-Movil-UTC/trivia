package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

/*
@autores:Sandoval,sanchez,Robayo
@creación/ 05/07/2021
@fModificación 05/17/2021
@descripción: gestion de preguntas
*/
public class GestionPreguntasActivity extends AppCompatActivity {
    //entrada
    EditText txtPreguntaRegistro;
    RadioButton respuestaVerdadero, respuestaFalso;
    BaseDatos miBdd;// creando un objeto para acceder a los procesos de la BDD SQlite

    //Salida
    ListView listPreguntas;
    ArrayList<String> listaPreguntas = new ArrayList<>(); //cargar los datos de la BDD

    //declaracion global
    Cursor preguntasObtenidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_preguntas);
        //mapeo de elementos
        txtPreguntaRegistro= (EditText)findViewById(R.id.txtPreguntaRegistro);
        respuestaVerdadero = (RadioButton)findViewById(R.id.radioButtonVerdadero);
        respuestaFalso = (RadioButton)findViewById(R.id.radioButtonFalso);
        listPreguntas = (ListView) findViewById(R.id.listPreguntas);
        //instanciar /construir la base de datos en el objeto mi bdd
        miBdd = new BaseDatos(getApplicationContext());

        consultarPreguntas(); //invoca al metodo de listar las preguntas que ya esten registradas

        //generar acciones cuando se da click sore un elemento de la lista de cursos
        listPreguntas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //programacion de acciones cuando se da click en un elemento/item de la lista de clientes

                preguntasObtenidas.moveToPosition(position); // moviendo el cursor hacia la posicion donde se dio click

                //mediante el cursor se obtiene los datos de la pregunta seleccionado en la lista mediante las posiciones en el cursor
                String idPregunta = preguntasObtenidas.getString(0);
                String pregunta = preguntasObtenidas.getString(1);
                String respuesta = preguntasObtenidas.getString(2);

                //manejando el objeto para abrir la ventana de editarPregunta
                Intent ventanaEditarEliminarPregunta = new Intent(getApplicationContext(), Editar_EliminarPreguntaActivity.class);
                ventanaEditarEliminarPregunta.putExtra("idPregunta", idPregunta); //pasando el id del cliente como parametro
                ventanaEditarEliminarPregunta.putExtra("pregunta", pregunta);
                ventanaEditarEliminarPregunta.putExtra("respuesta", respuesta);

                startActivity(ventanaEditarEliminarPregunta); //solicitando que se habra la ventana para editar preguntas
                //finish(); //cerrando la ventana de registro
            }
        });
    }

    public void registrarPregunta(View vista){
        //capturando los valores ingresados por el usuario en variables Java de tipo String
        String pregunta=txtPreguntaRegistro.getText().toString();
        String respuesta;
        //validaciones
        if (pregunta.isEmpty() ){ //si algun campo esta vacio
            Toast.makeText(getApplicationContext(), "Para continuar con el registro llene todos los campos solicitados",
                    Toast.LENGTH_LONG).show(); //mostrando mensaje de campo vacio a traves de un toast
        } else {

            if(respuestaVerdadero.isChecked()){
                respuesta = "Verdadero";
                miBdd.agregarPregunta(pregunta, respuesta);
                Toast.makeText(getApplicationContext(), "Pregunta registrada correctamente",
                        Toast.LENGTH_LONG).show(); //mostrando mensaje de usuario registrado
                consultarPreguntas();
                LimpiarRegistroPregunta(null); //limpiar el registro despues de guardarlo
            }else if (respuestaFalso.isChecked()){
                respuesta = "Falso";
                miBdd.agregarPregunta(pregunta, respuesta);
                Toast.makeText(getApplicationContext(), "Pregunta registrada correctamente",
                        Toast.LENGTH_LONG).show(); //mostrando mensaje de usuario registrado
                consultarPreguntas();
                LimpiarRegistroPregunta(null); //limpiar el registro despues de guardarlo
            }else{
                Toast.makeText(getApplicationContext(), "Selecciona la respuesta",
                        Toast.LENGTH_LONG).show(); //mostrando que seleccione un aopcion
            }
        }
    }

    //Boton Cancelar
    public void LimpiarRegistroPregunta(View vista) {
        txtPreguntaRegistro.setText("");
        respuestaVerdadero.setChecked(false); //desmarcar el checkbox
        respuestaFalso.setChecked(false); //desmarcar el checkbox
        txtPreguntaRegistro.requestFocus();
    }

    //Boton Salir
    public void salirRegistroPregunta(View vista) { //metodo para cerrar
        Intent ventanaIniciar = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(ventanaIniciar);
        finish(); //cerrando la activity
    }

    public void consultarPreguntas() {
        listaPreguntas.clear(); //vaciando el listado del ArrayList
        preguntasObtenidas = miBdd.obtenerPreguntas(); //consultando cursos y guardandolos en un cursor

        if (preguntasObtenidas != null) { //verificando que realmente haya datos dentro de SQLite
            //proceso cuando si se encuentren cursos almacenados en la BDD
            do {
                String id = preguntasObtenidas.getString(0).toString();
                String pregunta = preguntasObtenidas.getString(1).toString();
                String respuesta = preguntasObtenidas.getString(2).toString();

                //construyendo las filas para presentar datos en el ListView
                listaPreguntas.add(id + ": "+pregunta+" respuesta: "+respuesta );

                //crear un adaptador para presentar los datos del listado de cursos (Java) en una lista simple (XML)
                ArrayAdapter<String> adaptadorPreguntas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPreguntas);
                listPreguntas.setAdapter(adaptadorPreguntas); //presentando el adaptador de cursos dentro del listview

            } while (preguntasObtenidas.moveToNext()); //validando si aun existen cursos dentro del cursor
        } else {
            //presentando un mensaje de informacion cuando no existan cursos
            Toast.makeText(getApplicationContext(), "No existen preguntas", Toast.LENGTH_LONG).show();
        }
    }

}