package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/*
@autores:Sandoval
@creación/ 30/06/2021
@fModificación 30/06/2021
@descripción: Pregunta 1 de la trivia cpn opciones v o F
*/
public class Pregunta1Activity extends AppCompatActivity {
    //definicion de objetos
    TextView txtCronometroPregunta1;
    CountDownTimer cronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta1);
        //mapeo de elemntos XML a objetos Java
        txtCronometroPregunta1=(TextView)findViewById(R.id.txtCronometroPregunta1);
        int tiempoTotal = 15000; //tiempo total en segundos es de 15seg
        int intervalo = 1000; //tiempo del intervalo
        //instanciar cronometro
        cronometro = new CountDownTimer(tiempoTotal,intervalo) {
            @Override
            public void onTick(long millisUntilFinished) { //se ejecuta cada ves q pase el intervalo
                //presentando en pantalla el tiempo del cronometro
                txtCronometroPregunta1.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {//se llama cuando el tiempo descrito se termina
                //Toast.makeText(getApplicationContext(),"tiempo terminado -> avanzar a  la siguiente pregunta"
                //        ,Toast.LENGTH_LONG).show();
                responderMal(null); //si o hay respuesta por parte del usuario se asume que respondio mal

            }
        };

        cronometro.start(); //iniciando al cronometro

    }


    //metodo cuando el usuario responde de forma correcta
    public void responderBien(View vista){
        finish(); //cerrando pregunta actual
        //abrir la siguiente pregunta
        Intent ventanapregunta2 = new Intent(getApplicationContext(),Pregunta2Activity.class);
        //pasando la puntuacion como parametro ->se asigna 1 debido a que respondio bien
        ventanapregunta2.putExtra("puntuación", 1); startActivity(ventanapregunta2); //abrimos la actividad 2
        cronometro.cancel();//detener el cronometro.

    }

    //metodo cuando el usuario responde de forma incorrecta
    public void responderMal(View vista){
        finish(); //cerrando pregunta actual
        //abrir la siguiente pregunta
        Intent ventanapregunta2 = new Intent(getApplicationContext(),Pregunta2Activity.class);
        //pasando la puntuacion como parametro ->se asigna 0 debido a que respondio mal
        ventanapregunta2.putExtra("puntuación", 0); startActivity(ventanapregunta2); //abrimos la actividad 2
        cronometro.cancel();//detener el cronometro.

    }




}