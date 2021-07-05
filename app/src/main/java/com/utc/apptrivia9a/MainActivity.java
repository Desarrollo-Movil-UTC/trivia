package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/*
@autores:Sandoval,Sanchez,Robayo
@creación/ 30/06/2021
@fModificación 03/07/2021
@descripción: App para simular una trivia con preguntas asociadas a la IA
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //actividad para pregunta 1
    public void registrarJugador(View Vista){
        //creando un objeto para ponder manejar la ventana de registo
        Intent ventanaRegistroJugador=new Intent(getApplicationContext(),RegistrarJugadorActivity.class);
        startActivity(ventanaRegistroJugador);
    }

    public void salir (View vista){
        finish(); //lamar al metodo para cerrar la app
    }

    //MENU DE OPCIONES ****************
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow,menu);
        return true;
    }

     //si da clic a alguna opcion ******
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.gestionPreguntas){
            //proceso de cada boton
            Toast.makeText(this, "Gestión de Preguntas" ,Toast.LENGTH_LONG).show();

            //abriendo la ventana de gestion de cursos
            Intent ventanaPreguntas=new Intent(getApplicationContext(),GestionPreguntasActivity.class);
            startActivity(ventanaPreguntas); //solicitamos que habra el menu
            finish(); //cerrando la activity

        }else if(id == R.id.gestionUsuarios){

            Toast.makeText(this, "Gestión de Jugadores" ,Toast.LENGTH_LONG).show();
            Intent ventanaEstudiantes=new Intent(getApplicationContext(),GestionJugadoresActivity.class); //construyendo un objeto de tipo ventana para poder abrir la ventana de login
            startActivity(ventanaEstudiantes); //solicitamos que habra el formulario de login
            finish(); //cerrando la activity

        }
        return super.onOptionsItemSelected(item);
    }


}