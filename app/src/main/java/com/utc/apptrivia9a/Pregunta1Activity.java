package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

/*
@autores:Sandoval
@creación/ 30/06/2021
@fModificación 05/06/2021
@descripción: Preguntas de la trivia cpn opciones v o F
*/
public class Pregunta1Activity extends AppCompatActivity {
    String id,nombre,apellido,puntuacion; ////definicion de variables para capturar parametros que vienen de la actividad
    TextView txtCronometroPregunta1, txtNumeroPregunta, txtPregunta; //definicion de objetos xml
    String pregunta,respuesta; // variables para capturar valores que vienen de la BDD
    //variables del Cronometro
    CountDownTimer cronometro;
    int tiempoTotal = 15000; //tiempo total en segundos es de 15seg
    int intervalo = 1000; //tiempo del intervalo
    //declaracion global
    Cursor preguntasObtenidas;
    BaseDatos miBdd;// creando un objeto para acceder a los procesos de la BDD SQlite
    Integer numeroPregunta= 0;
    int puntuacionAcumulada = 0; //para ir sumando respuestas

    //para respuestas correctas e incorrectas
    int correctas=0, incorrectas=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta1);
        //mapeo de elemntos XML a objetos Java
        txtNumeroPregunta=(TextView) findViewById(R.id.txtNumeroPregunta);
        txtPregunta=(TextView) findViewById(R.id.txtPregunta);
        txtCronometroPregunta1=(TextView)findViewById(R.id.txtCronometroPregunta1);
        //instanciar /construir la base de datos en el objeto mi bdd
        miBdd = new BaseDatos(getApplicationContext());
        //captura los parametros que se han pasado a esta actividad**************
        Bundle parametrosExtra=getIntent().getExtras();
        if (parametrosExtra != null){
            try {  // manejo de exepciones
                //intente realizar estas lineas de codigo:
                id = parametrosExtra.getString("id");
                nombre = parametrosExtra.getString("nombre");
                apellido = parametrosExtra.getString("apellido");

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),"Error al procesar la solicitud" +ex.toString(),Toast.LENGTH_LONG).show();
            }

        }
        //*************************************************************************
        consultarPreguntas(); //invoca al metodo de listar las preguntas que ya esten registradas
        mostrarpregunta(0);
        cronometroBajada();
    }

    public void consultarPreguntas() {
        preguntasObtenidas = miBdd.obtenerPreguntas(); //consultando cursos y guardandolos en un cursor
        if (preguntasObtenidas != null) { //verificando que realmente haya datos dentro de SQLite
            return;
        } else {
            //presentando un mensaje de informacion cuando no existan cursos
            Toast.makeText(getApplicationContext(), "No existen preguntas", Toast.LENGTH_LONG).show();
        }
    }

    public void mostrarpregunta(Integer posision){
        preguntasObtenidas.moveToPosition(posision);
        pregunta = preguntasObtenidas.getString(1).toString();
        respuesta = preguntasObtenidas.getString(2).toString();
        //presentar los datos recibidos de curso en pantalla
        txtNumeroPregunta.setText(String.valueOf(posision+1));
        txtPregunta.setText(pregunta);
    }

    public void cronometroBajada(){
        //instanciar cronometro
        cronometro = new CountDownTimer(tiempoTotal,intervalo) {
            @Override
            public void onTick(long millisUntilFinished) { //se ejecuta cada ves q pase el intervalo
                //presentando en pantalla el tiempo del cronometro
                txtCronometroPregunta1.setText(String.valueOf(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {//se llama cuando el tiempo descrito se termina
                //si o hay respuesta por parte del usuario y se termina el tiempo
                numeroPregunta=numeroPregunta+1; //incremento el contador de preguntas
                //valido si el numero de la pregunta es mayor a 10 o si es igual al numero maximo de preguntas registradas
                if(numeroPregunta < preguntasObtenidas.getCount() && numeroPregunta < 10){ //el getcount me trae el numero de registro del cursor pero hay que tomar en cuentra que la la untima posisin es menos 1
                    mostrarpregunta(numeroPregunta); //mando a llamar al metodo para mostrar la siguiente pregunta
                    cronometroBajada(); //vuelvo a llamar al cronometro asi se reiniciara este
                }else{
                    mostrarResultados(); //se llama a este metodo si ya se supero las 10 preguntas o el numero maximo de preguntas registradas
                }

            }
        };

        cronometro.start(); //iniciando al cronometro
    }

    public void respondioVerdadero(View vista){
        if (respuesta.equals("Verdadero")){
            puntuacionAcumulada = puntuacionAcumulada+1;
            //Toast.makeText(getApplicationContext(), "Bien echo", Toast.LENGTH_LONG).show();
            correctas = correctas+1;
        }else{
            //Toast.makeText(getApplicationContext(), "mal", Toast.LENGTH_LONG).show();
            incorrectas = incorrectas+1;
        }
        cronometro.cancel();//detener el cronometro.
        numeroPregunta=numeroPregunta+1;
        if(numeroPregunta < preguntasObtenidas.getCount() && numeroPregunta < 10){ //el getcount me trae el numero de registro del cursor pero hay que tomar en cuentra que la la untima posisin es menos 1
            mostrarpregunta(numeroPregunta);
            cronometroBajada();
        }else{
            mostrarResultados();
        }
    }

    public void respondioFalso(View vista){
        if (respuesta.equals("Falso")){
            puntuacionAcumulada = puntuacionAcumulada+1;
            //Toast.makeText(getApplicationContext(), "Bien echo", Toast.LENGTH_LONG).show();
            correctas = correctas+1;
        }else{
            //Toast.makeText(getApplicationContext(), "mal", Toast.LENGTH_LONG).show();
            incorrectas = incorrectas+1;
        }
        cronometro.cancel();//detener el cronometro.
        numeroPregunta=numeroPregunta+1;
        if(numeroPregunta < preguntasObtenidas.getCount() && numeroPregunta < 10){ //el getcount me trae el numero de registro del cursor pero hay que tomar en cuentra que la la untima posisin es menos 1
            mostrarpregunta(numeroPregunta);
            cronometroBajada();
        }else{
            mostrarResultados();
        }
    }

    public void mostrarResultados(){
        Intent ventanaResultados = new Intent(getApplicationContext(),ResultadosActivity.class);
        //enviar parametros al los resultados
        ventanaResultados.putExtra("id", id);
        ventanaResultados.putExtra("nombre", nombre);
        ventanaResultados.putExtra("apellido", apellido);
        ventanaResultados.putExtra("puntuacion",puntuacionAcumulada);

        ventanaResultados.putExtra("resp_correctas",correctas);
        ventanaResultados.putExtra("resp_incorrectas",incorrectas);

        actualizarpuntuacion();
        startActivity(ventanaResultados); //abrimos la resultados
        finish();
    }

    public  void actualizarpuntuacion(){
        miBdd.actualizarUsuario(nombre,apellido,puntuacionAcumulada,id); //modificando en la tabla cliente respecto al id
        Toast.makeText(getApplicationContext(),"Puntuación guardada",Toast.LENGTH_SHORT).show(); //presentando un mensaje de confirmacion
    }


}