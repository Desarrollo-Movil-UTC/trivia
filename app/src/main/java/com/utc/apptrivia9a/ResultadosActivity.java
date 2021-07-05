package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResultadosActivity extends AppCompatActivity {
    //definicion de variables
    String id,nombre,apellido;// variables para capturar valores que vienen cmo parametro
    int puntajeFinal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Bundle parametrosExtra=getIntent().getExtras(); //capturando los parametros que vienen de la activiad anterior
        if(parametrosExtra!=null){
            try{
                //intente realizar estas lineas de codigo:
                id = parametrosExtra.getString("id");
                nombre = parametrosExtra.getString("nombre");
                apellido = parametrosExtra.getString("apellido");
                puntajeFinal=parametrosExtra.getInt("puntuacion"); //esta es de tipo Int
            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),"Error "+ex.toString(),Toast.LENGTH_LONG).show();
            }
        }
        Toast.makeText(getApplicationContext(),"PUNTAJE OBTENIDO: "+id+nombre+apellido+puntajeFinal,Toast.LENGTH_LONG).show();

    }


}