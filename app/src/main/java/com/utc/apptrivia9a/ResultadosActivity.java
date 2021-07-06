package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultadosActivity extends AppCompatActivity {
    //definicion de variables
    String id,nombre,apellido;// variables para capturar valores que vienen cmo parametro
    int puntajeFinal=0;


    //elementos
    TextView txtMensajeResultados, txtNombreJugadorResultados, txtPuntajeFinalResultados, txtCorrectosResultados, txtIncorrectosResultados;
    ImageView imagenResultados;

    //para resultados correctos e incorrectos
    int correctas=0, incorrectas=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        //mapeo de elementos
        txtMensajeResultados = (TextView) findViewById(R.id.txtMensajeResultados);
        txtNombreJugadorResultados = (TextView) findViewById(R.id.txtNombreJugadorResultados);
        txtPuntajeFinalResultados = (TextView) findViewById(R.id.txtPuntajeFinalResultados);
        txtCorrectosResultados = (TextView) findViewById(R.id.txtCorrectosResultados);
        txtIncorrectosResultados = (TextView) findViewById(R.id.txtIncorrectosResultados);
        imagenResultados = (ImageView) findViewById(R.id.imagenResultados);

        Bundle parametrosExtra=getIntent().getExtras(); //capturando los parametros que vienen de la activiad anterior
        if(parametrosExtra!=null){
            try{
                //intente realizar estas lineas de codigo:
                id = parametrosExtra.getString("id");
                nombre = parametrosExtra.getString("nombre");
                apellido = parametrosExtra.getString("apellido");
                puntajeFinal=parametrosExtra.getInt("puntuacion"); //esta es de tipo Int

                correctas = parametrosExtra.getInt("resp_correctas");
                incorrectas = parametrosExtra.getInt("resp_incorrectas");

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),"Error "+ex.toString(),Toast.LENGTH_LONG).show();
            }
        }
        //Toast.makeText(getApplicationContext(),"PUNTAJE OBTENIDO: "+id+nombre+apellido+puntajeFinal,Toast.LENGTH_LONG).show();

        String nombreCompleto = nombre+" "+apellido;
        if (correctas >= 9 && correctas <= 10){
            txtMensajeResultados.setText("LO HICISTE EXCELENTE");
            imagenResultados.setImageResource(R.drawable.excelente);
            txtNombreJugadorResultados.setText(nombreCompleto);
            txtPuntajeFinalResultados.setText("Puntos: "+puntajeFinal);
            //txtPuntajeFinalResultados.setBackgroundResource(R.color.design_default_color_on_secondary);
        } else{
            if (correctas >= 7 && correctas <= 8){ //bueno
                txtMensajeResultados.setText("LO HICISTE BIEN");
                imagenResultados.setImageResource(R.drawable.muy_bien);
                txtNombreJugadorResultados.setText(nombreCompleto);
                txtPuntajeFinalResultados.setText("Puntos: "+puntajeFinal);
            } else{
                if (correctas >= 5 && correctas <= 6){ //mala
                    txtMensajeResultados.setText("LO HICISTE MAL");
                    imagenResultados.setImageResource(R.drawable.mal);
                    txtNombreJugadorResultados.setText(nombreCompleto);
                    txtPuntajeFinalResultados.setText("Puntos: "+puntajeFinal);
                } else{
                    if (correctas <5 && correctas >=0){ //menor a 5
                        txtMensajeResultados.setText("FALLASTE");
                        imagenResultados.setImageResource(R.drawable.menos5);
                        txtNombreJugadorResultados.setText(nombreCompleto);
                        txtPuntajeFinalResultados.setText("Puntos: "+puntajeFinal);
                    }
                }
            }
        }
        txtCorrectosResultados.setText(""+correctas);
        txtIncorrectosResultados.setText(""+incorrectas);
    }

    //Boton Salir
    public void salirVentanaResultados(View vista) { //metodo para cerrar
        Intent ventanaIniciar = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(ventanaIniciar);
        finish(); //cerrando la activity
    }
}