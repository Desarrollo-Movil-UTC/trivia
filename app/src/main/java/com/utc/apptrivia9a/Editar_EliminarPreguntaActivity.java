package com.utc.apptrivia9a;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/*
@autores:Sandoval,Sanchez,Robayo
@creación/ 05/07/2021
@fModificación 05/07/2021
@descripción: editar o eliminar una pregunta
*/
public class Editar_EliminarPreguntaActivity extends AppCompatActivity {
    //definicion de variables
    String id,pregunta,respuesta; // variables para capturar valores que vienen cmo parametro
    //definicion de objetos que vienen del xml
    //entrada
    TextView txtIdPreguntaEditar;
    EditText txtPreguntaEditar;
    RadioButton respuestaVerdaderoEditar, respuestaFalsoEditar;
    BaseDatos miBdd;// creando un objeto para acceder a los procesos de la BDD SQlite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_eliminar_pregunta);
        //mapeo de leementos
        txtIdPreguntaEditar=(TextView) findViewById(R.id.txtIdPreg);
        txtPreguntaEditar= (EditText)findViewById(R.id.txtPregunta);
        respuestaVerdaderoEditar = (RadioButton)findViewById(R.id.radioButtonVerdaderoEditar);
        respuestaFalsoEditar = (RadioButton)findViewById(R.id.radioButtonFalsoEditar);
        miBdd = new BaseDatos(getApplicationContext());

        //objeto tipo Bundle que captura los parametros que se han pasado a esta actividad
        Bundle parametrosExtra = getIntent().getExtras();
        if (parametrosExtra != null){
            try {
                //usamos las variables declaradas
                id = parametrosExtra.getString("idPregunta");
                pregunta = parametrosExtra.getString("pregunta");
                respuesta = parametrosExtra.getString("respuesta");

            }catch (Exception ex){ //ex recibe el tipo de error
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud "+ex.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        //presentar los datos recibidos de curso en pantalla
        txtIdPreguntaEditar.setText(id);
        txtPreguntaEditar.setText(pregunta);
        //setear respuesta en el chexbox

        if(respuesta.equals("Verdadero")){
            respuestaVerdaderoEditar.setChecked(true); //marcar el checkbox
        }else if (respuesta.equals("Falso")){
            respuestaFalsoEditar.setChecked(true); //marcar el checkbox
        }else{
            Toast.makeText(getApplicationContext(), "No se pudo encontrar la respuesta seleccionela nuevamente",
                    Toast.LENGTH_LONG).show();
        }

    }

    //Boton Cancelar
    public void cerrarVentana(View vista){
        finish();
        Intent ventanaRegistroPreguntas = new Intent(getApplicationContext(),GestionPreguntasActivity.class);
        startActivity(ventanaRegistroPreguntas); //solicitando que se abra la ventana de gestion preguntas
    }

    public void editarPregunta(View vista){
        //capturando los valores ingresados por el usuario en variables Java de tipo String
        String pregunta=txtPreguntaEditar.getText().toString();
        String respuesta;
        //validaciones
        if (pregunta.isEmpty() ){ //si algun campo esta vacio
            Toast.makeText(getApplicationContext(), "Para continuar con el registro llene todos los campos solicitados",
                    Toast.LENGTH_LONG).show(); //mostrando mensaje de campo vacio a traves de un toast
        } else {

            if(respuestaVerdaderoEditar.isChecked()){
                respuesta = "Verdadero";
                miBdd.actualizarPregunta(pregunta, respuesta,id);
                Toast.makeText(getApplicationContext(), "Pregunta actualizada correctamente",
                        Toast.LENGTH_LONG).show(); //mostrando mensaje de usuario registrado
                cerrarVentana(null); //cerrar la actividad despues de actualizar

            }else if (respuestaFalsoEditar.isChecked()){
                respuesta = "Falso";
                miBdd.actualizarPregunta(pregunta, respuesta, id);
                Toast.makeText(getApplicationContext(), "Pregunta actualizada correctamente",
                        Toast.LENGTH_LONG).show(); //mostrando mensaje de usuario registrado
                cerrarVentana(null); //cerrar la actividad despues de actualizar
            }else{
                Toast.makeText(getApplicationContext(), "Selecciona la respuesta",
                        Toast.LENGTH_LONG).show(); //mostrando que seleccione un aopcion
            }
        }
    }

    //metodo para procesar la eliminacion para presentar una ventana de confirmacion
    public void eliminarPregunta (View vista){
        //que se habla una ventanita pequeña con las opciones de eliminacion
        AlertDialog.Builder estructuraConfirmacion = new AlertDialog.Builder(this)
                .setTitle("CONFIRMACION")
                .setMessage("¿esta seguro de eliminar ela pregunta ?")
                .setPositiveButton("si, Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //procesar la eliminarcion
                        miBdd.eliminarPregunta(id); //llamo al metodo eliminar y le paso el id de l a pregunta
                        Toast.makeText(getApplicationContext(),"Pregunta Eliminada" ,Toast.LENGTH_SHORT).show();
                        cerrarVentana(null); //invocando al metodo volver
                    }
                }).setNegativeButton("No, Calcelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { //si le da a este boton no se hara ninguna accin y solo aparece el toast.
                        Toast.makeText(getApplicationContext(),"Eliminacion Cancelada" ,Toast.LENGTH_SHORT).show();

                    }
                }).setCancelable(true); //que se pueda cnacelar la operacion que se esta realziando
        AlertDialog cuadroDialogo = estructuraConfirmacion.create(); //instanciando el cuadro de dialogo con la estructura indicada
        cuadroDialogo.show(); //mostrando en pantalla el cuadro de dialogo
    }

}