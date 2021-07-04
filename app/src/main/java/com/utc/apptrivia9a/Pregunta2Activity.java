package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Pregunta2Activity extends AppCompatActivity {
    //definicion de objetos
    TextView txtCronometroPregunta2;
    CountDownTimer cronometro;
    int puntuacionAcumulada = 0; //para ir sumando respuestas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta2);

        //mapeo de elemntos XML a objetos Java
        txtCronometroPregunta2=(TextView)findViewById(R.id.txtCronometroPregunta2);
        int tiempoTotal = 15000; //tiempo total en segundos es de 15seg
        int intervalo = 1000; //tiempo del intervalo
        //instanciar cronometro
        cronometro = new CountDownTimer(tiempoTotal,intervalo) {
            @Override
            public void onTick(long millisUntilFinished) { //se ejecuta cada ves q pase el intervalo
                //presentando en pantalla el tiempo del cronometro
                txtCronometroPregunta2.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {//se llama cuando el tiempo descrito se termina
                //responderMal(null); //si o hay respuesta por parte del usuario se asume que respondio mal

            }
        };

        cronometro.start(); //iniciando al cronometro
        //sumar respuestas
        Bundle parametroExtra=getIntent().getExtras(); //capturando los extras que vienen
        if(parametroExtra!=null){
            try{
                puntuacionAcumulada= parametroExtra.getInt("puntuacion");

            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),"error"+ex.toString(),Toast.LENGTH_LONG).show();
            }
        }

    }

    //metodo cuando el usuario responde de forma correcta
    public void responderBien(View vista){
        finish(); //cerrando pregunta actual
        //abrir la siguiente pregunta
        Intent ventanaResultados = new Intent(getApplicationContext(),ResultadosActivity.class);
        //pasando la puntuacion como parametro ->se asigna 1 debido a que respondio bien
        ventanaResultados.putExtra("puntuación", puntuacionAcumulada+1); startActivity(ventanaResultados); //abrimos la resultados
        cronometro.cancel();//detener el cronometro.

    }

    //metodo cuando el usuario responde de forma incorrecta
    public void responderMal(View vista){
        finish(); //cerrando pregunta actual
        //abrir la siguiente pregunta
        Intent ventanaResultados = new Intent(getApplicationContext(),Pregunta2Activity.class);
        //pasando la puntuacion como parametro ->se asigna 0 debido a que respondio mal
        ventanaResultados.putExtra("puntuación", puntuacionAcumulada); startActivity(ventanaResultados); //abrimos la resultados
        cronometro.cancel();//detener el cronometro.

    }




}