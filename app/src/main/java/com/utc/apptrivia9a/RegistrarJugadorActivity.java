package com.utc.apptrivia9a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/*
@autores:Sandoval,sanchez,Robayo
@creación/ 03/07/2021
@fModificación 03/17/2021
@descripción: Registro de jugadores.
*/
public class RegistrarJugadorActivity extends AppCompatActivity {

    //entrada
    EditText txtNombresRegistro, txtApellidosRegistro; // definiendo variables para capturar datos
    BaseDatos miBdd;// creando un objeto para acceder a los procesos de la BDD SQlite
    // declaracion del cursor
    Cursor jugadorObtenido; //declaracion global para usarla desde culquier metodo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_jugador);

        //mapeo de elementos
        txtNombresRegistro= (EditText)findViewById(R.id.txtNombreRegistro);
        txtApellidosRegistro=(EditText)findViewById(R.id.txtApellidoRegistro);
        miBdd= new BaseDatos(getApplicationContext()); //instanciar /construir la base de datos en el objeto mi bdd


    }

    public void registrarUsuario(View vista){
        //capturando los valores ingresados por el usuario en variables Java de tipo String
        String nombres=txtNombresRegistro.getText().toString();
        String apellidos=txtApellidosRegistro.getText().toString();
        int puntuacion = 0;
        //validaciones
        if (nombres.isEmpty() ||apellidos.isEmpty() ){ //si algun campo esta vacio
            Toast.makeText(getApplicationContext(), "Para continuar con el registro llene todos los campos solicitados",
                    Toast.LENGTH_LONG).show(); //mostrando mensaje de campo vacio a traves de un toast
        } else {
            if (contieneSoloLetras(nombres) == false) {
                Toast.makeText(getApplicationContext(), "El nombre no debe contener numeros",
                        Toast.LENGTH_LONG).show(); //mostrando error de nombre
            } else {
                if (contieneSoloLetras(apellidos) == false) {
                    Toast.makeText(getApplicationContext(), "El apellido no debe contener numeros",
                            Toast.LENGTH_LONG).show(); //mostrando error de apellido
                } else {
                    miBdd.agregarUsuario(nombres, apellidos,puntuacion);
                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente",
                            Toast.LENGTH_LONG).show(); //mostrando mensaje de usuario registrado
                    consultarRegistroJugador();
                }
            }
        }
    }


    //llamar al ultimo registro
    public void consultarRegistroJugador(){
        jugadorObtenido=miBdd.obtenerUltimoJugador(); // consultando estudiantes y guardandoles en un cursor
        //validar si hay datos
        if (jugadorObtenido != null ){ // si es diferrente de null
            //proceso cuando recupera el registro de la Bdd
            String id=jugadorObtenido.getString(0).toString(); //capturando el id de lciente
            String nombre = jugadorObtenido.getString(1) ;// Capturando el nombre
            String apellido = jugadorObtenido.getString(2); //capturando el apellido
            String puntuacion = jugadorObtenido.getString(3); //capturando el apellido
            String jugador;
            //construyendo las filas para presentar datos en el listview ej => 1: bryan sandoval
            jugador = id+": "+nombre+" "+apellido ;
            Toast.makeText(getApplicationContext(), " A Jugar" + jugador,
                    Toast.LENGTH_LONG).show(); //mostrando mensaje de usuario registrado

            //generando el objeto para abrir la ventana de preguntas enviando los datos del jugador como parametros
            Intent ventanaPreguntaUno = new Intent(getApplicationContext(), Pregunta1Activity.class);
            //enviar parametros al intent
            ventanaPreguntaUno.putExtra("id", id);
            ventanaPreguntaUno.putExtra("nombre", nombre);
            ventanaPreguntaUno.putExtra("apellido", apellido);
            ventanaPreguntaUno.putExtra("puntuacion", puntuacion);
            startActivity(ventanaPreguntaUno); //solicitando que se abra la ventana de edicion /eliminacion
            finish(); //cerando la ventana actual

        }else{
            Toast.makeText(getApplicationContext(),"No se encontro al jugador",Toast.LENGTH_LONG).show();
        }

    }

    public void salir (View vista){
        finish(); //lamar al metodo para cerrar la app
    }

    public boolean contieneSoloLetras(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ' || c =='ñ' || c=='Ñ'
                    || c=='á' || c=='é' || c=='í' || c=='ó' || c=='ú'
                    || c=='Á' || c=='É' || c=='Í' || c=='Ó' || c=='Ú')) {
                return false;
            }
        }
        return true;
    }




}