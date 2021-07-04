package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ResultadosActivity extends AppCompatActivity {
    int puntajeFinal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Bundle parametrosExtra=getIntent().getExtras(); //capturando los parametros que vienen de la activiad anterior
        if(parametrosExtra!=null){
            try{
                puntajeFinal=parametrosExtra.getInt("puntuacion");
            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),"Error "+ex.toString(),Toast.LENGTH_LONG).show();
            }
        }
        Toast.makeText(getApplicationContext(),"PUNTAJE OBTENIDO: "+puntajeFinal,Toast.LENGTH_LONG).show();

    }


}