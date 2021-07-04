package com.utc.apptrivia9a;
/*
@autores:Sandoval,sanchez,Robayo
@creación/ 19/06/2021
@fModificación 22/06/2021
@descripción: Base de Datos
*/

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    private static final String nombreBdd = "bdd_trivia"; //definiendo el nombre dela Bdd
    private static final int versionBdd = 1; //definiendo la version de la BDD

    //estructura de la tabla jugadores
    private static final String tablaUsuario = "create table usuario(id_usu integer primary key autoincrement," +
            "nombre_usu text, apellido_usu text, puntuacion_usu integer)"; // definiendo estructura de la tabla usuarios

    //estructura de la tabla Preguntas
    private static final String tablaPreguntas = "create table pregunta(id_preg integer primary key autoincrement," +
            "pregunta text, respuesta text);";

    //Constructor
    public BaseDatos(Context contexto) {
        super(contexto, nombreBdd, null, versionBdd);
    }

    //proceso 1 crear la BDD
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablaUsuario); // ejecutando el query DDl(sentencia de definicion de datos) para crear la tabla usuarios
        db.execSQL(tablaPreguntas);// ejecutando el query DDl(sentencia de definicion de datos) para crear la tabla preguntas
    }

    //proceso 2: metodo que se ejecuta automaticamente cuando se detectan cambios en la version de nuestra bdd
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //cuando se actualice
        db.execSQL("DROP TABLE IF EXISTS usuario");//elimincacion de la version anterior de la tabla usuarios se puee usar otro comando Dll como alter table
        db.execSQL(tablaUsuario); //Ejecucion del codigo para crear la tabla usuaios con su nueva estructura

        db.execSQL("DROP TABLE IF EXISTS pregunta");//elimincacion de la version anterior de la tabla usuarios se puee usar otro comando Dll como alter table
        db.execSQL(tablaPreguntas); //Ejecucion del codigo para crear la tabla usuaios con su nueva estructura
    }

    /*crud
         c -> create
         r -> leer
         U -> ACTUALIZAR
         D -> eliminar
     */

    //proceso 3: metodo para insertar datos de la tabla usuarios , retorno true cuando inserto y false cuando hay algun error
    public boolean agregarUsuario(String nombre, String apellido, Integer puntuacion ) {
        SQLiteDatabase miBdd = getWritableDatabase(); //llamando a la base de datos en el objeto mi Ddd
        if (miBdd != null) { //validando que la base de datos exista(q no sea nula)
            miBdd.execSQL("insert into usuario(nombre_usu, apellido_usu, puntuacion_usu) " +
                    "values('" + nombre + "','" + apellido + "','" + puntuacion +"')");    //ejecutando la sentencia de insercion de SQL
            miBdd.close(); //cerrando la conexion a la base de datos.
            return true; // valor de retorno si se inserto exitosamente.
        }
        return false; //retorno cuando no existe la BDD
    }

    //Metodo para consultar jugadores existentes en la base ded datos
    public Cursor obtenerUltimoJugador(){
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la base de datos
        //consultando los jugadores en la base de datos y guardando en un cursor
        Cursor jugador=miBdd.rawQuery("select * from usuario where id_usu = (select max(id_usu) from usuario);", null);
        if (jugador.moveToFirst()){ //validar si se encontro o no datos
            miBdd.close();
            //retornar el cursor que contiene el listado de jugadores
            return jugador; // retornar el cursor que contiene el listado de productos
        }else{
            return null; //se retorna nulo cuando no hay jugadores dentro de la tabla
        }
    }

    //Metodo para consultar jugadores existentes en la base ded datos
    public Cursor obtenerJugadores(){
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la base de datos
        //consultando los jugadores en la base de datos y guardando en un cursor
        Cursor jugadores=miBdd.rawQuery("select * from usuario;", null);
        if (jugadores.moveToFirst()){ //validar si se encontro o no datos
            miBdd.close();
            //retornar el cursor que contiene el listado de jugadores
            return jugadores; // retornar el cursor que contiene el listado de productos
        }else{
            return null; //se retorna nulo cuando no hay jugadores dentro de la tabla
        }
    }

    public boolean actualizarUsuario(String nombre, String apellido, Integer puntuacion, String id){
        SQLiteDatabase miBdd = getWritableDatabase(); // objeto para manejar la base de datos
        if(miBdd != null){
            //proceso de actualizacion
            miBdd.execSQL("update usuario set nombre_usu='"+nombre+"', " +
                    "apellido_usu='"+apellido+"', puntuacion_usu='"+puntuacion+"' where id_usu ="+id);
            miBdd.close(); //cerrando la conexion coon la BDD
            return true; //retornamos verdero ya que el proceso de actulaicacion fue exitoso
        }
        return false; // se retorna falso cuando no existe la base de datos
    }

    //Preguntas
    public boolean agregarPregunta(String pregunta, String respuesta){
        SQLiteDatabase miBdd =getWritableDatabase();
        if (miBdd != null) { //validando que la base de datos exista(q no sea nula)
            miBdd.execSQL("insert into pregunta(pregunta, respuesta) " +
                    "values  ('"+pregunta+"','"+respuesta+"');");
            //ejecutando la sentencia de insercion de SQL
            miBdd.close(); //cerrando la conexion a la base de datos.
            return true; // valor de retorno si se inserto exitosamente.
        }
        return false; //retorno cuando no existe la BDD
    }

    //Metodo para consultar productos existentes en la base ded datos
    public Cursor obtenerPreguntas(){
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la base de datos
        //consultando los productos en la base de datos y guardando en un cursor
        Cursor preguntas=miBdd.rawQuery("select * from pregunta;", null);
        if (preguntas.moveToFirst()){ //validar si se encontro o no clientes
            miBdd.close();
            //retornar el cursor que contiene el listado de cliente
            return preguntas; // retornar el cursor que contiene el listado de productos
        }else{
            return null; //se retorna nulo cuando no hay productos dentro de la tabla
        }
    }

    public boolean actualizarPregunta(String pregunta, String respuesta, String id){
        SQLiteDatabase miBdd = getWritableDatabase(); // objeto para manejar la base de datos
        if(miBdd != null){
            //proceso de actualizacion
            miBdd.execSQL("update pregunta set pregunta='"+pregunta+"', " +
                    "respuesta='"+respuesta+"' where id_preg ="+id);
            miBdd.close(); //cerrando la conexion coon la BDD
            return true; //retornamos verdero ya que el proceso de actulaicacion fue exitoso
        }
        return false; // se retorna falso cuando no existe la base de datos
    }

    //Metodo para eliminar un registro de una pregunta
    public boolean eliminarPregunta(String id){
        SQLiteDatabase miBdd = getWritableDatabase(); // objeto para manejar la base de datos
        if(miBdd != null){ //validando que la bdd realmente exista
            miBdd.execSQL("delete from pregunta where id_preg="+id); //ejecucion de la sentencia Sql para eliminar
            miBdd.close();
            return true; // //retornamos verdero ya que el proceso de eliminacion fue exitoso
        }
        return false; // se retorna falso cuando no existe la base de datos
    }



}
